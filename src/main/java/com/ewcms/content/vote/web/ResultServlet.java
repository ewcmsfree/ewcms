/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.vote.web;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ewcms.content.vote.VoteFacable;

/**
 * 查看调查投票结果
 * 
 * @author 吴智俊
 * 
 */
public class ResultServlet extends HttpServlet {

	private static final long serialVersionUID = 3134667138637919147L;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletOutputStream out = null;
		StringBuffer output = new StringBuffer();
		try{
			String id = req.getParameter("id");
			
			if (!id.equals("") && StringUtils.isNumeric(id)){
				Long questionnaireId = new Long(id);
				
		    	ServletContext application = getServletContext(); 
		    	WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(application);
		    	VoteFacable voteFac = (VoteFacable) wac.getBean("voteFac");
				
		    	String ipAddr = req.getRemoteAddr();
		    	
		    	output = voteFac.getQuestionnaireResultClientToHtml(questionnaireId, getServletContext().getContextPath(), ipAddr);
			}
			out = resp.getOutputStream();
	    	resp.setCharacterEncoding("UTF-8");
	    	resp.setContentType("text/html; charset=UTF-8");
	    	out.write(output.toString().getBytes());
	    	out.flush();
		}finally{
    		if (out != null){
    			out.close();
    			out = null;
    		}
		}
	}
}
