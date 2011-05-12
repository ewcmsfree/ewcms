/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ewcms.plugin.leadingwindow.generator;

import org.junit.Test;
import org.junit.Assert;

/**
 *
 * @author wangwei
 */
public class LeadingReleaseTest {

    @Test
    public void testTranslatParam(){
        LeadingMenu menu =  initLeadingMenu();
        translatParam(menu,true);
    }

    private void translatParam(LeadingMenu menu,boolean home){
        System.out.println(String.valueOf(home));
        for(LeadingMenu child : menu.getChildren()){
            if(child.getId() > 3){
                home = false;
                translatParam(child, home);
            }else{
                home = false;
                translatParam(child,home);
            }

        }
    }


    @Test
    public void testMenuFocus(){
        LeadingMenu menu =  initLeadingMenu();
        LeadingRelease release = new LeadingRelease();
        release.menuFocus(menu, 4);

        Assert.assertTrue(menu.isFocus());
        Assert.assertTrue(menu.getChildren().get(1).isFocus());
        Assert.assertTrue(menu.getChildren().get(1).getChildren().get(0).isFocus());

        Assert.assertFalse(menu.getChildren().get(0).isFocus());
        Assert.assertFalse(menu.getChildren().get(2).isFocus());
        Assert.assertFalse(menu.getChildren().get(2).getChildren().get(0).isFocus());
    }

    private LeadingMenu initLeadingMenu(){
        LeadingMenu menu = new LeadingMenu(1,"1");
        menu.addChild(new LeadingMenu(2,"2"));
        menu.addChild(new LeadingMenu(3,"3")).addChild(new LeadingMenu(4,"4"));
        menu.addChild(new LeadingMenu(5,"5")).addChild(new LeadingMenu(6,"6"));
        return menu;
    }

}
