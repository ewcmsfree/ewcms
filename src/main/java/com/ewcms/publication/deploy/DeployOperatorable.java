/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.deploy;

import java.io.File;

import org.apache.commons.vfs2.FileSystemException;

import com.ewcms.publication.PublishException;

/**
 * 发布资源接口
 * 
 * @author wangwei
 */
public interface DeployOperatorable {

    /**
     * 拷贝文件到指定的服务器文件
     * 
     * @param sourcePath 源路径
     * @param targetPath 目标路径 
     * @throws FileSystemException
     */
    public void copy(String sourcePath,String targetPath)throws PublishException;
    
    /**
     * 拷贝文件到指定的服务器文件
     * 
     * @param sourceFile 源文件对象
     * @param targetPath 目标路径 
     * @throws FileSystemException
     */
    public void copy(File sourceFile,String targetPath)throws PublishException;
    
    /**
     * 拷贝文件内容到指定服务器文件
     * 
     * @param content
     * @param targetPath
     * @throws PublishException
     */
    public void copy(byte[] content,String targetPath)throws PublishException;
    
    /**
     * 删除指定的服务器文件
     * 
     * 文件不存在，则不执行。
     * 
     * @param path 文件路基
     * @throws PublishException
     */
    public void delete(String path)throws PublishException;
    
    /**
     * 测试服务设置是否正确
     * 
     * @return true 测试成功
     * @throws PublishException
     */
    public boolean test()throws PublishException;
    
}
