/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.web.action;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

import com.ewcms.comm.convert.Convertable;
import com.ewcms.comm.convert.ConvertException;
import com.ewcms.comm.convert.ConvertFactory;
import com.ewcms.comm.jpa.query.EntityPageQueryable;
import com.ewcms.comm.jpa.query.PageQueryable;
import com.ewcms.util.DataGrid;
import com.ewcms.util.JSONUtil;
import com.ewcms.util.Struts2Util;

/**
 *查询Action抽象
 *
 * 针对jquery-easyui table的封装，为"CRUD"数据操作，提供辅助显示。
 *
 * @author wangwei
 */
public abstract class QueryBaseAction extends EwcmsBaseAction {

    private static final String ARRAY_SEPARATOR = ",";
    private int page = 1;
    private int rows = 15;
    protected Order order = new Order();
    private String[] selections = new String[]{};
    private Map<String, String> parameters = new HashMap<String, String>();
    private List<String> errors = new ArrayList<String>();
    private DateFormat dateFormat;

    public Map<String, String> getParameters() {
        return parameters;
    }

    /**
     * 查询参数设置
     *
     * 通过OGNL自动映射成。
     * 
     * @param parameters key查询属性名，value为字符串（集合使用","分割符）
     *
     * @param queryMap
     */
    public void setParamters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public void setPage(final int page) {
        this.page = page;
    }

    public int getPage() {
        return this.page;
    }

    public void setRows(final int rows) {
        this.rows = rows;
    }

    public int getRows() {
        return this.rows;
    }

    public void setSort(final String value) {
        order.setSort(value);
    }

    public String getSort() {
        return order.getSort();
    }

    public void setOrder(final String value) {
        order.setOrder(value);
    }

    public String getOrder() {
        return order.getOrder();
    }

    /**
     * 选择显示的记录
     *
     * @param selections 记录PK集合
     */
    public void setSelections(final String[] selections) {
        this.selections = selections;
    }

    public String[] getSelections() {
        return this.selections;
    }

    /**
     * 查询方法，Struts2执行方法
     * 
     */
    public String query() {
      return queryDataGrid();
    }
    
    /**
     * DataGrid数据查询
     * 
     * @return datagrid json
     */
    public String queryDataGrid() {
        PageQueryable query = (isNewQuery() ? constructNewQuery(order): constructQuery(order));
        DataGrid data;
        if (errors.isEmpty()) {
            setPageAndRows(query, page, rows);
            data = new DataGrid(constructCount(query.count()),
                    constructRows(query.getResultList()));
        } else {
            data = new DataGrid(errors);
        }
        if (dateFormat == null) {
            Struts2Util.renderJson(JSONUtil.toJSON(data));
        } else {
            Struts2Util.renderJson(JSONUtil.toJSON(data, dateFormat));
        }
        return NONE;
    }
    
    protected int constructCount(final int count) {
        return count;
    }

    protected List constructRows(final List data) {
        return data;
    }

    @Override
    public String execute() {
        return query();
    }

    private void setPageAndRows(final PageQueryable query, final int page, final int rows) {
        query.setPage(page - 1);
        query.setRows(rows);
    }

    private boolean isNewQuery() {
        return selections != null && selections.length != 0;
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

        if (isEmpty(value)) {
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
        if (!isEmpty(value)) {
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
     * 判断是否为空
     *
     * <p>String类型</p>
     * 
     * <pre>
     * isEmpty(null)=true
     * isEmpty("") =true
     * isEmpty("  ")=true
     * isEmpty("test")=false
     * isEmpty(" test  ")=false
     *
     * <p>非String类型</p>
     *
     * <pre>
     * isEmpty(null)=true
     *
     * @param value
     * @return
     */
    protected boolean isEmpty(final Object value) {
        if (value instanceof String) {
            return StringUtils.isBlank((String) value);
        }
        return value == null ? true : false;
    }

    /**
     * 判断是否不为空
     *
     * <p>String类型</p>
     *
     * <pre>
     * isEmpty(null)=false
     * isEmpty("") =false
     * isEmpty("  ")=false
     * isEmpty("test")=true
     * isEmpty(" test  ")=true
     *
     * <p>非String类型</p>
     *
     * <pre>
     * isEmpty(null)=false
     *
     * @param value
     * @return
     */
    protected boolean isNotEmpty(final Object value) {
        return !isEmpty(value);
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
     * 构造参数查询
     *
     * @param parameters 查询参数
     * @param Order 排序
     * @return
     */
    protected abstract PageQueryable constructQuery(final Order order);

    /**
     * 构造添加查询
     *
     * @param Order 排序
     * @return
     */
    protected abstract PageQueryable constructNewQuery(final Order order);

    /**
     * 得到新增记录编号(PK)
     *
     * @param <I> 编号类型
     * @param type 编号类型类（如：Integer.class,Date.class）
     * @return
     */
    protected <I> List<I> getNewIdAll(final Class<I> type) {
        try {
            return convertArray(type, selections);
        } catch (ConvertException e) {
            return new ArrayList<I>();
        }
    }

    /**
     * 排序
     * <p>使用EntityPageQuery的排序方法</p>
     *
     * @param query 查询接口
     * @param order 排序对象
     */
    protected void simpleEntityOrder(final EntityPageQueryable query, final Order order) {
        if (isEmpty(order.getOrder())) {
            return;
        }
        if (order.isAsc()) {
            query.orderAsc(order.sort);
        } else {
            query.orderDesc(order.sort);
        }
    }

    protected class Order {

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

        public boolean isAsc() {
            return order.equals(ASC);
        }
    }
}
