/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.web.struts2.scheduling.component.paser.type;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.ewcms.plugin.report.generate.vo.PageShowParam;

/**
 * Session输入框，并且不能修改，只能查看
 * 
 * @author 吴智俊
 */
public class SessionParser implements TypeParserable {

	public String parser(PageShowParam param) {
		String html = "";
		try{
			Map<String, String> map = param.getValue();
			String sessionName = map.get("0");
			String value = "";
			if (sessionName != null && sessionName.length() > 0) {
				if (!sessionName.equals("SPRING_SECURITY_CONTEXT")){
					HttpSession hs = ServletActionContext.getRequest().getSession();
					value = (String) hs.getAttribute(sessionName);
					if (value == null || value.length() == 0) {
						value = "";
					}
				}else{
					value = getUsername();
				}
			}
			html = "<input type='text' id='" + param.getEnName() + "' name='"
				+ param.getEnName() + "' value='" + value + "' readonly='true'/>";
		}catch(Exception e){
			System.out.println(e.toString());
		}
		return html;
	}
	
	public static String getUsername(){
		
		SecurityContext context = SecurityContextHolder.getContext();
		Object principal = context.getAuthentication().getPrincipal();
		
		if(principal == null){
			return "";
		}
		
		if(principal instanceof UserDetails){ 
			return ((UserDetails)principal).getUsername(); 
		}
		
		return "";
	}

}
