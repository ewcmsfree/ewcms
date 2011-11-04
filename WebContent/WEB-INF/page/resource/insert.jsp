<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
    <head>
        <title>插入资源</title>
        <script type='text/javascript' src='<s:url value="/ewcmssource/js/jquery.min.js" />'></script>
        <script type="text/javascript" src='<s:url value="/ewcmssource/easyui/jquery.easyui.min.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/ewcmssource/easyui/locale/easyui-lang-zh_CN.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/ewcmssource/js/ewcms.base.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/ewcmssource/js/ewcms.func.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/ewcmssource/page/resource/insert.js"/>'></script>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/default/easyui.css"/>'/>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/icon.css"/>'/>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/css/ewcms.css"/>'/>
  
        <script type="text/javascript">
            var _insert = new ResourceInsert(
                    '<s:property value="context"/>',
                    {query:'<s:url action="query"/>'});
            
              $(function(){
                  _insert.init({
                      type:'<s:property value="type"/>',
                      multi:<s:property value="multi"/>
                  });
              })
              
              var insert = function(callback,message){
                  _insert.insert(callback,message);
              }
        </script>
    </head>
    <body>
            <div class="easyui-tabs" border="true" fit="true">
                <div title="上传"  style="padding: 5px;">
                    <iframe id="resourceifr_id" name="resourceifr" src='<s:url action="resource"/>?type=<s:property value="type"/>' class="editifr" scrolling="no"></iframe>
                </div>
                <div title="浏览"  style="padding: 5px;">
                    <table id="tt" toolbar="#tb"></table>
                    <div id="tb" style="padding:5px;height:auto;display:none;">
                            <div style="padding-left:5px;">
                               <form id="queryform" style="padding: 0;margin: 0;">
                                文件名: <input type="text" name="name" style="width:80px"/>&nbsp;
                                描述: <input type="text" name="description" style="width:120px"/>&nbsp;
                                <a href="#" id="toolbar-query" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                               </form>
                           </div>
                    </div>
                </div>
            </div>
    </body>
</html>
