/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.vote.service;

import com.ewcms.content.vote.model.SubjectItem;

/**
 * 
 * @author wu_zhijun
 *
 */
public interface SubjectItemServiceable {
	
	public Long addSubjectItem(Long subjectId, SubjectItem subjectItem);
	
	public Long updSubjectItem(SubjectItem subjectItem);
	
	public void delSubjectItem(Long subjectId, Long subjectItemId);
	
	public SubjectItem findSubjectItem(Long subjectItemId);
	
	public SubjectItem findSubjectItemBySubject(Long subjectId);
	
	public void upSubjectItem(Long subjectId, Long subjectItemId);
	
	public void downSubjectItem(Long subjectId, Long subjectItemId);
}
