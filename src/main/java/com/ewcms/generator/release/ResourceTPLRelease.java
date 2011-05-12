package com.ewcms.generator.release;

import java.io.IOException;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.core.site.model.TemplateSource;
import com.ewcms.core.site.model.TemplatesrcEntity;
import com.ewcms.generator.dao.GeneratorDAOable;
import com.ewcms.util.FileUtil;

public class ResourceTPLRelease implements ResourceReleaseable {

    private static final Logger logger = LoggerFactory.getLogger(ResourceTPLRelease.class);
    
    @Override
    public void release(GeneratorDAOable dao, int siteId)throws ReleaseException {
        List<TemplateSource> sources = dao.findNotReleaseTemplateSources(siteId);
        String siteDir = dao.getSiteServerDir(siteId);
        for(TemplateSource source : sources){
            try{
                write(source,siteDir);
                dao.releaseTemplateSource(source.getId());
            }catch(IOException e){
                if(logger.isDebugEnabled()){
                    logger.debug(e.toString());
                }
            }
        }        
    }

    @Override
    public void releaseSingle(GeneratorDAOable dao, int id)throws ReleaseException {
        TemplateSource source = dao.getTemplateSource(id);
        String siteDir = source.getSite().getServerDir();
        try{
            write(source,siteDir);                
            dao.releaseTemplateSource(id);
        }catch(IOException e){
            if(logger.isDebugEnabled()){
                logger.debug(e.toString());
            }
        }
        releaseChildren(dao,id,siteDir);
    }
    
    private void releaseChildren(GeneratorDAOable dao,int parentId,String siteDir)throws ReleaseException{
        List<TemplateSource> children = dao.findChildrenTemplateSource(parentId);
        for(TemplateSource child : children){
            int id = child.getId();
            try{
                write(child,siteDir);                
                dao.releaseTemplateSource(id);
            }catch(IOException e){
                if(logger.isDebugEnabled()){
                    logger.debug(e.toString());
                }
            }
            releaseChildren(dao,id,siteDir);
        }
    }
    
    void write(final TemplateSource source,final String siteDir)throws IOException{
        
        if(siteDir == null){
            if(logger.isDebugEnabled()){
                logger.debug("site server directory is null");
            }
            return;
        }
        
        String fileName = String.format("%s/%s", siteDir, source.getPath());
        String dir = getDir(fileName);
        FileUtil.createDirs(dir, true);
        
        TemplatesrcEntity entity = source.getSourceEntity();
        if(entity == null){
            return ;
        }
        OutputStream stream = new FileOutputStream(fileName);
        stream.write(entity.getSrcEntity());
        stream.flush();
        stream.close();
    }
    
    String getDir(final String fileName) {
        return fileName.substring(0, fileName.lastIndexOf("/"));
    }
    
}
