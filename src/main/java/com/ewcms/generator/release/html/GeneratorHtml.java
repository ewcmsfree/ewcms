/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.generator.release.html;

import com.ewcms.core.GlobaPath;
import com.ewcms.generator.release.ReleaseException;
import com.ewcms.generator.directive.page.PageParam;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author wangwei
 */
public abstract class GeneratorHtml<T> implements GeneratorHtmlable<T> {

    private static final Log log = LogFactory.getLog(GeneratorHtml.class);
    
    @Override
    public void process(final Configuration cfg,final Writer writer, final T object,PageParam pageParam) throws ReleaseException {
        process(cfg,writer, object,pageParam, false);
    }

    @Override
    public void processDebug(final Configuration cfg,final Writer writer, final T object,PageParam pageParam) throws ReleaseException {
        process(cfg,writer, object, pageParam, true);
    }

    private void process(final Configuration cfg,final Writer writer, final T object ,PageParam pageParam, final boolean debug) throws ReleaseException {

        try {
            Template template = getTemplate(cfg,object);
            if (template == null) {
                 return ;
            }
            Map params = constructParams(object,pageParam,debug);
            template.process(params, writer);
        } catch (IOException e) {
            log.error(e.toString());
            throw new ReleaseException(e);
        } catch (TemplateException e) {
            log.error(e.toString());
            throw new ReleaseException(e);
        }
    }
    
    protected abstract Template getTemplate(Configuration cfg,T object) throws IOException;

    protected abstract Map constructParams(T object ,PageParam pageParam, boolean debug);
}
