/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.analyzer.seg;

import com.ewcms.analyzer.Context;

/**
 * <ul>
 * 子分词器接口
 * </ul>
 * 
 * @author 吴智俊
 */
public interface ISegmenter {
	
	/**
	 * 从分析器读取下一个可能分解的词元对象
	 * @param segmentBuff 文本缓冲
	 * @param context 分词算法上下文
	 */
	void nextLexeme(char[] segmentBuff , Context context);
	
	/**
	 * 重置子分析器状态
	 */
	void reset();
}
