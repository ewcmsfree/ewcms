/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.vote.service;

import com.ewcms.content.vote.model.Subject;

/**
 * @author wu_zhijun
 */
public interface SubjectServiceable {
	
	public Long addSubject(Long questionnaireId, Subject subject);
	
	public Long updSubject(Long questionnaireId, Subject subject);
	
	public void delSubject(Long questionnaireId, Long subjectId);
	
	public Subject findSubject(Long subjectId);
	
	public void upSubject(Long questionnaireId, Long subjectId);
	
	public void downSubject(Long questionnaireId, Long subjectId);
}
