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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.stereotype.Repository;
import com.ewcms.common.dao.JpaDAO;
import com.ewcms.core.site.model.Template;

/**
 * @author 周冬初
 *
 */
@Repository
public class TemplateDAO extends JpaDAO<Integer, Template> {
	/**
	 * 获取站点专栏所有模板
	 * 
	 */
	public List<Template> getTemplateList(final Integer siteId){
		@SuppressWarnings("unchecked")
		Object res = this.getJpaTemplate().execute(new JpaCallback() {
            @Override
            public Object doInJpa(EntityManager em) throws PersistenceException {
            	String hql = "From Template o Where  o.site.id=? and o.templateEntity is not null and o.channelId is not null Order By o.id";
                Query query = em.createQuery(hql);
                query.setParameter(1,siteId);
                return query.getResultList();
            }
        });
		return (List<Template>) res;
	}
	
	/**
	 * 获取站点子节点模板
	 * 
	 */
	public List<Template> getTemplateChildren(final Integer parentId,final Integer siteId,final Integer channelId){	
		@SuppressWarnings("unchecked")
		Object res = this.getJpaTemplate().execute(new JpaCallback() {
            @Override
            public Object doInJpa(EntityManager em) throws PersistenceException {
            	String hql;
            	Query query;
        		if(parentId==null){
        			hql = "From Template o Where o.parent is null and o.site.id=? Order By o.id"; 
                	query = em.createQuery(hql);
                    query.setParameter(1,siteId);
        		}else{
        			if(channelId==null){
	        			hql = "From Template o Where o.parent.id=? and o.site.id=? Order By o.id";  
	                	query = em.createQuery(hql);
	                	query.setParameter(1,parentId);
	                    query.setParameter(2,siteId);
        			}else{
	        			hql = "From Template o Where o.parent.id=? and o.site.id=? and o.channelId=? Order By o.id";  
	                	query = em.createQuery(hql);
	                	query.setParameter(1,parentId);
	                    query.setParameter(2,siteId);     
	                    query.setParameter(3,channelId); 
        			}
        		} 
                return query.getResultList();
            }
        });
		return (List<Template>) res;		
	}

	/**
	 * 获取站点专栏模板节点
	 * 
	 */
	public Template getChannelTemplate(final String siteTplName,final Integer siteId,final Integer parentId){
		@SuppressWarnings("unchecked")
		Object res = this.getJpaTemplate().execute(new JpaCallback() {
            @Override
            public Object doInJpa(EntityManager em) throws PersistenceException {
            	String hql;
            	Query query;
            	if(parentId==null){
            		hql = "From Template o Where  o.name=? and o.site.id=? and o.parent is null";
                    query = em.createQuery(hql);
                    query.setParameter(1,siteTplName);
                    query.setParameter(2,siteId);
            	}else{
            		hql = "From Template o Where  o.name=? and o.site.id=? and o.parent.id=?";
                    query = em.createQuery(hql);
                    query.setParameter(1,siteTplName);
                    query.setParameter(2,siteId);
                    query.setParameter(3,parentId);
            	}
                return query.getResultList();
            }
        });
		List<Template> templateList = (List<Template>) res;
		if(templateList==null||templateList.size()==0)return null;
		return templateList.get(0);
	}
	
	/**
	 * 通过模板路径得到模板
	 * 
	 * @param path 模板路基
	 * @return 模板实体
	 */
	public Template getTemplateByPath(final String path){
	    List<Template> res = this.getJpaTemplate().execute(new JpaCallback<List<Template>>() {
            @Override
            public List<Template> doInJpa(EntityManager em) throws PersistenceException {
                String hql = "From Template o Where  o.uniquePath=?";
                TypedQuery<Template> query = em.createQuery(hql, Template.class);
                query.setParameter(1,path);
                
                return query.getResultList();
            }
        });
	    
	    return res.isEmpty() ? null : res.get(0);
	}
}
