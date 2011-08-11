/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/**
 * 
 */
package com.ewcms.core.site.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.core.site.dao.OrganDAO;
import com.ewcms.core.site.model.Organ;
import com.ewcms.web.vo.TreeNode;

/**
 * @author 周冬初
 * 
 */
@Service
public class OrganService {
	@Autowired
	private OrganDAO organDao;
	public Integer addOrgan(Integer parentId, String organName) {
		Organ vo = new Organ();
		if(parentId != null)
		vo.setParent(organDao.get(parentId));
		vo.setName(organName);
		organDao.persist(vo);
		return vo.getId();
	}

	public Integer updOrgan(Organ vo) {
		organDao.merge(vo);
		return vo.getId();
	}

	public void delOrgan(Integer id) {
		organDao.removeByPK(id);
	}

	public Organ getOrgan(Integer id) {
		return organDao.get(id);
	}

	public Integer saveOrganInfo(Organ vo) {
		Organ oldvo = getOrgan(vo.getId());
		oldvo.setOrganInfo(vo.getOrganInfo());
		updOrgan(oldvo);
		return oldvo.getOrganInfo().getId();
	}
	/**
	 * 获取跟机构集
	 * 
	 */ 	
	public List<TreeNode> getOrganTreeList(){
		return getOrganChildren(null);
	}
	/**
	 * 获取机构子机构集
	 * 
	 */ 	
	public List<TreeNode> getOrganTreeList(Integer parentId){
		return getOrganChildren(parentId);
	}	
	private List<TreeNode> getOrganChildren(Integer parentId) {
		List<TreeNode> tnList = new ArrayList<TreeNode>();
		List<Organ> organList = organDao.getOrganChildren(parentId);
		for (Organ vo : organList) {
			TreeNode tnVo = new TreeNode();
			tnVo.setId(vo.getId().toString());
			tnVo.setText(vo.getName());
			if (vo.hasChildren()) {
				tnVo.setState("closed");
			} else {
				tnVo.setState("open");
			}

			tnList.add(tnVo);
		}
		return tnList;
	}
}
