/**
 * Article Index JavaScript Library v1.0.0
 * 
 * Licensed under the GPL terms To use it on other terms please contact us
 * 
 * author wu_zhijun
 */
var currentnode, rootnode;

function initSubMenu() {
	$('#btnSort .l-btn-left').attr('class', 'easyui-linkbutton').menubutton({menu : '#btnSortSub'});
	$('#btnReview .l-btn-left').attr('class', 'easyui-linkbutton').menubutton({menu : '#btnReviewSub'});
	$('#btnPub .l-btn-left').attr('class', 'easyui-linkbutton').menubutton({menu : '#btnPubSub'});
}
function channelPermission(rootnode, currentnode) {
	initSubMenu();
	if (rootnode.id == currentnode.id) {
		disableButtons();
		$('#btnSearch').linkbutton('disable');
		$('#btnBack').linkbutton('disable');
		if (currentnode.attributes.maxpermission >= 4) {
			$('#btnPub').linkbutton('enable');
			$('#btnPublishOk').attr('style', 'display:block;');
		}
		return;
	}
	if (currentnode.attributes.maxpermission == 1) {
		disableButtons();
		return;
	}
	if (currentnode.attributes.maxpermission == 2) {
		enableButtons();
		$('#btnPublishOk').attr('style', 'display:none;');
		$('#btnBreakArticle').attr('style', 'display:none;');
		return;
	}
	if (currentnode.attributes.maxpermission >= 4) {
		enableButtons();
		return;
	}
}

function articleReload() {
	var url = ewcmsOOBJ.getQueryURL();
	url = url + '?channelId=' + currentnode.id;
	$('#tt').datagrid( {
		pageNumber : 1,
		url : url
	});
	channelPermission(rootnode, currentnode);
}

function addOperate() {
	if (currentnode.id != 0 && currentnode.id != $('#tt2').tree('getRoot').id) {
		var url_param = '?channelId=' + currentnode.id + '';
		window.open(ewcmsOOBJ.getInputURL() + url_param,'popup','width=1280,height=700,resizable=yes,toolbar=no,directories=no,location=no,menubar=no,status=no,left=' + (window.screen.width - 1280)/ 2 + ',top=' + (window.screen.height - 700) / 2);
	} else {
		$.messager.alert('提示', '请选择栏目', 'info');
	}
	return false;
}
function updOperate() {
	if (currentnode.id != $('#tt2').tree('getRoot').id) {
		var rows = $('#tt').datagrid('getSelections');
		if (rows.length == 0) {
			$.messager.alert('提示', '请选择修改记录', 'info');
			return;
		}
		if (rows.length > 1) {
			$.messager.alert('提示', '只能选择一个修改', 'info');
			return;
		}
		if (rows[0].reference == true) {
			$.messager.alert('提示', '引用文章不能修改', 'info');
			return;
		}
		if (rows[0].article.statusDescription == '初稿' || rows[0].article.statusDescription == '重新编辑') {
			var url_param = '?channelId=' + currentnode.id + '&selections='	+ rows[0].id;
			window.open(ewcmsOOBJ.getInputURL() + url_param,'popup','width=1280,height=700,resizable=yes,toolbar=no,directories=no,location=no,menubar=no,status=no,left=' + (window.screen.width - 1280) / 2 + ',top=' + (window.screen.height - 700)/ 2);
		} else {
			$.messager.alert('提示', '文章只能在初稿或重新编辑状态在才能修改', 'info');
			return;
		}
	} else {
		$.messager.alert('提示', '请选择栏目', 'info');
		return;
	}
	return false;
}

function delOperate() {
	var rows = $('#tt').datagrid('getSelections');
	if (rows.length == 0) {
		$.messager.alert('提示', '请选择删除记录', 'info');
		return;
	}

	var url = ewcmsOOBJ.getDeleteURL();
	var parameter = 'channelId=' + currentnode.id;
	for ( var i = 0; i < rows.length; ++i) {
		parameter = parameter + '&selections=' + rows[i].id;
	}
	$.messager.confirm('提示', '确定要删除所选记录到回收站吗?', function(r) {
		if (r) {
			$.post(url, parameter, function(data) {
				$.messager.alert('成功', '删除文档到回收站成功!', 'info');
				$('#tt').datagrid('clearSelections');
				$('#tt').datagrid('reload');
			});
		}
	});
}

function initOperateQuery() {
	var url = ewcmsOOBJ.getQueryURL();
	url = url + '?channelId=' + currentnode.id;
	$('#tt').datagrid( {
		pageNumber : 1,
		url : url
	});
	initSubMenu();
}

function querySearch_Article(url) {
	var value = $('#queryform').serialize();
	value = 'parameters[\'' + value;
	value = value.replace(/\=/g, '\']=');
	value = value.replace(/\&/g, '&parameters[\'');

	url += '?channelId=' + currentnode.id + '&' + value;
	$('#tt').datagrid( {
		pageNumber : 1,
		url : url
	});

	$('#query-window').window('close');
	initSubMenu();
}
function moveArticle(url) {
	var selected = $('#tt3').tree('getSelected');
	var rootnode_tt3 = $('#tt3').tree('getRoot');

	var parameter = 'channelId=' + currentnode.id;
	var rows = $('#tt').datagrid('getSelections');
	for ( var i = 0; i < rows.length; i++) {
		parameter = parameter + '&selections=' + rows[i].id;
	}

	if (selected.id != rootnode_tt3.id) {
		parameter = parameter + '&selectChannelIds=' + selected.id;
	} else {
		$.messager.alert('提示', '文章不能移动到根栏目', 'info');
		return;
	}

	$.post(url, parameter, function(data) {
		if (data == 'true') {
			$.messager.alert('成功', '移动文章成功', 'info');
			$('#tt3').tree('reload');
			$('#tt').datagrid('clearSelections');
			$('#tt').datagrid('reload');
		}
		$('#moveorcopy-window').window('close');
	});
}

function copyArticle(url) {
	var checkeds = $('#tt3').tree('getChecked')
	var rootnode_tt3 = $('#tt3').tree('getRoot');

	var parameter = 'channelId=' + currentnode.id;
	var rows = $('#tt').datagrid('getSelections');
	for ( var i = 0; i < rows.length; i++) {
		parameter = parameter + '&selections=' + rows[i].id;
	}

	for ( var i = 0; i < checkeds.length; i++) {
		if (checkeds[i].id != rootnode_tt3.id) {
			parameter = parameter + '&selectChannelIds=' + checkeds[i].id;
		}
	}
	$.post(url, parameter, function(data) {
		if (data == 'true') {
			$.messager.alert('成功', '复制文章成功', 'info');
			$('#tt3').tree('reload');
			$('#tt').datagrid('clearSelections');
			$('#tt').datagrid('reload');
		}
		$('#moveorcopy-window').window('close');
	});
}

function closeCannel() {
	$('#moveorcopy-window').window('close');
}

function submitReviewOperate(url) {
	var rows = $('#tt').datagrid('getSelections');
	if (rows.length == 0) {
		$.messager.alert('提示', '请选择提交审核记录', 'info');
		return;
	}
	var parameter = 'channelId=' + currentnode.id;
	var rows = $('#tt').datagrid('getSelections');
	for ( var i = 0; i < rows.length; i++) {
		parameter = parameter + '&selections=' + rows[i].id;
	}
	$.post(url, parameter, function(data) {
		if (data != 'true') {
			if (data == 'system-false') {
				$.messager.alert('提示', '文章提交审核失败', 'info');
			} else if (data == 'accessdenied') {
				$.messager.alert('提示', '您没有提交审核文章的权限', 'info');
			} else if (data == 'notinstate') {
				$.messager.alert('提示', '文章只有在初稿或重新编辑状态下才能提交审核', 'info');
			}
			return;
		} else {
			$('#tt').datagrid('clearSelections');
			articleReload();
			$.messager.alert('提示', '文章提交审核成功', 'info');
			return;
		}
	});
	return false;
}
function pubOperate(url) {
	var parameter = 'channelId=' + currentnode.id + '';
	$.post(url, parameter, function(data) {
		if (data == 'system-false') {
			$.messager.alert('提示', '系统错误', 'error');
			return;
		} else if (data == 'accessdenied') {
			$.messager.alert('提示', '没有发布权限', 'info');
			return;
		} else {
			$.messager.alert('提示', '发布成功', 'info');
			return;
		}
	});
	return false;
}
function reviewArticle(url) {
	var rows = $('#tt').datagrid('getSelections');
	if (rows.length == 0) {
		$.messager.alert('提示', '请选择审核记录', 'info');
		return;
	}
	if (rows.length > 1) {
		$.messager.alert('提示', '只能选择一个审核', 'info');
		return;
	}

	var parameter = {};
	parameter['review'] = $('input[name=\'reviewRadio\']:checked').val();
	parameter['channelId'] = currentnode.id;
	parameter['selections'] = rows[0].id;
	parameter['reason'] = $('#reason').val();

	$.post(url, parameter, function(data) {
		$('#review-window').window('close');
		if (data == 'system-false') {
			$.messager.alert('提示', '文章审核失败', 'info');
		} else if (data == 'true') {
			$('#tt').datagrid('clearSelections');
			articleReload();
			$.messager.alert('提示', '文章审核成功', 'info');
		}
		return;
	});
	return false;
}
var sort = '';
function sortOperate(isUrl, url) {
	if (currentnode.id != $('#tt2').tree('getRoot').id) {
		var rows = $('#tt').datagrid('getSelections');
		if (rows.length == 0) {
			$.messager.alert('提示', '请选择排序记录', 'info');
			return;
		}
		if (rows.length > 1) {
			$.messager.alert('提示', '只能选择一个排序', 'info');
			return;
		}
		$.messager.prompt('是否要对所选中的文章进行排序',	'请输入排序号',function(r) {
			if (r) {
				var reg = /^\d+$/;
				if (reg.test(r)) {
					$.post(isUrl, {'selections' : $('#tt').datagrid('getSelections')[0].id,'channelId' : currentnode.id,'isTop' : $('#tt').datagrid('getSelections')[0].article.topFlag,'sort' : r},function(data) {
						if (data == 'true') {
							sort = r;
							ewcmsBOBJ.openWindow('#sort-window',{width : 550,height : 200,title : '排序'});
							return;
						} else if (data == 'false') {
							$.post(url,{'selections' : $('#tt').datagrid('getSelections')[0].id,'channelId' : currentnode.id,'isTop' : $('#tt').datagrid('getSelections')[0].article.topFlag,'sort' : r},function(data) {
								if (data == 'true') {
									$.messager.alert('提示','设置排序号成功','info');
									$('#tt').datagrid('clearSelections');
									articleReload();
								} else if (data == 'false') {
									$.messager.alert('提示','设置排序号失败','info');
								} else if (data == 'system-false') {
									$.messager.alert('提示','系统错误','error');
								}
								return;
							});
						} else if (data == 'system-false') {
							$.messager.alert('提示','系统错误','error');
							return;
								}
					});
				} else {
					sortOperate();
					return;
				}
			}
		});
	}
	return false;
}
function sortArticle(url) {
	$.post(url, {
		'selections' : $('#tt').datagrid('getSelections')[0].id,
		'channelId' : currentnode.id,
		'isTop' : $('#tt').datagrid('getSelections')[0].article.topFlag,
		'isInsert' : $('input[name=\'sortRadio\']:checked').val(),
		'sort' : sort
	}, function(data) {
		$('#sort-window').window('close');
		if (data == 'true') {
			$.messager.alert('提示', '设置排序号成功', 'info');
			$('#tt').datagrid('clearSelections');
			articleReload();
		} else if (data == 'false') {
			$.messager.alert('提示', '设置排序号失败', 'info');
		} else if (data == 'system-false') {
			$.messager.alert('提示', '系统错误', 'error');
		}
		return;
	});
	return false;
}
function clearSortOperate(url) {
	if (currentnode.id != $('#tt2').tree('getRoot').id) {
		var rows = $('#tt').datagrid('getSelections');
		if (rows.length == 0) {
			$.messager.alert('提示', '请选择清除排序记录', 'info');
			return;
		}
		var parameter = 'channelId=' + currentnode.id;
		for ( var i = 0; i < rows.length; i++) {
			parameter = parameter + '&selections=' + rows[i].id;
		}
		$.post(url, parameter, function(data) {
			if (data == 'true') {
				$.messager.alert('提示', '设置消除排序号成功', 'info');
				$('#tt').datagrid('clearSelections');
				articleReload();
			} else if (data == 'system-false') {
				$.messager.alert('提示', '系统错误', 'error');
			}
			return;
		});
	}
	return false;
}
function breakOperate(url) {
	var rows = $('#tt').datagrid('getSelections');
	if (rows.length == 0) {
		$.messager.alert('提示', '请选择退回记录', 'info');
		return;
	}
	if (rows.length > 1) {
		$.messager.alert('提示', '只能选择一个退回', 'info');
		return;
	}

	var parameter = {};
	parameter['channelId'] = currentnode.id;
	parameter['selections'] = rows[0].id;

	$.post(url, parameter, function(data) {
		if (data != 'true') {
			if (data == 'system-false') {
				$.messager.alert('提示', '文章退回失败', 'info');
			} else if (data == 'accessdenied') {
				$.messager.alert('提示', '没有退回权限', 'info');
			} else if (data == 'notinstate') {
				$.messager.alert('提示', '文章只有在发布版或已发布版状态下才能退回', 'info');
			}
			return;
		} else {
			$('#tt').datagrid('clearSelections');
			articleReload();
			$.messager.alert('提示', '文章退回成功', 'info');
			return;
		}
	});
	return false;
}
function reviewOperate() {
	var rows = $('#tt').datagrid('getSelections');
	if (rows.length == 0) {
		$.messager.alert('提示', '请选择审核的文章', 'info');
		return;
	}
	if (rows.length > 1) {
		$.messager.alert('提示', '只能选择一个审核', 'info');
		return;
	}
	if (rows[0].article.status == 'REVIEW') {
		ewcmsBOBJ.openWindow('#review-window', {
			width : 550,
			height : 230,
			title : '审核'
		});
	} else {
		$.messager.alert('提示', '文章只能在审核中状态才能审核', 'info');
	}
}
function moveOperate() {
	$('#tt3').tree( {
		checkbox : false,
		url : treeURL
	});
	var rows = $('#tt').datagrid('getSelections');
	if (rows.length == 0) {
		$.messager.alert('提示', '请选择移动记录', 'info');
		return;
	}
	$('#span_move').attr('style', '');
	$('#span_copy').attr('style', 'display:none');
	ewcmsBOBJ.openWindow('#moveorcopy-window', {
		title : '移动文章选择',
		width : 300,
		height : 400
	});
}

function copyOperate() {
	$('#tt3').tree( {
		checkbox : true,
		url : treeURL
	});
	var rows = $('#tt').datagrid('getSelections');
	if (rows.length == 0) {
		$.messager.alert('提示', '请选择复制记录', 'info');
		return;
	}
	$('#span_move').attr('style', 'display:none');
	$('#span_copy').attr('style', '');
	ewcmsBOBJ.openWindow('#moveorcopy-window', {
		width : 300,
		height : 400,
		title : '复制文章选择'
	});
}
function channelTreeLoad() {
	$('#tt2').tree('reload');
}
function disableButtons() {
	$('#btnAdd').linkbutton('disable');
	$('#btnUpd').linkbutton('disable');
	$('#btnRemove').linkbutton('disable');
	$('#btnCopy').linkbutton('disable');
	$('#btnMove').linkbutton('disable');
	$('#btnSort').linkbutton('disable');
	$('#btnReview').linkbutton('disable');
	$('#btnPub').linkbutton('disable');
	$('#btnSortSet').attr('style', 'display:none;');
	$('#btnSortClear').attr('style', 'display:none;');
	$('#btnReviewSubmit').attr('style', 'display:none;');
	$('#btnReviewProcess').attr('style', 'display:none;');
	$('#btnPublishOk').attr('style', 'display:none;');
	$('#btnBreakArticle').attr('style', 'display:none;');
}
function enableButtons() {
	$('#btnAdd').linkbutton('enable');
	$('#btnUpd').linkbutton('enable');
	$('#btnRemove').linkbutton('enable');
	$('#btnCopy').linkbutton('enable');
	$('#btnMove').linkbutton('enable');
	$('#btnSort').linkbutton('enable');
	$('#btnReview').linkbutton('enable');
	$('#btnPub').linkbutton('enable');
	$('#btnSortSet').attr('style', 'display:block;');
	$('#btnSortClear').attr('style', 'display:block;');
	$('#btnReviewSubmit').attr('style', 'display:block;');
	$('#btnReviewProcess').attr('style', 'display:block;');
	$('#btnPublishOk').attr('style', 'display:block;');
	$('#btnBreakArticle').attr('style', 'display:block;');
}
function showReason(url){
	$('#editifr_reason').attr('src',url);
	ewcmsBOBJ.openWindow('#reason-window',{width:600,height:300,title:'原因'});
}