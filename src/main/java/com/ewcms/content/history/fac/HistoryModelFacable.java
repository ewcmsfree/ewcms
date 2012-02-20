/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.history.fac;

import java.util.Date;
import java.util.List;

import com.ewcms.content.history.model.HistoryModel;

/**
 * 历史记录对象Fac接口
 * 
 * @author 吴智俊
 */
public interface HistoryModelFacable {
	/**
	 * 根据参数查询历史记录对象集合
	 * 
	 * @param className 类名(包括类路径和类名称)
	 * @param idName 关键字名称
	 * @param idValue 关键字值
	 * @param startDate 开始日期(当设为null值时,将不作为条件进行查询)
	 * @param endDate 结束日期(当设为null值时,将不作为条件进行查询)
	 * @return List&lt;HistoryModel&gt;
	 */
	public List<HistoryModel> findByHistoryModel(String className, String idName, String idValue, Date startDate, Date endDate);
	
	/**
	 * 根据参数查询历史记录对象
	 * 
	 * @param historyId 历史记录编号
	 * @return HistoryModel
	 */
	public HistoryModel findByHistoryModel(Long historyId);
	
	/**
	 * 根据参数删除历史记录对象
	 * 
	 * @param historyId 历史记录编号
	 */
	public void delHistoryModel(Long historyId);
	
	/**
	 * 删除相应日期之前的历史记录(三个月)
	 */
	public void delHistoryModelBeforeDate();
}
