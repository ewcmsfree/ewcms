<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="ewcms" uri="/ewcms-tags"%>

<html>
    <head>
        <title>互动信息</title>
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
                $('#organ_tree').tree({
                    checkbox: false,
                    url: '<s:url action="tree" namespace="/site/organ"/>',
                    onClick:function(node){
                        $('#organ_id_id').val(node.id);
                        $('#organ_name_id').val(node.text);
                    }
                });
                $('#organ_name_id').bind("focusin",function(){
                    $("#organ_top_id").css("display","block");
                });
                $('form textarea').bind("focusin",function(){
                    $("#organ_top_id").css("display","none");
                });
                $("#organ_button_id").bind('click',function(){
                    var display = $("#organ_top_id").css("display");
                    if(display == "block"){
                        $("#organ_top_id").css("display","none");
                    }else{
                        $("#organ_top_id").css("display","block");
                    }
                });
            });
            <s:if test="success">
    			parent.queryInteractionSearch('');
			</s:if>
        </script>
        <style type="text/css">
            .organ_width {width:250px;}
            .organ_top {border: 1px #a9c9e2 solid;background: #FFFFFF;position:absolute;display: none;height:200px;overflow: auto;}
        </style>
        <ewcms:datepickerhead></ewcms:datepickerhead>
    </head>
    <body>
        <s:form namespace="/plguin/interaction" action="edit" method="post">
            <table class="formtable" align="center">
                <tr>
                    <td width="100">编号：</td>
                    <td>
                        <s:property value="interaction.id"/>
                    </td>
                </tr>
                <tr>
                    <td>标题：</td>
                    <td>
                        <s:property value="interaction.title"/>
                    </td>
                </tr>
                <tr>
                    <td>用户：</td>
                    <td>
                        <s:property value="interaction.name"/>
                    </td>
                </tr>
                <tr>
                	<td>提问日期：</td>
                	<td>
                		<ewcms:datepicker id="date" name="date" option="inputsimple" format="yyyy-MM-dd"/>
                	</td>
                </tr>
                <tr>
                    <td width="90px">提交部门：</td>
                    <td>
                        <s:hidden name="organId" id="organ_id_id"/>
                        <s:textfield id="organ_name_id" cssClass="organ_width" name="organName" readonly="true"/>
                        <input type="button" id="organ_button_id" value="部门"/>
                        <br/>
                        <div id="organ_top_id" class="organ_width organ_top">
                            <div id ="organ_tree"></div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>互动类型：</td>
                    <td>
                        <s:if test = "interaction.type == 1">咨询</s:if>
                        <s:if test = "interaction.type == 2">投诉</s:if>
                        <s:if test = "interaction.type == 3">建议</s:if>
                    </td>
                </tr>
                <tr>
                    <td height="100">内容：</td>
                    <td>
                        <div style="height:40;overflow: auto;"><s:property value="interaction.content"/></div>
                    </td>
                </tr>
                <tr>
                    <td>回复内容：</td>
                    <td>
                        <s:textarea name="replay" class="mceEditor" style="height:150px;width:700px"/>
                    </td>
                </tr>
                <tr>
                	<td>回复日期：</td>
                	<td>
                		<ewcms:datepicker id="replayDate" name="replayDate" option="inputsimple" format="yyyy-MM-dd"/>
                	</td>
                </tr>
                <tr>
                    <td>发布：</td>
                    <td>
                        <s:radio name="checked" list="#{'true':'通过','false':'未通过'}"/>
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