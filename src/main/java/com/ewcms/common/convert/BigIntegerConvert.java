/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.common.convert;

import java.math.BigInteger;

/**
 *  转换成BigInteger
 *
 *  @author WangWei
 */
class BigIntegerConvert implements Convertable<BigInteger> {

    @Override
    public BigInteger parse(String value) throws ConvertException {
        try {
        	if (value == null || value.equals("")){
        		return new BigInteger("0");
        	}
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
