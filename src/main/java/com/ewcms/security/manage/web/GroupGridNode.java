package com.ewcms.security.manage.web;

import java.util.List;

import com.ewcms.security.manage.model.Authority;
import com.ewcms.security.manage.model.User;


public class GroupGridNode {
    
    private String id;
    private String name;
    private String remark;
    private String state = "closed";
    private String iconCls = "icon-folder";
    private String level;
    
    public static class Builder {
        public GroupGridNode build(String groupName,User user){
            GroupGridNode node = new GroupGridNode();
            String id = groupName +"_users_"+user.getUsername();
            node.setId(id);
            node.setName(user.getUsername());
            if(user.getUserInfo() != null){
                node.setRemark(user.getUserInfo().getName());
            }
            node.setState("open");
            node.setLevel("userdetail");
            return node;
        }
        
        public GroupGridNode build(String groupName,Authority authority){
            GroupGridNode node = new GroupGridNode();
            String id = groupName +"_authorities_"+authority.getName();
            node.setId(id);
            node.setName(authority.getName());
            node.setRemark(authority.getRemark());
            node.setState("open");
            node.setLevel("authdetail");
            return node;
        }
    }
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    private List<GroupGridNode> children;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getIconCls() {
        return iconCls;
    }
    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }
    public List<GroupGridNode> getChildren() {
        return children;
    }
    public void setChildren(List<GroupGridNode> children) {
        this.children = children;
    }
    public String getLevel() {
        return level;
    }
    public void setLevel(String level) {
        this.level = level;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GroupGridNode other = (GroupGridNode) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
