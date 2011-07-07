/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.vote.service;

import com.ewcms.content.vote.model.SubjectItem;

/**
 * 问卷调查主题明细
 * 
 * @author 吴智俊
 */
public interface SubjectItemServiceable {
	
	/**
	 * 新增问卷调查主题明细
	 * 
	 * @param subjectId 问卷调查主题对象
	 * @param subjectItem 问卷调查主题明细对象
	 * @return Long 问卷调查主题明细编号
	 */
	public Long addSubjectItem(Long subjectId, SubjectItem subjectItem);
	
	/**
	 * 修改问卷调查主题明细
	 * 
	 * @param subjectItem 问卷调查主题明细对象
	 * @return Long 问卷调查主题明细编号
	 */
	public Long updSubjectItem(SubjectItem subjectItem);
	
	/**
	 * 删除问卷调查主题明细
	 * 
	 * @param subjectId 问卷调查主题编号
	 * @param subjectItemId 问卷调查主题明细对象
	 */
	public void delSubjectItem(Long subjectId, Long subjectItemId);
	
	/**
	 * 查询问卷调查主题明细
	 * 
	 * @param subjectItemId 问卷调查主题明细编号
	 * @return SubjectItem 问卷调查主题明细对象
	 */
	public SubjectItem findSubjectItem(Long subjectItemId);
	
	/**
	 * 通过问卷调查主题查询问卷调查主题明细
	 * 
	 * @param subjectId 问卷调查主题编号
	 * @return SubjectItem 问卷调查主题明细对象
	 */
	public SubjectItem findSubjectItemBySubjectAndInputStatus(Long subjectId);
	
	/**
	 * 把问卷调查主题明细对象上移一位
	 * 
	 * @param subjectId 问卷调查主题编号
	 * @param subjectItemId 问卷调查主题明细编号
	 */
	public void upSubjectItem(Long subjectId, Long subjectItemId);
	
	/**
	 * 把问卷调查主题明细对象下移一位
	 * 
	 * @param subjectId 问卷调查主题编号
	 * @param subjectItemId 问卷调查主题明细编号
	 */
	public void downSubjectItem(Long subjectId, Long subjectItemId);
}
