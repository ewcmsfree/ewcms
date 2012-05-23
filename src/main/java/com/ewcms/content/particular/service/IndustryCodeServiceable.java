package com.ewcms.content.particular.service;

import java.util.List;

import com.ewcms.content.particular.model.IndustryCode;

/**
 * 行业编码接口
 * @author wuzhijun
 *
 */
public interface IndustryCodeServiceable {
	public Long addIndustryCode(IndustryCode industryCode);
	
	public Long updIndustryCode(IndustryCode industryCode);
	
	public void delIndustryCode(Long id);
	
	public IndustryCode findIndustryCodeById(Long id);

	public List<IndustryCode> findIndustryCodeAll();
	
	public Boolean findIndustryCodeSelected(Long projectBasicId, String industryCodeCode);
}
