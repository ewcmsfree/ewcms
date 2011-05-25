package com.ewcms.security.manage.web;

import static com.ewcms.common.lang.EmptyUtil.isStringNotEmpty;

import java.util.List;

import org.springframework.stereotype.Controller;

import com.ewcms.common.query.Resultable;
import com.ewcms.common.query.jpa.EntityQueryable;
import com.ewcms.common.query.jpa.QueryFactory;
import com.ewcms.security.manage.model.User;
import com.ewcms.web.QueryBaseAction;

@Controller
public class UserQueryAction extends QueryBaseAction {

    @Override
    protected Resultable queryResult(QueryFactory queryFactory,
            String cacheKey, int rows, int page, Order order) {

        EntityQueryable query = 
            queryFactory.createEntityQuery(User.class)
            .setPage(page)
            .setRow(rows);

        String username = getParameterValue(String.class, "username");
        if (isStringNotEmpty(username))
            query.likeAnywhere("username", username);
        entityOrder(query, order);

        return query.queryCacheResult(cacheKey);
    }

    @Override
    protected Resultable querySelectionsResult(QueryFactory queryFactory,
            int rows, int page, String[] selections, Order order) {
        EntityQueryable query = 
            queryFactory.createEntityQuery(User.class)
            .setPage(page)
            .setRow(rows);
        
        List<String> usernames = getIds(String.class);
        query.in("username", usernames);
        
        return query.queryResult();
    }
}