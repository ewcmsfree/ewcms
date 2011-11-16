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
    
    private FileSystemOptions initOptions(SiteServer server)throws FileSystemException{
        FileSystemOptions opts = new FileSystemOptions();
        setUserAuthenticator(opts,server.getUserName(),server.getPassword());
        return opts;
    }
    
    @Override
    public void out(SiteServer server, List<OutputResource> resources)throws PublishException {

        try {
            FileSystemOptions opts = initOptions(server);
            FileObject root = getTargetRoot(opts,server,VFS.getManager());
            outResources(root, server.getPath(), resources);
            root.close();
        } catch (FileSystemException e) {
            logger.error("Init output is error:{}", e.toString());
            throw new PublishException(e);
        }
    }
    
    @Override
    public void test(SiteServer server)throws PublishException{
        FileObject root = null;
        try {
            FileSystemOptions opts = initOptions(server);
            root = getTargetRoot(opts,server,VFS.getManager());
        } catch (FileSystemException e) {
            logger.error("Test output is error:{}", e.toString());
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
        }catch(FileSystemException e){
            
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
                logger.debug("Server's is root dir {} ",target.getContent().getFile().getName());
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
        FileObject out = root.resolveFile(path,NameScope.DESCENDENT);
        if (!out.exists()) {
            out.createFile();
        }
        return out;
    }
}
