/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.content.particular.web;

import static com.ewcms.common.lang.EmptyUtil.isNotNull;
import static com.ewcms.common.lang.EmptyUtil.isStringNotEmpty;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.common.query.Resultable;
import com.ewcms.common.query.jpa.EntityQueryable;
import com.ewcms.common.query.jpa.QueryFactory;
import com.ewcms.content.particular.ParticularFacable;
import com.ewcms.content.particular.model.ProjectBasic;
import com.ewcms.content.particular.model.ProjectBasic.Nature;
import com.ewcms.core.site.model.Organ;
import com.ewcms.web.QueryBaseAction;
import com.ewcms.web.util.EwcmsContextUtil;

public class ProjectBasicQueryAction extends QueryBaseAction{

	private static final long serialVersionUID = -4149344019910643538L;

	@Autowired
	private ParticularFacable particularFac;
	
	private Integer channelId;
	
	public Integer getChannelId() {
		return channelId;
	}
	
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	@Override
	protected Resultable queryResult(QueryFactory queryFactory, String cacheKey, int rows, int page, Order order) {
    	EntityQueryable query = queryFactory.createEntityQuery(ProjectBasic.class).setPage(page).setRow(rows).orderDesc("id");
    	
    	query.eq("channelId", getChannelId());

    	String code = getParameterValue(String.class,"code", "");
    	if (isNotNull(code)) query.likeAnywhere("code", code);
    	
    	String buildNature = getParameterValue(String.class, "buildNature", "");
    	if (isNotNull(buildNature) && buildNature.trim().length() > 0) {
    		query.eq("bildNature", Nature.valueOf(buildNature));
    	}
    	
    	String industryCode = getParameterValue(String.class, "industryCode", "");
    	if (isNotNull(industryCode)) query.eq("industryCode.code", industryCode);
    	
    	String name = getParameterValue(String.class, "name", "");
    	if (isStringNotEmpty(name)) query.likeAnywhere("name", name);
    	
		if (!EwcmsContextUtil.getGroupnames().contains("GROUP_GCLY_ADMIN")){
			Organ organ = particularFac.findOrganByUserName();
			if (organ == null){
				query.eq("organ.id", null);
			}else{
				query.eq("organ.id", organ.getId());
			}
		}
   	
    	entityOrder(query, order);
    	return query.queryCacheResult(cacheKey);
	}

	@Override
	protected Resultable querySelectionsResult(QueryFactory queryFactory, int rows, int page, String[] selections, Order order) {
    	EntityQueryable query = queryFactory.createEntityQuery(ProjectBasic.class).setPage(page).setRow(rows).orderDesc("id");
    	
    	query.eq("channelId", getChannelId());
    	
		if (!EwcmsContextUtil.getGroupnames().contains("GROUP_GCLY_ADMIN")){
			Organ organ = particularFac.findOrganByUserName();
			if (organ == null){
				query.eq("organ.id", null);
			}else{
				query.eq("organ.id", organ.getId());
			}
		}

    	List<Long> ids = getIds(Long.class);
        query.in("id", ids);
        
        return query.queryResult();    
	}
}
