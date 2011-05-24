package com.ewcms.generator.freemarker;

import java.io.IOException;

import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import com.ewcms.core.site.dao.TemplateDAO;
import com.ewcms.core.site.model.Template;

import freemarker.cache.TemplateLoader;


public class DatabaseTemplateLoader implements TemplateLoader{

    private static final Logger logger = LoggerFactory.getLogger(DatabaseTemplateLoader.class);
    
    private TemplateDAO templateDao;
    
    public DatabaseTemplateLoader(TemplateDAO templateDao){
        this.templateDao = templateDao;
    }
    
    @Override
    public Object findTemplateSource(String name) throws IOException {
        Assert.notNull(name,"template uniquepath is null");
        
        if(StringUtils.startsWith(name, "/")){
            name =StringUtils.substring(name, 1);
        }
        
        Template template = templateDao.getTemplateByPath(name);
        
        if(template == null){
            if(logger.isDebugEnabled()){
                logger.debug("template is null");
            }
            return null;
        }
               
        if(template.getTemplateEntity() == null){
            if(logger.isDebugEnabled()){
                logger.debug("template content is null");
            }
            return null;
        }
        
        byte[] bytes = template.getTemplateEntity().getTplEntity();
        Resource resource = new ByteArrayResource(bytes);
        
        return resource;
    }

    @Override
    public Reader getReader(Object templateSource, String encoding) throws IOException {
        Resource resource = (Resource)templateSource;
        try{
            return new InputStreamReader(resource.getInputStream(),encoding);
        }catch(IOException e){
            if (logger.isDebugEnabled()) {
                logger.debug("Could not find FreeMarker template: " + resource);
            }
            throw e;
        }
    }

    @Override
    public long getLastModified(Object templateSource) {
        return -1;
    }
    
    @Override
    public void closeTemplateSource(Object templateSource) throws IOException {
        //Do nothing
    }
}
