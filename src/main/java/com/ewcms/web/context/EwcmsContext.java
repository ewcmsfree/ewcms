/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ewcms.web.context;

import com.ewcms.core.site.model.Site;

/**
 *
 * @author wangwei
 */
public class EwcmsContext implements EwcmsContextable{

    private Site site;

    @Override
    public void setSite(Site site) {
        this.site = site;
    }

    @Override
    public Site getSite() {
        return this.site;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EwcmsContext other = (EwcmsContext) obj;
        if (this.site != other.site && (this.site == null || !this.site.equals(other.site))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
         if(this.site == null){
             return -1;
         }else{
             return this.site.hashCode();
         }
    }
}
