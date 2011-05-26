/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.model;

import java.io.Serializable;
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
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 发布文章
 * 
 * <ul>
 * <li>id:编号</li>
 * <li>title:标题</li>
 * <li>titleStyle:标题样式</li>
 * <li>shortTitle:短标题</li>
 * <li>shortTitleStyle:短标题样式</li>
 * <li>subTitle:副标题</li>
 * <li>subTitlStyle:副标题样式</li>
 * <li>author:作者</li>
 * <li>owner:创建者</li>
 * <li>origin:来源</li>
 * <li>keyword:关键字</li>
 * <li>tag:标签</li>
 * <li>summary:摘要</li>
 * <li>contents:内容集合对象</li>
 * <li>image:文章图片</li>
 * <li>contentHistories:历史内容集合对象</li>
 * <li>topFlag:新闻置顶</li>
 * <li>commentFlag:允许评论</li>
 * <li>imageFlag:图片</li>
 * <li>videoFlag:视频</li>
 * <li>annexFlag:附件</li>
 * <li>hotFlag:热点</li>
 * <li>recommendFlag:推荐</li>
 * <li>copyoutFlag:复制源</li>
 * <li>copyFlag:复制</li>
 * <li>type:文章类型</li>
 * <li>linkAddr:链接地址</li>
 * <li>eauthor:审核人</li>
 * <li>eauthorReal:审核人实名</li>
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
	private Integer id;
	@Column(name = "title", nullable = false, length = 100)
	private String title;
	@Column(name = "title_style")
	private String titleStyle;
	@Column(name = "short_title", length = 50)
	private String shortTitle;
	@Column(name = "short_title_style")
	private String shortTitleStyle;
	@Column(name = "sub_title", length = 100)
	private String subTitle;
	@Column(name = "sub_title_style")
	private String subTitleStyle;
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
	@Column(name = "image_flag")
	private Boolean imageFlag;
	@Column(name = "video_flag")
	private Boolean videoFlag;
	@Column(name = "annex_flag")
	private Boolean annexFlag;
	@Column(name = "hot_flag")
	private Boolean hotFlag;
	@Column(name = "recommend_flag")
	private Boolean recommendFlag;
	@Column(name = "copy_flag")
	private Boolean copyFlag;
	@Column(name = "copyout_flag")
	private Boolean copyoutFlag;
	@Column(name = "type", nullable = false)
	@Enumerated(EnumType.STRING)
	private ArticleStatus type;
	@Column(name = "link_addr", columnDefinition = "text")
	private String linkAddr;
	@Column(name = "eauthor")
	private String eauthor;
	@Column(name = "owner")
	private String owner;
	@Column(name = "eauthor_real")
	private String eauthorReal;
	
	public Article() {
		topFlag = false;
		commentFlag = false;
		imageFlag = false;
		videoFlag = false;
		annexFlag = false;
		hotFlag = false;
		recommendFlag = false;
		copyFlag = false;
		copyoutFlag = false;
		type = ArticleStatus.GENERAL;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitleStyle() {
		return titleStyle;
	}

	public void setTitleStyle(String titleStyle) {
		this.titleStyle = titleStyle;
	}

	public String getShortTitle() {
		return shortTitle;
	}

	public void setShortTitle(String shortTitle) {
		this.shortTitle = shortTitle;
	}

	public String getShortTitleStyle() {
		return shortTitleStyle;
	}

	public void setShortTitleStyle(String shortTitleStyle) {
		this.shortTitleStyle = shortTitleStyle;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getSubTitleStyle() {
		return subTitleStyle;
	}

	public void setSubTitleStyle(String subTitleStyle) {
		this.subTitleStyle = subTitleStyle;
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
	
	public Boolean getImageFlag() {
		return imageFlag;
	}

	public void setImageFlag(Boolean imageFlag) {
		this.imageFlag = imageFlag;
	}

	public Boolean getVideoFlag() {
		return videoFlag;
	}

	public void setVideoFlag(Boolean videoFlag) {
		this.videoFlag = videoFlag;
	}

	public Boolean getAnnexFlag() {
		return annexFlag;
	}

	public void setAnnexFlag(Boolean annexFlag) {
		this.annexFlag = annexFlag;
	}

	public Boolean getHotFlag() {
		return hotFlag;
	}

	public void setHotFlag(Boolean hotFlag) {
		this.hotFlag = hotFlag;
	}

	public Boolean getRecommendFlag() {
		return recommendFlag;
	}

	public void setRecommendFlag(Boolean recommendFlag) {
		this.recommendFlag = recommendFlag;
	}

	public Boolean getCopyFlag() {
		return copyFlag;
	}

	public void setCopyFlag(Boolean copyFlag) {
		this.copyFlag = copyFlag;
	}

	public Boolean getCopyoutFlag() {
		return copyoutFlag;
	}

	public void setCopyoutFlag(Boolean copyoutFlag) {
		this.copyoutFlag = copyoutFlag;
	}

	public ArticleStatus getType() {
		return type;
	}
	
	public String getTypeDescription(){
		if (type != null){
			return type.getDescription();
		}else{
			return ArticleStatus.GENERAL.getDescription();
		}
	}

	public void setType(ArticleStatus type) {
		this.type = type;
	}

	public String getLinkAddr() {
		return linkAddr;
	}

	public void setLinkAddr(String linkAddr) {
		this.linkAddr = linkAddr;
	}

	public String getEauthor() {
		return eauthor;
	}

	public void setEauthor(String eauthor) {
		this.eauthor = eauthor;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getEauthorReal() {
		return eauthorReal;
	}

	public void setEauthorReal(String eauthorReal) {
		this.eauthorReal = eauthorReal;
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
