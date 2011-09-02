/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.freemarker.directive;

import org.junit.Assert;
import org.junit.Test;

import com.ewcms.publication.freemarker.directive.out.DirectiveOutable;

/**
 * ChannelDirective单元测试
 * 
 * @author wangwei
 */
public class ChannelDirectiveTest {

    @Test
    public void testAlreadyHasAlias()throws Exception{
        String[] aliases = initAliases();
        
        ChannelDirective directive = new ChannelDirective();
        
        for(String alias : aliases){
            String name = directive.getPropertyName(alias);
            Assert.assertNotNull(name);
        }
    }
    
    @Test
    public void testAlreadyHasDirectiveOut()throws Exception{
        String[] aliases = initAliases();
        
        ChannelDirective directive = new ChannelDirective();
        
        for(String alias : aliases){
            String name = directive.getPropertyName(alias);
            DirectiveOutable out = directive.getDirectiveOut(name);
            Assert.assertNotNull(out);
        }
    }
        
    private String[] initAliases(){
        return new String[]{
             "编号","标题","引导图","链接地址"
        };
    }

}
