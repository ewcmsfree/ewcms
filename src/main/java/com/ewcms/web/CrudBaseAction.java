/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JTCT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  http://www.teachernet.com
 */
package com.ewcms.web;

import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Struts2中典型CRUD Action的抽象基类.
 * 
 * @author wangwei
 */
public abstract class CrudBaseAction<T, PK> extends EwcmsBaseAction {

	private static final long serialVersionUID = -7851825457853088626L;

	public static final String INDEX = "index";
	protected T vo;
	protected List<PK> operatorPK = new ArrayList<PK>();
	private String addFunction = "parent.queryNews";
	private String updFunction = "parent.queryReload";
	protected OperatorState operatorState = OperatorState.INIT;

	public enum OperatorState {

		INIT, ADD, UPDATE;
	}

	protected void setVo(T vo) {
		this.vo = vo;
	}

	protected T getVo() {
		return this.vo;
	}

	public void setOperatorPK(List<PK> operatorPK) {
		this.operatorPK = operatorPK;
	}

	public List<PK> getOperatorPK() {
		return operatorPK;
	}

	/**
	 * 设置调用javascript名称
	 * 
	 * @param addFunction
	 *            添加执行方法名称
	 * @param updFunction
	 *            修改执行方法名称
	 */
	protected void setJavaScriptFunction(String addFunction, String updFunction) {
		this.addFunction = addFunction;
		this.updFunction = updFunction;
	}

	/**
	 * 生成页面执行JavaScript语句
	 * 
	 * @return javascript语句
	 */
	public String getJavaScript() {
		if (operatorState == OperatorState.ADD) {
			return addJavaScriptFunction();
		}

		if (operatorState == OperatorState.UPDATE) {
			return updateJavaScriptFunction();
		}
		return "";
	}

	/**
	 * 添加javascript语句
	 * 
	 * @return
	 */
	protected String addJavaScriptFunction() {
		StringBuilder builder = new StringBuilder("");
		builder.append(addFunction);

		builder.append("(");
		builder.append(JSONUtil.toJSON(operatorPK));
		builder.append(");");
		return builder.toString();
	}

	/**
	 * 修改javascript语句
	 * 
	 * @return
	 */
	protected String updateJavaScriptFunction() {
		if (operatorPK.isEmpty()) {
			return updFunction + "(true);";
		}
		return updFunction + "();";
	}

	/**
	 * vo中的pk值
	 * 
	 * @param vo
	 *            保持同父类一致
	 * @return
	 */
	protected abstract PK getPK(T vo);

	/**
	 * Action函数,显示Index界面.
	 * 
	 * @return
	 * @throws Exception
	 */
	public String index() throws Exception {
		return INDEX;
	}

	/**
	 * Action函数,显示新增或修改Entity界面.
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public String input() throws Exception {

		vo = operatorPK.isEmpty() ? constructVo(createEmptyVo())
				: constructVo(getOperator(operatorPK.get(0)));

		return super.input();
	}

	/**
	 * 构造VO显示
	 * 
	 * @return
	 */
	protected T constructVo(T vo) {
		return vo;
	}

	/**
	 * 得到值对象
	 * 
	 * @param pk
	 *            键值
	 * @return
	 */
	protected abstract T getOperator(PK pk);

	/**
	 * Action函数,删除Entity.
	 * 
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception {
		for (PK pk : operatorPK) {
			deleteOperator(pk);
		}
		Struts2Util.renderText(SUCCESS);
		return NONE;
	}

	/**
	 * 删除操作
	 * 
	 * @param pk
	 *            键值
	 */
	protected abstract void deleteOperator(PK pk);

	/**
	 * Action函数,保存新增或修改Entity.
	 * 
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {
		if (isUpdateOperator()) {
		    operatorState = OperatorState.UPDATE;
            saveOperator(vo, true);
            operatorPK.remove(0);
		} else {
            operatorState = OperatorState.ADD;
            PK id = saveOperator(vo, false);
            operatorPK.add(id);
		}
		saveAfter();
		return SUCCESS;
	}
	
	/**
	 * 判断是否是添加操作
	 * 
	 * @return
	 */
	protected boolean isUpdateOperator(){
	    return getPK(vo) != null;
	}

	protected void saveAfter() {
		if (operatorState == OperatorState.UPDATE && !operatorPK.isEmpty()) {
			vo = constructVo(getOperator(operatorPK.get(0)));
		} else {
			vo = constructVo(createEmptyVo());
		}
	}

	/**
	 * 保存操作
	 * 
	 * @param vo
	 *            保持同父类一致
	 * @param isUpdate
	 *            是否修改操作
	 * @return
	 */
	protected abstract PK saveOperator(T vo, boolean isUpdate);

	/**
	 * 创建一个空的vo
	 * 
	 * @return vo
	 */
	protected abstract T createEmptyVo();

}
