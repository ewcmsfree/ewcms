/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator;

/**
 * 站点资源发布接口
 * 
 * @author wangwei
 */
public interface ResourceReleaseable {

    /**
     * 发布站点资源
     * <br>
     * 发布站点下未发布所有资源。
     * 
     * @param id 站点编号
     * @throws ReleaseException
     */
    void releaseSite(int id)throws ReleaseException;
    
    /**
     * 重新发布站点资源
     * <br>
     * 重新发布站点所有资源，不管是否已经发布。
     * 
     * @param id 站点编号
     * @throws ReleaseException
     */
    void releaseSiteAgain(int id)throws ReleaseException;

    /**
     * 发布单个资源
     * <br>
     * 发布单个资源，不管它是否已经发布。
     * 
     * @param id 资源编号
     * @throws ReleaseException
     */
    void releaseResource(int id)throws ReleaseException;
}
