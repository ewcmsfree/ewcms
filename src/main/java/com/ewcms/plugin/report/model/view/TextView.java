/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.report.model.view;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * 文本框视图
 * 
 * <ul>
 * <li>maxLength:最大长度</li>
 * <li>minLength:最小长度</li>
 * <li>reqularExpr:正则表达式</li>
 * <li>minValue:最小值</li>
 * <li>MaxValue:最大值</li>
 * <li>isStrictMin:约束最小值（也就是大于最小值）</li>
 * <li>isStrictMax:约束最大值（也就是小于最大值）</li>
 * </ul>
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "plugin_report_view_text")
@PrimaryKeyJoinColumn(name = "view_id")
public class TextView extends ComponentView {

    private static final long serialVersionUID = 279848623979213696L;

    @Column(name = "maxlength")
    private Integer maxLength;
    @Column(name = "minlength")
    private Integer minLength;
    @Column(name = "regularexpr")
    private String regularExpr;
    @Column(name = "minvalue")
    private String minValue;
    @Column(name = "maxvalue")
    private String maxValue;
    @Column(name = "strictmin")
    private Boolean isStrictMin;
    @Column(name = "strictmax")
    private Boolean isStrictMax;

    public TextView(){
    	isStrictMin = false;
    	isStrictMax = false;
    }
    
    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public Integer getMinLength() {
        return minLength;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    public String getRegularExpr() {
        return regularExpr;
    }

    public void setRegularExpr(String regularExpr) {
        this.regularExpr = regularExpr;
    }

    public String getMinValue() {
        return minValue;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    public String getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    public Boolean isStrictMin() {
        return isStrictMin;
    }

    public void setStrictMin(Boolean isStrictMin) {
        this.isStrictMin = isStrictMin;
    }

    public Boolean isStrictMax() {
        return isStrictMax;
    }

    public void setStrictMax(Boolean isStrictMax) {
        this.isStrictMax = isStrictMax;
    }
}
