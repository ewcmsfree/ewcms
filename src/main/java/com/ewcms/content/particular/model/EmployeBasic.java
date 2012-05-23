package com.ewcms.content.particular.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 从业人员基本信息
 * 
 * <ul>
 * <li>id:编号</li>
 * <li>name:人员姓名</li> 
 * <li>sex:性别</li> 
 * <li>publishingSector:发布部门</li>
 * <li>published:发布时间</li>
 * <li>cardType:证件类型</li>
 * <li>cardCode:证件号码</li>
 * <li>dense:所属密级</li>
 * <li>channelId:专栏编号</li>
 * </ul>
 * 
 * @author wuzhijun
 * 
 */
@Entity
@Table(name = "particular_employe_basic")
@SequenceGenerator(name = "seq_particular_employe_basic", sequenceName = "seq_particular_employe_basic_id", allocationSize = 1)
public class EmployeBasic implements Serializable {

	private static final long serialVersionUID = 2547138119716199680L;

	/**
	 * 性别
	 * 
	 * @author wuzhijun
	 */
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

	public enum CardType{
		RESIDENT("居民身份证"), DRIVE("驾驶证"), JINGGUAN("警官证"), OFFICER("军官证"), OTHER("其他证件");
		
		private String description;

		private CardType(String description) {
			this.description = description;
		}

		public String getDescription() {
			return description;
		}
	}
	
	@Id
	@GeneratedValue(generator = "seq_particular_employe_basic", strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Long id;
	@Column(name = "name", nullable = false, length = 200)
	private String name;
	@Column(name = "sex", nullable = false)
	@Enumerated(EnumType.STRING)
	private Sex sex;
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH }, targetEntity = PublishingSector.class)
	@JoinColumn(name = "publishing_sector", referencedColumnName = "code")
	private PublishingSector publishingSector;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "published")
	private Date published;
	@Column(name = "card_type")
	@Enumerated(EnumType.STRING)
	private CardType cardType;
	@Column(name = "card_code")
	private String cardCode;
	@Column(name = "dense", nullable = false)
	@Enumerated(EnumType.STRING)
	private Dense dense;
	@Column(name = "channel_id")
	private Integer channelId;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Sex getSex() {
		return sex;
	}

	public String getSexDescription(){
		if (sex != null){
			return sex.getDescription();
		}else{
			return Sex.MALE.getDescription();
		}
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public PublishingSector getPublishingSector() {
		return publishingSector;
	}

	public void setPublishingSector(PublishingSector publishingSector) {
		this.publishingSector = publishingSector;
	}

	public Date getPublished() {
		return published;
	}

	public void setPublished(Date published) {
		this.published = published;
	}

	public CardType getCardType() {
		return cardType;
	}

	public String getCardTypeDescription(){
		if (cardType != null){
			return cardType.getDescription();
		}else{
			return CardType.RESIDENT.getDescription();
		}
	}
	
	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public Dense getDense() {
		return dense;
	}

	public String getDenseDescription(){
		if (dense != null){
			return dense.getDescription();
		}else{
			return Dense.GENERAL.getDescription();
		}
	}
	
	public void setDense(Dense dense) {
		this.dense = dense;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
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
		EmployeBasic other = (EmployeBasic) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
