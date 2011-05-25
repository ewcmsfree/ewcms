/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.security.acls.domain;

import java.util.Map;
import com.ewcms.security.acls.domain.EwcmsPermission;
import org.springframework.security.acls.domain.DefaultPermissionFactory;
import org.springframework.security.acls.model.Permission;

public class EwcmsPermissionFactory extends DefaultPermissionFactory{
    
    public EwcmsPermissionFactory(){
        super(EwcmsPermission.class);
    }
    
    public EwcmsPermissionFactory(Class<Permission> permissionClass){
        super(permissionClass);
    }
    
    public EwcmsPermissionFactory(Map<String,? extends Permission> namedPermissions){
        super(namedPermissions);
    }

}
