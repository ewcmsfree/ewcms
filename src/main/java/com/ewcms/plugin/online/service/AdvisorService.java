/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.plugin.online.service;

import com.ewcms.plugin.online.dao.AdvisorDAO;
import com.ewcms.plugin.online.model.Advisor;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author wangwei
 */
@Service
public class AdvisorService implements AdvisorServiceable{

    @Autowired
    private AdvisorDAO advisorDAO;

    @Override
    public void release(Integer id,boolean pub){
        Advisor advisor = advisorDAO.get(id);
        if(advisor == null){
            return ;
        }
        advisor.setChecked(pub);
        advisorDAO.persist(advisor);
    }

    @Override
    public void replay(Integer id, String replay) {
        Advisor advisor = advisorDAO.get(id);
        if(advisor == null){
            return ;
        }
        advisor.setState(1);
        advisor.setReplay(replay);
        if(advisor.getReplay() != null){
            advisor.setReplayDate(new Date());
        }
        advisorDAO.persist(advisor);
    }

    @Override
    public Advisor get(Integer id){
    	if (id == null) return new Advisor();
        return advisorDAO.get(id);
    }
}
