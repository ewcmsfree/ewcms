/**
 * 创建日期 2009-4-13
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.scheduling.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.scheduling.manage.dao.AlqcJobDAO;
import com.ewcms.scheduling.manage.dao.AlqcJobTriggerDAO;
import com.ewcms.scheduling.model.AlqcJob;

/**
 * @author 吴智俊
 */
@Service("alqcJobService")
public class AlqcJobService implements AlqcJobServiceable {
	
	@Autowired
	private AlqcJobDAO alqcJobDAO;
	@Autowired
	private AlqcJobTriggerDAO alqcJobTriggerDAO;
	
	@Override
	public AlqcJob saveJob(AlqcJob alqcJob) {
		alqcJobDAO.persist(alqcJob);
		alqcJobDAO.flush(alqcJob);
		return alqcJob;
	}

	@Override
	public AlqcJob updateJob(AlqcJob alqcJob) {
		if (alqcJob.getTrigger() != null){
			Integer triggerId = alqcJob.getTrigger().getId();
			if (triggerId != null){
				alqcJobTriggerDAO.removeByPK(triggerId);
				alqcJob.getTrigger().setId(null);
				alqcJob.getTrigger().setVersion(-1);
			}
		}
		alqcJobDAO.merge(alqcJob);
		alqcJobDAO.flush(alqcJob);
		alqcJob = alqcJobDAO.get(alqcJob.getId());
		return alqcJob;
	}

	@Override
	public AlqcJob findByJob(Integer id)  {
		return alqcJobDAO.get(id);
	}
	
	@Override
	public void deletedJob(Integer id)  {
		alqcJobDAO.removeByPK(id);
	}
	
	@Override
	public List<AlqcJob> findByAllJob() {
		return (List<AlqcJob>)alqcJobDAO.findAll();
	}
}
