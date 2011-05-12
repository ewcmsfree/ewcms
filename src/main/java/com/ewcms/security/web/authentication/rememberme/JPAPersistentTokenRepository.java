/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ewcms.security.web.authentication.rememberme;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ewcms.security.web.authentication.rememberme.dao.RememberMeTokenDAO;
import com.ewcms.security.web.authentication.rememberme.model.RememberMeToken;

/**
 *
 * @author wangwei
 */
@Service
public class JPAPersistentTokenRepository implements PersistentTokenRepository{

    @Autowired
    private RememberMeTokenDAO rememberMeTokenDAO;

    public void setRememberMeTokenDAO(RememberMeTokenDAO rememberMeTokenDAO){
        this.rememberMeTokenDAO = rememberMeTokenDAO;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void createNewToken(PersistentRememberMeToken token) {
        
        IPPersistentRememberMeToken  rememberMeToken = (IPPersistentRememberMeToken)token;
        RememberMeToken model = new RememberMeToken();

        model.setId(token.getSeries());
        model.setIpAddress(rememberMeToken.getIpAddress());
        model.setToken(token.getTokenValue());
        model.setUsername(token.getUsername());
        model.setLastUsed(new Date());
        
        rememberMeTokenDAO.persist(model);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        
        RememberMeToken model = rememberMeTokenDAO.get(series);
        model.setToken(tokenValue);
        model.setLastUsed(lastUsed);
        rememberMeTokenDAO.persist(model);
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        RememberMeToken model = rememberMeTokenDAO.get(seriesId);
        if(model == null){
            return null;
        }
        return new IPPersistentRememberMeToken(model.getUsername(),model.getId(),
                model.getToken(),model.getLastUsed(),model.getIpAddress());
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void removeUserTokens(String username) {
        this.rememberMeTokenDAO.removeUserTokens(username);
    }
}
