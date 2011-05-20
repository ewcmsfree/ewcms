/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ewcms.content.resource;

import com.ewcms.content.resource.model.Resource;
import com.ewcms.content.resource.model.ResourceType;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author wangwei
 */
public interface ResourceFacable {
    
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
