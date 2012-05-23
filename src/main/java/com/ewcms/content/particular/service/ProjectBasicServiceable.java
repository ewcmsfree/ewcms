package com.ewcms.content.particular.service;

import java.io.File;
import java.util.List;

import org.dom4j.Document;

import com.ewcms.content.particular.model.ProjectBasic;

public interface ProjectBasicServiceable {
	
	public Long addProjectBasic(ProjectBasic projectBasic);
	
	public Long updProjectBasic(ProjectBasic projectBasic);
	
	public void delProjectBasic(Long id);
	
	public ProjectBasic findProjectBasicById(Long id);
	
	public List<ProjectBasic> findProjectBasicAll();
	
	public void addProjectBasicByImportXml(File file, Integer channelId);
	
	public Document exportXml(List<Long> projectBasicIds);
}
