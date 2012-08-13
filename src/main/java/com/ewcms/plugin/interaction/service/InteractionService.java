/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.plugin.interaction.service;

import com.ewcms.plugin.interaction.dao.InteractionDAO;
import com.ewcms.plugin.interaction.dao.SpeakDAO;
import com.ewcms.plugin.interaction.model.Interaction;
import com.ewcms.plugin.interaction.model.Speak;
import java.util.Date;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author wangwei
 */
@Service
public class InteractionService {

    @Autowired
    private InteractionDAO interactionDAO;
    @Autowired
    private SpeakDAO speakDAO;

    public Interaction getInteraction(Integer id) {
    	if (id == null) return new Interaction();
        return interactionDAO.get(id);
    }

    public void interactionChecked(Integer id, Boolean checked) {
        Interaction interaction = interactionDAO.get(id);
        if (interaction == null) {
            return;
        }
        interaction.setChecked(checked);
        interactionDAO.persist(interaction);
    }

    public void interactionReplay(Integer id, String replay) {
        Interaction interaction = interactionDAO.get(id);
        if (interaction == null) {
            return;
        }
        if (replay == null || replay.trim().length() == 0) {
            interaction.setState(0);
            interaction.setReplay(null);
            interaction.setReplayDate(null);
        } else {
            interaction.setState(1);
            interaction.setReplay(replay);
            interaction.setReplayDate(new Date());
        }

        interactionDAO.persist(interaction);
    }

    public void interactionOrgan(Integer id, Integer organId, String organName) {
        Interaction interaction = interactionDAO.get(id);
        if (interaction == null) {
            return;
        }
        interaction.setOrganId(organId);
        interaction.setOrganName(organName);
        interactionDAO.persist(interaction);
    }

    public void speakChecked(Integer id, boolean checked) {
        Speak speak = speakDAO.get(id);
        if (speak == null) {
            return;
        }
        speak.setChecked(checked);
        speakDAO.persist(speak);
    }

    public void interactionBackRatio(Integer id) {
        interactionDAO.interactionBackRatio(id);
    }
}
