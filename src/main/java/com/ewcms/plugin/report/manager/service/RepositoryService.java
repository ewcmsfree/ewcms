/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.report.manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.plugin.report.manager.dao.RepositoryDAO;
import com.ewcms.plugin.report.model.Repository;

/**
 * 
 * @author wu_zhijun
 *
 */
@Service
public class RepositoryService implements RepositoryServiceable {

	@Autowired
	private RepositoryDAO repositoryDAO;
	
	@Override
	public Long addRepository(Repository repository){
		repositoryDAO.persist(repository);
		return repository.getId();
	}

	@Override
	public Long updRepository(Repository repository){
		repositoryDAO.merge(repository);
		return repository.getId();
	}

	@Override
	public Repository findRepositoryById(Long repositoryId){
		return repositoryDAO.get(repositoryId);
	}

	@Override
	public void delRepository(Long repositoryId){
		repositoryDAO.removeByPK(repositoryId);
	}
}
