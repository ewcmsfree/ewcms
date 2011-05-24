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
