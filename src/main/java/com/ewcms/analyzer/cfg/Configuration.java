/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.analyzer.cfg;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Properties;

import com.ewcms.analyzer.dic.Dictionary;
import com.ewcms.analyzer.seg.CJKSegmenter;
import com.ewcms.analyzer.seg.ISegmenter;
import com.ewcms.analyzer.seg.LetterSegmenter;
import com.ewcms.analyzer.seg.QuantifierSegmenter;

/**
 * <ul>
 * 简单的配置管理类,单例模式
 * </ul>
 * 
 * @author 吴智俊
 */
public class Configuration {
	//分词器配置文件路径
	private static final String FILE_NAME = "/com/ewcms/analyzer/cfg/analyzer.cfg.xml";
	//配置属性——扩展字典
	private static final String EXT_DICT = "ext_dict";
	//配置属性——扩展停止词典
	private static final String EXT_STOP = "ext_stopwords";
	
	private static final Configuration CFG = new Configuration();
	
	private Properties props;
	
	/*
	 * 初始化配置文件
	 */
	private Configuration(){
		
		props = new Properties();
		
		InputStream input = Configuration.class.getResourceAsStream(FILE_NAME);
		if(input != null){
			try {
				props.loadFromXML(input);
			} catch (InvalidPropertiesFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 获取扩展字典配置路径
	 * @return List<String> 相对类加载器的路径
	 */
	public static List<String> getExtDictionarys(){
		List<String> extDictFiles = new ArrayList<String>(2);
		String extDictCfg = CFG.props.getProperty(EXT_DICT);
		if(extDictCfg != null){
			//使用;分割多个扩展字典配置
			String[] filePaths = extDictCfg.split(";");
			if(filePaths != null){
				for(String filePath : filePaths){
					if(filePath != null && !"".equals(filePath.trim())){
						extDictFiles.add(filePath.trim());
						//System.out.println(filePath.trim());
					}
				}
			}
		}		
		return extDictFiles;		
	}
	
	/**
	 * 获取扩展停止词典配置路径
	 * @return List<String> 相对类加载器的路径
	 */
	public static List<String> getExtStopWordDictionarys(){
		List<String> extStopWordDictFiles = new ArrayList<String>(2);
		String extStopWordDictCfg = CFG.props.getProperty(EXT_STOP);
		if(extStopWordDictCfg != null){
			//使用;分割多个扩展字典配置
			String[] filePaths = extStopWordDictCfg.split(";");
			if(filePaths != null){
				for(String filePath : filePaths){
					if(filePath != null && !"".equals(filePath.trim())){
						extStopWordDictFiles.add(filePath.trim());
						//System.out.println(filePath.trim());
					}
				}
			}
		}		
		return extStopWordDictFiles;		
	}
		
	
	/**
	 * 初始化子分词器实现
	 * （目前暂时不考虑配置扩展）
	 * @return List<ISegmenter>
	 */
	public static List<ISegmenter> loadSegmenter(){
		//初始化词典单例
		Dictionary.getInstance();
		List<ISegmenter> segmenters = new ArrayList<ISegmenter>(4);
		//处理数量词的子分词器
		segmenters.add(new QuantifierSegmenter());
		//处理中文词的子分词器
		segmenters.add(new CJKSegmenter());
		//处理字母的子分词器
		segmenters.add(new LetterSegmenter()); 
		return segmenters;
	}
}
