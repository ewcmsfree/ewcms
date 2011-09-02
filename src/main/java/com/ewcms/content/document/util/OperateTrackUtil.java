/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.util;

import java.util.List;

import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.ArticleOperateTrack;

public class OperateTrackUtil {
	
	
	public static void addOperateTrack(Article article, String statusDesc, String userName, String description){
		ArticleOperateTrack aot = new ArticleOperateTrack();
		
		aot.setUserName(userName);
		aot.setStatusDesc(statusDesc);
		aot.setDescription(description);
		
		List<ArticleOperateTrack> aots = article.getOperateTracks();
		aots.add(aot);
		
		article.setOperateTracks(aots);
	}
}
