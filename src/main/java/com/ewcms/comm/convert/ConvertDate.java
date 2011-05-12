/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ewcms.comm.convert;

/**
 *
 * @author wangwei
 */
public interface ConvertDate<T> extends Convertable<T>{

    /**
     * 设置日期格式
     * 
     * @param formate
     */
    public void setFormat(String format);
}
