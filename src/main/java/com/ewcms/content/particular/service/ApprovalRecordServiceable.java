/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.content.particular.service;

import java.util.List;

import com.ewcms.content.particular.model.ApprovalRecord;

/**
 * 审批备案机关接口
 * @author wuzhijun
 *
 */
public interface ApprovalRecordServiceable {
	
	public Long addApprovalRecord(ApprovalRecord approvalRecord);
	
	public Long updApprovalRecord(ApprovalRecord approvalRecord);
	
	public void delApprovalRecord(Long id);
	
	public ApprovalRecord findApprovalRecordById(Long id);
	
	public List<ApprovalRecord> findApprovalRecordAll();
	
	public Boolean findApprovalRecordSelected(Long projectBasicId, String approvalRecordCode);
}
