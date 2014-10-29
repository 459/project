<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Adding Project page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <center>
      <div class="logo">
			<img src="img/logo.png" width="200" height="180">
		</div><br><br><br><br>
	  <form method = "get" action = "add.jsp">
        项目名称：<input type="text" name="id"><br><br>
       总金额：<input type="text" name="prices"><br><br>
        <br><input type="submit" value="添加">
      </form><br><br><br><br><br>
      <jsp:useBean id = "add" class = "ADD.add"/>
  		<jsp:setProperty name = "add"  property="id"/>
  		<jsp:setProperty name = "add"  property="prices"/>
  		<%
  		String sql = "";
  		sql = add.Add();
  	%>
      <a href="./index.jsp">返回首页</a>
    </center>
  </body>
</html>
