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
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.core.site.SiteFacable;
import com.ewcms.core.site.model.TemplateSource;
import com.ewcms.core.site.model.TemplatesrcEntity;
import com.ewcms.publication.WebPublishFacable;
import com.ewcms.web.CrudBaseAction;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;
import com.ewcms.web.util.TreeNodeConvert;
import com.ewcms.web.vo.TreeNode;

/**
 * @author 周冬初
 * 
 */
public class SourceAction extends CrudBaseAction<TemplateSource, Integer> {
	private static final long serialVersionUID = 1L;

	@Autowired
	private SiteFacable siteFac;
	@Autowired
	private WebPublishFacable webPublish;
	private File sourceFile;
	private String sourceFileFileName;
	private String sourceFileContentType;
	private String sourceContent;
	private Integer id;

	public TemplateSource getSourceVo() {
		return super.getVo();
	}

	public void setSourceVo(TemplateSource templateVo) {
		super.setVo(templateVo);
	}

	public File getSourceFile() {
		return sourceFile;
	}

	public void setSourceFile(File sourceFile) {
		this.sourceFile = sourceFile;
	}

	public String getSourceFileFileName() {
		return sourceFileFileName;
	}

	public void setSourceFileFileName(String sourceFileFileName) {
		this.sourceFileFileName = sourceFileFileName;
	}

	public String getSourceFileContentType() {
		return sourceFileContentType;
	}

	public void setSourceFileContentType(String sourceFileContentType) {
		this.sourceFileContentType = sourceFileContentType;
	}

	public String getSourceContent() {
		return sourceContent;
	}

	public void setSourceContent(String sourceContent) {
		this.sourceContent = sourceContent;
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

	@Override
	protected Integer getPK(TemplateSource vo) {
		return vo.getId();
	}

	@Override
	protected TemplateSource getOperator(Integer pk) {
		return siteFac.getTemplateSource(pk);
	}

	@Override
	protected void deleteOperator(Integer pk) {
		siteFac.delTemplateSource(pk);
	}

	@Override
	protected Integer saveOperator(TemplateSource vo, boolean isUpdate) {
		TemplatesrcEntity tplEntityVo = new TemplatesrcEntity();
		try {
			if (sourceFile != null) {
				getSourceVo().setSize(converKB(sourceFile.length()));

				byte[] buffer = new byte[Integer.parseInt(String.valueOf(sourceFile.length()))];
				InputStream in = new BufferedInputStream(new FileInputStream(sourceFile), Integer.parseInt(String
						.valueOf(sourceFile.length())));
				in.read(buffer);
				tplEntityVo.setSrcEntity(buffer);
				getSourceVo().setSourceEntity(tplEntityVo);

			}
			if (isUpdate) {
				TemplateSource oldvo = siteFac.getTemplateSource(getSourceVo().getId());
				oldvo.setDescribe(getSourceVo().getDescribe());
				if (sourceFile != null) {
					oldvo.getSourceEntity().setSrcEntity(getSourceVo().getSourceEntity().getSrcEntity());
					oldvo.setName(sourceFileFileName);
					oldvo.setRelease(false);
				}
				return siteFac.updTemplateSource(oldvo);
			} else {
				getSourceVo().setSourceEntity(tplEntityVo);
				getSourceVo().setSite(getCurrentSite());
				getSourceVo().setParent(siteFac.channelTemplateSource(getSourceVo().getChannelId().toString()));
				if (sourceFile != null) {
					getSourceVo().setName(sourceFileFileName);
				} else {
					String fileName = "new" + (int) (Math.random() * 100) + ".htm";
					getSourceVo().setName(fileName);
				}
				return siteFac.addTemplateSource(getSourceVo());
			}
		} catch (Exception e) {
			//this.outputInfo(e.toString());
			return null;
		}
	}

	@Override
	protected TemplateSource createEmptyVo() {
		TemplateSource newvo = new TemplateSource();
		newvo.setChannelId(vo.getChannelId());
		newvo.setPath(siteFac.channelTemplateSource(vo.getChannelId().toString()).getPath());
		return newvo;
	}

	/**
	 * 获取模板资源树目录.
	 */
	public void sourceTree() throws Exception {
		if (id == null) {
			TreeNode treeFile = new TreeNode();
			treeFile.setText(getCurrentSite().getSiteName());
			treeFile.setState("open");
			treeFile.setChildren(TreeNodeConvert.templateSourceConvert(siteFac.getTemplaeSourceTreeList(false)));
			Struts2Util.renderJson(JSONUtil.toJSON(new TreeNode[] { treeFile }));
			return;
		}
		List<TreeNode> tnList = TreeNodeConvert.templateSourceConvert(siteFac.getTemplaeSourceTreeList(id, false));
		if (tnList.isEmpty()) {
			Struts2Util.renderJson("{}");
		} else {
			Struts2Util.renderJson(JSONUtil.toJSON(tnList));
		}
	}

	public String importSource() {
		if (sourceFile != null) {
			if (sourceFileContentType != null
					&& "application/octet-stream,application/zip,application/x-zip-compressed".indexOf(sourceFileContentType) != -1) {
				paraseSourceZIPFile();
			} else {
				getSourceVo().setSite(getCurrentSite());
				getSourceVo().setName(sourceFileFileName);
				getSourceVo().setSize(converKB(sourceFile.length()));
				TemplatesrcEntity tplEntityVo = new TemplatesrcEntity();
				byte[] buffer = new byte[Integer.parseInt(String.valueOf(sourceFile.length()))];
				try {
					InputStream in = new BufferedInputStream(new FileInputStream(sourceFile), Integer.parseInt(String
							.valueOf(sourceFile.length())));
					in.read(buffer);

					tplEntityVo.setSrcEntity(buffer);
					getSourceVo().setSourceEntity(tplEntityVo);
					if (getSourceVo().getParent().getId() == null) {
						getSourceVo().setParent(null);

					} else {
						getSourceVo().setParent(siteFac.getTemplateSource(getSourceVo().getParent().getId()));
					}
					siteFac.addTemplateSource(getSourceVo());
				} catch (Exception e) {
					//outputInfo(e.toString());
				}
			}
		} else {
			if (getSourceVo().getParent() != null && getSourceVo().getParent().getId() != null)
				getSourceVo().setPath(siteFac.getTemplateSource(getSourceVo().getParent().getId()).getPath());
		}
		return INPUT;
	}

	@SuppressWarnings("rawtypes")
	private void paraseSourceZIPFile() {
		try {
			ZipFile zfile = new ZipFile(sourceFile);
			Enumeration zList = zfile.entries();
			Map<String, Integer> dirMap = new HashMap<String, Integer>();
			ZipEntry ze = null;
			String[] pathArr;
			String pathKey;
			while (zList.hasMoreElements()) {
				try {
					ze = (ZipEntry) zList.nextElement();
					TemplateSource vo = new TemplateSource();
					vo.setSite(getCurrentSite());
					pathArr = ze.getName().split("/");
					vo.setName(pathArr[pathArr.length - 1]);
					pathKey = ze.getName().substring(0, ze.getName().lastIndexOf(pathArr[pathArr.length - 1]));
					if (pathKey == null || pathKey.length() == 0) {
						if (getSourceVo().getParent().getId() == null) {
							vo.setParent(null);
						} else {
							vo.setParent(siteFac.getTemplateSource(getSourceVo().getParent().getId()));
						}
					} else {
						vo.setParent(siteFac.getTemplateSource(dirMap.get(pathKey)));
					}
					if (ze.isDirectory()) {
						dirMap.put(ze.getName(), siteFac.addTemplateSource(vo));
						continue;
					}
					InputStream in = new BufferedInputStream(zfile.getInputStream(ze));
					byte[] buffer = new byte[Integer.parseInt(String.valueOf(ze.getSize()))];
					in.read(buffer);

					TemplatesrcEntity tplEntityVo = new TemplatesrcEntity();
					tplEntityVo.setSrcEntity(buffer);
					vo.setSourceEntity(tplEntityVo);
					siteFac.addTemplateSource(vo);
					buffer = null;
					in.close();
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
	 * 新建资源文件.
	 */
	public void addSource() {
		try {
			TemplateSource vo = getSourceVo();
			vo.setSite(getCurrentSite());
			vo.setSize("0 KB");
			vo.setSourceEntity(new TemplatesrcEntity());
			if (vo.getParent().getId() == null) {
				vo.setParent(null);
			} else {
				getSourceVo().setParent(siteFac.getTemplateSource(vo.getParent().getId()));
			}
			Integer tplId = siteFac.addTemplateSource(vo);
			Struts2Util.renderJson(JSONUtil.toJSON(tplId));
		} catch (Exception e) {
			//outputInfo(e.toString());
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}

	/**
	 * 新建资源文件夹.
	 */
	public void addFolder() {
		try {
			TemplateSource vo = getSourceVo();
			vo.setSite(getCurrentSite());
			if (vo.getParent().getId() == null) {
				vo.setParent(null);
			} else {
				getSourceVo().setParent(siteFac.getTemplateSource(vo.getParent().getId()));
			}
			Integer tplId = siteFac.addTemplateSource(vo);
			Struts2Util.renderJson(JSONUtil.toJSON(tplId));
		} catch (Exception e) {
			//outputInfo(e.toString());
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}

	/**
	 * 重命名资源.
	 */
	public void renameSource() {
		try {
			String newName = getSourceVo().getName();
			TemplateSource vo = siteFac.getTemplateSource(getSourceVo().getId());
			vo.setName(newName);
			siteFac.updTemplateSource(vo);
			Struts2Util.renderJson(JSONUtil.toJSON("true"));
		} catch (Exception e) {
			//outputInfo(e.toString());
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}

	/**
	 * 删除资源.
	 */
	public void delSource() {
		try {
			siteFac.delTemplateSource(getSourceVo().getId());
			Struts2Util.renderJson(JSONUtil.toJSON("true"));
		} catch (Exception e) {
			//outputInfo(e.toString());
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}

	/**
	 * 移动资源.
	 */
	public void movetoSource() {
		try {
			TemplateSource vo = siteFac.getTemplateSource(getSourceVo().getId());
			if (getSourceVo().getParent().getId() == null) {
				vo.setParent(null);
			} else {
				vo.setParent(siteFac.getTemplateSource(getSourceVo().getParent().getId()));
			}
			siteFac.updTemplateSource(vo);
			Struts2Util.renderJson(JSONUtil.toJSON("true"));
		} catch (Exception e) {
			//outputInfo(e.toString());
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}

	/**
	 * 编辑资源.
	 */
	public String editSource() {
		if (getSourceVo() != null && getSourceVo().getId() != null) {
			TemplateSource vo = siteFac.getTemplateSource(getSourceVo().getId());
			try {
				setSourceContent(new String(vo.getSourceEntity().getSrcEntity(), "UTF-8"));
			} catch (Exception e) {
			}
			setSourceVo(vo);
		}
		return INPUT;
	}

	public void pubSource() {
		try {
		    webPublish.publishTemplateSources(new int[]{getSourceVo().getId()});
			Struts2Util.renderJson(JSONUtil.toJSON("true"));
		} catch (Exception e) {
			//outputInfo(e.toString());
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}

	public String saveInfo() {
		try {
			TemplateSource vo = siteFac.getTemplateSource(getSourceVo().getId());
			if (sourceFile != null) {
				TemplatesrcEntity tplEntityVo = new TemplatesrcEntity();
				vo.setSize(converKB(sourceFile.length()));
				byte[] buffer = new byte[Integer.parseInt(String.valueOf(sourceFile.length()))];
				InputStream in = new BufferedInputStream(new FileInputStream(sourceFile), Integer.parseInt(String
						.valueOf(sourceFile.length())));
				in.read(buffer);
				tplEntityVo.setSrcEntity(buffer);
				vo.setSourceEntity(tplEntityVo);
			}
			vo.setName(sourceFileFileName);
			vo.setDescribe(getSourceVo().getDescribe());
			siteFac.updTemplateSource(vo);
			addActionMessage("数据保存成功！");
		} catch (Exception e) {
			addActionMessage("数据保存失败！");
		}
		return INPUT;
	}

	public String saveContent() {
		try {
			TemplateSource vo = siteFac.getTemplateSource(getSourceVo().getId());
			TemplatesrcEntity tplEntityVo = new TemplatesrcEntity();
			tplEntityVo.setSrcEntity(getSourceContent().getBytes("UTF-8"));
			vo.setSourceEntity(tplEntityVo);
			vo.setRelease(false);
			siteFac.updTemplateSource(vo);
			addActionMessage("模板内容保存成功！");
		} catch (Exception e) {
			addActionMessage("模板内容保存失败！");
		}
		return INPUT;
	}

	private String converKB(long size) {
		DecimalFormat dfom = new DecimalFormat("####.0");
		if (size <= 0)
			return "0 KB";
		return String.valueOf(dfom.format(size / 1000)) + " KB";
	}
}
