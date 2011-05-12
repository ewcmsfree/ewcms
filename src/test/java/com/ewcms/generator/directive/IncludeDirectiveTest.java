/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.generator.directive;

import com.ewcms.core.site.model.Site;
import com.ewcms.generator.dao.GeneratorDAOable;
import freemarker.template.Configuration;
import freemarker.template.Template;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author wangwei
 */
public class IncludeDirectiveTest extends AbstractDirectiveTest {

    private static final Log log = LogFactory.getLog(IncludeDirectiveTest.class);
    @Test
    public void testExecute() throws Exception {
        Template template = cfg.getTemplate("www/include/index.html");
        Map params = new HashMap();
        Site site = new Site();
        site.setId(1);
        params.put(DirectiveVariable.CurrentSite.toString(), site);
        params.put(DirectiveVariable.Debug.toString(), true);
        String value = process(template,params);
        log.info(value);
        Assert.assertTrue(value.indexOf("test Include") != -1);
    }

    @Override
    protected void setDirective(Configuration cfg) {
      //TODO mockito instend jmock
//        IncludeDirective directive = new IncludeDirective();
//        Mockery context = new Mockery();
//        final GeneratorDAOable dao = context.mock(GeneratorDAOable.class);
//        context.checking(new Expectations(){{
//            oneOf(dao).getSiteDir(1);will(returnValue("www"));
//        }});
//        directive.setDAO(dao);
//        cfg.setSharedVariable("include", directive);
    }
}
