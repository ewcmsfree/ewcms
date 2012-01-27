/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.externalds.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Jndi数据源
 * 
 * <ul>
 * <li>jndiName:Jndi名</li>
 * </ul>
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "plugin_ds_jndi")
@PrimaryKeyJoinColumn(name = "ds_id")
public class JndiDS extends BaseDS {

    private static final long serialVersionUID = 7204174514804533685L;
    
    @Column(name = "jndiname", nullable = false)
    private String jndiName;

    public String getJndiName() {
        return jndiName;
    }

    public void setJndiName(String jndiName) {
        this.jndiName = jndiName;
    }
}
