/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.crawler.web;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author wuzhijun
 *
 */
public class BlockTreeGridNode implements Serializable {

	private static final long serialVersionUID = -5454019132864181989L;

	private Long id;
	private String regex;
	private String state = "closed";
	private String iconCls = "icon-folder";
	private List<BlockTreeGridNode> children;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public List<BlockTreeGridNode> getChildren() {
		return children;
	}

	public void setChildren(List<BlockTreeGridNode> children) {
		this.children = children;
	}
}
