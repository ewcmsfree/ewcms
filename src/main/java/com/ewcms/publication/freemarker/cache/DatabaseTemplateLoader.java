/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.freemarker.cache;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.ewcms.core.site.model.Template;
import com.ewcms.core.site.model.TemplateEntity;
import com.ewcms.publication.service.TemplatePublishServiceable;

import freemarker.cache.TemplateLoader;

/**
 * 加载数据库中的模板
 * 
 * @author wangwei
 */
public class DatabaseTemplateLoader implements TemplateLoader{
    
    private static final Logger logger = LoggerFactory.getLogger(DatabaseTemplateLoader.class);
    
    private TemplatePublishServiceable templateService;
    
    public DatabaseTemplateLoader(TemplatePublishServiceable service){
        templateService = service;
    }
    
    @Override
    public Object findTemplateSource(String name) throws IOException {
        Assert.notNull(name,"template uniquepath is null");
        
        String path = StringUtils.removeStart(name, "/");
        Template template = templateService.getTemplateByUniquePath(path);
        
        if(template == null){
            logger.debug("{} is not exist.",path);
            return null;
        }
        
        TemplateEntity entity = template.getTemplateEntity();
        if(entity == null){
            logger.debug("template content is null");
            throw new FileNotFoundException(path + " content is null.");
        }
        
        byte[] content = entity.getTplEntity();
        long lastTime = template.getUpdTime().getTime();
        
        return new TemplateSource(path,content,lastTime);
    }

    @Override
    public Reader getReader(Object templateSource, String encoding) throws IOException {
        TemplateSource source = (TemplateSource)templateSource;
        try{
            return new InputStreamReader(new ByteArrayInputStream(source.source),encoding);
        }catch(IOException e){
            logger.debug("Could not find FreeMarker template:{} " + source.name);
            throw e;
        }
    }

    @Override
    public long getLastModified(Object templateSource) {
        return  ((TemplateSource)templateSource).lastModified;
    }
    
    @Override
    public void closeTemplateSource(Object templateSource) throws IOException {
        //Do nothing
    }
    
    private static class TemplateSource {
        private final String name;
        private final byte[] source;
        private final long lastModified;
        
        TemplateSource(String name, byte[] source, long lastModified) {
            if(name == null) {
                throw new IllegalArgumentException("name == null");
            }
            if(source == null) {
                throw new IllegalArgumentException("source == null");
            }
            if(lastModified < -1L) {
                throw new IllegalArgumentException("lastModified < -1L");
            }
            this.name = name;
            this.source = source;
            this.lastModified = lastModified;
        }
        
        @Override
        public boolean equals(Object obj) {
            if(obj instanceof TemplateSource) {
                return name.equals(((TemplateSource)obj).name);
            }
            return false;
        }
        
        @Override
        public int hashCode() {
            return name.hashCode();
        }
    }
}
