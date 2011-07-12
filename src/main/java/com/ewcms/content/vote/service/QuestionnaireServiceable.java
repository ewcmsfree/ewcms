/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.vote.service;

import com.ewcms.content.vote.model.Questionnaire;

/**
 * 问卷调查主体
 * 
 * @author 吴智俊
 */
public interface QuestionnaireServiceable {
	
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
	 * @param questionnaire 问卷调查主体对象
	 * @return Long 问卷调查主体编号
	 */
	public Long updQuestionnaire(Questionnaire questionnaire);
	
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
}
