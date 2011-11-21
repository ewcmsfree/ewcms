/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.report.manager.service;

import com.ewcms.plugin.report.model.Parameter;

/**
 * @author 吴智俊
 */
public interface ParameterServiceable {
	
	public Parameter findParameterById(Long parameterId);
}
