/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.output;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.ewcms.generator.output.event.DefaultOutputEvent;
import com.ewcms.generator.output.event.OutputEventable;

/**
 * 发布资源对象
 * 
 * @author wangwei
 */
public class OutputResource {
    
    private static final Logger logger = LoggerFactory.getLogger(OutputResource.class);
    
    private String path;
    private byte[] content;
    private String uri;
    private long size = -1L;
    private List<OutputResource> children;
    private OutputEventable event =new DefaultOutputEvent();
    
    public OutputResource(){
        //创建不用发布的资源对象，关联需要发布的子资源对象。
        //比如：一篇文章有多页，这片文章就有多个发布资源，所以需要一个主资源来关联这些发布资源，判断资源是否发布成功。
    }
    
    /**
     * 创建基于本地资源文件的发布资源对象
     * 
     * @param path 文件路径
     * @param uri 发布路径
     */
    public OutputResource(String path,String uri){
        this(path,uri,-1l);
    }
    
    /**
     * 创建基于本地资源文件的发布资源对象
     * 
     * @param path 文件路径
     * @param uri 发布路径
     * @param size 文件大小
     */
    public OutputResource(String path,String uri,long size){
        Assert.notNull(path);
        Assert.notNull(uri);
        this.path = path;
        this.uri = uri;
        this.size = size;
    }
    
    /**
     * 创建基于资源内容的发布资源对象
     * 
     * @param content 资源内容
     * @param uri 发布路径
     */
    public OutputResource(byte[] content,String uri){
        Assert.notNull(content);
        Assert.notNull(uri);
        this.content = content;
        this.uri = uri;
        this.size = content.length;
    }
    
    /**
     * 把资源写入输出数据流
     * 
     * @param out 输出数据流
     * @throws IOException
     */
    public void write(OutputStream out)throws IOException{
        if(!isOutput()){
            logger.warn("OutputResource does not output");
            if(out != null){
                out.close();
            }
            return ;
        }
        
        if(content != null && content.length > 0){
            out.write(content);
        }else{
            InputStream in = new FileInputStream(path);
            byte[] buffer = new byte[1024 * 10];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            in.close();
        }
       
        out.flush();
        out.close();
    }
    
    /**
     * 得到发布路径
     * 
     * @return
     */
    public String getUri() {
        return uri;
    }
    
    /**
     * 得到发布资源大小
     * 
     * @return
     */
    public long getSize() {
        return size;
    }
    
    /**
     * 得到子发布资源
     * 
     * @return
     */
    public List<OutputResource> getChildren() {
        return children;
    }
    
    /**
     * 设置子发布资源
     * 
     * @param children 子发布资源集合
     */
    public void setChildren(List<OutputResource> children) {
        this.children = children;
    }
    
    /**
     * 添加子发布资源
     * 
     * @param resource 发布资源
     */
    public void addChild(OutputResource resource){
        children = (children == null ? new ArrayList<OutputResource>() : children);
        children.add(resource);
    }
    
    /**
     * 注册发布时间
     * 
     * @param event
     */
    public void registerEvent(OutputEventable event){
        this.event = event;
    }
    
    /**
     * 判断是否需要发布
     * 
     * @return true 发布 false 不发布
     */
    public boolean isOutput(){
        return content != null || !(StringUtils.isBlank(path) || StringUtils.isBlank(uri));
    }
    
    /**
     * 发布成功
     */
    public void outputSuccess(){
        event.success();
        close();
    }
    
    /**
     * 发布失败
     * @param message 失败消息
     * @param e 失败异常
     */
    public void outputError(String message,Throwable e){
        event.error(message,e);
        close();
    }
    
    /**
     * 关闭发布资源
     * <br>
     * 释放资源
     */
    public void close(){
        content = null;
        if(!StringUtils.isBlank(path)){
            File file = new File(path);
            file.deleteOnExit();
            logger.debug("Delete {}",path);
        }
    }

    @Override
    public String toString() {
        return "OutputResource [path=" + path 
                + ", uri=" + uri
                + ", size=" + size 
                + "]";
    }
}
