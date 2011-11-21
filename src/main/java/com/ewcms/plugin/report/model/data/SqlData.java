/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.report.model.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * SQL数据
 * 
 * <ul>
 * <li>language:语言</li>
 * <li>sqlQuery:查询表达式</li>
 * </ul>
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "repo_data_sql")
@PrimaryKeyJoinColumn(name = "data_id")
public class SqlData extends Data {

    private static final long serialVersionUID = -3843273844436408123L;
    @Column(name = "language")
    private String language;
    @Column(name = "sqlquery", columnDefinition = "text", nullable = false)
    private String sqlQuery;

    public SqlData(){
    	language = "sql";
    }
    
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSqlQuery() {
        return sqlQuery;
    }

    public void setSqlQuery(String sqlQuery) {
        this.sqlQuery = sqlQuery;
    }
}
