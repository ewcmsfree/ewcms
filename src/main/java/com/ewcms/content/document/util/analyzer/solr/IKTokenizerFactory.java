/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.util.analyzer.solr;

import java.io.Reader;
import java.util.Map;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.solr.analysis.BaseTokenizerFactory;

import com.ewcms.content.document.util.analyzer.lucene.IKTokenizer;

/**
 * <ul>
 * 实现Solr1.4分词器接口，
 * 基于IKTokenizer的实现
 * </ul>
 * 
 * @author 吴智俊
 */
public final class IKTokenizerFactory extends BaseTokenizerFactory{
	
	private boolean isMaxWordLength = false;
	
	/**
	 * IK分词器Solr TokenizerFactory接口实现类
	 * 默认最细粒度切分算法
	 */
	public IKTokenizerFactory(){
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.apache.solr.analysis.BaseTokenizerFactory#init(java.util.Map)
	 */
	public void init(Map<String,String> args){
		String _arg = args.get("isMaxWordLength");
		isMaxWordLength = Boolean.parseBoolean(_arg);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.apache.solr.analysis.TokenizerFactory#create(java.io.Reader)
	 */
	public Tokenizer create(Reader reader) {
		return new IKTokenizer(reader , isMaxWordLength());
	}

	public void setMaxWordLength(boolean isMaxWordLength) {
		this.isMaxWordLength = isMaxWordLength;
	}

	public boolean isMaxWordLength() {
		return isMaxWordLength;
	}

}
