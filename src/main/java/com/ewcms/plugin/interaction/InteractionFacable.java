/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ewcms.plugin.interaction;

import com.ewcms.plugin.interaction.model.Interaction;

/**
 *
 * @author wangwei
 */
public interface InteractionFacable {

    public Interaction getInteraction(Integer id);
    
    public void interactionChecked(Integer id,Boolean checked);

    public void interactionReplay(Integer id,String replay);

    public void interactionOrgan(Integer id,Integer organId,String organName);

     public void speakChecked(Integer id,boolean checked);

      public void interactionBackRatio(Integer id) ;

}
