/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.publication.freemarker.error;

import java.io.IOException;
import java.io.Writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

/**
 * 处理freemarker模板错误
 * 
 * @author wuzhijun
 */
public class EwcmsFreemarkerExceptionHandler implements TemplateExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(EwcmsFreemarkerExceptionHandler.class);
	
	@Override
	public void handleTemplateException(TemplateException te, Environment env, Writer out) throws TemplateException {
		try {
			out.write("Error: [" + te.getMessage() + "]");
			logger.warn("Freemarker Error: [" + te.getMessage() + "]");
		} catch (IOException e) {
			logger.warn(e.getMessage());
			throw new TemplateException("Failed to print error message. Cause: " + e, env);
		}
	}
}
