package com.ewcms.content.document.web;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.common.query.Resultable;
import com.ewcms.common.query.jpa.QueryFactory;
import com.ewcms.content.document.DocumentFacable;
import com.ewcms.content.document.model.OperateTrack;
import com.ewcms.web.QueryBaseAction;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;
import com.ewcms.web.vo.DataGrid;

public class OperateTrackQueryAction extends QueryBaseAction {

	private static final long serialVersionUID = 2141489086820205940L;

	private SimpleDateFormat modDataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	private DocumentFacable documentFac;
	
	private Long articleMainId;
	
	public Long getArticleMainId() {
		return articleMainId;
	}

	public void setArticleMainId(Long articleMainId) {
		this.articleMainId = articleMainId;
	}

	@Override
	protected Resultable queryResult(QueryFactory queryFactory,	String cacheKey, int rows, int page, Order order) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Resultable querySelectionsResult(QueryFactory queryFactory,
			int rows, int page, String[] selections, Order order) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String query() {
		List<OperateTrack> list = documentFac.findOperateTrackByArticleMainId(getArticleMainId());
		DataGrid data = new DataGrid(list.size(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data, modDataFormat));
		return NONE;
	}
}
