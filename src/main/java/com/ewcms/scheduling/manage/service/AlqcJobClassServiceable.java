/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.manage.service;

import java.util.List;

import com.ewcms.scheduling.BaseException;
import com.ewcms.scheduling.model.AlqcJobClass;

/**
 *
 * @author 吴智俊
 */
public interface AlqcJobClassServiceable {
    public Integer saveJobClass(AlqcJobClass alqcJobClass) throws BaseException;

    public Integer updateJobClass(AlqcJobClass alqcJobClass) throws BaseException;

    public AlqcJobClass findByJobClass(Integer id) throws BaseException;

    public List<AlqcJobClass> findByAllJobClass() throws BaseException;

    public void deletedJobClass(Integer id) throws BaseException;

    public AlqcJobClass findByAlqcJobClassByClassEntity(String classEntity) throws BaseException;
}
