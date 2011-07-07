/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.vote;

import java.util.List;

import com.ewcms.content.vote.model.Subject;
import com.ewcms.content.vote.model.SubjectItem;
import com.ewcms.content.vote.model.Questionnaire;
import com.ewcms.content.vote.model.Person;

/**
 * 调查投票
 * 
 * @author 吴智俊
 */
public interface VoteFacable {
	
	/**
	 * 新增投票人员信息
	 * 
	 * @param person  投票人员信息对象
	 * @return Long 投票人员编号
	 */
	public Long addPerson(Person person);
	
	/**
	 * 查询用户是否已对问卷调查进行了投票
	 * 
	 * @param questionnaireId 问卷调查主体编号
	 * @param ip 投票用户IP
	 * @return Boolean (false:未投票,true:已投票)
	 */
	public Boolean findPersonIsEntity(Long questionnaireId, String ip);
	
	/**
	 * 删除投票人员信息
	 * 
	 * @param personId 投票人员信息编号
	 */
	public void delPerson(Long personId);
	
	/**
	 * 获取投票人员选项
	 * 
	 * @param questionnaireId 问卷调查主体编号
	 * @param personId 投票人员信息编号
	 * @return List 投票人员选项集合
	 */
	public List<String> getRecordToHtml(Long questionnaireId, Long personId);
	
	/**
	 * 新增问卷调查主体
	 * 
	 * @param questionnaire 问卷调查主体对象
	 * @return Long 问卷调查主体编号
	 */
	public Long addQuestionnaire(Questionnaire questionnaire);
	
	/**
	 * 修改问卷调查主体
	 * 
	 * @param questionnaireId 问卷调查主体编号
	 * @param questionnaire 问卷调查主体对象
	 * @return Long 问卷调查主体编号
	 */
	public Long updQuestionnaire(Long questionnaireId, Questionnaire questionnaire);
	
	/**
	 * 删除问卷调查主体
	 * 
	 * @param questionnaireId 问卷调查主体编号
	 */
	public void delQuestionnaire(Long questionnaireId);
	
	/**
	 * 查询问卷调查主体
	 * 
	 * @param questionnaireId 问卷调查主体编号
	 * @return Questionnaire 问卷调查主体对象
	 */
	public Questionnaire findQuestionnaire(Long questionnaireId);
	
	/**
	 * 显示问卷调查主体视图,并转换成网页
	 * 
	 * @param questionnaireId 问卷调查主体编号
	 * @param servletContentName 容器名
	 * @return StringBuffer 以字符串形式组成的网页
	 */
	public StringBuffer getQuestionnaireViewToHtml(Long questionnaireId, String servletContentName);
	
	/**
	 * 查看问卷调查主体的结果,并转换成网页(服务端)
	 * 
	 * @param questionnaireId 问卷调查主体编号
	 * @param servletContentName 容器名
	 * @param ipAddr IP地址
	 * @param isView 是否对不允许查看或投票后才能查看进行过滤(true:需要,false:不需要)
	 * @return StringBuffer 以字符串形式组成的网页
	 */
	public StringBuffer getQuestionnaireResultToHtml(Long questionnaireId, String servletContentName, String ipAddr, Boolean isView);
	
	/**
	 * 查看问卷调查主体的结果,并转换成网页(客户端),调用服务端方法,并把isView设为true
	 * 
	 * @param questionnaireId 问卷调查主体编号
	 * @param servletContentName 容器名
	 * @param ipAddr IP地址
	 * @return StringBuffer 以字符串形式组成的网页
	 */
	public StringBuffer getQuestionnaireResultClientToHtml(Long questionnaireId, String servletContentName, String ipAddr);

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
