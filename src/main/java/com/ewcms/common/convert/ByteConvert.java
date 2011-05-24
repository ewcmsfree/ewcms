/* 
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.common.convert;

/**
 * 转换成byte数据类型值
 * <p>
 * MIN_VALUE <= byte  <= MAX_VALUE 
 * <p>
 * MIN_VALUE = -pow(2,7)
 * <p>
 *  MAX_VALUE=pwo(2,7)-1
 *  
 * @author 王伟
 */
class ByteConvert implements Convertable<Byte> {

    @Override
    public Byte parse(String value) throws ConvertException {
        try {
            return Byte.valueOf(value);
        } catch (NumberFormatException e) {
            throw new ConvertException(e);
        }
    }

    @Override
    public String parseString(Byte value) {
        return String.valueOf(value.byteValue());
    }
}
