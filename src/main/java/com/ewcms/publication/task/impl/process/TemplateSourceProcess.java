/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.publication.task.impl.process;

import com.ewcms.publication.PublishException;
import com.ewcms.publication.output.DeployOperatorable;

/**
 * 模版资源发布过程
 * 
 * @author wangwei
 */
public class TemplateSourceProcess extends TaskProcessBase{
    private final String path;
    private final byte[] content;
    
    /**
     * 构造模版资源对象
     * 
     * @param path 发布路径
     * @param content 资源内容
     */
    public TemplateSourceProcess(String path,byte[] content){
        this.path = path;
        this.content =content;
    }

    @Override
    protected String process(DeployOperatorable operator) throws PublishException {
        operator.copy(content, path);
        return path;
    }
}
