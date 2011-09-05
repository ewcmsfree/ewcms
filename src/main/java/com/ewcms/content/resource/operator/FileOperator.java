/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.resource.operator;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.lang.xwork.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.common.io.FileUtil;
import com.ewcms.publication.PublishException;
import com.ewcms.publication.uri.UriRuleable;

/**
 * 以文件方式实现资源文件的操作
 * <br>
 * 资源以文本的方式保存在服务器指定的文件中。
 *
 * @author wangwei
 */
public class FileOperator implements ResourceOperatorable{

    private static final Logger logger = LoggerFactory.getLogger(FileOperator.class);
    
    private String rootDirPath;
    
    public FileOperator(String rootDirPath){
        this.rootDirPath = rootDirPath;
    }
    
    @Override
    public String write(InputStream source,UriRuleable uriRule) throws IOException {
        
        try {
            String uri = uriRule.getUri();
            
            String targetPath = getPath(uri);
            FileUtil.writerFile(source, targetPath);
            source.close();
            
            return uri;
        } catch (PublishException e) {
            logger.error("Resource uri is error :{}",e);
            throw new IOException(e);
        }
    }
    
    @Override
    public void read(OutputStream output,String uri)throws IOException{
        String path = getPath(uri);
        
        byte[] buff = new byte[1024 * 10];
        InputStream input = new FileInputStream(path);
        while(input.read(buff) > 0){
            output.write(buff);
        }
        output.flush();
        
        input.close();
        output.close();
    }

    @Override
    public void delete(String uri) throws IOException {
        String path = getPath(uri);
        FileUtil.delete(path);
    }
    
    /**
     * 得到资源完整路径
     * 
     * @param uri 访问地址
     * @return
     */
    private String getPath(String uri){
        String root = rootDirPath;
        root = StringUtils.removeStart(root, "/");
        root = StringUtils.removeEnd(root,"/");
        String path =   "/" + root + uri;
        logger.debug("Resource path is {}",path);
        return path;
    }
}
