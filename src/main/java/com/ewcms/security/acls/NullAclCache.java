/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.security.acls;

import java.io.Serializable;

import org.springframework.security.acls.model.AclCache;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.ObjectIdentity;

public class NullAclCache implements AclCache {

	@Override
	public void evictFromCache(Serializable pk) {
		
	}

	@Override
	public void evictFromCache(ObjectIdentity objectIdentity) {
		
	}

	@Override
	public MutableAcl getFromCache(ObjectIdentity objectIdentity) {
		return null;
	}

	@Override
	public MutableAcl getFromCache(Serializable pk) {
		return null;
	}

	@Override
	public void putInCache(MutableAcl acl) {
		
	}

	@Override
	public void clearCache() {
		
	}	
}
