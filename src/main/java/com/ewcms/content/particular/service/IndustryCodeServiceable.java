/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.content.particular.service;

import java.util.List;

import com.ewcms.content.particular.BaseException;
import com.ewcms.content.particular.model.IndustryCode;

/**
 * 行业编码接口
 * @author wuzhijun
 *
 */
public interface IndustryCodeServiceable {
	public Long addIndustryCode(IndustryCode industryCode) throws BaseException;
	
	public Long updIndustryCode(IndustryCode industryCode);
	
	public void delIndustryCode(Long id);
	
	public IndustryCode findIndustryCodeById(Long id);

	public List<IndustryCode> findIndustryCodeAll();
	
	public Boolean findIndustryCodeSelected(Long projectBasicId, String industryCodeCode);
}
