/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.freemarker;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import freemarker.template.SimpleDate;
import freemarker.template.SimpleNumber;
import freemarker.template.SimpleObjectWrapper;
import freemarker.template.SimpleScalar;
import freemarker.template.SimpleSequence;
import freemarker.template.TemplateBooleanModel;
import freemarker.template.TemplateDateModel;
import freemarker.template.TemplateModel;

/**
 * FreemarkerUtil单元测试
 * 
 * @author wangwei
 */
public class FreemarkerUtilTest {

    @Test
    public void testGetBoolean()throws Exception{
        Boolean value = FreemarkerUtil.getBoolean(TemplateBooleanModel.FALSE, true);
        Assert.assertFalse(value);
        
        SimpleScalar scalar = new SimpleScalar("true");
        value = FreemarkerUtil.getBoolean(scalar, false);
        Assert.assertNull(value);
        
        value = FreemarkerUtil.getBoolean(scalar, true);
        Assert.assertTrue(value);
    }
    
    @Test
    public void testGetDate()throws Exception{
        Calendar calendar = Calendar.getInstance();
        calendar.set(2011, 0, 1);
        Date date = new Date(calendar.getTime().getTime());
        SimpleDate model = new SimpleDate(date,TemplateDateModel.DATE);
        
        Date value = FreemarkerUtil.getDate(model, false);
        Assert.assertNotNull(value);
        Assert.assertEquals(date, value);
        
        SimpleScalar scalar = new SimpleScalar("2011-01-01");
        value = FreemarkerUtil.getDate(scalar, false);
        Assert.assertNull(value);
        value = FreemarkerUtil.getDate(scalar, true);
        Assert.assertNotNull(value);
        
        scalar = new SimpleScalar("2011/01/01");
        value = FreemarkerUtil.getDate(scalar, true);
        Assert.assertNull(value);
    }
    
    @Test
    public void testGetInteger()throws Exception{
        SimpleNumber model = new SimpleNumber(Integer.valueOf(1));
        Integer value = FreemarkerUtil.getInteger(model, false);
        Assert.assertEquals(Integer.valueOf(1), value);
        
        SimpleScalar scalar = new SimpleScalar("1");
        value = FreemarkerUtil.getInteger(scalar, false);
        Assert.assertNull(value);
        value = FreemarkerUtil.getInteger(scalar, true);
        Assert.assertEquals(Integer.valueOf(1), value);
        
        scalar = new SimpleScalar("a");
        value = FreemarkerUtil.getInteger(scalar, true);
        Assert.assertNull(value);
    }
    
    @Test
    public void testGetString()throws Exception{
        SimpleScalar model = new SimpleScalar("test");
        String value = FreemarkerUtil.getString(model);
        Assert.assertEquals("test", value);
    }
    
    @Test
    public void testGetBean()throws Exception{
     
        TemplateModel model =SimpleObjectWrapper.getDefaultInstance().wrap(new BeanObject("1"));
        Object value = FreemarkerUtil.getBean(model);
        Assert.assertNotNull(value);
        Assert.assertEquals(new BeanObject("1"), value);
        
        SimpleScalar scalar = new SimpleScalar("test");
        value = FreemarkerUtil.getBean(scalar);
        Assert.assertNull(value);
    }
    
    @Test
    public void testGetSequence()throws Exception{
        Object[] array = new Object[]{"1",Integer.valueOf(0),new BeanObject("1")};
        SimpleSequence model = new SimpleSequence(Arrays.asList(array));
        List<?> value = FreemarkerUtil.getSequence(model);
        Assert.assertTrue(value.size() == 3);
        Assert.assertEquals("1", value.get(0));
        Assert.assertEquals(Integer.valueOf(0), value.get(1));
        Assert.assertEquals(new BeanObject("1"), value.get(2));
    }
    
    static class BeanObject {
        private String key;
        BeanObject(String key){
            this.key = key;
        }
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((key == null) ? 0 : key.hashCode());
            return result;
        }
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            BeanObject other = (BeanObject) obj;
            if (key == null) {
                if (other.key != null)
                    return false;
            } else if (!key.equals(other.key))
                return false;
            return true;
        }
    }
}
