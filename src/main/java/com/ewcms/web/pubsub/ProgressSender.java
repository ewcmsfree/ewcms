/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.web.pubsub;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ewcms.publication.WebPublishFacable;
import com.ewcms.publication.task.Taskable;

public class ProgressSender extends PubsubSender{

    private WebPublishFacable publishFac;
    private Integer siteId;
    
    public ProgressSender(String path, ServletContext context) {
        super(path, context);
    }
    
    
    private WebPublishFacable getPublishFac(ServletContext context) {
        
        ApplicationContext applicationContext = 
            WebApplicationContextUtils.getWebApplicationContext(context);
        Assert.notNull(applicationContext, "Can not get spring's context");
        
        WebPublishFacable fac = 
            applicationContext.getBean("webPublishFac",WebPublishFacable.class);
        Assert.notNull(fac, "Can not get webPublishFacBean");
        
        return  fac;
    }
    
    private Integer getSiteId(String path){
        String[] s = StringUtils.split(path,"/");
        String value = s[s.length -1];
        try{
            return Integer.valueOf(value);
        }catch(Exception e){
            return Integer.MIN_VALUE;
        }
    }
    
    @Override
    protected void init(String path,ServletContext context){
            publishFac = getPublishFac(context);
            siteId = getSiteId(path);
    }

    @Override
    protected String constructOutput() {
        List<Taskable> tasks = publishFac.getSitePublishTasks(siteId);
      
        StringBuilder builder = new StringBuilder();
 
        builder.append(" <script type=\"text/javascript\">");
        builder.append("parent.progress(");
        AtomicInteger count = new AtomicInteger(0);
        builder.append("{\"rows\":[");
        for(Taskable task : tasks){
            constructTreeGridRows(builder,count,-1,task);
        }
        builder.append("]");
        builder.append(",\"total\":").append(count.get()).append("}");
        builder.append(");");
        builder.append("</script>");
        return builder.toString();
    }

    private void constructTreeGridRows(StringBuilder builder,AtomicInteger count,int partenId,Taskable task){
        if(task.getProgress() == -1){
            return ;
        }
        
        int id = count.incrementAndGet();
        builder.append("{");
        builder.append("\"id\":").append(id);
        builder.append(",\"taskId\":\"").append(task.getId()).append("\"");
        builder.append(",\"name\":\"").append(task.getDescription()).append("\"");
        builder.append(",\"progress\":").append(task.getProgress());
        if(partenId != -1){
            builder.append(",\"_parentId\":").append(partenId);
        }
        builder.append("}");
        
        for(Taskable child : task.getDependences()){
            builder.append(",");
            constructTreeGridRows(builder,count,id,child);
        }
    }
}
