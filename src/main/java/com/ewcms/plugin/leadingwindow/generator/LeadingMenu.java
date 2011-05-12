/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ewcms.plugin.leadingwindow.generator;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wangwei
 */
public class LeadingMenu {

    private int id;
    private String title;
    private boolean focus = false;
    private String url = "";
    private List<LeadingMenu> children = new ArrayList<LeadingMenu>();

    public LeadingMenu(int id,String title){
        this.id = id;
        this.title = title;
    }

    public LeadingMenu(int id,String title,String url){
        this(id,title);
        this.url = url;
    }

    public List<LeadingMenu> getChildren() {
        return children;
    }

    public void setChildren(List<LeadingMenu> children) {
        this.children = children;
    }

    public LeadingMenu addChild(LeadingMenu child){
        this.children.add(child);
        return child;
    }

    public boolean isFocus() {
        return focus;
    }

    public void setFocus(boolean current) {
        this.focus = current;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LeadingMenu other = (LeadingMenu) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + this.id;
        return hash;
    }
}
