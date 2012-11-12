package com.ewcms.plugin.visit.manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.plugin.visit.manager.dao.IpRangeDAO;
import com.ewcms.plugin.visit.model.IpRange;

/**
 * 
 * @author wu_zhijun
 *
 */
@Service
public class IpRangeService implements IpRangeServiceable {

	@Autowired
	private IpRangeDAO ipRangeDAO;
	
	@Override
	public IpRange findIpRangeByIp(Long ipBegin, Long ipEnd) {
		return ipRangeDAO.findIpRangeByIp(ipBegin, ipEnd);
	}

}
