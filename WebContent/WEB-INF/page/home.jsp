<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
    <head>
        <title>EWCMS 站群内容管理平台</title>
        <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
        <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
        <link rel="stylesheet" type="text/css" href='<s:url value="/source/css/portal.css"/>'>
        <link rel="stylesheet" type="text/css" href='<s:url value="/source/css/ewcms.css"/>'>
        <link rel="stylesheet" type="text/css" href='<s:url value="/source/page/home.css"/>'>
        <script type="text/javascript" src='<s:url value="/source/js/jquery.min.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>
        <script type="text/javascript" src="<s:url value="/source/js/jquery.portal.js"/>"></script>
        <script type="text/javascript" src='<s:url value="/source/js/ewcms.base.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/source/js/ewcms.func.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/source/page/home.js"/>'></script>
        <script type="text/javascript">
            var _home = new home();
            $(function(){
                ewcmsBOBJ = new EwcmsBase();
                _home.init({
                    user:'<s:url action="user" namespace="/account"/>',
                    password:'<s:url action="password" namespace="/account"/>',
                    exit:'<s:url value="/logout.do"/>'
                });
                
                $('#pp').portal({
    				border:false,
    				fit:true
    			});
                
                var popMessageUrl = '<s:url namespace="/notes" action="notesRemind"/>';
                var popInterval = setInterval("_home.getPopMessage('" + popMessageUrl + "')",60000);
                _home.setPopInterval(popInterval);
                
                var noticeUrl = '<s:url namespace="/message/send" action="notice"/>';
                var noticeDetailUrl = '<s:url namespace="/message/detail" action="index"/>?msgType=notice'
                _home.getNotice(noticeUrl, noticeDetailUrl);
                var noticeInterval = setInterval("_home.getNotice('" + noticeUrl + "','" + noticeDetailUrl + "')",60000);
                _home.setNoticeInterval(noticeInterval);
                
                var subscriptionUrl = '<s:url namespace="/message/send" action="subscription"/>';
                var subscriptionDetailUrl = '<s:url namespace="/message/detail" action="index"/>?msgType=subscription'
                _home.getSubscription(subscriptionUrl, subscriptionDetailUrl);
                var subscriptionInterval = setInterval("_home.getSubscription('" + subscriptionUrl  + "','" + subscriptionDetailUrl +  "')",60000);
                _home.setSubscriptionInterval(subscriptionInterval);
                
                var tipMessageUrl = '<s:url namespace="/message/receive" action="unRead"/>';
                _home.getTipMessage(tipMessageUrl);
                var tipInterval = setInterval("_home.getTipMessage('" + tipMessageUrl + "')",60000);
                _home.setTipInterval(tipInterval);
            });
        </script>
    </head>
    <body class="easyui-layout">
        <div region="north" split="true" class="head">
             <div style="padding: 10px;">
                  <div style="float:left;width:120px;">
                      <img src="<s:url value="/source/image/ewcms.png"/>" alt="ewcms.png"/>
                  </div>
                  <div  style="float:right;width:680px;padding-top:20px;height: 60px;">
                     <div style="float:left;width:646px;padding-top: 8px;text-align: right;">
                         <div style="width:100%;">
                            <span style="color:yellow;font-size:13px;font-weight: bold;"><span id="user-name"><s:property value="realName"/></span> <s:property value="siteName"/>欢迎你</span>
                            <span id="tipMessage" style="color:red;font-size:13px;"></span>
                         </div>
                     </div>
                     <div style="float:right;width:30px">
                         <a id="button-main"  href="#" style="border:0;padding:2;">
                            <img src='<s:url value="/source/image/exit.png"/>' width="24"/>
                         </a>
                     </div>
                  </div>
             </div>
             <div id="mm" class="easyui-menu" style="width:120px;display:none;">
                <div  iconCls="icon-switch">站点切换...</div>
                <div class="menu-sep"></div>
                <div id="user-menu" iconCls="icon-edit">修改用户信息</div>
                <div id="password-menu" iconCls="icon-password">修改密码</div>
                <div class="menu-sep"></div>
                <div id="exit-menu" iconCls="icon-exit">退出</div>
             </div>
        </div>
        <div region="south" style="height:2px;background:#efefef;overflow:hidden;"></div>
        <div region="west" split="true" title="EWCMS平台菜单" style="width:180px;padding:1px;overflow:hidden;">
            <div class="easyui-accordion" fit="true" border="false">
                 <sec:authorize ifAnyGranted="ROLE_ADMIN">
                 <div title="系统管理" style="overflow:auto;">
                    <div class="nav-item">
                        <a href="javascript:_home.addTab('站点管理','site/organ/index.do')">
                            <img src="source/image/site.png" style="border:0;"/><br/>
                            <span>站点管理</span>
                        </a>
                    </div>
                    <div class="nav-item">
                        <a href="javascript:_home.addTab('任务设置','scheduling/jobinfo/index.do')"> 
                            <img src="source/image/scheduling_job.png" style="border: 0" /><br/>
                            <span>任务设置</span>
                         </a>
                    </div>
                    <div class="nav-item">
                        <a  href="javascript:_home.addTab('作业设置','scheduling/jobclass/index.do')"> 
                             <img src="source/image/scheduling_jobclass.png" style="border: 0" /><br/>
                             <span>作业设置</span>
                        </a>
                    </div>
                    <div class="nav-item">
                        <a href="javascript:_home.addTab('历史记录','history/index.do')">
                            <img src="source/image/historymodel.png" style="border: 0" /><br />
                            <span>历史记录</span>
                        </a>
                    </div>
                     <div class="nav-item">
                         <a href="javascript:_home.addTab('文章分类属性','document/articlecategory/index.do')">
                            <img src="source/image/articlecategory.png" style="border:0"/><br/>
                            <span>文章分类属性</span>
                        </a>
                    </div>
                </div>
                <div title="权限管理" style="overflow:auto;">
                   <div class="nav-item">
                       <a href="javascript:_home.addTab('用户管理','security/user/index.do')">
                            <img src="source/image/user.png" style="border:0;"/><br/>
                            <span>用户管理</span>
                        </a>
                    </div>
                    <div class="nav-item">
                       <a href="javascript:_home.addTab('用户组管理','security/group/index.do')">
                            <img src="source/image/group.png" style="border:0;"/><br/>
                            <span>用户组管理</span>
                        </a>
                    </div>
                    <div class="nav-item">
                        <a href="javascript:_home.addTab('权限列表','security/authority/index.do')">
                            <img src="source/image/role.png" style="border:0;"/><br/>
                            <span>权限列表</span>
                        </a>
                    </div>
                </div>     
                </sec:authorize>             
                <div title="站点建设" selected="true" style="overflow:auto;">
                    <sec:authorize ifAnyGranted="ROLE_ADMIN,ROLE_EDITOR,ROLE_WRITER,ROLE_USER">
                    <div class="nav-item">
                        <a href="javascript:_home.addTab('专题与栏目','site/channel/index.do')">
                            <img src="source/image/package.png" style="border:0;"/><br/>
                            <span>专题与栏目</span>
                        </a>
                    </div>
                    </sec:authorize>
                    <sec:authorize ifAnyGranted="ROLE_ADMIN">
                    <div class="nav-item">
                        <a href="javascript:_home.addTab('模板与资源','site/template/index.do')">
                            <img src="source/image/kontact.png" style="border:0;"/><br/>
                            <span>模板与资源</span>
                        </a>
                    </div>
                    </sec:authorize>
                </div>
                <div title="站点内容" style="overflow:auto;">
                    <sec:authorize ifAnyGranted="ROLE_ADMIN,ROLE_EDITOR,ROLE_WRITER,ROLE_USER">
                    <div class="nav-item">
                        <a href="javascript:_home.addTab('文章编辑','document/article/index.do')">
                            <img src="source/image/articleedit.png" style="border:0"/><br/>
                            <span>文章编辑</span>
                        </a>
                    </div>
                    <div class="nav-item">
                        <a href="javascript:_home.addTab('文章回收站','document/recyclebin/index.do')">
                            <img src="source/image/recyclebin.png" style="border:0"/><br/>
                            <span>回收站</span>
                        </a>
                    </div>
                    <div class="nav-item">
                        <a href="javascript:_home.addTab('问卷调查','vote/index.do')">
                            <img src="source/image/vote.png" style="border:0"/><br/>
                            <span>问卷调查</span>
                        </a>
                    </div>
                    <div class="nav-item">
                        <a href="javascript:_home.addTab('备忘录','notes/index.do')">
                            <img src="source/image/notes.png" style="border:0"/><br/>
                            <span>备忘录</span>
                        </a>
                    </div>
                    <div class="nav-item">
                        <a href="javascript:_home.addTab('个人消息','message/index.do')">
                            <img src="source/image/message.png" style="border:0"/><br/>
                            <span>个人消息</span>
                        </a>
                    </div>
                    </sec:authorize>
                </div>
                <sec:authorize ifAnyGranted="ROLE_ADMIN,ROLE_RESOURCE">
                <div title="站点资源" style="overflow:auto;">
                    <div class="nav-item">
                        <a href="javascript:_home.addTab('资源管理','resource/index.do')">
                            <img src="source/image/kontact.png" style="border:0"/><br/>
                            <span>资源管理</span>
                        </a>
                    </div>
                    <div class="nav-item">
                        <a href="javascript:_home.addTab('资源回收站','resource/recycle.do')">
                            <img src="source/image/recyclebin.png" style="border:0"/><br/>
                            <span>回收站</span>
                        </a>
                    </div>
                </div>
                </sec:authorize>
            </div>
        </div>
        <div region="center" style="overflow:hidden;">
            <div class="easyui-tabs"  id="systemtab" fit="true" border="false">
                <div title="首页" style="padding:20px;overflow:hidden;">
                    <div style="margin-top:20px;">
                        <center><h2>欢迎使用EWCMS企业网站内容管理系统</h2></center>
                    </div>
                    <div id="pp" style="position:relative">  
        				<div style="width:33%">
        					<div id='notice' title="公告栏" collapsible="true" closable="false" style="height:200px;padding:5px;">
			    			</div>
        				</div>  
                        <div style="width:33%">
        					<div id='subscription' title="订阅栏" collapsible="true" closable="false" style="height:200px;padding:5px;">
			    			</div>
                        </div>  
			            <div style="width:33%">
        					<div id='other' title="其它栏" collapsible="true" closable="false" style="height:200px;padding:5px;">
			    			</div>
			           </div>  
			    	</div>  
                </div>
                </div>
            </div>
        </div>
        <div id="edit-window" class="easyui-window" closed="true" icon="icon-winedit" style="display:none;">
            <div class="easyui-layout" fit="true">
                <div region="center" border="false" style="padding: 10px;">
                    <iframe name="editifr" class="editifr" frameborder="0" onload="iframeFitHeight(this);" scrolling="no"></iframe>
                </div>
                <div region="south" border="false" style="padding-right:20px;text-align:right;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-save" href="javascript:void(0)" onclick="window.frames['editifr'].pageSubmit();">保存</a>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)" onclick="$('#edit-window').window('close');">取消</a>
                </div>
            </div>
        </div>
		<div id="detail-window" class="easyui-window" icon="" closed="true" style="display:none;">
            <div class="easyui-layout" fit="true">
                <div region="center" border="false">
                	<iframe id="editifr_detail"  name="editifr_detail" frameborder="0" width="100%" height="100%" scrolling="auto" style="width:100%;height:100%;"></iframe>
                </div>
            </div>
        </div>
    </body>
</html>