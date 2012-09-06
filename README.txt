企业网站信息内容管理平台（EWCMS）
目标

    简单方便的完成，用户站点管理和生成。
    通过ftp、sftp等手段，在线发布和管理用户托管网站。
    通过Webservic技术，实现于其他业务系统的集成。
    引入SEO功能，辅助用户对站点在搜索引擎中的优化，主要针对(google和baidu)。 

技术路线

　 JDK：6.0以上。
    开发架构：J2EE。
    操作系统：兼容全部主流服务操作系统，包括Windows Server、 Linux。
    数据库：Postgresql 8.0以上。
    应用服务器：Tomcat6.0以上。 
    
演示地址
  http://59.53.171.148/
  username:demo
  password:123456
  
  
使用TinyMCE作为在线编辑器，在测试过程中，遇到一个小问题：
TinyMCE默认的段落缩进功能采用的方法是在标签中插入padding-left:30px样式。
而通常我们需要的功能是段落首行缩进两个中文字符的空白，经过尝试，只需对timy_mce.js中的代码作很小的改动就可以实现这样的功能，
具体是把上述js文件中所有的paddingLeft改为textIndent（共有6处），
然后引用编辑器的时候在tinyMCE.init()中加入indentation : '2em',配置行。
上述改动涉及到timy_mce.js中以下三个函数的定义：Indent : function()、Outdent : function()、queryStateOutdent : function()。
当然，更简便的办法是不用TinyMCE提供的缩进代码，
直接在页面中写 p { text-indent:2em; }这样的样式来控制，但这样做的缺点是使内容中所有的段落都缩进两个字符，
而我想要的是可以手动控制缩进，因为并非所有的段落都需要缩进。