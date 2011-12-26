/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.publication.uri;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 实现uri模版规则解析
 * 
 * @author wangwei
 */
public class RuleParse implements RuleParseable {

    private static final Logger logger = LoggerFactory.getLogger(RuleParse.class);
    private static final String  VARIABLE_START="${";
    private static final String  VARIABLE_END="}";
    private static final String VARIABLE_FORMAT= "?";
    
    private final String patter;
    private final Map<String, String> variables;

    public RuleParse(String patter) {
        this.patter = patter;
        this.variables = parseVariables(patter);
    }

    @Override
    public String getPatter() {
        return this.patter;
    }

    @Override
    public Map<String,String> getVariables(){
        return this.variables;
    }
    /**
     * 解析uri规则模版得到模版设置的变量
     * 
     * @param patter
     *            uri规则模版
     * @return 函数 key:函数名称 value:格式
     */
    private static Map<String, String> parseVariables(String patter) {
        Map<String, String> variables = new HashMap<String, String>();
        if(!StringUtils.contains(patter, VARIABLE_START)){
            return variables;
        }
        String[] tokens = StringUtils.splitByWholeSeparator(patter, VARIABLE_START);
        for (String token : tokens) {
            logger.debug("Token with ${{}", token);
            if (!StringUtils.contains(token, "}")) {
                logger.warn("\"{}\" is not closed", token);
                continue;
            }
            String value = StringUtils.split(token, VARIABLE_END)[0];
            if(StringUtils.isBlank(value)){
                logger.warn("Variable's nam is null");
                continue;
            }
            logger.debug("variable is {}", value);
            String[] s = StringUtils.splitByWholeSeparator(value,VARIABLE_FORMAT);
            if (s.length == 1) {
                variables.put(s[0], null);
            } else {
                variables.put(s[0], s[1]);
            }
        }
        return variables;
    }
}
