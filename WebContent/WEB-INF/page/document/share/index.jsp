<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>站点共享文章</title>	
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
		<link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>
		<script type="text/javascript" src='<s:url value="/source/js/jquery-1.4.2.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/easyui-lang-zh_CN.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/ewcms.js"/>'></script>
		<script>
			$(function(){
				//基本变量初始
				setGlobaVariable({
					inputURL:'<s:url action="input"/>',
					queryURL:'<s:url action="query"/>',
					deleteURL:'<s:url action="delete"/>',
					defaultWidth:300
				});
				//数据表格定义 						
				openDataGrid({
					columns:[[
							 {field:'id',title:'文章序号',width:120,sortable:true,align:'center'},
			                 {field:'articleTitle',title:'文章标题',width:120,align:'center'},
			                 {field:'channelName',title:'所属频道',width:120,align:'center'}
			        ]],
					toolbar:[
								{text:'转至',iconCls:'icon-add',handler:chooseChannel},'-',
								{text:'删除',iconCls:'icon-remove', handler:delOperateBack},'-',
								{text:'查询',iconCls:'icon-search', handler:queryOperateBack},'-',
								{text:'缺省查询',iconCls:'icon-back', handler:initOperateQueryBack}
					]
				});
			});

			function chooseChannel(){
				var ids = getSelectRow();
				if(ids == '')return;				
				//频道目录树初始
				$(function(){
					$('#channeltt').tree({
						checkbox: true,
						url: '<s:url value="/site/channel/tree.do"/>',
						cascadeCheck:false
					});
				});	
				openWindow("#channel-window",{width:280,height:400,title:"选择转至频道"});			
			}
			
			function toChannel(){
				var checkeds = $('#channeltt').tree('getChecked');
				if(checkeds.length == 0){
					$.messager.alert('提示','请选择转至频道');
					return;
				}				
	    		$.messager.confirm("提示","确定将所选文章转至所选频道中吗?",function(r){
	    			if (r){
	    				var ids = getSelectRow();//文章id集
	    	            for(var i=0;i<checkeds.length;++i){//站点集
	    	            	ids =ids + 'channelIds=' + checkeds[i].id +'&';
	    	            }							
	    	            
			            $.post('<s:url action="tochannel"/>',ids,function(data){
				            if(data=='false'){
				            	$.messager.alert('提示','数据转至失败');
				            }else{
				            	$("#channel-window").window("close");
				            	$.messager.alert('提示','数据转至成功');
				            }
			            });	
	    			}
	    		});
			}
			
			function getSelectRow(){
	            var rows = $("#tt").datagrid('getSelections');
	            if(rows.length == 0){
	            	$.messager.alert('提示','请选择要转至记录');
	            	return '' ;
	            }
	            var ids = '';
	            for(var i=0;i<rows.length;++i){
	            	ids =ids + 'selections=' + rows[i].id +'&';
	            }	
	            return ids;			
			}						
		</script>		
	</head>
	<body class="easyui-layout">
		<div region="center" style="padding:2px;" border="false">
	 		<table id="tt" fit="true"></table>	
	 	</div>
  
        <div id="query-window" class="easyui-window" closed="true" icon='icon-search' title="查询"  style="display:none;">
            <div class="easyui-layout" fit="true"  >
                <div region="center" border="false" >
                <form id="queryform">
                	<table class="formtable">
                            <tr>
                                <td class="tdtitle">文章编号：</td>
                                <td class="tdinput">
                                    <input type="text" id="id" name="id" class="inputtext"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="tdtitle">文章标题：</td>
                                <td class="tdinput">
                                    <input type="text" id="title" name="title" class="inputtext"/>
                                </td>
                            </tr>                            
                            <tr>
                                <td class="tdtitle">频道名称：</td>
                                <td class="tdinput">
                                    <input type="text" id="name" name="name" class="inputtext"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="tdtitle">已转至：</td>
                                <td class="tdinput">
                                    <input type="checkbox" value="1" id="refed" name="refed" class="inputtext"/>
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
        <div id="channel-window" class="easyui-window" closed="true"   style="display:none;overflow:hidden;">
            <div class="easyui-layout" fit="true" >
                <div region="center" border="false">
                	<ul  id="channeltt"></ul>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)"  onclick="javascript:toChannel();">确定</a>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)"  onclick='javascript:$("#channel-window").window("close");'>取消</a>
                </div>
            </div>
        </div>	     	
	</body>
</html>