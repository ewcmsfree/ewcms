/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.plugin.vote.manager.service;

import java.util.List;

import com.ewcms.plugin.vote.model.Person;

/**
 * 投票人员信息
 * 
 * @author 吴智俊
 */
public interface PersonServiceable {
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
}
