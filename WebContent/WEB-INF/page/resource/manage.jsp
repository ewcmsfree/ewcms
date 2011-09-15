<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="ewcms" uri="/ewcms-tags"%>
<html>
    <head>
        <title>资源管理管理</title>
        <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
        <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
        <link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>
        <script type="text/javascript" src='<s:url value="/source/js/jquery.min.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/source/js/easyui-lang-zh_CN.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/source/js/ewcms.base.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/source/js/ewcms.func.js"/>'></script>
        <ewcms:datepickerhead></ewcms:datepickerhead>
        <script type="text/javascript">
            var ewcms = new EwcmsBase();
            $(function(){
                $('#tt').datagrid({
                    url: 'datagrid_data2.json',
                    width: 700,
                    height: 'auto',
                    fitColumns: true,
                    pagination:true,
                    rownumbers:true,
                    columns:[[
                            {field:'itemid',title:'Item ID',width:80},
                            {field:'productid',title:'Product ID',width:120},
                            {field:'listprice',title:'List Price',width:80,align:'right'},
                            {field:'unitcost',title:'Unit Cost',width:80,align:'right'},
                            {field:'attr1',title:'Attribute',width:250},
                            {field:'status',title:'Status',width:60,align:'center'}
                    ]],
                    onHeaderContextMenu: function(e, field){
                            e.preventDefault();
                            if (!$('#tmenu').length){
                                    createColumnMenu();
                            }
                            $('#tmenu').menu('show', {
                                    left:e.pageX,
                                    top:e.pageY
                            });
                    }
            });
            });
           
            function openWindowUpload(){
                $("#uploadifr_id").attr("src","<s:url action="upload"/>?type=<s:property value="type"/>")
                openWindow("#window-upload",{width:600,height:400,title:"资源上传"});
            }
           
        </script>
    </head>
    <body>
        <table id="tt" fit="true" toolbar="#tb"></table>
        <div id="tb" style="padding:5px;height:auto">
                <div style="margin-bottom:5px">
                   <a href="#" class="easyui-linkbutton" iconCls="icon-upload" plain="true" onclick='javascript:openWindowUpload();'>上传</a>
                   <a href="#" class="easyui-linkbutton" iconCls="icon-publish" plain="true">发布</a>
                   <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
                </div>
                <div style="padding-left:5px;">
                   <form id="queryform" style="padding: 0;margin: 0;">
                    文件名: <input type="text" name="name" style="width:80px"/>&nbsp;
                    描述: <input type="text" name="description" style="width:120px"/>&nbsp;
                    上传日期 从: <ewcms:datepicker name="createTimeFrom" option="inputsimple" format="yyyy-MM-dd"/> 到: <ewcms:datepicker name="createTimeTO" option="inputsimple" format="yyyy-MM-dd"/>
                    <s:hidden name="type"/>
                    <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="querySearch('');">查询</a>
                   </form>
               </div>
        </div>
        <div id="window-upload" class="easyui-window" closed="true" icon='icon-upload'>
          <div class="easyui-layout" fit="true" >
                <div region="center" border="false" style="padding: 5px;">
                   <iframe src="" id="uploadifr_id"  name="uploadifr" class="editifr" scrolling="no"></iframe>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)"  onclick="javascript:uploadifr.insert();">确定</a>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)"  onclick="javascript:$('#window-upload').window('close');return false;">取消</a>
                </div>
            </div>
       </div>
    </body>
</html>