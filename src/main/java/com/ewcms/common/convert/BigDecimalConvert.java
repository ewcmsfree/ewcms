/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.common.convert;

import java.math.BigDecimal;

/**
 *  转换成BigDecimal
 *
 *  @author WangWei
 */
class BigDecimalConvert implements Convertable<BigDecimal> {

    @Override
    public BigDecimal parse(String value)throws ConvertException{
        try {
            return new BigDecimal(value);
        } catch (NumberFormatException e) {
            throw new ConvertException(e);
        }
    }

    @Override
    public String parseString(BigDecimal value) {
        return value.toString();
    }
}
