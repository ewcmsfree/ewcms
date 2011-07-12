/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.html;

import java.util.List;

import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.core.site.model.Template;
import com.ewcms.generator.PublishException;
import com.ewcms.generator.output.OutputResource;
import com.ewcms.generator.uri.UriRule;
import com.ewcms.generator.uri.UriRuleable;

import freemarker.template.Configuration;

/**
 * 生成其他页面
 * <br>
 * 只能生成单页面。
 * 
 * @author wangwei
 */
public class OtherGenerator extends HomeGenerator {

    public OtherGenerator(Configuration cfg) {
        super(cfg);
    }
    
    @Override
    public List<OutputResource> process(Template template,Site site,Channel channel)throws PublishException {
        UriRuleable rule = new UriRule();
        rule.parse(template.getUriPattern());
        return process(template,rule,site,channel);
    }
}
