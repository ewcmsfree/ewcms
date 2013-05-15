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

import java.util.List;

import com.ewcms.plugin.comment.model.Comment;
import com.ewcms.plugin.comment.service.CommentServiceable;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author wangwei
 */
@Service
public class CommentFac implements CommentFacable {

    @Autowired
    private CommentServiceable commentService;

    @Override
    public void commentChecked(Long id,Boolean checked) {
        commentService.commentChecked(id,checked);
    }

    @Override
    public Comment getComment(Long id) {
        return commentService.getComment(id);
    }

	@Override
	public void deleteComment(List<Long> ids) {
		commentService.deleteComment(ids);
	}

}
