/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ewcms.generator.freemarker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;

import com.ewcms.core.site.dao.TemplateDAO;

import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;

/**
 *
 * @author wangwei
 */
public class EwcmsConfigurationFactory extends FreeMarkerConfigurationFactory implements ResourceLoaderAware  {

    private Configuration config;
    
    private List<TemplateLoader> postTemplateLoaders;
    
    @Autowired
    private TemplateDAO templateDao;
    
    @Override
    public Configuration createConfiguration()throws IOException,TemplateException{
        
        if(postTemplateLoaders == null){
            postTemplateLoaders = new ArrayList<TemplateLoader>();
        }
        
        postTemplateLoaders.add(new DatabaseTemplateLoader(templateDao));
        super.setPostTemplateLoaders(postTemplateLoaders.toArray(new TemplateLoader[postTemplateLoaders.size()]));
        
        return super.createConfiguration();
    }
    
    public Configuration getConfiguration() throws IOException, TemplateException{
        config = (config == null ? this.createConfiguration() : config);
        return config;
    }

    @Override
    public void setPostTemplateLoaders(TemplateLoader[] postTemplateLoaders) {
        this.postTemplateLoaders = Arrays.asList(postTemplateLoaders);
    }
    
    public void setTemplateDao(TemplateDAO templateDao){
        this.templateDao = templateDao;
    }
}
