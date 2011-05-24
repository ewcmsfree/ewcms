package com.ewcms.common.query;

import java.util.List;

public interface Resultable {
 
    int getCount();
    
    int getPageCount();
    
    List<Object> getResultList();
    
    List<Object> getExtList();
}
