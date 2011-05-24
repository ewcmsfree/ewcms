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
