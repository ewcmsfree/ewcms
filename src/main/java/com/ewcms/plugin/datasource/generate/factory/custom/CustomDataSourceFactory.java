/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.datasource.generate.factory.custom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.ewcms.plugin.BaseRuntimeException;
import com.ewcms.plugin.datasource.generate.factory.DataSourceFactoryable;
import com.ewcms.plugin.datasource.generate.service.EwcmsDataSourceServiceable;
import com.ewcms.plugin.datasource.generate.util.CustomDataSourceDefinition;
import com.ewcms.plugin.datasource.model.BaseDS;
import com.ewcms.plugin.datasource.model.CustomDS;

/**
 * Custom数据源工厂
 * 
 * @author 吴智俊
 */
@Service
public class CustomDataSourceFactory implements DataSourceFactoryable, ApplicationContextAware {

	private static final Logger logger = LoggerFactory.getLogger(CustomDataSourceFactory.class);
	
    public static final String PROPERTY_MAP = "propertyMap";
    ApplicationContext ctx;
    private List<CustomDataSourceDefinition> customDataSourceDefs = new ArrayList<CustomDataSourceDefinition>();

    /**
     * 构造函数
     */
    public CustomDataSourceFactory() {
        super();
    }

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        this.ctx = ctx;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public EwcmsDataSourceServiceable createService(BaseDS alqcDataSource) {
        if (!(alqcDataSource instanceof CustomDS)) {
        	logger.error("无效的Custom数据源");
            throw new BaseRuntimeException("无效的Custom数据源", new Object[]{alqcDataSource.getClass()});
        }
        CustomDS customDataSource = (CustomDS) alqcDataSource;

        // 获得服务类的名称，查询类，并创建一个实例
        String serviceClassName = customDataSource.getServiceClass();
        EwcmsDataSourceServiceable service;
        try {
            Class serviceClass = Class.forName(serviceClassName);
            service = (EwcmsDataSourceServiceable) serviceClass.newInstance();
        } catch (Exception e) {
        	logger.error("不能建立Custom数据源",e);
            BaseRuntimeException ex = new BaseRuntimeException("不能建立Custom数据源", e);
            ex.setArgs(new Object[]{serviceClassName});
            throw ex;
        }
        try {
            // 使用Spring反映射类
            BeanWrapperImpl bw = new BeanWrapperImpl(service);
            // 获得CustomDataSourceDefinition类
            CustomDataSourceDefinition def = getDefinitionByServiceClass(serviceClassName);
            // 使用"propertyMap"传递参数
            Map<String, Object> propMap = new HashMap<String, Object>();
            // 设置参数
            Iterator pdi = def.getPropertyDefinitions().iterator();
            while (pdi.hasNext()) {
                Map pd = (Map) pdi.next();
                String name = (String) pd.get(CustomDataSourceDefinition.PARAM_NAME);
                Object deflt = pd.get(CustomDataSourceDefinition.PARAM_DEFAULT);
                Object value = customDataSource.getPropertyMap().get(name);
                if (value == null && deflt != null) {
                    value = deflt;
                }
                // 如果为空写入参数值
                if (value != null) {
                    if (bw.isWritableProperty(name)) {
                        bw.setPropertyValue(name, value);
                    }
                    propMap.put(name, value);
                }
            }
            if (bw.isWritableProperty(PROPERTY_MAP)) {
                bw.setPropertyValue(PROPERTY_MAP, propMap);
            }

        } catch (Exception e) {
        	logger.error("设置Custom数据源属性",e);
            BaseRuntimeException ex = new BaseRuntimeException("设置Custom数据源属性", e);
            ex.setArgs(new Object[]{serviceClassName});
            throw ex;
        }

        return service;
    }

    public void addDefinition(CustomDataSourceDefinition def) {
        customDataSourceDefs.add(def);
    }

    public List<CustomDataSourceDefinition> getDefinitions() {
        return customDataSourceDefs;
    }

    /**
     * @param serviceClass
     * @return
     */
    public CustomDataSourceDefinition getDefinitionByServiceClass(String serviceClass) {
        Iterator<CustomDataSourceDefinition> cdsi = getDefinitions().iterator();
        while (cdsi.hasNext()) {
            CustomDataSourceDefinition cds = (CustomDataSourceDefinition) cdsi.next();
            if (cds.getServiceClassName().equals(serviceClass)) {
                return cds;
            }
        }
        return null;
    }

    /**
     * @param serviceClass
     * @return
     */
    public CustomDataSourceDefinition getDefinitionByName(String name) {
        Iterator<CustomDataSourceDefinition> cdsi = getDefinitions().iterator();
        while (cdsi.hasNext()) {
            CustomDataSourceDefinition cds = (CustomDataSourceDefinition) cdsi.next();
            if (cds.getName().equals(name)) {
                return cds;
            }
        }
        return null;
    }
}
