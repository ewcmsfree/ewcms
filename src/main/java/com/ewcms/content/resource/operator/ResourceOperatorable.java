/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ewcms.content.resource.operator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author wangwei
 */
public interface ResourceOperatorable {

    public void setFileNameRule(ResourceNameable nameRule);
    
    public ResourceNameable writer(InputStream stream,String root,String fileName)throws IOException;

    public ResourceNameable writer(File file,String root,String fileName)throws IOException;

    public void delete(File file)throws IOException;

    public void delete(String fileName)throws IOException;
}
