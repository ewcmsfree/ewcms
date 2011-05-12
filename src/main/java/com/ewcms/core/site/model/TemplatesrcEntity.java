/**
 * 
 */
package com.ewcms.core.site.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author 周冬初
 *
 */

@Entity
@Table(name = "site_templatesrcent")
@SequenceGenerator(name = "seq_site_templatesrcent", sequenceName = "seq_site_templatesrcent_id", allocationSize = 1)

public class TemplatesrcEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(generator = "seq_site_templatesrcent", strategy = GenerationType.SEQUENCE)
    private Integer id;
    
    @Column()
    private byte[] srcEntity;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public byte[] getSrcEntity() {
		return srcEntity;
	}

	public void setSrcEntity(byte[] srcEntity) {
		this.srcEntity = srcEntity;
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
		TemplatesrcEntity other = (TemplatesrcEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}   
	
}
