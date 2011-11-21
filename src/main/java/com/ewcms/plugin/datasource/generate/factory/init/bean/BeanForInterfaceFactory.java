/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.datasource.generate.factory.init.bean;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.ewcms.plugin.BaseRuntimeException;
import com.ewcms.plugin.BaseRuntimeExceptionWrapper;

/**
 * <ul>根据的数据源映射对象,查找相对应的数据源工厂
 * <p>通过在org.jict.alqc.datasource.init.AlqcDataSourceFactory类中定义的serviceDefinitionMap变量进行查找;
 * 而此类(AlqcDataSourceFactory)为服务器启动时自动加载.
 * </ul>
 * @author 吴智俊
 */
@Service
public class BeanForInterfaceFactory implements BeanForInterfaceFactoryable, ApplicationContextAware {

	private static final Logger logger = LoggerFactory.getLogger(BeanForInterfaceFactory.class);
	
    private ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext ctx) {
        this.ctx = ctx;
    }
    @SuppressWarnings("rawtypes")
    private final Comparator itfComparator = new Comparator() {

        @SuppressWarnings("unchecked")
		@Override
        public int compare(Object o1, Object o2) {
            Class itf1 = (Class) o1;
            Class itf2 = (Class) o2;

            if (itf1.equals(itf2)) {
                return 0;
            } else if (itf2.isAssignableFrom(itf1)) {
                return -1;
            } else if (itf1.isAssignableFrom(itf2)) {
                return 1;
            } else {
                return itf1.getName().compareTo(itf2.getName());
            }
        }
    };

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public String getBeanName(Map interfaceToBeanMappings, Class itfClass) {
        if (interfaceToBeanMappings == null) {
            return null;
        }

        try {
            SortedSet interfaces = new TreeSet(itfComparator);

            for (Iterator it = interfaceToBeanMappings.keySet().iterator(); it.hasNext();) {
                String itfName = (String) it.next();
                Class itf = Class.forName(itfName, true, Thread.currentThread().getContextClassLoader());
                if (itf.isAssignableFrom(itfClass)) {
                    interfaces.add(itf);
                }
            }

            if (!interfaces.isEmpty()) {
                Class itf = (Class) interfaces.iterator().next();
                return (String) interfaceToBeanMappings.get(itf.getName());
            }
            return null;
        } catch (ClassNotFoundException e) {
            logger.error("ClassNotFoundException",e);
            throw new BaseRuntimeExceptionWrapper(e);
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Object getBean(Map classToBeanMappings, Class _class) {
        String beanName = getBeanName(classToBeanMappings, _class);

        if (beanName == null) {
        	logger.error("名称未找到接口");
            throw new BaseRuntimeException("名称未找到接口", new Object[]{_class.getName()});
        }

        Object bean = ctx.getBean(beanName);

        if (bean == null) {
        	logger.error("Bean没有名字");
            throw new BaseRuntimeException("Bean没有名字", new Object[]{beanName});
        }

        return bean;
    }
}
