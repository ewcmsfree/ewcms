/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.plugin.notes.manager;

import java.util.List;

import com.ewcms.plugin.notes.model.Memoranda;

/**
 * 
 * @author wu_zhijun
 */
public interface NotesFacable {
	/**
	 * 初始化日历并生成HTML页面
	 * 
	 * @param year 年
	 * @param month 月
	 * @return StringBuffer HTML页面
	 */
	public StringBuffer getInitCalendarToHtml(final int year, final int month);
	
	/**
	 * 新增备忘录
	 * 
	 * @param memoranda 备忘录对象
	 * @param year 年
	 * @param month 月
	 * @param day 日
	 * @return Long 备忘录编号
	 */
	public Long addMemoranda(Memoranda memoranda, Integer year, Integer month, Integer day);
	
	/**
	 * 修改备忘录
	 * 
	 * @param memoranda 备忘录对象
	 * @return Long 备忘录编号
	 */
	public Long updMemoranda(Memoranda memoranda);
	
	/**
	 * 查询备忘录
	 * 
	 * @param memorandaId 备忘录编号
	 * @return Memoranda 备忘录对象
	 */
	public Memoranda findMemoranda(Long memorandaId);
	
	/**
	 * 删除备忘录
	 * 
	 * @param memorandaId 备忘录编号
	 */
	public void delMemoranda(Long memorandaId);
	
	/**
	 * 更新备忘录
	 * 
	 * @param memorandaId 备忘录编号
	 * @param year 年
	 * @param month 月
	 * @param day 日
	 */
	public void updMemoranda(Long memorandaId, Integer year, Integer month, Integer day);
	
	/**
	 * 根据客户端时间显示备忘录对象集合
	 * 
	 * @param userName 用户名
	 * @param clientTime 客户端时间
	 * @return List 备忘录对象集合
	 */
	public List<Memoranda> getMemorandaFireTime(String userName, String clientTime);
	
	/**
	 * 根据登录的用户查询备忘录对象集合
	 * 
	 * @return List 备忘录对象集合
	 */
	public List<Memoranda> findMemorandaByUserName();
}
