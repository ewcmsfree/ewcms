/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.html;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;

import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.core.site.model.Template;
import com.ewcms.generator.freemarker.FreemarkerTest;
import com.ewcms.generator.output.OutputResource;

/**
 * 页面生成单元测试类
 * <br>
 * 提供页面生成单元测试基础方法类
 * 
 * @author wangwei
 */
public abstract class GeneratorTest extends FreemarkerTest {
    
    protected String getContent(OutputResource resource)throws Exception{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        resource.write(out);
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        BufferedReader reader = new BufferedReader(new InputStreamReader(in,"utf-8"));
        StringBuilder builder = new StringBuilder();
        String line;
        while((line = reader.readLine())!= null){
            builder.append(line);
        }
        reader.close();
        in.close();
        out.close();
        return builder.toString();
    }
    
    protected Site initSite(){
        Site  site = new Site();
        return site;
    }
    
    protected Channel initChannel(){
        Channel channel = new Channel();
        
        channel.setId(1);
        channel.setListSize(10);
        channel.setAbsUrl("/news/cn");
        
        return channel;
    }
    
    protected String getTemplatePath(String name) {
        return String.format("html/%s", name);
    }
    
    protected Template initTemplate(String path){
        Template template = new Template();
        template.setUniquePath(path);
        return template;
    }
}
