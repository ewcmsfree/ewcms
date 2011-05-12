/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ewcms.plugin.member.model;
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
@Table(name = "plugin_site_member_article")
@SequenceGenerator(name = "seq_plugin_member_artilce", sequenceName = "seq_plugin_member_artilce_id", allocationSize = 1)
public class MemberArticle implements Serializable {

    @Id
    @GeneratedValue(generator = "seq_plugin_member_artilce", strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column
    private String username;
    @Column
    private Integer articleRmcId;

    public Integer getArticleRmcId() {
        return articleRmcId;
    }

    public void setArticleRmcId(Integer articleRmcId) {
        this.articleRmcId = articleRmcId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MemberArticle other = (MemberArticle) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
}
