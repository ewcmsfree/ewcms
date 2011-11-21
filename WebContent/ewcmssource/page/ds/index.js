var connectTest;
function test(id){
	$.post(connectTest, {"id":id}, function(data) {
		$.messager.alert('提示', data, 'info');
	});
	return false;
}
