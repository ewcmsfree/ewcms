/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.web.filter.render;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 返回数据接口，访问页面404错误时，通过该接口返回所需要的内容。
 * 
 * @author wangwei
 */
public interface Renderable {

    /**
     * 返回内容
     * 
     * @param request
     * @param response
     * @return true:返回
     */
    boolean render(HttpServletRequest request,HttpServletResponse response)throws IOException;
}
