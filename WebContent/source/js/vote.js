function clickInput(id){
	document.getElementById(id+'_Button').checked = true;
}

function checkVote(id){
	var f = document.getElementById('voteForm_'+id);
	var dts = f.getElementsByTagName('dt');
	var arrs = [];
	for(var i=0;i<dts.length;i++){
		var subjectID = dts[i].getAttribute("id");
		arrs.push(subjectID);
	}
	
	var str='';
	var position = '';
	var err = false;
	
	for(var i=0;i<arrs.length;i++){
		var sid = arrs[i];
		var eles = document.getElementsByName('Subject_'+sid);
		var flag = false;
		if (eles!=null && eles.length==1) {
			if (eles[0].getAttribute("type")=="radio" || eles[0].getAttribute("type")=="checkbox") {
				if (eles[0].checked) {
					flag = true;
				}
			} else if (eles[0].value && eles[0].value.trim()) {
				continue;
			}
		} else {
			for(var j=0;j<eles.length;j++){
				if(eles[j].checked){
					flag = true;
					break;
				}
			}
		}
		
		if(!flag){
			err = true;
			if(document.getElementById(arrs[i])){
				if(document.getElementById(arrs[i]).innerText){
					str+='\n'+document.getElementById(arrs[i]).innerText;
				}else{
					str+='\n'+document.getElementById(arrs[i]).textContent;
				}
				if(!position){
					position = sid;
				}
			}
		}
	}
	var code = document.getElementsByName('j_checkcode');
	if (code != null && code.length == 1){
		if (code[0].value.trim() == ''){
			err = true;
			str+='\n验证码不能为空';
		}
	}
	if(err){
		var url = window.location+'';
		alert('您还有以下调查没有填写：'+str);
		window.location = url.substring(0,url.lastIndexOf('#'))+'#'+position;
		return false;
	}
	return true;
}
function codeRefresh(obj, url){
	obj.src = url + '?nocache=' + Math.random();
}