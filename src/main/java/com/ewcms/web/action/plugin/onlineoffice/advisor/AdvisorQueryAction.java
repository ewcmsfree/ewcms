/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.web.action.plugin.onlineoffice.advisor;

import com.ewcms.comm.jpa.query.EntityPageQueryable;
import com.ewcms.comm.jpa.query.PageQueryable;
import com.ewcms.comm.jpa.query.QueryFactory;
import com.ewcms.plugin.onlineoffice.model.Advisor;
import com.ewcms.web.action.QueryBaseAction;
import com.ewcms.util.EmptyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


/**
 *
 * @author wangwei
 */
@Controller
public class AdvisorQueryAction extends QueryBaseAction {

    @Autowired
    private QueryFactory queryFactory;
    private int replay;
    private String title;
    private String content;

    public void setReplay(int replay) {
        this.replay = replay;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    protected PageQueryable constructQuery(Order order) {

        EntityPageQueryable query = queryFactory.createEntityPageQuery(Advisor.class);

        if (replay != 0) {
            if (replay == 1) {
                query.eq("state", 1);
            } else {
                query.eq("state", 0);
            }
        }

        if (EmptyUtil.isStringNotEmpty(title)) {
            query.likeAnywhere("title", title);
        }

        if (EmptyUtil.isStringNotEmpty(content)) {
            query.likeAnywhere("content", content);
        }

        query.orderDesc("date");

        return query;
    }

    @Override
    protected PageQueryable constructNewQuery(Order order) {
        //TODO 无操作
        return null;
    }
}
