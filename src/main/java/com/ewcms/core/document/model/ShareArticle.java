/**
 * 
 */
package com.ewcms.core.document.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnore;


/**
 * 
 * <ul>
 * <li>id:编号</li>
 * <li>createTime:共享时间</li>
 * <li>refed:是否被引用</li>
 * <li>articleRmc:共享文章对象</li>
 * </ul>
 * 
 * @author 周冬初
 *
 */
@Entity
@Table(name = "doc_sharearticle")
@SequenceGenerator(name = "seq_sev_sharearticle", sequenceName = "seq_sev_sharearticle_id", allocationSize = 1)
public class ShareArticle implements Serializable {
    @Id
    @GeneratedValue(generator = "seq_sev_sharearticle", strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date createTime = new Date();
    @Column()
    private Boolean refed = false;
	@OneToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY, targetEntity = ArticleRmc.class)
	@JoinColumn(name = "ref_id")
	private ArticleRmc articleRmc;
	@Column()
	private Integer siteId;
	@Column()
	private String channelName;
	@Column()
	private String articleTitle;
	
	
	public Integer getId() {
		return id;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Boolean getRefed() {
		return refed;
	}

	public void setRefed(Boolean refed) {
		this.refed = refed;
	}

	@JsonIgnore
	public ArticleRmc getArticleRmc() {
		return articleRmc;
	}

	public void setArticleRmc(ArticleRmc articleRmc) {
		this.articleRmc = articleRmc;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	
}
