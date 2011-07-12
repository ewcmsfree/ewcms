/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.html;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.generator.PublishException;
import com.ewcms.generator.freemarker.GlobalVariable;
import com.ewcms.generator.output.OutputResource;
import com.ewcms.generator.uri.UriRuleable;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 *  生成html页面
 *  
 * @author wangwei
 */
public abstract class GeneratorHtmlBase implements GeneratorHtmlable {
    private static final Logger logger = LoggerFactory.getLogger(GeneratorHtmlBase.class);

    /**
     * 得到Freemarker中模板
     * 
     * @param cfg freemaker配置对象
     * @param path 模板路径
     * @return
     * @throws PublishException
     */
    protected Template getFreemarkerTemplate(Configuration cfg,String path)throws PublishException{
        try {
            return cfg.getTemplate(path);
        } catch (IOException e) {
            logger.error("{} template is not exist.error:",path,e);
            throw new PublishException(e);
        }
    }
    /**
     * 生成指定的页面
     * 
     * @param template
     * @param rule
     * @param parameters
     * @return
     * @throws PublishException
     */
    protected OutputResource generator(Template template,Map<String, Object> parameters,UriRuleable rule) throws PublishException {
        
        try {
            completeParameters(parameters,rule);
            File temp = createTempFile();
            Writer out = new FileWriter(temp);
            template.process(parameters, out);
            out.flush();
            out.close();
            return new OutputResource(temp.getPath(), rule.getUri());
        } catch (IOException e) {
            logger.error("Writer tempfile error {}", e);
            throw new PublishException(e);
        } catch (TemplateException e) {
            logger.error("Freemarker proccess error {}", e);
            throw new PublishException(e);
        }
    }
    
    /**
     * 创建临时文件
     * 
     * @return 临时文件
     * @throws IOException
     */
    protected File createTempFile()throws IOException{
        String tempFileName = RandomStringUtils.random(32, FILE_NAME_CHARS);
        logger.debug("Temp file's name is {}", tempFileName);
        File file = File.createTempFile(tempFileName, ".html");
        logger.debug("Temp file's path is {}", file.getPath());
        
        return file;
    }
    
    /**
     * 完整生成页面参数
     * 
     * @param parameters 参数集合
     * @param rule uri生成规则
     */
    protected void completeParameters(Map<String,Object> parameters,UriRuleable rule){
        rule.setParameters(parameters);
        if(!parameters.containsKey(GlobalVariable.URI_RULE.toString())){
            parameters.put(GlobalVariable.URI_RULE.toString(), rule);
        }
    }

}
