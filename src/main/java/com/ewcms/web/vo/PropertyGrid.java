package com.ewcms.web.vo;

import java.io.Serializable;

/**
 * 
 * @author wu_zhijun
 *
 */
public class PropertyGrid implements Serializable {

	private static final long serialVersionUID = -5249060440741974236L;
	
	private String name;
    private String value;
    private String group;
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}

}
