/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.interaction.service;

import java.util.Date;
import java.util.List;

import com.ewcms.plugin.interaction.model.Interaction;

/**
 * @author wuzhijun
 */
public interface InteractionServiceable {
	public Interaction getInteraction(Integer id);

	public void interactionChecked(Integer id, Boolean checked);

	public void interactionReplay(Integer id, String replay, Date date, Date replayDate, String content, String title, Integer type);

	public void interactionOrgan(Integer id, Integer organId, String organName);

	public void speakChecked(Integer id, boolean checked);

	public void interactionBackRatio(Integer id);
	
	public void deleteInteraction(List<Integer> ids);
	
	public void deleteSpeak(int[] ids);
}
