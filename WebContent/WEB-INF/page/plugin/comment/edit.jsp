<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="ewcms" uri="/ewcms-tags"%>

<html>
    <head>
        <title>文章评论</title>
		<s:include value="../../taglibs.jsp"/>
		<script type="text/javascript" src="<s:url value='/ewcmssource/tiny_mce/tiny_mce.js'/>"></script>
		<script type="text/javascript" src="<s:url value='/ewcmssource/tiny_mce/config_simple.js'/>"></script>
        <script type="text/javascript">
            //机构目录树初始
            $(function(){
            	<s:include value="../../alertMessage.jsp"/>
            	
            	if ($('#id').val()==""){
        			parent.closeWindow();
        		}
                $('form textarea').bind("focusin",function(){
                    $("#organ_top_id").css("display","none");
                });
            });
            <s:if test="success">
    			parent.queryInteractionSearch('');
			</s:if>
        </script>
        <ewcms:datepickerhead></ewcms:datepickerhead>
    </head>
    <body>
        <s:form namespace="/plugin/comment" action="edit" method="post">
            <table class="formtable" align="center">
                <tr>
                    <td width="10%">文章编号：</td>
                    <td width="40%">
                        <s:property value="comment.articleId"/>
                    </td>
                    <td width="10%">呢称：</td>
                    <td width="40%">
                        <s:property value="comment.username"/>
                    </td>
                </tr>
                <tr>
                    <td>性别：</td>
                    <td><s:property value="comment.sexDescription"/></td>
                    <td>年龄：</td>
                    <td><s:property value="comment.age"/></td>
                </tr>
                <tr>
                    <td>文化程度：</td>
                    <td><s:property value="comment.educationDescription"/></td>
                    <td>职业：</td>
                    <td><s:property value="comment.profession"/></td>
                </tr>
                <tr>
                	<td width="10%">提问日期：</td>
                	<td width="40%">
                		<ewcms:datepicker id="date" name="date" option="inputsimple" format="yyyy-MM-dd"/>
                	</td>
                	 <td width="10%">发布：</td>
                    <td width="40%">
                        <s:radio name="checked" list="#{'true':'通过','false':'未通过'}"/>
                    </td>
                <tr>
                    <td height="100">内容：</td>
                    <td colspan="3">
                    	<s:textarea name="content" class="mceEditor" style="height:130px;width:700px"/>
                        <!-- <div style="height:40;overflow: auto;"><s:property value="interaction.content"/></div> -->
                    </td>
                </tr>
            </table>
            <s:hidden id="id" name="id"/>
            <s:hidden name="update" value="true"/>
        </s:form>
        <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
             <a class="easyui-linkbutton" icon="icon-save" href="javascript:void(0)" onclick="document.forms[0].submit();">保存</a>
        </div>
    </body>
</html>