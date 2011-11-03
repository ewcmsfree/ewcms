/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.freemarker.directive;

import org.apache.commons.lang.xwork.StringUtils;

/**
 * 格式资源地址，使其在系统保持一直。
 * 
 * @author wangwei
 */
public class UriFormat {

    /**
     * 格式化频道路径，使其同同数据库中路径匹配。
     * 
     * @param path
     * @return
     */
    public static String formatChannelPath(String path){
        String[] s = StringUtils.split(path, "/");
        return StringUtils.join(s, "/");
    }
}
