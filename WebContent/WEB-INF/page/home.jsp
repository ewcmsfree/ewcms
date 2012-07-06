<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
        <title>EWCMS 站群内容管理平台</title>
        <s:include value="taglibs.jsp"/>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/page/home.css"/>'/>
        <script type="text/javascript" src='<s:url value="/ewcmssource/page/home.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/ewcmssource/fcf/js/FusionCharts.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/ewcmssource/js/ewcms.pubsub.js"/>'></script>
        <script type="text/javascript">
            var _home = new home();
            $(function(){
            	var clock = new Clock();
            	clock.display(document.getElementById("clock"));
            	
                ewcmsBOBJ = new EwcmsBase();
                ewcmsOOBJ = new EwcmsOperate();
                _home.init({
                    user:'<s:url action="user" namespace="/account"/>',
                    password:'<s:url action="password" namespace="/account"/>',
                    exit:'<s:url value="/logout.do"/>',
                    siteswitch:'<s:url action="siteswitch"/>',
                    progress:'<s:url action="progress"/>',
                    hasSite:'<s:property value="hasSite"/>'
                });
                
                $(window).unload(function() {
                    pubsub.onUnload();
                }).load(function(){
                    var url = 'pubsub/message/<s:property value="userName"/>';
                    pubsub.initialize(url);
                });
            });
        </script>
    </head>
    <body class="easyui-layout">
        <div region="north" split="true" class="head">
        	<table width="100%">
        		<tr>
        			<td width="50%" style="text-align: left"><img src='<s:url value="/ewcmssource/image/top_bg_ewcms.gif"/>' height="35px" border="0" style="border:0;padding-left:4px;padding-top:13px;"/> | 企业网站站群内容管理系统V2.0</td>
        			<td width="50%">
        				<table width="100%">
        					<tr>
			        			<td height="30px" width="97%" style="text-align: right"><span style="font-size:13px;font-weight: bold;"><span id="user-name"><s:property value="realName"/></span> <s:property value="siteName"/>欢迎你</span> | <span id="clock"></span></td>
        						<td width="2%"><a id="button-main" href="#" style="border:0;padding:0;"><img src="<s:url value='/ewcmssource/image/exit.png'/>" width="17" height="17" style="border:0;"/></a></td>
        						<td width="1%"></td>
        					</tr>
        					<tr>
        						<td height="20px" colspan="2" >
        							<table width="100%">
        								<tr>
        									<td width="65%" style="text-align:right;">
			        							<span id="tipMessage" style="color:red;font-size:13px;"></span>
			        						</td>
			        						<td width="35%" style="text-align:left">
			        							<div class="bs">
													<a class="styleswitch a1" style="cursor: pointer" title="谈黄色" rel="sunny"></a>
													<a class="styleswitch a2" style="cursor: pointer" title="浅蓝色" rel="cupertino"></a>
													<a class="styleswitch a4" style="cursor: pointer" title="黑色" rel="dark-hive"></a>	
													<a class="styleswitch a5" style="cursor: pointer" title="灰色" rel="pepper-grinder"></a>		
												</div>
			        						</td>
			        					</tr>
			        				</table>
			        			</td>
        					</tr>
        				</table>
        			</td>
        		</tr>
        	</table>
             <div id="mm" class="easyui-menu" style="width:120px;display:none;">
                <div  id="switch-menu" iconCls="icon-switch">站点切换</div>
                <div class="menu-sep"></div>
                <div id="user-menu" iconCls="icon-edit">修改用户信息</div>
                <div id="password-menu" iconCls="icon-password">修改密码</div>
                <div class="menu-sep"></div>
                <div id="progress-menu">发布进度</div>
                <div class="menu-sep"></div>
                <div id="exit-menu" iconCls="icon-exit">退出</div>
             </div>
        </div>
        <div region="south" split="true" style="height:2px;background:#efefef;overflow:hidden;"></div>
        <div region="east" split="true" style="height:2px;width:2px;background:#efefef;overflow:hidden;"></div>  
        <div region="west" split="true" title="EWCMS平台菜单" style="width:180px;padding:1px;overflow:hidden;">
            <div id="mainmenu" class="easyui-accordion" fit="true" border="false">
                 <sec:authorize ifAnyGranted="ROLE_ADMIN">
                 <div title="系统管理" style="overflow:auto;">
                    <div class="nav-item">
                        <a href="javascript:_home.addTab('站点管理','site/organ/index.do')">
                            <img src="ewcmssource/image/site.png" style="border:0;"/><br/>
                            <span>站点管理</span>
                        </a>
                    </div>
                    <div class="nav-item">
                        <a href="javascript:_home.addTab('任务设置','scheduling/jobinfo/index.do')"> 
                            <img src="ewcmssource/image/scheduling_job.png" style="border: 0" /><br/>
                            <span>任务设置</span>
                         </a>
                    </div>
                    <div class="nav-item">
                        <a  href="javascript:_home.addTab('作业设置','scheduling/jobclass/index.do')"> 
                             <img src="ewcmssource/image/scheduling_jobclass.png" style="border: 0" /><br/>
                             <span>作业设置</span>
                        </a>
                    </div>
                    <div class="nav-item">
                        <a href="javascript:_home.addTab('历史记录','history/index.do')">
                            <img src="ewcmssource/image/historymodel.png" style="border: 0" /><br />
                            <span>历史记录</span>
                        </a>
                    </div>
                     <div class="nav-item">
                         <a href="javascript:_home.addTab('文章分类','document/category/index.do')">
                            <img src="ewcmssource/image/articlecategory.png" style="border:0"/><br/>
                            <span>文章分类</span>
                        </a>
                    </div>
                </div>
                <div title="权限管理" style="overflow:auto;">
                   <div class="nav-item">
                        <a href="javascript:_home.addTab('权限列表','security/authority/index.do')">
                            <img src="ewcmssource/image/role.png" style="border:0;"/><br/>
                            <span>权限列表</span>
                        </a>
                   </div>
                   <div class="nav-item">
                       <a href="javascript:_home.addTab('用户组管理','security/group/index.do')">
                            <img src="ewcmssource/image/group.png" style="border:0;"/><br/>
                            <span>用户组管理</span>
                        </a>
                   </div>
                   <div class="nav-item">
                       <a href="javascript:_home.addTab('用户管理','security/user/index.do')">
                            <img src="ewcmssource/image/user.png" style="border:0;"/><br/>
                            <span>用户管理</span>
                        </a>
                    </div>
                </div>     
                </sec:authorize>             
                <div title="站点建设" style="overflow:auto;">
                    <sec:authorize ifAnyGranted="ROLE_ADMIN,ROLE_EDITOR,ROLE_WRITER,ROLE_USER">
                    <div class="nav-item">
                        <a href="javascript:_home.addTab('专题与栏目','site/channel/index.do')">
                            <img src="ewcmssource/image/package.png" style="border:0;"/><br/>
                            <span>专题与栏目</span>
                        </a>
                    </div>
                    </sec:authorize>
                    <sec:authorize ifAnyGranted="ROLE_ADMIN">
                    <div class="nav-item">
                        <a href="javascript:_home.addTab('模板与资源','site/template/index.do')">
                            <img src="ewcmssource/image/kontact.png" style="border:0;"/><br/>
                            <span>模板与资源</span>
                        </a>
                    </div>
                    </sec:authorize>
                </div>
                <div title="站点内容" selected="true" style="overflow:auto;">
                    <sec:authorize ifAnyGranted="ROLE_ADMIN,ROLE_EDITOR,ROLE_WRITER,ROLE_USER">
                    <div class="nav-item">
                        <a href="javascript:_home.addTab('内容编辑','document/tree/index.do')">
                            <img src="ewcmssource/image/articleedit.png" style="border:0"/><br/>
                            <span>内容编辑</span>
                        </a>
                    </div>
                    <div class="nav-item">
                        <a href="javascript:_home.addTab('文章回收站','document/recyclebin/tree/index.do')">
                            <img src="ewcmssource/image/recyclebin.png" style="border:0"/><br/>
                            <span>回收站</span>
                        </a>
                    </div>
                    <div class="nav-item">
                        <a href="javascript:_home.addTab('问卷调查','vote/index.do')">
                            <img src="ewcmssource/image/vote.png" style="border:0"/><br/>
                            <span>问卷调查</span>
                        </a>
                    </div>
                    <div class="nav-item">
                        <a href="javascript:_home.addTab('备忘录','notes/index.do')">
                            <img src="ewcmssource/image/notes.png" style="border:0"/><br/>
                            <span>备忘录</span>
                        </a>
                    </div>
                    <div class="nav-item">
                        <a href="javascript:_home.addTab('个人消息','message/index.do')">
                            <img src="ewcmssource/image/message.png" style="border:0"/><br/>
                            <span>个人消息</span>
                        </a>
                    </div>
                    </sec:authorize>
                </div>
                <sec:authorize ifAnyGranted="ROLE_ADMIN,ROLE_INTERACTION">
                <div title="互动服务" style="overflow:auto;">
                    <div class="nav-item">
                        <a href="javascript:_home.addTab('政民互动','plguin/interaction/index.do')">
                            <img src="ewcmssource/image/kontact.png" style="border:0"/><br/>
                            <span>政民互动</span>
                        </a>
                    </div>
                    <div class="nav-item">
                        <a href="javascript:_home.addTab('留言审核','plguin/interaction/speak.do')">
                            <img src="ewcmssource/image/kontact.png" style="border:0"/><br/>
                            <span>留言审核</span>
                        </a>
                    </div>
                    <div class="nav-item">
                        <a href="javascript:_home.addTab('网上咨询','plugin/online/advisor/index.do')">
                            <img src="ewcmssource/image/kontact.png" style="border:0"/><br/>
                            <span>网上咨询</span>
                        </a>
                    </div>
                </div>
                </sec:authorize>  
                <sec:authorize ifAnyGranted="ROLE_ADMIN,ROLE_RESOURCE">
                <div title="站点资源" style="overflow:auto;">
                    <div class="nav-item">
                        <a href="javascript:_home.addTab('资源管理','resource/manage/index.do')">
                            <img src="ewcmssource/image/kontact.png" style="border:0"/><br/>
                            <span>资源管理</span>
                        </a>
                    </div>
                    <div class="nav-item">
                        <a href="javascript:_home.addTab('资源回收站','resource/manage/recycle.do')">
                            <img src="ewcmssource/image/recyclebin.png" style="border:0"/><br/>
                            <span>回收站</span>
                        </a>
                    </div>
                </div>
                </sec:authorize>
                <!-- 
                <sec:authorize ifAnyGranted="ROLE_ADMIN">
                <div title="网络采集" style="overflow:auto;">
                    <div class="nav-item">
                        <a href="javascript:_home.addTab('内容采集','crawler/content/index.do')">
                            <img src="ewcmssource/image/crawler_content.png" style="border:0"/><br/>
                            <span>内容采集</span>
                        </a>
                    </div>
                    <div class="nav-item">
                        <a href="javascript:_home.addTab('资源采集','crawler/resource/index.do')">
                            <img src="ewcmssource/image/crawler_resource.png" style="border:0"/><br/>
                            <span>资源采集</span>
                        </a>
                    </div>
                    <div class="nav-item">
                        <a href="javascript:_home.addTab('存储库','crawler/storage/index.do')">
                            <img src="ewcmssource/image/crawler_storage.png" style="border:0"/><br/>
                            <span>存储库</span>
                        </a>
                    </div>
                </div>
                </sec:authorize>
                 -->
                <sec:authorize ifAnyGranted="ROLE_ADMIN">
               	<div title="报表管理" style="overflow:auto;">
               	    <div class="nav-item">
                         <a href="javascript:_home.addTab('文字报表','report/text/index.do')">
                            <img src="ewcmssource/image/report_text.png" style="border:0"/><br/>
                            <span>文字报表</span>
                        </a>
               	    </div>
               	    <div class="nav-item">
                         <a href="javascript:_home.addTab('图型报表','report/chart/index.do')">
                            <img src="ewcmssource/image/report_chart.png" style="border:0"/><br/>
                            <span>图型报表</span>
                        </a>
               	    </div>
               	    <div class="nav-item">
                         <a href="javascript:_home.addTab('报表分类','report/category/index.do')">
                            <img src="ewcmssource/image/report_category.png" style="border:0"/><br/>
                            <span>报表分类</span>
                        </a>
               	    </div>
               	    <div class="nav-item">
                         <a href="javascript:_home.addTab('报表存储','report/repository/index.do')">
                            <img src="ewcmssource/image/report_repository.png" style="border:0"/><br/>
                            <span>报表存储</span>
                        </a>
               	    </div>
               	    <div class="nav-item">
                         <a href="javascript:_home.addTab('报表集','report/show/index.do')">
                            <img src="ewcmssource/image/report_show.png" style="border:0"/><br/>
                            <span>报表集</span>
                        </a>
               	    </div>
               	    <div class="nav-item">
                         <a href="javascript:_home.addTab('数据源','ds/index.do')">
                            <img src="ewcmssource/image/report_ds.png" style="border:0"/><br/>
                            <span>数据源</span>
                        </a>
               	    </div>
               	</div>
                </sec:authorize>
                <!-- 
                <sec:authorize ifAnyGranted="ROLE_ADMIN">
                <div title="特殊信息" style="overflow:auto;">
                	<div class="nav-item">
                         <a href="javascript:_home.addTab('审批备案','particular/ar/index.do')">
                            <img src="ewcmssource/image/articlecategory.png" style="border:0"/><br/>
                            <span>审批备案</span>
                        </a>
                    </div>
                    <div class="nav-item">
                         <a href="javascript:_home.addTab('行业编码','particular/ic/index.do')">
                            <img src="ewcmssource/image/articlecategory.png" style="border:0"/><br/>
                            <span>行业编码</span>
                        </a>
                    </div>
                    <div class="nav-item">
                         <a href="javascript:_home.addTab('行政区划','particular/zc/index.do')">
                            <img src="ewcmssource/image/articlecategory.png" style="border:0"/><br/>
                            <span>行政区划</span>
                        </a>
                    </div>
                </div>
                </sec:authorize>
                 -->
            </div>
        </div>
        <div region="center" style="overflow:hidden;">
            <div class="easyui-tabs" id="systemtab" fit="true" border="false">
                <div title="首页" style="padding:5px;overflow:hidden;">
                    <div style="margin-top:10px;">
                        <center><h2>欢迎使用EWCMS企业网站内容管理系统</h2></center>
                    </div>
                    <table cellspacing="0" cellpadding="0" border="0" width="100%">
                    	<tr>
                    		<td class="portal-column-td" width="32%">
                    			<div style="overflow:hidden;padding:0 0 0 0">
	        						<div class="panel" style="margin-bottom:2px;">
	        							<div class="panel-header">
	        								<div class="panel-title">新增文章数</div>
	        								<div class="panel-tool">
												<s:select id="yearCreate" list="years" name="yearCreate" onchange="selectYearCreate(this);"/>
											</div>
	        							</div>
	        							<div style="height: 200px; padding: 5px;" closable="true" collapsible="false" class="portal-p panel-body">
	        								<div id="chartDiv" align="center">新增文章数</div>
											<script type="text/javascript">
												function selectYearCreate(select){
													$('#yearCreate').val(select.value);
													showCreateChart();
											  	}
											  	function showCreateChart(){
												  	$.post("<s:url action='createArticle'/>", {yearCreate : $('#yearCreate').val()}, function(result) {
														var myChart = new FusionCharts("<s:url value='/ewcmssource/fcf/swf/Column3D.swf'/>?ChartNoDataText=无数据显示", "myChartId", "400", "200");
												      	myChart.setDataXML(result);      
												      	myChart.render("chartDiv");
												   	});  
											  	}
											  	showCreateChart();
											</script>
				    					</div>
				    				</div>
        						</div>
        					</td>
        					<td width="1%"></td>
        					<td class="portal-column-td" width="32%">
                    			<div style="overflow:hidden;padding:0 0 0 0">
	        						<div class="panel" style="margin-bottom:2px;">
	        							<div class="panel-header">
	        								<div class="panel-title">发布文章数</div>
	        								<div class="panel-tool">
												<s:select id="yearRelease" list="years" name="yearRelease" onchange="selectYearRelease(this);"/>
											</div>
	        							</div>
	        							<div style="height: 200px; padding: 5px;" closable="true" collapsible="false" class="portal-p panel-body">
	        								<div id="lineDiv" align="center">发布文章数</div>
										  	<script type="text/javascript">
										  		function selectYearRelease(select){
											  		$('#yearRelease').val(select.value);
											  		showReleaseChart();
										  		}
										  		function showReleaseChart(){
												  	$.post("<s:url action='releaseArticle'/>", {yearRelease : $('#yearRelease').val()}, function(result) {
												  		var myChart = new FusionCharts("<s:url value='/ewcmssource/fcf/swf/Line.swf'/>?ChartNoDataText=无数据显示", "myChartId", "400", "200");
											      		myChart.setDataXML(result);      
											      		myChart.render("lineDiv");
											   		});  
										  		}
										  		showReleaseChart();
										  	</script>
				    					</div>
				    				</div>
        						</div>
        					</td>
        					<td width="1%"></td>
        					<td class="portal-column-td" width="32%">
                    			<div style="overflow:hidden;padding:0 0 0 0">
	        						<div class="panel" style="margin-bottom:2px;">
	        							<div class="panel-header">
	        								<div class="panel-title">发布文章人员</div>
	        								<div class="panel-tool">
												<s:select id="yearPerson" list="years" name="yearPerson" onchange="selectYearPerson(this);"/>
											</div>
	        							</div>
	        							<div style="height: 200px; padding: 5px;" closable="true" collapsible="false" class="portal-p panel-body">
	        								<div id="pieDiv" align="center">发布文章人员</div>
										  	<script type="text/javascript">
										  		function selectYearPerson(select){
											  		$('#yearPerson').val(select.value);
											  		showPersonChart();
										  		}
										  		function showPersonChart(){
												  	$.post("<s:url action='releaseArticlePerson'/>", {yearPerson : $('#yearPerson').val()}, function(result) {
												  		var myChart = new FusionCharts("<s:url value='/ewcmssource/fcf/swf/Pie3D.swf'/>?ChartNoDataText=无数据显示", "myChartId", "400", "200");
											      		myChart.setDataXML(result);
											      		myChart.render("pieDiv");
											   		});  
										  		}
										  		showPersonChart();
										  	</script>
				    					</div>
				    				</div>
        						</div>
        					</td>
        				</tr>
                    	<tr>
                    		<td class="portal-column-td" width="32%">
                    			<div style="overflow:hidden;padding:0 0 0 0">
	        						<div class="panel" style="margin-bottom:2px;">
	        							<div class="panel-header">
	        								<div class="panel-title">待办事栏</div>
	        								<div class="panel-tool"><a href="javascript:void(0);" onclick="" style="text-decoration:none;"></a></div>
	        							</div>
	        							<div id="other" style="height: 160px; padding: 5px;" closable="true" collapsible="false" id="other" class="portal-p panel-body">
				    					</div>
				    				</div>
        						</div>
        					</td>
        					<td width="1%"></td>
                    		<td class="portal-column-td" width="32%">
                    			<div style="overflow:hidden;padding:0 0 0 0">
	        						<div class="panel" style="margin-bottom:2px;">
	        							<div class="panel-header">
	        								<div class="panel-title">公告栏</div>
	        								<div class="panel-tool"><a href="javascript:void(0);" onclick="javascript:_home.addTab('公告栏信息','message/more/index.do?type=NOTICE');return false;" style="text-decoration:none;display:inline;">更多...</a></div>
	        							</div>
	        							<div style="height: 160px; padding: 5px;" closable="true" collapsible="false" title="" id="notice" class="portal-p panel-body">
				    					</div>
				    				</div>
        						</div>
        					</td>
        					<td width="1%"></td>
                    		<td class="portal-column-td" width="32%">
                    			<div style="overflow:hidden;padding:0 0 0 0">
	        						<div class="panel" style="margin-bottom:2px;">
	        							<div class="panel-header">
	        								<div class="panel-title">订阅栏</div>
	        								<div class="panel-tool"><a href="javascript:void(0);" onclick="javascript:_home.addTab('订阅栏信息','message/more/index.do?type=SUBSCRIPTION');return false;" style="text-decoration:none;display:inline;">更多...</a></div>
	        							</div>
	        							<div style="height: 160px; padding: 5px;" closable="true" collapsible="false" id="subscription" class="portal-p panel-body">
				    					</div>
				    				</div>
        						</div>
        					</td>
        				</tr>
			         </table>
                </div>
            </div>
        </div>
        <div id="edit-window" class="easyui-window" closed="true" icon="icon-winedit" style="display:none;">
            <div class="easyui-layout" fit="true">
                <div region="center" border="false" style="padding: 10px;" fit="true">
                    <iframe id="editifr" name="editifr" class="editifr" frameborder="0" onload="iframeFitHeight(this);" scrolling="auto"></iframe>
                </div>
                <div region="south" border="false" style="padding-right:20px;text-align:right;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-save" href="javascript:void(0)" onclick="window.frames['editifr'].pageSubmit();">确定</a>
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