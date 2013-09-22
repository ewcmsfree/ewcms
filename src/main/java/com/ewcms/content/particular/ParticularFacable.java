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
import com.ewcms.content.particular.model.ZoningCode;
import com.ewcms.core.site.model.Organ;

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
	
	public Long addZoningCode(ZoningCode zoningCode) throws BaseException;
	
	public Long updZoningCode(ZoningCode zoningCode);
	
	public void delZoningCode(Long id);
	
	public ZoningCode findZoningCodeById(Long id);

	public List<ZoningCode> findZoningCodeAll();
	
	public Boolean findZoningCodeSelected(Long projectBasicId, String zoningCodeCode);
	
	public Long addProjectBasic(ProjectBasic projectBasic) throws BaseException;
	
	public Long updProjectBasic(ProjectBasic projectBasic) throws BaseException;
	
	public void delProjectBasic(Long id);
	
	public ProjectBasic findProjectBasicById(Long id);

	public Long addProjectArticle(ProjectArticle projectArticle);
	
	public Long updProjectArticle(ProjectArticle projectArticle);
	
	public void delProjectArticle(Long id);
	
	public ProjectArticle findProjectArticleById(Long id);
	
	public List<ProjectBasic> findProjectBasicByPageAndRows(Integer page, Integer rows, String name);
	
	public Long findProjectBasicTotal(String name);
	
	public void addProjectBasicByImportXml(File file, Integer channelId, String fileType);
	
	public Document exportXml(List<Long> projectBasicIds);

	public Long addEnterpriseBasic(EnterpriseBasic enterpriseBasic);
	
	public Long updEnterpriseBasic(EnterpriseBasic enterpriseBasic);
	
	public void delEnterpriseBasic(Long id);
	
	public EnterpriseBasic findEnterpriseBasicById(Long id);
	
	public List<EnterpriseBasic> findEnterpriseBasicByPageAndRows(Integer page, Integer rows, String name);
	
	public Long findEnterpriseBasicTotal(String name);
	
	public Long addEnterpriseArticle(EnterpriseArticle enterpriseArticle);
	
	public Long updEnterpriseArticle(EnterpriseArticle enterpriseArticle);
	
	public void delEnterpriseArticle(Long id);
	
	public EnterpriseArticle findEnterpriseArticleById(Long id);
	
	public Long addEmployeBasic(EmployeBasic employeBasic);
	
	public Long updEmployeBasic(EmployeBasic employeBasic);
	
	public void delEmployeBasic(Long id);
	
	public EmployeBasic findEmployeBasicById(Long id);
	
	public List<EmployeBasic> findEmployeBasicByPageAndRows(Integer page, Integer rows, String name);
	
	public Long findEmployeBasicTotal(String name);

	public Long addEmployeArticle(EmployeArticle employeArticle);
	
	public Long updEmployeArticle(EmployeArticle employeArticle);
	
	public void delEmployeArticle(Long id);
	
	public EmployeArticle findEmployeArticleById(Long id);
	
	public void pubProjectBasic(Integer channelId, List<Long> projectBasicIds);
	
	public void unPubProjectBasic(Integer channelId, List<Long> projectBasicIds);

	public void pubProjectArticle(Integer channelId, List<Long> projectArticleIds);
	
	public void unPubProjectArticle(Integer channelId, List<Long> projectArticleIds);

	public void pubEnterpriseBasic(Integer channelId, List<Long> enterpriseBasicIds);
	
	public void unPubEnterpriseBasic(Integer channelId, List<Long> enterpriseBasicIds);

	public void pubEnterpriseArticle(Integer channelId, List<Long> enterpriseArticleIds);
	
	public void unPubEnterpriseArticle(Integer channelId, List<Long> enterpriseArticleIds);

	public void pubEmployeBasic(Integer channelId, List<Long> employeBasicIds);
	
	public void unPubEmployeBasic(Integer channelId, List<Long> employeBasicIds);

	public void pubEmployeArticle(Integer channelId, List<Long> employeArticleIds);
	
	public void unPubEmployeArticle(Integer channelId, List<Long> employeArticleIds);
	
	public Organ findOrganByUserName();
	
	public List<ProjectArticle> findProjectArticleAll();
	
	public List<EmployeArticle> findEmployeArticleAll();
	
	public List<EnterpriseArticle> findEnterpriseArticleAll();
}
