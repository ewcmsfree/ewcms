var st, projectName, projectUrl, treeUrl, organId;

(function($) {
	$.fn.extChange = function(callback) {
		var value = $(this).val();
		setInterval($.proxy(function() {
			if (callback) {
				var newValue = $(this).val();
				if (value != newValue) {
					callback.call(this, window.event);
					value = newValue;
				}
			}
		}, this), 300);
	};
})(jQuery);

$(function() {
	$('#cc_projectBasic').combogrid(
			{
				panelWidth : 870,
				panelHeight : 400,
				idField : 'code',
				textField : 'name',
				pagination : true,
				striped : true,
				rownumbers : true,
				collapsible : false,
				fit : true,
				pageSize : 30,
				pageList : [ 30 ],
				url : projectUrl + '?name=' + projectName + '',
				columns : [ [
						{
							field : 'id',
							title : '编号',
							hidden : true
						},
						{
							field : 'organ_name',
							title : '发布部门',
							width : 200,
							formatter : function(val, rec) {
								return (rec.organ == null) ? ""
										: rec.organ.name;
							}
						},
						{
							field : 'code',
							title : '项目编号',
							width : 150,
							sortable : true
						},
						{
							field : 'name',
							title : '项目名称',
							width : 200
						},
						{
							field : 'buildTime',
							title : '建设时间',
							width : 85
						},
						{
							field : 'investmentScale',
							title : '投资规模',
							width : 200
						},
						{
							field : 'overview',
							title : '项目概况',
							width : 300
						},
						{
							field : 'buildUnit',
							title : '建设单位',
							width : 200
						},
						{
							field : 'unitId',
							title : '项目编号',
							width : 60
						},
						{
							field : 'unitPhone',
							title : '单位联系电话',
							width : 100
						},
						{
							field : 'unitAddress',
							title : '单位地址',
							width : 200
						},
						{
							field : 'zoningName',
							title : '行政区划名称',
							width : 80,
							formatter : function(val, rec) {
								return (rec.zoningCode == null) ? ""
										: rec.zoningCode.name;
							}
						},
						{
							field : 'organizationCode',
							title : '组织机构代码',
							width : 80
						},
						{
							field : 'industryName',
							title : '行业名称',
							width : 80,
							formatter : function(val, rec) {
								return (rec.industryCode == null) ? ""
										: rec.industryCode.name;
							}
						},
						{
							field : 'approvalRecordName',
							title : '审批备案机关名称',
							width : 120,
							formatter : function(val, rec) {
								return (rec.approvalRecord == null) ? ""
										: rec.approvalRecord.name;
							}
						}, {
							field : 'contact',
							title : '联系人',
							width : 120
						}, {
							field : 'phone',
							title : '联系人电话',
							width : 100
						}, {
							field : 'email',
							title : '联系人电子邮箱',
							width : 120
						}, {
							field : 'address',
							title : '项目地址',
							width : 200
						}, {
							field : 'natureDescription',
							title : '建设性质',
							width : 100
						}, {
							field : 'shape',
							title : '形式',
							width : 100
						}, {
							field : 'documentId',
							title : '文号',
							width : 100
						}, {
							field : 'participation',
							title : '参建单位',
							width : 200
						} ] ],
				onClickRow : function(rowIndex, rowData) {
					$('#projectBasic_code').html(rowData.code);
				},
				onSelect : function(rowIndex, rowData) {
					$("#projectBasic_code").html(rowData.code);
				}
			});
	var combo = $('#cc_projectBasic').data("combo").combo;
	var combo_text = combo.find(".combo-text");
	$(combo_text).val(projectName);
	combo_text.extChange(function(e) {
		if (st) {
			clearTimeout(st);
		}
		var options = $('#cc_projectBasic').combogrid('options');
		st = setTimeout(function() {
			var grid = $('#cc_projectBasic').combogrid('grid');
			options.keyHandler.query.call($('#cc_projectBasic')[0], $(
					combo_text).val());
			$(grid).datagrid({
				url : projectUrl + '?name=' + $(combo_text).val() + ''
			});
		}, options.delay);
	});
	$('#tt_organ').combotree({
		url : treeUrl,
		onBeforeSelect : function(node) {
			if (node.id == null) {
				$.messager.alert('提示', '根节点不能选择', 'info');
				return;
			}
		}
	});
	$('#tt_organ').combotree($('#organShow').val());
	$("#tt_organ").combotree("setValue", organId);

	var height = $(window).height() - $("#inputBarTable").height() - 38;
	var width = $(window).width() - 30 * 2;
	$("div #_DivContainer").css("height", height + "px");
	try {
		if (tinyMCE.getInstanceById('_Content_1') != null) {
			tinyMCE.getInstanceById('_Content_1').theme.resizeTo(width,
					(height - 110));
		} else {
			$("#_Content_1").css("width", (width + 2) + "px");
			$("#_Content_1").css("height", (height - 42) + "px");
		}
	} catch (errRes) {
	}
});
parent.$(window)
		.resize(
				function() {
					var height = $(window).height()
							- $("#inputBarTable").height() - 38;
					var width = $(window).width() - 30 * 2;
					$("div #_DivContainer").css("height", height + "px");
					try {
						if (tinyMCE.getInstanceById('_Content_1') != null) {
							tinyMCE.getInstanceById('_Content_1').theme
									.resizeTo(width, (height - 135));
						} else {
							$("#_Content_1").css("width", (width + 2) + "px");
							$("#_Content_1").css("height",
									(height - 142) + "px");
						}
					} catch (errRes) {
					}
				});