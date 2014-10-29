<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Adding Information Page</title>
    
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
      <form name="idfind"  action ="" method="post">
        <table>
     	<tr>
     		<td>参与者：</td>
     		<td><input type = "text" name = "partners" /></td>
     	</tr>
     	<tr>
     		<td>百分比：</td>
     		<td><input type = "text" name = "percents" /></td>
     	</tr>
     	</table>
     	<br><input type = "submit" value = "添加" />
      </form>
      <jsp:useBean id = "add" class = "ADD.add"/>
  		<jsp:setProperty name = "add"  property="partners"/>
  		<jsp:setProperty name = "add"  property="percents"/>
      <br><br><br><br><br><br><br><br><a href="./addproject.jsp">返回</a>
      <%
  		String sql = "";
  		sql = add.Add();
  	%>
    </center>
  </body>
</html>
