package com.ewcms.security.manage.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ewcms.security.core.session.EwcmsSessionRegistry;
import com.ewcms.security.manage.dao.AuthorityDAOable;
import com.ewcms.security.manage.dao.GroupDAOable;
import com.ewcms.security.manage.dao.UserDAOable;
import com.ewcms.security.manage.model.Authority;
import com.ewcms.security.manage.model.Group;
import com.ewcms.security.manage.model.UserInfo;

/**
 * Test {@link UsernameService}
 * 
 * @author wangwei
 */
public class UserServiceTest extends TestCase{

    private static final Calendar calendar = Calendar.getInstance();
    
    @Test
    public void testTimeIsNullNoExcpired(){
        UserService service = new UserService();
        
        assertTrue(service.noExpired(null, null));
    }
    
    @Test
    public void testStartTimeLessCurrentTimeNoExpired(){
        UserService service = new UserService();
        
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.DATE, -2);
        Date start = calendar.getTime();
        
        assertTrue(service.noExpired(start, null));
    }
    
    @Test
    public void testStartTimeThanCurrentTimeExpried(){
        UserService service = new UserService();
        
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.DATE, 2);
        Date start = calendar.getTime();
        
        assertFalse(service.noExpired(start, null));
    }
    
    @Test
    public void testEndTimeLessCurrentTimeExpried(){
        UserService service = new UserService();
        
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.DATE, -2);
        Date end = calendar.getTime();
        
        assertFalse(service.noExpired(null, end));
    }
    
    @Test
    public void testEndTimeLessCurrentTimeNoExpried(){
        UserService service = new UserService();
        
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.DATE, 2);
        Date end = calendar.getTime();
        
        assertTrue(service.noExpired(null, end));
    }
    
    @Test
    public void testComplexTimeNoExpried(){
        UserService service = new UserService();
        
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.DATE, -2);
        Date start = calendar.getTime();
        
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.DATE, 2);
        Date end = calendar.getTime();
        
        assertTrue(service.noExpired(start, end));
    }
    
    @Test
    public void testComplexTimeExpried(){
        UserService service = new UserService();
        
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.DATE, -2);
        Date start = calendar.getTime();
        
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.DATE, -1);
        Date end = calendar.getTime();
        
        assertFalse(service.noExpired(start, end));
    }
       
    @Test
    public void testUsernameAlreadyExist(){
        UserService service = new UserService();
        
        UserDAOable dao = mock(UserDAOable.class);
        when(dao.get(any(String.class))).thenAnswer(new Answer<com.ewcms.security.manage.model.User>(){
            @Override
            public com.ewcms.security.manage.model.User answer(InvocationOnMock invocation) throws Throwable {
                 String username = (String)invocation.getArguments()[0];
                 com.ewcms.security.manage.model.User user = new com.ewcms.security.manage.model.User();
                 user.setUsername(username);
                 return user;
            }
        });
        service.setUserDao(dao);
        assertTrue(service.usernameExist("Pertty"));
    }
    
    @Test
    public void testUsernameNoExist(){
        UserService service = new UserService();
        
        UserDAOable dao = mock(UserDAOable.class);
        when(dao.get(any(String.class))).thenReturn(null);
        service.setUserDao(dao);
        assertFalse(service.usernameExist("Pertty"));
    }
    
    @Test
    public void testUsernameNoExistLoadUserByUsername(){
        UserService service = new UserService();
        
        UserDAOable dao = mock(UserDAOable.class);
        when(dao.get(any(String.class))).thenReturn(null);
        service.setUserDao(dao);
        try{
            service.loadUserByUsername("Pertty");
            fail();
        }catch(UsernameNotFoundException e){
            
        }
    }
    
    @Test
    public void testGroupAuthorities(){
        UserService service = new UserService();
        
        Group group = new Group();
        group.setName("GROUP_ADMIN");
        Set<Authority> auths = new HashSet<Authority>();
        auths.add(new Authority("ROLE_ADMIN",null));
        auths.add(new Authority("ROLE_USER",null));
        group.setAuthorities(auths);
        
        Set<GrantedAuthority> grantedAuthorities = service.groupAuthorities(group);
        assertEquals(grantedAuthorities.size(),3);
        assertTrue(grantedAuthorities.contains(new GrantedAuthorityImpl("GROUP_ADMIN")));
    }
    
    @Test
    public void testLoadUserByUsername(){
        UserService service = new UserService();
        
        UserDAOable dao = mock(UserDAOable.class);
        when(dao.get(any(String.class))).thenAnswer(new Answer<com.ewcms.security.manage.model.User>(){
            @Override
            public com.ewcms.security.manage.model.User answer(InvocationOnMock invocation) throws Throwable {
                String username = (String)invocation.getArguments()[0];
                com.ewcms.security.manage.model.User user = new com.ewcms.security.manage.model.User();
                user.setUsername(username);
                user.setEnabled(true);
                user.setAccountEnd(null);
                user.setAccountStart(null);
                user.setPassword("123456");
                
                Set<Authority> auths = new HashSet<Authority>();
                auths.add(new Authority("ROLE_USER"));
                auths.add(new Authority("ROLE_ADMIN"));
                user.setAuthorities(auths);
                
                Group group = new Group("GROUP_ADNIM");
                Set<Group> groups =new HashSet<Group>();
                groups.add(group);
                user.setGroups(groups);
                
                return user;
            }
        });
        service.setUserDao(dao);
        
        UserDetails details = service.loadUserByUsername("Pertty");
        assertNotNull(details);
        assertEquals("Pertty",details.getUsername());
        assertEquals("123456",details.getPassword());
        assertTrue(details.isEnabled());
        assertTrue(details.isAccountNonExpired());
        assertTrue(details.isAccountNonLocked());
        assertTrue(details.isCredentialsNonExpired());
        assertTrue(details.getAuthorities().size() == 3);
    }
    
    @Test
    public void testPasswordNotEncoder(){
        UserService service = new UserService();
        
        User user = new User("Pertty","123456",true,true,true,true,new ArrayList<GrantedAuthority>());
        String password = service.passwordEncoder(user, "123456");
        assertEquals("123456",password);
    }
    
    @Test
    public void testPasswordMd5(){
        UserService service = new UserService();
        
        service.setPasswordEncoder(new Md5PasswordEncoder());
        User user = new User("Pertty","123456",true,true,true,true,new ArrayList<GrantedAuthority>());
        String password = service.passwordEncoder(user, "123456");
        assertFalse("123456".equals(password));
    }
    
    @Test
    public void testUsernameExistAddUserFail(){
        UserService service = new UserService();
        
        UserDAOable dao = mock(UserDAOable.class);
        when(dao.get(any(String.class))).thenReturn(new com.ewcms.security.manage.model.User("Pertty"));
        service.setUserDao(dao);
        try{
            service.addUser("Pertty","123456", true, null, null, null,null,null);
            fail();
        }catch(UserServiceException e){
        }
    }
    
    @Test
    public void testAddUser(){
        UserService service = new UserService();
        
        UserDAOable userDao = mock(UserDAOable.class);
        when(userDao.get("Pertty")).thenReturn(null);
        service.setUserDao(userDao);
        
        GroupDAOable gropDao = mock(GroupDAOable.class);
        when(gropDao.get("GROUP_ADMIN")).thenReturn(new Group("GROUP_ADMIN"));
        when(gropDao.get("GROUP_USER")).thenReturn(new Group("GROUP_USER"));
        service.setGroupDao(gropDao);
        
        AuthorityDAOable authorityDao = mock(AuthorityDAOable.class);
        when(authorityDao.get("ROLE_ADMIN")).thenReturn(new Authority("ROLE_ADMIN"));
        when(authorityDao.get("ROLE_USER")).thenReturn(new Authority("ROLE_USER"));
        service.setAuthorityDao(authorityDao);
        
        Set<String> groupNames = new HashSet<String>();
        groupNames.add("GROUP_ADMIN");
        groupNames.add("GROUP_USER");
        
        Set<String> authNames = new HashSet<String>();
        authNames.add("ROLE_ADMIN");
        authNames.add("ROLE_USER");
        service.addUser("Pertty","123456", true, null, null, new UserInfo(), authNames, groupNames);
        
        ArgumentCaptor<com.ewcms.security.manage.model.User> argument = ArgumentCaptor.forClass(com.ewcms.security.manage.model.User.class);
        verify(userDao).persist(argument.capture());
        assertEquals(argument.getValue().getUsername(),"Pertty");
        assertTrue(argument.getValue().isEnabled());
        assertEquals(argument.getValue().getPassword(),"123456");
        assertNull(argument.getValue().getAccountEnd());
        assertNull(argument.getValue().getAccountStart());
        assertEquals(argument.getValue().getUserInfo().getUsername(),"Pertty");
        assertEquals(argument.getValue().getUserInfo().getName(),"Pertty");
        assertTrue(argument.getValue().getAuthorities().size()==2);
        assertTrue(argument.getValue().getGroups().size()==2);
    }
    
    @Test
    public void testAddUserPasswordDefaultEmpty(){
        UserService service = new UserService();
        
        UserDAOable userDao = mock(UserDAOable.class);
        when(userDao.get("Pertty")).thenReturn(null);
        service.setUserDao(userDao);
        
        GroupDAOable gropDao = mock(GroupDAOable.class);
        service.setGroupDao(gropDao);
        
        AuthorityDAOable authorityDao = mock(AuthorityDAOable.class);
        service.setAuthorityDao(authorityDao);
        
        Set<String> groupNames = new HashSet<String>();
        
        Set<String> authNames = new HashSet<String>();
        service.addUser("Pertty",null, true, null, null, new UserInfo(), authNames, groupNames);
        
        ArgumentCaptor<com.ewcms.security.manage.model.User> argument = ArgumentCaptor.forClass(com.ewcms.security.manage.model.User.class);
        verify(userDao).persist(argument.capture());
        assertEquals(argument.getValue().getPassword(),"666666");
    }
    
    @Test
    public void testUsernameNoExistUpdateUserFail(){
        UserService service = new UserService();
        
        UserDAOable dao = mock(UserDAOable.class);
        when(dao.get(any(String.class))).thenReturn(null);
        service.setUserDao(dao);
        try{
            service.updateUser("Pertty", true, null, null, null,null,null);
            fail();
        }catch(UserServiceException e){
        }
    }
    
    @Test
    public void testUpdateUser(){
        UserService service = new UserService();
        
        UserDAOable userDao = mock(UserDAOable.class);
        when(userDao.get(any(String.class))).thenAnswer(new Answer<com.ewcms.security.manage.model.User>(){
            @Override
            public com.ewcms.security.manage.model.User answer(InvocationOnMock invocation) throws Throwable {
                com.ewcms.security.manage.model.User user = new com.ewcms.security.manage.model.User();
                user.setUsername((String)invocation.getArguments()[0]);
                user.setEnabled(true);
                user.setPassword("123456");
                Set<Authority> authorities = new HashSet<Authority>();
                authorities.add(new Authority("ROLE_ADMIN"));
                user.setAuthorities(authorities);
                Set<Group> groups = new HashSet<Group>();
                groups.add(new Group("GROUP_ADMIN"));
                groups.add(new Group("GROUP_USER"));
                groups.add(new Group("GROUP_TEST"));
                user.setGroups(groups);
                UserInfo userInfo = new UserInfo();
                userInfo.setUsername((String)invocation.getArguments()[0]);
                userInfo.setName((String)invocation.getArguments()[0]);
                user.setUserInfo(userInfo);
                return user;
            }
        });
        service.setUserDao(userDao);
        
        GroupDAOable gropDao = mock(GroupDAOable.class);
        when(gropDao.get("GROUP_ADMIN")).thenReturn(new Group("GROUP_ADMIN"));
        when(gropDao.get("GROUP_USER")).thenReturn(new Group("GROUP_USER"));
        service.setGroupDao(gropDao);
        
        AuthorityDAOable authorityDao = mock(AuthorityDAOable.class);
        when(authorityDao.get("ROLE_ADMIN")).thenReturn(new Authority("ROLE_ADMIN"));
        when(authorityDao.get("ROLE_USER")).thenReturn(new Authority("ROLE_USER"));
        service.setAuthorityDao(authorityDao);
        
        Set<String> groupNames = new HashSet<String>();
        groupNames.add("GROUP_ADMIN");
        groupNames.add("GROUP_USER");
        
        Set<String> authNames = new HashSet<String>();
        authNames.add("ROLE_ADMIN");
        authNames.add("ROLE_USER");
        
        UserInfo info = new UserInfo();
        info.setName("Pertty User");
        info.setBirthday(new Date());
        info.setEmail("pertty@xxxx.com");
        info.setIdentification("1234567890");
        info.setMphone("8798444");
        info.setPhone("348533");
        Calendar calendar = Calendar.getInstance();
        Date start = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date end  = calendar.getTime();
        
        service.updateUser("Pertty",false, start, end, info, authNames, groupNames);
        
        ArgumentCaptor<com.ewcms.security.manage.model.User> argument = ArgumentCaptor.forClass(com.ewcms.security.manage.model.User.class);
        verify(userDao).persist(argument.capture());
        assertEquals(argument.getValue().getUsername(),"Pertty");
        assertFalse(argument.getValue().isEnabled());
        assertEquals(argument.getValue().getPassword(),"123456");
        assertNotNull(argument.getValue().getAccountEnd());
        assertNotNull(argument.getValue().getAccountStart());
        assertEquals(argument.getValue().getUserInfo().getUsername(),"Pertty");
        assertEquals(argument.getValue().getUserInfo().getName(),"Pertty User");
        assertNotNull(argument.getValue().getUserInfo().getBirthday());
        assertEquals(argument.getValue().getUserInfo().getEmail(),"pertty@xxxx.com");
        assertEquals(argument.getValue().getUserInfo().getIdentification(),"1234567890");
        assertEquals(argument.getValue().getUserInfo().getMphone(),"8798444");
        assertEquals(argument.getValue().getUserInfo().getPhone(),"348533");
        assertTrue(argument.getValue().getAuthorities().size()==2);
        assertTrue(argument.getValue().getGroups().size()==2);
    }
    
    @Test
    public void testUpdateUserMustRemoveExpriedUser(){
        UserService service = new UserService();
        
        UserDAOable userDao = mock(UserDAOable.class);
        when(userDao.get(any(String.class))).thenAnswer(new Answer<com.ewcms.security.manage.model.User>(){
            @Override
            public com.ewcms.security.manage.model.User answer(InvocationOnMock invocation) throws Throwable {
                com.ewcms.security.manage.model.User user = new com.ewcms.security.manage.model.User();
                user.setUsername((String)invocation.getArguments()[0]);
                user.setEnabled(true);
                user.setPassword("123456");
                Set<Authority> authorities = new HashSet<Authority>();
                user.setAuthorities(authorities);
                Set<Group> groups = new HashSet<Group>();
                user.setGroups(groups);
                UserInfo userInfo = new UserInfo();
                userInfo.setUsername((String)invocation.getArguments()[0]);
                userInfo.setName((String)invocation.getArguments()[0]);
                user.setUserInfo(userInfo);
                return user;
            }
        });
        service.setUserDao(userDao);
        
        EwcmsSessionRegistry sessionRegistry = mock(EwcmsSessionRegistry.class);
        service.setSessionRegistry(sessionRegistry);
        
        service.updateUser("Pertty",false, null, null, new UserInfo(), new HashSet<String>(), new HashSet<String>());
        verify(sessionRegistry).removeSessionInformationByUsername("Pertty");
    }
}
