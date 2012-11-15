package com.ewcms.plugin.visit.manager.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.common.lang.EmptyUtil;
import com.ewcms.core.site.dao.ChannelDAO;
import com.ewcms.core.site.model.Channel;
import com.ewcms.plugin.visit.manager.dao.IpRangeDAO;
import com.ewcms.plugin.visit.manager.dao.VisitDAO;
import com.ewcms.plugin.visit.manager.dao.VisitItemDAO;
import com.ewcms.plugin.visit.model.IpRange;
import com.ewcms.plugin.visit.model.Visit;
import com.ewcms.plugin.visit.model.VisitItem;
import com.ewcms.plugin.visit.util.VisitUtil;

/**
 * 
 * @author wu_zhijun
 *
 */
@Service
public class VisitService implements VisitServiceable {

	@Autowired
	private VisitDAO visitDAO;
	@Autowired
	private IpRangeDAO ipRangeDAO;
	@Autowired
	private VisitItemDAO visitItemDAO;
	@Autowired
	private ChannelDAO channelDAO;

	@Override
	public void addVisitByLoadEvent(Visit visit, VisitItem visitItem) {
		Visit dbVisit = findVisitEntity(visit);
		if (dbVisit == null) {
			Long ip = VisitUtil.convertIP(visit.getIp());
			IpRange ipRange = ipRangeDAO.findIpRangeByIp(ip, ip);
			if (ipRange == null) {
				visit.setCountry("未知");
				visit.setDistrict("未知");
			}else{
				String country = ipRange.getCountry();
				String district = ipRange.getCity();
				if (EmptyUtil.isStringEmpty(country)){
					country = "未知";
				}
				if (EmptyUtil.isStringEmpty(district)){
					district = "未知";
				}
				visit.setCountry(country);
				visit.setDistrict(district);
			}
			
			visitDAO.persist(visit);
			visitItem.setUniqueId(visit.getUniqueId());
			visitItem.setPageView(1L);
			visitItem.setDepth(findVisitDepth(1L, visitItem.getChannelId()));
			visitItemDAO.persist(visitItem);
		} else {
			VisitItem dbVisitItem = visitItemDAO.findVisitItemByVisitItemPK(dbVisit.getUniqueId(), visitItem.getSiteId(), visitItem.getChannelId(), visitItem.getArticleId(), visitItem.getUrl());
			if (dbVisitItem == null){
				visitItem.setUniqueId(dbVisit.getUniqueId());
				visitItem.setPageView(1L);
				visitItem.setDepth(findVisitDepth(1L, visitItem.getChannelId()));
				visitItemDAO.persist(visitItem);
			}else{
				dbVisitItem.setPageView(dbVisitItem.getPageView() + 1);
				visitItemDAO.merge(dbVisitItem);
			}
			dbVisit.setRvFlag(visit.getRvFlag());
			visitDAO.merge(dbVisit);
		}
	}

	@Override
	public void addVisitByKeepAliveEvent(Visit visit, VisitItem visitItem) {
		Visit dbVisit = findVisitEntity(visit);
		if (dbVisit != null){
			VisitItem dbVisitItem = visitItemDAO.findVisitItemByVisitItemPK(dbVisit.getUniqueId(), visitItem.getSiteId(), visitItem.getChannelId(), visitItem.getArticleId(), visitItem.getUrl());
			if (dbVisitItem != null){
				dbVisitItem.setStickTime(visitItem.getStickTime() + dbVisitItem.getStickTime());
				visitItem.setDepth(findVisitDepth(1L, visitItem.getChannelId()));
				visitItemDAO.merge(dbVisitItem);
			}
		}
	}
	
	@Override
	public void addVisitByUnloadEvent(Visit visit, VisitItem visitItem){
		VisitItem dbVisitItem = visitItemDAO.findVisitItemByVisitItemPK(visit.getUniqueId(), visitItem.getSiteId(), visitItem.getChannelId(), visitItem.getArticleId(), visitItem.getUrl());
		if (dbVisitItem != null){
			dbVisitItem.setEvent(VisitUtil.UNLOAD_EVENT);
			visitItem.setDepth(findVisitDepth(1L, visitItem.getChannelId()));
			visitItemDAO.merge(dbVisitItem);
		}
	}

	private Visit findVisitEntity(Visit visit) {
		String uniqueId = visit.getUniqueId();
		Date addDate = visit.getAddDate();
		String ip = visit.getIp();
		if (EmptyUtil.isStringNotEmpty(uniqueId) && EmptyUtil.isNotNull(addDate) && EmptyUtil.isNotNull(ip))
			return visitDAO.findVisitByVisitPK(uniqueId, addDate, ip);
		else
			return null;
	}
	
	private Long findVisitDepth(Long depth, Integer channelId){
		if (channelId == null) return depth; 
		Channel channel = channelDAO.get(channelId);
		if (channel == null)  return depth;
		Channel parent = channel.getParent();
		if (parent == null) return depth; 
		depth = depth + 1;
		return findVisitDepth(depth, parent.getId());
	}
}
