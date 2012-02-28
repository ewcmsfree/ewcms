/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.web.pubsub;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.InterruptedException;
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
           String out ="<html><head></head><body>" +  constructFirstOutput();
           render(out,connection);
           connections.notifyAll();
       }
    }
    
    /**
     * 第一次推送内容
     * 
     * @return
     */
    protected String constructFirstOutput(){
        return constructOutput();
    }
    
    @Override
    public void removeClient(HttpServletResponse connection){
        synchronized(connections){
            if(connections.contains(connection)){
                String out ="</body></html>";
                render(out,connection);
                connections.remove(connection);
            }
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
            executor.scheduleWithFixedDelay(new Runnable(){
                @Override
                public void run() {
                    synchronized (connections) {
                        try{
                            if(connections.isEmpty()){
                                connections.wait() ;
                            }    
                        }catch(InterruptedException e){
                            logger.error("Running wait error:{}",e);
                        }
                        
                        String out = constructOutput();
                        logger.debug("Output :{}",out);
                        for (HttpServletResponse connection : connections) {
                            render(out,connection);
                        }
                    }
                }
            },
            initialDelay ,
            delay ,
            TimeUnit.SECONDS);
            
            alreadyStart = Boolean.TRUE;
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
    
    /**
     * 构造输出内容
     * 
     * @return
     */
    protected abstract String constructOutput();
}
