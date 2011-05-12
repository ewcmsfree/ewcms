/**
 * 
 */
package com.ewcms.util;

import java.io.Serializable;

/**
 * @author 周冬初
 *
 */
public class FileInfo implements Serializable {
	private String name;
	private String length;
	private String path;
	private String modifyTime;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	} 	
}
