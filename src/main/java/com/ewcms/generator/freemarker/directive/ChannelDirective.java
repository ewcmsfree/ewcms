/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.directive;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.ewcms.generator.freemarker.FreemarkerUtil;
import com.ewcms.generator.freemarker.GlobalVariable;
import com.ewcms.generator.freemarker.directive.out.DefaultDirectiveOut;
import com.ewcms.generator.freemarker.directive.out.LengthDirectiveOut;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 频道属性标签
 *
 * @author wangwei
 */
public class ChannelDirective extends ArticleDirective{
    
    @SuppressWarnings("rawtypes")
    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars,
            TemplateDirectiveBody body) throws TemplateException, IOException {
        
        FreemarkerUtil.setVariable(env, valueParam, GlobalVariable.CHANNEL.toString());
        super.execute(env, params, loopVars, body);
        FreemarkerUtil.removeVariable(env, valueParam);
    }
      
    protected Map<String,String> initDefaultAliasProperties(){
        Map<String,String> map = new HashMap<String,String>();
        
        map.put("编号", "id");
        map.put("id", "id");
        
        map.put("标题", "name");
        map.put("title", "name");
        map.put("name", "name");
        
        map.put("图片", "iconUrl");
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
