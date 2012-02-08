/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.generator;

import java.io.File;
import java.io.OutputStream;

import com.ewcms.publication.PublishException;

/**
 * 生成生成页面
 * 
 * @author wangwei
 */
public interface Generatorable {
 
    /**
     * 根据模板生成页面
     * <p/>
     * 生成页面在临时目录中：如（/tmp或$CATLINA_HOME/tmp）。
     * 
     * @param path 模板路径
     * @return 生成页面文件
     * @throws PublishException
     */
    public File process(String path)throws PublishException;
    
    /**
     * 根据模板生成的页面，输出到指定的数据流。
     * 
     * @param stream 
     *          输出生成页面
     * @param path
     *          模板路径
     * 
     * @throws PublishException
     */
    public void process(OutputStream stream,String path)throws PublishException;
    
    /**
     * 使用调试模式生成页面
     */
    public void debugEnable();
    
    /**
     * 得到生成页面发布地址
     * 
     * @return uri地址
     * @throws PublishException
     */
    public String getPublishUri()throws PublishException;
    
    /**
     * 得到生成页面附加的发布地址
     * <br>
     * 实现一个页面发布到多个位置功能（如：缺省首页）
     * 
     * @return 
     * @throws PublishException
     */
    public String[] getPublishAdditionUris()throws PublishException;
}
