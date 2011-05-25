/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.security.manage.web;

import org.springframework.stereotype.Controller;

import com.ewcms.common.query.Resultable;
import com.ewcms.common.query.jpa.QueryFactory;
import com.ewcms.web.QueryBaseAction;

@Controller
public class GroupQueryAction extends QueryBaseAction{

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
    
//    private String name;
//
//    @Autowired
//    private QueryFactory queryFactory;
//    
//    @Override
//    protected PageQueryable constructQuery(Order order) {
//        
//        EntityPageQueryable query = queryFactory.createEntityPageQuery(Group.class);
//        String name =  getParameterValue(String.class,"name");
//        if(this.isNotEmpty(name)) query.likeAnywhere("name", name);
//        simpleEntityOrder(query, order);
//        
//        return query;
//    }
//
//    @Override
//    protected PageQueryable constructNewQuery(Order order) {
//        // no using
//        return null;
//    }
//    
//    /**
//     * TreeGrid数据查询
//     * 
//     * @return treegrid json
//     */
//    public void queryTreeGrid(){
//        
//        EntityQueryable query = queryFactory.createEntityQuery(Group.class);
//        List list = query.getResultList();
//        List<GroupGridNode> nodes = new ArrayList<GroupGridNode>();
//        for(int i = 0 ; i < list.size() ; i++){
//            GroupGridNode node = new GroupGridNode();
//            Group group =(Group)list.get(i);
//            node.setId(group.getName());
//            node.setName(group.getName());
//            node.setRemark(group.getRemark());
//            node.setLevel("group");
//            node.setChildren(createSubNode(group));
//            nodes.add(node);
//        }
//        Struts2Util.renderJson(JSONUtil.toJSON(nodes));
//    }
//    
//    private List<GroupGridNode> createSubNode(final Group group){
//        List<GroupGridNode> nodes = new ArrayList<GroupGridNode>();
//        GroupGridNode usersNode = new GroupGridNode();
//        usersNode.setId(group.getName()+"_users");
//        usersNode.setName("用户");
//        usersNode.setLevel("users");
//        nodes.add(usersNode);
//        GroupGridNode authsNode = new GroupGridNode();
//        authsNode.setId(group.getName()+"_authorities");
//        authsNode.setName("权限");
//        authsNode.setLevel("authorities");
//        nodes.add(authsNode);
//        return nodes;
//    }
//
//    private Group getGroup(String name){
//        EntityQueryable query = queryFactory.createEntityQuery(Group.class);
//        query.eq("name", name);
//        List list = query.getResultList();
//        return (Group)(list.isEmpty()? null : list.get(0));
//    }
//    
//    public void queryUsers(){
//        Group group =getGroup(name);
//        if(group == null){
//            Struts2Util.renderJson("{}");
//        }
//        List<GroupGridNode> nodes = new ArrayList<GroupGridNode>();
//        for(User  user : group.getUsers() ){
//            GroupGridNode node = new GroupGridNode.Builder().build(group.getName(), user);
//            nodes.add(node);
//        }
//        Struts2Util.renderJson(JSONUtil.toJSON(nodes));
//    }
//    
//    public void queryAuthorities(){
//        Group group =getGroup(name);
//        if(group == null){
//            Struts2Util.renderJson("{}");
//        }
//        List<GroupGridNode> nodes = new ArrayList<GroupGridNode>();
//        for(Authority  auth : group.getAuthorities()){
//            GroupGridNode node = new GroupGridNode.Builder().build(group.getName(), auth);
//            nodes.add(node);
//        }
//        Struts2Util.renderJson(JSONUtil.toJSON(nodes));
//    }
//    
//    public void setQueryFactory(QueryFactory queryFactory){
//        this.queryFactory = queryFactory;
//    }
//    
//    public void setName(String name){
//        this.name = name;
//    }
}
