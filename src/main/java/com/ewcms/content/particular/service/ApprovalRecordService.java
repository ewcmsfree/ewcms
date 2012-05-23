package com.ewcms.content.particular.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.content.particular.dao.ApprovalRecordDAO;
import com.ewcms.content.particular.model.ApprovalRecord;

@Service
public class ApprovalRecordService implements ApprovalRecordServiceable {

	@Autowired
	private ApprovalRecordDAO approvalRecordDAO;
	
	@Override
	public Long addApprovalRecord(ApprovalRecord approvalRecord){
		approvalRecordDAO.persist(approvalRecord);
		return approvalRecord.getId();
	}

	@Override
	public Long updApprovalRecord(ApprovalRecord approvalRecord){
		approvalRecordDAO.merge(approvalRecord);
		return approvalRecord.getId();
	}

	@Override
	public void delApprovalRecord(Long id) {
		approvalRecordDAO.removeByPK(id);
	}

	@Override
	public ApprovalRecord findApprovalRecordById(Long id){
		return approvalRecordDAO.get(id);
	}

	@Override
	public List<ApprovalRecord> findApprovalRecordAll() {
		return approvalRecordDAO.findApprovalRecordAll();
	}

	@Override
	public Boolean findApprovalRecordSelected(Long projectBasicId, String approvalRecordCode) {
		return approvalRecordDAO.findApprovalRecordSelected(projectBasicId, approvalRecordCode);
	}

}
