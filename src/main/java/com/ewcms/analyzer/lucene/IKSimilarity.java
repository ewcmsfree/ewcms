/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.analyzer.lucene;

import org.apache.lucene.search.DefaultSimilarity;

/**
 * <ul>
 * 相似度评估器，
 * 重载了DefaultSimilarity的coord方法，
 * 提高词元命中个数在相似度比较中的权重影响，即，当有多个词元得到匹配时，文档的相似度将提高
 * </ul>
 * 
 * @author 吴智俊
 */
public class IKSimilarity extends DefaultSimilarity {

	private static final long serialVersionUID = 7558565500061194774L;

	/* (non-Javadoc)
	 * @see org.apache.lucene.search.Similarity#coord(int, int)
	 */
	public float coord(int overlap, int maxOverlap) {
		float overlap2 = (float)Math.pow(2, overlap);
		float maxOverlap2 = (float)Math.pow(2, maxOverlap);
		return (overlap2 / maxOverlap2);
	}
}
