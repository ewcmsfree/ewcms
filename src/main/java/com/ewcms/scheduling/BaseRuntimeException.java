/**
 * 创建日期 2008-5-9
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.scheduling;

/**
 * 运行时异常类
 * 
 * @author 吴智俊
 */
public class BaseRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -7281897014256533959L;
    protected Object[] args;
    private boolean wrapperObject;

    public BaseRuntimeException(String message) {
        super(message);
    }

    public BaseRuntimeException(String message, Throwable cause) {
        super(message, cause);
        this.wrapperObject = true;
    }

    public BaseRuntimeException(Throwable cause) {
        super(cause);
        this.wrapperObject = true;
    }

    public BaseRuntimeException(String message, Object[] args) {
        super(message);
        this.args = args;
    }

    public BaseRuntimeException() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * @return args.
     */
    public Object[] getArgs() {
        return args;
    }

    /**
     * @param args
     *            设置args
     */
    public void setArgs(Object[] args) {
        this.args = args;
    }

    /**
     * @return wrapperObject.
     */
    public boolean isWrapperObject() {
        return wrapperObject;
    }

    /**
     * @param wrapperObject
     *            设置wrapperObject
     */
    public void setWrapperObject(boolean wrapperObject) {
        this.wrapperObject = wrapperObject;
    }
}
