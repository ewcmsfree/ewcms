/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.preview;

import java.io.OutputStream;

import com.ewcms.publication.PublishException;

/**
 * 预览接口
 * 
 * @author wangwei
 */
public interface PreviewServiceable {

    /**
     * 模板预览
     * 
     * @param out       
     *          输出数据流
     * @param id
     *          模板编号
     * @param mock
     *          是否模拟 
     * @throws PublishException
     */
    public void viewTemplate(OutputStream out,Integer id,Boolean mock)throws PublishException;
       
    /**
     * 文章预览
     * 
     * @param out       
     *          输出数据流
     * @param channelId
     *          频道编号
     * @param id
     *          文章编号
     * @param pageNumber
     *          页数 
     * @throws PublishException
     */
    public void viewArticle(OutputStream out,Integer channelId,Long id,Integer pageNumber)throws PublishException;
}
