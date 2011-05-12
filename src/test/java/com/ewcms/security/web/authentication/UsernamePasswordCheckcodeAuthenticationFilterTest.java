package com.ewcms.security.web.authentication;

import static org.mockito.Mockito.*;

import com.octo.captcha.service.CaptchaService;
import junit.framework.TestCase;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

/**
 * Test {@link UsernamePasswordCheckcodeAuthenticationFilter}
 * 
 * @author wangwei
 */
public class UsernamePasswordCheckcodeAuthenticationFilterTest extends TestCase{

    @Test
    public void testUsingDifferentParamenterNameWorksAsExpceted(){
        
        UsernamePasswordCheckcodeAuthenticationFilter filter = new UsernamePasswordCheckcodeAuthenticationFilter();
        filter.setAuthenticationManager(createAuthenticationManage());
        filter.setCaptchaService(createCaptchaService(true));
        
        filter.setUsernameParameter("u");
        filter.setPasswordParameter("p");
        filter.setCheckcodeParameter("c");
        
        MockHttpServletRequest request = new MockHttpServletRequest("POST","/");
        request.addParameter("u", "Pertty");
        request.addParameter("p", "123456");
        request.addParameter("c", "aaaa");
        
        Authentication auth = filter.attemptAuthentication(request, new MockHttpServletResponse());
        assertNotNull(auth);
        assertEquals("127.0.0.1",((WebAuthenticationDetails)auth.getDetails()).getRemoteAddress());
        
    }
    
    @Test
    public void testCheckcodeFailedAuthenticationThrowsException() {
        UsernamePasswordCheckcodeAuthenticationFilter filter = new UsernamePasswordCheckcodeAuthenticationFilter();
        filter.setCaptchaService(createCaptchaService(false));
                
        MockHttpServletRequest request = new MockHttpServletRequest("POST","/");
        request.addParameter(UsernamePasswordCheckcodeAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, "Pertty");
        request.addParameter(UsernamePasswordCheckcodeAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY, "123456");
        request.addParameter(UsernamePasswordCheckcodeAuthenticationFilter.FORM_CODECHECK_KEY, "aaaa");
        try{
            filter.attemptAuthentication(request, new MockHttpServletResponse());
            fail("Expected AuthenticationException");
        }catch(Exception e){
            
        }
    }
    
    @Test
    public void testAuthenticationExceptionAddUsernameToSessionAttribute(){
        UsernamePasswordCheckcodeAuthenticationFilter filter = new UsernamePasswordCheckcodeAuthenticationFilter();
        filter.setAuthenticationManager(createAuthenticationManage());
        filter.setCaptchaService(createCaptchaService(false));
        filter.setAllowSessionCreation(true);
        
        MockHttpServletRequest request = new MockHttpServletRequest("POST","/");
        request.addParameter(UsernamePasswordCheckcodeAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, "Pertty");
        request.addParameter(UsernamePasswordCheckcodeAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY, "123456");
        request.addParameter(UsernamePasswordCheckcodeAuthenticationFilter.FORM_CODECHECK_KEY, "aaaa");
        try{
            filter.attemptAuthentication(request, new MockHttpServletResponse());
            fail("Expected AuthenticationException");
        }catch(Exception e){
            
        }
        
        assertEquals("Pertty",request.getSession().getAttribute(UsernamePasswordCheckcodeAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY));
    }
    
    @Test
    public void testAuthenticationSuccessRemoveUsernameInSessionAttribute(){
        
        UsernamePasswordCheckcodeAuthenticationFilter filter = new UsernamePasswordCheckcodeAuthenticationFilter();
        filter.setAuthenticationManager(createAuthenticationManage());
        filter.setCaptchaService(createCaptchaService(true));
        filter.setAllowSessionCreation(true);
        
        MockHttpServletRequest request = new MockHttpServletRequest("POST","/");
        request.addParameter(UsernamePasswordCheckcodeAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, "Pertty");
        request.addParameter(UsernamePasswordCheckcodeAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY, "123456");
        request.addParameter(UsernamePasswordCheckcodeAuthenticationFilter.FORM_CODECHECK_KEY, "aaaa");
 
        filter.attemptAuthentication(request, new MockHttpServletResponse());
     
        
        assertNull(request.getSession().getAttribute(UsernamePasswordCheckcodeAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY));
    }
    
    private AuthenticationManager createAuthenticationManage(){
        AuthenticationManager am = mock(AuthenticationManager.class);
        when(am.authenticate(any(Authentication.class))).thenAnswer(new Answer<Authentication>(){
            @Override
            public Authentication answer(InvocationOnMock invocation)throws Throwable {
                return (Authentication)invocation.getArguments()[0];
            }
            
        });
        return am;
    }
    
    private CaptchaService createCaptchaService(final boolean enabeld){
        CaptchaService cs = mock(CaptchaService.class);
        when(cs.validateResponseForID(any(String.class), any(String.class))).thenReturn(enabeld);
        
        return cs;
    }
    
    
}
