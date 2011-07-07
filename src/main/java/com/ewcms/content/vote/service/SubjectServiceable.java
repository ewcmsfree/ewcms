/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.vote.service;

import com.ewcms.content.vote.model.Subject;

/**
 * 问卷调查主题
 * 
 * @author 吴智俊
 */
public interface SubjectServiceable {
	
	/**
	 * 新增问卷调查主题
	 * 
	 * @param questionnaireId 问卷调查主体编号
	 * @param subject 问卷调查主题对象
	 * @return Long 问卷调查主题编号
	 */
	public Long addSubject(Long questionnaireId, Subject subject);
	
	/**
	 * 修改问卷调查主题
	 * 
	 * @param questionnaireId 问卷调查主体编号
	 * @param subject 问卷调查主题对象
	 * @return Long 问卷调查主题编号
	 */
	public Long updSubject(Long questionnaireId, Subject subject);
	
	/**
	 * 删除问卷调查主题
	 * 
	 * @param questionnaireId 问卷调查主体编号
	 * @param subjectId 问卷调查主题编号
	 */
	public void delSubject(Long questionnaireId, Long subjectId);
	
	/**
	 * 查询问卷调查主题
	 * 
	 * @param subjectId 问卷调查主题编号
	 * @return Subject 问卷调查主题对象
	 */
	public Subject findSubject(Long subjectId);
	
	/**
	 * 把问卷调查主题对象上移一位
	 * 
	 * @param questionnaireId 问卷调查主体编号
	 * @param subjectId 问卷调查主题编号
	 */
	public void upSubject(Long questionnaireId, Long subjectId);
	
	/**
	 * 把问卷调查主题对象下移一位
	 * 
	 * @param questionnaireId 问卷调查主体编号
	 * @param subjectId 问卷调查主题编号
	 */
	public void downSubject(Long questionnaireId, Long subjectId);
}
