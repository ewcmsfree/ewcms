/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.plugin.leadingwindow.generator;

import com.ewcms.generator.directive.AbstractDirectiveTest;
import freemarker.template.Configuration;
import freemarker.template.Template;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author wangwei
 */
public class MenuTest extends AbstractDirectiveTest {

    private static final Log log = LogFactory.getLog(MenuTest.class);

    @Override
    protected void setDirective(Configuration cfg) {
    }

    @Test
    public void testExecute() throws Exception {
        Template template = cfg.getTemplate("www/plugin/leading/menuIndex.html");
        LeadingMenu menu = initLeadingMenu();
        cfg.setSharedVariable("menu", menu);

        String value = this.process(template, null);
//        log.info(value);
        System.out.println(value);
    }

    private LeadingMenu initLeadingMenu() {
        LeadingMenu menu = new LeadingMenu(1, "root");
        LeadingMenu menuArea = new LeadingMenu(2000000,"区委领导");
        menuArea.setFocus(true);
        menuArea.addChild(new LeadingMenu(3, "书记")).addChild(new LeadingMenu(4, "朱汉浩"));
        List<LeadingMenu> list = new ArrayList<LeadingMenu>();
        list.add(new LeadingMenu(6, "李广欣"));
        list.add(new LeadingMenu(6, "曹应发"));
        menuArea.addChild(new LeadingMenu(5, "副书记")).setChildren(list);
        menu.addChild(menuArea);

        return menu;
    }
}
