<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="ewcms" uri="/ewcms-tags"%>

<html>
    <head>
        <title>投稿审核</title>
		<s:include value="../../taglibs.jsp"/>
		<ewcms:datepickerhead></ewcms:datepickerhead>
        <script>
        	var datagridId='#tt';
			var queryWinID="#query-window";
            $(function(){
				//创建和设置页面的基本对象 EwcmsBase
				ewcmsBOBJ = new EwcmsBase();
				ewcmsBOBJ.setQueryURL('<s:url namespace="/plugin/contribute" action="query"/>'); 
            	ewcmsBOBJ.delToolItem('新增');
              	ewcmsBOBJ.delToolItem('修改');
              	ewcmsBOBJ.delToolItem('删除');
              	
              	ewcmsBOBJ.addToolItem('删除','icon-remove',deleteOperate);
                //数据表格定义
              	ewcmsBOBJ.openDataGrid('#tt',{
                    singleSelect:true,
                    columns:[[
                            {field:'id',title:'序号',width:40},
                            {field:'username',title:'姓名',width:100},
                            {field:'title',title:'标题',width:200},
                            {field:'content',title:'内容',width:400},
                            {field:'date',title:'提问日期',width:120},
                            {field:'email',title:'电子邮件',width:100},
                            {field:'phone',title:'联系方式',width:100}
                        ]]
                });
				ewcmsOOBJ = new EwcmsOperate();
				ewcmsOOBJ.setQueryURL(ewcmsBOBJ.getQueryURL());
				ewcmsOOBJ.setInputURL('<s:url namespace="/plugin/contribute" action="input"/>');
                $('#tt').datagrid({
                  onDblClickRow:function(rowIndex, rowData){
                      var url = '<s:url namespace="/plugin/contribute" action="edit"/>?id='+rowData.id;
                      //$("#editifr").attr('src',url);
                      //openWindow1('#edit-window',{height:380,width:600});
                      openWindow1({title:'文章评论审核', url:url, width:800, height:350})
                  }
                });
            });
            
            function deleteOperate(){
        	    var rows = $('#tt').datagrid('getSelections');
        	    if(rows.length == 0){
        	        $.messager.alert('提示','请选择删除记录','info');
        	        return ;
        	    }
        	    var ids = '';
        	    for(var i=0;i<rows.length;++i){
        	        ids =ids + 'selections=' + rows[i].id +'&';
        	    }
        	    $.messager.confirm("提示","确定要删除所选记录吗?",function(r){
        	        if (r){
        	            $.post('<s:url namespace="/plugin/contribute" action="delete"/>',ids,function(data){          	
        	            	$.messager.alert('成功','删除成功','info');
        	            	$('#tt').datagrid('clearSelections');
        	                $('#tt').datagrid('reload');              	
        	            });
        	        }
        	    });
            }
        </script>
    </head>
    <body class="easyui-layout">
        <div region="center" style="padding:2px;" border="false">
            <table id="tt" fit="true"></table>
        </div>
        <div id="edit-window" class="easyui-window" closed="true" icon="icon-winedit" title="&nbsp;" style="display:none;">
            <div class="easyui-layout" fit="true">
                <div region="center" border="false">
                    <iframe id="editifr"  name="editifr" class="editifr" frameborder="0" onload="iframeFitHeight(this);" scrolling="no"></iframe>
                </div>
            </div>
        </div>

        <div id="query-window" class="easyui-window" closed="true" icon='icon-search' title="查询"  style="display:none;">
            <div class="easyui-layout" fit="true">
                <div region="center" border="false">
                    <form id="queryform">
                        <table class="formtable">
                            <tr>
                                <td class="tdtitle">文章编号：</td>
                                <td class="tdinput"><input type="text" id="articleId" name="articleId" class="inputtext"/></td>
                                <td class="tdtitle">内容：</td>
                                <td class="tdinput">
                                    <input type="text" id="content_id" name="content" class="inputtext"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="tdtitle">姓名：</td>
                                <td class="tdinput"><input type="text" id="username" name="username" class="inputtext"/>
                                <td class="tdtitle">审核：</td>
                                <td class="tdinput">
                                    <input type="radio" id="checked_id" name="checked" value="0"/>所有
                                    <input type="radio" id="checked_id" name="checked" value="1"/>通过&nbsp;&nbsp;
                                    <input type="radio" id="checked_id" name="checked" value="2"/>未通过&nbsp;&nbsp;
                                </td>
                            </tr>
                            <tr>
                                <td class="tdtitle">电子邮件：</td>
                                <td class="tdinput"><input type="text" id="email" name="email" class="inputtext"/></td>
                                <td class="tdtitle">联系方式</td>
                                <td class="tdinput"><input type="text" id="phone" name="phone" class="inputtext"/></td>
                            </tr>
                            <tr>
                                <td class="tdtitle">标题：</td>
                                <td class="tdinput" colspan="3"><input type="text" id="title" name="title" class="inputtext"/></td>
                            </tr>
                            <tr>
                                <td class="tdtitle">提问时间：</td>
                                <td class="tdinput" colspan="3">
                                  <ewcms:datepicker id="dateStart" name="dateStart" option="inputsimple" format="yyyy-MM-dd HH:mm:ss"/>
                                  &nbsp;至&nbsp;
                                  <ewcms:datepicker id="dateEnd" name="dateEnd" option="inputsimple" format="yyyy-MM-dd HH:mm:ss"/></td>
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