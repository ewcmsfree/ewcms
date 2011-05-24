package com.ewcms.security.web.authority;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ewcms.comm.jpa.query.EntityPageQueryable;
import com.ewcms.comm.jpa.query.PageQueryable;
import com.ewcms.comm.jpa.query.QueryFactory;
import com.ewcms.security.manage.model.Authority;
import com.ewcms.web.action.QueryBaseAction;

@Controller("security.authority.query")
public class QueryAction extends QueryBaseAction{

    @Autowired
    private QueryFactory queryFactory;
    
    @Override
    protected PageQueryable constructQuery(Order order) {
        
        EntityPageQueryable query = queryFactory.createEntityPageQuery(Authority.class);
        String name =  getParameterValue(String.class,"name");
        if(this.isNotEmpty(name)) query.likeAnywhere("name", name);
        simpleEntityOrder(query, order);
        
        return query;
    }

    @Override
    protected PageQueryable constructNewQuery(Order order) {
        return null;
    }
    
    public void setQueryFactory(QueryFactory queryFactory){
        this.queryFactory = queryFactory;
    }

}
