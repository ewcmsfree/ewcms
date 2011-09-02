<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="org.springframework.security.web.WebAttributes"%>
<%@page import="org.springframework.security.core.AuthenticationException"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
    <!-- skip exit iframe -->
    <script type="text/javascript">
        if(parent != self) {
            top.location='<s:url value="/login.do"/>';
        }
    </script>
    <head>
        <title>Ewcms用户登录</title>
        <script type="text/javascript" src='<s:url value="/source/js/jquery.min.js"/>'></script>
        <link rel="stylesheet" type="text/css" href='<s:url value="/source/css/login.css"/>'>
        <script type="text/javascript">
        function isEmpty(input) {
                if ($.trim(input.val()) == "") {
                        input.focus();
                        return true;
                } else {
                        return false;
                }
        }
        
        function errorMsg(msg) {
                $('#id_error_msg').html(msg);
        }

        function submitForm() {
                if (isEmpty($('input[name=j_username]'))) {
                        errorMsg('用户名不能为空。');
                        return;
                }
                if (isEmpty($('input[name=j_password]'))) {
                        errorMsg('密码不能为空。');
                        return;
                }
                if (isEmpty($('input[name=j_checkcode]'))) {
                        errorMsg('验证码不能为空。');
                        return;
                }
                $('form')[0].submit();
        }

        $(function() {
                $('#id_login_btn').click(function() {
                        submitForm();
                });
                $('input[name=j_checkcode]').keypress(function(event) {
                        if (event.which == '13') {
                                submitForm();
                        }
                });
                $('#id_checkcode').click(function() {
                        var url = '<s:url value = "/checkcode.jpg"/>?nocache=' + Math.random();
                        this.src = url;
                        var checkcodeInput = $('input[name=j_checkcode]');
                        checkcodeInput.val("");
                        checkcodeInput.focus();
                });
                $('input [name=j_username]').focus();
        });
        </script>
    </head>
    <body>
        <div align="center">
            <div class="main">
                <div>
                    <div class="main_icon" align="left" ><img src="<s:url value="/source/image/ewcms.png"/>" alt="ewcms.png"/><span class="main_title">内容管理系统</span></div>
                </div>
                <div class="left" align="left">
                     <div>
                        <div class="main_detail" align="left">EWCMS 的开发理念是：可更加高效生成站点，内容管理更加简单。EWCMS 具有以下特点： </div>
                    </div>
                    <div>
                        <div class="preface_icon"><img src="<s:url value="/source/image/login/login01.jpg"/>" alt="login01.jpg"/></div>
                        <div class="preface_info">方便、简单的创建门户网站，支持多站点。</div>
                    </div>
                    <div>
                        <div class="preface_icon"><img src="<s:url value="/source/image/login/login02.jpg"/>" alt="login02.jpg"/></div>
                        <div class="preface_info">更多安全保障</div>
                    </div>
                </div>
                <div class="right" align="left">
                    <s:form  action="/j_spring_security_check" method="post">
                        <div class="wapper">
                            <label class="title">用户名:</label>
                            <%String username=(String)session.getAttribute("j_username");
                            if(username== null){
                                username="";
                            }%>
                            <input type="text" name="j_username" class="input" value="<%=username%>"/>
                        </div>
                        <div class="wapper">
                            <label class="title">密码:</label>
                            <input type="password" name="j_password" class="input"/>
                        </div>
                        <div class="wapper">
                            <label class="title">&nbsp;</label>
                            <s:checkbox name="_spring_security_remember_me" id="_spring_security_remember_me" value="falses"/>
                            <label class="title" for="_spring_security_remember_me">记住我</label>
                        </div>
                        <div class="wapper">
                            <label class="title">验证码:</label>
                            <img id="id_checkcode" width="180px" src="<s:url value="/checkcode.jpg"/>" alt="checkcode.jpg" title="看不清,换一张"/>
                            <br/>
                            <label class="title">&nbsp;</label>
                            <input type="text" name="j_checkcode" class="checkcode"/>
                            <p class="inputtxt">验证码不区分大小写</p>
                        </div>
                        <div class="wapper">
                            <label class="title">&nbsp;</label>
                            <span id="id_error_msg" class="error">
                                <%
                                String error = request.getParameter("error");
                                AuthenticationException authException = null;
                                if (error != null) {
                                    authException = (AuthenticationException) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
                                    if (authException == null) {
                                        authException = (AuthenticationException) request.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
                                    }
                                }
                                String errorMsg = "";
                                if (authException != null) {
                                    errorMsg = authException.getMessage()+"。";
                                }
                                %>
                                <%=errorMsg%>
                            </span>
                        </div>
                        <div class="wapper" align="center">
                            <input type="button" class=""  id="id_login_btn" value="登录"/>
                        </div>
                    </s:form>
                </div>
            </div>
        </div>
    </body>
</html>