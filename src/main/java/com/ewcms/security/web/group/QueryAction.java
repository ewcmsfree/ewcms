package com.ewcms.security.web.group;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ewcms.common.jpa.query.EntityPageQueryable;
import com.ewcms.common.jpa.query.EntityQueryable;
import com.ewcms.common.jpa.query.PageQueryable;
import com.ewcms.common.jpa.query.QueryFactory;
import com.ewcms.security.manage.model.Authority;
import com.ewcms.security.manage.model.Group;
import com.ewcms.security.manage.model.User;
import com.ewcms.util.JSONUtil;
import com.ewcms.util.Struts2Util;
import com.ewcms.web.action.QueryBaseAction;

@Controller("security.group.query")
public class QueryAction extends QueryBaseAction{
    
    private String name;

    @Autowired
    private QueryFactory queryFactory;
    
    @Override
    protected PageQueryable constructQuery(Order order) {
        
        EntityPageQueryable query = queryFactory.createEntityPageQuery(Group.class);
        String name =  getParameterValue(String.class,"name");
        if(this.isNotEmpty(name)) query.likeAnywhere("name", name);
        simpleEntityOrder(query, order);
        
        return query;
    }

    @Override
    protected PageQueryable constructNewQuery(Order order) {
        // no using
        return null;
    }
    
    /**
     * TreeGrid数据查询
     * 
     * @return treegrid json
     */
    public void queryTreeGrid(){
        
        EntityQueryable query = queryFactory.createEntityQuery(Group.class);
        List list = query.getResultList();
        List<GroupGridNode> nodes = new ArrayList<GroupGridNode>();
        for(int i = 0 ; i < list.size() ; i++){
            GroupGridNode node = new GroupGridNode();
            Group group =(Group)list.get(i);
            node.setId(group.getName());
            node.setName(group.getName());
            node.setRemark(group.getRemark());
            node.setLevel("group");
            node.setChildren(createSubNode(group));
            nodes.add(node);
        }
        Struts2Util.renderJson(JSONUtil.toJSON(nodes));
    }
    
    private List<GroupGridNode> createSubNode(final Group group){
        List<GroupGridNode> nodes = new ArrayList<GroupGridNode>();
        GroupGridNode usersNode = new GroupGridNode();
        usersNode.setId(group.getName()+"_users");
        usersNode.setName("用户");
        usersNode.setLevel("users");
        nodes.add(usersNode);
        GroupGridNode authsNode = new GroupGridNode();
        authsNode.setId(group.getName()+"_authorities");
        authsNode.setName("权限");
        authsNode.setLevel("authorities");
        nodes.add(authsNode);
        return nodes;
    }

    private Group getGroup(String name){
        EntityQueryable query = queryFactory.createEntityQuery(Group.class);
        query.eq("name", name);
        List list = query.getResultList();
        return (Group)(list.isEmpty()? null : list.get(0));
    }
    
    public void queryUsers(){
        Group group =getGroup(name);
        if(group == null){
            Struts2Util.renderJson("{}");
        }
        List<GroupGridNode> nodes = new ArrayList<GroupGridNode>();
        for(User  user : group.getUsers() ){
            GroupGridNode node = new GroupGridNode.Builder().build(group.getName(), user);
            nodes.add(node);
        }
        Struts2Util.renderJson(JSONUtil.toJSON(nodes));
    }
    
    public void queryAuthorities(){
        Group group =getGroup(name);
        if(group == null){
            Struts2Util.renderJson("{}");
        }
        List<GroupGridNode> nodes = new ArrayList<GroupGridNode>();
        for(Authority  auth : group.getAuthorities()){
            GroupGridNode node = new GroupGridNode.Builder().build(group.getName(), auth);
            nodes.add(node);
        }
        Struts2Util.renderJson(JSONUtil.toJSON(nodes));
    }
    
    public void setQueryFactory(QueryFactory queryFactory){
        this.queryFactory = queryFactory;
    }
    
    public void setName(String name){
        this.name = name;
    }
}
