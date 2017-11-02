<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!doctype html>
<html>
	<head>
		<base href="<%=basePath%>">
		<meta charset="utf-8">
		<title>留言板</title>
		<meta name="keywords" content="韩利鹏的博客，韩利鹏" />
		<meta name="description" content="韩利鹏的博客，韩利鹏" />
		<link href="css/base.css" rel="stylesheet">
		<link href="css/style.css" rel="stylesheet">
		<link href="css/media.css" rel="stylesheet">
		<meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0">
		<!--[if lt IE 9]>
		<script src="js/modernizr.js"></script>
		<![endif]-->
	</head>
<body>
<div class="ibody">
    <!--名言-->
  <header>
    <h1>韩利鹏的博客</h1>
    <h2>不积跬步无以至千里，每天进步一点点，我离大牛更接近....</h2>
    <div class="logo"><a href="/"></a></div>
    <nav id="topnav"><a href="article/index">首页</a><a href="article/about">关于我</a><a href="article/leave">留言板</a><a href="article/photo">个人相册</a></nav>
  </header>
  
  <article>
    <h2 class="about_h">您现在的位置是：<a href="/">首页</a>><a href="1/">留言板</a></h2>
    <div class="bloglist">
      <div class="newblog">
        <ul>
          <h3><a href="/">个人博客从简不繁</a></h3>
          <div class="autor"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span> <span>&nbsp;&nbsp;&nbsp;&nbsp;</span><span>&nbsp;&nbsp;&nbsp;</span><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></div>
        <p>路过路过</p>
        </ul>
        <figure><img src="images/001.jpg" ></figure>
        <div class="dateview">2014-04-08</div>
      </div>
      
      
      
      
     
    </div>
    
  </article>
  <aside>
    <div class="rnav">
      <li class="rnav1 "><a href="article/index">主页</a></li>
      <li class="rnav2 "><a href="article/about">关于我</a></li>
      <li class="rnav3 "><a href="article/leave">留言板</a></li>
      <li class="rnav4 "><a href="article/photo">照片墙</a></li>
    </div>
    
    
    

    
    
    
    
  </aside>
  <script src="js/silder.js"></script>
  <div class="clear"></div>
  <!-- 清除浮动 --> 
</div>
</body>
</html>
