/* 
 */
package com.ewcms.context;

import org.springframework.util.Assert;

/**
 */
public class ThreadLocalEwcmsContextHolderStrategy implements EwcmsContextHolderStrategy {
    private static ThreadLocal contextHolder = new ThreadLocal();

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
