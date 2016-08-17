<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import = "com.founder.weixin.pojo.Location;"%>
<html>
<head>
	<title>OAuth2.0网页授权</title>
	<meta name="viewport" content="width=device-width,user-scalable=0">
	<style type="text/css">
		*{margin:0; padding:0}
		table{border:1px dashed #B9B9DD;font-size:12pt}
		td{border:1px dashed #B9B9DD;word-break:break-all; word-wrap:break-word;}
	</style>
</head>
<body>
	<%  
		//获取由OAuthServlet中传入的参数 
		Location loc = (Location)request.getAttribute("location");  
		if(null != loc) { 
	%>
	<table width="100%" cellspacing="0" cellpadding="0" background="yellow">
		<tr><td>您的纬度</td><td><%=loc.getLatitude()%></td></tr>
		<tr><td>经度</td><td><%=loc.getLongitude()%></td></tr>
		<tr><td>地理位置精度 </td><td><%=loc.getPrecision()%></td></tr>
	</table>
	<%
		}
		else 
			out.print("用户不同意授权,未获取到用户信息！");
	%>
</body>
</html>