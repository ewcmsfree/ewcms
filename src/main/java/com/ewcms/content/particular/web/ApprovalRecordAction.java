/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.particular.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.content.particular.BaseException;
import com.ewcms.content.particular.ParticularFacable;
import com.ewcms.content.particular.model.ApprovalRecord;
import com.ewcms.web.CrudBaseAction;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;
import com.ewcms.web.vo.ComboBoxString;

/**
 * @author 吴智俊
 */
public class ApprovalRecordAction extends CrudBaseAction<ApprovalRecord, Long> {

	private static final long serialVersionUID = -7215016049247026935L;

	@Autowired
	private ParticularFacable particularFac;

	public ApprovalRecord getApprovalRecordVo() {
		return super.getVo();
	}

	public void setApprovalRecordVo(ApprovalRecord approvalRecordVo) {
		super.setVo(approvalRecordVo);
	}

	public List<Long> getSelections() {
		return super.getOperatorPK();
	}

	public void setSelections(List<Long> selections) {
		super.setOperatorPK(selections);
	}

	@Override
	protected Long getPK(ApprovalRecord vo) {
		return vo.getId();
	}

	@Override
	protected ApprovalRecord getOperator(Long pk) {
		return particularFac.findApprovalRecordById(pk);
	}

	@Override
	protected void deleteOperator(Long pk) {
		particularFac.delApprovalRecord(pk);
	}

	@Override
	protected Long saveOperator(ApprovalRecord vo, boolean isUpdate) {
		if (isUpdate) {
			return particularFac.updApprovalRecord(vo);
		} else {
			try {
				return particularFac.addApprovalRecord(vo);
			} catch (BaseException e) {
				addActionMessage(e.getPageMessage());
				return null;
			}
		}
	}

	@Override
	protected ApprovalRecord createEmptyVo() {
		return new ApprovalRecord();
	}

	private Long projectBasicId;

	public Long getProjectBasicId() {
		return projectBasicId;
	}

	public void setProjectBasicId(Long projectBasicId) {
		this.projectBasicId = projectBasicId;
	}

	public void findApprovalRecordAll() {
		List<ApprovalRecord> approvalRecords = particularFac.findApprovalRecordAll();
		if (approvalRecords != null){
			List<ComboBoxString> comboBoxStrings = new ArrayList<ComboBoxString>();
			ComboBoxString comboBox = null;
			for (ApprovalRecord approvalRecord : approvalRecords){
				comboBox = new ComboBoxString();
				comboBox.setId(approvalRecord.getCode());
				comboBox.setText(approvalRecord.getName());
				if (getProjectBasicId() != null){
					Boolean isEntity = particularFac.findApprovalRecordSelected(getProjectBasicId(), approvalRecord.getCode());
					if (isEntity) comboBox.setSelected(true);
				}
				comboBoxStrings.add(comboBox);
			}
			Struts2Util.renderJson(JSONUtil.toJSON(comboBoxStrings.toArray(new ComboBoxString[0])));
		}
	}
}