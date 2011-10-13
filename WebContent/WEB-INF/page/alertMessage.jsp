<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<s:if test="hasActionErrors()">
   <s:iterator value="actionErrors">  
   $.messager.alert('错误','<s:property escape="false"/>','error');
   </s:iterator>  
</s:if>
<s:if test="hasActionMessages()">
   <s:iterator value="actionMessages">  
   $.messager.alert('提示','<s:property escape="false"/>','info');
   </s:iterator>  
</s:if>