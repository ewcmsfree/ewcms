/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.directive;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.ewcms.common.lang.EmptyUtil;
import com.ewcms.generator.freemarker.directive.out.DateDirectiveOut;
import com.ewcms.generator.freemarker.directive.out.DefaultDirectiveOut;
import com.ewcms.generator.freemarker.directive.out.article.CategoriesDirectiveOut;
import com.ewcms.generator.freemarker.directive.out.article.ContentDirectiveOut;
import com.ewcms.generator.freemarker.directive.out.article.RelationsDirectiveOut;

import freemarker.core.Environment;
import freemarker.template.TemplateModelException;

/**
 * 文章标签
 * 
 * @author wangwei
 */
public class ArticleDirective extends ObjectPropertyDirective{
    private static final Logger logger = LoggerFactory.getLogger(ArticleDirective.class);
    
    private static final Map<String,String> DEFAULT_ALIAS_PROPERTIES = initDefaultAliasProperties();
    private static final Map<String,DirectiveOutable> DEFAULT_PROPERTY_DIRECTIVEOUTS = initDefaultPropertyDirectiveOuts();
    
    private Map<String,String> aliasProperties = DEFAULT_ALIAS_PROPERTIES;
    private Map<String,DirectiveOutable> propertyDirectiveOuts = DEFAULT_PROPERTY_DIRECTIVEOUTS;
    
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
        return getPropertyName(value);
    }
    
    /**
     * 通过别名或属性名，得到对应的属性名
     * 
     * @param name 别名或属性名
     * @return 属性名
     * @throws TemplateModelException
     */
    protected String getPropertyName(String name)throws TemplateModelException{
        String property = aliasProperties.get(name);
        if(EmptyUtil.isNull(property)){
            logger.error("Get not property name of \"{}\"",name);
            throw new TemplateModelException("Get not property name of \""+name+"\"");
        }
        return property;
    }
    
    /**
     * 通过属性名得到对应标签输出类
     * 
     * @param propertyName 属性名
     * @return 输出标签对象
     * @throws TemplateModelException
     */
    protected DirectiveOutable getDirectiveOut(String propertyName)throws TemplateModelException{
        DirectiveOutable out = propertyDirectiveOuts.get(propertyName);
        if(EmptyUtil.isNull(out)){
            logger.error("Get not directive out of \"{}\"",propertyName);
            throw new TemplateModelException("Get not directive out of \""+propertyName+"\"");
        }
        return out;
    }
    
    private static Map<String,String> initDefaultAliasProperties(){
        Map<String,String> map = new HashMap<String,String>();
        
        map.put("编号", "id");
        map.put("id", "id");
        
        map.put("标题", "title");
        map.put("title", "title");
        
        map.put("短标题", "shortTitle");
        map.put("shortTitle", "shortTitle");
        
        map.put("副标题", "subTitle");
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
    
    private static Map<String,DirectiveOutable> initDefaultPropertyDirectiveOuts(){
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
        map.put("categories", new CategoriesDirectiveOut());
        map.put("relations", new RelationsDirectiveOut());
        map.put("contents",new ContentDirectiveOut());
        map.put("createTime", new DateDirectiveOut());
        map.put("modified", new DateDirectiveOut());
        map.put("published", new DateDirectiveOut());
        
        return map;
    }

    /**
     * 放入指定的属性标签
     * 
     * <p>标签存在则更新标签</p>
     * 
     * @param property 属性名
     * @param directiveOut 标签输出
     */
    public void putDirective(String property,DirectiveOutable directiveOut){
        Assert.hasText(property);
        Assert.notNull(directiveOut);
        
        putDirective(property, property,directiveOut);
    }
    
    /**
     * 放入指定的属性标签
     * 
     * <p>标签存在则更新标签</p>
     * 
     * @param alias    属性别名
     * @param property 属性名
     * @param directiveOut 标签输出
     */
    public void putDirective(String alias,String property,DirectiveOutable directiveOut){
        Assert.hasText(alias);
        Assert.hasText(property);
        Assert.notNull(directiveOut);
        
        aliasProperties.put(alias, property);    
        aliasProperties.put(property, property);
        propertyDirectiveOuts.put(property, directiveOut);
    }
}
