/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.output.event;

import com.ewcms.generator.service.ArticleLoaderServiceable;

/**
 * 发布文章事件
 * 
 * @author wangwei
 */
public class ArticleOutputEvent implements OutputEventable {

    private ArticleLoaderServiceable service;
    private Long id;
    private String url;
    
    public ArticleOutputEvent(Long id,String url,ArticleLoaderServiceable service){
        this.id = id;
        this.url = url;
        this.service = service;
    }

    @Override
    public void success() {
        service.releaseArticle(id, url);
    }

    @Override
    public void error(String message,Throwable e){
        // TODO 写错误日志
    }
}
