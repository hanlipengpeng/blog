<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" language="javascript"
			src="<%=path%>/portal/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" language="javascript"
			src="<%=path%>/portal/deamon/diyrank.js"></script>
<script type="text/javascript" language="javascript"
			src="<%=path%>/portal/deamon/jquery-pub.js"></script>
<script type="text/javascript"
            src="<%=path%>/portal/deamon/tangram-1.2.0.js"></script>    <!-- ???????????? -->
<script type="text/javascript" language="javascript" src="<%=path%>/portal/js/pub.js"></script>
<link href="table.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="../static/css/uc.css" />

<title>守护进程管理端 - 营销文档系统</title>
</head>
<body>

<div class="titleBar ui_sr"><img src="image/deamon.gif">守护进程列表&nbsp;<img style="cursor:pointer;"  onclick="javascript:diyrank.load();" src="image/refresh.gif"></div>
<table id="industrytable" width="100%" cellpadding="0" cellspacing="0" border="0"
	class="tbl4 sorttable">
	<thead>
		<tr>
			<th width="60" height="50"><font size="2" ><b>序号</b></font></th>

			<th width="120"><font size="2" ><b>名称</b></font><br/>
			<img _ntessort_="order:asc;sort:PRICE" src="image/p_up.gif" /> <img
				_ntessort_="order:desc;sort:PRICE" src="image/p_down2.gif" /></th>
			<th width="120"><font size="2" ><b>IP</b></font><br/>
			<img _ntessort_="order:asc;sort:PERCENT" src="image/p_up2.gif" /> <img
				_ntessort_="order:desc;sort:PERCENT" src="image/p_down2.gif" /></th>
			<th width="260"><font size="2" ><b>任务状态</b></font><br/>
			<img _ntessort_="order:asc;sort:MFRATIO.MFRATIO14" src="image/p_up2.gif" />
			<img _ntessort_="order:desc;sort:MFRATIO.MFRATIO14" src="image/p_down2.gif" />
			</th>
			<th width="100"><font size="2" ><b>守护进程状态</b></font><br/>
			<img _ntessort_="order:asc;sort:MCAP" src="image/p_up2.gif" /> 
			<img ntessort_="order:desc;sort:MCAP" src="image/p_down2.gif" /></th>
			<th width="80"><font size="2" ><b>操作</b></font></th>

		</tr>
	</thead>
	<tbody>
		<tr>
			<td>&nbsp;</td>
			<td></td>
			<td></td>
			<td></td>
			<td>数据载入中...</td>
			<td></td>

		</tr>
		
	</tbody>
</table>
<br/>
<div class="titleBar ui_sr"><img src="image/detail.gif">日志</div>
<table id="detailtable" width="100%" cellpadding="0" cellspacing="0"
	class="tbl3">
	<tbody>
		<tr class="alter">
			<td>&nbsp;</td>
		</tr>
	</tbody>
</table>
<script type="text/javascript">

//10秒钟一次状态刷新
/**
window.setInterval(
	function () {
            diyrank.load();
     }, 10000);
*/

function operMenuShow(o)
{
	var $o = $(o);
	var next = $o.next();
	next.show();
}

$(document).ready(function(){
	
   	$('.start').live('click',function() {
			var status = $(this).attr("_s");
			if(status == "alive")
			{
				alert("守护进程已启动!");
			}
			else
			{
				var ip = $(this).attr("_ip");
				$.ajax({
	                url:'<%=path%>/xng/xngAction!startDeamon.action', 
					type:'post', 
					dataType:'json', 
					data:
					{
						"host" : ip
			     	}, 
	                error: function(){
	                    alert("提交ajax请求异常,请检查您的计算机网络连接!");
	                },
	                success: function(data){
	                //eval函数把后台json字符串翻译成json
	                    var dataObj=eval(data);
	                    if(dataObj.success)
	                    {
	                    	alert("启动成功,请稍后更新状态!");
	                    }
	                    else
	                    {
	                    	alert(dataObj.msg);
	                    }
	                }
	            });
			}
     });
     
     $('.stop').live('click',function() {
			var status = $(this).attr("_s");
			if(status == "dead")
			{
				alert("守护进程已停止!");
			}
			else
			{
				var ip = $(this).attr("_ip");
				$.ajax({
	                url:'<%=path%>/xng/xngAction!stopDeamon.action', 
					type:'post', 
					dataType:'json', 
					data:
					{
						"host" : ip
			     	}, 
	                error: function(){
	                    alert("提交ajax请求异常,请检查您的计算机网络连接!");
	                },
	                success: function(data){
	                    var dataObj=eval(data);
	                    if(dataObj.success)
	                    {
	                    	alert("停止成功,请稍后更新状态!");
	                    }
	                    else
	                    {
	                    	alert(dataObj.msg);
	                    }
	                }
	            });
			}
     });
     
     $('.restart').live('click',function() {
			var ip = $(this).attr("_ip");
			$.ajax({
	                url:'<%=path%>/xng/xngAction!restartDeamon.action', 
					type:'post', 
					dataType:'json', 
					data:
					{
						"host" : ip
			     	}, 
	                error: function(){
	                    alert("提交ajax请求异常,请检查您的计算机网络连接!");
	                },
	                success: function(data){
	                    var dataObj=eval(data);
	                    if(dataObj.success)
	                    {
	                    	alert("重启动成功,请稍后更新状态!");
	                    }
	                    else
	                    {
	                    	alert(dataObj.msg);
	                    }
	                }
	            });
     });
     
     $('.log').live('click',function() {
			var status = $(this).attr("_s");
			if(status == "dead")
			{
				alert("守护进程已停止,无法获取日志信息!");
			}
			else
			{
				var ip = $(this).attr("_ip");
				$.ajax({
	                url:'<%=path%>/xng/xngAction!deamonLog.action', 
					type:'post', 
					dataType:'json', 
					data:
					{
						"host" : ip
			     	}, 
	                error: function(){
	                    alert("提交ajax请求异常,请检查您的计算机网络连接!");
	                },
	                success: function(data){
	                    var dataObj=eval(data);
	                    if(dataObj.success)
	                    {
	                    	var tbody = jQuery("#detailtable > tbody");
							tbody.empty();
							tbody.append('<tr class="alter"><td align="left"><pre class="brush: java;">'+dataObj.log+'</pre></td></tr>');
	                    }
	                    else
	                    {
	                    	alert(dataObj.msg);
	                    }
	                }
	            });
			}
     });
});

// 填充table数据
function fillranktable(id, rank){
	var trfmt = '<tr class="{css}">'+
		'<td align="center">{index}</td>'+
		'<td align="center">{hostName}</td>'+
		'<td align="center">{ip}</td>'+
		'<td class="doc-op">'+
             '<a class="op" href="javascript:" onmouseout="menuHide(event,this)" onmouseover="operMenuShow(this)">详情({percent}%)'+
             '<div class="nvt-rate-trough"><span style="width: {percent}%;"></span></div></a>'+
             '<div class="doc-del-menu" style="display: none;width: 140px; left: 78px;top: 19px; text-align: left;" onmouseout="hideTip(event,this)">'+
                  '<a class="op" href="<%=path%>/xng/xngAction!gotaskDetail.action?jobListType=1&host={ip}" target="_blank">已执行的任务({executed})</a>'+
                  '<a class="op" href="<%=path%>/xng/xngAction!gotaskDetail.action?jobListType=2&host={ip}" target="_blank">待执行任务({waiting})</a>'+
                  '<a class="op" href="<%=path%>/xng/xngAction!gotaskDetail.action?jobListType=3&host={ip}" target="_blank">正在执行的任务({runningQueueSize})</a>'+
              '</div>'+
        '</td>'+
		'<td align="center">{statusImage}</td>'+
		'<td align="left"><img style="cursor:pointer;" _s="{status}" class="start" _ip="{ip}"  src="image/{startSrc}.png"><img style="cursor:pointer;" _s="{status}" class="stop" _ip="{ip}" src="image/{stopSrc}.png"><img style="cursor:pointer;" _s="{status}" class="restart" _ip="{ip}" src="image/restart_ok.png"><img style="cursor:pointer;" _s="{status}" class="log" _ip="{ip}" src="image/log.png"></td>'+
		'</tr>';
	var tbody = jQuery("#"+id+" > tbody");
	tbody.empty();
	for(var i=0; i<rank.list.length; i++){
		var deamon = rank.list[i];
		deamon["index"] = i+1;
		deamon["css"] = i%2==0 ? "" : "alter";
		deamon["statusImage"] = deamon["status"]=="alive"?'<img src="image/green-on-16.png">':'<img src="image/red-on-16.png">'
		deamon["startSrc"] = deamon["status"]=="alive"?'start_unok':'start_ok';
		deamon["stopSrc"] = deamon["status"]=="alive"?'stop_ok':'stop_unok';
		deamon["executed"] = deamon["successQueueSize"]+deamon["failureQueueSize"];
		deamon["waiting"] = deamon["workQueueSize"]+deamon["indexRebuildQueueSize"]+deamon["indexDeleteQueueSize"]-deamon["runningQueueSize"];
		deamon["running"] = deamon["runningQueueSize"];
		deamon["percent"] = deamon["total"]==0?0:Math.round((deamon["executed"]) / deamon["total"] * 1000) / 10;
		
		tbody.append(jQuery.formatString(trfmt, deamon));
	}
}

//数据模型
var diyrank = new yxlib_diyrank({
	host: getRootPath() + "/xng/xngAction!listDeamons.action",
	page: 0,
	count: 10,
	sort: "SYMBOL",
	order: "asc",
	query: "STYPE:EQA;CINDUSTRY.STYLE:009;CINDUSTRY.STYLECODE:ZC7"
});
// 加载数据后触发，这里处理页面更新
diyrank.onload = function(sender){
	fillranktable("industrytable", sender);
}

// 初始化排序
var sortoptions = {
	imgasc:"image/up02.gif",
	imgdesc:"image/down02.gif",
	imgasc2:"image/up01.gif",
	imgdesc2:"image/down01.gif"
};

// 排行榜表格对象
var tbsort = new yxlib_tablesort("industrytable", sortoptions);
tbsort.init();
tbsort.onclick = function(img, meta){
	if(!meta["sort"]){
		alert("缺少排序依据");
		return;
	}
	diyrank.sort = meta["sort"];
	diyrank.order = meta["order"];
	diyrank.page = 0;
	diyrank.load();
}

// 载入第一页排行榜数据
diyrank.load();

</script>
<script type="text/javascript">if(baidu.G("mainData")){var tip=baidu.GT("span",baidu.G("mainData"));var tipList=G("lowQualityTip");var wrap=G("wrap");wrappos=baidu.dom.getPosition(wrap);baidu.each(tip,function(A){baidu.on(A,"click",function(B){var C=baidu.dom.getPosition(A);tipList.style.top=(C.top-92)+"px";tipList.style.left=C.left-wrappos.left+"px";baidu.show(tipList);baidu.stopPropagation(B)})});baidu.on(document,"click",function(){baidu.hide(tipList)});baidu.on(tipList,"click",function(A){baidu.stopPropagation(A)})}function del(B){var A=document.d;A.doc_id.value=B;A.lu.value=location.href;login.verlogin(function(){A.submit()})}function confirmDel(F,A,B,D){var C='<div class="doc-pop-del"><div class="f-14"><b>您确定要删除文档</b><a href="/view/'+F+'.html" target="_blank">'+D+'</a><b>吗？</b></div><p>如果删除，系统将扣除您因这份文档获得的</p><p class="f-red">财富值：'+A+"&nbsp;&nbsp;&nbsp;&nbsp;积分值："+B+'</p><div class="line"></div><p class="f-14" >同时，请您配合填写删除原因</p><p class="f-red" id="reasonInfo"></p><form id="docDelForm" name="docDelForm" method="post" action="/submit" target="popIframe" ><div id="reason"><p><input type="radio" name="sub_status" value="20"> 文档需要更新</p><p><input type="radio" name="sub_status" value="21"> 文档泄漏了隐私内容</p><p><input type="radio" name="sub_status" value="22"> 文档上传失误</p><p><input type="radio" name="sub_status" value="23"> 文档侵犯了他人版权</p><p><input type="radio" name="sub_status" value="24"> 其他</p></div><input type="hidden" name="ct" value="20021" /><input type="hidden" name="doc_id" value="'+F+'"><input type="hidden" name="lu" value="'+location.href+'"><div class="docSubmit"><input type="submit" class="pop_btn_short ml12" value="确定" ><input type="button" class="pop_btn_short ml12" value="取消" onclick="pop.hide()"></div></form></div>';pop.show("提示信息",{info:C,width:400,height:410});baidu.on("docDelForm","submit",delDoc);var E=document.docDelForm.sub_status;for(i=0;i<E.length;i++){baidu.on(E[i],"click",function(){baidu.g("reasonInfo").innerHTML=""})}}function delDoc(A){baidu.preventDefault(A);baidu.stopPropagation(A);if(!ischecked()){baidu.g("reasonInfo").innerHTML="请选择删除原因";return }else{pop.show("提示",{url:"/static/html/empty.html",width:340,height:200});document.docDelForm.submit()}}function ischecked(){var A=baidu.G("reason").getElementsByTagName("input");for(i=0;i<A.length;i++){if(A[i].checked==true){return true}}return false}function menuShow(C,B){var A=baidu.dom.next(B);baidu.dom.show(A)}function menuHide(A,B){baidu.event.stopPropagation(A);var A=A?A:window.event;var F=A.toElement?A.toElement:A.relatedTarget;var E=B.parentNode;E=baidu.dom.last(E);while(F&&F!=E){F=F.parentNode}if((E!=F)){var C=baidu.dom.next(B);var D=baidu.dom.last(C);baidu.dom.hide(C)}}function showErrorTip(A){var B=baidu.dom.next(A);baidu.dom.show(B)}function hideTip(C,A){baidu.event.stopPropagation(C);var B=baidu.dom.last(A);if(!isMouseLeaveOrEnter(C,A)&&!isMouseLeaveOrEnter(C,B)){var B=baidu.dom.last(A);baidu.dom.hide(A)}}function isMouseLeaveOrEnter(C,B){if(C.type!="mouseout"&&C.type!="mouseover"){return false}var A=C.relatedTarget?C.relatedTarget:C.type=="mouseout"?C.toElement:C.fromElement;while(A&&A!=B){A=A.parentNode}return(A==B)};</script>
</body>
</html>