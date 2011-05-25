/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/*
 *
 */
package com.ewcms.web.context;

/**
 * 
 */
public class EwcmsContextHolder {
    private static EwcmsContextHolderStrategy strategy;
    private static int initializeCount = 0;

    static {
        initialize();
    }

    public static void clearContext() {
        strategy.clearContext();
    }

    public static EwcmsContextable getContext() {
        return strategy.getContext();
    }

    public static int getInitializeCount() {
        return initializeCount;
    }

    private static void initialize() {
        strategy = new ThreadLocalEwcmsContextHolderStrategy();
        initializeCount++;
    }

    public static void setContext(EwcmsContextable context) {
        strategy.setContext(context);
    }

    @Override
    public String toString() {
        return String.format("EwcmsContextHolder["+"initializeCount=d%]", initializeCount);
    }
}
