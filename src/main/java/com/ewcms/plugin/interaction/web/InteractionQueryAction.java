/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.plugin.interaction.web;

import java.util.Collection;

import com.ewcms.web.QueryBaseAction;
import com.ewcms.web.util.EwcmsContextUtil;
import com.ewcms.common.lang.EmptyUtil;
import com.ewcms.common.query.Resultable;
import com.ewcms.common.query.jpa.EntityQueryable;
import com.ewcms.common.query.jpa.QueryFactory;
import com.ewcms.plugin.interaction.model.Interaction;
import com.ewcms.security.manage.SecurityFacable;
import com.ewcms.security.manage.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


/**
 *
 * @author wangwei
 */
@Controller
public class InteractionQueryAction extends QueryBaseAction {
    
	private static final long serialVersionUID = 8941876237437479766L;

	@Autowired
	private SecurityFacable securityFac;
	
	private int checked;
    private int replay;
    private String title;
    private String content;
    private int type;

    public void setChecked(int checked) {
        this.checked = checked;
    }

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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	protected Resultable queryResult(QueryFactory queryFactory,	String cacheKey, int rows, int page, Order order) {
		EntityQueryable query = queryFactory.createEntityQuery(Interaction.class).setPage(page).setRow(rows).orderDesc("date");
		
		Boolean isAdmin = false;
		Collection<String> autorityNames = EwcmsContextUtil.getAutoritynames();
		for (String autorityName : autorityNames){
			if (autorityName.equals("ROLE_ADMIN")){
				isAdmin = true;
				break;
			}
		}
		
		if (!isAdmin){
			User user = securityFac.getUser(EwcmsContextUtil.getUserName());
			if (user.getOrgan() != null){
				query.eq("organId", user.getOrgan().getId());
			}else{
				query.eq("organId", null);
			}
		}
		
        if (checked != 0) {
            if (checked == 1) {
                query.eq("checked", true);
            } else {
                query.eq("checked", false);
            }
        }

        if (replay != 0) {
            if (replay == 1) {
                query.eq("state", 1);
            } else {
                query.eq("state", 0);
            }
        }
        
        if (type != 0){
        	query.eq("type", getType());
        }
        
        if (EmptyUtil.isStringNotEmpty(title)) {
            query.likeAnywhere("title", title);
        }

        if (EmptyUtil.isStringNotEmpty(content)) {
            query.likeAnywhere("content", content);
        }	
        
		return query.queryCacheResult(cacheKey);
	}

	@Override
	protected Resultable querySelectionsResult(QueryFactory queryFactory, int rows, int page, String[] selections, Order order) {
		return null;
	}
}
