/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator;

/**
 * 发布和生成html页面
 *
 * @author wangwei
 */
public interface HtmlReleaseable {

    /**
     * 发布和生成频道下html页面
     * <br>
     * 只发布发生改变的频道，如频道文章或关联频道数据改变。
     * 
     * @param id 频道编号
     * @throws ReleaseException
     */
    void releaseChannel(int id) throws ReleaseException;

    /**
     * 重新发布和生成频道下html页面
     * <br>
     * 频道下的内容都会重新生成和发布。
     * 
     * @param id 频道编号
     * @throws ReleaseException
     */
    void releaseChannelAgain(int id) throws ReleaseException;
    
    /**
     * 发布和生成单模板的html页面
     * <br>
     * 模板对应的页面都会从新生成和发布。
     *     
     * @param id 模板编号
     * @throws ReleaseException
     */
    void releaseTemplate(int id) throws ReleaseException;
}
