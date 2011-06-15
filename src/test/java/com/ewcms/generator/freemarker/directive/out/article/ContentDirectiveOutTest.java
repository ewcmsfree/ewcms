/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.directive.out.article;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.ewcms.content.document.model.Content;

import freemarker.core.Environment;
import freemarker.template.TemplateModelException;

/**
 * ContentDirectiveOut单元测试
 * 
 * @author wangwei
 */
public class ContentDirectiveOutTest {
    
    @Test
    public void testValueIsEmptyOfGetContent()throws Exception{
        ContentDirectiveOutMock out = new ContentDirectiveOutMock();
        
        String content = out.getContent(null, null);
        Assert.assertNull(content);
        content = out.getContent(new ArrayList<Content>(), null);
        Assert.assertNull(content);
    }
    
    @Test
    public void testPageNumberOverflowOfGetContent()throws Exception{
                
        ContentDirectiveOutMock out = new ContentDirectiveOutMock();
        out.setPageNumber(2);
        
        String value = out.getContent(initContents(), null);
        Assert.assertNull(value);
    }
    
    @Test
    public void testGetContent()throws Exception{
        ContentDirectiveOutMock out = new ContentDirectiveOutMock();
        out.setPageNumber(1);
        
        String value = out.getContent(initContents(), null);
        Assert.assertEquals("test1", value);
    }
    
    @Test
    public void testLoopValueCall()throws Exception{
        ContentDirectiveOutMock out = new ContentDirectiveOutMock();
        out.setPageNumber(0);
        
        Object value = out.loopValue(initContents(), null,null);
        Assert.assertEquals("test", value);
    }
    
    @Test
    public void testConstructOutCall()throws Exception{
        ContentDirectiveOutMock out = new ContentDirectiveOutMock();
        out.setPageNumber(1);
        
        String value = out.constructOut(initContents(), null,null);
        Assert.assertEquals("test1", value);
    }
    
    private List<Content> initContents(){
        
        List<Content> list = new ArrayList<Content>();
        Content content = new Content();
        content.setDetail("test");
        list.add(content);
        
        content = new Content(); 
        content.setDetail("test1");
        list.add(content);
        
        return list;
    }

    
    class ContentDirectiveOutMock extends ContentDirectiveOut{
        private int pageNumber = 0;
        
        @Override
        int getPageNumber(Environment env)throws TemplateModelException{
            return pageNumber;
        }
        
        void setPageNumber(int pageNumber){
            this.pageNumber = pageNumber;
        }
    }
}
