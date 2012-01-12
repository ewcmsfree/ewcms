/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.report.manager.service;

import java.util.List;

import com.ewcms.core.site.model.Site;
import com.ewcms.plugin.report.model.Repository;

/**
 * 
 * @author wu_zhijun
 *
 */
public interface RepositoryServiceable {
	/**
	 * 新增存储
	 * 
	 * @param repository 存储对象
	 * @return Long 存储编号
	 */
    public Long addRepository(Repository repository);

    /**
     * 修改存储
     * 
     * @param repository 存储对象
     * @return Long 存储编号
     */
    public Long updRepository(Repository repository);

    /**
     * 查询存储
     * 
     * @param repositoryId 存储编号
     * @return Repository 存储对象
     */
    public Repository findRepositoryById(Long repositoryId);
    
    /**
     * 删除存储
     * 
     * @param repositoryId 存储编号
     */
    public void delRepository(Long repositoryId);
    
    /**
     * 发布存储报表
     * 
     * @param repositoryIds 存储编号集合 
     * @param site 站点对象
     */
    public void publishRepository(List<Long> repositoryIds, Site site);
}
