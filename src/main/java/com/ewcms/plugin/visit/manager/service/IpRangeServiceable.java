package com.ewcms.plugin.visit.manager.service;

import com.ewcms.plugin.visit.model.IpRange;

/**
 * 
 * @author wu_zhijun
 *
 */
public interface IpRangeServiceable {
	public IpRange findIpRangeByIp(Long ipBegin, Long ipEnd);
}
