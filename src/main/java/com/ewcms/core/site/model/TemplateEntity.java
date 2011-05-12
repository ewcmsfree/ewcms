package com.ewcms.core.site.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "site_templateentity")
@SequenceGenerator(name = "seq_site_templateentity", sequenceName = "seq_site_templateentity_id", allocationSize = 1)
public class TemplateEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "seq_site_templateentity", strategy = GenerationType.SEQUENCE)
    private Integer id;
    
    @Column()
    private byte[] tplEntity;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public byte[] getTplEntity() {
		return tplEntity;
	}

	public void setTplEntity(byte[] tplEntity) {
		this.tplEntity = tplEntity;
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
		TemplateEntity other = (TemplateEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}   
	
}
