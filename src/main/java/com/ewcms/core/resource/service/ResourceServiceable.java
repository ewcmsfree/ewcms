/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.core.resource.service;

import com.ewcms.core.resource.model.Resource;
import com.ewcms.core.resource.model.ResourceType;
import java.io.File;
import java.io.IOException;

/**
 * 上传文件操作接口
 * 
 * @author 吴智俊
 */
public interface ResourceServiceable {

    /**
     * 新增上传文件
     *
     * @param uploadFile
     * @return
     */
    public Resource addResource(File file,String fileName,ResourceType type)throws IOException;

    /**
     * 删除上传文件
     *
     * @param uploadFileId
     */
    public void delResource(Integer id);

    /**
     * 查询上传文件
     *
     * @param uploadFileId
     * @return
     */
    public Resource getResource(Integer id);

    /**
     * 更新资源信息
     * 
     * @param id
     * @param title
     * @param description
     */
    public Resource updResourceInfo(Integer id,String title,String description);
}
