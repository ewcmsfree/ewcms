/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.web.struts2.scheduling.component.paser.type;

import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Map.Entry;

import com.ewcms.plugin.report.generate.vo.PageShowParam;

/**
 * 多选框
 * 
 * @author 吴智俊
 */
public class CheckBoxParser implements TypeParserable {

	public String parser(PageShowParam param) {
		StringBuffer html = new StringBuffer();
		
		html.append("<script language='javascript' type='text/javascript'>\n");
		html.append("  function checkboxvalue(name) {\n");
		html.append("    try{\n");
		html.append("      var form_attachments = '';\n");
		html.append("      var list = document.getElementsByName(name);\n");
		html.append("      for (var i = 0; i < list.length; i++) {\n");
		html.append("        if (list[i].type == 'checkbox') {\n");
		html.append("          if (list[i].checked == true) {\n");
		html.append("            var listValue = list[i].value;\n");
		html.append("            if (isNumber(listValue)) {\n");
		html.append("              form_attachments += listValue;\n");
		html.append("            } else {\n");
		html.append("              form_attachments += \"'\" + listValue + \"'\";\n");
		html.append("            }\n");
		html.append("            form_attachments += ',';\n");
		html.append("          }\n");
		html.append("        }\n");
		html.append("      }\n");
		html.append("      var sequnce_value = form_attachments.substring(0, form_attachments.length - 1);\n");
		html.append("      var checkbox_name = name.substring(0, name.length - 1);\n");
		html.append("      document.getElementById(checkbox_name).value = sequnce_value;\n");
		html.append("    }catch(err){\n");
		html.append("    }\n");
		html.append("  }\n");
		html.append("  function isNumber(oNum) {\n");
		html.append("    var r, re;\n");
	    html.append("    re = /\\d*/i;\n");
	    html.append("    r = oNum.match(re);\n");
	    html.append("    return (r == oNum) ? true : false;\n");
	    html.append("  }\n");
	    String defaultValue = param.getDefaultValue();
	    String enName = param.getEnName();
	    if (defaultValue != null && defaultValue.length() > 0){
		    html.append("  function checkSet(){\n");
		    html.append("    try{\n");
	    	StringTokenizer tokenizer = new StringTokenizer(defaultValue,",",false);
	    	html.append("      var checkboxSet = document.getElementsByName('" + enName + "_');\n");
	    	html.append("      for (var i = 0; i < checkboxSet.length; i++){\n");
	    	while (tokenizer.hasMoreElements()){
	    		String checkValue = (String)tokenizer.nextToken();
	    		html.append("        if (checkboxSet[i].value == " + checkValue + "){\n");
	    		html.append("          checkboxSet[i].checked = true;\n");
	    		html.append("        }\n");
	    	}
	    	html.append("      }\n");
		    html.append("    }catch(err){\n");
		    html.append("    }\n");
		    html.append("  }\n");
		    html.append("if (window.attachEvent) {\n");
			html.append("  window.attachEvent('onload', checkSet);\n");
			html.append("}else {\n");
			html.append("  window.addEventListener('load', checkSet, false);\n");
			html.append("}\n");
	    }else{
	    	defaultValue = "";
	    }
	    
		html.append("</script>\n");
		
		html.append("<table>\n");
		Map<String, String> valueMap = param.getValue();
		int iCount = 0;
		if (!valueMap.isEmpty()) {
			Iterator<Entry<String, String>> it = valueMap.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, String> entry = (Map.Entry<String, String>) it
						.next();
				String value = entry.getValue();
				if (value == null || value.length() == 0) {
					value = "";
				}

				if (iCount % 2 == 0) {
					html.append("<tr>\n");
				}

				html.append("<td><input type='checkbox' id='"
						+ enName + "_' name='" + enName
						+ "_' value='" + value + "' onclick=\"checkboxvalue('"
						+ param.getEnName() + "_');\">" + value
						+ "</input></td>\n");

				iCount++;
				if (iCount % 2 == 0) {
					html.append("</tr>\n");
				}
			}
		}
		html.append("<input type='hidden' id='" + enName + "' name='"
				+ enName + "' value=\"" + defaultValue + "\"/>\n");
		html.append("</table>\n");
		return html.toString();
	}
}
