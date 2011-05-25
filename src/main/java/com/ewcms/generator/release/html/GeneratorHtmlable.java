/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.generator.release.html;

import com.ewcms.generator.release.ReleaseException;
import com.ewcms.generator.freemarker.directive.page.PageParam;

import freemarker.template.Configuration;
import java.io.Writer;

/**
 *
 * @author wangwei
 */
public interface GeneratorHtmlable <T>{
 
    public void process(Configuration cfg,Writer writer,T object,PageParam pageParm)throws ReleaseException;

    public void processDebug(Configuration cfg,Writer writer,T objec,PageParam pageParam)throws ReleaseException;
    
}
