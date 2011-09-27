/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.freemarker;

import java.io.IOException;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

/**
 * 提供FreeMarker配置对象
 *
 * @author wangwei
 */
public class EwcmsConfigurationFactoryBean extends EwcmsConfigurationFactory implements FactoryBean<Configuration>, InitializingBean {

    private Configuration configuration;

    @Override
    public void afterPropertiesSet() throws IOException, TemplateException {
        Assert.notNull(channelService,"channelService must setting");
        Assert.notNull(articleService,"articleService must setting");
        Assert.notNull(templateService,"templateService must setting");
        
        this.configuration = createConfiguration();
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
