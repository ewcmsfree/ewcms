/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.content.particular;

import java.io.File;
import java.util.List;

import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

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
import com.ewcms.content.particular.service.ApprovalRecordServiceable;
import com.ewcms.content.particular.service.EmployeArticleServiceable;
import com.ewcms.content.particular.service.EmployeBasicServiceable;
import com.ewcms.content.particular.service.EnterpriseArticleServiceable;
import com.ewcms.content.particular.service.EnterpriseBasicServiceable;
import com.ewcms.content.particular.service.IndustryCodeServiceable;
import com.ewcms.content.particular.service.ProjectArticleServiceable;
import com.ewcms.content.particular.service.ProjectBasicServiceable;
import com.ewcms.content.particular.service.PublishingSectorServiceable;
import com.ewcms.content.particular.service.ZoningCodeServiceable;

@Service
public class ParticularFac implements ParticularFacable {

	@Autowired
	private ApprovalRecordServiceable approvalRecordService;
	@Autowired
	private IndustryCodeServiceable industryCodeService;
	@Autowired
	private PublishingSectorServiceable publishingSectorService;
	@Autowired
	private ZoningCodeServiceable zoningCodeService;
	@Autowired
	private ProjectBasicServiceable projectBasicService;
	@Autowired
	private ProjectArticleServiceable projectArticleService;
	@Autowired
	private EnterpriseBasicServiceable enterpriseBasicService;
	@Autowired
	private EnterpriseArticleServiceable enterpriseArticleService;
	@Autowired
	private EmployeBasicServiceable employeBasicService;
	@Autowired
	private EmployeArticleServiceable employeArticleService;
	
	@Override
	public Long addApprovalRecord(ApprovalRecord approvalRecord) throws BaseException{
		return approvalRecordService.addApprovalRecord(approvalRecord);
	}

	@Override
	public Long updApprovalRecord(ApprovalRecord approvalRecord){
		return approvalRecordService.updApprovalRecord(approvalRecord);
	}

	@Override
	public void delApprovalRecord(Long id){
		approvalRecordService.delApprovalRecord(id);
	}

	@Override
	public ApprovalRecord findApprovalRecordById(Long id){
		return approvalRecordService.findApprovalRecordById(id);
	}

	@Override
	public List<ApprovalRecord> findApprovalRecordAll(){
		return approvalRecordService.findApprovalRecordAll();
	}
	
	@Override
	public Boolean findApprovalRecordSelected(Long projectBasicId, String approvalRecordCode){
		return approvalRecordService.findApprovalRecordSelected(projectBasicId, approvalRecordCode);
	}

	@Override
	public Long addIndustryCode(IndustryCode industryCode) throws BaseException {
		return industryCodeService.addIndustryCode(industryCode);
	}

	@Override
	public Long updIndustryCode(IndustryCode industryCode) {
		return industryCodeService.updIndustryCode(industryCode);
	}

	@Override
	public void delIndustryCode(Long id) {
		industryCodeService.delIndustryCode(id);
	}

	@Override
	public IndustryCode findIndustryCodeById(Long id) {
		return industryCodeService.findIndustryCodeById(id);
	}

	@Override
	public List<IndustryCode> findIndustryCodeAll() {
		return industryCodeService.findIndustryCodeAll();
	}

	@Override
	public Boolean findIndustryCodeSelected(Long projectBasicId,
			String industryCodeCode) {
		return industryCodeService.findIndustryCodeSelected(projectBasicId, industryCodeCode);
	}

	@Override
	public Long addPublishingSector(PublishingSector publishingSector) throws BaseException {
		return publishingSectorService.addPublishingSector(publishingSector);
	}

	@Override
	public Long updPublishingSector(PublishingSector publishingSector) {
		return publishingSectorService.updPublishingSector(publishingSector);
	}

	@Override
	public void delPublishingSector(Long id) {
		publishingSectorService.delPublishingSector(id);
	}

	@Override
	public PublishingSector findPublishingSectorById(Long id) {
		return publishingSectorService.findPublishingSectorById(id);
	}

	@Override
	public List<PublishingSector> findPublishingSectorAll() {
		return publishingSectorService.findPublishingSectorAll();
	}

	@Override
	public Boolean findPublishingSectorSelectedByPBId(Long projectBasicId,
			String publishingSectorCode) {
		return publishingSectorService.findPublishingSectorSelectedByPBId(projectBasicId, publishingSectorCode);
	}
	
	@Override
	public Boolean findPublishingSectorSelectedByPAId(Long projectArticleId, String publishingSectorCode){
		return publishingSectorService.findPublishingSectorSelectedByPAId(projectArticleId, publishingSectorCode);
	}

	@Override
	public Boolean findPublishingSectorSelectedByEBId(Long enterpriseBasicId, String publishingSectorCode){
		return publishingSectorService.findPublishingSectorSelectedByEBId(enterpriseBasicId, publishingSectorCode);
	}
	
	@Override
	public Boolean findPublishingSectorSelectedByEAId(final Long enterpriseArticleId, final String publishingSectorCode){
		return publishingSectorService.findPublishingSectorSelectedByEAId(enterpriseArticleId, publishingSectorCode);
	}
	
	@Override
	public Boolean findPublishingSectorSelectedByMBId(Long employeBasicId, String publishingSectorCode){
		return publishingSectorService.findPublishingSectorSelectedByMBId(employeBasicId, publishingSectorCode);
	}
	
	@Override
	public Boolean findPublishingSectorSelectedByMAId(Long employeArticleId, String publishingSectorCode){
		return publishingSectorService.findPublishingSectorSelectedByMAId(employeArticleId, publishingSectorCode);
	}
	
	@Override
	public Long addZoningCode(ZoningCode zoningCode) throws BaseException {
		return zoningCodeService.addZoningCode(zoningCode);
	}

	@Override
	public Long updZoningCode(ZoningCode zoningCode) {
		return zoningCodeService.updZoningCode(zoningCode);
	}

	@Override
	public void delZoningCode(Long id) {
		zoningCodeService.delZoningCode(id);
	}

	@Override
	public ZoningCode findZoningCodeById(Long id) {
		return zoningCodeService.findZoningCodeById(id);
	}

	@Override
	public List<ZoningCode> findZoningCodeAll() {
		return zoningCodeService.findZoningCodeAll();
	}

	@Override
	public Boolean findZoningCodeSelected(Long projectBasicId,
			String zoningCodeCode) {
		return zoningCodeService.findZoningCodeSelected(projectBasicId, zoningCodeCode);
	}

	@Override
	public Long addProjectBasic(ProjectBasic projectBasic) throws BaseException {
		return projectBasicService.addProjectBasic(projectBasic);
	}

	@Override
	public Long updProjectBasic(ProjectBasic projectBasic) throws BaseException {
		return projectBasicService.updProjectBasic(projectBasic);
	}

	@Override
	public void delProjectBasic(Long id) {
		projectBasicService.delProjectBasic(id);
	}

	@Override
	public ProjectBasic findProjectBasicById(Long id) {
		return projectBasicService.findProjectBasicById(id);
	}

	@Override
	public void addProjectBasicByImportXml(File file, Integer channelId){
		projectBasicService.addProjectBasicByImportXml(file, channelId);
	}

	@Override
	public Document exportXml(List<Long> projectBasicIds){
		return projectBasicService.exportXml(projectBasicIds);
	}
	
	@Override
	public List<ProjectBasic> findProjectBasicAll(){
		return projectBasicService.findProjectBasicAll();
	}
	
	@Override
	public Long addProjectArticle(ProjectArticle projectArticle) {
		return projectArticleService.addProjectArticle(projectArticle);
	}

	@Override
	public Long updProjectArticle(ProjectArticle projectArticle) {
		return projectArticleService.updProjectArticle(projectArticle);
	}

	@Override
	public void delProjectArticle(Long id) {
		projectArticleService.delProjectArticle(id);
	}

	@Override
	public ProjectArticle findProjectArticleById(Long id) {
		return projectArticleService.findProjectArticleById(id);
	}

	@Override
	public Long addEnterpriseBasic(EnterpriseBasic enterpriseBasic) {
		return enterpriseBasicService.addEnterpriseBasic(enterpriseBasic);
	}

	@Override
	public Long updEnterpriseBasic(EnterpriseBasic enterpriseBasic) {
		return enterpriseBasicService.updEnterpriseBasic(enterpriseBasic);
	}

	@Override
	public void delEnterpriseBasic(Long id) {
		enterpriseBasicService.delEnterpriseBasic(id);
	}

	@Override
	public EnterpriseBasic findEnterpriseBasicById(Long id) {
		return enterpriseBasicService.findEnterpriseBasicById(id);
	}
	
	@Override
	public List<EnterpriseBasic> findEnterpriseBasicAll(){
		return enterpriseBasicService.findEnterpriseBasicAll();
	}
	
	@Override
	public Long addEnterpriseArticle(EnterpriseArticle enterpriseArticle) {
		return enterpriseArticleService.addEnterpriseArticle(enterpriseArticle);
	}

	@Override
	public Long updEnterpriseArticle(EnterpriseArticle enterpriseArticle) {
		return enterpriseArticleService.updEnterpriseArticle(enterpriseArticle);
	}

	@Override
	public void delEnterpriseArticle(Long id) {
		enterpriseArticleService.delEnterpriseArticle(id);
	}

	@Override
	public EnterpriseArticle findEnterpriseArticleById(Long id) {
		return enterpriseArticleService.findEnterpriseArticleById(id);
	}

	@Override
	public Long addEmployeBasic(EmployeBasic employeBasic) {
		return employeBasicService.addEmployeBasic(employeBasic);
	}

	@Override
	public Long updEmployeBasic(EmployeBasic employeBasic) {
		return employeBasicService.updEmployeBasic(employeBasic);
	}

	@Override
	public void delEmployeBasic(Long id) {
		employeBasicService.delEmployeBasic(id);
	}

	@Override
	public EmployeBasic findEmployeBasicById(Long id) {
		return employeBasicService.findEmployeBasicById(id);
	}

	@Override
	public List<EmployeBasic> findEmployeBasicAll() {
		return employeBasicService.findEmployeBasicAll();
	}

	@Override
	public Long addEmployeArticle(EmployeArticle employeArticle) {
		return employeArticleService.addEmployeArticle(employeArticle);
	}

	@Override
	public Long updEmployeArticle(EmployeArticle employeArticle) {
		return employeArticleService.updEmployeArticle(employeArticle);
	}

	@Override
	public void delEmployeArticle(Long id) {
		employeArticleService.delEmployeArticle(id);
	}

	@Override
	public EmployeArticle findEmployeArticleById(Long id) {
		return employeArticleService.findEmployeArticleById(id);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN') "
			+ "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','PUBLISH') "
			+ "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
	public void pubProjectBasic(Integer channelId, List<Long> projectBasicIds) {
		projectBasicService.pubProjectBasic(projectBasicIds);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN') "
			+ "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','PUBLISH') "
			+ "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
	public void unPubProjectBasic(Integer channelId, List<Long> projectBasicIds) {
		projectBasicService.unPubProjectBasic(projectBasicIds);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN') "
			+ "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','PUBLISH') "
			+ "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
	public void pubProjectArticle(Integer channelId,
			List<Long> projectArticleIds) {
		projectArticleService.pubProjectArticle(projectArticleIds);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN') "
			+ "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','PUBLISH') "
			+ "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
	@Override
	public void unPubProjectArticle(Integer channelId,
			List<Long> projectArticleIds) {
		projectArticleService.unPubProjectArticle(projectArticleIds);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN') "
			+ "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','PUBLISH') "
			+ "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
	@Override
	public void pubEnterpriseBasic(Integer channelId,
			List<Long> enterpriseBasicIds) {
		enterpriseBasicService.pubEnterpriseBasic(enterpriseBasicIds);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN') "
			+ "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','PUBLISH') "
			+ "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
	@Override
	public void unPubEnterpriseBasic(Integer channelId,
			List<Long> enterpriseBasicIds) {
		enterpriseBasicService.unPubEnterpriseBasic(enterpriseBasicIds);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN') "
			+ "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','PUBLISH') "
			+ "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
	@Override
	public void pubEnterpriseArticle(Integer channelId,
			List<Long> enterpriseArticleIds) {
		enterpriseArticleService.pubEnterpriseArticle(enterpriseArticleIds);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN') "
			+ "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','PUBLISH') "
			+ "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
	@Override
	public void unPubEnterpriseArticle(Integer channelId,
			List<Long> enterpriseArticleIds) {
		enterpriseArticleService.unPubEnterpriseArticle(enterpriseArticleIds);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN') "
			+ "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','PUBLISH') "
			+ "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
	@Override
	public void pubEmployeBasic(Integer channelId, List<Long> employeBasicIds) {
		employeBasicService.pubEmployeBasic(employeBasicIds);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN') "
			+ "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','PUBLISH') "
			+ "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
	@Override
	public void unPubEmployeBasic(Integer channelId, List<Long> employeBasicIds) {
		employeBasicService.unPubEmployeBasic(employeBasicIds);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN') "
			+ "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','PUBLISH') "
			+ "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
	@Override
	public void pubEmployeArticle(Integer channelId,
			List<Long> employeArticleIds) {
		employeArticleService.pubEmployeArticle(employeArticleIds);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN') "
			+ "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','PUBLISH') "
			+ "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
	@Override
	public void unPubEmployeArticle(Integer channelId,
			List<Long> employeArticleIds) {
		employeArticleService.unPubEmployeArticle(employeArticleIds);
	}
}
