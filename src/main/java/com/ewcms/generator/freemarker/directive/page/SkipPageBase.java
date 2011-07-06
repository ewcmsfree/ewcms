/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.directive.page;

import org.springframework.util.Assert;

import com.ewcms.generator.ReleaseException;
import com.ewcms.generator.freemarker.GlobalVariable;
import com.ewcms.generator.uri.UriRuleable;

import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;

/**
 * 
 * @author wangwei
 *
 * @param <T>
 */
abstract class SkipPageBase implements SkipPageable<PageOut> {
    
    /**
    * 得到链接地址
    * 
    * @param rule 
    *         uri生成规则
    * @param pageNumber
    *         页数
    * @return
    * @throws TemplateException
    */
   protected String getUriValue(UriRuleable rule,Integer pageNumber) throws TemplateException {
       Assert.notNull(rule);
       rule.putParameter(GlobalVariable.PAGE_NUMBER.toString(), pageNumber);
       try{
           return rule.getUri();    
       }catch(ReleaseException e){
           throw new TemplateModelException("Generator uri error:{}",e);
       }
   }
}
