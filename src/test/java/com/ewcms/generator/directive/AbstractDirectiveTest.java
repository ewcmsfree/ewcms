/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.generator.directive;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Locale;
import org.junit.Before;

/**
 *
 * @author wangwei
 */
public abstract class AbstractDirectiveTest {

    protected static final String TEMPLATEDIR = "src/test/resources/template/";
    protected static final String ENCODING = "UTF-8";
    protected Configuration cfg;

    @Before
    public void befor() throws Exception {
        cfg = new Configuration();
        cfg.setEncoding(Locale.CHINA, ENCODING);
        cfg.setDirectoryForTemplateLoading(new File(TEMPLATEDIR));
        cfg.setObjectWrapper(new DefaultObjectWrapper());
        setDirective(cfg);
    }

    protected abstract  void setDirective(Configuration cfg);

    protected String process(Template template,Object value)throws Exception{
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Writer writer = createWriter(stream);
        template.process(value, writer);

        return  stringValueOf(stream.toByteArray());
    }

    
    protected Writer createWriter(OutputStream stream) throws Exception {
        return new OutputStreamWriter(stream);
    }

    protected String stringValueOf(byte[] array) throws UnsupportedEncodingException {
        String value = new String(array, ENCODING);
        return value;
    }
}
