/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/**
 * 
 */
package com.ewcms.core.site.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ewcms.core.site.dao.TemplateSourceDAO;
import com.ewcms.core.site.model.Site;
import com.ewcms.core.site.model.TemplateSource;
import com.ewcms.web.util.EwcmsContextUtil;

/**
 * @author 周冬初
 *
 */
@Service
public class TemplateSourceService implements TemplateSourceServiceable{
	@Autowired
	private TemplateSourceDAO templateSourceDao;
	
	public TemplateSource getTemplateSource(Integer id){
		return templateSourceDao.get(id);
	}
	
	public Integer addTemplateSource(TemplateSource vo){
		templateSourceDao.persist(vo);
		return vo.getId();
	}
	
	public Integer updTemplateSource(TemplateSource vo){
		updPubPath(vo);
		return vo.getId();
	}
	
	/**
	 * 模板目录发生修改，需要更新模板发布路径，并且包括其子模板路径
	 * 
	 * @param channel
	 *            模板
	 */
	private void updPubPath(final TemplateSource vo) {
		for (TemplateSource child : templateSourceDao.getTemplateSourceChildren(vo.getId(), getCurSite().getId())) {
			child.setPath(null);
			updPubPath(child);
		}
		templateSourceDao.merge(vo);
	}


	public void delTemplateSource(Integer id){
		templateSourceDao.removeByPK(id);
	}
	
	/**
	 * 获取跟模板资源集
	 * 
	 */ 	
	public List<TemplateSource> getTemplaeSourceTreeList(Boolean channelEnable){
		return getTemplateSourceChildren(null,channelEnable);
	}
	
	/**
	 * 获取模板资源子资源集
	 * 
	 */ 	
	public List<TemplateSource> getTemplaeSourceTreeList(Integer parentId,Boolean channelEnable){
		return getTemplateSourceChildren(parentId,channelEnable);
	}	
	
    private List<TemplateSource> getTemplateSourceChildren(Integer parentId,Boolean channelEnable){
        List<TemplateSource> srcList = templateSourceDao.getTemplateSourceChildren(parentId,EwcmsContextUtil.getCurrentSite().getId());
        List<TemplateSource> validateList = new ArrayList<TemplateSource>();
        for(TemplateSource vo:srcList){
        	if(!channelEnable&&vo.getName().equals(getSiteSrcName())){
        		continue;
        	}
        	validateList.add(vo);
        }
        return validateList;
    }	
	/**
	 * 获取站点专栏资源根目录
	 * 
	 */    
    public TemplateSource channelSRCRoot(){
    	return channelTemplateSource(null);
    }
    
    public TemplateSource channelTemplateSource(String srcName){
    	if(srcName==null||srcName.length()==0){
        	TemplateSource vo = templateSourceDao.getChannelTemplateSource(getSiteSrcName(),getCurSite().getId(),null);
        	if(vo == null){//没有站点专栏模板节点，就创建
        		vo = new TemplateSource();
        		vo.setDescribe(getCurSite().getSiteName()+"专栏资源目录");
        		vo.setName(getSiteSrcName());
        		vo.setSite(getCurSite());
        		vo.setSize("0KB");
        		vo.setPath(getSiteSrcName());
        		templateSourceDao.persist(vo);
        	}
        	return vo;
    	}else{
    		Integer parentId = channelSRCRoot().getId();
    		TemplateSource vo = templateSourceDao.getChannelTemplateSource(srcName,getCurSite().getId(),parentId);
        	if(vo == null){//没有站点专栏模板节点，就创建
        		vo = new TemplateSource();
        		vo.setDescribe(srcName+"专栏资源目录");
        		vo.setName(srcName);
        		vo.setSite(getCurSite());
        		vo.setSize("0KB");
        		vo.setParent(channelSRCRoot());
        		vo.setPath(getSiteSrcName()+"/"+srcName);
        		templateSourceDao.persist(vo);
        	} 
        	return vo;
    	}    	
    }
    
	private Site getCurSite(){
		return EwcmsContextUtil.getCurrentSite();
	} 
	
	private String getSiteSrcName(){
		return getCurSite().getId()+"src";
	}

	@Override
	public List<TemplateSource> findPublishTemplateSources(Integer siteId, Boolean forceAgain) {
		return templateSourceDao.getPublishTemplateSources(siteId, forceAgain);
	}

	@Override
	public List<TemplateSource> getTemplateSourceChildren(Integer id) {
		return templateSourceDao.getTemplateSourceChildren(id, getCurSite().getId());
	}

	@Override
	public void publishTemplateSourceSuccess(Integer id) {
		TemplateSource vo = getTemplateSource(id);
		vo.setRelease(true);
		templateSourceDao.persist(vo);
	}

	@Override
	public TemplateSource getTemplateSourceByUniquePath(String path) {
		return templateSourceDao.getTemplateSourceByPath(getCurSite().getId()+path);
	}	
}
