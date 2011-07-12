/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/**
 * 
 */
package com.ewcms.core.site.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * <li>id:编号 
 * <li>path:发布路径
 * <li>hostName:服务器IP
 * <li>port: 端口
 * <li>user:用户
 * <li>password:密码
 * </ul>
 * 
 * @author 周冬�?
 */
@Entity
@Table(name = "site_siteserver")
@SequenceGenerator(name = "seq_site_siteserver", sequenceName = "seq_site_siteserver_id", allocationSize = 1)
public class SiteServer implements Serializable {
    @Id
    @GeneratedValue(generator = "seq_site_siteserver", strategy = GenerationType.SEQUENCE)
    private Integer id;
    
    @Column(length = 100)
    private String path;
    @Column(length = 20)
    private String hostName;
    @Column(length = 5)
    private String port;
    @Column(length = 30)
    private String user;
    @Column(length = 20)
    private String password;
	@Column(length = 15)
	@Enumerated(EnumType.STRING)
	private UploadType uploadType;
    
	public UploadType getUploadType() {
		return uploadType;
	}
	public void setUploadType(UploadType uploadType) {
		this.uploadType = uploadType;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}

	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
    
}
