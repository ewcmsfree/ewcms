/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Before;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * Freemarker标签单元测试超类
 * 
 * <p>完成Freemarker初始化工作，简化Freemarker相关单元测试难度</p>
 *
 * @author wangwei
 */
public abstract class FreemarkerTest{
    private static final Logger logger = LoggerFactory.getLogger(FreemarkerTest.class);
    
    protected static final String DEFAULT_ENCODING = "UTF-8";
    
    protected String encoding = DEFAULT_ENCODING;
    protected Configuration cfg;

    @Before
    public void setBefore() throws Exception {
        cfg = new Configuration();
        cfg.setEncoding(Locale.CHINA, DEFAULT_ENCODING);
        String templateDir = FreemarkerTest.class.getResource(".").getPath();
        cfg.setDirectoryForTemplateLoading(new File(templateDir));
        cfg.setObjectWrapper(new DefaultObjectWrapper());
        currentConfiguration(cfg);
    }

    /**
     * 设置单元测试所需要的Freemarker配置
     * 
     * @param cfg 
     *          Freemarker配置
     */
    protected abstract void currentConfiguration(Configuration cfg);

    /**
     * 生成测试内容
     * 
     * <p>方便对测试测试内容的判断，直接把生成结果以字符串的形式返回。</p>
     * 
     * @param template  
     *               模板对象
     * @param value     
     *               写入模板的值
     * @return       生成内容   
     * @throws Exception
     */
    protected String process(Template template,Object value){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Writer writer = new OutputStreamWriter(stream);
        try{
            template.process(value, writer);      
            String content =  stringOf(stream.toByteArray());
            writer.close();
            stream.close();
            return content;
        }catch(Exception e){
            logger.error("Freemarker process exception:{}",e.toString());
            return "throws Exception";
        }
    }

    protected String stringOf(byte[] array) throws UnsupportedEncodingException {
        String value = new String(array, encoding);
        return value;
    }
    
    /**
     * 设置Freemarker字符编码
     * 
     * @param encoding 字符编码,缺省"UTF-8"。
     */
    public void setEncoding(String encoding){
        this.encoding = encoding;
    }
}
