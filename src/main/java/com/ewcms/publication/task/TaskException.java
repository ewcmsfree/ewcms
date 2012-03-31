/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.task;

public class TaskException extends Exception {

	private static final long serialVersionUID = -1778138042412124141L;

	public TaskException(){}

    public TaskException(String message) {
       super(message);
   }

   public TaskException(String message, Throwable thrwbl) {
       super(message, thrwbl);
   }

   public TaskException(Throwable thrwbl){
       super(thrwbl);
   }
}
