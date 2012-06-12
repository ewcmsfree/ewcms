/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/**
 * 
 */
package com.ewcms.core.site.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import com.ewcms.common.dao.JpaDAO;
import com.ewcms.core.site.model.Template;
import com.ewcms.core.site.model.TemplateType;

/**
 * @author 周冬初
 * @author wuzhijun
 */
@Repository
public class TemplateDAO extends JpaDAO<Integer, Template> {
	/**
	 * 获取站点专栏所有模板
	 * 
	 */
	public List<Template> getTemplateList(final Integer siteId){
		String hql = "From Template o Where o.site.id=:siteId and o.templateEntity is not null and o.channelId is not null Order By o.id";

		TypedQuery<Template> query = this.getEntityManager().createQuery(hql,Template.class);
		query.setParameter("siteId", siteId);

		return query.getResultList();
	}
	
	/**
	 * 获取站点子节点模板
	 * 
	 */
	public List<Template> getTemplateChildren(final Integer parentId,final Integer siteId,final Integer channelId){	
		String hql;
		TypedQuery<Template> query;
		if (parentId == null) {
			hql = "From Template o Where o.parent is null and o.site.id=:siteId Order By o.id";
			query = this.getEntityManager().createQuery(hql, Template.class);
			query.setParameter("siteId", siteId);
		} else {
			if (channelId == null) {
				hql = "From Template o Where o.parent.id=:parentId and o.site.id=:siteId Order By o.id";

				query = this.getEntityManager().createQuery(hql, Template.class);
				query.setParameter("parentId", parentId);
				query.setParameter("siteId", siteId);
			} else {
				hql = "From Template o Where o.parent.id=:parentId and o.site.id=:siteId and o.channelId=:channelId Order By o.id";

				query = this.getEntityManager().createQuery(hql, Template.class);
				query.setParameter("parentId", parentId);
				query.setParameter("siteId", siteId);
				query.setParameter("channelId", channelId);
			}
		}
		return query.getResultList();		
	}

	/**
	 * 获取站点专栏模板节点
	 * 
	 */
	public Template getChannelTemplate(final String siteTplName,final Integer siteId,final Integer parentId){
		String hql;
		TypedQuery<Template> query;
		if (parentId == null) {
			hql = "From Template o Where o.name=:siteTplName and o.site.id=:siteId and o.parent is null";

			query = this.getEntityManager().createQuery(hql, Template.class);
			query.setParameter("siteTplName", siteTplName);
			query.setParameter("siteId", siteId);
		} else {
			hql = "From Template o Where o.name=:siteTplName and o.site.id=:siteId and o.parent.id=:parentId";

			query = this.getEntityManager().createQuery(hql, Template.class);
			query.setParameter("siteTplName", siteTplName);
			query.setParameter("siteId", siteId);
			query.setParameter("parentId", parentId);
		}
		
		Template template = null;
		try{
			template = (Template) query.getSingleResult();
		}catch(NoResultException e){
		}
		return template;
	}
	
	/**
	 * 通过模板路径得到模板
	 * 
	 * @param path 模板路基
	 * @return 模板实体
	 */
	public Template getTemplateByPath(final String path){
		String hql = "From Template o Where o.uniquePath=:path";

		TypedQuery<Template> query = this.getEntityManager().createQuery(hql, Template.class);
		query.setParameter("path", path);

		Template template = null;
		try{
			template = (Template) query.getSingleResult();
		}catch(NoResultException e){
		}
		return template;
	}
	
	/**
	 * 获取专栏所有模板
	 * 
	 * @param channelId 专栏编号
	 * @return 模板集合
	 */
	public List<Template> getTemplatesInChannel(final Integer channelId){
		String hql = "From Template o Where o.channelId=:channelId order by o.type";
		TypedQuery<Template> query = this.getEntityManager().createQuery(hql, Template.class);
		query.setParameter("channelId", channelId);
		return query.getResultList();
	}
	
	public Template findTemplateByChannelIdAndTemplateType(final Integer channelId, final TemplateType templateType){
		String hql = "From Template t Where t.channelId=:channelId And t.type=:templateType";
		TypedQuery<Template> query = this.getEntityManager().createQuery(hql, Template.class);
		query.setParameter("channelId", channelId);
		query.setParameter("templateType", templateType);
		
		Template template = null;
		try{
			template = (Template) query.getSingleResult();
		}catch(NoResultException e){
		}
		return template;
	}
}
