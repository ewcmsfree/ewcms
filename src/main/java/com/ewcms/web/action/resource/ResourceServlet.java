/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.web.action.resource;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.ewcms.util.EwcmsContextUtil;
import com.ewcms.util.GlobaPath;

/**
 *
 * @author 吴智俊
 */
public class ResourceServlet extends HttpServlet {

    private static final long serialVersionUID = -7652140025632831940L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedInputStream in = null;
        OutputStream out = null;
        try {
            String contextPath = req.getContextPath();
            String uri = req.getRequestURI();

            uri = uri.replace(contextPath, "");
            uri = uri.replace(GlobaPath.ResourceDir.getPath(), "");

            String root = EwcmsContextUtil.getCurrentSite().getResourceDir();
            String fileName = root + uri;

            ServletContext context = getServletContext();
            String mimeType = context.getMimeType(fileName);
            if (mimeType == null) {
                context.log("Could not get MIME type of " + fileName);
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }
            resp.setContentType(mimeType);
            in = new BufferedInputStream(new FileInputStream(fileName));
            out = resp.getOutputStream();
            byte[] bytes = new byte[1024 * 10];
            int len = 0;
            while ((len = in.read(bytes)) > 0) {
                out.write(bytes, 0, len);
            }
            out.flush();
        } catch (NullPointerException e) {
        } catch (FileNotFoundException e) {
        } finally {
            if (out != null) {
                out.close();
                out = null;
            }
            if (in != null) {
                in.close();
                in = null;
            }
        }
    }
}
