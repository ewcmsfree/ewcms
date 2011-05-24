package com.ewcms.common.convert;

/* 
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
import java.math.BigInteger;

/**
 *  转换成BigInteger
 *
 *  @author 王伟
 */
class BigIntegerConvert implements Convertable<BigInteger> {

    @Override
    public BigInteger parse(String value) throws ConvertException {
        try {
            return new BigInteger(value);
        } catch (NumberFormatException e) {
            throw new ConvertException(e);
        }
    }

    @Override
    public String parseString(BigInteger value) {
        return value.toString();
    }
}
