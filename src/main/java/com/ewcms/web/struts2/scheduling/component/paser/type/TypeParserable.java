/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.web.struts2.scheduling.component.paser.type;

import com.ewcms.plugin.report.generate.vo.PageShowParam;

/**
 * 根据参数类型生成相应的页面接口
 * 
 * @author 吴智俊
 */
public interface TypeParserable {
	public String parser(PageShowParam param);
}
