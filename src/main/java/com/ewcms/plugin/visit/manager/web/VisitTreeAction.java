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
	
	static{
		oneTreeMap.put("0综合报告", "");
		oneTreeMap.put("1全站点击率", "");
		oneTreeMap.put("2最近访问记录", "");
		oneTreeMap.put("3时段分布", "");
		oneTreeMap.put("4入口分析", "");
		oneTreeMap.put("5出口分析", "");
		oneTreeMap.put("6被访主机分析", "");
		oneTreeMap.put("7区域分布", "");
		oneTreeMap.put("8在线人数", "");
		
		twoTreeMap.put("0栏目点击排行", "");
		twoTreeMap.put("1文章点击排行", "");
		twoTreeMap.put("2文章点击排行", "");
		twoTreeMap.put("3视频点击排行", "");
		twoTreeMap.put("4广告点击排行", "");
		twoTreeMap.put("5URL点击排行", "");
		
		threeTreeMap.put("0访问深度", "");
		threeTreeMap.put("1访问频率", "");
		threeTreeMap.put("2回头率", "");
		threeTreeMap.put("3停留时间", "");
		
		fourTreeMap.put("0来源组成", "");
		fourTreeMap.put("1搜索引擎", "");
		fourTreeMap.put("2来源网站", "");
		fourTreeMap.put("3关键字分析", "");
		
		fiveTreeMap.put("0操作系统", "");
		fiveTreeMap.put("1浏览器", "");
		fiveTreeMap.put("2语言", "");
		fiveTreeMap.put("3屏幕分辨率", "");
		fiveTreeMap.put("4屏幕色深", "");
		fiveTreeMap.put("5是否支持Apple", "");
		fiveTreeMap.put("6Flash版本", "");
		fiveTreeMap.put("7是否允许Cookies", "");
		
		sixTreeMap.put("0人员发布统计", "");
		sixTreeMap.put("1栏目发布统计", "");
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
