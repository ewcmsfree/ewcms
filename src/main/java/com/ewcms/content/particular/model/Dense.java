package com.ewcms.content.particular.model;

public enum Dense {
	GENERAL("普通"), TOPSECRET("绝密"), CONFIDENCE("机密"), ARCANUM("秘密");

	private String description;

	private Dense(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
