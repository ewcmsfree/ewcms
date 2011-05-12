/**
 * 
 */
package com.ewcms.core.site.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ewcms.comm.jpa.dao.JpaDAO;
import com.ewcms.core.site.model.Organ;
import com.ewcms.core.site.model.Template;

/**
 * @author 周冬初
 *
 */
@Repository
public class OrganDAO extends JpaDAO<Integer,Organ> {
	/**
	 * 获取子节点模板
	 * 
	 */
	public List<Organ> getOrganChildren(Integer parentId){
		String hql;
		if(parentId==null){
			hql = "From Organ o Where o.parent is null Order By o.id";
		}else{
			hql = "From Organ o Where  o.parent.id = " + parentId + " Order By o.id";
		}
		return getJpaTemplate().find(hql);
	}	
}
