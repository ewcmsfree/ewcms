/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.web.action.document.history;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ewcms.aspect.history.fac.HistoryModelFacable;
import com.ewcms.aspect.history.model.HistoryModel;
import com.ewcms.aspect.history.util.ByteToObject;
import com.ewcms.comm.jpa.query.PageQueryable;
import com.ewcms.content.document.model.Article;
import com.ewcms.util.DataGrid;
import com.ewcms.util.JSONUtil;
import com.ewcms.util.Struts2Util;
import com.ewcms.web.action.QueryBaseAction;

/**
 * 
 * @author 吴智俊
 */
@Controller("contenthistory")
public class QueryAction extends QueryBaseAction {

	private static final long serialVersionUID = 6797696723188141927L;
	
	private DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");

	@Autowired
	private HistoryModelFacable historyModelFac;

	private Integer articleId;
	
	private Date startDate;
	
	private Date endDate;

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected PageQueryable constructNewQuery(Order order) {
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected PageQueryable constructQuery(Order order) {
		return null;
	}

	@Override
	public String query() {
		String className = Article.class.getPackage().getName() + "." + Article.class.getSimpleName();
		String idName = "id";
		String idValue = getArticleId().toString();
		
		List<HistoryModel> historyModels = historyModelFac.findByHistoryModel(className, idName, idValue, getStartDate(), getEndDate());
    	List<Map<String,Object>> listValues = new ArrayList<Map<String,Object>>();
    	int version = 1;
    	for(HistoryModel historyModel : historyModels){
    		Map<String, Object> map = new HashMap<String, Object>();
    		map.put("historyId", historyModel.getId());
    		map.put("version", version);
			Object obj = ByteToObject.conversion(historyModel.getModelObject());
			if (obj == null){
				map.put("maxPage",0);
			}else{
				Article article = (Article)obj;
				map.put("maxPage",article.getContents().size());
			}
    		map.put("historyTime", historyModel.getCreateDate());
    		listValues.add(map);
    		
    		version++;
    	}
		DataGrid data = new DataGrid(listValues.size(), listValues);
		Struts2Util.renderJson(JSONUtil.toJSON(data,DATE_FORMAT));
		return NONE;
	}
}
