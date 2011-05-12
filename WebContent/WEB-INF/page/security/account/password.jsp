<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>修改密码</title>
    <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'/>
    <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
    <link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>
    <script type="text/javascript" src='<s:url value="/source/js/jquery-1.4.2.min.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>
    <style type="text/css">
        .newpassowrd td{padding: 1px 3px;border-style: none;}
        .inputfloat {float:left;margin:0 0 0 10px; }
        .passW {float:left;position:relative;margin-top:4px;}
        .passW_w {display:block;width:48px;height:6px; overflow:hidden; border:1px solid #d0d0d0;}
        .passW_t {position:absolute;top:11px;left:20px;font-family:"宋体";color:#666;}
        .passW_curr .passW_t{color: red;}
        .passW_curr .passW_w{background-color:#3300ff;}
    </style>
    <script type="text/javascript">
        $(function(){
            <s:if test="closeWindow">
            parent.closeWindow();
            </s:if>
            <s:else>
            init();
            <s:if test="hasActionErrors()">
            <s:iterator value="actionErrors">  
            $.messager.alert('错误','<s:property escape="false"/>\n');
            </s:iterator>  
            </s:if>
            </s:else>
           
        });
        function init(){
            $('input[name=newPassword]').bind('keyup',function(){
                $('.inputfloat div').removeClass('passW_curr');
                var val = $(this).val();
                var rate = 1;
                if(val.length == 0){
                    rate = 0;
                    return;
                }
                if(val.length > 5){
                    if(!(/^[a-z]*$/.test(val)
                        || /^[0-9]*$/.test(val)
                        || /^[A-Z]*$/.test(val))){
                        rate++;
                    }
                    if(val.length > 9){
                        rate ++;
                    }
                }
                $('#passwordRate'+rate).addClass('passW_curr');
            }); 
        }
        function pageSubmit(){
            $('form').submit();
        }
    </script>
</head>
<body>
    <s:form action="savePassword" method="post">
    <table class="formtable">
        <tr>
            <td width="70px">现有密码:</td>
            <td class="formFieldError">
                <s:password name="password" cssClass="inputtext"/>
                <s:fielderror ><s:param value="%{'password'}"/></s:fielderror>
            </td>
	</tr>
	<tr>
	    <td width="70px">新密码:</td>
	    <td>
                <table class="newpassowrd">
                    <tr>
                        <td>
                            <div class="inputfloat">
                                <div id="passwordRate1" class="passW">
                                    <div class="passW_w"></div>
                                    <div class="passW_t">弱</div>
                                </div>
                                <div id="passwordRate2" class="passW">
                                    <div class="passW_w"></div>
                                    <div class="passW_t">中</div>
                                </div>
                                <div id="passwordRate3" class="passW">
                                     <div class="passW_w"></div>
                                     <div class="passW_t">强</div>
                                </div>
                            </div>
                            <div style="clear: both; height: 18px;"></div>
                        </td>
                    </tr>
                    <tr>
                        <td class="formFieldError">
                            <s:password name="newPassword" cssClass="inputtext"/>
                            <s:fielderror ><s:param value="%{'newPassword'}"/></s:fielderror>
                        </td>
                    </tr>
                    <tr>
                        <td><span class="inputtxt">6-16位字符，可以是半角字母、数字、“.”、“-”、“?”和下划线</span></td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td width="70px">确认密码:</td>
            <td class="formFieldError">
                <s:password name="againPassword" cssClass="inputtext"/>
                <s:fielderror ><s:param value="%{'againPassword'}"/></s:fielderror>
            </td>
        </tr>
    </table>
    </s:form>
</body>
</html>