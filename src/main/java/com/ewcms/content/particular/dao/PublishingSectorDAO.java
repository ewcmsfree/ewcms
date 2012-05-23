package com.ewcms.content.particular.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.content.particular.model.PublishingSector;

@Repository
public class PublishingSectorDAO extends JpaDAO<Long, PublishingSector> {
	
	public List<PublishingSector> findPublishingSectorAll(){
		String hql = "From PublishingSector As a Order By a.code";
		TypedQuery<PublishingSector> query = this.getEntityManager().createQuery(hql, PublishingSector.class);
		return query.getResultList();
	}
	
	public Boolean findPublishingSectorSelectedByPBId(final Long projectBasicId, final String publishingSectorCode){
    	String hql = "Select r From ProjectBasic As p Inner Join p.publishingSector As r Where p.id=:projectBasicId And r.code=:publishingSectorCode";

    	TypedQuery<PublishingSector> query = this.getEntityManager().createQuery(hql, PublishingSector.class);
    	query.setParameter("projectBasicId", projectBasicId);
    	query.setParameter("publishingSectorCode", publishingSectorCode);

    	List<PublishingSector> list = query.getResultList();
    	return list.isEmpty()? false : true;
    }
	
	public Boolean findPublishingSectorSelectedByPAId(final Long projectArticleId, final String publishingSectorCode){
    	String hql = "Select r From ProjectArticle As p Inner Join p.publishingSector As r Where p.id=:projectArticleId And r.code=:publishingSectorCode";

    	TypedQuery<PublishingSector> query = this.getEntityManager().createQuery(hql, PublishingSector.class);
    	query.setParameter("projectArticleId", projectArticleId);
    	query.setParameter("publishingSectorCode", publishingSectorCode);

    	List<PublishingSector> list = query.getResultList();
    	return list.isEmpty()? false : true;
	}
	
	public Boolean findPublishingSectorSelectedByEBId(final Long enterpriseBasicId, final String publishingSectorCode){
    	String hql = "Select r From EnterpriseBasic As p Inner Join p.publishingSector As r Where p.id=:enterpriseBasicId And r.code=:publishingSectorCode";

    	TypedQuery<PublishingSector> query = this.getEntityManager().createQuery(hql, PublishingSector.class);
    	query.setParameter("enterpriseBasicId", enterpriseBasicId);
    	query.setParameter("publishingSectorCode", publishingSectorCode);

    	List<PublishingSector> list = query.getResultList();
    	return list.isEmpty()? false : true;
	}
	
	public Boolean findPublishingSectorSelectedByEAId(final Long enterpriseArticleId, final String publishingSectorCode){
    	String hql = "Select r From EnterpriseArticle As p Inner Join p.publishingSector As r Where p.id=:enterpriseArticleId And r.code=:publishingSectorCode";

    	TypedQuery<PublishingSector> query = this.getEntityManager().createQuery(hql, PublishingSector.class);
    	query.setParameter("enterpriseArticleId", enterpriseArticleId);
    	query.setParameter("publishingSectorCode", publishingSectorCode);

    	List<PublishingSector> list = query.getResultList();
    	return list.isEmpty()? false : true;
	}

	public Boolean findPublishingSectorSelectedByMBId(final Long employeBasicId, final String publishingSectorCode){
    	String hql = "Select r From EmployeBasic As p Inner Join p.publishingSector As r Where p.id=:employeArticleId And r.code=:publishingSectorCode";

    	TypedQuery<PublishingSector> query = this.getEntityManager().createQuery(hql, PublishingSector.class);
    	query.setParameter("employeBasicId", employeBasicId);
    	query.setParameter("publishingSectorCode", publishingSectorCode);

    	List<PublishingSector> list = query.getResultList();
    	return list.isEmpty()? false : true;
	}
	
	public Boolean findPublishingSectorSelectedByMAId(final Long employeArticleId, final String publishingSectorCode){
    	String hql = "Select r From EmployeArticle As p Inner Join p.publishingSector As r Where p.id=:employeArticleId And r.code=:publishingSectorCode";

    	TypedQuery<PublishingSector> query = this.getEntityManager().createQuery(hql, PublishingSector.class);
    	query.setParameter("employeArticleId", employeArticleId);
    	query.setParameter("publishingSectorCode", publishingSectorCode);

    	List<PublishingSector> list = query.getResultList();
    	return list.isEmpty()? false : true;
	}	
	
	public PublishingSector findPublishingSectorByCode(final String code){
		String hql = "From PublishingSector As a Where a.code=:code";
		TypedQuery<PublishingSector> query = this.getEntityManager().createQuery(hql, PublishingSector.class);
		query.setParameter("code", code);
		PublishingSector publishingSector = null;
		try{
			publishingSector = (PublishingSector)query.getSingleResult();
		}catch(NoResultException e){
		}
		return publishingSector;
	}
}
