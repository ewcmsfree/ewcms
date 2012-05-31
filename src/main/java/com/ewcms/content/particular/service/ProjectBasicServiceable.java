/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.content.particular.service;

import java.io.File;
import java.util.List;

import org.dom4j.Document;

import com.ewcms.content.particular.BaseException;
import com.ewcms.content.particular.model.ProjectBasic;

public interface ProjectBasicServiceable {
	
	public Long addProjectBasic(ProjectBasic projectBasic) throws BaseException;
	
	public Long updProjectBasic(ProjectBasic projectBasic) throws BaseException;
	
	public void delProjectBasic(Long id);
	
	public ProjectBasic findProjectBasicById(Long id);
	
	public List<ProjectBasic> findProjectBasicAll();
	
	public void addProjectBasicByImportXml(File file, Integer channelId);
	
	public Document exportXml(List<Long> projectBasicIds);
	
	public void pubProjectBasic(List<Long> projectBasicIds);
	
	public void unPubProjectBasic(List<Long> projectBasicIds);
}
