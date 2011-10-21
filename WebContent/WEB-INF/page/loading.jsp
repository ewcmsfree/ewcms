<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<script type="text/javascript">
	var left = (document.documentElement.clientWidth - 200)/2;
	var top = (document.documentElement.clientHeight - 100)/2;
	document.write('<div id="loading_mask" style="position: absolute; left: 0; top: 0; background: #ccc; opacity: 0.3; filter: alpha(opacity = 30); display: block; width: 100%; height: 100%"></div>')
	document.write('<div id="loading_mask_msg" style="position: absolute; cursor1: wait; width: 200px; height: 16px;top:' + top + 'px;left:' + left + 'px;  padding: 12px 5px 10px 30px; background: #fff url() no-repeat scroll 5px 10px; border: 2px solid #6593CF; color: #222; display: block;"><font size="9">页面正在加载，请稍候。。。</font></div>');
	document.onreadystatechange = function() {
		if (window.document.readyState == 'complete') {
			window.document.getElementById('loading_mask').style.display = 'none';
			window.document.getElementById('loading_mask_msg').style.display = 'none';
		}else{
			window.document.getElementById('loading_mask').style.display = 'block';
			window.document.getElementById('loading_mask_msg').style.display = 'block';
		}
	}
</script>
