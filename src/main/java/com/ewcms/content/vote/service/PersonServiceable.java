/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.vote.service;

import java.util.List;

import com.ewcms.content.vote.model.Person;

/**
 * 
 * @author wu_zhijun
 */
public interface PersonServiceable {
	public Long addPerson(Person person);
	
	public Boolean findPersonIsEntity(Long questionnaireId, String ip);
	
	public void delPerson(Long personId);
	
	public List<String> getRecordToHtml(Long questionnaireId, Long personId);
}
