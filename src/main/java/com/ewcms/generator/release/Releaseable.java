/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ewcms.generator.release;

import com.ewcms.core.site.model.Channel;
import com.ewcms.generator.dao.GeneratorDAOable;
import freemarker.template.Configuration;

/**
 *
 * @author wangwei
 */
public interface Releaseable {

    public void setEncoder(String  charsetName);

    public void release(Configuration cfg,GeneratorDAOable dao,Channel channel) throws ReleaseException;

    public void releaseDebug(Configuration cfg,GeneratorDAOable dao,Channel channel) throws ReleaseException;
    
}
