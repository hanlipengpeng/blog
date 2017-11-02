<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>韩利鹏的博客</title>
<meta name="keywords" content="韩利鹏的博客，韩利鹏" />
<meta name="description" content="韩利鹏的博客，韩利鹏" />
<link href="/blog/css/base.css" rel="stylesheet">
<link href="/blog/css/index.css" rel="stylesheet">
<link href="/blog/css/media.css" rel="stylesheet">
<meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0">
<!--[if lt IE 9]>
<script src="/blog/js/modernizr.js"></script>
<![endif]-->
</head>
<body>
<div class="ibody">

  <!--名言-->
  <header>
    <h1>韩利鹏的博客</h1>
    <h2>不积跬步无以至千里，每天进步一点点，我离大牛更接近....</h2>
    <div class="logo"><a href="/"></a></div>
    <nav id="topnav"><a href="index.html">首页</a><a href="about">关于我</a><a href="leave">留言板</a><a href="photo">个人相册</a></nav>
  </header>
  
  <!--文章-->
  <article>
	<!--名言-->
    <div class="banner">
      <ul class="texts">
        <p>The best life is use of willing attitude, a happy-go-lucky life. </p>
        <p>最好的生活是用心甘情愿的态度，过随遇而安的生活。</p>
      </ul>
    </div>
	
	
	
    <div class="bloglist">
      <h2>
        <p><span>推荐</span>文章</p>
      </h2>
	  
	  <!--每篇的文章-->
      <div class="blogs">
        <h3><a href="/">大数据要学习哪些技术</a></h3>
        <figure><img src="/blog/images/01.jpg" ></figure>
        <ul>
          <p>大数据技术体系太庞杂了，基础技术覆盖数据采集、数据预处理、分布式存储、NOSQL数据库、多模式计算（批处理、在线处理、实时流处理、内存处理）、多模态计算（图像、文本、视频、音频）、数据仓库、数据挖掘、机器学习、人工智能、深度学习、并行计算、可视化等各种技术范畴和不同的层面。另外大数据应用领域广泛，各领域采用技术的差异性还是比较大的。</p>
          <a href="/" target="_blank" class="readmore">阅读全文&gt;&gt;</a>
        </ul>
        <p class="autor"><span>作者：韩利鹏</span><span>分类：【<a href="/">日记</a>】</span><span>浏览（<a href="/">459</a>）</span><span>评论（<a href="/">30</a>）</span></p>
        <div class="dateview">2017-04-08</div>
      </div>
    </div>
  </article>
  
  
  
  
  
  <aside>
    <div class="avatar"><a href="about.html"><span>关于小鹏鹏</span></a></div>
    <div class="topspaceinfo">
      <h1>执子之手，与子偕老</h1>
      <p>于千万人之中，我遇见了我所遇见的人....</p>
    </div>
    <div class="about_c">
      <p>网名：小鹏鹏</p>
      <p>职业：大数据后端开发，大数据分析 </p>
      <p>籍贯：河南省—郑州</p>
      <p>电话：13051609402</p>
      <p>邮箱：915619712@qq.com</p>
    </div>
    <div class="bdsharebuttonbox"><a href="#" class="bds_qzone" data-cmd="qzone" title="分享到QQ空间"></a><a href="#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博"></a><a href="#" class="bds_tqq" data-cmd="tqq" title="分享到腾讯微博"></a><a href="#" class="bds_renren" data-cmd="renren" title="分享到人人网"></a><a href="#" class="bds_weixin" data-cmd="weixin" title="分享到微信"></a><a href="#" class="bds_more" data-cmd="more"></a></div>
    <div class="tj_news">
      <h2>
        <p class="tj_t1">最新文章</p>
      </h2>
      <ul>
        <li><a href="/">分布式开发</a></li>
        <li><a href="/">高并发解决方案</a></li>
       
      </ul>
      <h2>
        <p class="tj_t2">推荐文章</p>
      </h2>
      <ul>
        <li><a href="/">分布式开发</a></li>
        <li><a href="/">高并发解决方案</a></li>
        
      </ul>
    </div>
	
	<!--友情链接-->
    <div class="links">
      <h2>
        <p>友情链接</p>
      </h2>
      <ul>
        <li><a href="/">小鹏鹏的csdn博客</a></li>
        <li><a href="/">大数据训练营</a></li>
      </ul>
    </div>
    <div class="copyright">
      <ul>
        <p> Design by <a href="/">小鹏鹏</a></p>
        <p></p>
        </p>
      </ul>
    </div>
  </aside>
  <script src="/js/silder.js"></script>
  <div class="clear"></div>
  <!-- 清除浮动 --> 
</div>
</body>
</html>
