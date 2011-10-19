/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.web;

import static com.ewcms.common.lang.EmptyUtil.isArrayNotEmpty;
import static com.ewcms.common.lang.EmptyUtil.isStringEmpty;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.common.convert.ConvertException;
import com.ewcms.common.convert.ConvertFactory;
import com.ewcms.common.convert.Convertable;
import com.ewcms.common.query.Resultable;
import com.ewcms.common.query.cache.CacheResultable;
import com.ewcms.common.query.jpa.EntityQueryable;
import com.ewcms.common.query.jpa.QueryFactory;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;
import com.ewcms.web.vo.DataGrid;

/**
 * 查询显示
 *
 * <p>用于页面查询显示，查询结果通过JSON数据格式返回，数据格式针对Jquery easyUI DataGrid</p>
 *
 * @author wangwei
 */
public abstract class QueryBaseAction extends EwcmsBaseAction {

    private static final String ARRAY_SEPARATOR = ",";
    private String cacheKey;
    private int page = 1;
    private int rows = 15;
    protected Order order = new Order();
    private String[] selections = new String[]{};
    private Map<String, String> parameters = new HashMap<String, String>();
    private List<String> errors = new ArrayList<String>();
    private DateFormat dateFormat;

    @Autowired
    protected QueryFactory queryFactory;
    
    @Override
    public String execute() {
        return query();
    }
    
    public String query() {
        
        page = page -1;
        
        Resultable result = (isSelectionQuery() ? 
                querySelectionsResult(queryFactory,rows,page,selections,order) : 
                queryResult(queryFactory,cacheKey,rows,page,order));
        
        DataGrid grid = createDataGrid(result);
        
        renderJson(grid);
        
        return NONE;
    }

    private boolean isSelectionQuery() {
        return isArrayNotEmpty(selections);
    }
    
    /**
     * 查询记录
     * 
     * <p>实现通过参数查询，一般使用Cache查询可以提高效率，减少数据库压力</p>
     * 
     * @param queryFactory 数据查询工厂
     * @param cacheKey 缓存key
     * @param rows  行数
     * @param page  页数
     * @param order 排序
     * @return Resultable
     */
    protected abstract Resultable queryResult(QueryFactory queryFactory,String cacheKey,int rows,int page,Order order);

    /**
     * 查询选定的记录
     * 
     * <p>通过用户编号(PK)查询选定的记录，一般用于添加、修改查询显示，不应使用Cache查询。</p>
     * 
     * @param queryFactory 数据查询工厂
     * @param rows 行数
     * @param page 页数
     * @param selections 查询编号集合
     * @param order 排序
     * @return Resultable
     */
    protected abstract Resultable querySelectionsResult(QueryFactory queryFactory,int rows,int page,String[] selections,Order order);
    
    /**
     * 创建DataGrid VO
     * 
     * <p>生成前台显示数据值对象</p>
     * 
     * @param result 查询结果
     * @return DataGrid
     */
    protected DataGrid createDataGrid(Resultable result){
        if(!errors.isEmpty()){
             return new DataGrid(errors);
        }
        if(result instanceof CacheResultable){
            return new DataGrid(((CacheResultable)result).getCacheKey(),result.getCount(), result.getResultList());
        }else{
            return new DataGrid(result.getCount(), result.getResultList());
        }
    }
    
    /**
     * 以JSON格式数据返回查询结果
     * 
     * @param data DataGrid数据对象
     */
    protected void renderJson(DataGrid data){
        if (dateFormat == null) {
            Struts2Util.renderJson(JSONUtil.toJSON(data));
        } else {
            Struts2Util.renderJson(JSONUtil.toJSON(data, dateFormat));
        }
    }

    /**
     * 得到指定参数值，构造查询使用。
     *
     * @param <I>  类型
     * @param type 类型类（如:Integer.class,Date.class)
     * @param name 参数名（parameters中的名称）
     * @return
     */
    protected <I> I getParameterValue(final Class<I> type, final String name) {
        return getParameterValue(type, name, null, null);
    }

    /**
     * 得到指定参数值，构造查询使用。
     *
     * @param <I>  类型
     * @param type 类型类（如:Integer.class,Date.class)
     * @param name 参数名（parameters中的名称）
     * @param msg  错误提示信息
     * @return
     */
    protected <I> I getParameterValue(final Class<I> type, final String name, final String msg) {
        return getParameterValue(type, name, null, msg);
    }

    /**
     * 得到指定参数值，参数不存在返回缺省值，构造查询使用。
     *
     * @param <I>  类型
     * @param type 类型类（如:Integer.class,Date.class)
     * @param name 参数名（parameters中的名称）
     * @param defaultValue 缺省值
     * @param msg 错误提示信息
     * @return
     */
    protected <I> I getParameterValue(final Class<I> type, final String name, final I defaultValue, final String msg) {

        String value = parameters.get(name);
        
        if (isStringEmpty(value)) {
            return defaultValue;
        }

        try {
            Convertable<I> handler = ConvertFactory.instance.convertHandler(type);
            return handler.parse(value);
        } catch (ConvertException e) {
            if(msg == null){
                errors.add("");
            }else{
                errors.add(msg);
            }
            return null;
        }
    }

    /**
     * 得到指定参数集合，构造查询使用。<br/>
     * 使用缺省的分割符号","
     * 
     * @param <I>  类型
     * @param type 类型类（如:Integer.class,Date.class)
     * @param name 参数名（parameters中的名称）
     * @param msg 错误提示信息
     * @return
     */
    protected <I> List<I> getParameterArrayValue(final Class<I> type, final String name, final String msg) {
        return getParameterArrayValue(type, ARRAY_SEPARATOR, name, msg);
    }

    /**
     * 指定字符分割符号，得到指定参数集合，构造查询使用。
     *
     * @param <I>  类型
     * @param type 类型类（如:Integer.class,Date.class)
     * @param regex 分割符号
     * @param name 参数名（parameters中的名称）
     * @param msg 错误提示信息
     * @return
     */
    protected <I> List<I> getParameterArrayValue(final Class<I> type, final String regex, final String name, final String msg) {
        return getParameterArrayValue(type, regex, name, null, msg);
    }

    /**
     * 得到指定参数集合，参数不存在返回缺省值，构造查询使用。
     *
     * @param <I>  类型
     * @param type 类型类（如:Integer.class,Date.class)
     * @param name 参数名（parameters中的名称）
     * @param defaultValue 缺省值
     * @param msg 错误提示信息
     * @return
     */
    protected <I> List<I> getParameterArrayValue(final Class<I> type, final String name, final List<I> defaultValue, final String msg) {
        return getParameterArrayValue(type, ARRAY_SEPARATOR, name, null, msg);
    }

    /**
     * 指定字符分割符号，得到指定参数集合，参数不存在返回缺省值，构造查询使用。
     *
     * @param <I>  类型
     * @param type 类型类（如:Integer.class,Date.class)
     * @param regex 分割符号
     * @param name 参数名（parameters中的名称）
     * @param defaultValue 缺省值
     * @param msg 错误提示信息
     * @return
     */
    protected <I> List<I> getParameterArrayValue(final Class<I> type, final String regex, final String name, final List<I> defaultValue, final String msg) {

        String value = parameters.get(name);
        if (isStringEmpty(value)) {
            return defaultValue;
        }

        String suffice = value.replace("'", "").replace("\"", "");

        try {
            return convertArray(type, suffice.split(regex));
        } catch (ConvertException e) {
            errors.add(msg);
            return null;
        }
    }

    /**
     * 字符串数组转换成指定类型的集合
     * 
     * @param <I>  类型
     * @param type 类型类
     * @param values 转换字符串数组
     * @return
     * @throws ConvertException
     */
    protected <I> List<I> convertArray(final Class<I> type, final String[] values) throws ConvertException {
        List<I> list = new ArrayList<I>();
        Convertable<I> handler = ConvertFactory.instance.convertHandler(type);
        for (String value : values) {
            list.add(handler.parse(value));
        }
        return list;
    }

    /**
     * 设置json日期格式
     * 
     * @param dateFormat
     */
    protected void setDateFormat(final DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    /**
     * 得到查询编号（PK）集合
     * 
     * @param <I> 编号类型
     * @param type 编号类型类（如：Integer.class,Date.class）
     * @return
     */
    protected <I> List<I> getIds(final Class<I> type) {
        try {
            return convertArray(type, selections);
        } catch (ConvertException e) {
            return new ArrayList<I>();
        }
    }

    /**
     * 实体查询排序
     *
     * @param query 查询接口
     * @param order 排序对象
     */
    protected void entityOrder(final EntityQueryable query, final Order order) {
        if (!order.hasOrder()) {
            return;
        }
        if (order.isAsc()) {
            query.orderAsc(order.sort);
        } else {
            query.orderDesc(order.sort);
        }
    }

    public void setQueryFactory(QueryFactory  queryFactory){
        this.queryFactory = queryFactory;
    }
    
    /**
     * 查询参数
     * 
     * @param parameters 查询参数
     */
    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }
    
    public Map<String, String> getParameters() {
        return parameters;
    }
    
    /**
     * 选择显示的记录
     *
     * @param selections 记录PK集合
     */
    public void setSelections(final String[] selections) {
        this.selections = selections;
    }
    
    public void setCacheKey(String cacheKey){
        this.cacheKey = cacheKey;
    }
    
    public void setPage(final int page) {
        this.page = page;
    }

    public void setRows(final int rows) {
        this.rows = rows;
    }

    public void setSort(final String value) {
        order.setSort(value);
    }

    public void setOrder(final String value) {
        order.setOrder(value);
    }
    
    public class Order {

        private static final String ASC = "asc";
        private String sort;
        private String order;

        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }
        
        public boolean hasOrder(){
            return !isStringEmpty(order);
        }

        public boolean isAsc() {
            return order.equals(ASC);
        }
    }
}
