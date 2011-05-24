/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.generator.freemarker;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import java.io.IOException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author wangwei
 */
public class EwcmsConfigurationFactoryBean implements FactoryBean<Configuration>, InitializingBean {

    @Autowired
    private EwcmsConfigurationFactory factory;

    public void setFactory(EwcmsConfigurationFactory factory){
        this.factory = factory;
    }
    
    private Configuration configuration;

    @Override
    public void afterPropertiesSet() throws IOException, TemplateException {
        this.configuration = factory.getConfiguration();
    }

    @Override
    public Configuration getObject() {
        return this.configuration;
    }

    @Override
    public Class<? extends Configuration> getObjectType() {
        return Configuration.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
