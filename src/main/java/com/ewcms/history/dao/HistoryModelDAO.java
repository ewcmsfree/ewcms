/**
 * 创建日期 2011-3-31
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.history.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.springframework.orm.jpa.JpaCallback;
import org.springframework.stereotype.Service;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.history.model.HistoryModel;

/**
 * @author 吴智俊
 */
@Service
public class HistoryModelDAO extends JpaDAO<Long, HistoryModel> {
	@SuppressWarnings("unchecked")
	public List<HistoryModel> findByHistoryModel(String className,
			String idName, String idValue, Date startDate, Date endDate) {
		String hql = "From HistoryModel h Where h.className=? And h.idName=? And h.idValue=? ";
		if (startDate != null) {
			hql += " And h.createDate>=? ";
		}
		if (endDate != null) {
			hql += " And h.createDate<=? ";
		}
		hql += " Order By h.id ";

		List<HistoryModel> list = new ArrayList<HistoryModel>();
		if (startDate != null && endDate != null) {
			list = this.getJpaTemplate().find(hql, className, idName, idValue,
					startDate, endDate);
		} else if (startDate != null && endDate == null) {
			list = this.getJpaTemplate().find(hql, className, idName, idValue,
					startDate);
		} else if (startDate == null && endDate != null) {
			list = this.getJpaTemplate().find(hql, className, idName, idValue,
					endDate);
		} else {
			list = this.getJpaTemplate().find(hql, className, idName, idValue);
		}
		if (list.isEmpty())
			return new ArrayList<HistoryModel>();
		return list;
	}

	public void deleteHistoryModelByBeforeDate(final Date createDate) {
		getJpaTemplate().execute(new JpaCallback<Object>() {
			@Override
			public Object doInJpa(EntityManager em) throws PersistenceException {
				String hql = "Delete HistoryModel h Where h.createDate<=?";
				em.createQuery(hql).setParameter(1, createDate).executeUpdate();
				return null;
			}
		});
	}
}
