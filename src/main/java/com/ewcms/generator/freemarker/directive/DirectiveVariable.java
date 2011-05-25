/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.generator.freemarker.directive;

/**
 *
 * @author wangwei
 */
public enum DirectiveVariable {

    CurrentSite("ewcms_current_site"),
    CurrentChannel("ewcms_current_channel"),
    PageParam("pageParam"),
    Article("article"),
    ArtilceRelation("article_relation"),
    Channel("channel"),
    Page("page"),
    SkipPage("skip_page"),
    ListIndex("list_index"),
    Debug("ewcms_debug"),
    JQuery("javascript_jquery");
    
    private String name;

    private DirectiveVariable(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
