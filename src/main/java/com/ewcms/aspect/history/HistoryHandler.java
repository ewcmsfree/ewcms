/**
 * 创建日期 2011-3-31
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.aspect.history;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.aspect.history.dao.HistoryModelDAO;
import com.ewcms.aspect.history.model.HistoryModel;
import com.ewcms.util.EwcmsContextUtil;

/**
 * @author 吴智俊
 */
@Service
@Aspect
public class HistoryHandler {
	protected static final Log log = LogFactory.getLog(HistoryHandler.class);
	
	@Autowired
	private HistoryModelDAO historyModelDAO;

	@SuppressWarnings("unused")
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
			log.info(e.toString());
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
