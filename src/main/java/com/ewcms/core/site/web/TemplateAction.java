/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/**
 * 
 */
package com.ewcms.core.site.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;

import com.ewcms.core.site.SiteFacable;
import com.ewcms.core.site.model.Template;
import com.ewcms.core.site.model.TemplateEntity;
import com.ewcms.core.site.util.ConvertUtil;
import com.ewcms.web.CrudBaseAction;
import com.ewcms.web.util.EwcmsContextUtil;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;
import com.ewcms.web.util.TreeNodeConvert;
import com.ewcms.web.vo.TreeNode;

/**
 * @author 周冬初
 * 
 */
public class TemplateAction extends CrudBaseAction<Template, Integer> {
	private static final long serialVersionUID = 1L;
	@Autowired
	private SiteFacable siteFac;
	private File templateFile;
	private String templateFileFileName;
	private String templateFileContentType;
	private String templateContent;
	private Integer id;

	public Template getTemplateVo() {
		return super.getVo();
	}

	public void setTemplateVo(Template templateVo) {
		super.setVo(templateVo);
	}

	public String getTemplateFileContentType() {
		return templateFileContentType;
	}

	public void setTemplateFileContentType(String templateFileContentType) {
		this.templateFileContentType = templateFileContentType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setSelections(List<Integer> selections) {
		super.setOperatorPK(selections);
	}

	public List<Integer> getSelections() {
		return super.getOperatorPK();
	}

	public File getTemplateFile() {
		return templateFile;
	}

	public void setTemplateFile(File templateFile) {
		this.templateFile = templateFile;
	}

	public String getTemplateFileFileName() {
		return templateFileFileName;
	}

	public void setTemplateFileFileName(String templateFileFileName) {
		this.templateFileFileName = templateFileFileName;
	}

	public String getTemplateContent() {
		return templateContent;
	}

	public void setTemplateContent(String templateContent) {
		this.templateContent = templateContent;
	}

	@Override
	protected Integer getPK(Template vo) {
		return vo.getId();
	}

	@Override
	protected Template getOperator(Integer pk) {
		return siteFac.getTemplate(pk);
	}

	@Override
	protected void deleteOperator(Integer pk) {
		try {
			siteFac.delTemplate(pk);
		} catch (Exception e) {
			//outputInfo(e.toString());
		}
	}

	@Override
	protected Integer saveOperator(Template vo, boolean isUpdate) {
		TemplateEntity tplEntityVo = new TemplateEntity();
		InputStream in = null;
		try {
			if (templateFile != null) {
				getTemplateVo().setSize(ConvertUtil.kb(templateFile.length()));

				byte[] buffer = new byte[Integer.parseInt(String.valueOf(templateFile.length()))];
				in = new BufferedInputStream(new FileInputStream(templateFile), Integer.parseInt(String
						.valueOf(templateFile.length())));
				in.read(buffer);
				tplEntityVo.setTplEntity(buffer);
				getTemplateVo().setTemplateEntity(tplEntityVo);
				
				in.close();

			}
			if (isUpdate) {
				Template oldvo = siteFac.getTemplate(getTemplateVo().getId());
				oldvo.setDescribe(getTemplateVo().getDescribe());
				oldvo.setType(getTemplateVo().getType());
				oldvo.setUriPattern(getTemplateVo().getUriPattern());
				if (templateFile != null) {
					oldvo.getTemplateEntity().setTplEntity(getTemplateVo().getTemplateEntity().getTplEntity());
					oldvo.setName(templateFileFileName);
				}
				return siteFac.updTemplate(oldvo);
			} else {
				getTemplateVo().setTemplateEntity(tplEntityVo);
				getTemplateVo().setSite(getCurrentSite());
				getTemplateVo().setParent(siteFac.channelTemplate(getTemplateVo().getChannelId().toString()));
				if (templateFile != null) {
					getTemplateVo().setName(templateFileFileName);
				} else {
					String fileName = "new" + (int) (Math.random() * 100) + ".htm";
					getTemplateVo().setName(fileName);
				}
				return siteFac.addTemplate(getTemplateVo());
			}
		} catch (Exception e) {
			return null;
		} finally {
			try {
				if (in != null){
					in.close();
					in = null;
				}
			} catch (IOException e) {}
		}
	}

	@Override
	protected Template createEmptyVo() {
		Template newvo = new Template();
		newvo.setChannelId(vo.getChannelId());
		newvo.setPath(siteFac.channelTemplate(vo.getChannelId().toString()).getPath());
		return newvo;
	}

	public String importTemplate() {
		if (templateFile != null) {
			if (templateFileContentType != null
					&& "application/octet-stream,application/zip,application/x-zip-compressed,application/x-download".indexOf(templateFileContentType) != -1) {
				parseTemplateZIPFile();
			} else {
				getTemplateVo().setSite(getCurrentSite());
				getTemplateVo().setName(templateFileFileName);
				getTemplateVo().setSize(ConvertUtil.kb(templateFile.length()));
				TemplateEntity tplEntityVo = new TemplateEntity();
				InputStream in = null;
				try {
					byte[] buffer = new byte[Integer.parseInt(String.valueOf(templateFile.length()))];
					in = new BufferedInputStream(new FileInputStream(templateFile), Integer.parseInt(String
							.valueOf(templateFile.length())));
					in.read(buffer);
					tplEntityVo.setTplEntity(buffer);
					getTemplateVo().setTemplateEntity(tplEntityVo);
					if (getTemplateVo().getParent().getId() == null) {
						getTemplateVo().setParent(null);
					} else {
						getTemplateVo().setParent(siteFac.getTemplate(getTemplateVo().getParent().getId()));
					}
					siteFac.addTemplate(getTemplateVo());
					in.close();
				} catch (Exception e) {
				} finally {
					try {
						if (in != null){
							in.close();
							in = null;
						}
					} catch (IOException e) {}
				}
			}

		} else {
			if (getTemplateVo().getParent() != null && getTemplateVo().getParent().getId() != null)
				getTemplateVo().setPath(siteFac.getTemplate(getTemplateVo().getParent().getId()).getPath());
		}
		return INPUT;
	}
	
	private String zipName;
	
	public String getZipName() {
		return zipName;
	}

	public void setZipName(String zipName) {
		this.zipName = zipName;
	}

	public void downLoadTemplate(){
		PrintWriter pw = null;
		InputStream in = null;
		if (getSelections() != null && getSelections().size() == 1){
			try {
				Template template = siteFac.getTemplate(getSelections().get(0));
				if (template != null && template.getTemplateEntity() != null){
					String templateSource = new String(template.getTemplateEntity().getTplEntity(), "UTF-8");
					if (templateSource != null && templateSource.length() > 0){
						String fileName = template.getName();
						fileName = URLEncoder.encode(fileName, "UTF-8");
						
						HttpServletResponse response = ServletActionContext.getResponse();
						response.setCharacterEncoding("UTF-8");
						response.setHeader("Content-disposition", "attachment; filename=" + fileName);
						response.setContentType("application/zip;charset=UTF-8");
		
						pw = response.getWriter();
						pw.write(templateSource);
						
						pw.flush();
					}
				}else{
					this.addActionError("没有文件可供下载");
				}
			}catch(Exception e){
			}finally {
				if (pw != null) {
					try {
						pw.close();
						pw = null;
					} catch (Exception e) {
					}
				}
				if (in != null) {
					try {
						in.close();
						in = null;
					} catch (Exception e) {
					}
				}
			}
		}else{
			this.addActionError("没有文件可供下载");
		}
	}

	@SuppressWarnings("rawtypes")
	private void parseTemplateZIPFile() {
		try {
			ZipFile zfile = new ZipFile(templateFile);
			Enumeration zList = zfile.getEntries();
			Map<String, Integer> dirMap = new HashMap<String, Integer>();
			ZipEntry ze = null;
			String[] pathArr;
			String pathKey;
			while (zList.hasMoreElements()) {
				try {
					ze = (ZipEntry) zList.nextElement();
					Template vo = new Template();
					vo.setSite(getCurrentSite());
					pathArr = ze.getName().split("/");
					vo.setName(pathArr[pathArr.length - 1]);
					pathKey = ze.getName().substring(0, ze.getName().lastIndexOf(pathArr[pathArr.length - 1]));
					if (pathKey == null || pathKey.length() == 0) {
						if (getTemplateVo().getParent().getId() == null) {
							vo.setParent(null);
						} else {
							vo.setParent(siteFac.getTemplate(getTemplateVo().getParent().getId()));
						}
					} else {
						vo.setParent(siteFac.getTemplate(dirMap.get(pathKey)));
					}

					if (ze.isDirectory()) {
						dirMap.put(ze.getName(), siteFac.addTemplate(vo));
						continue;
					}

					InputStream in = new BufferedInputStream(zfile.getInputStream(ze));
					byte[] buffer = new byte[Integer.parseInt(String.valueOf(ze.getSize()))];
					in.read(buffer);
					TemplateEntity tplEntityVo = new TemplateEntity();
					tplEntityVo.setTplEntity(buffer);
					vo.setTemplateEntity(tplEntityVo);
					siteFac.addTemplate(vo);
					in.close();
				} catch (Exception e) {
				}
			}
			zfile.close();
		} catch (Exception e) {
		}
	}

	/**
	 * 获取模板树目录.
	 */
	public void templateTree() throws Exception {
		if (id == null) {
			TreeNode treeFile = new TreeNode();
			treeFile.setText(getCurrentSite().getSiteName());
			treeFile.setState("open");
			treeFile.setChildren(TreeNodeConvert.templateConvert(siteFac.getTemplaeTreeList(false)));
			Struts2Util.renderJson(JSONUtil.toJSON(new TreeNode[] { treeFile }));
			return;
		}
		List<TreeNode> tnList = TreeNodeConvert.templateConvert(siteFac.getTemplaeTreeList(id, false));
		if (tnList.isEmpty()) {
			Struts2Util.renderJson("{}");
		} else {
			Struts2Util.renderJson(JSONUtil.toJSON(tnList));
		}
	}

	/**
	 * 获取专栏模板树目录.
	 */
	public void templateChannelTree() throws Exception {
		if (id == null) {
			TreeNode treeFile = new TreeNode();
			treeFile.setText(getCurrentSite().getSiteName());
			treeFile.setState("open");
			treeFile.setChildren(TreeNodeConvert.templateConvert(siteFac.getTemplaeTreeList(true)));
			Struts2Util.renderJson(JSONUtil.toJSON(new TreeNode[] { treeFile }));
			return;
		}
		List<TreeNode> tnList = TreeNodeConvert.templateConvert(siteFac.getTemplaeTreeList(id, getTemplateVo()
				.getChannelId().toString()));
		if (tnList.isEmpty()) {
			Struts2Util.renderJson("{}");
		} else {
			Struts2Util.renderJson(JSONUtil.toJSON(tnList));
		}
	}

	/**
	 * 新建模板文件.
	 */
	public void addTemplate() {
		try {
			Template vo = getTemplateVo();
			vo.setSite(getCurrentSite());
			vo.setSize("0 KB");
			vo.setTemplateEntity(new TemplateEntity());
			if (vo.getParent().getId() == null) {
				vo.setParent(null);
			} else {
				getTemplateVo().setParent(siteFac.getTemplate(vo.getParent().getId()));
			}
			Integer tplId = siteFac.addTemplate(vo);
			Struts2Util.renderJson(JSONUtil.toJSON(tplId));
		} catch (Exception e) {
			//outputInfo(e.toString());
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}

	/**
	 * 新建模板文件夹.
	 */
	public void addFolder() {
		try {
			Template vo = getTemplateVo();
			vo.setSite(getCurrentSite());
			if (vo.getParent().getId() == null) {
				vo.setParent(null);
			} else {
				getTemplateVo().setParent(siteFac.getTemplate(vo.getParent().getId()));
			}
			Integer tplId = siteFac.addTemplate(vo);
			Struts2Util.renderJson(JSONUtil.toJSON(tplId));
		} catch (Exception e) {
			//outputInfo(e.toString());
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}

	/**
	 * 重命名模板文件名.
	 */
	public void renameTemplate() {
		try {
			String newName = getTemplateVo().getName();
			Template vo = siteFac.getTemplate(getTemplateVo().getId());
			vo.setName(newName);
			siteFac.updTemplate(vo);
			Struts2Util.renderJson(JSONUtil.toJSON("true"));
		} catch (Exception e) {
			//outputInfo(e.toString());
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}

	/**
	 * 删除模板.
	 */
	public void delTemplate() {
		try {
			siteFac.delTemplate(getTemplateVo().getId());
			Struts2Util.renderJson(JSONUtil.toJSON("true"));
		} catch (Exception e) {
			//outputInfo(e.toString());
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}

	/**
	 * 移动模板.
	 */
	public void movetoTemplate() {
		try {
			Template vo = siteFac.getTemplate(getTemplateVo().getId());
			if (getTemplateVo().getParent().getId() == null) {
				vo.setParent(null);
			} else {
				vo.setParent(siteFac.getTemplate(getTemplateVo().getParent().getId()));
			}
			siteFac.updTemplate(vo);
			Struts2Util.renderJson(JSONUtil.toJSON("true"));
		} catch (Exception e) {
			//outputInfo(e.toString());
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}

	/**
	 * 编辑模板.
	 */
	public String editTemplate() {
		if (getTemplateVo() != null && getTemplateVo().getId() != null) {
			Template vo = siteFac.getTemplate(getTemplateVo().getId());
			try {
				setTemplateContent(new String(vo.getTemplateEntity().getTplEntity(), "UTF-8"));
			} catch (Exception e) {
			}
			setTemplateVo(vo);
		}
		return INPUT;
	}

	public String saveInfo() {
		InputStream in = null;
		try {
			Template vo = siteFac.getTemplate(getTemplateVo().getId());
			vo.setDescribe(getTemplateVo().getDescribe());
			if (templateFile != null) {
				vo.setSize(ConvertUtil.kb(templateFile.length()));
				TemplateEntity tplEntityVo = new TemplateEntity();
				byte[] buffer = new byte[Integer.parseInt(String.valueOf(templateFile.length()))];
				in = new BufferedInputStream(new FileInputStream(templateFile), Integer.parseInt(String
						.valueOf(templateFile.length())));
				in.read(buffer);
				tplEntityVo.setTplEntity(buffer);
				vo.setTemplateEntity(tplEntityVo);
				vo.setName(templateFileFileName);
				
				in.close();
			}
			siteFac.updTemplate(vo);

			addActionMessage("数据保存成功！");
		} catch (Exception e) {
			addActionMessage("数据保存失败！");
		} finally {
			try{
				if (in != null){
					in.close();
					in = null;
				}
			} catch(IOException e){}
		}
		return INPUT;
	}

	public String saveContent() {
		try {
			Template vo = siteFac.getTemplate(getTemplateVo().getId());
			TemplateEntity tplEntityVo = new TemplateEntity();
			tplEntityVo.setTplEntity(getTemplateContent().getBytes("UTF-8"));
			vo.setTemplateEntity(tplEntityVo);
			siteFac.updTemplate(vo);

			addActionMessage("模板内容保存成功！");
		} catch (Exception e) {
			addActionMessage("模板内容保存失败！");
		}
		return INPUT;
	}

//	public void previewTemplate() {
//		HttpServletResponse response = ServletActionContext.getResponse();
//		response.setContentType("text/html");
//		response.setCharacterEncoding("utf-8");
//		try {
//			templatePreview.view(response.getOutputStream(), getCurrentSite(),
//					siteFac.getChannel(getTemplateVo().getChannelId()), siteFac.getTemplate(getTemplateVo().getId()),
//					true);
//		} catch (Exception e) {
//			outputInfo(e.toString());
//		}
//	}

	private Integer channelId;
	
	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	private Integer cover;
	
	public Integer getCover() {
		return cover;
	}

	public void setCover(Integer cover) {
		this.cover = cover;
	}

	public void appChild(){
		try{
			if (getChannelId() != null && getSelections() != null && getSelections().size() > 0 && getCover() != null){
				if (getCover().intValue() == 1)
					siteFac.saveAppChild(getChannelId(), getSelections(), true);
				else
					siteFac.saveAppChild(getChannelId(), getSelections(), false);
				Struts2Util.renderJson(JSONUtil.toJSON("模板应用于子栏目成功!"));
			}else{
				Struts2Util.renderJson(JSONUtil.toJSON("模板应用于子栏目失败!"));
			}
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("模板应用于子栏目出现系统错误!"));
		}
	}
	
	private Integer children;
	
	public Integer getChildren() {
		return children;
	}

	public void setChildren(Integer children) {
		this.children = children;
	}
	
	public void forceRelease() {
		try {
			if (getChannelId() != null && getChildren() != null){
				if (getChildren().intValue() == 1)
					siteFac.forceRelease(getChannelId(), true);
				else
					siteFac.forceRelease(getChannelId(), false);
				Struts2Util.renderJson(JSONUtil.toJSON("强制发布成功"));
			}else{
				Struts2Util.renderJson(JSONUtil.toJSON("强制发布失败"));
			}
		} catch (AccessDeniedException e) {
			Struts2Util.renderJson(JSONUtil.toJSON("没有强制发布权限"));
		} catch (Exception e) {
			Struts2Util.renderJson(JSONUtil.toJSON("系统错误"));
		}
	}

	private String channelName;
	
	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public void pinYin(){
		if (getChannelName() != null && getChannelName().trim().length() > 0){
			Struts2Util.renderJson(JSONUtil.toJSON(ConvertUtil.pinYin(getChannelName())));
		}else{
			Struts2Util.renderJson(JSONUtil.toJSON(""));
		}
	}
	
	public void exportZip(){
		ZipOutputStream zos = null;
		try{
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/x-download;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=template" + id + ".zip");
			zos = new ZipOutputStream(response.getOutputStream());
			zos.setEncoding("UTF-8");
			
			if (id != null){
				siteFac.exportTemplateZip(id, zos, "");
			}else{
				List<Template> templates = siteFac.getTemplaeTreeList(false);
				for (Template template : templates){
					siteFac.exportTemplateZip(template.getId(), zos, EwcmsContextUtil.getCurrentSite().getSiteName() + "/");
				}
			}
			zos.flush();
			zos.close();
		}catch(Exception e){
			
		}finally {
			if (zos != null){
				try{
					zos.close();
					zos = null;
				}catch(Exception e){
				}
			}
		}
	}
}
