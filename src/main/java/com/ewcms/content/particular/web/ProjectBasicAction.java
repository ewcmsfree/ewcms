/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.particular.web;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;

import com.ewcms.content.particular.BaseException;
import com.ewcms.content.particular.ParticularFacable;
import com.ewcms.content.particular.model.ProjectBasic;
import com.ewcms.core.site.model.Organ;
import com.ewcms.web.CrudBaseAction;
import com.ewcms.web.util.EwcmsContextUtil;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;

/**
 * @author 吴智俊
 */
public class ProjectBasicAction extends CrudBaseAction<ProjectBasic, Long> {

	private static final long serialVersionUID = -7215016049247026935L;

	@Autowired
	private ParticularFacable particularFac;
	
	private Integer channelId;
	
	private String organShow;
	
	private Integer organId;
	
	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public String getOrganShow() {
		return organShow;
	}

	public void setOrganShow(String organShow) {
		this.organShow = organShow;
	}

	public Integer getOrganId() {
		return organId;
	}

	public void setOrganId(Integer organId) {
		this.organId = organId;
	}

	public ProjectBasic getProjectBasicVo() {
		return super.getVo();
	}

	public void setProjectBasicVo(ProjectBasic projectBasic) {
		super.setVo(projectBasic);
	}

	public List<Long> getSelections() {
		return super.getOperatorPK();
	}

	public void setSelections(List<Long> selections) {
		super.setOperatorPK(selections);
	}

	@Override
	protected Long getPK(ProjectBasic vo) {
		return vo.getId();
	}

	@Override
	protected ProjectBasic getOperator(Long pk) {
		ProjectBasic projectBasic = particularFac.findProjectBasicById(pk);
		if (EwcmsContextUtil.getGroupnames().contains("GROUP_ADMIN")){
			organShow = "enable";
		}else{
			organShow = "disable";
			Organ organ = particularFac.findOrganByUserName();
			if (organ != null && organ.getId() != null && organ.getId() > 0){
				organId = organ.getId();
			}
		}
		return projectBasic;
	}

	@Override
	protected void deleteOperator(Long pk) {
		particularFac.delProjectBasic(pk);
	}

	@Override
	protected Long saveOperator(ProjectBasic vo, boolean isUpdate) {
		vo.setChannelId(getChannelId());
		try{
			if (isUpdate) {
				return particularFac.updProjectBasic(vo);
			} else {
				return particularFac.addProjectBasic(vo);
			}
		}catch(BaseException e){
			addActionMessage(e.getPageMessage());
			return null;
		}
	}

	@Override
	protected ProjectBasic createEmptyVo() {
		if (EwcmsContextUtil.getGroupnames().contains("GROUP_ADMIN")){
			organShow = "enable";
		}else{
			organShow = "disable";
			Organ organ = particularFac.findOrganByUserName();
			if (organ != null){
				organId = organ.getId();
			}
		}
		return new ProjectBasic();
	}
	
	private File xmlFile;
	private String xmlFileContentType;
	
	public File getXmlFile() {
		return xmlFile;
	}

	public void setXmlFile(File xmlFile) {
		this.xmlFile = xmlFile;
	}

	public String getXmlFileContentType() {
		return xmlFileContentType;
	}

	public void setXmlFileContentType(String xmlFileContentType) {
		this.xmlFileContentType = xmlFileContentType;
	}

	public String importXML(){
		try{
			if (getXmlFile() != null && getChannelId() != null) {
				particularFac.addProjectBasicByImportXml(getXmlFile(), getChannelId(), getXmlFileContentType());
			}
		}catch(Exception e){
		}
		return INPUT;
	}
	
	public void exportXML(){
		if (getSelections() != null && getSelections().size() > 0){
			ServletOutputStream out = null;
			try{
				Document document = particularFac.exportXml(getSelections());
				
				StringWriter stringWriter = new StringWriter();
				
	            OutputFormat xmlFormat = new OutputFormat();  
	            xmlFormat.setEncoding("UTF-8");  
	            XMLWriter xmlWriter = new XMLWriter(stringWriter, xmlFormat);  
	            xmlWriter.write(document); 
	            xmlWriter.flush();
	            xmlWriter.close();  
	            
	            HttpServletResponse resp = Struts2Util.getResponse();
	            out = resp.getOutputStream();
		    	resp.setCharacterEncoding("UTF-8");
		    	resp.setContentType("text/xml; charset=UTF-8");
		    	resp.addHeader("Content-Disposition", "attachment; filename=xmjbxx.xml");
		    	out.write(stringWriter.toString().getBytes("UTF-8"));
		    	out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if (out != null){
					try {
						out.close();
					} catch (IOException e) {
					}
					out = null;
				}
			}		
		}
	}
	
	public void pub(){
		try{
			if (getChannelId() != null && getSelections() != null && getSelections().size() > 0){
				particularFac.pubProjectBasic(getChannelId(), getSelections());
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			}else{
				Struts2Util.renderJson(JSONUtil.toJSON("false"));
			}
		}catch (AccessDeniedException e) {
			Struts2Util.renderJson(JSONUtil.toJSON("accessdenied"));
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
	
	public void unPub(){
		try{
			if (getChannelId() != null && getSelections() != null && getSelections().size() > 0){
				particularFac.unPubProjectBasic(getChannelId(), getSelections());
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			}else{
				Struts2Util.renderJson(JSONUtil.toJSON("false"));
			}
		}catch (AccessDeniedException e) {
			Struts2Util.renderJson(JSONUtil.toJSON("accessdenied"));
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
}