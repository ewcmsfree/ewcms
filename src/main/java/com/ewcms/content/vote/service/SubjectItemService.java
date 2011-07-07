/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.vote.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ewcms.content.vote.dao.SubjectDAO;
import com.ewcms.content.vote.dao.SubjectItemDAO;
import com.ewcms.content.vote.model.Subject;
import com.ewcms.content.vote.model.SubjectItem;

/**
 * 问卷调查主题明细Service
 * 
 * @author 吴智俊
 */
@Service
public class SubjectItemService implements SubjectItemServiceable {
	
	@Autowired
	private SubjectDAO subjectDAO;
	@Autowired
	private SubjectItemDAO subjectItemDAO;

	@Override
	public Long addSubjectItem(Long subjectId, SubjectItem subjectItem) {
		Assert.notNull(subjectId);
		Long maxSort = subjectItemDAO.findSubjectItemMaxSort(subjectId);
		Subject subject = subjectDAO.get(subjectId);
		Assert.notNull(subject);
		List<SubjectItem> subjectItems = subject.getSubjectItems();
		subjectItem.setSort(maxSort + 1);
		subjectItems.add(subjectItem);
		subject.setSubjectItems(subjectItems);
		subjectDAO.merge(subject);
		subjectDAO.flush(subject);
		return subjectItem.getId();
	}

	@Override
	public void delSubjectItem(Long subjectId, Long subjectItemId) {
		SubjectItem subjectItem = subjectItemDAO.get(subjectItemId);
		Assert.notNull(subjectItem);
		Subject subject = subjectDAO.get(subjectId);
		Assert.notNull(subject);
		List<SubjectItem> subjectItems = subject.getSubjectItems();
		subjectItems.remove(subjectItem);
		subject.setSubjectItems(subjectItems);
		subjectDAO.merge(subject);
		
	}

	@Override
	public SubjectItem findSubjectItem(Long subjectItemId) {
		return subjectItemDAO.get(subjectItemId);
	}

	@Override
	public Long updSubjectItem(SubjectItem subjectItem) {
		subjectItemDAO.merge(subjectItem);
		return subjectItem.getId();
	}

	@Override
	public SubjectItem findSubjectItemBySubjectAndInputStatus(Long subjectId) {
		return subjectItemDAO.findSubjectItemBySubjectAndInputStatus(subjectId);
	}

	@Override
	public void downSubjectItem(Long subjectId, Long subjectItemId) {
		Assert.notNull(subjectId);
		SubjectItem subjectItem = subjectItemDAO.get(subjectItemId);
		Assert.notNull(subjectItem);
		Long sort = subjectItem.getSort();
		if (sort == null){
			sort = subjectItemDAO.findSubjectItemMaxSort(subjectItemId);
			subjectItem.setSort(sort + 1);
			subjectItemDAO.merge(subjectItem);
		}else{
			SubjectItem subjectItem_next = subjectItemDAO.findSubjectItemBySort(subjectId, sort + 1);
			if (subjectItem_next != null){
				subjectItem_next.setSort(sort);
				subjectItemDAO.merge(subjectItem_next);
				subjectItem.setSort(sort + 1);
				subjectItemDAO.merge(subjectItem);
			}
		}
	}

	@Override
	public void upSubjectItem(Long subjectId, Long subjectItemId) {
		Assert.notNull(subjectId);
		SubjectItem subjectItem = subjectItemDAO.get(subjectItemId);
		Assert.notNull(subjectItem);
		Long sort = subjectItem.getSort();
		if (sort == null){
			sort = subjectItemDAO.findSubjectItemMaxSort(subjectId);
			subjectItem.setSort(sort + 1);
			subjectItemDAO.merge(subjectItem);
		}else{
			SubjectItem subjectItem_prv = subjectItemDAO.findSubjectItemBySort(subjectId, sort - 1);
			if (subjectItem_prv != null){
				subjectItem_prv.setSort(sort);
				subjectItemDAO.merge(subjectItem_prv);
				subjectItem.setSort(sort - 1);
				subjectItemDAO.merge(subjectItem);
			}
		}
	}
}
