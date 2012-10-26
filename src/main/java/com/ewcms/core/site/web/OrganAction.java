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
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.core.site.SiteFacable;
import com.ewcms.core.site.model.Organ;
import com.ewcms.core.site.model.Site;
import com.ewcms.web.CrudBaseAction;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;
import com.ewcms.web.vo.TreeNode;

/**
 * @author 周冬初
 * 
 */
public class OrganAction extends CrudBaseAction<Organ, Integer> {

	private static final long serialVersionUID = -8775454105957516242L;
	
	@Autowired
	private SiteFacable siteFac;
	private File iconFile;
	private Integer id;

	public Organ getOrganVo() {
		return super.getVo();
	}

	public void setOrganVo(Organ organVo) {
		super.setVo(organVo);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Site getSiteVo() {
		if (getOrganVo().getHomeSiteId() != null) {
			return siteFac.getSite(getOrganVo().getHomeSiteId());
		}
		return new Site();
	}

	public File getIconFile() {
		return iconFile;
	}

	public void setIconFile(File iconFile) {
		this.iconFile = iconFile;
	}

	@Override
	protected Integer getPK(Organ vo) {
		return vo.getId();
	}

	@Override
	protected Organ getOperator(Integer pk) {
		return siteFac.getOrgan(pk);
	}

	@Override
	protected void deleteOperator(Integer pk) {
	}

	@Override
	protected Integer saveOperator(Organ vo, boolean isUpdate) {
		return null;
	}

	@Override
	protected Organ createEmptyVo() {
		return new Organ();
	}

	/**
	 * 获取机构树.
	 */
	public void organTree() throws Exception {
		if (id == null) {
			TreeNode treeFile = new TreeNode();
			treeFile.setText("客户机构");
			treeFile.setState("open");
			treeFile.setChildren(siteFac.getOrganTreeList());
			Struts2Util
					.renderJson(JSONUtil.toJSON(new TreeNode[] { treeFile }));
			return;
		}
		List<TreeNode> tnList = siteFac.getOrganTreeList(id);
		if (tnList.isEmpty()) {
			Struts2Util.renderJson("{}");
		} else {
			Struts2Util.renderJson(JSONUtil.toJSON(tnList));
		}
	}
	
	/**
	 * 创建机构.
	 */
	public void addOrgan() {
		try {
			Integer id = siteFac.addOrgan(vo.getId(), vo.getName());
			Struts2Util.renderJson(JSONUtil.toJSON(id));
		} catch (Exception e) {
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}

	/**
	 * 设置机构主站.
	 */
	public void setSite() {
		try {
			Organ vo = siteFac.getOrgan(getOrganVo().getId());
			if (vo.getHomeSiteId() != null)
				siteFac.updSiteParent(vo.getId(), vo.getHomeSiteId(),
						getOrganVo().getHomeSiteId());
			vo.setHomeSiteId(getOrganVo().getHomeSiteId());
			siteFac.updOrgan(vo);
			Struts2Util.renderJson(JSONUtil.toJSON("true"));
		} catch (Exception e) {
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}

	/**
	 * 删除机构.
	 */
	public void delOrgan() {
		try {
			siteFac.delOrgan(vo.getId());
			Struts2Util.renderJson(JSONUtil.toJSON("true"));
		} catch (Exception e) {
			//outputInfo(e.toString());
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}

	/**
	 * 重命名机构.
	 */
	public void renameOrgan() {
		try {
			Organ vo = siteFac.getOrgan(getOrganVo().getId());
			vo.setName(getOrganVo().getName());
			siteFac.updOrgan(vo);
			Struts2Util.renderJson(JSONUtil.toJSON("true"));
		} catch (Exception e) {
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}

	/**
	 * 移动机构.
	 */
	public void movetoOrgan() {
		try {
			Organ vo = siteFac.getOrgan(getOrganVo().getId());
			if (getOrganVo().getParent().getId() == null) {
				vo.setParent(null);
			} else {
				vo.setParent(siteFac
						.getOrgan(getOrganVo().getParent().getId()));
			}
			siteFac.updOrgan(vo);
			//vo.getHomeSiteId()
			Struts2Util.renderJson(JSONUtil.toJSON("true"));
		} catch (Exception e) {
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}

	/**
	 * 编辑机构.
	 */
	public String editOrgan() {
		if (getOrganVo() != null && getOrganVo().getId() != null) {
			setOrganVo(siteFac.getOrgan(getOrganVo().getId()));
		}
		return INPUT;
	}

	public String saveInfo() {
		try {
			Organ vo = siteFac.getOrgan(getOrganVo().getId());
			vo.setUpdateTime(getOrganVo().getUpdateTime());
			vo.setDescripe(getOrganVo().getDescripe());
			if (iconFile != null) {
				InputStream in = null;
				try {
					byte[] buffer = new byte[Integer.parseInt(String
							.valueOf(iconFile.length()))];
					in = new BufferedInputStream(
							new FileInputStream(iconFile),
							Integer.parseInt(String.valueOf(iconFile.length())));
					in.read(buffer);
					vo.setIcon(buffer);
					in.close();
				} catch (Exception e) {
				} finally {
					try{
						if (in != null){
							in.close();
							in = null;
						}
					} catch (IOException e){}
				}
			}
			siteFac.updOrgan(vo);
			addActionMessage("数据保存成功！");
		} catch (Exception e) {
			addActionMessage("数据保存失败！");
		}
		return INPUT;
	}

	public String saveIntroduce() {
		try {
			siteFac.saveOrganInfo(getOrganVo());
			addActionMessage("数据保存成功！");
		} catch (Exception e) {
			addActionMessage("数据保存失败！");
		}
		return INPUT;
	}
}
