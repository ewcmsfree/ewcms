/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.util;

import java.util.List;

import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.ArticleOperateTrack;

/**
 * 
 * @author wu_zhijun
 */
public class OperateTrackUtil {

	public static void addOperateTrack(Article article, String statusDesc, String description, String reason, String userName, String userRealName){
		ArticleOperateTrack aot = new ArticleOperateTrack();
		
		aot.setUserName(userName);
		aot.setUserRealName(userRealName);
		aot.setStatusDesc(statusDesc);
		aot.setDescription(description);
		aot.setReason(reason);
		
		List<ArticleOperateTrack> aots = article.getOperateTracks();
		aots.add(aot);
		
		article.setOperateTracks(aots);
	}
}
