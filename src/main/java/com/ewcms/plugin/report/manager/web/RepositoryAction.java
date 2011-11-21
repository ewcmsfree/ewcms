package com.ewcms.plugin.report.manager.web;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.plugin.report.manager.ReportFacable;
import com.ewcms.plugin.report.model.Repository;
import com.ewcms.web.CrudBaseAction;

public class RepositoryAction extends CrudBaseAction<Repository, Long> {

	private static final long serialVersionUID = -1093034528876795837L;

	@Autowired
	private ReportFacable reportFac;

	@Override
	protected Long getPK(Repository vo) {
		return vo.getId();
	}

	@Override
	protected Repository getOperator(Long pk) {
		return reportFac.findRepository(pk);
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
				Repository repository = reportFac.findRepository(getRepositoryId());
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
}
