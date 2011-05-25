/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.common.query.cache;

import com.ewcms.common.query.Resultable;

public interface CacheResultable extends Resultable{
    
    String getCacheKey();
    
    boolean isModified();
}
