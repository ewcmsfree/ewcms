/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ewcms.content.resource.web.annex;

import com.ewcms.content.resource.model.ResourceType;
import com.ewcms.content.resource.web.ReceiveAction;

/**
 *
 * @author wangwei
 */
public class AnnexReceiveAction extends ReceiveAction{

    @Override
    protected ResourceType resourceType() {
        return ResourceType.ANNEX;
    }
 }
