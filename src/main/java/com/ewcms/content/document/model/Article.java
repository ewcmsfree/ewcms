/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 文章信息
 * 
 * <ul>
 * <li>id:编号</li>
 * <li>title:标题</li>
 * <li>shortTitle:短标题</li>
 * <li>subTitle:副标题</li>
 * <li>author:作者</li>
 * <li>origin:来源</li>
 * <li>keyword:关键字</li>
 * <li>tag:标签</li>
 * <li>summary:摘要</li>
 * <li>contents:内容集合对象</li>
 * <li>image:文章图片</li>
 * <li>topFlag:新闻置顶</li>
 * <li>commentFlag:允许评论</li>
 * <li>type:文章类型</li>
 * <li>owner:创建者</li>
 * <li>audit:审核人</li>
 * <li>auditReal:审核人实名</li>
 * <li>published:发布时间</li>
 * <li>modified:修改时间</li>
 * <li>status:状态</li>
 * <li>url:链接地址</li>
 * <li>deleteFlag:删除标志</li>
 * <li>relatedArticles:相关文章</li>
 * <li>createTime:创建时间</li>
 * <li>categories:文章分类属性集合</li>
 * <li>contentTotal:内容总页数<li>
 * <li>inside:使用内部标题</li>
 * </ul>
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "doc_article")
@SequenceGenerator(name = "seq_doc_article", sequenceName = "seq_doc_article_id", allocationSize = 1)
public class Article implements Serializable {

	private static final long serialVersionUID = -5809802652492615658L;

	@Id
    @GeneratedValue(generator = "seq_doc_article",strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Long id;
	@Column(name = "title", nullable = false, length = 100)
	private String title;
	@Column(name = "short_title", length = 50)
	private String shortTitle;
	@Column(name = "sub_title", length = 100)
	private String subTitle;
	@Column(name = "author")
	private String author;
	@Column(name = "origin")
	private String origin;
	@Column(name = "key_word", columnDefinition = "text")
	private String keyword;
	@Column(name = "tag")
	private String tag;
	@Column(name = "summary", columnDefinition = "text")
	private String summary;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Content.class, orphanRemoval = true)
	@JoinColumn(name = "article_id")
	@OrderBy(value = "page asc")
	private List<Content> contents;
	@Column(name = "image")
	private String image;
	@Column(name = "top_flag")
	private Boolean topFlag;
	@Column(name = "comment_flag")
	private Boolean commentFlag;
	@Column(name = "type", nullable = false)
	@Enumerated(EnumType.STRING)
	private ArticleType type;
	@Column(name = "owner")
	private String owner;
	@Column(name = "audit")
	private String audit;
	@Column(name = "audit_real")
	private String auditReal;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "published")
	private Date published;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified", nullable = false)
	private Date modified;
	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private ArticleStatus status;
	@Column(name = "url", columnDefinition = "text")
	private String url;
	@Column(name = "delete_flag")
	private Boolean deleteFlag;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Relation.class)
	@JoinColumn(name = "article_id")
	@OrderBy(value = "sort")
	private List<Relation> relations;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createtime", nullable = false)
	private Date createTime;
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY, targetEntity = ArticleCategory.class)
	@JoinTable(name = "doc_article_articlecategory", joinColumns = @JoinColumn(name = "article_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "articlecategory_id", referencedColumnName = "id"))
	@OrderBy(value = "id")
	private List<ArticleCategory> categories;
	@Column(name = "total")
	private Integer contentTotal;
	@Column(name = "inside")
	private Boolean inside;
	
	public Article() {
		topFlag = false;
		commentFlag = false;
		type = ArticleType.GENERAL;
		status = ArticleStatus.DRAFT;
		createTime = new Date(Calendar.getInstance().getTime().getTime());
		deleteFlag = false;
		inside = false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getShortTitle() {
		return shortTitle;
	}

	public void setShortTitle(String shortTitle) {
		this.shortTitle = shortTitle;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@JsonIgnore
	public List<Content> getContents() {
		return contents;
	}

	public void setContents(List<Content> contents) {
		this.contents = contents;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Boolean getTopFlag() {
		return topFlag;
	}

	public void setTopFlag(Boolean topFlag) {
		this.topFlag = topFlag;
	}

	public Boolean getCommentFlag() {
		return commentFlag;
	}

	public void setCommentFlag(Boolean commentFlag) {
		this.commentFlag = commentFlag;
	}
	
	public ArticleType getType() {
		return type;
	}
	
	public String getTypeDescription(){
		if (type != null){
			return type.getDescription();
		}else{
			return ArticleType.GENERAL.getDescription();
		}
	}

	public void setType(ArticleType type) {
		this.type = type;
	}

	public String getAudit() {
		return audit;
	}

	public void setAudit(String audit) {
		this.audit = audit;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getAuditReal() {
		return auditReal;
	}

	public void setAuditReal(String auditReal) {
		this.auditReal = auditReal;
	}

	public Date getPublished() {
		return published;
	}

	public void setPublished(Date published) {
		this.published = published;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public String getStatusDescription(){
		return status.getDescription();
	}
	
	public ArticleStatus getStatus() {
		return status;
	}

	public void setStatus(ArticleStatus status) {
		this.status = status;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public Boolean getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@JsonIgnore
	public List<Relation> getRelations() {
		return relations;
	}

	public void setRelations(List<Relation> relations) {
		this.relations = relations;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public List<ArticleCategory> getCategories() {
		return categories;
	}

	public void setCategories(List<ArticleCategory> categories) {
		this.categories = categories;
	}

	public Integer getContentTotal() {
		return contentTotal;
	}

	public void setContentTotal(Integer contentTotal) {
		this.contentTotal = contentTotal;
	}

	public Boolean getInside() {
		return inside;
	}

	public void setInside(Boolean inside) {
		this.inside = inside;
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
		Article other = (Article) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
