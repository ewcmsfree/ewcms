/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.web.action.plugin.member;

import com.ewcms.comm.jpa.query.EntityPageQueryable;
import com.ewcms.comm.jpa.query.PageQueryable;
import com.ewcms.comm.jpa.query.QueryFactory;
import com.ewcms.plugin.member.model.Member;
import com.ewcms.web.action.QueryBaseAction;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author wangwei
 */
@Controller
public class MemberQueryAction extends QueryBaseAction {

    @Autowired
    private QueryFactory queryFactory;
    
    private Boolean cppcc = Boolean.TRUE;

    @Override
    protected PageQueryable constructQuery(Order order) {
        EntityPageQueryable query = queryFactory.createEntityPageQuery(Member.class);
        String username = this.getParameterValue(String.class, "username");
        query.eq("cppcc", cppcc);
        if (isNotEmpty(username)) {
            query.likeAnywhere("username", username);
        }
        String name = this.getParameterValue(String.class, "name");
        if (isNotEmpty(name)) {
            query.likeAnywhere("name", name);
        }
        query.orderAsc("username");
        return query;
    }

    @Override
    protected PageQueryable constructNewQuery(Order order) {
        EntityPageQueryable query = queryFactory.createEntityPageQuery(Member.class);
        List<String> usernames = this.getNewIdAll(String.class);
        query.in("username", usernames);
        query.orderAsc("username");
        return query;
    }

    public void setCppcc(Boolean cppcc) {
        this.cppcc = cppcc;
    }
}
