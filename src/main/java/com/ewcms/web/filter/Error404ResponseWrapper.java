/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.web.filter;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * 
 * @author wangwei
 *
 */
class Error404ResponseWrapper extends HttpServletResponseWrapper {
    
    private int status = SC_OK;
    
    public Error404ResponseWrapper(Error404Filter error404Filter, HttpServletResponse response){
        super(response);
    }
    
    @Override
    public void sendError(int sc) throws IOException {
        this.status = sc;
        if(isFound()){
            super.sendError(sc);                
        }else{
            super.setStatus(SC_OK);
        }
    }

    @Override
    public void sendError(int sc, String msg) throws IOException {
        this.status = sc;
        if(isFound()){
            super.sendError(sc,msg);  
        }else{
            super.setStatus(SC_OK);                 
        }
    }
    
    public void setStatus(int status){
       this.status = status;
       super.setStatus(status);
    }
    
    @Override
    public void reset() {
        this.status = SC_OK;
        super.reset();
    }

    
    public boolean isFound(){
        return status != SC_NOT_FOUND;
    }
}