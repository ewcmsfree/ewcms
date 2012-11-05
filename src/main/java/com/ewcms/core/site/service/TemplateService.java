/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.core.site.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.content.history.History;
import com.ewcms.content.history.dao.HistoryModelDAO;
import com.ewcms.content.history.model.HistoryModel;
import com.ewcms.content.history.util.ByteToObject;
import com.ewcms.core.site.dao.ChannelDAO;
import com.ewcms.core.site.dao.TemplateDAO;
import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.core.site.model.Template;
import com.ewcms.core.site.model.TemplateEntity;
import com.ewcms.core.site.util.ConvertUtil;
import com.ewcms.web.util.EwcmsContextUtil;

/**
 * @author 周冬初
 *
 */
@Service
public class TemplateService implements TemplateServiceable{

	@Autowired
	private TemplateDAO templateDAO;
	@Autowired
	private ChannelDAO channelDAO;
	@Autowired
	private HistoryModelDAO historyModelDAO;
	
	public Template getTemplate(Integer id){
		return templateDAO.get(id);
	}
	
	@History(modelObjectIndex = 0)	
	public Integer addTemplate(Template vo){
		templateDAO.persist(vo);
		return vo.getId();
	}
	
	@History(modelObjectIndex = 0)	
	public Integer updTemplate(Template vo){
		templateDAO.merge(vo);	
		updPubPath(vo);
		return vo.getId();
	}
	
	/**
	 * 模板目录发生修改，需要更新模板发布路径，并且包括其子模板路径
	 * 
	 * @param channel 模板
	 */
	private void updPubPath(final Template vo) {
		for (Template child : templateDAO.getTemplateChildren(vo.getId(), getCurSite().getId(), null)) {
			child.setPath(null);
			updPubPath(child);
		}
		templateDAO.merge(vo);
	}
	
	public void delTemplateBatch(List<Integer> idList){
		for(Integer id :idList){
			delTemplate(id);
		}		
	}
	
	public void delTemplate(Integer id){
		delTemplateChildren(id);
		templateDAO.removeByPK(id);
	}
	
	private void delTemplateChildren(Integer id){
		List<Template> templates = templateDAO.getTemplateChildren(id, getCurSite().getId(), null);
		if (templates != null && !templates.isEmpty()){
			for (Template template : templates){
				List<Template> templateChildrens = templateDAO.getTemplateChildren(template.getId(), getCurSite().getId(), null);
				if (templateChildrens != null && !templateChildrens.isEmpty()){
					delTemplateChildren(template.getId());
				}else{
					templateDAO.remove(template);
				}
			}
		}
	}
	
	public List<Template> getTemplateList(){
		return templateDAO.getTemplateList(getCurSite().getId());
	}
	/**
	 * 获取跟模板集
	 * 
	 */ 	
	public List<Template> getTemplaeTreeList(Boolean channelEnable){
		return getTemplateChildren(null,channelEnable,null);
	}
	
	/**
	 * 获取模板子模板集
	 * 
	 */ 	
	public List<Template> getTemplaeTreeList(Integer parentId,Boolean channelEnable){
		return getTemplateChildren(parentId,channelEnable,null);
	}

	/**
	 * 获取模板子模板集及 某个专栏模板
	 * 
	 */ 	
	public List<Template> getTemplaeTreeList(Integer parentId,String channelName){
		return getTemplateChildren(parentId,true,channelName);
	}
	
	/**
	 * 获取模板子模板
	 * 
	 */ 	
    private List<Template> getTemplateChildren(Integer parentId,Boolean channelEnable,String channelName){
        List<Template> tplList = getTemplateChildren(parentId,channelName);
        List<Template> validateList = new ArrayList<Template>();
        for(Template vo:tplList){
        	if(!channelEnable&&vo.getName().equals(getSiteTplName())){//屏蔽所有专栏模板
        		continue;
        	}
        	if(channelName!=null && parentId!=null){//屏蔽其它专栏模板,只显示某个专栏模板
        		if(getTemplate(parentId).getName().equals(getSiteTplName())&&!vo.getName().equals(channelName)){
        			continue;
        		}
        	}
        	validateList.add(vo);
        }
        return validateList;
    }
	
	private List<Template> getTemplateChildren(Integer parentId,String channelName){
		if(channelName!=null && getTemplate(parentId).getName().equals(channelName))
			return templateDAO.getTemplateChildren(parentId,EwcmsContextUtil.getCurrentSite().getId(),Integer.valueOf(channelName));
		return templateDAO.getTemplateChildren(parentId,EwcmsContextUtil.getCurrentSite().getId(),null);
	}  
	/**
	 * 获取站点专栏模板根目录
	 * 
	 */    
    public Template channelTPLRoot(){
    	return channelTemplate(null);
    }
	/**
	 * 获取模板唯一路径
	 * 
	 */      
    public String getTemplateUniquePath(Integer siteId,Integer channelId,String templateName){
    	return siteId.toString()+"/"+ siteId.toString()+"tpl/"+channelId.toString()+"/"+templateName;
    }
    
	/**
	 * 获取站点专栏模板目录
	 * 
	 */     
    public Template channelTemplate(String tplName){
    	if(tplName==null||tplName.length()==0){
        	Template vo = templateDAO.getChannelTemplate(getSiteTplName(),getCurSite().getId(),null);
        	if(vo == null){//没有站点专栏模板节点，就创建
        		vo = new Template();
        		vo.setDescribe(getCurSite().getSiteName()+"专栏模板目录");
        		vo.setName(getSiteTplName());
        		vo.setSite(getCurSite());
        		vo.setPath(getSiteTplName());
        		vo.setSize("0KB");
        		templateDAO.persist(vo);
        	} 
        	return vo;
    	}else{
    		Integer parentId = channelTPLRoot().getId();
    		Template vo = templateDAO.getChannelTemplate(tplName,getCurSite().getId(),parentId);
        	if(vo == null){//没有站点专栏模板节点，就创建
        		vo = new Template();
        		vo.setDescribe(tplName+"专栏模板目录");
        		vo.setName(tplName);
        		vo.setSite(getCurSite());
        		vo.setPath(getSiteTplName()+"/"+tplName);
        		vo.setSize("0KB");
        		vo.setParent(channelTPLRoot());
        		templateDAO.persist(vo);
        	} 
        	return vo;
    	}
    }
    
	private Site getCurSite(){
		return EwcmsContextUtil.getCurrentSite();
	}
	
	private String getSiteTplName(){
		return getCurSite().getId()+"tpl";
	}
	
	@Override
	public List<Template> getTemplatesInChannel(Integer id) {
		return templateDAO.getTemplatesInChannel(id);
	}
	
	@Override
	public Template getTemplateByUniquePath(String path) {
		return templateDAO.getTemplateByPath(path);
	}
	
	@Override
	public String getUniquePathOfChannelTemplate(Integer siteId, Integer channelId, String name) {
		return siteId.toString()+"/"+siteId.toString()+"tpl/"+channelId.toString()+"/"+name;
	}
	
	@Override
	public void saveAppChild(Integer channelId, List<Integer> templateIds, Boolean cover) {
		List<Template> templates = new ArrayList<Template>();
		for (Integer templateId : templateIds){
			Template template = templateDAO.get(templateId);
			templates.add(template);
		}
		templateChild(channelId, channelId, templates, cover);
	}
	
	private void templateChild(Integer initChannelId, Integer channelId, List<Template> templates, Boolean cover){
		List<Channel> channels = channelDAO.getChannelChildren(channelId);
		if (channels != null && !channels.isEmpty()){
			for (Channel channel : channels){
				for (Template template : templates){
					Template dbTemplate = templateDAO.findTemplateByChannelIdAndTemplateType(channel.getId(), template.getType());

					TemplateEntity templateEntity = template.getTemplateEntity();
					TemplateEntity newTemplateEntity = new TemplateEntity();
					if (dbTemplate != null){
						if (!cover) continue;
						if (templateEntity != null && templateEntity.getTplEntity() != null && templateEntity.getTplEntity().length != 0){
							newTemplateEntity.setTplEntity(templateEntity.getTplEntity());
							dbTemplate.setSize(ConvertUtil.kb(templateEntity.getTplEntity().length));
							dbTemplate.setTemplateEntity(newTemplateEntity);
							try{
								updTemplate(dbTemplate);
							}catch(Exception e){
							}
						}
					}else{
						Template newTemplate = new Template();
						newTemplate.setUpdTime(new Date(Calendar.getInstance().getTime().getTime()));
						newTemplate.setChannelId(channel.getId());
						if (templateEntity != null && templateEntity.getTplEntity() != null && templateEntity.getTplEntity().length != 0){
							newTemplateEntity.setTplEntity(templateEntity.getTplEntity());
							newTemplate.setSize(ConvertUtil.kb(templateEntity.getTplEntity().length));
						}else{
							newTemplate.setSize(ConvertUtil.kb(0));
						}
						
						newTemplate.setTemplateEntity(newTemplateEntity);
						newTemplate.setSite(template.getSite());
						newTemplate.setDescribe(template.getDescribe());
						newTemplate.setType(template.getType());
						
						String name = template.getName();
						String newName = "app_" + initChannelId + "_" + name;
						
						newTemplate.setName(newName);
						newTemplate.setParent(channelTemplate(channel.getId().toString()));
						try{
							addTemplate(newTemplate);
						}catch(Exception e){
						}
					}
				}
				templateChild(initChannelId, channel.getId(), templates, cover);
			}
		}
	}

	@Override
	public void exportTemplateZip(Integer templateId, ZipOutputStream zos, String templatePath) {
		try{
			Template template = getTemplate(templateId);
			if (template == null) return;

			TemplateEntity templateEntity = template.getTemplateEntity();
			
			String filePath = templatePath + template.getName();
			ZipEntry zipEntry;
			if (templateEntity == null){
				filePath += "/";
				
				//创建栏目目录
				zipEntry = new ZipEntry(filePath);
				zipEntry.setUnixMode(755);
				zos.putNextEntry(zipEntry);
				
				List<Template> templateChildrens = templateDAO.getTemplateChildren(template.getId(), getCurSite().getId(), null);
				
				for (Template templateChildren : templateChildrens){
					exportTemplateZip(templateChildren.getId(), zos, filePath);
				}
			}else{
				zipEntry = new ZipEntry(filePath);
				zipEntry.setUnixMode(644);
				zos.putNextEntry(zipEntry);
				
				BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(templateEntity.getTplEntity()));
				int b;
				while((b = bis.read())!=-1){  
					zos.write(b);  
		        }  
				
				zos.closeEntry();
				bis.close();
			}
		}catch(Exception e){
			
		}
	}

	@Override
	public Boolean restoreTemplate(Integer templateId, Long historyId) {
		Boolean result = false;
		try{
			HistoryModel historyModel = historyModelDAO.get(historyId);
			String className = Template.class.getPackage().getName() + "." + Template.class.getSimpleName();
			if (className.equals(historyModel.getClassName())){
				Template oldTemplate = templateDAO.get(templateId);
				TemplateEntity oldTemplateEntity = oldTemplate.getTemplateEntity();
				Object obj = ByteToObject.conversion(historyModel.getModelObject());
				Template template = (Template) obj;
				TemplateEntity templateEntity = template.getTemplateEntity();
				if (templateEntity == null) return result;
				if (oldTemplateEntity == null) oldTemplateEntity = new TemplateEntity();
				oldTemplateEntity.setTplEntity(templateEntity.getTplEntity());
				templateDAO.merge(oldTemplate);
				return true;
			}
			return result;
		}catch (Exception e){
			return result;
		}
	}
}
