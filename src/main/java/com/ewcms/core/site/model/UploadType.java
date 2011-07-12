/**
 * 
 */
package com.ewcms.core.site.model;

/**
 * @author 周冬初
 *
 */
public enum UploadType {
    LOCAL("本地上传"),SFTP("sftp上传"),FTP("ftp上传"),SMB("smb方式");
	private String description;
	
	private UploadType(String description){
		this.description = description;
	}
	
	public String getDescription(){
		return description;
	}
}
