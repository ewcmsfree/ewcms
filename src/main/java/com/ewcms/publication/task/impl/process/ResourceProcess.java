/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.publication.task.impl.process;

import org.springframework.util.Assert;

import com.ewcms.publication.PublishException;
import com.ewcms.publication.deploy.DeployOperatorable;

/**
 * 内容资源发布过程
 * 
 * @author wangwei
 */
public class ResourceProcess extends TaskProcessBase{
    
    private final String[] paths;
    private final String[] uris;
    
    public ResourceProcess(String[] paths,String[] uris){
        Assert.isTrue(paths.length == uris.length,"Path and uri is not map");
        this.paths = paths;
        this.uris = uris;
    }

    @Override
    protected String process(DeployOperatorable operator) throws PublishException {
        int length = paths.length;
        for(int i = 0 ; i < length ; i++){
            String path = paths[i];
            String uri = uris[i];
            operator.copy(path, uri);
        }
        return uris[length -1];
    }
    
    /**
     * 得到资源路径
     * 
     * @return
     */
    public String[] getPaths(){
        return paths;
    }
    
    /**
     * 得到发布的uri
     *  
     * @return
     */
    public String[] getUris(){
        return uris;
    }
}
