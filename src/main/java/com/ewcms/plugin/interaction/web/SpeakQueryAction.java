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

import com.ewcms.common.lang.EmptyUtil;
import com.ewcms.common.query.Resultable;
import com.ewcms.common.query.jpa.QueryFactory;
import com.ewcms.web.QueryBaseAction;

import org.springframework.stereotype.Controller;

/**
 *
 * @author wangwei
 */
@Controller
public class SpeakQueryAction extends QueryBaseAction {
    
	private static final long serialVersionUID = -7338454227886515408L;

	private int checked;
    private String content;

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
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
        String hql = "Select s From Speak As  s,Interaction As i Where s.interactionId=i.id  ";
        String countHql = "Select count(s.id) From Speak As  s,Interaction As i Where s.interactionId=i.id ";
        
        StringBuilder where = new StringBuilder();
        if (checked != 0) {
            where.append(" And s.checked = ");
            if (checked == 1) {
                where.append(true);
            } else {
                where.append(false);
            }
        }
        
        if (EmptyUtil.isStringNotEmpty(content)) {
            where.append(" And s.content like %").append(content).append("%");
        }
        
//        int organId = getOrganId();
//        if(organId != 1){
//            where.append(" And i.organId=").append(organId);
//        }
        String orderString = " Order By s.date";
        
        hql = hql + where.toString() + orderString;
        countHql = countHql + where.toString();
        
        return  queryFactory.createHqlQuery(hql, countHql).queryCacheResult(cacheKey);		
	}

	@Override
	protected Resultable querySelectionsResult(QueryFactory queryFactory,
			int rows, int page, String[] selections, Order order) {
		// TODO Auto-generated method stub
		return null;
	}
}
