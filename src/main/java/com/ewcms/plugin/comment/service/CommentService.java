/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.plugin.comment.service;

import java.util.List;

import com.ewcms.plugin.comment.dao.CommentDAO;
import com.ewcms.plugin.comment.model.Comment;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author wangwei
 */
@Service
public class CommentService implements CommentServiceable {

    @Autowired
    private CommentDAO commentDAO;

    @Override
    public Comment getComment(Long id) {
    	if (id == null) return new Comment();
        return commentDAO.get(id);
    }

    @Override
    public void commentChecked(Long id, Boolean checked) {
        Comment comment = commentDAO.get(id);
        if (comment == null) {
            return;
        }
        comment.setChecked(checked);
        commentDAO.persist(comment);
    }

	@Override
	public void deleteComment(List<Long> ids) {
		for (Long id : ids){
			commentDAO.removeByPK(id);
		}
	}

}
