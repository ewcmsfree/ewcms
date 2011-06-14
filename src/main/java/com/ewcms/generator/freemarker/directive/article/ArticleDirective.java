/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.directive.article;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.common.lang.EmptyUtil;
import com.ewcms.generator.freemarker.directive.DirectiveOutable;
import com.ewcms.generator.freemarker.directive.ObjectPropertyDirective;
import com.ewcms.generator.freemarker.directive.out.ArticleCategoriesDirectiveOut;
import com.ewcms.generator.freemarker.directive.out.ArticleContentDirectiveOut;
import com.ewcms.generator.freemarker.directive.out.ArticleRelationsDirectiveOut;
import com.ewcms.generator.freemarker.directive.out.DateDirectiveOut;
import com.ewcms.generator.freemarker.directive.out.DefaultDirectiveOut;

import freemarker.core.Environment;
import freemarker.template.TemplateModelException;

/**
 * 文章标签
 * 
 * @author wangwei
 */
public class ArticleDirective extends ObjectPropertyDirective{
    private static final Logger logger = LoggerFactory.getLogger(ArticleDirective.class);
    
    private static final Map<String,String> DEFAULT_MAP_PROPERTIES = initDefaultMapProperties();
    private static final Map<String,DirectiveOutable> DEFAULT_MAP_DIRECTIVEOUTS = initDefaultMapDirectiveOuts();
    
    private Map<String,String> mapProperties = DEFAULT_MAP_PROPERTIES;
    
    private Map<String,DirectiveOutable> mapDirectiveOuts = DEFAULT_MAP_DIRECTIVEOUTS;
    
    @SuppressWarnings("rawtypes")
    @Override
    protected Object loopValue(Object value,String propertyName,Environment env, Map params)throws TemplateModelException{
        DirectiveOutable out = getDirectiveOut(propertyName);
        return out.loopValue(value, env, params);
    }
    
    @SuppressWarnings("rawtypes")
    @Override
    protected String constructOut(Object value,String propertyName,Environment env, Map params)throws TemplateModelException{
        DirectiveOutable out = getDirectiveOut(propertyName);
        return out.constructOut(value, env, params);
    }
    
    @SuppressWarnings("rawtypes")
    @Override
    protected String getPropertyName(Map params)throws TemplateModelException{
        String value = super.getPropertyName(params);
        return mapProperties.get(value);
    }
    
    private DirectiveOutable getDirectiveOut(String propertyName)throws TemplateModelException{
        DirectiveOutable out = mapDirectiveOuts.get(propertyName);
        if(EmptyUtil.isNull(out)){
            logger.error("Get not directive out of \"{}\"",propertyName);
            throw new TemplateModelException("Get not directive out of \""+propertyName+"\"");
        }
        return out;
    }
    
    private static Map<String,String> initDefaultMapProperties(){
        Map<String,String> map = new HashMap<String,String>();
        map.put("编号", "id");
        map.put("id", "id");
        
        map.put("标题", "title");
        map.put("title", "title");
        map.put("短标题", "shortTitle");
        map.put("shortTitle", "shortTitle");
        map.put("子标题", "subTitle");
        map.put("subTitle", "subTitle");
        
        map.put("作者", "author");
        map.put("author", "author");
        map.put("编辑", "auditReal");
        map.put("audit", "auditReal");
        
        map.put("引导图片", "image");
        map.put("image", "image");
        map.put("摘要", "summary");
        map.put("summary", "summary");
        map.put("来源", "origin");
        map.put("origin", "origin");
        map.put("关键字", "keyword");
        map.put("keyword", "keyword");
        map.put("标签", "tag");
        map.put("tag", "tag");
        map.put("分类", "categories");
        map.put("categories", "categories");
        
        map.put("链接地址", "url");
        map.put("url", "url");
        
        map.put("关联文章","relations");
        map.put("relations","relations");
        
        map.put("正文", "contents");
        map.put("content", "contents");
        
        map.put("创建时间", "createTime");
        map.put("createTime", "createTime");
        map.put("修改时间", "modified");
        map.put("modified", "modified");
        map.put("发布时间", "published");
        map.put("published", "published");
                
        return map;
    }
    
    private static Map<String,DirectiveOutable> initDefaultMapDirectiveOuts(){
        Map<String,DirectiveOutable> map = new HashMap<String,DirectiveOutable>();
        
        map.put("id", new DefaultDirectiveOut());
        map.put("title", new DefaultDirectiveOut());
        map.put("shortTitle", new DefaultDirectiveOut());
        map.put("subTitle", new DefaultDirectiveOut());
        map.put("author", new DefaultDirectiveOut());
        map.put("auditReal", new DefaultDirectiveOut());
        map.put("image", new DefaultDirectiveOut());
        map.put("summary", new DefaultDirectiveOut());
        map.put("origin", new DefaultDirectiveOut());
        map.put("keyword", new DefaultDirectiveOut());
        map.put("tag", new DefaultDirectiveOut());
        map.put("url", new DefaultDirectiveOut());
        map.put("createTime", new DateDirectiveOut());
        map.put("modified", new DateDirectiveOut());
        map.put("published", new DateDirectiveOut());
        map.put("categories", new ArticleCategoriesDirectiveOut());
        map.put("relations", new ArticleRelationsDirectiveOut());
        map.put("contents",new ArticleContentDirectiveOut());
        
        return map;
    }
    
    public void setMapProperties(Map<String,String> map){
        mapProperties = map;
    }
    
    public void setMapDirectiveOuts(Map<String,DirectiveOutable> map){
        mapDirectiveOuts = map;
    }
    
    public void putMapProperty(String name,String property){
        mapProperties.put(name, property);
    }
    
    public void putMapDirectiveOut(String name,DirectiveOutable directiveOut){
        mapDirectiveOuts.put(name, directiveOut);
    }
}
