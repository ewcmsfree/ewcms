/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.plugin.contribute.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.plugin.contribute.dao.ContributeDAO;
import com.ewcms.plugin.contribute.model.Contribute;

/**
 *
 * @author wangwei
 */
@Service
public class ContributeService implements ContributeServiceable {

    @Autowired
    private ContributeDAO contributeDAO;

    @Override
    public Contribute getContribute(Long id) {
    	if (id == null) return new Contribute();
        return contributeDAO.get(id);
    }

    @Override
    public void contributeChecked(Long id, Boolean checked) {
        Contribute contribute = contributeDAO.get(id);
        if (contribute == null) {
            return;
        }
        contribute.setChecked(checked);
        contributeDAO.persist(contribute);
    }

	@Override
	public void deleteContribute(List<Long> ids) {
		for (Long id : ids){
			contributeDAO.removeByPK(id);
		}
	}

}
