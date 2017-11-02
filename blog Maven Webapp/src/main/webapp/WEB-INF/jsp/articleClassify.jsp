<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
	<meta name="keywords" content="scclui框架">
	<meta name="description" content="scclui为轻量级的网站后台管理系统模版。">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta http-equiv="Cache-Control" content="no-siteapp" />
    <title>文章分类管理</title>
    
  </head>
  
  <body>
  
	添加分类
	<form action="/blog/admin/addArticleClassify" method="post">
		<input type="text" name="name" value="输入要添加的分类" >
		<!-- <input type="text" name="description" value="输入分类的描述" > -->
		<br>
		说明:
		<br>
		<textarea rows="5" cols="20" name="description"></textarea>
		<input type="submit" value="添加">
	</form>
	<table>
		
		<c:forEach  var = "iterm" items="${list}">
			<tr>
			<td>name：${iterm.name }</td>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>description:${iterm.description }</td>
			<td>&nbsp;&nbsp;&nbsp;</td>
			<td><a href="../admin/deletArticleClassify?id=${ iterm.id}">删除</a></td>
			</tr>
		</c:forEach>
	</table>
	
  </body>
</html>
