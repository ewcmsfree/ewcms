/**
 * 
 */
package com.ewcms.util;

import java.io.Serializable;

/**
 * @author 周冬初
 *
 */
public class EnumValue implements Serializable {
	private String id;
	private String text;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
}
