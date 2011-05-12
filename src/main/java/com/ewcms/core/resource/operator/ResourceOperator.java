/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ewcms.core.resource.operator;

import com.ewcms.util.FileUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author wangwei
 */
public class ResourceOperator implements ResourceOperatorable{

    private ResourceNameable nameRule;
    
    @Override
    public void setFileNameRule(ResourceNameable nameRule) {
        this.nameRule = nameRule;
    }

    @Override
    public ResourceNameable writer(InputStream inputStream,String root,String fileName) throws IOException {
        ResourceNameable rule = getNameRule(root,fileName);
        FileUtil.createDirs(rule.getPersistDir(), true);
        FileUtil.writerFile(inputStream, rule.getFileNewName());

        return rule;
    }

    private ResourceNameable getNameRule(String root , String fileName){
        if(nameRule == null){
            return new  ResourceName.Builder(root,fileName).build();
        }else{
            return nameRule;
        }
    }

    @Override
    public ResourceNameable writer(File file,String root,String fileName) throws IOException {
        InputStream stream = new FileInputStream(file);
        ResourceNameable rule =  writer(stream,root,fileName);
        stream.close();
        return rule;
    }

    @Override
    public void delete(File file) throws IOException {
        FileUtil.deleteFile(file);
    }

    @Override
    public void delete(String fileName) throws IOException {
        File file = new File(fileName);
        delete(file);
    }
}
