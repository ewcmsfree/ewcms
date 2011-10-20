/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.security.manage.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 用户组
 *
 * <ul>
 * <li>name:用户组名称
 * <li>remark:备注
 * <li>authorities:所属权限
 * <li>users:所属用户
 * </ul>
 *
 * @author wangwei
 */
@Entity
@Table(name = "auth_group")
public class Group implements Serializable {

    @Id
    @Column(name = "name", length = 50, nullable=false)
    private String name;

    @Column(name = "remark", length = 200)
    private String remark;
    
    @ManyToMany(cascade = {CascadeType.REFRESH}, targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinTable(name = "auth_group_members", joinColumns = @JoinColumn(name = "group_name"), inverseJoinColumns = @JoinColumn(name = "username"))
    @OrderBy("username asc")
    private Set<User> users;

    @ManyToMany(cascade = {CascadeType.REFRESH}, targetEntity = Authority.class, fetch = FetchType.LAZY)
    @JoinTable(name = "auth_group_authorities", joinColumns = @JoinColumn(name = "group_name"), inverseJoinColumns = @JoinColumn(name = "authority_name"))
    @OrderBy("name asc")
    private Set<Authority> authorities;

    public Group(){}
    
    public Group(String name){
        this(name,null);
    }
    
    public Group(String name,String remark){
        this.name = name;
        this.remark = remark;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String description) {
        this.remark = description;
    }

    @JsonIgnore
    public Set<User> getUsers() {
        return users = (users == null ? new HashSet<User>() : users);
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @JsonIgnore
    public Set<Authority> getAuthorities() {
        return authorities = (authorities == null ? new HashSet<Authority>() : authorities);
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        Group other = (Group) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Group{");
        builder.append("name:").append(name).append(";");
        builder.append("remark:").append(remark).append(";");

        if (authorities != null && !authorities.isEmpty()) {
            builder.append("authorities:[");
            for (Authority auth : authorities) {
                builder.append(auth.getName()).append(";");
            }
            builder.append("];");
            builder.append("users:[");
        }

        if (users != null && !users.isEmpty()) {
            for (User user : users) {
                builder.append(user.getUsername()).append(";");
            }
            builder.append("];");
        }
        builder.append("}");

        return builder.toString();
    }
}
