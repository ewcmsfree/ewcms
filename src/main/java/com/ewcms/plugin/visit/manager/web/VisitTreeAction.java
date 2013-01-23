/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.visit.manager.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;
import com.ewcms.web.vo.TreeNode;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author wu_zhijun
 *
 */
public class VisitTreeAction extends ActionSupport {

	private static final long serialVersionUID = 146162923400817102L;

	private static final String ICON_CLS_VISIT = "icon-visit-analysis";
	private static final String ICON_CLS_NOTE = "icon-channel-note";
	private Integer id = 0;
	private static Map<String, String> oneTreeMap = new TreeMap<String, String>();
	private static Map<String, String> twoTreeMap = new TreeMap<String, String>();
	private static Map<String, String> threeTreeMap = new TreeMap<String, String>();
	private static Map<String, String> fourTreeMap = new TreeMap<String, String>();
	private static Map<String, String> fiveTreeMap = new TreeMap<String, String>();
	private static Map<String, String> sixTreeMap = new TreeMap<String, String>();
	private static Map<String, String> sevenTreeMap = new TreeMap<String, String>();
	
	static{
		oneTreeMap.put("0综合报告", "summary");
		oneTreeMap.put("1全站点击率", "site");
		oneTreeMap.put("2访问记录", "lastVisit");
		oneTreeMap.put("3时段分布", "hour");
		oneTreeMap.put("4入口分析", "entrance");
		oneTreeMap.put("5出口分析", "exit");
		oneTreeMap.put("6被访主机分析", "host");
		oneTreeMap.put("7区域分布", "country");
		oneTreeMap.put("8在线情况", "online");
		
		twoTreeMap.put("0栏目点击排行", "channel");
		twoTreeMap.put("1文章点击排行", "article");
		twoTreeMap.put("5URL点击排行", "url");
		
		threeTreeMap.put("0访问深度", "depth");
		threeTreeMap.put("1访问频率", "frequency");
		threeTreeMap.put("2回头率", "visitor");
		threeTreeMap.put("3停留时间", "stickTime");
		
		fourTreeMap.put("0来源组成", "source");
		fourTreeMap.put("1搜索引擎", "search");
		fourTreeMap.put("2来源网站", "webSite");
		
		fiveTreeMap.put("0操作系统", "os");
		fiveTreeMap.put("1浏览器", "browser");
		fiveTreeMap.put("2语言", "language");
		fiveTreeMap.put("3屏幕分辨率", "screen");
		fiveTreeMap.put("4屏幕色深", "colorDepth");
		fiveTreeMap.put("5是否支持Apple", "javaEnabled");
		fiveTreeMap.put("6Flash版本", "flashVersion");
		fiveTreeMap.put("7是否允许Cookies", "cookieEnabled");
		
		sixTreeMap.put("0人员发布统计", "staffReleased");
		sixTreeMap.put("1栏目发布统计", "channelReleased");
		sixTreeMap.put("2机构发布统计", "organReleased");
		
		sevenTreeMap.put("0政民互动统计", "interactive");
		sevenTreeMap.put("1网上咨询统计", "advisory");
		//sevenTreeMap.put("2留言审核统计", "audit");
	}
	
	public void tree() {
		try {
			TreeNode rootNode = new TreeNode();
			rootNode.setId((id++).toString());
			rootNode.setText("统计分析");
			rootNode.setState("open");
			rootNode.setIconCls("icon-channel-site");
			
			List<TreeNode> twoNodes = new ArrayList<TreeNode>();
			
			TreeNode twoNode = new TreeNode();
			twoNode.setId((id++).toString());
			twoNode.setText("总体情况");
			twoNode.setState("open");
			twoNode.setIconCls(ICON_CLS_NOTE);
			
			List<TreeNode> threeNodes = getThreeNode(oneTreeMap.entrySet().iterator());
			twoNode.setChildren(threeNodes);
			twoNodes.add(twoNode);
			
			twoNode = new TreeNode();
			twoNode.setId((id++).toString());
			twoNode.setText("访问量排行");
			twoNode.setState("open");
			twoNode.setIconCls(ICON_CLS_NOTE);
			
			threeNodes = getThreeNode(twoTreeMap.entrySet().iterator()); 
			twoNode.setChildren(threeNodes);
			twoNodes.add(twoNode);
			
			twoNode = new TreeNode();
			twoNode.setId((id++).toString());
			twoNode.setText("忠诚度分析");
			twoNode.setState("open");
			twoNode.setIconCls(ICON_CLS_NOTE);
			
			threeNodes = getThreeNode(threeTreeMap.entrySet().iterator()); 
			twoNode.setChildren(threeNodes);
			twoNodes.add(twoNode);
			
			twoNode = new TreeNode();
			twoNode.setId((id++).toString());
			twoNode.setText("点击量来源");
			twoNode.setState("open");
			twoNode.setIconCls(ICON_CLS_NOTE);
			
			threeNodes = getThreeNode(fourTreeMap.entrySet().iterator()); 
			twoNode.setChildren(threeNodes);
			twoNodes.add(twoNode);
			
			twoNode = new TreeNode();
			twoNode.setId((id++).toString());
			twoNode.setText("客户端情况");
			twoNode.setState("open");
			twoNode.setIconCls(ICON_CLS_NOTE);
			
			threeNodes = getThreeNode(fiveTreeMap.entrySet().iterator()); 
			twoNode.setChildren(threeNodes);
			twoNodes.add(twoNode);
			
			twoNode = new TreeNode();
			twoNode.setId((id++).toString());
			twoNode.setText("发布统计");
			twoNode.setState("open");
			twoNode.setIconCls(ICON_CLS_NOTE);
			
			threeNodes = getThreeNode(sixTreeMap.entrySet().iterator()); 
			twoNode.setChildren(threeNodes);
			twoNodes.add(twoNode);
			
			twoNode = new TreeNode();
			twoNode.setId((id++).toString());
			twoNode.setText("互动统计");
			twoNode.setState("open");
			twoNode.setIconCls(ICON_CLS_NOTE);
			
			threeNodes = getThreeNode(sevenTreeMap.entrySet().iterator()); 
			twoNode.setChildren(threeNodes);
			twoNodes.add(twoNode);
			
			rootNode.setChildren(twoNodes);
			Struts2Util.renderJson(JSONUtil.toJSON(new TreeNode[]{rootNode}));
		} catch (Exception e) {
		}
	}
	
	private List<TreeNode> getThreeNode(Iterator<Entry<String, String>> it){
		List<TreeNode> threeNodes = new ArrayList<TreeNode>();
		TreeNode threeNode;
		while (it.hasNext()){
			Entry<String, String> entry = it.next();
			threeNode = new TreeNode();
			threeNode.setId((id++).toString());
			threeNode.setText(entry.getKey().substring(1));
			threeNode.setState("open");
			threeNode.setIconCls(ICON_CLS_VISIT);
			Map<String, String> attributes = new HashMap<String, String>();
			attributes.put("url", entry.getValue());
			threeNode.setAttributes(attributes);
			threeNodes.add(threeNode);
		}
		return threeNodes;
	}
}
