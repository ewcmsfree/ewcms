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
public interface EwcmsContextable {

    public void setSite(Site site);

    public Site getSite();
}
