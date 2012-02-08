/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.publication.task.impl.process;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.publication.PublishException;
import com.ewcms.publication.deploy.DeployOperatorable;
import com.ewcms.publication.generator.Generatorable;

/**
 * 生成页面发布过程
 * 
 * @author wangwei
 */
public class GeneratorProcess extends TaskProcessBase{
    private static final Logger logger = LoggerFactory.getLogger(GeneratorProcess.class);
    
    private final Generatorable[] generators;
    private final String path;
    
    /**
     * 构造单页面生成过程
     * 
     * @param generators 多个页面生成
     * @param path 模版路径
     */
    public GeneratorProcess(Generatorable generator, String path){
        this(new Generatorable[]{generator},path);
    }
    
    /**
     * 构造含多页面生成过程（如： 多页文章）
     * 
     * @param generators 多个页面生成
     * @param path 模版路径
     */
    public GeneratorProcess(Generatorable[] generators,String path){
        this.generators = generators;
        this.path = path;
    }
    
    @Override
    protected String process(DeployOperatorable operator) throws PublishException {
        int length = generators.length;
        String firstUri = null ;
        for(int i = 0 ; i < length ; i++){
            File source = null;
            try{
                Generatorable generator = generators[i];
                source = generator.process(path);
                String uri = generator.getPublishUri();
                if (i == 0) { firstUri = uri; }    
                operator.copy(source, uri);
                String[] additionUris = generator.getPublishAdditionUris();
                for(String additionUri : additionUris){
                    operator.copy(source, additionUri);
                }
            }catch(PublishException e){
                throw e;
            }catch(Exception e){
                throw new PublishException(e);
            }finally{
                if(source != null){
                    logger.debug("Temp file path's {} by deleted",source.getPath());
                    source.delete();
                }
            }
        }
        return firstUri;
    }
}
