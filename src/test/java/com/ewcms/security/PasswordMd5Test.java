/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ewcms.security;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.dao.ReflectionSaltSource;

import org.junit.Test;
/**
 *
 * @author wangwei
 */
public class PasswordMd5Test {

	private static final Logger logger = LoggerFactory.getLogger(PasswordMd5Test.class);

    @Test
    public void testMd5(){
        Md5PasswordEncoder encoder = new Md5PasswordEncoder();
        User user = new User("resource", "123456", true, true, true, true, new ArrayList());

        ReflectionSaltSource saltSource = new ReflectionSaltSource();
        saltSource.setUserPropertyToUse("username");
        Object salt = saltSource.getSalt(user);

        String password = encoder.encodePassword("123456", salt);

        logger.info(password);
    }
}
