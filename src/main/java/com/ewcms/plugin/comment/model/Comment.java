/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ewcms.plugin.comment.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author 吴智俊
 */
@Entity
@Table(name = "component_comment")
public class Comment implements Serializable {

	private static final long serialVersionUID = 3534227027073857782L;
	
	public enum Sex {
		MALE("男"), FEMALE("女");

		private String description;

		private Sex(String description) {
			this.description = description;
		}

		public String getDescription() {
			return description;
		}
	}

	public enum Education {
		DOCTOR("博士"), MASTER("硕士"), UNDERGRADUATE("本科"), COLLEGE("大专"), SECONDARY("中专"), MIDDLE("高中"), JUNIOR("初中"), PRIMARY("小学"), ILLITERACY("文盲");

		private String description;

		private Education(String description) {
			this.description = description;
		}

		public String getDescription() {
			return description;
		}
	}
	
	@Id
	private Long id;
	@Column(name = "article_id", nullable = false)
    private Integer articleId;
	@Column(name = "username", nullable = false)
    private String username;
	@Column(name = "ip", nullable = false)
    private String ip;
    @Column(name="content", columnDefinition="text")
    private String content;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="date", nullable = false)
    private Date date;
    @Column(name = "checked", nullable = false)
    private Boolean checked;
    @Column(name = "sex")
    @Enumerated(EnumType.STRING)
	private Sex sex = Sex.MALE;
    @Column(name = "education")
    @Enumerated(EnumType.STRING)
	private Education education;
	@Column(name = "age")
	private Integer age = 0;
	@Column(name = "profession")
	private String profession = "无";
//    @OneToMany
//    private List<Reply> replies = new ArrayList<Reply>();

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date == null ? new Timestamp(System.currentTimeMillis()) : date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

//	@JsonIgnore
//	public List<Reply> getReplies() {
//        return replies ;
//    }
//
//    public void setReplies(List<Reply> replies) {
//        this.replies = replies;
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}
	
	public String getSexDescription(){
		if (sex == null){
			return Sex.MALE.getDescription();
		}else{
			return sex.getDescription();
		}
	}

	public Education getEducation() {
		return education;
	}

	public void setEducation(Education education) {
		this.education = education;
	}
	
	public String getEducationDescription(){
		if (education == null){
			return Education.ILLITERACY.getDescription();
		}else{
			return education.getDescription();
		}
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	@Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Comment other = (Comment) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
}
