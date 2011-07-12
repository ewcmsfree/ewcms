<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>专栏模板管理</title>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
		<link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>
		<script type="text/javascript" src='<s:url value="/source/js/jquery-1.4.2.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/easyui-lang-zh_CN.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/ewcms.base.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/ewcms.func.js"/>'></script>		
		<script type="text/javascript"> 
			var tpltreeURL = '<s:url value="/site/template/tree.do" />';
			function browseTPL(){
				openWindow("#template-window");
			}

			function selectTPL(){
				var node = $('#tt2').tree('getSelected');
    	    	if(node == null || typeof(node) == 'undefined' || node.iconCls != "")
    	    	{
    	    		$.messager.alert('提示','请选择模板文件');
    	    		return false;
    	    	}
	            $.post('<s:url action="importtpl"/>',{'channelVo.id':node.id},function(data){
		            if(data == 'false'){
	    	    		$.messager.alert('提示','模板导入失败');
	    	    		return;
		            }
		            defQueryCallBack();
		            closeTPL();
	    	    }); 	    	    					
			}	
			
						
			$(function(){
				//公共模板目录树初始	
				$('#tt2').tree({
					checkbox: false,
					url: tpltreeURL
				});
								
				//创建和设置页面的基本对象 EwcmsBase`
				ewcmsBOBJ = new EwcmsBase();
				ewcmsBOBJ.setQueryURL('<s:url action="query" namespace="/site/template"/>?parameters["channelId"]=<s:property value="channelVo.id"/>');
				ewcmsBOBJ.addToolItem(("导入","icon-print","browseTPL"));
				ewcmsBOBJ.openDataGrid('#tt',{
					columns:[[
								 {field:'id',title:'编号',width:50,sortable:true,align:'center'},
				                 {field:'path',title:'模板路径',width:300,align:'left'},
				                 {field:'describe',title:'说明',width:120,align:'left'},
				                 {field:'parentId',title:'编辑',width:60,align:'center',formatter:function(val,rec){
									return '<input type="button" name="Submit" value="编辑.." class="inputbutton" onClick="editTPL('+rec.id+');">';
								 }}
				    ]]
				});
				
				//创建和设置页面的操作对象 EwcmsOperate
				ewcmsOOBJ = new EwcmsOperate();
				ewcmsOOBJ.setQueryURL(ewcmsBOBJ.getQueryURL());
				ewcmsOOBJ.setInputURL('<input type="button" name="Submit" value="编辑.." class="inputbutton" onClick="editTPL('+rec.id+');">');
				ewcmsOOBJ.setDeleteURL('<s:url action="delete" namespace="/site/template"/>');
			});			
			function editTPL(idValue){
				top.addTab('模板编辑','<s:url action="editContent" namespace="/site/template"/>?templateVo.id='+idValue);
			}			
		</script>		
	</head>
	<body class="easyui-layout">
		<div region="center" style="padding:2px;" border="false">
	 		<table id="tt" fit="true"></table>	
	 	</div>
	 	
        <div id="edit-window" class="easyui-window" closed="true" icon="icon-winedit" title="&nbsp;模板编辑" style="display:none;">
            <div class="easyui-layout" fit="true">
                <div region="center" border="false">
                   <iframe id="editifr"  name="editifr" class="editifr" frameborder="0" onload="iframeFitHeight(this);" scrolling="no"></iframe>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-save" href="javascript:void(0)" onclick="saveOperator()">保存</a>
                </div>
            </div>
        </div>	
        
        <div id="query-window" class="easyui-window" closed="true" icon='icon-search' title="查询"  style="display:none;">
            <div class="easyui-layout" fit="true"  >
                <div region="center" border="false" >
                <form id="queryform">
                	<table class="formtable">
                            <tr>
                                <td class="tdtitle">编号：</td>
                                <td class="tdinput">
                                    <input type="text" id="id" name="id" class="inputtext"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="tdtitle">名称：</td>
                                <td class="tdinput">
                                    <input type="text" id="name" name="name" class="inputtext"/>
                                </td>
                            </tr>                            
               		</table>
               	</form>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" onclick="querySearch('');">查询</a>
                </div>
            </div>
        </div>
        
        <div id="template-window" class="easyui-window" closed="true"   style="display:none;overflow:hidden;">
            <div class="easyui-layout" fit="true" >
                <div region="center" border="false">
                	<ul  id="tt2"></ul>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)"  onclick="javascript:selectTPL();">确定</a>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)"  onclick="javascript:closeWindow('#template-window');">取消</a>
                </div>
            </div>
        </div> 
        
        <div id="settplproperty-window" class="easyui-window" closed="true"   style="display:none;overflow:hidden;">
            <div class="easyui-layout" fit="true" >
                <div region="center" border="false">
					<table class="formtable" align="center">
						<tr>
							<td>模板类型：</td>
							<td>
								<s:textfield name="templateVo.type"  readonly="true" cssClass="inputdisabled" size="50"/>
							</td>
						</tr>
						<tr>
							<td>路径生成规则：</td>
							<td >
								<s:textfield name="templateVo.uriPattern" cssClass="inputtext"/>
							</td>				
						</tr>
					</table>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)"  onclick="javascript:setTPL();">确定</a>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)"  onclick='javascript:closeWindow("#settplproperty-window");'>取消</a>
                </div>
            </div>
        </div>               
	</body>
</html>