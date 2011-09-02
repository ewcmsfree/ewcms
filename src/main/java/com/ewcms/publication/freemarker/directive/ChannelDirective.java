/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.freemarker.directive;

import java.util.HashMap;
import java.util.Map;

import com.ewcms.publication.freemarker.GlobalVariable;
import com.ewcms.publication.freemarker.directive.out.DefaultDirectiveOut;
import com.ewcms.publication.freemarker.directive.out.DirectiveOutable;
import com.ewcms.publication.freemarker.directive.out.LengthDirectiveOut;

/**
 * 频道属性标签
 *
 * @author wangwei
 */
public class ChannelDirective extends ArticleDirective{
    
    @Override
    protected String defaultValueParamValue(){
        return GlobalVariable.CHANNEL.toString();
    }
      
    protected Map<String,String> initDefaultAliasProperties(){
        Map<String,String> map = new HashMap<String,String>();
        
        map.put("编号", "id");
        map.put("id", "id");
        
        map.put("标题", "name");
        map.put("title", "name");
        map.put("name", "name");
        
        map.put("引导图", "iconUrl");
        map.put("image", "iconUrl");
        map.put("iconUrl", "iconUrl");
        
        map.put("链接地址", "absUrl");
        map.put("url", "absUrl");
        map.put("absUrl", "absUrl");
                
        return map;
    }
    
    protected Map<String,DirectiveOutable> initDefaultPropertyDirectiveOuts(){
        
        Map<String,DirectiveOutable> map = new HashMap<String,DirectiveOutable>();
        
        map.put("id", new DefaultDirectiveOut());
        map.put("name", new LengthDirectiveOut());
        map.put("iconUrl", new DefaultDirectiveOut());
        map.put("absUrl", new DefaultDirectiveOut());
        
        return map;
    }
}
