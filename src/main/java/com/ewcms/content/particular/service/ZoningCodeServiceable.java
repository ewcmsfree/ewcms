package com.ewcms.content.particular.service;

import java.util.List;

import com.ewcms.content.particular.model.ZoningCode;

/**
 * 行政区划代码接口
 * 
 * @author wuzhijun
 *
 */
public interface ZoningCodeServiceable {

	public Long addZoningCode(ZoningCode zoningCode);
	
	public Long updZoningCode(ZoningCode zoningCode);
	
	public void delZoningCode(Long id);
	
	public ZoningCode findZoningCodeById(Long id);

	public List<ZoningCode> findZoningCodeAll();
	
	public Boolean findZoningCodeSelected(Long projectBasicId, String zoningCodeCode);
}
