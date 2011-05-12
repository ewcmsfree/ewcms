/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ewcms.core.resource.operator;

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
