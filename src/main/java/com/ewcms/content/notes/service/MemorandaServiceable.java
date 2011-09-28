/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.notes.service;

import java.util.List;

import com.ewcms.content.notes.model.Memoranda;

/**
 * 备忘录操作Service
 * 
 * @author wu_zhijun
 */
public interface MemorandaServiceable {
	/**
	 * 初始化日历并生成HTML页面
	 * 
	 * @param year �?
	 * @param month �?
	 * @return StringBuffer HTML页面
	 */
	public StringBuffer getInitCalendarToHtml(final int year, final int month);
	
	/**
	 * 新增备忘 �?
	 * 
	 * @param memoranda 备忘录对�?
	 * @param year �?
	 * @param month �?
	 * @param day �?
	 * @return Long 备忘录编�?
	 */
	public Long addMemoranda(Memoranda memoranda, Integer year, Integer month, Integer day);
	
	/**
	 * 修改备忘�?
	 * 
	 * @param memoranda 备忘录对�?
	 * @return Long 备忘录编�?
	 */
	public Long updMemoranda(Memoranda memoranda);
	
	/**
	 * 查询备忘�?
	 * 
	 * @param memorandaId 备忘录编�?
	 * @return Memoranda 备忘录对�?
	 */
	public Memoranda findMemoranda(Long memorandaId);
	
	/**
	 * 删除备忘�?
	 * 
	 * @param memorandaId 备忘录编�?
	 */
	public void delMemoranda(Long memorandaId);
	
	/**
	 * 更新备忘�?
	 * 
	 * @param memorandaId 备忘录编�?
	 * @param year �?
	 * @param month �?
	 * @param day �?
	 */
	public void updMemoranda(Long memorandaId, Integer year, Integer month, Integer day);
	
	/**
	 * 根据客户端时间显示备忘录对象集合
	 * 
	 * @param clientTime 客户端时�?
	 * @return List 备忘录对象集�?
	 */
	public List<Memoranda> getMemorandaFireTime(String clientTime);
	
	/**
	 * 根据登录的用户查询备忘录对象集合
	 * 
	 * @return List 备忘录对象集�?
	 */
	public List<Memoranda> findMemorandaByUserName();
}
