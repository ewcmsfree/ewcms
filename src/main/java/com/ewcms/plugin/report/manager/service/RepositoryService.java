/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.report.manager.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.content.resource.model.Resource;
import com.ewcms.content.resource.service.ResourceServiceable;
import com.ewcms.core.site.model.Site;
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
	@Autowired
	private ResourceServiceable resourceService;
	
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

	@Override
	public void publishRepository(List<Long> repositoryIds, Site site) {
		for (Long repositoryId : repositoryIds) {
			Repository repository = findRepositoryById(repositoryId);
			String type = repository.getType();
			byte[] bytes = repository.getEntity();
			String outputFile = repository.getName() + "." + type;

			Resource.Type resourceType = Resource.Type.ANNEX;
			if (type.toLowerCase().equals("png")) {
				resourceType = Resource.Type.IMAGE;
			}

			File file = null;
			FileOutputStream fileStream = null;
			BufferedOutputStream bufferStream = null;
			try {
				file = new File(outputFile);
				fileStream = new FileOutputStream(file);
				bufferStream = new BufferedOutputStream(fileStream);
				bufferStream.write(bytes);

				resourceService.upload(site, file, outputFile, resourceType);
				
				repository.setPublishDate(new Date(Calendar.getInstance().getTime().getTime()));
				repositoryDAO.merge(repository);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (fileStream != null) {
					try {
						fileStream.close();
					} catch (IOException e) {
					}
					fileStream = null;
				}
				if (bufferStream != null) {
					try {
						bufferStream.close();
					} catch (IOException e) {
					}
					bufferStream = null;
				}
				file = null;
			}
		}
	}
}
