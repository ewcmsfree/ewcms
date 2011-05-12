package com.ewcms.comm;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;

public class EwcmsMessageSource extends ResourceBundleMessageSource {
    
    public EwcmsMessageSource (){
        this.setBasenames(new String[]{"com.ewcms.security.messages"});
    }
    
    public static MessageSourceAccessor getAccessor() {
        return new MessageSourceAccessor(new EwcmsMessageSource());
    }
}
