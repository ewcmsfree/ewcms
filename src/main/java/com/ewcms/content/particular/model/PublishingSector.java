package com.ewcms.content.particular.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 发布部门
 * 
 * <ul>
 * <li>id:编号</li>
 * <li>code:组织机构代码</li>
 * <li>name:机关单位名称</li>
 * </ul>
 * @author wuzhijun
 */

@Entity
@Table(name = "particular_publishing_sector")
@SequenceGenerator(name = "seq_particular_publishing_sector", sequenceName = "seq_particular_publishing_sector_id", allocationSize = 1)
public class PublishingSector implements Serializable {

	private static final long serialVersionUID = 6391042116353665786L;

	@Id
    @GeneratedValue(generator = "seq_particular_publishing_sector",strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Long id;
	@Column(name = "code", length = 9, nullable = false, unique = true)
	private String code;
	@Column(name = "name", length = 200, nullable = false)
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
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
		PublishingSector other = (PublishingSector) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}
}
