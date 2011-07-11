/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.output.provider;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.vfs.FileContent;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileSystemManager;
import org.apache.commons.vfs.cache.DefaultFilesCache;
import org.apache.commons.vfs.impl.DefaultFileSystemManager;
import org.apache.commons.vfs.provider.local.DefaultLocalFileProvider;
import org.apache.commons.vfs.provider.sftp.SftpFileProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.core.site.model.SiteServer;
import com.ewcms.generator.ReleaseException;
import com.ewcms.generator.output.OutputResource;
import com.ewcms.generator.output.Outputable;

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
    public void out(SiteServer server, List<OutputResource> resources)
            throws ReleaseException {

        try {
            FileObject root = getTargetRoot(server, fileSystemManager);
            outResources(root, server.getPath(), resources);
            root.close();
        } catch (FileSystemException e) {
            logger.error("Init output is error:{}", e.toString());
            throw new ReleaseException(e);
        }
    }

    /**
     * 发布在资源 <br>
     * 使用递归发布所有资源
     * 
     * @param root
     *            根目录文件对象
     * @param rootPath
     *            根目录路径
     * @param resources
     *            发布的资源
     * @throws ReleaseException
     */
    protected void outResources(FileObject root, String rootPath,
            List<OutputResource> resources) throws ReleaseException {

        if (resources == null || resources.isEmpty()) {
            return;
        }

        for (OutputResource resource : resources) {

            if (resource.isOutput()) {
                String targetPath = getTargetPath(rootPath, resource.getUri());
                logger.debug("Target path is {}", targetPath);

                try {
                    FileObject target = getTargetFileObject(root, targetPath);
                    FileContent content = target.getContent();
                    writeStream(content.getOutputStream(),getLocalStream(resource.getPath()));
                    content.close();
                    target.close();
                    resource.outputSuccess();
                } catch (FileSystemException e) {
                    logger.error("Output {} is error:{}", resource.getUri(),
                            e.toString());
                    resource.outputError("发布文件错误", e);
                    throw new ReleaseException(e);
                } catch (IOException e) {
                    logger.error("Output {} is error:", resource.getUri(),
                            e.toString());
                    resource.outputError("发布文件错误", e);
                    throw new ReleaseException(e);
                }
            }

            outResources(root, rootPath, resource.getChildren());
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
        manager.setFilesCache(new DefaultFilesCache());
        manager.init();
        return manager;
    }

    /**
     * 得到发布到资源根目录对象
     * 
     * @param server
     *            发布服务对象
     * @param manager
     *            文件管理对象
     * @return 根目录文件对象
     * @throws FileSystemException
     */
    protected abstract FileObject getTargetRoot(SiteServer server,
            FileSystemManager manager) throws FileSystemException;

    /**
     * 读入本地资源数据流
     * 
     * @param path
     * @return
     * @throws IOException
     */
    protected InputStream getLocalStream(String path) throws IOException {
        return new FileInputStream(path);
    }

    /**
     * 输出本地资源到发布资源数据流中
     * 
     * @param output
     *            输出流
     * @param in
     *            输入流()
     * @throws IOException
     */
    protected void writeStream(OutputStream out, InputStream in)
            throws IOException {

        byte[] buffer = new byte[1024 * 10];
        int len = 0;
        while ((len = in.read(buffer)) > 0) {
            out.write(buffer, 0, len);
        }
        out.flush();

        in.close();
        out.close();
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
    protected FileObject getTargetFileObject(FileObject root, String path)
            throws FileSystemException {
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
