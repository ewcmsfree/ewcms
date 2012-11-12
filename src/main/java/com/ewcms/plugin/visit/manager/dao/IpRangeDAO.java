package com.ewcms.plugin.visit.manager.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.plugin.visit.model.IpRange;

/**
 * IP区域DAO
 * 
 * @author wu_zhijun
 *
 */
@Repository
public class IpRangeDAO extends JpaDAO<Long, IpRange>{
	
	public IpRange findIpRangeByIp(Long ipBegin, Long ipEnd){
		String hql = "From IpRange Where ipBegin>=:ipBegin And ipEnd<=:ipEnd Order By id Desc";
		TypedQuery<IpRange> query = this.getEntityManager().createQuery(hql, IpRange.class);
		query.setParameter("ipBegin", ipBegin);
		query.setParameter("ipEnd", ipEnd);
		List<IpRange> list = query.getResultList();
		if (list == null || list.isEmpty()) return null;
		return list.get(0);
	}
}
