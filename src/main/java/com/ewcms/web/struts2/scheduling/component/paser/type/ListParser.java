/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.web.struts2.scheduling.component.paser.type;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.ewcms.plugin.report.generate.vo.PageShowParam;

/**
 * 下拉列表框
 * 
 * @author 吴智俊
 */
public class ListParser implements TypeParserable {

	public String parser(PageShowParam param) {
		StringBuffer html = new StringBuffer();
		
	    String defaultValue = param.getDefaultValue();
	    String enName = param.getEnName();
	    if (defaultValue != null && defaultValue.length() > 0){
			html.append("<script language='javascript' type='text/javascript'>\n");
			html.append("  function selectItemByValue(){\n");
			html.append("    try{\n");
			html.append("      var selects = document.getElementsByName('" + enName + "');\n");
			html.append("      for (var i = 0; i < selects[0].options.length; i++){\n");
			html.append("        if (selects[0].options[i].text == \"" + defaultValue + "\"){\n");
			html.append("          selects[0].options[i].selected = true;\n");
			html.append("          break;\n");
			html.append("        }\n");
			html.append("      }\n");
			html.append("    }catch(err){\n");
			html.append("    }\n");
			html.append("  }\n");
		    html.append("if (window.attachEvent) {\n");
			html.append("  window.attachEvent('onload', selectItemByValue);\n");
			html.append("}else {\n");
			html.append("  window.addEventListener('load', selectItemByValue, false);\n");
			html.append("}\n");
			html.append("</script>\n");
	    }		
		
		html.append("<select id='" + enName + "' name='" + enName + "' size='1'>");
		Map<String, String> valueMap = param.getValue();
		if (!valueMap.isEmpty()) {
			Iterator<Entry<String, String>> it = valueMap.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, String> entry = (Map.Entry<String, String>) it
						.next();
				// String name = entry.getKey();
				String value = entry.getValue();
				if (value == null || value.length() == 0) {
					value = "";
				}
				html.append("<option value='" + value + "'>" + value + "</option>\n");
			}
		}
		html.append("</select>\n");
		return html.toString();
	}
}
