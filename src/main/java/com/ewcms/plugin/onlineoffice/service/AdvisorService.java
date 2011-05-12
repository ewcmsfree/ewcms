/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.plugin.onlineoffice.service;

import com.ewcms.plugin.onlineoffice.dao.AdvisorDAO;
import com.ewcms.plugin.onlineoffice.model.Advisor;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author wangwei
 */
@Service
public class AdvisorService {

    @Autowired
    private AdvisorDAO advisorDAO;

    public void release(Integer id,boolean pub){
        Advisor advisor = advisorDAO.get(id);
        if(advisor == null){
            return ;
        }
        advisor.setChecked(pub);
        advisorDAO.persist(advisor);
    }

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

    public Advisor get(Integer id){
        return advisorDAO.get(id);
    }
}
