/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.plugin.interaction.dao;

import com.ewcms.comm.jpa.dao.JpaDAO;
import com.ewcms.plugin.interaction.model.Interaction;
import com.ewcms.plugin.interaction.model.InteractionRatio;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.stereotype.Repository;

/**
 *
 * @author wangwei
 */
@Repository
public class InteractionDAO extends JpaDAO<Integer, Interaction> {

    private final static Integer BACK_STATE = 1;
    private final static Integer INIT_STATE = 0;

    public void interactionBackRatio(Integer id) {
        int all = getBackInteractionCount(null);
        int ratio = -1;
        if (all != 0) {
            int back = getBackInteractionCount(id);
            ratio = (back * 100) / all;
        }

        all = getNoBackInteractionCount(null);
        int noRatio = -1;
        if (all != 0) {
            int noBack = getNoBackInteractionCount(id);
            noRatio = (noBack * 100) / all;
        }
        
        InteractionRatio interactionRatio= this.getJpaTemplate().find(InteractionRatio.class, id);
        if(interactionRatio != null){
            interactionRatio.setNoRatio(noRatio);
            interactionRatio.setRatio(ratio);
        }else{
            interactionRatio = new InteractionRatio();
            interactionRatio.setId(id);
            interactionRatio.setNoRatio(noRatio);
            interactionRatio.setRatio(ratio);
        }
        getJpaTemplate().persist(interactionRatio);
    }

    private Integer getBackInteractionCount(final Integer id){
           Object count = this.getJpaTemplate().execute(new JpaCallback() {

            @Override
            public Object doInJpa(EntityManager em) throws PersistenceException {
               String hql = "Select count(o.id) From Interaction o Where o.state = ?";
               Query query;
               if(id == null){
                    query = em.createQuery(hql);
                    query.setParameter(1, BACK_STATE);

               }else{
                   hql = hql + " And organId = ?";
                    query = em.createQuery(hql);
                    query.setParameter(1, BACK_STATE);
                    query.setParameter(2, id);
               }
               Number number = (Number) query.getSingleResult();
               return number.intValue();
            }
        });

        return (Integer) count;
    }

     private Integer getNoBackInteractionCount(final Integer id){
           Object count = this.getJpaTemplate().execute(new JpaCallback() {

            @Override
            public Object doInJpa(EntityManager em) throws PersistenceException {
               String hql = "Select count(o.id) From Interaction o Where o.checked = true And o.state = ?";
               Query query;
               if(id == null){
                    query = em.createQuery(hql);
                    query.setParameter(1, INIT_STATE);

               }else{
                   hql = hql + " And organId = ?";
                    query = em.createQuery(hql);
                    query.setParameter(1, INIT_STATE);
                    query.setParameter(2, id);
               }
               Number number = (Number) query.getSingleResult();
               return number.intValue();
            }
        });

        return (Integer) count;
    }
}
