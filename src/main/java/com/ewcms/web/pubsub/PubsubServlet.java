/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.web.pubsub;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.CometEvent;
import org.apache.catalina.CometProcessor;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 该Servlet需要通过Tomcat Comet来实现推送机制，Tomcat 6.0以上版本支持。<br/>
 * 详细帮助<a href="http://tomcat.apache.org/tomcat-6.0-doc/aio.html">tomcat comet</a>。
 * 
 * @author wangwei
 */
public class PubsubServlet extends HttpServlet implements CometProcessor {

    private static final Logger logger = LoggerFactory.getLogger(PubsubServlet.class);
    private static final String INITIALDELAY_PARAM_NAME = "initialDelay";
    private static final String DELAY_PARAM_NAME = "delay";
    private static final String PUBSUB_CONTEXT = "pubsub";
    
    private final Map<String,String> pathSender = new HashMap<String,String>();
    private final Map<String,PubsubSenderable> senders = Collections.synchronizedMap(new HashMap<String,PubsubSenderable>());
    
    private long initialDelay = 5;
    private long delay = 15;
    
    private void initInitialDelay(){
        String value = this.getInitParameter(INITIALDELAY_PARAM_NAME);
        if(StringUtils.isNumeric(value)){
            initialDelay = Long.valueOf(value); 
        }
    }
    
    private void initDelay(){
        String value = this.getInitParameter(DELAY_PARAM_NAME);
        if(StringUtils.isNumeric(value)){
            delay =Long.valueOf(value); 
        }
    }

    private void initPathMapSender(){
        Enumeration<?> names = this.getInitParameterNames();
        for(;names.hasMoreElements();){
            String path = (String)names.nextElement();
            if(path.equals(INITIALDELAY_PARAM_NAME) || path.equals(DELAY_PARAM_NAME)){
                continue;
            }
            String className = this.getInitParameter(path);
            String pubSubPath = StringUtils.substringAfter(path, PUBSUB_CONTEXT);
            pathSender.put(pubSubPath, className);
        }
    }
    
    @Override
    public void init() throws ServletException {
        initInitialDelay();
        initDelay();
        initPathMapSender();
    }

    @Override
    public void destroy() {
    }

    /**
     * Comet event处理过程
     * 
     * 这部分代码主要参考<a href = "http://tomcat.apache.org/tomcat-6.0-doc/aio.html#Example_code">ChatServlet</a>实现。
     * 
     * @param event
     *            The Comet event that will be processed
     * @throws IOException
     * @throws ServletException
     */
    public void event(CometEvent event) throws IOException, ServletException {
        HttpServletRequest request = event.getHttpServletRequest();
        HttpServletResponse response = event.getHttpServletResponse();
        
        if (event.getEventType() == CometEvent.EventType.BEGIN) {
            logger.debug("Begin for session:{} ", request.getSession(true).getId());
            String fullContentType = "text/html;charset=UTF-8";
            response.setContentType(fullContentType);
            sayHello(response);
            addConnection(request.getPathInfo(),response);
        } else if (event.getEventType() == CometEvent.EventType.ERROR) {
            logger.debug("Error for session: {}", request.getSession(true).getId());
            removeConnection(response);
            event.close();
        } else if (event.getEventType() == CometEvent.EventType.END) {
            logger.debug("End for session:{} ", request.getSession(true).getId());
            removeConnection(response);
            event.close();
        } else if (event.getEventType() == CometEvent.EventType.READ) {
            InputStream is = request.getInputStream();
            byte[] buf = new byte[512];
            do {
                int n = is.read(buf); // can throw an IOException
                if (n > 0) {
                    logger.debug("Read {} bytes:{} for session:{}",
                            new Object[]{n ,new String(buf, 0, n),  request.getSession(true).getId()});
                } else if (n < 0) {
                    return;
                }
            } while (is.available() > 0);
        }
    }
    
    private void sayHello(HttpServletResponse connection)throws IOException{
        PrintWriter writer = connection.getWriter();
        writer.println("<html><head></head><body><h1>hello</h1></body></htm>");
        writer.flush();
    }
    
    private PubsubSenderable createPubsubSender(String path){
        String name = null;
        if(StringUtils.indexOf(path, '/') == -1){
            name = pathSender.get(path);
        }else{
            String root = StringUtils.substringBeforeLast(path, "/");
            name = pathSender.get(root);
            if(name == null){
                String middle = StringUtils.substringAfter(root, "/");
                name= pathSender.get(middle);
            }
        }
        if(StringUtils.isNotBlank(name)){
            try {
                Class<?> clazz= Class.forName(name);
                Class<?>[] argsClass = new Class<?>[]{String.class,ServletContext.class};
                Constructor<?> cons = clazz.getConstructor(argsClass);
                return (PubsubSenderable)cons.newInstance(new Object[]{path,this.getServletContext()});
            } catch (Exception e) {
                logger.error("PubsubSender create error:{}",e.toString());
            }
        }
        
        return new NoneSender(); 
    }
    
    private void addConnection(String path,HttpServletResponse connection){
        synchronized(senders){
            PubsubSenderable sender = senders.get(path);
            if(sender == null){
                sender = createPubsubSender(path);
                sender.setDelay(delay);
                sender.setInitialDelay(initialDelay);
                sender.start();
                senders.put(path, sender);
            }
            sender.addClient(connection);
        }
    }
    
    private void removeConnection(HttpServletResponse connection){
        synchronized(senders){
            for(PubsubSenderable sender : senders.values()){
                sender.removeClient(connection);
            }
        }
    }
}
