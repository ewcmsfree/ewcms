/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.freemarker;

import java.io.IOException;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

/**
 * 提供FreeMarker配置对象
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
