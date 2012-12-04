<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="ewcms" uri="/ewcms-tags"%>

<html>
    <head>
        <title>网上咨询回复</title>
		<s:include value="../../../taglibs.jsp"/>
		<script type="text/javascript" src="<s:url value='/ewcmssource/tiny_mce/tiny_mce.js'/>"></script>
		<script type="text/javascript" src="<s:url value='/ewcmssource/tiny_mce/config_simple.js'/>"></script>
        <script type="text/javascript">
	        $(function(){
	        	<s:include value="../../../alertMessage.jsp"/>
	        });
	        <s:if test="success">
        		parent.queryAdvisorSearch('');
    		</s:if>
        </script>
        <ewcms:datepickerhead></ewcms:datepickerhead>
    </head>
    <body>
        <s:form action="edit" method="post">
            <table class="formtable" align="center">
                <tr>
                    <td width="100">编号：</td>
                    <td>
                        <s:property value="advisor.id"/>
                    </td>
                    <td>用户：</td>
                    <td>
                        <s:property value="advisor.name"/>
                    </td>
                </tr>
                <tr>
                    <td>标题：</td>
                    <td colspan="3">
                    	<s:textfield id="title" name="title" size="60"></s:textfield>
                        <!--<s:property value="advisor.title"/>-->
                    </td>
                </tr>
                <tr>
                	<td>提问日期：</td>
                	<td colspan="3">
                		<ewcms:datepicker id="date" name="date" option="inputsimple" format="yyyy-MM-dd"/>
                	</td>
                </tr>
                <tr>
                    <td>咨询：</td>
                    <td colspan="3">
                        <s:property value="advisor.matter.name"/>
                    </td>
                </tr>
                <tr>
                    <td>咨询单位：</td>
                    <td colspan="3">
                        <s:property value="advisor.organ.name"/>
                    </td>
                </tr>
                <tr>
                    <td height="100">内容：</td>
                    <td colspan="3">
                    	<s:textarea name="content" class="mceEditor" style="height:130px;width:700px"/>
                        <!--  <div style="height:40;overflow: auto;"><s:property value="advisor.content"/></div>-->
                    </td>
                </tr>
                <tr>
                    <td>回复内容：</td>
                    <td colspan="3">
                        <s:textarea name="replay" class="mceEditor" style="height:130px;width:700px"/>
                    </td>
                </tr>
                <tr>
                	<td>回复日期：</td>
                	<td colspan="3">
                		<ewcms:datepicker id="replayDate" name="replayDate" option="inputsimple" format="yyyy-MM-dd"/>
                	</td>
                </tr>
                <tr>
                    <td>发布：</td>
                    <td colspan="3">
                        <s:radio name="checked" list="#{'true':'通过','false':'未通过'}"/>
                    </td>
                </tr>
            </table>
            <s:hidden id="id" name="id"/>
        </s:form>
        <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
             <a class="easyui-linkbutton" icon="icon-save" href="javascript:void(0)" onclick="document.forms[0].submit();">保存</a>
        </div>
    </body>
</html>