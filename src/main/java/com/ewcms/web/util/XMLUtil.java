package com.ewcms.web.util;

import java.io.IOException;  
import org.dom4j.Document;  
import org.dom4j.DocumentHelper;  
import org.dom4j.Element; 

/**
 * 使用dom4j生成XML工具类
 * 
 * @author wuzhijun
 */
public class XMLUtil {
	private Document document = null;

	public Document getDocument() {
		return document;
	}

	/**
	 * 构造方法，初始化Document
	 */
	public XMLUtil() {
		document = DocumentHelper.createDocument();
	}

	/**
	 * 生成根节点
	 * 
	 * @param rootName
	 * @return
	 */
	public Element addRoot(String rootName) {
		Element root = document.addElement(rootName);
		return root;
	}

	/**
	 * 生成节点
	 * 
	 * @param parentElement
	 * @param elementName
	 * @return
	 */
	public Element addNode(Element parentElement, String elementName) {
		Element node = parentElement.addElement(elementName);
		return node;
	}

	/**
	 * 为节点增加一个属性
	 * 
	 * @param thisElement
	 * @param attributeName
	 * @param attributeValue
	 */
	public void addAttribute(Element thisElement, String attributeName,
			String attributeValue) {
		thisElement.addAttribute(attributeName, attributeValue);
	}

	/**
	 * 为节点增加多个属性
	 * 
	 * @param thisElement
	 * @param attributeNames
	 * @param attributeValues
	 */
	public void addAttributes(Element thisElement, String[] attributeNames,
			String[] attributeValues) {
		for (int i = 0; i < attributeNames.length; i++) {
			thisElement.addAttribute(attributeNames[i], attributeValues[i]);
		}
	}

	/**
	 * 增加节点的值
	 * 
	 * @param thisElement
	 * @param text
	 */
	public void addText(Element thisElement, String text) {
		thisElement.addText(text);
	}

	/**
	 * 获取最终的XML
	 * 
	 * @return
	 * @throws IOException
	 */
	public String getXML() {
		return document.asXML().substring(39);
	}
}
