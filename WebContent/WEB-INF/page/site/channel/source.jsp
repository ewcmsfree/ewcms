<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>专栏资源管理</title>
		<s:include value="../../taglibs.jsp"/>
		<script>
			$(function(){
				//创建和设置页面的基本对象 EwcmsBase
				ewcmsBOBJ = new EwcmsBase();
				ewcmsBOBJ.setQueryURL('<s:url action="query" namespace="/site/template/source"/>?channelId=<s:property value="channelVo.id"/>');
				ewcmsBOBJ.openDataGrid('#tt',{
					columns:[[
								 {field:'id',title:'编号',width:50,sortable:true,align:'center'},
				                 {field:'path',title:'资源路径',width:300,align:'left'},
				                 {field:'describe',title:'说明',width:120,align:'left'},
				                 {field:'parentId',title:'编辑',width:60,align:'center',formatter:function(val,rec){
				                	if (rec.path.search(/.js/)>-1 || rec.path.search(/.htm*/)>-1){
										return '<input type="button" name="Submit" value="编  辑" class="inputbutton" style="height:18px" onClick="editTPL('+rec.id+');">';
				                	}
								 }}
				    ]]
				});

				//创建和设置页面的操作对象 EwcmsOperate
				ewcmsOOBJ = new EwcmsOperate();
				ewcmsOOBJ.setQueryURL(ewcmsBOBJ.getQueryURL());
				ewcmsOOBJ.setInputURL('<s:url action="input" namespace="/site/template/source"/>?sourceVo.channelId=<s:property value="channelVo.id"/>');
				ewcmsOOBJ.setDeleteURL('<s:url action="delete" namespace="/site/template/source"/>');
			});
			
			function editTPL(idValue){
				$("#editifr_pop").attr("src",'<s:url action="editContent" namespace="/site/template/source"/>?sourceVo.id='+idValue);
				ewcmsBOBJ.openWindow("#pop-window",{width:800,height:455,title:"资源编辑"});
				//top.addTab('资源编辑','<s:url action="editContent" namespace="/site/template/source"/>?sourceVo.id='+idValue);
			}
			
		</script>
	</head>
	<body class="easyui-layout">
		<div region="center" style="padding:2px;" border="false">
	 		<table id="tt" fit="true"></table>	
	 	</div>
	 	
        <div id="edit-window" class="easyui-window" closed="true" icon="icon-winedit" title="&nbsp;资源编辑" style="display:none;">
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
		<div id="pop-window" class="easyui-window" title="弹出窗口" icon="icon-save" closed="true" style="display:none;">
            <div class="easyui-layout" fit="true">
                <div region="center" border="false">
                	<iframe id="editifr_pop"  name="editifr_pop" class="editifr" frameborder="0" width="100%" height="100%" scrolling="no"></iframe>
                </div>
            </div>
        </div>
	</body>
</html>