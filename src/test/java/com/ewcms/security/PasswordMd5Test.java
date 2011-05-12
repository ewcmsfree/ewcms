/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ewcms.security;

import java.util.ArrayList;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.dao.ReflectionSaltSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.junit.Test;
/**
 *
 * @author wangwei
 */
public class PasswordMd5Test {

    private static final Log log = LogFactory.getLog(PasswordMd5Test.class);

    @Test
    public void testMd5(){
        Md5PasswordEncoder encoder = new Md5PasswordEncoder();
        User user = new User("resource", "123456", true, true, true, true, new ArrayList());

        ReflectionSaltSource saltSource = new ReflectionSaltSource();
        saltSource.setUserPropertyToUse("username");
        Object salt = saltSource.getSalt(user);

        String password = encoder.encodePassword("123456", salt);

        log.info(password);
    }
}
