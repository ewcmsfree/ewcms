/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator;

import org.apache.commons.lang.StringUtils;

/**
 * 发布资源
 * <br>
 * 
 * @author wangwei
 */
public class ResourceInfo {
    
    private static final String PATH_SEPARATOR = "/";
    
    private String context;
    private String path;
    private String releasePath;
    private long size = -1L;
    
    public ResourceInfo(String path){
        this(path,null);
    }
    
    public ResourceInfo(String path,String releasePath){
        this(null,path,releasePath);
    }
    
    public ResourceInfo(String context,String path,String releasePath){
        this(context,path,releasePath,-1L);
    }
    
    public ResourceInfo(String context,String path,String releasePath,long size){
        this.context = context;
        this.path = path;
        this.releasePath = releasePath;
        this.size = size;
    }
    public String getContext() {
        context = StringUtils.removeStart(context, PATH_SEPARATOR);
        return context = StringUtils.removeEnd(context, PATH_SEPARATOR);
    }
    public void setContext(String context) {
        this.context = context;
    }
    
    public String getPath() {
        return path = StringUtils.removeStart(path, PATH_SEPARATOR);
    }
    public void setPath(String path) {
        this.path = path;
    }
    
    public String getReleasePath() {
        if(releasePath == null){
            return getPath();
        }else{
            return releasePath =StringUtils.removeStart(releasePath, PATH_SEPARATOR);
        }
    }
    public void setReleasePath(String releasePath) {
        this.releasePath = releasePath;
    }
    
    public long getSize() {
        return size;
    }
    public void setSize(long size) {
        this.size = size;
    }
    
    public String getUri(){
        if(getContext() == null){
            return getReleasePath();
        }else{
            return getContext() + "/" + getReleasePath();
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((context == null) ? 0 : context.hashCode());
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
        ResourceInfo other = (ResourceInfo) obj;
        if (context == null) {
            if (other.context != null)
                return false;
        } else if (!context.equals(other.context))
            return false;
        if (path == null) {
            if (other.path != null)
                return false;
        } else if (!path.equals(other.path))
            return false;
        return true;
    }
    
    @Override
    public String toString() {
        return "FileInfo [context=" + getContext() + ", path=" + getPath() + ", relasePath="
                + getReleasePath() + ", size=" + size + "]";
    }
}
