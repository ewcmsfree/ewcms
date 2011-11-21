/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.report.manager.service;

import com.ewcms.plugin.BaseException;
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
	 * @param repository
	 * @throws BaseException
	 */
    public Long addRepository(Repository repository);

    /**
     * 修改存储
     * 
     * @param repository
     * @throws BaseException
     */
    public Long updRepository(Repository repository);

    /**
     * 查询存储
     * 
     * @param repositoryId
     * @return
     * @throws BaseException
     */
    public Repository findRepository(Long repositoryId);
    
    /**
     * 删除存储
     * 
     * @param repositoryId
     * @throws BaseException
     */
    public void delRepository(Long repositoryId);
}
