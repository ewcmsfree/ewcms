/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.resource.service.operator;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.ewcms.publication.uri.UriRuleable;

/**
 * 资源文件操作接口
 *
 * @author wangwei
 */
public interface ResourceOperatorable {

    /**
     * 写入资源文件
     * 
     * @param source    源资源输入流
     * @param uriRule   生成资源地址规则
     * @return          资源访问地址
     * @throws IOException
     */
    String write(InputStream source,UriRuleable uriRule)throws IOException;
    
    /**
     * 写入资源文件
     * 
     * @param source    源资源输入流
     * @param uriRule   生成资源地址规则
     * @param suffix    资源后缀名
     * @return          资源访问地址
     * @throws IOException
     */
    String write(InputStream source,UriRuleable uriRule,String suffix)throws IOException;
    
    /**
     * 读资源文件
     * 
     * @param stream 输出资源数据流
     * @param uri    资源访问地址
     * @throws IOException
     */
    void read(OutputStream stream,String uri)throws IOException;

    /**
     * 删除资源文件
     * 
     * @param uri 资源路径
     * @throws IOException
     */
    void delete(String uri)throws IOException;
}
