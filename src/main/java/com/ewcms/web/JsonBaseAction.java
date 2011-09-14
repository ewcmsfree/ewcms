/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;

/**
 * Json Action基础类，实现Json 操作通用方法
 * 
 * @author wangwei
 */
public class JsonBaseAction {
    
    private static final Logger logger = LoggerFactory.getLogger(JsonBaseAction.class);
    
    /**
     * 操作成功输出json
     * 
     * @return 输出内容
     */
    public String renderSuccess(){
       return renderSuccess(null,null);
    }
    
    /**
     * 操作成能输出json
     * <br>
     * 指定操作成能返回值
     * 
     * @param value 返回值
     * @return 输出内容
     */
    public String renderSuccess(Object value){
        return renderSuccess(null,value);
    }

    /**
     * 操作成能输出json
     * <br>
     * 指定操作成功提示信息，和放回值
     * 
     * @param message 成功信息
     * @param value   返回值
     * @return 输出内容
     */
    public String renderSuccess(String message,Object value){
        return render(Boolean.TRUE,message,value);
    }

    /**
     * 操作错误输出json
     */
    public String renderError(){
        return renderError(null);
    }
    
    /**
     * 操作错误输出json
     * <br>
     * 指定错误信息
     * 
     * @param message 错误信息
     * @return 输出内容
     */
    public String renderError(String message){
        return render(Boolean.FALSE,message,null);
    }
    
    /**
     * 以Json格式输出操作信息
     * 
     * @param success 操作是否成功
     * @param message 提示信息
     * @param value   返回值
     * @return 输出内容
     */
    public String render(Boolean success,String message,Object value){
        JsonMessage m = new JsonMessage();
        m.success = success;
        m.message = message;
        m.value = value;
        
        String json = JSONUtil.toJSON(m);
        logger.debug("render success output {}",json);
        
        render(json);
        
        return json;
    }
    
    /**
     * 输出json
     * 
     * @param json json格式字符串
     * @return 输出内容
     */
    public String render(String json){
        
        Struts2Util.renderJson(json);
        return json;
    }
    
    /**
     * 输出信息对象
     * 
     * @author wangwei
     */
    public class JsonMessage{
        
        private Boolean success;
        private String message;
        private Object value;
        
        public Boolean getSuccess() {
            return success;
        }
        
        public String getMessage() {
            return message;
        }
        
        public Object getValue() {
            return value;
        }
    }
}
