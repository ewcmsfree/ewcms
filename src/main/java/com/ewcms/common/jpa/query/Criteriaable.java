/*
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.common.jpa.query;

import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

/**
 *
 * @author wangwei
 */
interface Criteriaable<I extends Criteriaable,V,T> extends Queryable<V>{

    List<Path> getSelects();
    
    List<Expression> getExpressions();

    List<Order> getOrders();

    Root<T> getRoot();

    CriteriaQuery<V> getCriteriaQuery();

    EntityManager getEntityManager();

    CriteriaBuilder getCriteriaBuilder();

    I select (String... names);

    I select (SingularAttribute... attrs);
    
    /**
     * 添加"="条件
     *
     * @param name 属性名
     * @param value 值
     * @return this
     */
    I eq(String name, Object value);

    I eq(SingularAttribute attr, Object value);

    /**
     * 添加"!="条件
     *
     * @param name 属性名
     * @param value 值
     * @return this
     */
    I notEq(String name, Object value);

    I notEq(SingularAttribute attr, Object value);

    /**
     * 添加">"条件
     *
     * @param name 属性名
     * @param value 值
     * @return this
     */
    I gt(String name, Number value);

    I gt(SingularAttribute attr, Number value);

    /**
     * 添加">="条件
     *
     * @param name 属性名
     * @param value 值
     * @return this
     */
    I ge(String name, Number value);

    I ge(SingularAttribute attr, Number value);

    /**
     * 添加"&nbsp;"条件
     *
     * @param name 属性名
     * @param value 值
     * @return this
     */
    I lt(String name, Number value);

    I lt(SingularAttribute attr, Number value);

    /**
     * 添加"&nbsp;="条件
     *
     * @param name 属性名
     * @param value 值
     * @return this
     */
    I le(String name, Number value);

    I le(SingularAttribute attr, Number value);

    /**
     * 添加"Like"条件,Like '%xx'
     *
     * @param name 属性名
     * @param value 值
     * @return this
     */
    I likeStart(String name, String value);

    I likeStart(SingularAttribute attr, String value);

    /**
     * 添加"Like"条件,Like '%xx%'
     *
     * @param name 属性名
     * @param value 值
     * @return this
     */
    I likeAnywhere(String name, String value);

    I likeAnywhere(SingularAttribute attr, String value);

    /**
     * 添加"Like"条件,Like 'xx%'
     *
     * @param name 属性名
     * @param value 值
     * @return this
     */
    I likeEnd(String name, String value);

    I likeEnd(SingularAttribute attr, String value);

    /**
     * 添加"between"条件
     *
     * @param name 属性名
     * @param lo 下值
     * @param hi 上值
     * @return this
     */
    I between(String name, Comparable lo, Comparable hi);

    I between(SingularAttribute attr, Comparable lo, Comparable hi);

    /**
     * 添加"in"条件
     *
     * @param name 属性名
     * @param value 值
     * @return this
     */
    I in(String name, Collection<?> value);

    I in(SingularAttribute attr, Collection<?> value);

    /**
     * 添加"in"条件
     *
     * @param name 属性名
     * @param value 值
     * @return this
     */
    I in(String name, Object[] value);

    I in(SingularAttribute attr, Object[] value);

    /**
     * 添加"null"条件
     *
     * @param name 属性名
     * @return this
     */
    I isNull(String name);

    I isNull(SingularAttribute attr);

    /**
     * 添加"notNull"条件
     *
     * @param name 属性名
     * @return this
     */
    I isNotNull(String name);

    I isNotNull(SingularAttribute attr);

    I and();
    
    I and(Criteriaable x);

    I or(Criteriaable x);

    I or();

    I not(Criteriaable entityQuery);

    /**
     * 递增排序
     *
     * @param name
     * @return this
     */
    I orderAsc(String name);

    I orderAsc(SingularAttribute attr);

    /**
     * 递减排序
     *
     * @param name
     * @return this
     */
    I orderDesc(String name);

    I orderDesc(SingularAttribute attr);
}
