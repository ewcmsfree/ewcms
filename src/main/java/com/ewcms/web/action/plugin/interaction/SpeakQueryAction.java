/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.web.action.plugin.interaction;

import com.ewcms.comm.jpa.query.EntityPageQueryable;
import com.ewcms.comm.jpa.query.PageQueryable;
import com.ewcms.comm.jpa.query.QueryFactory;
import com.ewcms.plugin.interaction.model.Speak;
import com.ewcms.util.EmptyUtil;
import com.ewcms.web.action.QueryBaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author wangwei
 */
@Controller
public class SpeakQueryAction extends QueryBaseAction {

    private int checked;
    private String content;

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    @Autowired
    private QueryFactory queryFactory;

    @Override
    protected PageQueryable constructQuery(Order order) {
        EntityPageQueryable query = queryFactory.createEntityPageQuery(Speak.class);
        if (checked != 0) {
            if (checked == 1) {
                query.eq("checked", true);
            } else {
                query.eq("checked", false);
            }
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
