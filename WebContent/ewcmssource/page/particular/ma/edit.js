var st, employeName, employeUrl, treeUrl, organId;

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
	$('#cc_employeBasic').combogrid({
		panelWidth : 870,
		mode : 'remote',
		idField : 'cardCode',
		textField : 'name',
		pagination : true,
		striped : true,
		rownumbers : true,
		collapsible : false,
		fit : true,
		pageSize : 5,
		pageList : [ 5 ],
		url : employeUrl + '?name=' + employeName + '',
		columns : [ [ {
			field : 'id',
			title : '编号',
			hidden : true
		}, {
			field : 'organ_name',
			title : '发布部门',
			width : 200,
			formatter : function(val, rec) {
				return (rec.organ == null) ? "" : rec.organ.name;
			}
		}, {
			field : 'name',
			title : '姓名',
			width : 150,
			sortable : true
		}, {
			field : 'sexDescription',
			title : '性别',
			width : 60
		}, {
			field : 'published',
			title : '发布时间',
			width : 145
		}, {
			field : 'cardTypeDescription',
			title : '证件类型',
			width : 200
		}, {
			field : 'cardCode',
			title : '证件号码',
			width : 150
		}, {
			field : 'denseDescription',
			title : '所属密级',
			width : 100
		} ] ],
		onClickRow : function(rowIndex, rowData) {
			$('#employeBasic_cardCode').html(rowData.cardCode);
		},
		onSelect : function(rowIndex, rowData) {
			$("#employeBasic_cardCode").html(rowData.code);
		},
		onClick : function(rowIndex, rowData) {
			alert("1");
		}
	});
	var combo = $('#cc_employeBasic').data("combo").combo;
	var combo_text = combo.find(".combo-text");
	$(combo_text).val(employeName);
	combo_text.extChange(function(e) {
		if (st) {
			clearTimeout(st);
		}
		var options = $('#cc_employeBasic').combogrid('options');
		st = setTimeout(function() {
			var grid = $('#cc_employeBasic').combogrid('grid');
			options.keyHandler.query.call($('#cc_employeBasic')[0], $(
					combo_text).val());
			$(grid).datagrid({
				url : employeUrl + '?name=' + $(combo_text).val() + ''
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
	$('#tt_organ').combotree('setValue', organId);

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