/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.resource.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.ewcms.content.resource.model.Resource;
import com.ewcms.content.resource.model.Resource.Type;
import com.ewcms.publication.service.ResourcePublishServiceable;

/**
 * 资源管理接口
 * 
 * @author 吴智俊 王伟
 */
public interface ResourceServiceable extends ResourcePublishServiceable {
    
    /**
     * 上传资源
     * 
     * @param file      资源文件
     * @param path    文件路径
     * @param type    资源类型
     * @return            资源对象
     * @throws IOException
     */
    Resource uplaod(File file,String path,Type type)throws IOException;
    
    /**
     * 更新资源
     * 
     * @param id        资源编号
     * @param file      资源文件
     * @param path   文件路径
     * @param type   资源类型
     * @return          资源对象
     * @throws IOException
     */
    Resource update(Integer id,File file,String path,Resource.Type type)throws IOException;
    
    /**
     * 更新引导图
     * 
     * @param id 资源编号
     * @param file 引导图文件
     * @param path 引导图文件路径
     * @return
     * @throws IOException
     */
    Resource updateThumb(Integer id,File file,String path)throws IOException;
    
    /**
     * 保存资源
     * <br>
     * 保存后的资源状态为正常状态（state = NORMAL）
     * 
     * @param descriptions 资源描述集合(key:资源编号,value:描述)
     * @return
     */
    List<Resource> save(Map<Integer,String> descriptions);

    /**
     * 删除资源
     * 
     * @param ids 资源编号
     */
    void delete(int[] ids);

    /**
     * 软删除资源
     * <br>
     * 资源并没有真正删除，只是标识为删除
     *
     * @param ids 资源编号
     */
    void softDelete(int[] ids);
    
    /**
     * 恢复删除资源
     * <br>
     * 只有软删除资源才能恢复
     * 
     * @param ids 资源编号
     */
    void revert(int[] ids);
    
    /**
     * 清空状态为DELETE的记录
     */
    void clearSoftDelete();

    /**
     * 更新资源描述信息
     * 
     * @param id 
     *         资源编号
     * @param description
     *         资源描述信息
     */
    Resource updateDescription(Integer id,String description);
    
    /**
     * 通过uri得到资源对象
     * 
     * @param uri 资源地址
     * @return
     */
    Resource getResourceByUri(String uri);
}
