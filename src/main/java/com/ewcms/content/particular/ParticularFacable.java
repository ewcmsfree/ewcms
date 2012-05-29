/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.content.particular;

import java.io.File;
import java.util.List;

import org.dom4j.Document;

import com.ewcms.content.particular.model.ApprovalRecord;
import com.ewcms.content.particular.model.EmployeArticle;
import com.ewcms.content.particular.model.EmployeBasic;
import com.ewcms.content.particular.model.EnterpriseArticle;
import com.ewcms.content.particular.model.EnterpriseBasic;
import com.ewcms.content.particular.model.IndustryCode;
import com.ewcms.content.particular.model.ProjectArticle;
import com.ewcms.content.particular.model.ProjectBasic;
import com.ewcms.content.particular.model.PublishingSector;
import com.ewcms.content.particular.model.ZoningCode;

public interface ParticularFacable {
	
	public Long addApprovalRecord(ApprovalRecord approvalRecord) throws BaseException;
	
	public Long updApprovalRecord(ApprovalRecord approvalRecord);
	
	public void delApprovalRecord(Long id);
	
	public ApprovalRecord findApprovalRecordById(Long id);
	
	public List<ApprovalRecord> findApprovalRecordAll();
	
	public Boolean findApprovalRecordSelected(Long projectBasicId, String approvalRecordCode);

	public Long addIndustryCode(IndustryCode industryCode) throws BaseException;
	
	public Long updIndustryCode(IndustryCode industryCode);
	
	public void delIndustryCode(Long id);
	
	public List<IndustryCode> findIndustryCodeAll();
	
	public Boolean findIndustryCodeSelected(Long projectBasicId, String industryCodeCode);

	public IndustryCode findIndustryCodeById(Long id);
	
	public Long addPublishingSector(PublishingSector publishingSector) throws BaseException;
	
	public Long updPublishingSector(PublishingSector publishingSector);
	
	public void delPublishingSector(Long id);
	
	public PublishingSector findPublishingSectorById(Long id);

	public List<PublishingSector> findPublishingSectorAll();
	
	public Boolean findPublishingSectorSelectedByPBId(Long projectBasicId, String publishingSectorCode);

	public Boolean findPublishingSectorSelectedByPAId(Long projectArticleId, String publishingSectorCode);

	public Boolean findPublishingSectorSelectedByEBId(Long enterpriseBasicId, String publishingSectorCode);
	
	public Boolean findPublishingSectorSelectedByEAId(final Long enterpriseArticleId, final String publishingSectorCode);

	public Boolean findPublishingSectorSelectedByMBId(Long employeBasicId, String publishingSectorCode);
	
	public Boolean findPublishingSectorSelectedByMAId(Long employeArticleId, String publishingSectorCode);

	public Long addZoningCode(ZoningCode zoningCode) throws BaseException;
	
	public Long updZoningCode(ZoningCode zoningCode);
	
	public void delZoningCode(Long id);
	
	public ZoningCode findZoningCodeById(Long id);

	public List<ZoningCode> findZoningCodeAll();
	
	public Boolean findZoningCodeSelected(Long projectBasicId, String zoningCodeCode);
	
	public Long addProjectBasic(ProjectBasic projectBasic);
	
	public Long updProjectBasic(ProjectBasic projectBasic);
	
	public void delProjectBasic(Long id);
	
	public ProjectBasic findProjectBasicById(Long id);

	public Long addProjectArticle(ProjectArticle projectArticle);
	
	public Long updProjectArticle(ProjectArticle projectArticle);
	
	public void delProjectArticle(Long id);
	
	public ProjectArticle findProjectArticleById(Long id);
	
	public List<ProjectBasic> findProjectBasicAll();
	
	public void addProjectBasicByImportXml(File file, Integer channelId);
	
	public Document exportXml(List<Long> projectBasicIds);

	public Long addEnterpriseBasic(EnterpriseBasic enterpriseBasic);
	
	public Long updEnterpriseBasic(EnterpriseBasic enterpriseBasic);
	
	public void delEnterpriseBasic(Long id);
	
	public EnterpriseBasic findEnterpriseBasicById(Long id);
	
	public List<EnterpriseBasic> findEnterpriseBasicAll();
	
	public Long addEnterpriseArticle(EnterpriseArticle enterpriseArticle);
	
	public Long updEnterpriseArticle(EnterpriseArticle enterpriseArticle);
	
	public void delEnterpriseArticle(Long id);
	
	public EnterpriseArticle findEnterpriseArticleById(Long id);
	
	public Long addEmployeBasic(EmployeBasic employeBasic);
	
	public Long updEmployeBasic(EmployeBasic employeBasic);
	
	public void delEmployeBasic(Long id);
	
	public EmployeBasic findEmployeBasicById(Long id);
	
	public List<EmployeBasic> findEmployeBasicAll();

	public Long addEmployeArticle(EmployeArticle employeArticle);
	
	public Long updEmployeArticle(EmployeArticle employeArticle);
	
	public void delEmployeArticle(Long id);
	
	public EmployeArticle findEmployeArticleById(Long id);
}
