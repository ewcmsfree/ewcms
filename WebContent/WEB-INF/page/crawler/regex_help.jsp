<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 

<html>
	<head>
		<title>表达式使用说明</title>
		<script type="text/javascript" src="<s:url value='/ewcmssource/js/loading.js'/>"></script>
	    <script type="text/javascript" src='<s:url value="/ewcmssource/js/skin.js"/>'></script>
	</head>
	<body>
		<font color="#0066FF"><b>表达式似于CSS（或JQuery）的选择器语法，来实现非常强大和灵活的查找功能。</b></font><p/>
		<b>1.概述</b>
		<ul>
			<li>tagname：通过标签查找元素，比如：a</li>
			<li>ns｜tag：通过标签在命名空间查找元素，比如：可以用fb｜name语法来查找&lt;fb:name&gt;元素</li>
			<li>#id：通过ID查找元素，比如：#logo</li>
			<li>.class：通过class名称查找元素，比如：.masthead</li>
			<li>[attribute]：利用属性查找元素，比如：[href]</li>
			<li>[^attr]：利用属性名前缀来查找元素，比如：可以用[^data-]来查找带有HTML5 Dataset属性的元素</li>
			<li>[attr=value]：利用属性值来查找元素，比如：[width=500]</li>
			<li>[attr^=value],[attr$=value],[attr*=value]：利用匹配属性值开头、结尾或包含属性值来查找元素，比如：[href*=/path/]</li>
			<li>[attr~=regex]：利用属性值匹配正则表达式来查找元素，比如：img[src~=(?i)\.(png|jpe?g)]</li>
			<li>*：这个符号将匹配所有元素</li>
		</ul>
		<b>2.组合使用</b>
		<ul>
			<li>el#id：元素＋ID，比如：div#logo</li>
			<li>el.class：元素＋class，比如：div.masthead</li>
			<li>el[attr]：元素＋attr，比如：a[href]</li>
			<li>任意组合，比如：a[href].highlight</li>
			<li>ancestor child：查找某个元素下子元素，比如：可以用.body p查找在“body”元素下的所有p元素</li>
			<li>parent &gt child：查找某个父元素下的直接子元素，比如：可以用div.content &gt ｐ查找ｐ元素，也可以用body &gt * 查找body标签下所有直接子元素</li>
			<li>siblingA + siblingB：查找在A元素之前第一个同级元素B，比如：div.head + div</li>
			<li>siblingA ~ siblinX：查找A元素之前的同级X元素，比如：h1 ~ p</li>
			<li>el, el, el：多个选择器组合，查找匹配任一选择器的唯一元素，例如：div.masthead,div.logo</li>
		</ul>
		<b>3.条件使用</b>
		<ul>
			<li>:lt(n)：查找哪些元素的同级索引值（它的位置在DOM树中是相对于它的父节点）小于n，比如：td:lt(3)表示小于三列的元素</li>
			<li>:qt(n)：查找哪些元素的同级索引值大于n，比如：div p:get(2)表示哪些div中有包含2个以上的p元素</li>
			<li>:eq(n)：查打哪些元素的同级索引值与n相等，比如：form input:eq(1)表示包含一个input标签的Form元素</li>
			<li>:has(selector)：查找匹配选择器包含元素的元素，比如：div:has(p)表示哪些div包含了p元素</li>
			<li>:not(selector)：查找与选择器不匹配的元素，比如：div:not(.logo)表示不包含class=logo元素的所有div列表</li>
			<li>:contains(text)：查找包含组定文本的元素，搜索不区分大小写，比如：p:contains(jsoup)</li>
			<li>:containsOwn(text)：查找直接包含给定文本的元素</li>
			<li>:matches(regex)：查找哪些元素的文本匹配反映定的正则表达式，比如：div:matches((?i)login)</li>
			<li>:matchesOwn(regex)：查找自身包含文本匹配指定正则表达式的元素</li>
			注意：上述伪选择器索引是从0开始的，也就是说第一个元素索引值为0，第二个元素索引值为1等
		</ul>
	</body>
</html>