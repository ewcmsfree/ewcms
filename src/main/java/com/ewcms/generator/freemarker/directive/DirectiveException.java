/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.generator.freemarker.directive;

import java.io.IOException;
import java.io.Writer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author wangwei
 */
public class DirectiveException extends Exception {

    private static final Log log = LogFactory.getLog(DirectiveException.class);
    
    public DirectiveException() {
    }

    public DirectiveException(String message) {
        super(message);
    }

    public DirectiveException(String message, Throwable thrwbl) {
        super(message, thrwbl);
    }

    public DirectiveException(Throwable thrwbl){
        super(thrwbl);
    }

    public void render(Writer writer){
        try{
            writer.write(this.getMessage());
        }catch(IOException e){
            log.error(e.getMessage());
        }
    }
}
