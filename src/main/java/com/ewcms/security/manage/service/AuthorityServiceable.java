/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.security.manage.service;

/**
 * 通用权限管理接口
 * 
 * @author wangwei
 */
public interface AuthorityServiceable {

    public static final String Authority_NAME_PERFIX = "ROLE_";
    
    /**
     * 判断通过用权限名是否存在
     * 
     * @param name 通用权限名 
     * @return if 'true' exist ,else 'false' not exist
     */
    public boolean hasAuthorityname(String name);
    
}
