package com.ewcms.content.particular.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.content.particular.model.EnterpriseBasic;

@Repository
public class EnterpriseBasicDAO extends JpaDAO<Long, EnterpriseBasic> {
	public List<EnterpriseBasic> findProjectBasicAll(){
		String hql = "From EnterpriseBasic As e Order By e.yyzzzch";
		TypedQuery<EnterpriseBasic> query = this.getEntityManager().createQuery(hql, EnterpriseBasic.class);
		return query.getResultList();
	}
	
	public EnterpriseBasic findEnterpriseBasicByYyzzzch(final String yyzzzch){
		String hql = "From EnterpriseBasic As p Where p.yyzzzch=:yyzzzch";
		TypedQuery<EnterpriseBasic> query = this.getEntityManager().createQuery(hql, EnterpriseBasic.class);
		query.setParameter("yyzzzch", yyzzzch);
		EnterpriseBasic enterpriseBasic = null;
		try{
			enterpriseBasic = (EnterpriseBasic) query.getSingleResult();
		}catch(NoResultException e){
		}
		return enterpriseBasic;
	}
}
