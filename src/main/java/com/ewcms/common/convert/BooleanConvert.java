/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.common.convert;

/**
 *  转换成布尔型boolean
 *  
 *  if(value = "true") //忽略大小写
 *       return true;
 *  else
 *       return null;
 *       
 *  @author 王伟
 */
class BooleanConvert implements Convertable<Boolean> {

    @Override
    public Boolean parse(String value)throws ConvertException {
        return Boolean.valueOf(value);
    }

    @Override
    public String parseString(Boolean value) {
        return value ? "true":"false";
    }

}
