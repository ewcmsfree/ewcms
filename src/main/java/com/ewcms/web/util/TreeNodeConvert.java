/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.web.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.security.acls.model.Permission;

import com.ewcms.core.site.ChannelNode;
import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Template;
import com.ewcms.core.site.model.TemplateSource;
import com.ewcms.web.vo.TreeNode;

/**
 * @author 周冬初
 * 
 */
public class TreeNodeConvert {
	private final static Pattern FILTERS_CSS = Pattern.compile(".*(\\.(css))$");
	private final static Pattern FILTERS_HTML = Pattern.compile(".*(\\.(html|htm))$");
	private final static Pattern FILTERS_JS = Pattern.compile(".*(\\.(js))$");
	private final static Pattern FILTERS_XML = Pattern.compile(".*(\\.(xml))$");
	private final static Pattern FILTERS_PICTURE = Pattern.compile(".*(\\.(bmp|gif|jpe?g|png|tiff?|psd))$");
	private final static Pattern FILTERS_VOIDE = Pattern.compile(".*(\\.(mid|mp2|mp3|mp4|wav|avi|mov|mpeg|ram|m4v|pdf|rm|smil|wmv|swf|wma))$");
	private final static Pattern FILTERS_WORD = Pattern.compile(".*(\\.(doc|doc?|dot|rtf|olk|scd|wri|wpd|wtf|wps))$");
	private final static Pattern FILTERS_EXCEL = Pattern.compile(".*(\\.(xl?|wk?))$");
	private final static Pattern FILTERS_POWERPOINT = Pattern.compile(".*(\\.(ppt?|pps?|pot?))$");
	
	public static List<TreeNode> channelNodeConvert(List<ChannelNode> cnList) {
		List<TreeNode> tnList = new ArrayList<TreeNode>();
		if (cnList == null)
			return tnList;
		for (ChannelNode vo : cnList) {
			TreeNode tnVo = new TreeNode();
			tnVo.setId(vo.getId().toString());
			tnVo.setText(vo.getName());
			if (vo.isChildren()) {
				tnVo.setState("closed");
			} else {
				tnVo.setState("open");
			}
			Map<String, String> attributes = new HashMap<String, String>();
			attributes.put("type", vo.getChannelType().name());
			attributes.put("typeDesc", vo.getChannelTypeDes());
			attributes.put("sort", vo.getSort());
			tnVo.setAttributes(attributes);
			Channel.Type type = vo.getChannelType();
			switch(type){
			case ARTICLE: tnVo.setIconCls("icon-channel-article");break;
			case LEADER: tnVo.setIconCls("icon-channel-leader");break;
			case ONLINE : tnVo.setIconCls("icon-channel-online");break;
			case LEADERARTICLE :tnVo.setIconCls("icon-channel-articlerefer");break;
			case RETRIEVAL : tnVo.setIconCls("icon-channel-retrieval");break;
			case PROJECT : tnVo.setIconCls("icon-channel-project");break;
			case PROJECTARTICLE : tnVo.setIconCls("icon-channel-projectarticle");break;
			case ENTERPRISE : tnVo.setIconCls("icon-channel-enterprise");break;
			case ENTERPRISEARTICLE : tnVo.setIconCls("icon-channel-enterprisearticle");break;
			case EMPLOYE : tnVo.setIconCls("icon-channel-employe");break;
			case EMPLOYEARTICLE : tnVo.setIconCls("icon-channel-employearticle");break;
			default : tnVo.setIconCls("icon-channel-note");
			}
			treeNodePermission(attributes, vo.getPermissions());
			tnList.add(tnVo);
		}
		return tnList;
	}

	public static int treeNodePermission(Map<String, String> attributes, Set<Permission> permissions) {
		String permission = "";
		int max = -1;
		if (permissions != null &&!permissions.isEmpty()) {
			for (Permission pm : permissions) {
				if (pm.getMask() > max)
					max = pm.getMask();
				permission += pm.getMask() + ",";
			}
			permission = permission.substring(0, permission.length() - 2);
		}

		attributes.put("permission", permission);
		attributes.put("maxpermission", String.valueOf(max));
		return max;
	}
	
	public static List<TreeNode> templateConvert(List<Template> tplList) {
		List<TreeNode> tnList = new ArrayList<TreeNode>();
		if (tplList == null)
			return tnList;
		for (Template vo : tplList) {
			TreeNode tnVo = new TreeNode();
			String name = vo.getName();
			tnVo.setIconCls("icon-channel-note");
			if (name != null && name.length() > 0){
				name = name.toLowerCase();
				if (FILTERS_CSS.matcher(name).matches()) tnVo.setIconCls("icon-resource-css");
				else if (FILTERS_JS.matcher(name).matches()) tnVo.setIconCls("icon-resource-js");
				else if (FILTERS_HTML.matcher(name).matches()) tnVo.setIconCls("icon-resource-html");
				else if (FILTERS_XML.matcher(name).matches()) tnVo.setIconCls("icon-resource-html");
				else if (FILTERS_PICTURE.matcher(name).matches()) tnVo.setIconCls("icon-resource-picture");
				else if (FILTERS_VOIDE.matcher(name).matches()) tnVo.setIconCls("icon-resource-voide");
				else if (FILTERS_WORD.matcher(name).matches()) tnVo.setIconCls("icon-resource-word");
				else if (FILTERS_EXCEL.matcher(name).matches()) tnVo.setIconCls("icon-resource-excel");
				else if (FILTERS_POWERPOINT.matcher(name).matches()) tnVo.setIconCls("icon-resource-powerpoint");
			}
			Map<String, String> attributes = new HashMap<String, String>();
			attributes.put("path", vo.getPath());
			tnVo.setAttributes(attributes);
			tnVo.setId(vo.getId().toString());
			tnVo.setText(vo.getName());
			if (vo.getTemplateEntity() == null) {
				if (vo.hasChildren()) {
					tnVo.setState("closed");
				} else {
					tnVo.setState("open");
				}
			} else {
				tnVo.setState("open");
			}
			tnList.add(tnVo);
		}
		return tnList;
	}

	public static List<TreeNode> templateSourceConvert(List<TemplateSource> srcList) {
		List<TreeNode> tnList = new ArrayList<TreeNode>();
		if (srcList == null)
			return tnList;
		for (TemplateSource vo : srcList) {
			TreeNode tnVo = new TreeNode();
			
			tnVo.setIconCls("icon-channel-note");
			String name = vo.getName();
			if (name != null && name.length() > 0){
				name = name.toLowerCase();
				if (FILTERS_CSS.matcher(name).matches()) tnVo.setIconCls("icon-resource-css");
				else if (FILTERS_JS.matcher(name).matches()) tnVo.setIconCls("icon-resource-js");
				else if (FILTERS_HTML.matcher(name).matches()) tnVo.setIconCls("icon-resource-html");
				else if (FILTERS_XML.matcher(name).matches()) tnVo.setIconCls("icon-resource-html");
				else if (FILTERS_PICTURE.matcher(name).matches()) tnVo.setIconCls("icon-resource-picture");
				else if (FILTERS_VOIDE.matcher(name).matches()) tnVo.setIconCls("icon-resource-voide");
				else if (FILTERS_WORD.matcher(name).matches()) tnVo.setIconCls("icon-resource-word");
				else if (FILTERS_EXCEL.matcher(name).matches()) tnVo.setIconCls("icon-resource-excel");
				else if (FILTERS_POWERPOINT.matcher(name).matches()) tnVo.setIconCls("icon-resource-powerpoint");
			}
			
			tnVo.setId(vo.getId().toString());
			tnVo.setText(vo.getName());
			if (vo.getSourceEntity() == null) {
				if (vo.hasChildren()) {
					tnVo.setState("closed");
				} else {
					tnVo.setState("open");
				}
			} else {
				tnVo.setState("open");
			}
			tnList.add(tnVo);
		}
		return tnList;
	}
}
