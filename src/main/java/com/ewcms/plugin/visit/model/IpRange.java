package com.ewcms.plugin.visit.model;

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
 * @author wu_zhijun
 * 
 */
@Entity
@Table(name = "plugin_visit_iprange")
@SequenceGenerator(name = "seq_plugin_visit_iprange", sequenceName = "seq_plugin_visit_iprange_id", allocationSize = 1)
public class IpRange implements Serializable {

	private static final long serialVersionUID = -4857600927906735033L;

	@Id
    @GeneratedValue(generator = "seq_plugin_visit_iprange",strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Long id;
	@Column(name = "ip_begin")
	private Long ipBegin;
	@Column(name = "ip_end")
	private Long ipEnd;
	@Column(name = "country")
	private String country;
	@Column(name = "city")
	private String city;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIpBegin() {
		return ipBegin;
	}

	public void setIpBegin(Long ipBegin) {
		this.ipBegin = ipBegin;
	}

	public Long getIpEnd() {
		return ipEnd;
	}

	public void setIpEnd(Long ipEnd) {
		this.ipEnd = ipEnd;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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
		IpRange other = (IpRange) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
