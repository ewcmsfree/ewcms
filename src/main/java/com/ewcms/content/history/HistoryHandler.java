/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.history;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.content.history.dao.HistoryModelDAO;
import com.ewcms.content.history.model.HistoryModel;
import com.ewcms.web.util.EwcmsContextUtil;

/**
 * @author 吴智俊
 */
@Service
@Aspect
public class HistoryHandler {
	protected static final Logger logger = LoggerFactory.getLogger(HistoryHandler.class);
	
	@Autowired
	private HistoryModelDAO historyModelDAO;

	@Pointcut("@annotation(ahistory)")
	private void save(History ahistory) {
	}

	@After(value = "save(ahistory)", argNames = "ahistory")
	public void saveCompress(JoinPoint pjp, History ahistory) throws Throwable {
		int modelObjectIndex = ahistory.modelObjectIndex();
		if (modelObjectIndex <= -1){
			return;
		}
		Object[] args = pjp.getArgs();
		ByteArrayOutputStream out = null;
		ObjectOutputStream outputStream = null;
		try {
			HistoryModel historyModel = new HistoryModel();

			String packageName = args[modelObjectIndex].getClass().getPackage().getName();
			String modelName = args[modelObjectIndex].getClass().getSimpleName();
			historyModel.setClassName(packageName + "." + modelName);
			historyModel.setUserName(EwcmsContextUtil.getUserName());

			historyModel.setMethodName(pjp.getSignature().getName());

			out = new ByteArrayOutputStream();
			outputStream = new ObjectOutputStream(out);
			outputStream.writeObject(args[modelObjectIndex]);
			byte[] bytes = out.toByteArray();
			historyModel.setModelObject(bytes);

			Field[] fields = args[modelObjectIndex].getClass().getDeclaredFields();
			Boolean isId = false;
			for (int j = 0; j < fields.length; j++) {
				Annotation[] annotations = fields[j].getAnnotations();
				for (int k = 0; k < annotations.length; k++) {
					String annotationsName = annotations[k].toString();
					if (annotationsName.equals("@javax.persistence.Id()")) {
						isId = true;
						fields[j].setAccessible(true);
						historyModel.setIdName(fields[j].getName());
						historyModel.setIdType(fields[j].getType().getName());
						historyModel.setIdValue((fields[j].get(args[modelObjectIndex])).toString());
						break;
					}
				}
				if (isId) {
					break;
				}
			}
			historyModelDAO.persist(historyModel);
			historyModelDAO.flush(historyModel);
		} catch (Exception e) {
			logger.info(e.toString());
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
				}
			}
			out = null;
			outputStream = null;
		}
	}
}
