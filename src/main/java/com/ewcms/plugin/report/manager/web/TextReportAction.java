/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.report.manager.web;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.plugin.BaseException;
import com.ewcms.plugin.datasource.manager.BaseDSFacable;
import com.ewcms.plugin.datasource.model.BaseDS;
import com.ewcms.plugin.report.generate.factory.TextFactoryable;
import com.ewcms.plugin.report.manager.ReportFacable;
import com.ewcms.plugin.report.model.Parameter;
import com.ewcms.plugin.report.model.ParametersType;
import com.ewcms.plugin.report.model.TextReport;
import com.ewcms.plugin.report.model.TextType;
import com.ewcms.web.CrudBaseAction;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;
import com.ewcms.web.vo.ComboBox;

/**
 * 
 * @author wuzhijun
 * 
 */
public class TextReportAction extends CrudBaseAction<TextReport, Long> {

	private static final long serialVersionUID = 8962123364258360077L;

	@Autowired
	private ReportFacable reportFac;
	@Autowired
	private BaseDSFacable baseDSFac;
	@Autowired
	private TextFactoryable textFactory;

	private File textFile;// 实际上传文件

	public File getTextFile() {
		return textFile;
	}

	public void setTextFile(File textFile) {
		this.textFile = textFile;
	}

	public List<Long> getSelections() {
		return super.getOperatorPK();
	}

	public void setSelections(List<Long> selections) {
		super.setOperatorPK(selections);
	}

	public TextReport getReportTextVo() {
		return super.getVo();
	}

	public void setReportTextVo(TextReport reportText) {
		super.setVo(reportText);
	}

	@Override
	protected Long getPK(TextReport vo) {
		return vo.getId();
	}

	@Override
	protected TextReport getOperator(Long pk) {
		return reportFac.findByText(pk);
	}

	@Override
	protected void deleteOperator(Long pk) {
		reportFac.deletedText(pk);
	}

	@Override
	protected Long saveOperator(TextReport vo, boolean isUpdate) {
		InputStream in = null;
		try {
			vo.setBaseDS(baseDSFac.findByBaseDS(vo.getBaseDS().getId()));
			if (textFile != null) {
				byte[] buffer = new byte[Integer.parseInt(String
						.valueOf(textFile.length()))];
				in = new BufferedInputStream(new FileInputStream(textFile),
						Integer.parseInt(String.valueOf(textFile.length())));
				in.read(buffer);
				vo.setTextEntity(buffer);
			}
			if (isUpdate) {
				return reportFac.updateText(vo);
			} else {
				return reportFac.saveText(vo);
			}
		} catch (BaseException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
					in = null;
				}
			} catch (IOException e) {

			}
		}
		return null;
	}

	@Override
	protected TextReport createEmptyVo() {
		return new TextReport();
	}

	/**
	 * 数据源选择
	 * 
	 * @return 记录集
	 */
	public List<BaseDS> getBaseDSList() {
		List<BaseDS> list = new ArrayList<BaseDS>();
		try {
			list = baseDSFac.findAllBaseDS();
		} catch (Exception e) {
		}
		return list;
	}

	public void parameterType() {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		for (ParametersType paramEnum : ParametersType.values()) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", paramEnum.name());
			map.put("text", paramEnum.getDescription());
			list.add(map);
		}

		Struts2Util.renderJson(JSONUtil.toJSON(list));
	}

	private Long textId;

	public Long getTextId() {
		return textId;
	}

	public void setTextId(Long textId) {
		this.textId = textId;
	}

	public void download() {
		PrintWriter pw = null;
		InputStream in = null;
		try {
			if (getTextId() != null) {
				TextReport report = reportFac.findByText(getTextId());
				if (report.getTextEntity() != null
						&& report.getTextEntity().length != 0) {
					String fileName = String.valueOf(report.getTextName());
					fileName = URLEncoder.encode(fileName, "UTF-8");
					//fileName = new String(fileName.getBytes("GBK"), "ISO8859-1");

					HttpServletResponse response = ServletActionContext
							.getResponse();
					response.setContentType("application/jrxml");
					response.setCharacterEncoding("UTF-8");
					response.setHeader("Content-disposition",
							"attachment; filename=" + fileName + ".jrxml");

					byte[] bytes = new byte[report.getTextEntity().length];
					bytes = report.getTextEntity();

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

	public void preview() {
		PrintWriter pw = null;
		InputStream in = null;
		try {
			if (getTextId() != null) {
				TextReport report = reportFac.findByText(getTextId());
				Set<Parameter> parameters = report.getParameters();
				Map<String, String> map = new HashMap<String, String>();
				for (Parameter param : parameters) {
					map.put(param.getEnName(), param.getDefaultValue());
				}
				HttpServletResponse response = ServletActionContext
						.getResponse();
				HttpServletRequest request = ServletActionContext.getRequest();

				pw = response.getWriter();
				response.reset();// 清空输出
				response.setContentLength(0);
				byte[] bytes = textFactory.export(map, report, TextType.PDF,
						response, request);
				in = new ByteArrayInputStream(bytes);
				int len = 0;
				while ((len = in.read()) > -1) {
					pw.write(len);
				}
				pw.flush();
			}
		} catch (Exception e) {
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

	private Long categoryId;

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public void findReportText() {
		List<TextReport> texts = reportFac.findAllText();
		if (texts != null) {
			List<ComboBox> comboBoxs = new ArrayList<ComboBox>();
			ComboBox comboBox = null;
			for (TextReport text : texts) {
				comboBox = new ComboBox();
				comboBox.setId(text.getId());
				comboBox.setText(text.getTextName());
				if (getCategoryId() != null) {
					Boolean isEntity = reportFac
							.findTextIsEntityByTextAndCategory(text.getId(),
									getCategoryId());
					if (isEntity)
						comboBox.setSelected(true);
				}
				comboBoxs.add(comboBox);
			}
			Struts2Util.renderJson(JSONUtil.toJSON(comboBoxs
					.toArray(new ComboBox[0])));
		}
	}
}
