package com.ewcms.content.particular.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.content.particular.model.EmployeBasic;

@Repository
public class EmployeBasicDAO extends JpaDAO<Long, EmployeBasic> {
	public List<EmployeBasic> findProjectBasicAll(){
		String hql = "From EmployeBasic As e Order By e.cardCode";
		TypedQuery<EmployeBasic> query = this.getEntityManager().createQuery(hql, EmployeBasic.class);
		return query.getResultList();
	}
	
	public EmployeBasic findEmployeBasicByCardCode(final String cardCode){
		String hql = "From EmployeBasic As p Where p.cardCode=:cardCode";
		TypedQuery<EmployeBasic> query = this.getEntityManager().createQuery(hql, EmployeBasic.class);
		query.setParameter("cardCode", cardCode);
		EmployeBasic employeBasic = null;
		try{
			employeBasic = (EmployeBasic) query.getSingleResult();
		}catch(NoResultException e){
		}
		return employeBasic;
	}
}
