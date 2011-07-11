/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.output;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ewcms.generator.output.event.DefaultOutputEvent;
import com.ewcms.generator.output.event.OutputEventable;

/**
 * 发布资源
 * <br>
 * 
 * @author wangwei
 */
public class OutputResource {
    
    private static final String PATH_SEPARATOR = "/";
    
    private String path;
    private String uri;
    private long size = -1L;
    private List<OutputResource> children;
    private OutputEventable event =new DefaultOutputEvent();
    
    public OutputResource(){
        this("","");
    }
    
    public OutputResource(String path,String uri){
        this(path,uri,-1l);
    }
    
    public OutputResource(String path,String uri,long size){
        this.path = path;
        this.uri = uri;
        this.size = size;
    }
    
    public String getPath() {
        return path;
    }
    
    public void setPath(String path) {
        this.path = path;
    }
    
    public String getUri() {
        return uri =StringUtils.removeStart(uri, PATH_SEPARATOR);
    }
    
    public void setUri(String releasePath) {
        this.uri = releasePath;
    }
    
    public long getSize() {
        return size;
    }
    
    public void setSize(long size) {
        this.size = size;
    }
    
    public List<OutputResource> getChildren() {
        return children;
    }

    public void setChildren(List<OutputResource> children) {
        this.children = children;
    }
    
    public void addChild(OutputResource resource){
        if(children == null){
            children = new ArrayList<OutputResource>();
        }
        children.add(resource);
    }
    
    public void registerEvent(OutputEventable event){
        this.event = event;
    }
    
    public boolean isOutput(){
        return !(StringUtils.isBlank(path) || StringUtils.isBlank(uri));
    }
    
    public void outputSuccess(){
        event.success();
        close();
    }
    
    public void outputError(String message,Throwable e){
        event.error(message,e);
        close();
    }
    
    private void close(){
        //TODO 销毁资源
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((path == null) ? 0 : path.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OutputResource other = (OutputResource) obj;
        if (path == null) {
            if (other.path != null)
                return false;
        } else if (!path.equals(other.path))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "OutputResource [path=" + path 
                + ", uri=" + uri
                + ", size=" + size 
                + "]";
    }
}
