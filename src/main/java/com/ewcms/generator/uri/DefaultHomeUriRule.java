/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.uri;

import com.ewcms.generator.ReleaseException;

/**
 * 缺省首页 uri生成规则
 * 
 * @author wangwei
 */
public class DefaultHomeUriRule extends UriRule{
    public DefaultHomeUriRule()throws ReleaseException{
        super("${c.absUrl}/index.html");
    }
}
