/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.freemarker.directive.page;

import org.springframework.util.Assert;

import com.ewcms.common.lang.EmptyUtil;
import com.ewcms.publication.PublishException;
import com.ewcms.publication.freemarker.FreemarkerUtil;
import com.ewcms.publication.freemarker.GlobalVariable;
import com.ewcms.publication.uri.UriRuleable;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;

/**
 * 页面跳转基础标签类
 * <br>
 * 主要为标签得到通用参数值如：页数、总页数。
 * 
 * @author wangwei
 *
 */
public abstract class SkipBaseDirective implements TemplateDirectiveModel {

    private static final Integer DEFAULT_PAGE_COUNT = 1;
    private static final Integer DEFAULT_PAGE_NUMBER = 0;

    /**
     * 得到当前页数
     * 
     * @param env 
     *         Freemarker 环境变量
     * @return
     * @throws TemplateModelException
     */
    protected Integer getPageNumberValue(Environment env)throws TemplateException {
        Integer value = FreemarkerUtil.getInteger(env,GlobalVariable.PAGE_NUMBER.toString());
        return EmptyUtil.isNull(value) ? DEFAULT_PAGE_NUMBER : value;
    }

    /**
     * 得到总页数
     * 
     * @param env 
     *         Freemarker 环境变量
     * @return
     * @throws TemplateModelException
     */
    protected Integer getPageCountValue(Environment env)throws TemplateException {
        Integer value = FreemarkerUtil.getInteger(env, GlobalVariable.PAGE_COUNT.toString());
        return EmptyUtil.isNull(value) ? DEFAULT_PAGE_COUNT : value;
    }

    /**
     * 得到Uri生成那个规则
     * 
     * @param env
     * @return
     * @throws TemplateException
     */
    protected UriRuleable getUriRule(Environment env)throws TemplateException{
        return (UriRuleable)FreemarkerUtil.getBean(env, GlobalVariable.URI_RULE.toString());
    }
    
    static class GeneratorUrl{
        
        private UriRuleable rule;
        private Integer currentPage;
        
        GeneratorUrl(UriRuleable rule,Integer currentPage){
            Assert.notNull(rule);
            this.rule = rule;
            this.currentPage = currentPage;
        }
        
        /**
         * 得到链接地址
         * 
         * @param rule 
         *         uri生成规则
         * @param page
         *         页数
         * @return
         * @throws TemplateException
         */
        public String getUriValue(Integer page) throws TemplateException {
            try{
                setPage(page);
                String uri =  rule.getUri(); 
                setPage(currentPage);
                return uri;
            }catch(PublishException e){
                throw new TemplateModelException("Generator uri error:{}",e);
            }
        }
        
        private void setPage(Integer page){
            rule.putParameter(GlobalVariable.PAGE_NUMBER.toString(), page);
        }
    }
}
