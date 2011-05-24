/* 
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
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
