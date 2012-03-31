/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.security.acls.domain;

import org.springframework.security.acls.domain.AbstractPermission;

public class EwcmsPermission extends AbstractPermission{

	private static final long serialVersionUID = 2981918244450659548L;
	
	public static final EwcmsPermission READ = new EwcmsPermission(1 << 0, 'R'); // 1
    public static final EwcmsPermission WRITE = new EwcmsPermission(1 << 1, 'W'); // 2
    public static final EwcmsPermission PUBLISH = new EwcmsPermission(1 << 2,'P'); // 4
    //专栏管理权限
    public static final EwcmsPermission CREATE = new EwcmsPermission(1 << 3, 'C'); // 8
    public static final EwcmsPermission UPDATE = new EwcmsPermission(1 << 4, 'U'); // 16
    public static final EwcmsPermission DELETE = new EwcmsPermission(1 << 5, 'D'); // 32
    public static final EwcmsPermission ADMIN = new EwcmsPermission(1 << 6, 'A'); // 64
    
    protected EwcmsPermission(int mask) {
        super(mask);
    }
    
    protected EwcmsPermission(int mask,char code){
        super(mask,code);
    }
    
    public static EwcmsPermission maskOf(int mask){
        if(mask == READ.getMask()){
            return READ;
        }
        if(mask == WRITE.getMask()){
            return WRITE;
        }
        if(mask == PUBLISH.getMask()){
            return PUBLISH;
        }
        if(mask == CREATE.getMask()){
            return CREATE;
        }
        if(mask == UPDATE.getMask()){
            return UPDATE;
        }
        if(mask == DELETE.getMask()){
            return DELETE;
        }
        if(mask == ADMIN.getMask()){
            return ADMIN;
        }
        throw new PermissionNotFoundException("mask=" + String.valueOf(mask)+" permission not font");
    }
}
