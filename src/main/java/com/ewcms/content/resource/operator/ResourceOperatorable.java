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
