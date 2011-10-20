/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.web;

import org.junit.Test;
import org.junit.Assert;

/**
 * JsonBaseAction 单元测试
 * 
 * @author wangwei
 */
public class JsonBaseActionTest {
    
    @Test
    public void testRenderObject(){
        JsonBaseAction action = new JsonBaseActionNotRender();
        String json = action.renderObject(new Object(){
            @SuppressWarnings("unused")
            public String getValue(){
                return "test";
            }
        });
        
        Assert.assertEquals("{\"value\":\"test\"}", json);
    }
    
    @Test
    public void testRenderSuccess(){
        JsonBaseAction action = new JsonBaseActionNotRender();
        String json = action.renderSuccess();
        Assert.assertEquals("{\"value\":null,\"message\":null,\"success\":true}", json);
    }
    
    @Test
    public void testRenderSuccessOfValue(){
        JsonBaseAction action = new JsonBaseActionNotRender();
        String json = action.renderSuccess("test");
        Assert.assertEquals("{\"value\":\"test\",\"message\":null,\"success\":true}", json);
    }
    
    @Test
    public void testRenderSuccessOfValueAndMessage(){
        JsonBaseAction action = new JsonBaseActionNotRender();
        String json = action.renderSuccess("test","test");
        Assert.assertEquals("{\"value\":\"test\",\"message\":\"test\",\"success\":true}", json);
    }
    
    @Test
    public void testRenderError(){
        JsonBaseAction action = new JsonBaseActionNotRender();
        String json = action.renderError();
        
        Assert.assertEquals("{\"value\":null,\"message\":null,\"success\":false}", json);
    }
    
    @Test
    public void testRenderErrorOfMessage(){
        JsonBaseAction action = new JsonBaseActionNotRender();
        String json = action.renderError("test");
        
        Assert.assertEquals("{\"value\":null,\"message\":\"test\",\"success\":false}", json);
    }
    
    @Test
    public void testRender(){
        JsonBaseAction action = new JsonBaseActionNotRender();
        String json = action.render(Boolean.TRUE,"test","test");
        
        Assert.assertEquals("{\"value\":\"test\",\"message\":\"test\",\"success\":true}", json);
    }
    
    class JsonBaseActionNotRender extends JsonBaseAction{
        
        @Override
        public String render(String json){
            // not implement
            return json;
        }
    }
}
