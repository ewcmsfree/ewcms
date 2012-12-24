/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.freemarker.generator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.publication.PublishException;
import com.ewcms.publication.freemarker.GlobalVariable;
import com.ewcms.publication.generator.Generatorable;
import com.ewcms.publication.uri.UriRuleable;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 *  生成页面
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
     * 生成页面缺省编码
     */
    private static final String DEFAULT_CHARSET="UTF-8";
    
    protected final Site site;
    protected final Channel channel;
    protected final UriRuleable uriRule;
    protected final Configuration cfg;
    protected final String charset = DEFAULT_CHARSET;
    protected boolean debug = false;
       
    /**
     * 生成页面构造函数
     * 
     * @param cfg freemarker 配置对象
     * @param site 站点
     * @param channel 频道
     * @param uriRule uri生成规则（分页链接使用）
     */
    public GeneratorBase(Configuration cfg,Site site,Channel channel,UriRuleable uriRule){
        this.cfg = cfg;
        this.site = site;
        this.channel = channel;
        this.uriRule = uriRule;
    }
    
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
            logger.error("{} template is not exist.error:{}", path, e.getMessage());
            throw new PublishException(e);
        }
    }
    
    /**
     * 设置链接地址生成参数
     * 
     * @param rule uri生成规则
     * @param parameters 参数集合
     */
    protected void setUriRuleParameters(UriRuleable rule,Map<String,Object> parameters){
        rule.setParameters(parameters);
    }
    
    /**
     * 添加uri生成规则到生成页面参数中File temp = createTempFile();
             generator(new FileOutputStream(temp),template,parameters);
             return temp;
     * 
     * @param rule uri生成规则
     * @param parameters 参数集合
     */
    protected void addUriRuleToProcessParameters(UriRuleable rule,Map<String,Object> parameters){
        parameters.put(GlobalVariable.URI_RULE.toString(), rule);
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
        
        setUriRuleParameters(rule,parameters);
        addUriRuleToProcessParameters(rule,parameters);
        
        try{
            template.process(parameters, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            logger.error("Writer tempfile error {}", e.getMessage());
            throw new PublishException(e);
        } catch (TemplateException e) {
            logger.error("Freemarker proccess error {}", e.getMessage());
            throw new PublishException(e);
        }
    }

    /**
     * 生成页面临时文件
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
     * 构造生成页面参数
     * 
     * @param site 站点
     * @param channel 频道
     * @return
     */
    protected abstract Map<String,Object> constructParameters(Site site,Channel channel);

    @Override
    public void process(OutputStream out,String path)throws PublishException {
        Map<String,Object> parameters =constructParameters(site,channel);
        Template t = getFreemarkerTemplate(cfg,path);
        try {
            Writer writer = new OutputStreamWriter(out,charset);
            write(t,parameters,uriRule,writer);
        } catch (IOException e) {
            logger.error("Writer output stream is error {}", e.getMessage());
            throw new PublishException(e);
        } 
    }
    
    @Override
    public File process(String path) throws PublishException {
        try {
            File temp = createTempFile();
            process(new FileOutputStream(temp),path);
            return temp;
        } catch (IOException e) {
            logger.error("Writer temp file is error {}", e.getMessage());
            throw new PublishException(e);
        } 
    }
    
    @Override
    public void debugEnable(){
        debug = true;
    }
    
    @Override
    public String getPublishUri()throws PublishException{
        Map<String,Object> parameters =constructParameters(site,channel);
        setUriRuleParameters(this.uriRule,parameters);
        return uriRule.getUri();
    }
    
    @Override
    public String[] getPublishAdditionUris()throws PublishException{
        return new String[0];
    }
}
