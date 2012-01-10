/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.report.manager.web;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.content.resource.ResourceFacable;
import com.ewcms.content.resource.model.Resource;
import com.ewcms.core.site.model.Site;
import com.ewcms.plugin.report.manager.ReportFacable;
import com.ewcms.plugin.report.model.Repository;
import com.ewcms.web.CrudBaseAction;
import com.ewcms.web.util.EwcmsContextUtil;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;

public class RepositoryAction extends CrudBaseAction<Repository, Long> {

	private static final long serialVersionUID = -1093034528876795837L;

	@Autowired
	private ReportFacable reportFac;
	@Autowired
	private ResourceFacable resourceFac;

	public List<Long> getSelections() {
		return super.getOperatorPK();
	}

	public void setSelections(List<Long> selections) {
		super.setOperatorPK(selections);
	}

	@Override
	protected Long getPK(Repository vo) {
		return vo.getId();
	}

	@Override
	protected Repository getOperator(Long pk) {
		return reportFac.findRepositoryById(pk);
	}

	@Override
	protected void deleteOperator(Long pk) {
		reportFac.delRepository(pk);
	}

	@Override
	protected Long saveOperator(Repository vo, boolean isUpdate) {
		return null;
	}

	@Override
	protected Repository createEmptyVo() {
		return null;
	}

	private Long repositoryId;

	public Long getRepositoryId() {
		return repositoryId;
	}

	public void setRepositoryId(Long repositoryId) {
		this.repositoryId = repositoryId;
	}

	public void download() {
		PrintWriter pw = null;
		InputStream in = null;
		try {
			if (getRepositoryId() != null) {
				Repository repository = reportFac.findRepositoryById(getRepositoryId());
				String type = repository.getType();
				byte[] bytes = repository.getEntity();

				HttpServletResponse response = ServletActionContext
						.getResponse();
				response.setContentLength(bytes.length);

				if (type.toLowerCase().equals("pdf")) {
					response.setContentType("application/pdf");
				} else if (type.toLowerCase().equals("png")) {
					response.setContentType("image/png");
				}

				pw = response.getWriter();
				in = new ByteArrayInputStream(bytes);
				int len = 0;
				while ((len = in.read()) > -1) {
					pw.write(len);
				}
				pw.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (pw != null) {
				try {
					pw.close();
					pw = null;
				} catch (Exception e) {
				}
			}
			if (in != null) {
				try {
					in.close();
					in = null;
				} catch (Exception e) {
				}
			}
		}
	}

	public void publish() {
		if (getSelections() != null && !getSelections().isEmpty()) {
			List<Long> respositoryIds = getSelections();
			Site site = EwcmsContextUtil.getCurrentSite();
			for (Long respositoryId : respositoryIds) {
				Repository repository = reportFac
						.findRepositoryById(respositoryId);
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

					resourceFac.uploadResource(site, file, outputFile, resourceType);
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
			Struts2Util.renderJson(JSONUtil.toJSON("true"));
		} else {
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
}
