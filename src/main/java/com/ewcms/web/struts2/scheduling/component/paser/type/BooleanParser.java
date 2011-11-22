/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.web.struts2.scheduling.component.paser.type;

import com.ewcms.plugin.report.generate.vo.PageShowParam;

/**
 * 布尔选择项
 * 
 * @author 吴智俊
 */
public class BooleanParser implements TypeParserable {

	public String parser(PageShowParam param) {
		StringBuffer html = new StringBuffer();
		html.append("<script language='javascript' type='text/javascript'>\n");
		html.append("  function checkboxboolean(name) {\n");
		html.append("    try{\n");
		html.append("      var checkboxobj = document.getElementsByName(name);\n");
		html.append("      var checkbox_name = name.substring(0, name.length - 1);\n");
		html.append("      if (checkboxobj[0].checked == true) {\n");
		html.append("        document.getElementById(checkbox_name).value = 'true';\n");
		html.append("      } else {\n");
		html.append("        document.getElementById(checkbox_name).value = 'false';\n");
		html.append("      }\n");
		html.append("    }catch(err){\n");
		html.append("      alert(err);\n");
		html.append("    }\n");
		html.append("  }\n");
		
		String defaultValue = param.getDefaultValue();
		if (defaultValue == null || defaultValue.length() == 0) {
			defaultValue = "false";
		}else{
			html.append("  function checkBoolSet(){\n");
			html.append("    try{\n");
			html.append("      var checkboxSet = document.getElementsByName('" + param.getEnName() + "');\n");
			html.append("      var checkboxSet_ = document.getElementsByName('" + param.getEnName() + "_');\n");
			html.append("      if (checkboxSet[0].value == \"" + defaultValue + "\"){\n");
			html.append("        checkboxSet_[0].checked = true;\n");
			html.append("      }else{\n");
			html.append("        checkboxSet_[0].checked = false;\n");
			html.append("      }\n");
			html.append("    }catch(err){\n");
			html.append("    }\n");
			html.append("  }\n");
		    html.append("if (window.attachEvent) {\n");
			html.append("  window.attachEvent('onload', checkBoolSet);\n");
			html.append("}else {\n");
			html.append("  window.addEventListener('load', checkBoolSet, false);\n");
			html.append("}\n");
		}
		html.append("</script>\n");

		html.append("<input type='checkbox' id='" + param.getEnName() + "_' name='"
				+ param.getEnName() + "_' onclick=\"checkboxboolean('" + param.getEnName() + "_');\"/>\n");
		html.append("<input type='hidden' id='" + param.getEnName() + "' name='"
				+ param.getEnName() + "' value=\"" + defaultValue + "\"/>\n");
		return html.toString();
	}
}
