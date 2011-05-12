/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.plugin.leadingwindow.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 领导基本信息
 * 
 * <ul>
 * <li>id:编号</li>
 * <li>name:姓名</li>
 * <li>resume:简历</li>
 * <li>chargeWork:分管工作</li>
 * <li>duties:职务</li>
 * <li>email:E-mail</li>
 * <li>contact:联系电话</li>
 * <li>mobile:手机</li>
 * <li>officeAddress:办公地址</li>
 * <li>image:照片</li>
 * <li>sort:排序</li>
 * <li>channel:频道对象</li>
 * <li>copySource:复制源</li>
 * <li>channelId:频道编号</li>
 * </ul>
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "plugin_leader")
@SequenceGenerator(name = "seq_plugin_leader", sequenceName = "seq_plugin_leader_id", allocationSize = 1)
public class Leader implements Serializable {

    private static final long serialVersionUID = 1793557225088009255L;
    @Id
    @GeneratedValue(generator = "seq_plugin_leader", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name", length = 30, nullable = false)
    private String name;
    @Column(name = "resume", columnDefinition = "text")
    private String resume;
    @Column(name = "charge_work", columnDefinition = "text")
    private String chargeWork;
    @Column(name = "duties")
    private String duties;
    @Column(name = "email")
    private String email;
    @Column(name = "contact")
    private String contact;
    @Column(name = "mobile")
    private String mobile;
    @Column(name = "office_address")
    private String officeAddress;
    @Column(name = "image")
    private String image;
    @Column(name = "sort")
    private Integer sort;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "leaders")
    @OrderBy(value = "sort,id")
    private List<Position> positions;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = LeaderChannel.class, orphanRemoval = true)
    @JoinColumn(name = "leader_id")
    @OrderBy(value = "sort,id")
    private List<LeaderChannel> leaderChannels;
    @Column(name = "channel_id")
    private Integer channelId;

    public Leader() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getChargeWork() {
        return chargeWork;
    }

    public void setChargeWork(String chargeWork) {
        this.chargeWork = chargeWork;
    }

    public String getDuties() {
		return duties;
	}

	public void setDuties(String duties) {
		this.duties = duties;
	}

	public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public List<LeaderChannel> getLeaderChannels() {
        return leaderChannels;
    }

    public void setLeaderChannels(List<LeaderChannel> leaderChannels) {
        this.leaderChannels = leaderChannels;
    }

    @JsonIgnore
    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
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
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Leader other = (Leader) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }
}
