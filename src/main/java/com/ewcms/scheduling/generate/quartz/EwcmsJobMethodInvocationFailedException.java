/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.scheduling.generate.quartz;

import org.springframework.core.NestedRuntimeException;
import org.springframework.util.MethodInvoker;

/**
 * 
 * @author wuzhijun
 *
 */
public class EwcmsJobMethodInvocationFailedException extends NestedRuntimeException {

	private static final long serialVersionUID = -5853922879523025668L;

	public EwcmsJobMethodInvocationFailedException(MethodInvoker methodInvoker, Throwable cause) {
		super((new StringBuilder("Invocation of method '")).append(methodInvoker.getTargetMethod()).append("' on target class [").append(methodInvoker.getTargetClass()).append("] failed").toString(), cause);
	}
}
