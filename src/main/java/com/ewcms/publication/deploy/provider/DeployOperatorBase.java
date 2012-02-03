/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.deploy.provider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.vfs2.FileContent;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.NameScope;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.auth.StaticUserAuthenticator;
import org.apache.commons.vfs2.impl.DefaultFileSystemConfigBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.publication.PublishException;
import com.ewcms.publication.deploy.DeployOperatorable;

/**
 * 发布指定的资源
 * 
 * @author wangwei
 */
public abstract class DeployOperatorBase implements DeployOperatorable {

    private static final Logger logger = LoggerFactory.getLogger(DeployOperatorBase.class);
    private static final String PATH_SPARATOR = "/";
    
    public static abstract class BuilderBase{
        
        protected String hostname;
        protected String port;
        protected String username;
        protected String password;
        protected String path;
        
        public String getHostname() {
            return hostname;
        }
        
        public BuilderBase setHostname(String hostname) {
            this.hostname = hostname;
            return this;
        }

        public String getPort() {
            return port;
        }
        
        public BuilderBase setPort(String port) {
            this.port = port;
            return this;
        }

        public String getUsername() {
            return username;
        }
        
        public BuilderBase setUsername(String username) {
            this.username = username;
            return this;
        }

        public String getPassword() {
            return password;
        }
        
        public BuilderBase setPassword(String password) {
            this.password = password;
            return this;
        }

        public String getPath() {
            return path;
        }
        
        public BuilderBase setPath(String path) {
            this.path = path;
            return this;
        }

        public abstract DeployOperatorable build();
    }
    
    private BuilderBase builder;
    
    public DeployOperatorBase(BuilderBase builder){
        this.builder = builder;
    }
    
    /**
     * 设置服务器认证
     * 
     * @param opts 文件系统设置选项
     * @param username 用户名
     * @param password 密码
     * @throws FileSystemException
     */
    protected void setAuthenticator(FileSystemOptions opts,String username,String password)throws FileSystemException{
        if(username != null){
            StaticUserAuthenticator auth = new StaticUserAuthenticator(null,username,password);
            DefaultFileSystemConfigBuilder.getInstance().setUserAuthenticator(opts, auth);        
        }
    }
    
    /**
     * 得到发布的根目录文件对象
     * 
     * <br/>
     * 根据不同的文件服务实现
     * 
     * @param opts
     *            文件系统设置选项
     * @param builder
     *            构建对象
     * @param manager
     *            文件管理对象
     * @return 根目录文件对象
     * @throws FileSystemException
     */
    protected abstract FileObject getRootFileObject(FileSystemOptions opts,
            BuilderBase builder,FileSystemManager manager) throws FileSystemException;
    
    /**
     * 得到发布的根目录文件对象

     * @return 文件对象
     * @throws FileSystemException
     */
    protected FileObject getRootFileObject() throws FileSystemException {
        FileSystemOptions opts = new FileSystemOptions();
        setAuthenticator(opts, builder.getUsername(), builder.getPassword());
        return getRootFileObject(opts, builder, VFS.getManager());
    }
    
    /**
     * 得到输出文件的绝对路径
     * 
     * @param root
     *            根目录路径
     * @param path
     *            相对根目录路径
     * @return 绝对路径
     */
    protected String targetFullPath(String root, String path) {
        String s = root + PATH_SPARATOR + path;
        String[] segments = StringUtils.split(s, PATH_SPARATOR);
        return PATH_SPARATOR + StringUtils.join(segments, PATH_SPARATOR);
    }
    
    /**
     * 得到目标文件对象
     * 
     * @param root
     *            根目录路径
     * @param path
     *            相对根目录路径
     * @return
     * @throws FileSystemException
     */
    protected FileObject getTargetFileObject(FileObject root, String path)throws FileSystemException {
        FileObject out = root.resolveFile(path,NameScope.DESCENDENT);
        if (!out.exists()) {
            out.createFile();
        }
        return out;
    }
    
    @Override
    public void copy(String sourcePath,String targetPath)throws PublishException {
        copy(new File(sourcePath),targetPath);
    }
    
    @Override
    public void copy(File sourceFile,String targetPath)throws PublishException {
        
        if(!sourceFile.exists()){
            logger.debug("Source file path's {} is not exists.",sourceFile.getPath());
            return ;
        }
        
        String targetFullPath = targetFullPath(builder.getPath(),targetPath);
        logger.debug("Server file's path is {}", targetFullPath);
        logger.debug("Source file's path is {}  ",sourceFile.getPath());
        
        FileObject root =null;
        FileObject target = null;
        try {
            root = getRootFileObject();
            target = getTargetFileObject(root, targetFullPath);
            FileContent fileContent = target.getContent();
            
            OutputStream out = fileContent.getOutputStream();
            InputStream in = new FileInputStream(sourceFile);
           
            byte[] buffer = new byte[1024 * 8];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            out.flush();
            
            in.close();
            out.close();
            fileContent.close();
        } catch (FileSystemException e) {
            logger.error("Copy {} is error:{}", targetFullPath,e);
            throw new PublishException(e);
        } catch (IOException e) {
            logger.error("Copy {} is error:{}", targetFullPath,e);
            throw new PublishException(e);
        } finally{
            try{
                if(root != null){
                    root.close();
                }
                if(target != null){
                    target.close();
                }    
            }catch(Exception e){
                logger.error("vfs close is error:{}",e.toString());
            }
        }
    }
    
    @Override
    public void copy(byte[] content,String targetPath)throws PublishException {
        
        String targetFullPath = targetFullPath(builder.getPath(),targetPath);
        logger.debug("Server file's path is {}", targetFullPath);

        FileObject root =null;
        FileObject target = null;
        try {
            root = getRootFileObject();
            target = getTargetFileObject(root, targetFullPath);
            FileContent fileContent = target.getContent();
            OutputStream stream = fileContent.getOutputStream();
            stream.write(content);
            stream.flush();
            
            stream.close();
            fileContent.close();
            target.close();
            root.close();
        } catch (FileSystemException e) {
            logger.error("Copy {} is error:{}", targetFullPath,e);
            throw new PublishException(e);
        } catch (IOException e) {
            logger.error("Copy {} is error:{}", targetFullPath,e);
            throw new PublishException(e);
        } finally{
            try{
                if(root != null){
                    root.close();
                }
                if(target != null){
                    target.close();
                }    
            }catch(Exception e){
                logger.error("vfs close is error:{}",e.toString());
            }
        }
    }
    
    @Override
    public void delete(String path)throws PublishException{
        String fullPath = targetFullPath(builder.getPath(),path);
        logger.debug("Delete file's path is {}", path);

        try {
            FileObject root = getRootFileObject();
            FileObject target = getTargetFileObject(root, fullPath);
            
             if(target.exists()){
                 target.delete();
             }
            target.close();
            root.close();
        } catch (FileSystemException e) {
            logger.error("Delete {} file delete is error:{}", fullPath,e);
            throw new PublishException(e);
        }
    }
    
    @Override
    public boolean test()throws PublishException{
        FileObject root = null;
        try {
            root = getRootFileObject();
        } catch (FileSystemException e) {
            logger.error("Test is fail:{}", e.toString());
            throw new PublishException("error.output.notconnect");
        }

        try{
            if(!root.exists()){
                throw new PublishException("error.output.nodir");
            }    
            if(!root.isWriteable()){
                throw new PublishException("error.output.notwrite");
            }
            root.close();
            return true;
        }catch(FileSystemException e){
            logger.error("Test  is fail:{}", e.toString());
            return false;
        }
    }
}
