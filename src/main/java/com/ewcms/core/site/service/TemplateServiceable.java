/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/**
 * 
 */
package com.ewcms.core.site.service;

import java.util.List;
import com.ewcms.core.site.model.Template;
import com.ewcms.publication.service.TemplatePublishServiceable;

/**
 * @author 周冬初
 *
 */
public interface TemplateServiceable extends TemplatePublishServiceable {
	public Integer addTemplate(Template vo);

	public Integer updTemplate(Template vo);
	
	public void delTemplateBatch(List<Integer> idList);
	
	public void delTemplate(Integer id);
	public List<Template> getTemplateList();
	/**
	 * 获取跟模板集
	 * 
	 */ 	
	public List<Template> getTemplaeTreeList(Boolean channelEnable);
	
	/**
	 * 获取模板子模板集
	 * 
	 */ 	
	public List<Template> getTemplaeTreeList(Integer parentId,Boolean channelEnable);

	/**
	 * 获取模板子模板集�?某个专栏模板
	 * 
	 */ 	
	public List<Template> getTemplaeTreeList(Integer parentId,String channelName);
	/**
	 * 获取站点专栏模板根目录
	 * 
	 */    
    public Template channelTPLRoot();
	/**
	 * 获取模板唯一路径
	 * 
	 */      
    public String getTemplateUniquePath(Integer siteId,Integer channelId,String templateName);
    
	/**
	 * 获取站点专栏模板目录
	 * 
	 */     
    public Template channelTemplate(String tplName);
    
    /**
     * 把模板应用于子栏目
     * @param channelId 栏目编号
     * @param templateIds 选择应用的模板
     */
    public void saveAppChild(Integer channelId, List<Integer> templateIds);
}
