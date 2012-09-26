/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.history.web;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.content.history.fac.HistoryModelFacable;
import com.ewcms.content.history.model.HistoryModel;
import com.ewcms.content.history.util.ByteToObject;
import com.ewcms.core.site.model.Template;
import com.ewcms.web.CrudBaseAction;

/**
 *
 * @author 吴智俊
 */
public class HistoryAction extends CrudBaseAction<HistoryModel, Long> {

	private static final long serialVersionUID = 2667146571103157163L;

	@Autowired
	private HistoryModelFacable historyModelFac;
	
	public List<Long> getSelections() {
        return super.getOperatorPK();
    }
	
	public void setSelections(List<Long> selections) {
        super.setOperatorPK(selections);
    }

    public String query() throws Exception {
        return SUCCESS;
    }
	
	@Override
	protected HistoryModel createEmptyVo() {
		return null;
	}

	@Override
	protected void deleteOperator(Long pk) {
		historyModelFac.delHistoryModel(pk);
	}

	@Override
	protected HistoryModel getOperator(Long pk) {
		return historyModelFac.findByHistoryModel(pk);
	}

	@Override
	protected Long getPK(HistoryModel vo) {
		return vo.getId();
	}

	@Override
	protected Long saveOperator(HistoryModel vo, boolean isUpdate) {
		return null;
	}
	
	private Long historyId;
	
	public Long getHistoryId() {
		return historyId;
	}

	public void setHistoryId(Long historyId) {
		this.historyId = historyId;
	}

	public void download(){
		PrintWriter pw = null;
		InputStream in = null;
		try {
			if (getHistoryId() != null) {
				HistoryModel historyModel = historyModelFac.findByHistoryModel(getHistoryId());
				if (historyModel.getModelObject() != null && historyModel.getModelObject().length != 0) {
					String fileName = String.valueOf(historyModel.getIdType() + "_" + getHistoryId());
					fileName = URLEncoder.encode(fileName, "UTF-8");

					Object obj = ByteToObject.conversion(historyModel.getModelObject());
					Template template = (Template) obj;
					
					;
					byte[] bytes = new byte[template.getTemplateEntity().getTplEntity().length];
					bytes = template.getTemplateEntity().getTplEntity();
					
					HttpServletResponse response = ServletActionContext.getResponse();
					response.setContentType("application/jrxml");
					response.setCharacterEncoding("UTF-8");
					response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".html");

					pw = response.getWriter();

					response.setContentLength(bytes.length);
					in = new ByteArrayInputStream(bytes);
					int len = 0;
					while ((len = in.read()) > -1) {
						pw.write(len);
					}
					pw.flush();
				} else {
					this.addActionError("没有文件可供下载");
				}
			} else {
				this.addActionError("没有文件可供下载");
			}
		} catch (IOException e) {
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
