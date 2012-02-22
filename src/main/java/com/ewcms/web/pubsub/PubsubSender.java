/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.web.pubsub;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 推送信息到客户端
 * 
 * @author wangwei
 */
public abstract class PubsubSender  implements PubsubSenderable{

    private static final Logger logger = LoggerFactory.getLogger(PubsubSender.class);
    
    protected final String path;
    protected final ServletContext context;
    private final List<HttpServletResponse> connections =Collections.synchronizedList(new ArrayList<HttpServletResponse>());
    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    
    private Boolean alreadyStart = Boolean.FALSE;
    private long initialDelay = 5;
    private long delay = 15;

    public PubsubSender(String path,ServletContext context) {
        this.path = path;
        this.context = context;
        init(path,context);
    }

    protected abstract void init(String path,ServletContext context);
    
    @Override
    public void addClient(HttpServletResponse connection){
       synchronized(connections){
           connections.add(connection);
       }
    }
    
    @Override
    public void removeClient(HttpServletResponse connection){
        synchronized(connections){
            connections.remove(connection);
        }
    }

    @Override
    public void setInitialDelay(long initialDelay) {
        this.initialDelay = initialDelay;
        
    }

    @Override
    public void setDelay(long delay) {
        this.delay = delay;
    }
    
    @Override
    public void start() {
        synchronized(alreadyStart){
            if(alreadyStart){
                return;
            }
            executor.scheduleWithFixedDelay(new SendRunner(), initialDelay , delay , TimeUnit.SECONDS);
            alreadyStart = Boolean.TRUE;
        }
    }
    
    /**
     * 构造输出Javascritp
     * 
     * @return
     */
    protected abstract String constructJS();
    
    private class SendRunner implements Runnable{
        
        @Override
        public void run() {
            
            synchronized (connections) {
                if(connections.isEmpty()){
                    return ;
                }
            }
            
            synchronized (connections) {
                String js = constructJS();
                logger.debug("Output JS:{}",js);
                for (HttpServletResponse response : connections) {
                    render(js,response);
                }
            }
        }
        
        private void render(String m,HttpServletResponse response){
            try {
                PrintWriter writer = response.getWriter();
                writer.println(m);
                writer.flush();
            } catch (IOException e) {
                logger.error(e.toString());
            }
        }
    }

   
}
