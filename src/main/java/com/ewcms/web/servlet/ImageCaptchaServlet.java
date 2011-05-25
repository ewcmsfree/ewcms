/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.web.servlet;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.CaptchaService;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author wangwei
 */
public class ImageCaptchaServlet extends HttpServlet {

    private static final String IMG_JPEG_TYPE = "image/jpeg";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            String captchaId = request.getSession().getId();
            createCheckCodeImage(captchaId, request.getLocale(), jpegOutputStream);
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        } catch (CaptchaServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        initResponseHeader(response);
        responseImage(response, jpegOutputStream.toByteArray());
        jpegOutputStream.close();
    }

    private void createCheckCodeImage(final String id, final Locale locale, final OutputStream stream) throws CaptchaServiceException, IOException {
        CaptchaService service = getCaptchaService();
        BufferedImage challenge = (BufferedImage) service.getChallengeForID(id, locale);
        ImageIO.write(challenge, "jpg", stream);
    }

    private void initResponseHeader(final HttpServletResponse response) {
        response.setHeader("Content-Language", "zh-CN");
        response.setHeader("Cache-Control", "no-store");
        //set on cache
        ///Http 1.0 header
        response.setDateHeader("Expires", 0);
        response.addHeader("Pragma", "no-cache");
        //Http 1.1 header
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType(IMG_JPEG_TYPE);
    }

    private void responseImage(HttpServletResponse response, byte[] imageBytes) throws IOException {
        OutputStream stream = response.getOutputStream();
        stream.write(imageBytes);
        stream.flush();
        stream.close();
    }

    protected CaptchaService getCaptchaService() {
        ServletContext application = getServletContext();
        WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(application);
        return (CaptchaService) wac.getBean("captchaService");
    }
}
