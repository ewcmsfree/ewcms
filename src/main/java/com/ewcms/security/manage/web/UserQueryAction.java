package com.ewcms.security.manage.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ewcms.common.jpa.query.EntityPageQueryable;
import com.ewcms.common.jpa.query.PageQueryable;
import com.ewcms.common.jpa.query.QueryFactory;
import com.ewcms.security.manage.model.User;
import com.ewcms.web.QueryBaseAction;

@Controller("security.user.query")
public class UserQueryAction extends QueryBaseAction{

    @Autowired
    private QueryFactory queryFactory;
    
    @Override
    protected PageQueryable constructQuery(Order order) {
        
        EntityPageQueryable query = queryFactory.createEntityPageQuery(User.class);
        String username =  getParameterValue(String.class,"username");
        if(this.isNotEmpty(username)) query.likeAnywhere("username", username);
        simpleEntityOrder(query, order);
        
        return query;
    }

    @Override
    protected PageQueryable constructNewQuery(Order order) {
        EntityPageQueryable query = queryFactory.createEntityPageQuery(User.class);
        List<String> usernames = getIds(String.class);
        query.orderDesc("createTime");
        query.in("username", usernames);
        return query;
    }
    
    @Override
    protected List constructRows(List rows){
        for(Object row : rows){
            User user = (User)row;
            user.setPassword(null);
        }
        return rows;
    }
    
    public void setQueryFactory(QueryFactory queryFactory){
        this.queryFactory = queryFactory;
    }

}