<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="ewcms" uri="/ewcms-tags"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>政民互动统计</title>	
		<s:include value="../../taglibs.jsp"/>
		<script type="text/javascript">
			$(function() {
				$('#tt').treegrid({
					rownumbers: true,  
	                collapsible: true,
	                striped : true,
					url : '<s:url namespace="/plugin/visit" action="interactiveTable"/>?startDate=' + $('#startDate').val() + '&endDate=' + $('#endDate').val(),
					idField:'id',
					treeField: 'text',
					frozenColumns:[[
						{field:'id',title:'组织编号',rowspan:2,width:60},
						{field:'text',title:'组织名称',rowspan:2,width:200}
				    ]],
				    columns:[[
			            {title:'在线咨询',colspan:4},
			            {title:'投诉监督',colspan:4},
			            {title:'建言献策',colspan:4},
			            {title:'总数',colspan:4}
			            ],[
			            {field:'zxblCount',title:'办理中',width:60,
			            	formatter : function(val, rec){
			            		if (rec.data != null)
			            		return rec.data.zxblCount;
			            	}	
			            },  
			            {field:'zxhfCount',title:'已回复',width:60,
			            	formatter : function(val, rec){
			            		if (rec.data != null)
			            		return rec.data.zxhfCount;
			            	}	
			            },
			            {field:'zxtgCount',title:'已通过',width:60,
			            	formatter : function(val, rec){
			            		if (rec.data != null)
			            		return rec.data.zxtgCount;
			            	}	
			            },
			            {field:'zxwtgCount',title:'未通过',width:60,
			            	formatter : function(val, rec){
			            		if (rec.data != null)
			            		return rec.data.zxwtgCount;
			            	}	
			            },
				        {field:'tsblCount',title:'办理中',width:60,
				           	formatter : function(val, rec){
				           		if (rec.data != null)
				           		return rec.data.tsblCount;
				           	}	
				        },  
				        {field:'tshfCount',title:'已回复',width:60,
				           	formatter : function(val, rec){
				           		if (rec.data != null)
				           		return rec.data.tshfCount;
				           	}	
				        },
				        {field:'tstgCount',title:'已通过',width:60,
				           	formatter : function(val, rec){
				           		if (rec.data != null)
				           		return rec.data.tstgCount;
				           	}	
				        },
				        {field:'tswtgCount',title:'未通过',width:60,
				           	formatter : function(val, rec){
				          		if (rec.data != null)
				           		return rec.data.tswtgCount;
				        	}	
				        },
				        {field:'jyblCount',title:'办理中',width:60,
				           	formatter : function(val, rec){
				           		if (rec.data != null)
				           		return rec.data.jyblCount;
				           	}	
				        },  
				        {field:'jyhfCount',title:'已回复',width:60,
				           	formatter : function(val, rec){
				           		if (rec.data != null)
				           		return rec.data.jyhfCount;
				           	}	
				        },
				        {field:'jytgCount',title:'已通过',width:60,
				           	formatter : function(val, rec){
				           		if (rec.data != null)
				           		return rec.data.jytgCount;
				           	}	
				        },
				        {field:'jywtgCount',title:'未通过',width:60,
				           	formatter : function(val, rec){
				          		if (rec.data != null)
				           		return rec.data.jywtgCount;
				        	}	
				        },
			            {field:'blsumCount',title:'办理中',width:60,
			            	formatter : function(val, rec){
			            		if (rec.data != null)
			            		return rec.data.zxblCount + rec.data.tsblCount + rec.data.jyblCount;
			            	}	
			            },
			            {field:'hfsumCount',title:'已回复',width:60,
			            	formatter : function(val, rec){
			            		if (rec.data != null)
			            		return rec.data.zxhfCount + rec.data.tshfCount + rec.data.jyhfCount;
			            	}	
			            },
			            {field:'tgsumCount',title:'已通过',width:60,
			            	formatter : function(val, rec){
			            		if (rec.data != null)
			            		return rec.data.zxtgCount + rec.data.tstgCount + rec.data.jytgCount;
			            	}	
			            },
			            {field:'wtgsumCount',title:'未通过',width:60,
			            	formatter : function(val, rec){
			            		if (rec.data != null)
			            		return rec.data.zxwtgCount + rec.data.tswtgCount + rec.data.jywtgCount;
			            	}	
			            }
				    ]]
				});
			});
			function refresh(){
				$('#tt').treegrid({
					url : '<s:url namespace="/plugin/visit" action="interactiveTable"/>?startDate=' + $('#startDate').val() + '&endDate=' + $('#endDate').val()
				});
			}
		</script>
		<ewcms:datepickerhead></ewcms:datepickerhead>
	</head>
	<body class="easyui-layout">
		<div region="north" style="height:40px">
			<table width="100%" border="0" cellspacing="6" cellpadding="0"style="border-collapse: separate; border-spacing: 6px;">
				<tr>
					<td>
						当前报表：政民互动统计&nbsp;&nbsp;&nbsp;&nbsp;从 <ewcms:datepicker id="startDate" name="startDate" option="inputsimple" format="yyyy-MM-dd"/> 至 <ewcms:datepicker id="endDate" name="endDate" option="inputsimple" format="yyyy-MM-dd"/> <a class="easyui-linkbutton" href="javascript:void(0)" onclick="refresh();return false;">查看</a>
					</td>
				</tr>
			</table>
		</div>
		<div region="center">
			<table id="tt" fit="true"></table>
		</div>
	</body>
</html>