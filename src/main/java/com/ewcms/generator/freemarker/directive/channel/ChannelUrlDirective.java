/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.directive.channel;


/**
 * 频道链接地址标签
 *
 * @deprecated
 * @author wangwei
 */
public class ChannelUrlDirective extends ChannelPropertyDirective {

    @Override
    protected String getPropertyName() {
        return "absUrl";
    }

}
