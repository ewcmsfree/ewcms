<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
  <head>
    <script type="text/javascript" src='<s:url value="/ewcmssource/js/jquery.min.js"/>'></script>

    <script type="text/javascript">
      var i = 0;
      var progress = function(value) {
        i = i + 1;
        $('#messages').val(value + i);
      }
    </script>
</head>
<body>
    <textarea rows="20" cols="80" id="messages"></textarea>
     <iframe src="/pubsub/progress/<s:property value="currentSite.id"/>" style="width: 0;height: 0;overflow: hidden;" frameborder="0"></iframe>
</body>
</html>