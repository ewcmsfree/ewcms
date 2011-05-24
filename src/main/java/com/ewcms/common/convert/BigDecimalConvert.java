/*
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.common.convert;

import java.math.BigDecimal;

/**
 *  转换成BigDecimal
 *
 *  @author 王伟
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
