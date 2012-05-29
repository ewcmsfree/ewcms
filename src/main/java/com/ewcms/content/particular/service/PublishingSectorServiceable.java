/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.content.particular.service;

import java.util.List;

import com.ewcms.content.particular.BaseException;
import com.ewcms.content.particular.model.PublishingSector;

/**
 * 发布部门接口
 * 
 * @author wuzhijun
 *
 */
public interface PublishingSectorServiceable {
	public Long addPublishingSector(PublishingSector publishingSector) throws BaseException;
	
	public Long updPublishingSector(PublishingSector publishingSector);
	
	public void delPublishingSector(Long id);
	
	public PublishingSector findPublishingSectorById(Long id);

	public List<PublishingSector> findPublishingSectorAll();
	
	public Boolean findPublishingSectorSelectedByPBId(Long projectBasicId, String publishingSectorCode);
	
	public Boolean findPublishingSectorSelectedByPAId(Long projectArticleId, String publishingSectorCode);
	
	public Boolean findPublishingSectorSelectedByEBId(Long enterpriseBasicId, String publishingSectorCode);
	
	public Boolean findPublishingSectorSelectedByEAId(Long enterpriseArticleId, String publishingSectorCode);

	public Boolean findPublishingSectorSelectedByMBId(Long employeBasicId, String publishingSectorCode);
	
	public Boolean findPublishingSectorSelectedByMAId(Long employeArticleId, String publishingSectorCode);
}
