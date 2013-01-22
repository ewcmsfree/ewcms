<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
    <head>
        <title>政民互动</title>
		<s:include value="../../taglibs.jsp"/>
        <script>
        	var datagridId='#tt';
			var queryWinID="#query-window";
            $(function(){
				//创建和设置页面的基本对象 EwcmsBase
				ewcmsBOBJ = new EwcmsBase();
				ewcmsBOBJ.setQueryURL('<s:url namespace="/plguin/interaction" action="query"/>'); 
            	ewcmsBOBJ.delToolItem('新增');
              	ewcmsBOBJ.delToolItem('修改');
              	ewcmsBOBJ.delToolItem('删除');
                //数据表格定义
              ewcmsBOBJ.openDataGrid('#tt',{
                    singleSelect:true,
                    columns:[[
                            {field:'id',title:'序号',width:40},
                            {field:'username',title:'用户名',width:100},
                            {field:'name',title:'昵名',width:100},
                            {field:'title',title:'标题',width:400},
                            {field:'organName',title:'单位',width:200},
                            {field:'type',title:'类型',width:80,
                                formatter:function(val,rec){
                                    if (val == 1){return '在线咨询';}
                                    if (val == 2){return '投诉监督';}
                                    if (val == 3){return '建言献策';}
                                }
                            },
                            {field:'state',title:'状态',width:60,
                                formatter:function(val,rec){
                                    if (val == 0){return '办理中';}
                                    if (val == 1){return '已回复';}
                                }
                            },
                             {field:'checked',title:'审核',width:60,
                                formatter:function(val,rec){
                                    if (val){
                                        return '通过';
                                    }else{
                                        return '';
                                    }
                                }
                            },
                            {field:'date',title:'提问日期',width:120},
                            {field:'replayDate',title:'回复日期',width:120},
                            {field:'tel',title:'联系电话',width:180}
                        ]]
                });
				ewcmsOOBJ = new EwcmsOperate();
				ewcmsOOBJ.setQueryURL(ewcmsBOBJ.getQueryURL());
				ewcmsOOBJ.setInputURL('<s:url action="input"/>');
                $('#tt').datagrid({
                  onDblClickRow:function(rowIndex, rowData){
                      var url = '<s:url action="edit"/>?id='+rowData.id;
                      //$("#editifr").attr('src',url);
                      //openWindow1('#edit-window',{height:380,width:600});
                      openWindow1({title:'审核政民互动', url:url, width:800, height:550})
                  }
                });
            });
            function queryInteractionSearch(options){
                if(typeof(options) == 'undefined')options = {};
                var tableid = (options.tableid ? options.tableid : "#tt");
                var windowid = (options.windowid ? options.windowid : "#query-window");
                var url = (options.url ? options.url : '<s:url namespace="/plguin/interaction" action="query"/>');
                var formid = (options.formid ? options.formid : "#queryform");

                var value = $(formid).serialize();
                var index = url.indexOf("?");
                if (index == -1){
                    url = url + '?' + value;
                }else{
                    url = url + '&' + value;
                }
                $(tableid).datagrid({
                    url:url
                });
                $(windowid).window('close');
            }

            function closeWindow(){
            	queryInteractionSearch('');
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
                                <td class="tdtitle">标题：</td>
                                <td class="tdinput">
                                    <input type="text" id="title_id" name="title" class="inputtext"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="tdtitle">内容：</td>
                                <td class="tdinput">
                                    <input type="text" id="content_id" name="content" class="inputtext"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="tdtitle">审核：</td>
                                <td class="tdinput">
                                    <input type="radio" id="checked_id" name="checked" value="0"/>所有
                                    <input type="radio" id="checked_id" name="checked" value="1"/>通过&nbsp;&nbsp;
                                    <input type="radio" id="checked_id" name="checked" value="2"/>未通过&nbsp;&nbsp;
                                </td>
                            </tr>
                            <tr>
                                <td class="tdtitle">回复：</td>
                                <td class="tdinput">
                                    <input type="radio" id="replay_id" name="replay" value="0"/>所有&nbsp;&nbsp;
                                    <input type="radio" id="replay_id" name="replay" value="1"/>回复&nbsp;&nbsp;
                                    <input type="radio" id="replay_id" name="replay" value="2"/>未回复
                                </td>
                            </tr>
                            <tr>
                                <td class="tdtitle">类型：</td>
                                <td class="tdinput">
                                    <input type="radio" id="type_id" name="type" value="0"/>所有&nbsp;&nbsp;
                                	<input type="radio" id="type_id" name="type" value="1"/>在线咨询&nbsp;&nbsp;
                                    <input type="radio" id="type_id" name="type" value="2"/>投诉监督&nbsp;&nbsp;
                                    <input type="radio" id="type_id" name="type" value="3"/>建言献策
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" onclick="queryInteractionSearch();">查询</a>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)" onclick="javascript:$('#query-window').window('close');">取消</a>
                </div>
            </div>
        </div>
    </body>
</html>