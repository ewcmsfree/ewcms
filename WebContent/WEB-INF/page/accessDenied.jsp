<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>

<html>
    <head>
        <title>访问拒绝</title>
        <style type="text/css">
        h1 {font-size: 120%}
        p {font-size: 13px;font-weight: bold;}
        ul li {font-size: 12px}
        em {font-family: sans-serif;font-style: normal;color:red;}
        </style>
    </head>
    <body>
        <h1>访问拒绝</h1>
        <p>访问指定的资源被拒绝，拒绝原因可能如下：</p>
        <ul>
            <li><em>修改用户信息</em>和<em>修改密码</em>需要登录后设置，请<s:a value="/logout.do">退出系统</s:a>，登录后再设置。</li>
            <li>你的权限不能访问该资源，请与系统管理员联系。</li>
        </ul>
        <!-- 
        <blockquote>
           <pre><s:property value="errorTrace"/></pre>
        </blockquote>
       -->
</body>
</html>
