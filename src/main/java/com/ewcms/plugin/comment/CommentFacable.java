/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ewcms.plugin.comment;

import java.util.Date;
import java.util.List;

import com.ewcms.plugin.comment.model.Comment;

/**
 *
 * @author wangwei
 */
public interface CommentFacable {

    public Comment getComment(Long id);
    
    public void commentChecked(Long id,Boolean checked, Date date);

    public void deleteComment(List<Long> ids);
}
