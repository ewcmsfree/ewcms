/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.common.query;

import java.util.List;

public interface Resultable {
 
    int getCount();
    
    int getPageCount();
    
    List<Object> getResultList();
    
    List<Object> getExtList();
}
