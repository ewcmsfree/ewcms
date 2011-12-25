/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.publication.uri;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;

/**
 * 创建不同uri生成规则对象
 * 
 * @author wangwei
 */
public class UriRules {
    private final static Map<String,RuleParseable> cacheRuleParse = 
        Collections.synchronizedMap(new HashMap<String,RuleParseable>());
    
    private UriRules(){
        //factory class,so hide.    
    }
    
    /**
     * 创建空的uri规则生成对象
     * 
     * @return
     */
    public static UriRuleable newNull(){
        return new NullUriRule();
    }
    
    /**
     * 创建Home页uri规则生成对象
     * 
     * @return
     */
    public static UriRuleable newHome(){
        String patter = "${c.absUrl}/index.html";
        return newUriRuleBy(patter);
    }
    
    /**
     * 创建List页uri规则生成对象
     * 
     * @return
     */
    public static UriRuleable newList(){
        String patter = "${c.absUrl}/${p}.html";
        return newUriRuleBy(patter);
    }
    
    /**
     * 创建Detail页uri规则生成对象
     * 
     * @return
     */
    public static UriRuleable newDetail(){
        String patter = "/document/${a.createTime}/${a.id}_${p}.html";
        return newUriRuleBy(patter);
    }
    
    /**
     * 初始化资源参数
     * 
     * @param rule uri规则
     * @param context 资源上下文
     * @return
     */
    private static UriRuleable initResourceParameters(UriRuleable rule,String context){
        rule.putParameter("context", context);
        rule.putParameter("n",RandomStringUtils.randomNumeric(32));
        return rule;
    }
    
    /**
     * 创建内容资源uri规则生成对象
     * 
     * @return
     */
    public static UriRuleable newResource(String context){
        String patter = "/${context}/${now}/${n}";
        UriRuleable rule = newUriRuleBy(patter);
        return initResourceParameters(rule,context);
    }
    
    /**
     * 创建内容资源引导图uri规则生成对象
     * 
     * @return
     */
    public static UriRuleable newResourceThumb(String context){
        String patter = "/${context}/${now}/${n}_thumb";
        UriRuleable rule = newUriRuleBy(patter);
        return initResourceParameters(rule,context);
    }
    
    /**
     * 创建文章预览uri规则生成对象
     * 
     * @return
     */
    public static UriRuleable newArticlePreview(){
        String patter = "${a.id}.html?view=true&channelId=${c.id}&articleId=${a.id}&page=${p}";
        return newUriRuleBy(patter);
    }
    
    /**
     * 根据指定的规则创建uri规则生成对象
     * b
     * @return
     */
    public static UriRuleable newUriRuleBy(String patter){
        synchronized(cacheRuleParse){
            RuleParseable parse = cacheRuleParse.get(patter);
            if(parse == null){
                parse = new RuleParse(patter);
            }
            return new UriRule(parse);
        }
    }
}
