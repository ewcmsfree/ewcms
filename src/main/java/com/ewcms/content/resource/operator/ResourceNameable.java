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

/**
 *
 * @author wangwei
 */
public interface ResourceNameable {

    public String getNewName();
    
    public String getFileNewName();

    public String getPersistDir();

    public String getReleaseaPath();

    public String getReleasePathZip();
    
    public String getFileNewNameZip();
}
