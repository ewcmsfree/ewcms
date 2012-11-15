package com.ewcms.plugin.visit.manager.vo;

import java.io.Serializable;
import java.util.Date;

public class DataValueVo implements Serializable {

	private static final long serialVersionUID = -1133335235031262104L;

	private Date date;
	private Long value;

	public DataValueVo(Date date, Long value) {
		super();
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
