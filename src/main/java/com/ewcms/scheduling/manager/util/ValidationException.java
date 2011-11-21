/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.manager.util;

import com.ewcms.scheduling.BaseException;
import com.ewcms.scheduling.generate.common.ValidationErrorsable;

/**
 * @author 吴智俊
 */
public class ValidationException extends BaseException {
	
	private static final long serialVersionUID = -900119303006387745L;
	
	private final ValidationErrorsable errors;
	
	public ValidationException(ValidationErrorsable errors) {
		super(errors.toString(), errors.toString());
		
		this.errors = errors;
	}
	
	public ValidationErrorsable getErrors() {
		return errors;
	}
	
}
