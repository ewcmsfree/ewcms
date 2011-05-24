package com.ewcms.common.query.cache;

import com.ewcms.common.query.Resultable;

public interface CacheResultable extends Resultable{
    
    String getCacheKey();
    
    boolean isModified();
}
