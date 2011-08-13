/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.freemarker.html;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.xwork.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.publication.PublishException;
import com.ewcms.publication.freemarker.GlobalVariable;
import com.ewcms.publication.output.OutputResource;
import com.ewcms.publication.uri.UriRule;
import com.ewcms.publication.uri.UriRuleable;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 *  生成html页面
 *  
 * @author wangwei
 */
public abstract class GeneratorBase implements Generatorable {
    private static final Logger logger = LoggerFactory.getLogger(GeneratorBase.class);

    /**
     * 生成临时文件名称指定的范围
     */
    private static final String FILE_NAME_CHARS = "1234567890abcdefghigklmnopqrstuvwxyz";
    
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
     * 写出模板生成内容
     * 
     * @param template   模板对象
     * @param parameters 参数
     * @param rule       链接地址生成规则
     * @param writer     输出对象
     * @throws TemplateException
     * @throws IOException
     */
    protected void write(Template template,Map<String, Object> parameters,UriRuleable rule,Writer writer)throws PublishException{
        completeParameters(parameters,rule);
        try{
            template.process(parameters, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            logger.error("Writer tempfile error {}", e);
            throw new PublishException(e);
        } catch (TemplateException e) {
            logger.error("Freemarker proccess error {}", e);
            throw new PublishException(e);
        }
    }

    /**
     * 生成指定的页面
     * 
     * @param template    模板对象
     * @param parameters  参数集合
     * @param rule        链接地址生成规则
     * @return
     * @throws PublishException
     */
    protected OutputResource generator(Template template,Map<String, Object> parameters,UriRuleable rule) throws PublishException {
       return generator(template,parameters,rule,rule);
    }
    
    /**
     * 生成指定的页面
     * 
     * @param template    模板对象
     * @param parameters  参数集合
     * @param pageRule    翻页链接地址生成规则
     * @param fileRule    页面文件地址生成规则
     * @return
     * @throws PublishException
     */
    protected OutputResource generator(Template template,Map<String,Object> parameters,UriRuleable pageRule,UriRuleable fileRule)throws PublishException{
    	 try {
             File temp = createTempFile();
             fileRule.setParameters(parameters);
             String uri = fileRule.getUri();
             write(template,parameters,pageRule,new FileWriter(temp));
             return new OutputResource(temp.getPath(), uri);
         } catch (IOException e) {
             logger.error("Writer tempfile error {}", e);
             throw new PublishException(e);
         } 
    }
    
    /**
     * 得到链接地址生成规则
     * 
     * @param patter      生成模板
     * @param defaultRule 缺省生成规则
     * @return
     * @throws PublishException
     */
    protected UriRuleable getUriRule(String patter,UriRuleable defaultRule)throws PublishException{
        if(StringUtils.isBlank(patter)){
            return defaultRule;
        }else{
            UriRuleable rule = new UriRule();
            rule.parse(patter);
            return rule;
        }
    }
   
}
