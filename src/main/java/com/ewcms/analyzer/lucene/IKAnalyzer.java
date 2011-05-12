/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.analyzer.lucene;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;

/**
 * <ul>
 * 实现Lucene Analyzer，基于IKTokenizer的中文分词器
 * </ul>
 * 
 * @author 吴智俊
 */
public final class IKAnalyzer extends Analyzer {
	
	private boolean isMaxWordLength = false;
	
	/**
	 * IK分词器Lucene Analyzer接口实现类
	 * 默认最细粒度切分算法
	 */
	public IKAnalyzer(){
		this(false);
	}
	
	/**
	 * IK分词器Lucene Analyzer接口实现类
	 * 
	 * @param isMaxWordLength 当为true时，分词器进行最大词长切分
	 */
	public IKAnalyzer(boolean isMaxWordLength){
		super();
		this.setMaxWordLength(isMaxWordLength);
	}

	/* (non-Javadoc)
	 * @see org.apache.lucene.analysis.Analyzer#tokenStream(java.lang.String, java.io.Reader)
	 */
	@Override
	public TokenStream tokenStream(String fieldName, Reader reader) {
		return new IKTokenizer(reader , isMaxWordLength());
	}

	public void setMaxWordLength(boolean isMaxWordLength) {
		this.isMaxWordLength = isMaxWordLength;
	}

	public boolean isMaxWordLength() {
		return isMaxWordLength;
	}
}
