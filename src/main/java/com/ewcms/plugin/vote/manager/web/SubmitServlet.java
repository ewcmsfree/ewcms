/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.plugin.vote.manager.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ewcms.plugin.vote.manager.VoteFacable;
import com.ewcms.plugin.vote.model.Person;
import com.ewcms.plugin.vote.model.Questionnaire;
import com.ewcms.plugin.vote.model.Record;
import com.octo.captcha.service.CaptchaService;
import com.octo.captcha.service.CaptchaServiceException;

/**
 * 提交调查投票内容
 * 
 * @author 吴智俊
 */
public class SubmitServlet extends HttpServlet {

	private static final long serialVersionUID = 2041752401130109359L;

	protected static final Logger logger = LoggerFactory.getLogger(SubmitServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletOutputStream out = null;
		String output = "";
		try {
			Boolean voteEnd = Boolean.valueOf(req.getParameter("voteEnd"));
			if (voteEnd) {
				output = "投票已经结束！";
			} else {
				String questionnaireId = req.getParameter("questionnaireId");
				String ipAddr = req.getRemoteAddr();
				logger.info("IP : " + ipAddr);

				ServletContext application = getServletContext();
				WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(application);

				VoteFacable voteFac = (VoteFacable) wac.getBean("voteFac");
				Questionnaire questionnaire = voteFac.findQuestionnaire(new Long(questionnaireId));
				Boolean captcha = true;

				if (questionnaire.getVerifiCode()) {
					CaptchaService captchaService = (CaptchaService) wac.getBean("captchaService");
					String checkcode = req.getParameter("j_checkcode");
					;
					if (checkcode == null) {
						checkcode = "";
					}
					checkcode = checkcode.toUpperCase();
					String captchaId = req.getSession().getId();
					captcha = captchaService.validateResponseForID(captchaId, checkcode);
				}

				if (!captcha) {
					output = "验证码不正确";
				} else {
					Boolean isEntity = false;
					if (questionnaire.getIsIpLimit()) {
						isEntity = voteFac.findPersonIsEntity(new Long(questionnaireId), ipAddr);
						if (isEntity) {
							output = "您已经投过票了！";
						}
					} else {
						if (questionnaire.getIsIpDay()) {
							isEntity = voteFac.findPersonIsEntityToDay(new Long(questionnaireId), ipAddr);
							if (isEntity) {
								output = "您今天已经投过票了，明天再投票！";
							}
						}
					}
					if (!isEntity) {
						Enumeration<String> paramNames = req.getParameterNames();

						Person person = new Person();
						person.setIp(ipAddr);
						person.setQuestionnaireId(new Long(questionnaireId));

						List<Record> records = new ArrayList<Record>();
						while (paramNames.hasMoreElements()) {
							String parameterName = paramNames.nextElement();
							if (parameterName.indexOf("Subject") == 0) {
								String[] parameterValue = req.getParameterValues(parameterName);
								if (parameterValue.length > 0) {
									for (String value : parameterValue) {
										logger.info(parameterName + " : " + value);
										if (value.equals(""))
											continue;

										Record record = new Record();
										record.setSubjectName(parameterName);
										record.setSubjectValue(value);
										records.add(record);
									}
								}
							}
						}
						person.setRecords(records);
						removeRepeat(person);
						voteFac.addPerson(person);
						output = "投票成功，感谢您的投票！";
					}
				}
			}
		} catch (CaptchaServiceException e) {
			output = "验证码不正确";
		} catch (Exception e) {
			output = "参数不正确";
		} finally {
			output += "<p><a href='javascript:history.go(-1);'>返回<a>";

			out = resp.getOutputStream();
			resp.setCharacterEncoding("utf-8");
			resp.setContentType("text/html; charset=utf-8");
			out.write(output.toString().getBytes("UTF-8"));
			out.flush();

			if (out != null) {
				out.close();
				out = null;
			}
		}
	}

	private void removeRepeat(Person person) {
		List<Record> records = person.getRecords();
		List<Record> records_new = new ArrayList<Record>();
		for (Record record : records) {
			String subjectName = record.getSubjectName();
			String[] subjectNames = subjectName.split("_");
			if (subjectNames.length == 4) {
				String subjectId = "Subject_" + subjectNames[1];
				String subjectItemId = subjectNames[3];
				Boolean isEntity = false;
				for (Record record_keep : person.getRecords()) {
					if (record_keep.getSubjectName().equals(subjectId) && record_keep.getSubjectValue().equals(subjectItemId)) {
						isEntity = true;
						break;
					}
				}
				if (isEntity) {
					records_new.add(record);
				}
			} else {
				records_new.add(record);
			}
		}
		person.setRecords(records_new);
	}
}
