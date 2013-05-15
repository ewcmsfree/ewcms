/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ewcms.plugin.contribute;

import java.util.List;

import com.ewcms.plugin.contribute.model.Contribute;
import com.ewcms.plugin.contribute.service.ContributeServiceable;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author wangwei
 */
@Service
public class ContributeFac implements ContributeFacable {

    @Autowired
    private ContributeServiceable contributeService;

    @Override
    public void contributeChecked(Long id,Boolean checked) {
        contributeService.contributeChecked(id,checked);
    }

    @Override
    public Contribute getContribute(Long id) {
        return contributeService.getContribute(id);
    }

	@Override
	public void deleteContribute(List<Long> ids) {
		contributeService.deleteContribute(ids);
	}

}
