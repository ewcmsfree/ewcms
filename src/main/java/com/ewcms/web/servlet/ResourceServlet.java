/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.web.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.web.util.EwcmsContextUtil;

/**
 * 读取站点资源 <br>
 * 文件上传被保持到本地并未发布，预览文章和模板需要使用本地资源。
 * 
 * @author 吴智俊
 */
public class ResourceServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(ResourceServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
        
        String contextPath = req.getContextPath();
        String uri = req.getRequestURI();
        uri = uri.replace(contextPath, "");

        String root = EwcmsContextUtil.getCurrentSite().getResourceDir();
        String path = "/" + StringUtils.join(StringUtils.split(root + uri, "/"), "/");
        
        logger.debug("Resource's path is {}",path);
        
        if (!(new File(path).exists())) {
            logger.error("Path's \"{}\" file is not exits", path);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        ServletContext context = getServletContext();
        String mimeType = context.getMimeType(path);
        resp.setContentType(mimeType);
        InputStream in = new FileInputStream(path);
        OutputStream out = resp.getOutputStream();
        IOUtils.copy(new FileInputStream(path), resp.getOutputStream());
        out.flush();

        in.close();
        out.close();
    }
}
