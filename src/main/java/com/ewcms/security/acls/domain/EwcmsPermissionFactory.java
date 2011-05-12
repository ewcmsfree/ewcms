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
