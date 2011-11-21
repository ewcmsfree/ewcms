/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.datasource.generate.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ewcms.plugin.datasource.generate.factory.custom.CustomDataSourceFactory;
import com.ewcms.plugin.datasource.model.CustomDS;

//import net.sf.jasperreports.engine.util.JRProperties;

public class CustomDataSourceDefinition implements Serializable {
	
	private static final long serialVersionUID = -8676924133836692130L;
	
	public static final String PARAM_NAME = "name";
	public static final String PARAM_LABEL = "label";
	public static final String PARAM_DEFAULT = "default";
	// 这个属性不能修改,必须使用这个默认值.
	public static final String PARAM_HIDDEN = "hidden";

	private transient CustomDataSourceFactory factory;
	private String name;
	private String serviceClassName;
	private CustomDataSourceValidatorable validator;
	@SuppressWarnings({ "rawtypes" })
	private List propertyDefinitions;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param factory
	 */
	public void setFactory(CustomDataSourceFactory factory) {
		this.factory = factory;
		factory.addDefinition(this);
	}

	public CustomDataSourceFactory getFactory() {
		return factory;
	}

	/**
	 * 
	 * @param serviceClassName
	 */
	public void setServiceClassName(String serviceClassName) {
		this.serviceClassName = serviceClassName;
	}

	public String getServiceClassName() {
		return serviceClassName;
	}

	/**
	 * 
	 * @param validatorClassName
	 */
	public void setValidator(CustomDataSourceValidatorable validator) {
		this.validator = validator;
	}

	public CustomDataSourceValidatorable getValidator() {
		return validator;
	}

	/**
	 * a list of parameter defs. Each param is a map of props. Here are current
	 * valid props: name: name used to refer to the prop--in particular, used as
	 * the key in the persisted custom data source prop map label (auto
	 * generated): name of a label for this parameter to get out of a message
	 * catalog type: mandatory:
	 * 
	 * @param paramDefs
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setPropertyDefinitions(List propertyDefinitions) {
		this.propertyDefinitions = new ArrayList();
		// auto generate labels
		Iterator pdi = propertyDefinitions.iterator();
		while (pdi.hasNext()) {
			Map pd = (Map) pdi.next();
			Map newPd = new HashMap(pd);
			// create label name
			newPd.put(PARAM_LABEL, getParameterLabelName((String) newPd.get(PARAM_NAME)));
			this.propertyDefinitions.add(newPd);
		}
	}

	@SuppressWarnings({ "rawtypes" })
	public List getPropertyDefinitions() {
		return propertyDefinitions;
	}

	/**
	 * message name used as label
	 */
	public String getLabelName() {
		return name + ".name";
	}

	/**
	 * message names for params
	 * 
	 * @param paramName
	 * @return
	 */
	public String getParameterLabelName(String paramName) {
		return name + ".properties." + paramName;
	}

	/**
	 * utility function for the jsp--return just the editable param defs
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getEditablePropertyDefinitions() {
		List list = new ArrayList(propertyDefinitions);
		Iterator pdi = list.iterator();
		while (pdi.hasNext()) {
			Map pd = (Map) pdi.next();
			// if hidden, delete from list
			String hidden = (String) pd.get(CustomDataSourceDefinition.PARAM_HIDDEN);
			if (Boolean.parseBoolean(hidden)) {
				pdi.remove();
			}
		}
		return list;
	}

	/**
	 * Initialize a CustomReportDataSource instance with the defaults specified
	 * 
	 * @param cds
	 * @param b
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setDefaultValues(CustomDS cds,
			boolean includeHidden) {
		if (cds.getPropertyMap() == null) {
			cds.setPropertyMap(new HashMap());
		}
		Iterator pdi = propertyDefinitions.iterator();
		while (pdi.hasNext()) {
			Map pd = (Map) pdi.next();
			String paramName = (String) pd.get(CustomDataSourceDefinition.PARAM_NAME);
			Object def = pd.get(CustomDataSourceDefinition.PARAM_DEFAULT);
			String hidden = (String) pd.get(CustomDataSourceDefinition.PARAM_HIDDEN);
			if (Boolean.parseBoolean(hidden) && !includeHidden) {
				continue;
			}
			String value = (String) cds.getPropertyMap().get(paramName);
			if (value == null) {
				if (def == null) {
					def = "";
				}
				cds.getPropertyMap().put(paramName, def);
			}
		}
	}
}
