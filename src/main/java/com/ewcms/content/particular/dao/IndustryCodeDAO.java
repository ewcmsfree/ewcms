package com.ewcms.content.particular.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.content.particular.model.IndustryCode;

@Repository
public class IndustryCodeDAO extends JpaDAO<Long, IndustryCode> {
	
	public List<IndustryCode> findIndustryCodeAll(){
		String hql = "From IndustryCode As a Order By a.code";
		TypedQuery<IndustryCode> query = this.getEntityManager().createQuery(hql, IndustryCode.class);
		return query.getResultList();
	}
	
	public Boolean findIndustryCodeSelected(final Long projectBasicId, final String industryCodeCode){
    	String hql = "Select r From ProjectBasic As p Inner Join p.industryCode As r Where p.id=:projectBasicId And r.code=:industryCodeCode";

    	TypedQuery<IndustryCode> query = this.getEntityManager().createQuery(hql, IndustryCode.class);
    	query.setParameter("projectBasicId", projectBasicId);
    	query.setParameter("industryCodeCode", industryCodeCode);

    	List<IndustryCode> list = query.getResultList();
    	return list.isEmpty()? false : true;
    }
	
	public IndustryCode findIndustryCodeByCode(final String code){
		String hql = "From IndustryCode As a Where a.code=:code";
		TypedQuery<IndustryCode> query = this.getEntityManager().createQuery(hql, IndustryCode.class);
		query.setParameter("code", code);
		IndustryCode industryCode = null;
		try{
			industryCode = (IndustryCode) query.getSingleResult();
		}catch(NoResultException e){
		}
		return industryCode;
	}
}
