/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.datasource.generate.factory.bean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.ewcms.plugin.BaseRuntimeException;
import com.ewcms.plugin.datasource.generate.factory.DataSourceFactoryable;
import com.ewcms.plugin.datasource.generate.service.EwcmsDataSourceServiceable;
import com.ewcms.plugin.datasource.model.BaseDS;
import com.ewcms.plugin.datasource.model.BeanDS;

/**
 * Bean数据源工厂
 * 
 * @author 吴智俊
 */
@Service
public class BeanDataSourceFactory implements DataSourceFactoryable, ApplicationContextAware {

	private static final Logger logger = LoggerFactory.getLogger(BeanDataSourceFactory.class);
	
    ApplicationContext ctx;

    public BeanDataSourceFactory() {
        super();
    }

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        this.ctx = ctx;
    }

    @Override
    public EwcmsDataSourceServiceable createService(BaseDS alqcDataSource) {
        if (!(alqcDataSource instanceof BeanDS)) {
        	logger.error("无效的Bean数据库");
            throw new BaseRuntimeException("无效的Bean数据源", new Object[]{alqcDataSource.getClass()});
        }
        BeanDS beanDataSource = (BeanDS) alqcDataSource;

        Object bean = ctx.getBean(beanDataSource.getBeanName());

        if (bean == null) {
        	logger.error("Bean数据源不存在");
            throw new BaseRuntimeException("Bean数据源不存在", new Object[]{beanDataSource.getBeanName()});
        }

        if (beanDataSource.getBeanMethod() == null) {
            if (!(bean instanceof EwcmsDataSourceServiceable)) {
            	logger.error("Bean数据源不属于EwcmsDataSourceServiceable");
                throw new BaseRuntimeException("Bean数据源不属于EwcmsDataSourceServiceable", new Object[]{beanDataSource.getBeanName()});
            } else {
                return (EwcmsDataSourceServiceable) bean;
            }
        } else {
            Method serviceMethod;
            try {
                // serviceMethod = bean.getClass().getMethod(beanDataSource.getBeanMethod(),null);
                // return (ReportDataSourceServiceable)serviceMethod.invoke(bean,null);
                serviceMethod = bean.getClass().getMethod(beanDataSource.getBeanMethod(), new Class[0]);
                return (EwcmsDataSourceServiceable) serviceMethod.invoke(bean, new Object[0]);
            } catch (SecurityException e) {
            	logger.error("SecurityException", e);
                throw new BaseRuntimeException(e);
            } catch (NoSuchMethodException e) {
            	logger.error("bean数据源没有方法",e);
                throw new BaseRuntimeException("bean数据源没有方法", new Object[]{beanDataSource.getBeanName(), beanDataSource.getBeanMethod()});
            } catch (IllegalArgumentException e) {
            	logger.error("IllegalArgumentException",e);
                throw new BaseRuntimeException(e);
            } catch (IllegalAccessException e) {
            	logger.error("IllegalAccessException", e);
                throw new BaseRuntimeException(e);
            } catch (InvocationTargetException e) {
            	logger.error("InvocationTargetException",e.getMessage());
                throw new BaseRuntimeException(e);
            }
        }
    }
}
