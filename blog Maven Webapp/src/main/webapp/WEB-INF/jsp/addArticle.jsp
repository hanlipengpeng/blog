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
    <title>添加文章</title>
    
  </head>
  
  <body>
	<form action="/blog/admin/addArticle" enctype="multipart/form-data" method="post">
		添加文章标题：<input type="text" name="title"><br>
		选择文章分类：
		<select name="category">
		<c:forEach var = "item" items="${list}">
			<option value="${item.id }">${item.name}</option>
			
		</c:forEach>
		</select>
		<br>
		文章的作者:&nbsp;<input type="text" name="author"><br>
		添加相关图片：<input type="file" name="file"><br><br>
		文章内容:<br>
		<textarea rows="23" cols="80" name="content"></textarea><br>
		<input type="submit" value="提交">
		
	</form>
	
	
	
	
	
	
	
	
	
	
  </body>
</html>
