package com.ewcms.util;

import java.util.List;

public class TreeGridNode {

    private Integer id;
    private Object data;
    private String state = "closed";
    private String iconCls;
    private List<TreeGridNode> children;
    
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
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
    public List<TreeGridNode> getChildren() {
        return children;
    }
    public void setChildren(List<TreeGridNode> children) {
        this.children = children;
    }   
}
