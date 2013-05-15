<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="ewcms" uri="/ewcms-tags"%>

<html>
    <head>
        <title>文章评论审核</title>
		<s:include value="../../taglibs.jsp"/>
		<ewcms:datepickerhead></ewcms:datepickerhead>
        <script>
        	var datagridId='#tt';
			var queryWinID="#query-window";
            $(function(){
				//创建和设置页面的基本对象 EwcmsBase
				ewcmsBOBJ = new EwcmsBase();
				ewcmsBOBJ.setQueryURL('<s:url namespace="/plugin/comment" action="query"/>'); 
            	ewcmsBOBJ.delToolItem('新增');
              	ewcmsBOBJ.delToolItem('修改');
              	ewcmsBOBJ.delToolItem('删除');
              	
              	ewcmsBOBJ.addToolItem('删除','icon-remove',deleteOperate);
                //数据表格定义
              	ewcmsBOBJ.openDataGrid('#tt',{
                    singleSelect:true,
                    columns:[[
                            {field:'id',title:'序号',width:40},
                            {field:'articleId', title:'文章编号',width:100},
                            {field:'username',title:'呢称',width:100},
                            {field:'sexDescription',title:'性别',width:60},
                            {field:'age',title:'年龄',width:60},
                            {field:'educationDescription',title:'文化程度',width:100},
                            {field:'profession',title:'职业',width:100},
                            {field:'checked',title:'审核',width:60,
                                formatter:function(val,rec){
                                    if (val){
                                        return '通过';
                                    }else{
                                        return '';
                                    }
                                }
                            },
                            {field:'content',title:'内容',width:400},
                            {field:'date',title:'提问日期',width:120}
                        ]]
                });
				ewcmsOOBJ = new EwcmsOperate();
				ewcmsOOBJ.setQueryURL(ewcmsBOBJ.getQueryURL());
				ewcmsOOBJ.setInputURL('<s:url namespace="/plugin/comment" action="input"/>');
                $('#tt').datagrid({
                  onDblClickRow:function(rowIndex, rowData){
                      var url = '<s:url namespace="/plugin/comment" action="edit"/>?id='+rowData.id;
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
        	            $.post('<s:url namespace="/plugin/comment" action="delete"/>',ids,function(data){          	
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
                                <td class="tdtitle">呢称：</td>
                                <td class="tdinput"><input type="text" id="username" name="username" class="inputtext"/>
                                <td class="tdtitle">审核：</td>
                                <td class="tdinput">
                                    <input type="radio" id="checked_id" name="checked" value="0"/>所有
                                    <input type="radio" id="checked_id" name="checked" value="1"/>通过&nbsp;&nbsp;
                                    <input type="radio" id="checked_id" name="checked" value="2"/>未通过&nbsp;&nbsp;
                                </td>
                            </tr>
                            <tr>
                                <td class="tdtitle">性别：</td>
                                <td class="tdinput"><s:select list="@com.ewcms.plugin.comment.model.Comment$Sex@values()" listValue="description" name="sex" id="sex" headerKey="-1" headerValue="------请选择------"></s:select></td>
                                <td class="tdtitle">年龄：</td>
                                <td class="tdinput"><input type="text" id="ageStart" name="ageStart" size="3"/>&nbsp;至&nbsp;<input type="text" id="ageEnd" name="ageEnd" size="3"/>
                            </tr>
                            <tr>
                                <td class="tdtitle">文化程度：</td>
                                <td class="tdinput"><s:select list="@com.ewcms.plugin.comment.model.Comment$Education@values()" listValue="description" name="education" id="education" headerKey="-1" headerValue="------请选择------"></s:select></td>
                                <td class="tdtitle">职业：</td>
                                <td class="tdinput"><input type="text" id="profession" name="profession" class="inputtext"/></td>
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