/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.security.web.authentication.rememberme.dao;

import com.ewcms.common.jpa.dao.JpaDAO;
import com.ewcms.security.web.authentication.rememberme.model.RememberMeToken;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.stereotype.Repository;

/**
 *
 * @author wangwei
 */
@Repository
public class RememberMeTokenDAO extends JpaDAO<String, RememberMeToken> {

    public void removeUserTokens(final String username) {
        getJpaTemplate().execute(new JpaCallback<Object>() {
            @Override
            public Object doInJpa(EntityManager em) throws PersistenceException {
                String hql = "Delete From RememberMeToken o Where o.username = :username ";
                em.createQuery(hql).setParameter("username", username).executeUpdate();
                return null;
            }
        });
    }
}
