/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.content.particular.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.content.particular.model.ApprovalRecord;

/**
 * 审批备案机关DAO
 * @author wuzhijun
 *
 */

@Repository
public class ApprovalRecordDAO extends JpaDAO<Long, ApprovalRecord> {
	
	public List<ApprovalRecord> findApprovalRecordAll(){
		String hql = "From ApprovalRecord As a Order By a.code";
		TypedQuery<ApprovalRecord> query = this.getEntityManager().createQuery(hql, ApprovalRecord.class);
		return query.getResultList();
	}
	
	public Boolean findApprovalRecordSelected(final Long projectBasicId, final String approvalRecordCode){
    	String hql = "Select r From ProjectBasic As p Inner Join p.approvalRecord As r Where p.id=:projectBasicId And r.code=:approvalRecordCode";

    	TypedQuery<ApprovalRecord> query = this.getEntityManager().createQuery(hql, ApprovalRecord.class);
    	query.setParameter("projectBasicId", projectBasicId);
    	query.setParameter("approvalRecordCode", approvalRecordCode);

    	List<ApprovalRecord> list = query.getResultList();
    	return list.isEmpty()? false : true;
    }

	public ApprovalRecord findApprovalRecordByCode(final String code){
		String hql = "From ApprovalRecord As a Where a.code=:code";
		TypedQuery<ApprovalRecord> query = this.getEntityManager().createQuery(hql, ApprovalRecord.class);
		query.setParameter("code", code);
		ApprovalRecord approvalRecord = null;
		try{
			approvalRecord = (ApprovalRecord)query.getSingleResult();
		}catch(NoResultException e){
		}
		return approvalRecord;
	}
}
