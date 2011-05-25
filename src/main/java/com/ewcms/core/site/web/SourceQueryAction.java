/**
 * 
 */
package com.ewcms.core.site.web;

import static com.ewcms.common.lang.EmptyUtil.isStringNotEmpty;
import static com.ewcms.common.lang.EmptyUtil.isNotNull;
import com.ewcms.common.query.Resultable;
import com.ewcms.common.query.jpa.EntityQueryable;
import com.ewcms.core.site.model.TemplateSource;
import com.ewcms.web.QueryBaseAction;

/**
 * @author 周冬初
 * 
 */
public class SourceQueryAction extends QueryBaseAction {

	@Override
	protected Resultable queryResult(
			com.ewcms.common.query.jpa.QueryFactory queryFactory,
			String cacheKey, int rows, int page, Order order) {
		EntityQueryable query = queryFactory
				.createEntityQuery(TemplateSource.class).setPage(page).setRow(rows);
		Integer id = getParameterValue(Integer.class, "id");
		if (isNotNull(id))
			query.eq("id", id);
		String name = getParameterValue(String.class, "name");
		if (isStringNotEmpty(name))
			query.likeAnywhere("name", name);
		Integer channelId = getParameterValue(Integer.class, "channelId", "");
		query.eq("channelId", channelId);
		entityOrder(query, order);
		return query.queryCacheResult(cacheKey);
	}

	@Override
	protected Resultable querySelectionsResult(
			com.ewcms.common.query.jpa.QueryFactory queryFactory, int rows,
			int page, String[] selections, Order order) {
		EntityQueryable query = queryFactory
				.createEntityQuery(TemplateSource.class).setPage(page)
				.setRow(rows);
		query.in("id", getIds(Integer.class));
		query.orderDesc("id");
		return query.queryResult();
	}
}
