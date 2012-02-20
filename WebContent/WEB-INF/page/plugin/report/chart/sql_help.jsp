<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>SQL语句使用参数说明</title>
		<s:include value="../../../taglibs.jsp"/>
	</head>
	<body>
		<font color="#0066FF"><b>SQL语句使用方式</b></font><p/>
		<b>1.概述</b>
		<ul>
			按照正常的SQL语句书写，在条件语句中可以定义参数，而所定义的参数将作为用户输入的查询条件进行查询。
		</ul>
		<b>2.查询条件格式</b>
		<ul>
			例如：<br/>
			Select nandu,shuliang From test Where nandu=$p{name=nandu|class=java.lang.Long|des=|dv=2009}<br/>
			其中$p{...}就是自定义的参数，需要接照格式书写<br/>
		</ul>
		<b>3.参数内说明</b>
		<ul>
			<li>name:参数名称(用户可定义任意字符串)</li>
			<li>class:参数类型对象(需全路径)</li>
			<li>des:参数说明</li>
			<li>dv:参数默认值</li>
		</ul>
	</body>
</html>