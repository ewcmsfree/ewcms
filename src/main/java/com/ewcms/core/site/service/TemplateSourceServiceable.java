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
import com.ewcms.core.site.model.TemplateSource;
import com.ewcms.publication.service.TemplateSourcePublishServiceable;

/**
 * @author 周冬初
 *
 */
public interface TemplateSourceServiceable extends TemplateSourcePublishServiceable {
	public Integer addTemplateSource(TemplateSource vo);
	
	public Integer updTemplateSource(TemplateSource vo);
	
	public void delTemplateSource(Integer id);
	
	/**
	 * 获取跟模板资源集
	 * 
	 */ 	
	public List<TemplateSource> getTemplaeSourceTreeList(Boolean channelEnable);
	
	/**
	 * 获取模板资源子资源集
	 * 
	 */ 	
	public List<TemplateSource> getTemplaeSourceTreeList(Integer parentId,Boolean channelEnable);
	

	/**
	 * 获取站点专栏资源根目录
	 * 
	 */    
    public TemplateSource channelSRCRoot();
    
    public TemplateSource channelTemplateSource(String srcName);
    
    /**
     * 通过UniquePath得到模板，模板不存在返回null值
     * 
     * @param path 模板唯一路径
     * @return
     */
    public TemplateSource getTemplateSourceByUniquePath(String path);    
}
