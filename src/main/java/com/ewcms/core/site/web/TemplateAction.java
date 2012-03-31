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
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.springframework.beans.factory.annotation.Autowired;
import com.ewcms.core.site.SiteFac;
import com.ewcms.core.site.model.Template;
import com.ewcms.core.site.model.TemplateEntity;
import com.ewcms.core.site.model.TemplateType;
import com.ewcms.web.CrudBaseAction;
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
	private SiteFac siteFac;
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
		try {
			if (templateFile != null) {
				getTemplateVo().setSize(converKB(templateFile.length()));

				byte[] buffer = new byte[Integer.parseInt(String.valueOf(templateFile.length()))];
				InputStream in = new BufferedInputStream(new FileInputStream(templateFile), Integer.parseInt(String
						.valueOf(templateFile.length())));
				in.read(buffer);
				tplEntityVo.setTplEntity(buffer);
				getTemplateVo().setTemplateEntity(tplEntityVo);

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
			//this.outputInfo(e.toString());
			return null;
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
					&& "application/zip,application/x-zip-compressed".indexOf(templateFileContentType) != -1) {
				parseTemplateZIPFile();
			} else {
				getTemplateVo().setSite(getCurrentSite());
				getTemplateVo().setName(templateFileFileName);
				getTemplateVo().setSize(converKB(templateFile.length()));
				TemplateEntity tplEntityVo = new TemplateEntity();
				try {
					byte[] buffer = new byte[Integer.parseInt(String.valueOf(templateFile.length()))];
					InputStream in = new BufferedInputStream(new FileInputStream(templateFile), Integer.parseInt(String
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
				} catch (Exception e) {
					//outputInfo(e.toString());
				}
			}

		} else {
			if (getTemplateVo().getParent() != null && getTemplateVo().getParent().getId() != null)
				getTemplateVo().setPath(siteFac.getTemplate(getTemplateVo().getParent().getId()).getPath());
		}
		return INPUT;
	}

	@SuppressWarnings("rawtypes")
	private void parseTemplateZIPFile() {
		try {
			ZipFile zfile = new ZipFile(templateFile);
			Enumeration zList = zfile.entries();
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
				} catch (Exception e) {
					//outputInfo(e.toString());
				}
			}
			zfile.close();
		} catch (Exception e) {
			//outputInfo(e.toString());
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
		try {
			Template vo = siteFac.getTemplate(getTemplateVo().getId());
			vo.setDescribe(getTemplateVo().getDescribe());
			if (templateFile != null) {
				vo.setSize(converKB(templateFile.length()));
				TemplateEntity tplEntityVo = new TemplateEntity();
				byte[] buffer = new byte[Integer.parseInt(String.valueOf(templateFile.length()))];
				InputStream in = new BufferedInputStream(new FileInputStream(templateFile), Integer.parseInt(String
						.valueOf(templateFile.length())));
				in.read(buffer);
				tplEntityVo.setTplEntity(buffer);
				vo.setTemplateEntity(tplEntityVo);
				vo.setName(templateFileFileName);
			}
			siteFac.updTemplate(vo);

			addActionMessage("数据保存成功！");
		} catch (Exception e) {
			addActionMessage("数据保存失败！");
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

	/**
	 * 模板类型选择
	 * 
	 * @return 记录集
	 */
	public List<TemplateType> getTemplateTypeList() {
		return Arrays.asList(TemplateType.values());
	}

	private String converKB(long size) {
		DecimalFormat dfom = new DecimalFormat("####.0");
		if (size <= 0)
			return "0 KB";
		return String.valueOf(dfom.format(size / 1000)) + " KB";
	}
}
