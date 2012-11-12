package com.ewcms.plugin.visit.manager.vo;

import java.io.Serializable;
import java.util.Date;

public class CountVo implements Serializable {

	private static final long serialVersionUID = 2487917918381541573L;

	private Date date;
	private Long value;

	public CountVo(Date date, Long value){
		this.date = date;
		this.value = value;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

}
