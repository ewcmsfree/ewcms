/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.plugin.online.web;

import com.ewcms.common.lang.EmptyUtil;
import com.ewcms.common.query.Resultable;
import com.ewcms.common.query.jpa.EntityQueryable;
import com.ewcms.common.query.jpa.QueryFactory;
import com.ewcms.plugin.interaction.model.Interaction;
import com.ewcms.web.QueryBaseAction;

/**
 *
 * @author wangwei
 */
public class AdvisorQueryAction extends QueryBaseAction {
	
	private static final long serialVersionUID = 1093995809222040158L;

	private int replay;
    private String title;
    private String content;

    public void setReplay(int replay) {
        this.replay = replay;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

	@Override
	protected Resultable queryResult(QueryFactory queryFactory,
			String cacheKey, int rows, int page, Order order) {
		EntityQueryable query = queryFactory.createEntityQuery(Interaction.class).setPage(page).setRow(rows);
        if (replay != 0) {
            if (replay == 1) {
                query.eq("state", 1);
            } else {
                query.eq("state", 0);
            }
        }

        if (EmptyUtil.isStringNotEmpty(title)) {
            query.likeAnywhere("title", title);
        }

        if (EmptyUtil.isStringNotEmpty(content)) {
            query.likeAnywhere("content", content);
        }

        query.orderDesc("date");		
        return query.queryCacheResult(cacheKey);
	}

	@Override
	protected Resultable querySelectionsResult(QueryFactory queryFactory,
			int rows, int page, String[] selections, Order order) {
		// TODO Auto-generated method stub
		return null;
	}
}
