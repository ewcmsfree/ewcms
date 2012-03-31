/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/* 
 */
package com.ewcms.web.context;

import org.springframework.util.Assert;

/**
 */
public class ThreadLocalEwcmsContextHolderStrategy implements EwcmsContextHolderStrategy {
    private static ThreadLocal<EwcmsContextable> contextHolder = new ThreadLocal<EwcmsContextable>();

    @Override
    public void clearContext() {
        contextHolder.remove();
    }

    @Override
    public EwcmsContextable getContext() {
        if (contextHolder.get() == null) {
            contextHolder.set(new EwcmsContext());
        }

        return (EwcmsContextable) contextHolder.get();
    }

    @Override
    public void setContext(EwcmsContextable context) {
        Assert.notNull(context, "Only non-null SecurityContext instances are permitted");
        contextHolder.set(context);
    }
}
