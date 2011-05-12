package com.ewcms.security.core.session;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Test {@link EwcmsSessionRegistryImpl}
 * 
 * @author wangwei
 */
public class EwcmsSessionRegistryImplTest extends TestCase{
    
    private EwcmsSessionRegistryImpl initSessionRegistry(){
        EwcmsSessionRegistryImpl sessionRegistry = new EwcmsSessionRegistryImpl();
        
        User user = new User("Pertty","123456",true,true,true,true,new ArrayList<GrantedAuthority>());
        sessionRegistry.registerNewSession("perttyid", user);
        sessionRegistry.registerNewSession("jonsid", "Jons");
        return sessionRegistry;       
    }
    
    @Test
    public void testGetPrincipalTypeUserDetails(){
        EwcmsSessionRegistryImpl sessionRegistry = initSessionRegistry();
        Object p = sessionRegistry.getPrincipal("Pertty");
        assertNotNull(p);
        assertTrue(p instanceof UserDetails);
    }
    
    @Test
    public void testGetPrincipalTypeString(){
        EwcmsSessionRegistryImpl sessionRegistry = initSessionRegistry();
        Object p = sessionRegistry.getPrincipal("Jons");
        assertNotNull(p);
        assertTrue(p instanceof String);
    }

    @Test
    public void testRemoveSessionInformationByUsername(){
        EwcmsSessionRegistryImpl sessionRegistry = initSessionRegistry();
                
        User user = new User("Pertty","123456",true,true,true,true,new ArrayList<GrantedAuthority>());
        sessionRegistry.removeSessionInformationByUsername(user.getUsername());
        List<SessionInformation> sessionInformations = sessionRegistry.getAllSessions(user,true);
        assertTrue(sessionInformations.isEmpty());  
    }
    
    @Test
    public void testExpiredSessionInformationByUsername(){
        EwcmsSessionRegistryImpl sessionRegistry = initSessionRegistry();
        
        String username = "Jons";
        sessionRegistry.expiredSessionInformationByUsername(username);
        List<SessionInformation> sessionInformations = sessionRegistry.getAllSessions(username,true);
        assertTrue(sessionInformations.size() == 1);  
        assertTrue(sessionInformations.get(0).isExpired());
    }
}
