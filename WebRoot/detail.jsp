<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" import="java.sql.*"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Details of Projects</title>
    
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
	<form method="get" action="">
	 项目名称：<input type="text" name="id" />
	 <br><br><input type = "submit" value = "提交" />
	</form>
	<br><br><br><br><br><jsp:useBean id = "conn" class = "ADD.Connect"/>
  	<jsp:setProperty name = "conn" property="*"/>
  	<%
  	try{
  		Class.forName("com.mysql.jdbc.Driver"); 
  		Connection Con =  (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/aaproject","root","123456");
  		if (Con == null) {
			      System.err.println("链接数据库失败.\n");
			}
	      	
	      	String sql = "";
		    String id = request.getParameter("id");
		    sql = "select * from projects where id = \""+ id +"\"";
			Statement stm = (Statement) Con.createStatement();
			ResultSet res = null;
			res = stm.executeQuery(sql);
			if(res.next()){
			    out.print("项目名称："+res.getString(1)+"<br>");
				out.print("总金额："+res.getString(2)+"<br>");
			}
			
			sql = "select * from details where projectid = "+ id +"";
			res = stm.executeQuery(sql);
			while(res.next()){
			    out.print("参与者："+res.getString(1)+"<br>");
			    out.print("应付金额："+res.getString(2)+"<br>");
				out.print("百分比："+res.getString(3)+"<br><br>");
			}
			try {
				if (res != null) {
					res.close();
				}
				if (stm != null) {
					stm.close();
				}
				if (conn != null) {
					Con.close();
				}
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
			
	    }
	    catch (Exception ee) {
	      ee.printStackTrace();
	    }
  	 %>
  	 <br><br><form method = "get" action = "delete.jsp">
  	             删除项目（项目名称）:<input type="text" name="id" />
	    <br><br><input type = "submit" value = "提交" />
	 </form>
  	 <br><br><br><br><br><br><br><br><a href="./index.jsp">返回首页</a>
	</center>
  </body>
</html>
