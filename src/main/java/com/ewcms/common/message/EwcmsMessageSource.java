/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.common.message;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;

public class EwcmsMessageSource extends ResourceBundleMessageSource {
    
    public EwcmsMessageSource (){
        this.setBasenames(new String[]{
                "com.ewcms.security.messages",
                "com.ewcms.publication.messages",
                "com.ewcms.publication.preview.service.article"});
    }
    
    public static MessageSourceAccessor getAccessor() {
        return new MessageSourceAccessor(new EwcmsMessageSource());
    }
}
