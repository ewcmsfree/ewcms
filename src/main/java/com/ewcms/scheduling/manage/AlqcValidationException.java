/**
 * 创建日期 2009-4-21
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.scheduling.manage;

import com.ewcms.scheduling.BaseException;
import com.ewcms.scheduling.common.ValidationErrorsable;

/**
 * @author 吴智俊
 */
public class AlqcValidationException extends BaseException {
	
	private static final long serialVersionUID = -900119303006387745L;
	
	private final ValidationErrorsable errors;
	
	public AlqcValidationException(ValidationErrorsable errors) {
		super(errors.toString(), errors.toString());
		
		this.errors = errors;
	}
	
	public ValidationErrorsable getErrors() {
		return errors;
	}
	
}
