package com.ewcms.web.util;

public enum GlobaPath {
    
	ResourceDir("/pub_res"),
    DocumentDir("/document");
	
	private String path;

	private GlobaPath(String path){ 
		this.path = path;
	}
	public String getPath(){
		return path;
	}
}
