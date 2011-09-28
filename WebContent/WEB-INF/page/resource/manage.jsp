<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="ewcms" uri="/ewcms-tags"%>
<html>
    <head>
        <title>资源管理实现</title>
        <script type="text/javascript" src='<s:url value="/source/js/jquery.min.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/source/js/easyui-lang-zh_CN.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/source/js/ewcms.base.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/source/js/ewcms.func.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/source/page/resource/manage.js"/>' charset="utf-8"></script>
        <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
        <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
        <link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>
       
        <ewcms:datepickerhead></ewcms:datepickerhead>
        
        <script type="text/javascript">
            var _m = new manage('<s:property value="context"/>','<s:property value="type"/>');
            
            $(function(){
                _m.init({
                    query:'<s:url action="query"/>',
                    resource:'<s:url action="resource"/>',
                    thumb:'<s:url action="thumb"/>',
                    publish:'<s:url action="publish"/>',
                    remove:'<s:url action="softDelete"/>'
                });
            });
        </script>
    </head>
    <body>
        <table id="tt" toolbar="#tb"></table>
        <div id="tb" style="padding:5px;height:auto;display:none;">
                <div style="margin-bottom:5px">
                   <a href="#" id="toolbar-upload" class="easyui-linkbutton" iconCls="icon-upload" plain="true">上传</a>
                   <a href="#" id="toolbar-publish" class="easyui-linkbutton" iconCls="icon-publish" plain="true">发布</a>
                   <a href="#" id="toolbar-remove" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
                </div>
                <div style="padding-left:5px;">
                   <form id="queryform" style="padding: 0;margin: 0;">
                    文件名: <input type="text" name="name" style="width:80px"/>&nbsp;
                    描述: <input type="text" name="description" style="width:120px"/>&nbsp;
                    上传日期 从: <ewcms:datepicker name="fromDate" option="inputsimple" format="yyyy-MM-dd"/> 
                    到: <ewcms:datepicker name="toDate" option="inputsimple" format="yyyy-MM-dd"/>
                    <a href="#" id="toolbar-query" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                   </form>
               </div>
        </div>
        <div id="mm" class="easyui-menu" style="width:180px;display:none;">  
            <div id="menu-item-title"><b>操作</b></div>
            <div class="menu-sep"></div>   
            <div iconCls="icon-save">更新资源</div>  
            <div iconCls="icon-image-add">更新引导图</div>  
            <div class="menu-sep"></div>
            <div iconCls="icon-publish">发布</div>
            <div iconCls="icon-remove">删除</div>  
            <div class="menu-sep"></div>   
            <div iconCls="icon-download">下载</div>   
        </div>  
        <div id="resource-upload-window" class="easyui-window" closed="true" icon='icon-upload'>
          <div class="easyui-layout" fit="true" >
                <div region="center" border="false" style="padding: 5px;">
                   <iframe src="" name="uploadifr" class="editifr" scrolling="no"></iframe>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a id="button-save" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)">确定</a>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)"  onclick="javascript:$('#resource-upload-window').window('close');return false;">取消</a>
                </div>
            </div>
       </div>
       <div id="resource-update-window" class="easyui-window" closed="true" icon='icon-save'>
          <div class="easyui-layout" fit="true">
                <div region="center" border="false" style="padding: 5px;">
                   <iframe src="" name="updateifr" class="editifr" scrolling="no"></iframe>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)"  onclick="javascript:$('#resource-update-window').window('close');return false;">关闭</a>
                </div>
            </div>
       </div>
       <div id="thumb-update-window" class="easyui-window" closed="true" icon='icon-save'>
          <div class="easyui-layout" fit="true" >
                <div region="center" border="false" style="padding: 5px;">
                   <iframe src="" name="thumbifr" class="editifr" scrolling="no"></iframe>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)"  onclick="javascript:$('#thumb-update-window').window('close');return false;">关闭</a>
                </div>
            </div>
       </div>
    </body>
</html>