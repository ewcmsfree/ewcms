var st, enterpriseName, enterpriseUrl, treeUrl, organId;

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
	$('#cc_enterpriseBasic').combogrid({
		panelWidth:870,
		panelHeight:400,
		idField:'yyzzzch',
		textField:'name',
		pagination : true,
        striped: true,  
        rownumbers:true,
        collapsible:false,
        fit: true,
        pageSize: 30,
        pageList: [30],
		url:enterpriseUrl + '?name=' + enterpriseName + '',
		columns:[[
				{field:'id',title:'编号',hidden:true},
                {field:'organ_name',title:'发布部门',width:200,
					formatter : function(val, rec){
						return (rec.organ == null) ? "" : rec.organ.name;
					}	
                },
				{field:'yyzzzch',title:'营业执照注册号',width:150,sortable:true},
                {field:'name',title:'企业名称',width:200},
                {field:'published',title:'发布时间',width:85},
                {field:'yyzzdjjg',title:'营业执照登记机关',width:200},
                {field:'frdb',title:'法人代表',width:300},
                {field:'clrq',title:'成立日期',width:200},
                {field:'jyfw',title:'经营范围',width:60},
                {field:'zzjgdjjg',title:'组织机构登记机关',width:100},
                {field:'zzjgdm',title:'组织机构代码',width:200},
                {field:'qyrx',title:'企业类型',width:80,},
                {field:'zzzb',title:'注册资本',width:80},
                {field:'sjzzzb',title:'实缴注册资本',width:80},
                {field:'jyqx',title:'经营期限',width:120},
                {field:'zs',title:'住所',width:120},
                {field:'denseDescription',title:'所属密级',width:100}
		]],
		onClickRow : function(rowIndex, rowData){
			$('#enterpriseBasic_yyzzch').html(rowData.yyzzzch);
		},
		onSelect : function(rowIndex, rowData){
			$("#enterpriseBasic_yyzzch").html(rowData.yyzzzch);
		}
	});
	var combo = $('#cc_enterpriseBasic').data("combo").combo;
	var combo_text = combo.find(".combo-text");
	$(combo_text).val(enterpriseName);
	combo_text.extChange(function(e) {
		if (st) {
			clearTimeout(st);
		}
		var options = $('#cc_enterpriseBasic').combogrid('options');
		st = setTimeout(function() {
			var grid = $('#cc_enterpriseBasic').combogrid('grid');
			options.keyHandler.query.call($('#cc_enterpriseBasic')[0], $(
					combo_text).val());
			$(grid).datagrid({
				url : enterpriseUrl + '?name=' + $(combo_text).val() + ''
			});
		}, options.delay);
	});
    $('#tt_organ').combotree({
    	url : treeUrl,
    	onBeforeSelect: function(node){
            if (node.id == null) {
           		$.messager.alert('提示','根节点不能选择','info');
           		return;
           	}
    	}
    });
    $('#tt_organ').combotree($('#organShow').val());
    $("#tt_organ").combotree("setValue", organId);

    var height = $(window).height() - $("#inputBarTable").height() - 38;
	var width = $(window).width() - 30*2;
	$("div #_DivContainer").css("height",height + "px");
	try{
		if (tinyMCE.getInstanceById('_Content_1') != null){
			tinyMCE.getInstanceById('_Content_1').theme.resizeTo(width,(height - 110));
		}else{
			$("#_Content_1").css("width", (width + 2) + "px");
			$("#_Content_1").css("height", (height - 42) + "px");
		}
	}catch(errRes){
	}
});
parent.$(window).resize(function(){
		var height = $(window).height() - $("#inputBarTable").height() - 38;
	var width = $(window).width() - 30*2;
    $("div #_DivContainer").css("height",height + "px");
    try{
    	if (tinyMCE.getInstanceById('_Content_1') != null){
    		tinyMCE.getInstanceById('_Content_1').theme.resizeTo(width,(height - 135));
    	}else{
    		$("#_Content_1").css("width", (width + 2) + "px");
    		$("#_Content_1").css("height", (height - 142) + "px");
    	}
    }catch(errRes){
    }
});