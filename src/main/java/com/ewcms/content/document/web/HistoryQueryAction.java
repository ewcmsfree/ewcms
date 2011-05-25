/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.content.document.web;

import org.springframework.stereotype.Controller;

import com.ewcms.common.query.Resultable;
import com.ewcms.common.query.jpa.QueryFactory;
import com.ewcms.web.QueryBaseAction;

/**
 * 
 * @author 吴智俊
 */
@Controller("contenthistory")
public class HistoryQueryAction extends QueryBaseAction {

    @Override
    protected Resultable queryResult(QueryFactory queryFactory,
            String cacheKey, int rows, int page, Order order) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Resultable querySelectionsResult(QueryFactory queryFactory,
            int rows, int page, String[] selections, Order order) {
        // TODO Auto-generated method stub
        return null;
    }

//	private static final long serialVersionUID = 6797696723188141927L;
//	
//	private DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
//
//	@Autowired
//	private HistoryModelFacable historyModelFac;
//
//	private Integer articleId;
//	
//	private Date startDate;
//	
//	private Date endDate;
//
//	public Integer getArticleId() {
//		return articleId;
//	}
//
//	public void setArticleId(Integer articleId) {
//		this.articleId = articleId;
//	}
//
//	public Date getStartDate() {
//		return startDate;
//	}
//
//	public void setStartDate(Date startDate) {
//		this.startDate = startDate;
//	}
//
//	public Date getEndDate() {
//		return endDate;
//	}
//
//	public void setEndDate(Date endDate) {
//		this.endDate = endDate;
//	}
//
//	@SuppressWarnings("rawtypes")
//	@Override
//	protected PageQueryable constructNewQuery(Order order) {
//		return null;
//	}
//
//	@SuppressWarnings("rawtypes")
//	@Override
//	protected PageQueryable constructQuery(Order order) {
//		return null;
//	}
//
//	@Override
//	public String query() {
//		String className = Article.class.getPackage().getName() + "." + Article.class.getSimpleName();
//		String idName = "id";
//		String idValue = getArticleId().toString();
//		
//		List<HistoryModel> historyModels = historyModelFac.findByHistoryModel(className, idName, idValue, getStartDate(), getEndDate());
//    	List<Map<String,Object>> listValues = new ArrayList<Map<String,Object>>();
//    	int version = 1;
//    	for(HistoryModel historyModel : historyModels){
//    		Map<String, Object> map = new HashMap<String, Object>();
//    		map.put("historyId", historyModel.getId());
//    		map.put("version", version);
//			Object obj = ByteToObject.conversion(historyModel.getModelObject());
//			if (obj == null){
//				map.put("maxPage",0);
//			}else{
//				Article article = (Article)obj;
//				map.put("maxPage",article.getContents().size());
//			}
//    		map.put("historyTime", historyModel.getCreateDate());
//    		listValues.add(map);
//    		
//    		version++;
//    	}
//		DataGrid data = new DataGrid(listValues.size(), listValues);
//		Struts2Util.renderJson(JSONUtil.toJSON(data,DATE_FORMAT));
//		return NONE;
//	}
}
