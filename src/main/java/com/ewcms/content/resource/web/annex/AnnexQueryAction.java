/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ewcms.content.resource.web.annex;

import com.ewcms.content.resource.model.ResourceType;
import com.ewcms.content.resource.web.ResourceQueryAction;

/**
 *
 * @author wangwei
 */
public class AnnexQueryAction extends ResourceQueryAction{

    @Override
    protected ResourceType resourceType() {
       return ResourceType.ANNEX;
    }

}
