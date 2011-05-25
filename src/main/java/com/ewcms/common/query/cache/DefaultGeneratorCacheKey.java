/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.common.query.cache;

import java.util.Random;

public class DefaultGeneratorCacheKey implements GeneratorCacheKeyable {

    private static final Random random = new Random();
    
    @Override
    public String generatorKey(Object... params) {
        random.setSeed(System.currentTimeMillis());
        return String.valueOf(Math.abs(random.nextLong()));
    }

}
