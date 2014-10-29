<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>AA Project Starting Page</title>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
    <center>
		<div class="logo">
			<img src="img/logo.png" width="200" height="180">
		</div><br><br><br><br>
		<div class="nav-top">
			<a href="./addproject.jsp">添加项目</a><br><br>
			<a href="./detail.jsp">搜索项目</a>
		</div>
	</center>
  </body>
</html>
