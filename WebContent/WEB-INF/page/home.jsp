<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
    <head>
        <title>EWCMS 站群内容管理平台</title>
        <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
        <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
        <link rel="stylesheet" type="text/css" href='<s:url value="/source/css/ewcms.css"/>'>
        <script type="text/javascript" src='<s:url value="/source/js/jquery-1.4.2.min.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/source/js/ewcms.js"/>'></script>
        <script type="text/javascript">
            function addTab(title,src){
                var tabob = $('#systemtab');
                if(tabob.tabs('exists',title))
                {
                    tabob.tabs('select',title);
                    var tab = tabob.tabs("getTab",title);
                    tabob.tabs('update',{
                        tab:tab,
                        options:{content:'<iframe src='+src+' width=100% frameborder=0 height=100%/>'}
                    });

                }
                else{
                    tabob.tabs('add',{
                        title:title,
                        content:'<iframe src='+src+' width=100% frameborder=0 height=100%/>',
                        closable:true
                    });
                }
            }
            $(function(){
                $('#cc').combobox({
                    url:'<s:url action="siteload"/>',
                    valueField:'id',
                    textField:'siteName',
                    onSelect:function(data){
                        siteLoad(data.id);
                    },onLoadSuccess:function(){
                        $('#cc').combobox('setValue','<s:property value="siteId"/>');
                    }
                });
                $('#edit-window').window({
                    onClose:function(){
                        $('#editifr').attr('src',"about:blank");
                    }
                });
                
            });
            
            function openEdit(url,width,height,title){
                $('#editifr').attr('src',url);
                openWindow('#edit-window',{width:width,height:height,title:title});
            }
            
            function editSubmit(){
                window.frames['editifr'].pageSubmit();
            }
            
            function closeWindow(){
                $('#edit-window').window('close');
            }
            
            function siteLoad(siteId){
                window.location = '<s:url action="index"/>?siteId='+siteId;
            }
        </script>
        <style>
            .nav-item{text-align:center; background:#fff; height:80px;}
            .nav-item img{border:0;}
            a:link {text-decoration: none;color: #1B5978;}
            a:visited {text-decoration: none;color: #1B5978;}
            a:hover {text-decoration: underline;color: red;}
            p{line-height:200%;}
        </style>
    </head>
    <body class="easyui-layout">
        <div region="north" split="true" style="height:65px;padding:0px;overflow:hidden;background:url('<s:url value="/source/image/vistaBlue.jpg"/>') #1E4176">
            <table width="100%" >
                <tr height="35">
                    <td width="320" rowspan="2"><FONT style="FONT-SIZE: 16pt; FILTER: glow(color=black); WIDTH: 100%; COLOR: white; FONT-FAMILY: 华文彩云"><img src="<s:url value="/source/image/ewcms.png"/>" alt="ewcms.png"/>－站群平台</font></td>
                    <td align="right">
                       <font color="gray" style="font-size:12px;"><select id="cc" class="easyui-combobox"  style="width:180px;"  name="siteId"  onchange="siteLoad(this.value);"></select></font>
                    </td>                    
                </tr>
                <tr >
                    <td align="right">
                        <table   border="0" cellpadding="0" cellspacing="2" >
			    <tr>
			        <td><font style="color:yellow;font-size: 15px;"><strong>&nbsp;&nbsp;&nbsp;&nbsp;您好 <s:property value="userName"/>!</strong></font></td>
			        <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                <s:url id="userInfoUrl" action="userInfo" namespace="/account"/>
                                <td  nowrap align="right">
                                    <div><span style="vertical-align:middle;"><img src="<s:url value="/source/image/user_edit.png"/>"  border="0"></span>&nbsp;<s:a href='javascript:openEdit("%{userInfoUrl}?nocache=" + Math.random(),550,300,"修改用户信息");'><font style="color:white;font-size: 12px;"><strong>修改用户信息</strong></font></s:a></div>                                               
                                 </td>    
                                 <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>                                        
                                 <s:url id="passwordUrl" action="password" namespace="/account"/>
                                 <td  nowrap align="right">
                                     <div><span style="vertical-align:middle;"><img src="<s:url value="/source/image/user_orange.png"/>"  border="0"></span>&nbsp;<s:a href='javascript:openEdit("%{passwordUrl}",550,300,"修改密码");'><font style="color:white;font-size: 12px;"><strong>修改密码</strong></font></s:a></div>                                               
                                 </td>
                                 <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>			                        	
			         <s:url id="logoutUrl" value="/logout.do"/>
			         <td  nowrap align="right" >
			             <div><span style="vertical-align:middle;"><img src="<s:url value="/source/image/user_go.png"/>"  border="0"></span>&nbsp;<s:a href='%{logoutUrl}'><font style="color:white;font-size: 12px;"><strong>退出系统</strong></font></s:a></div>
				 </td>
                            </tr>
                       </table>             
                    </td>                
                </tr>
            </table>
        </div>
        <div region="south" style="height:2px;background:#efefef;overflow:hidden;"></div>
        <div region="west" split="true" title="EWCMS平台菜单" style="width:180px;padding:1px;overflow:hidden;">
            <div class="easyui-accordion" fit="true" border="false">
                 <sec:authorize ifAnyGranted="ROLE_ADMIN">
                 <div title="系统管理" style="overflow:auto;">
                    <div class="nav-item">
                        <a href="javascript:addTab('站点管理','site/organ/index.do')">
                            <img src="source/image/site.png" style="border:0;"/><br/>
                            <span>站点管理</span>
                        </a>
                    </div>
                    <div class="nav-item">
                        <a href="javascript:addTab('任务设置','scheduling/job/index.do')"> 
                            <img src="source/image/scheduling_job.png" style="border: 0" /><br/>
                            <span>任务设置</span>
                         </a>
                    </div>
                    <div class="nav-item">
                        <a  href="javascript:addTab('作业设置','scheduling/jobclass/index.do')"> 
                             <img src="source/image/scheduling_jobclass.png" style="border: 0" /><br/>
                             <span>作业设置</span>
                        </a>
                    </div>
                    <div class="nav-item">
                        <a href="javascript:addTab('历史记录','aspect/history/index.do')">
                            <img src="source/image/historymodel.png" style="border: 0" /><br />
                            <span>历史记录</span>
                        </a>
                    </div>
                </div>
                <div title="权限管理" style="overflow:auto;">
                   <div class="nav-item">
                       <a href="javascript:addTab('用户管理','security/user/index.do')">
                            <img src="source/image/user.png" style="border:0;"/><br/>
                            <span>用户管理</span>
                        </a>
                    </div>
                    <div class="nav-item">
                       <a href="javascript:addTab('用户组管理','security/group/index.do')">
                            <img src="source/image/group.png" style="border:0;"/><br/>
                            <span>用户组管理</span>
                        </a>
                    </div>
                    <div class="nav-item">
                        <a href="javascript:addTab('权限列表','security/authority/index.do')">
                            <img src="source/image/role.png" style="border:0;"/><br/>
                            <span>权限列表</span>
                        </a>
                    </div>
                </div>     
                </sec:authorize>             
                <div title="站点建设" selected="true" style="overflow:auto;">
                    <sec:authorize ifAnyGranted="ROLE_ADMIN,ROLE_EDITOR,ROLE_WRITER,ROLE_USER">
                    <div class="nav-item">
                        <a href="javascript:addTab('专题与栏目','site/channel/index.do')">
                            <img src="source/image/package.png" style="border:0;"/><br/>
                            <span>专题与栏目</span>
                        </a>
                    </div>
                    </sec:authorize>
                    <sec:authorize ifAnyGranted="ROLE_ADMIN">
                    <div class="nav-item">
                        <a href="javascript:addTab('模板与资源','site/template/index.do')">
                            <img src="source/image/kontact.png" style="border:0;"/><br/>
                            <span>模板与资源</span>
                        </a>
                    </div>
                    </sec:authorize>
                </div>
                <div title="站点内容" style="overflow:auto;">
                    <sec:authorize ifAnyGranted="ROLE_ADMIN,ROLE_EDITOR,ROLE_WRITER,ROLE_USER">
                    <div class="nav-item">
                        <a href="javascript:addTab('文章编辑','document/article/index.do')">
                            <img src="source/image/articleedit.png" style="border:0"/><br/>
                            <span>文章编辑</span>
                        </a>
                    </div>
                    <div class="nav-item">
                        <a href="javascript:addTab('文章共享库 ','document/share/index.do')">
                            <img src="source/image/package.png" style="border:0"/><br/>
                            <span>共享库 </span>
                        </a>
                    </div>
                    <div class="nav-item">
                        <a href="javascript:addTab('文章回收站','document/recyclebin/index.do')">
                            <img src="source/image/recyclebin.png" style="border:0"/><br/>
                            <span>回收站</span>
                        </a>
                    </div>
                    </sec:authorize>
                    <sec:authorize ifAnyGranted="ROLE_ADMIN,ROLE_RESOURCE">
                    <div class="nav-item">
                        <a href="javascript:addTab('资源库','resource/manage.do')">
                            <img src="source/image/kontact.png" style="border:0"/><br/>
                            <span>资源库</span>
                        </a>
                    </div>
                    </sec:authorize>
                </div>
            </div>
        </div>
        <div region="center" style="overflow:hidden;">
            <div class="easyui-tabs"  id="systemtab" fit="true" border="false">
                <div title="首页" style="padding:20px;overflow:hidden;">
                    <div style="margin-top:20px;">
                        <center><h2>欢迎使用EWCMS企业网站内容管理系统</h2></center>
                    </div>
                </div>
            </div>
        </div>
        <div id="edit-window" class="easyui-window" closed="true" icon="icon-winedit" style="display:none;">
            <div class="easyui-layout" fit="true">
                <div region="center" border="false">
                    <iframe id="editifr"  name="editifr" class="editifr" frameborder="0" onload="iframeFitHeight(this);" scrolling="no"></iframe>
                </div>
                <div region="south" border="false" style="padding-right:20px;text-align:right;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-save" href="javascript:void(0)" onclick="editSubmit()">保存</a>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)" onclick="closeWindow()">取消</a>
                </div>
            </div>
        </div>
    </body>
</html>