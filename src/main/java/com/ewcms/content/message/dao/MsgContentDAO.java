/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.message.dao;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.content.message.model.MsgContent;

/**
 * 
 * @author wu_zhijun
 *
 */
@Repository
public class MsgContentDAO extends JpaDAO<Long, MsgContent> {
}
