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
