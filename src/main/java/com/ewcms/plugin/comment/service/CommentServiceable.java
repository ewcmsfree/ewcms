/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.comment.service;

import java.util.List;

import com.ewcms.plugin.comment.model.Comment;

/**
 * @author wuzhijun
 */
public interface CommentServiceable {
	public Comment getComment(Long id);

	public void commentChecked(Long id, Boolean checked);
	
	public void deleteComment(List<Long> ids);
}
