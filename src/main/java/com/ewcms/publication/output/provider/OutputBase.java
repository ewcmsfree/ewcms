/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.output.provider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.vfs.FileContent;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileSystemManager;
import org.apache.commons.vfs.FileSystemOptions;
import org.apache.commons.vfs.auth.StaticUserAuthenticator;
import org.apache.commons.vfs.cache.DefaultFilesCache;
import org.apache.commons.vfs.impl.DefaultFileSystemConfigBuilder;
import org.apache.commons.vfs.impl.DefaultFileSystemManager;
import org.apache.commons.vfs.provider.ftp.FtpFileProvider;
import org.apache.commons.vfs.provider.local.DefaultLocalFileProvider;
import org.apache.commons.vfs.provider.sftp.SftpFileProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.common.lang.EmptyUtil;
import com.ewcms.core.site.model.SiteServer;
import com.ewcms.publication.PublishException;
import com.ewcms.publication.output.OutputResource;
import com.ewcms.publication.output.Outputable;

/**
 * 发布指定的资源
 * 
 * @author wangwei
 */
public abstract class OutputBase implements Outputable {

    private static final Logger logger = LoggerFactory.getLogger(OutputBase.class);
    private static final String PATH_SPARATOR = "/";
    public static FileSystemManager DEFAULT_FILE_SYSTEM_MANAGER ;
    
    static{
        try{
            DEFAULT_FILE_SYSTEM_MANAGER = initFileSystemManager();
        }catch(FileSystemException e){
            logger.error("Init DEFAULT_FILE_SYSTEM_MANAGER is fail:{}",e);
        }
    }

    private FileSystemManager fileSystemManager = DEFAULT_FILE_SYSTEM_MANAGER;

    @Override
    public void out(SiteServer server, List<OutputResource> resources)throws PublishException {

        try {
            FileSystemOptions opts = new FileSystemOptions();
            setUserAuthenticator(opts,server.getUserName(),server.getPassword());
            FileObject root = getTargetRoot(opts,server,fileSystemManager);
            outResources(root, server.getPath(), resources);
            root.close();
        } catch (FileSystemException e) {
            logger.error("Init output is error:{}", e.toString());
            throw new PublishException(e);
        }
    }
    
    /**
     * 设置用户认证
     * 
     * @param opts
     *           文件系统设置选项
     * @param username
     *            用户名
     * @param password
     *            密码
     * @throws FileSystemException
     */
    protected void setUserAuthenticator(FileSystemOptions opts,String username,String password)throws FileSystemException {
        StaticUserAuthenticator auth = new StaticUserAuthenticator(null,username,password);
        DefaultFileSystemConfigBuilder.getInstance().setUserAuthenticator(opts, auth);
    }

    /**
     * 发布在资源
     * 
     * @param root
     *            根目录文件对象
     * @param rootPath
     *            根目录路径
     * @param resources
     *            发布的资源集合
     * @throws PublishException
     */
    protected void outResources(FileObject root, String rootPath,List<OutputResource> resources) {

        if (resources == null || resources.isEmpty()) {
            return;
        }
        
        for (OutputResource resource : resources) {
            try{
                outResource(root,rootPath,resource);
            }catch(PublishException e){
                logger.error(e.toString());
            }
            
        }
    }
    
    /**
     * 发布单个资源
     * <br>
     * 递归发布子资源
     * 
    * @param root
     *            根目录文件对象
     * @param rootPath
     *            根目录路径
     * @param resource
     *            发布的资源
     * @throws PublishException
     */
    protected void outResource(FileObject root,String rootPath,OutputResource resource)throws PublishException{
        
        if(EmptyUtil.isCollectionNotEmpty(resource.getChildren())){
            List<OutputResource> errorResources = new ArrayList<OutputResource>();
            for (OutputResource child : resource.getChildren()) {
                try{
                    outResource(root, rootPath, child);
                }catch(PublishException e){
                    logger.debug("Children has error:{}",e);
                    errorResources.add(child);
                }
            }
            if(errorResources.isEmpty()){
                resource.outputSuccess();
            }else{
                String[] errorUirs = new String[errorResources.size()];
                for(int i = 0 ; i < errorResources.size() ; ++i){
                    errorUirs[i] = errorResources.get(i).getUri();
                }
                PublishException e =new PublishException("子资源发布错误:"+StringUtils.join(errorUirs,","));
                resource.outputError("子资源发布错误:"+StringUtils.join(errorUirs,","), e);
                throw e;
            }
        }
        
        if (resource.isOutput()) {
            String targetPath = getTargetPath(rootPath, resource.getUri());
            logger.debug("Target path is {}", targetPath);

            try {
                FileObject target = getTargetFileObject(root, targetPath);
                FileContent content = target.getContent();
                resource.write(content.getOutputStream());
                content.close();
                target.close();
                resource.outputSuccess();
            } catch (FileSystemException e) {
                logger.error("Output {} is error:{}", resource.getUri(),e);
                resource.outputError("发布错误:" + resource.getUri(), e);
                throw new PublishException(e);
            } catch (IOException e) {
                logger.error("Output {} is error:", resource.getUri(),e);
                resource.outputError("发布错误:" + resource.getUri() , e);
                throw new PublishException(e);
            }
        }
    }

    /**
     * 初始化文件管理对象
     * 
     * @return 文件管理对象
     * @throws FileSystemException
     */
    protected static FileSystemManager initFileSystemManager()throws FileSystemException {
        DefaultFileSystemManager manager = new DefaultFileSystemManager();
        manager.addProvider("sftp", new SftpFileProvider());
        manager.addProvider("file", new DefaultLocalFileProvider());
        manager.addProvider("ftp", new FtpFileProvider());
        manager.setFilesCache(new DefaultFilesCache());
        manager.init();
        return manager;
    }

    /**
     * 得到发布到资源根目录对象
     * 
     * @param opts
     *            文件系统设置选项
     * @param server
     *            发布服务
     * @param manager
     *            文件管理对象
     * @return 根目录文件对象
     * @throws FileSystemException
     */
    protected abstract FileObject getTargetRoot(FileSystemOptions opts,
            SiteServer server,FileSystemManager manager) throws FileSystemException;

    /**
     * 得到输出文件的绝对路径
     * 
     * @param root
     *            根目录路径
     * @param path
     *            相对根目录路径
     * @return 绝对路径
     */
    protected String getTargetPath(String root, String path) {
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
        FileObject out = root.resolveFile(path);
        if (!out.exists()) {
            out.createFile();
        }
        return out;
    }

    /**
     * 设置文件管理类
     * 
     * @param manager
     *            文件管理类
     */
    public void setFileSystemManager(FileSystemManager manager) {
        fileSystemManager = manager;
    }
}
