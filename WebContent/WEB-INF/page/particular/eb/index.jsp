<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>企业基本信息</title>	
		<s:include value="../../taglibs.jsp"/>
		<script type="text/javascript">
		$(function(){
			ewcmsBOBJ = new EwcmsBase();
			ewcmsBOBJ.setQueryURL('<s:url namespace="/particular/eb" action="query"><s:param name="channelId" value="channelId"></s:param></s:url>');
			
			ewcmsBOBJ.setWinWidth(1050);
			ewcmsBOBJ.setWinHeight(600);
			
			ewcmsBOBJ.openDataGrid('#tt',{
                columns:[[
                        {field:'id',title:'编号',hidden:true},
						{field:'yyzzzch',title:'营业执照注册号',width:150,sortable:true},
		                {field:'name',title:'企业名称',width:200},
		                {field:'published',title:'发布时间',width:85},
		                {field:'yyzzdjjg',title:'营业执照登记机关',width:200},
		                {field:'frdb',title:'法人代表',width:300},
		                {field:'clrq',title:'成立日期',width:200},
		                {field:'jyfw',title:'经营范围',width:60},
		                {field:'zzjgdjjg',title:'组织机构登记机关',width:100},
		                {field:'zzjgdm',title:'组织机构代码',width:200},
		                {field:'qyrx',title:'企业类型',width:80,},
		                {field:'zzzb',title:'注册资本',width:80},
		                {field:'sjzzzb',title:'实缴注册资本',width:80},
		                {field:'jyqx',title:'经营期限',width:120},
		                {field:'zs',title:'住所',width:120},
		                {field:'denseDescription',title:'所属密级',width:100}
                  ]]
			});

			ewcmsOOBJ = new EwcmsOperate();
			ewcmsOOBJ.setQueryURL(ewcmsBOBJ.getQueryURL());
			ewcmsOOBJ.setInputURL('<s:url namespace="/particular/eb" action="input"><s:param name="channelId" value="channelId"></s:param></s:url>');
			ewcmsOOBJ.setDeleteURL('<s:url namespace="/particular/eb" action="delete"><s:param name="channelId" value="channelId"></s:param></s:url>');
		});
		</script>		
	</head>
	<body class="easyui-layout">
		<s:hidden id="channelId" name="channelId"/>
		<div region="center" style="padding:2px;" border="false">
	 		<table id="tt" fit="true"></table>	
	 	</div>
        <div id="edit-window" class="easyui-window" closed="true" icon="icon-winedit" title="&nbsp;企业基本信息" style="display:none;">
            <div class="easyui-layout" fit="true">
                <div region="center" border="false">
                   <iframe id="editifr"  name="editifr" class="editifr" frameborder="0" onload="iframeFitHeight(this);" scrolling="no"></iframe>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-save" href="javascript:void(0)" onclick="saveOperator()">保存</a>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)" onclick="javascript:$('#edit-window').window('close');">取消</a>
                </div>
            </div>
        </div>	
        <div id="query-window" class="easyui-window" closed="true" icon='icon-search' title="查询"  style="display:none;">
            <div class="easyui-layout" fit="true"  >
                <div region="center" border="false" >
                <form id="queryform">
                	<table class="formtable">
                            <tr>
                                <td class="tdtitle">组织机构代码：</td>
                                <td class="tdinput">
                                    <input type="text" id="code" name="code" class="inputtext" maxLength="9" size="9"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="tdtitle">机关单位名称：</td>
                                <td class="tdinput">
                                    <input type="text" id="name" name="name" class="inputtext" size="60"/>
                                </td>
                            </tr>
               		</table>
               	</form>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" onclick="querySearch('');">查询</a>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)" onclick="javascript:$('#query-window').window('close');">取消</a>
                </div>
            </div>
        </div>      	
	</body>
</html>