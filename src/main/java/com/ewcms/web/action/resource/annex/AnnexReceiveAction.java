/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ewcms.web.action.resource.annex;

import com.ewcms.core.resource.model.ResourceType;
import com.ewcms.web.action.resource.ReceiveAction;

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
