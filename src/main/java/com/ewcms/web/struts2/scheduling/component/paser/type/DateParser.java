/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.web.struts2.scheduling.component.paser.type;

import com.ewcms.plugin.report.generate.vo.PageShowParam;

/**
 * 日期输入框,只能使用日期辅助录入
 * 
 * @author 吴智俊
 */
public class DateParser implements TypeParserable {

	public String parser(PageShowParam param) {
		String defaultValue = param.getDefaultValue();
		if (defaultValue == null || defaultValue.length() == 0) {
			defaultValue = "";
		}
		String html = "<input type='text' id='" + param.getEnName() + "' name='" + param.getEnName()
				+ "' value='" + defaultValue + "' class='Wdate' onfocus=\"WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,readOnly:true})\"/>";
		return html;
	}

}
