/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.plugin.interaction.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author wangwei
 */
@Entity
@Table(name = "plugin_interaction_backratio")
@SequenceGenerator(name = "seq_plugin_interaction_backratio", sequenceName = "seq_plugin_interaction_backratio_id", allocationSize = 1)
public class InteractionRatio implements Serializable {

	private static final long serialVersionUID = -823177356332483391L;

	@Id
	@GeneratedValue(generator = "seq_plugin_interaction_backratio", strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column(name = "ratio")
    private Integer ratio;
    @Column(name="no_ratio")
    private Integer noRatio;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNoRatio() {
        return noRatio;
    }

    public void setNoRatio(Integer noRatio) {
        this.noRatio = noRatio;
    }

    public Integer getRatio() {
        return ratio;
    }

    public void setRatio(Integer ratio) {
        this.ratio = ratio;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final InteractionRatio other = (InteractionRatio) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
}
