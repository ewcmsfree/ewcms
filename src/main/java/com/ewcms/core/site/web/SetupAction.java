/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/**
 * 
 */
package com.ewcms.core.site.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.ewcms.core.site.SiteFac;
import com.ewcms.core.site.model.Site;
import com.ewcms.web.CrudBaseAction;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;
import com.ewcms.web.vo.TreeNode;

/**
 * @author 周冬初
 * 
 */
public class SetupAction extends CrudBaseAction<Site, Integer> {
	private static final long serialVersionUID = 1L;
	@Autowired
	private SiteFac siteFac;
	private Integer id;
	
	public Site getSiteVo() {
		return super.getVo();
	}

	public void setSiteVo(Site siterVo) {
		super.setVo(siterVo);
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
	protected Integer getPK(Site vo) {
		return vo.getId();
	}

	@Override
	protected Site getOperator(Integer pk) {
		return siteFac.getSite(pk);
	}

	@Override
	protected void deleteOperator(Integer pk) {
		siteFac.delSite(pk);
	}

	@Override
	protected Integer saveOperator(Site vo, boolean isUpdate) {
		return null;
	}

	@Override
	protected Site createEmptyVo() {
		return new Site();
	}
	
	/**
	 * 获取站点树.
	 */
	public void siteTree() throws Exception {
		if (id == null) {
			TreeNode treeFile = new TreeNode();
			if(getSiteVo()!=null&&getSiteVo().getId()!=null){
				if(hasOrgan()){
					treeFile.setChildren(siteFac.getOrganSiteTreeList(getSiteVo().getId(),getSiteVo().getOrgan().getId()));
				}else{
					treeFile.setChildren(siteFac.getCustomerSiteTreeList(getSiteVo().getId()));
				}
				treeFile.setId(getSiteVo().getId().toString());
			}else{
				if(hasOrgan()){
					treeFile.setChildren(siteFac.getOrganSiteTreeList(getSiteVo().getOrgan().getId()));
				}else{
					treeFile.setChildren(siteFac.getCustomerSiteTreeList());
				}
			}
			treeFile.setText("机构站群管理");
			treeFile.setState("open");
			Struts2Util.renderJson(JSONUtil.toJSON(new TreeNode[] { treeFile }));
			return;
		}
		List<TreeNode> tnList;
		if(hasOrgan()){
			tnList = siteFac.getOrganSiteTreeList(id,getSiteVo().getOrgan().getId());
		}else{
			tnList = siteFac.getCustomerSiteTreeList(id);
		}		
		if (tnList.isEmpty()) {
			Struts2Util.renderJson("{}");
		} else {
			Struts2Util.renderJson(JSONUtil.toJSON(tnList));
		}
	}

	private boolean hasOrgan(){
		if(getSiteVo()==null || getSiteVo().getOrgan()==null||getSiteVo().getOrgan().getId()==null)return false;
		return true;
	}
	
	/**
	 * 创建机构站点.
	 */
	public void addSite() {
		try {
			Integer id = siteFac.addSite(getSiteVo().getId(), getSiteVo()
					.getSiteName(),getSiteVo().getOrgan().getId());
			Struts2Util.renderJson(JSONUtil.toJSON(id));
		} catch (Exception e) {
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}

	/**
	 * 删除站点.
	 */
	public void delSite() {
		try {
			siteFac.delSite(getSiteVo().getId());
			Struts2Util.renderJson(JSONUtil.toJSON("true"));
		} catch (Exception e) {
			outputInfo(e.toString());
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}

	/**
	 * 重命名站点.
	 */
	public void renameSite() {
		try {
			Site vo = siteFac.getSite(getSiteVo().getId());
			vo.setSiteName(getSiteVo().getSiteName());
			siteFac.updSite(vo);
			Struts2Util.renderJson(JSONUtil.toJSON("true"));
		} catch (Exception e) {
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}

	/**
	 * 移动专栏.
	 */
	public void movetoSite() {
		try {
			Site vo = siteFac.getSite(getSiteVo().getId());
			Site parentVo = siteFac.getSite(getSiteVo().getParent().getId());
			vo.setParent(parentVo);
			siteFac.updSite(vo);
			Struts2Util.renderJson(JSONUtil.toJSON("true"));
		} catch (Exception e) {
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
	
	/**
	 * 编辑专栏.
	 */
	public String editSite() {
		if (getSiteVo() != null && getSiteVo().getId() != null) {
			setSiteVo(siteFac.getSite(getSiteVo().getId()));
		}
		return INPUT;
	}

	/**
	 * 保存专栏.
	 */
	public String saveInfo() {
		try {
			Site vo = getSiteVo();
			Site oldVo = siteFac.getSite(vo.getId());
			vo.setOrgan(oldVo.getOrgan());
			vo.setParent(oldVo.getParent());
			siteFac.updSite(vo);
			addActionMessage("数据保存成功！");
		} catch (Exception e) {
			this.outputInfo(e.toString());
			addActionMessage("数据保存失败！");
		}
		setSiteVo(siteFac.getSite(vo.getId()));
		return INPUT;
	}
	
	/**
	 * 保存站点服务器信息.
	 */
	public String saveSiteServer() {
		try {
			Site vo = getSiteVo();
			siteFac.saveSiteServer(vo);
			addActionMessage("数据保存成功！");
		} catch (Exception e) {
			this.outputInfo(e.toString());
			addActionMessage("数据保存失败！");
		}
		return INPUT;
	}
}
