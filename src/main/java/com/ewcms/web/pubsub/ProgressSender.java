/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.web.pubsub;

import java.util.List;

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
        return StringUtils.isNumeric(value) ? 
                Integer.valueOf(value) : Integer.MIN_VALUE;
    }
    
    @Override
    protected void init(String path,ServletContext context){
            publishFac = getPublishFac(context);
            siteId = getSiteId(path);
    }

    @Override
    protected String constructJS() {
        List<Taskable> tasks = publishFac.getSitePublishTasks(siteId);
        if(tasks.isEmpty()){
            
        }
        StringBuilder builder = new StringBuilder();
        builder.append("<html>");
        builder.append(" <head>");
        builder.append(" <script type=\"text/javascript\">");
        builder.append("parent.progress(\"").append("test").append("\")");
        builder.append("</script>");
        builder.append("</head>");
        builder.append("<body></body>");
        builder.append("</html>");
        return builder.toString();
    }

}
