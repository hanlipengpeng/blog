var speed = 10; // 速度
var max_opacity = 100; // 最大
var min_opacity = 65; // 最小
var do_step = 5; // 变化的幅度

function change_opacity(obj, do_option) {
	if (obj.doing)
		clearInterval(obj.doing);
	obj.doing = setInterval(do_option + "_alpha(" + obj.sourceIndex + ")",
			speed);
}

function down_alpha(obj_index) {
	var obj = document.all[obj_index];

	if (obj.filters.Alpha.Opacity > min_opacity) {
		obj.filters.Alpha.Opacity += -do_step;
	} else {
		clearInterval(obj.doing);
		obj.filters.Alpha.Opacity = min_opacity;
		obj.doing = false;
	}
}
function up_alpha(obj_index) {
	var obj = document.all[obj_index];

	if (obj.filters.Alpha.Opacity < max_opacity)
		obj.filters.Alpha.Opacity += do_step;
	else {
		clearInterval(obj.doing);
		obj.filters.Alpha.Opacity = max_opacity;
		obj.doing = false;
	}
}

function beforeDelete() {
	return confirm('请确认是否要删除？');
}

/**
 * 删除记录
 * 注意事项：删除<a>链接必须有delete的样式
 * 
 * example:
 * 	$(document).ready(function(){
 *  	jQueryBeforeDelete();
 *	});
 */
function jQueryBeforeDelete()
{
	$(".delete").click(function(event){
	   	 var index = $(".delete").index(this);
	     if(!confirm('请确认是否要删除第'+(index+1)+'行信息？'))
	     	event.preventDefault();
   	});
}

function fckeditor(instanceName, width, height, value) {
	var oFCKeditor = new FCKeditor(instanceName);
	oFCKeditor.BasePath = "../fckeditor/";
	oFCKeditor.ToolbarSet = "Default";
	oFCKeditor.Config['ImageBrowserURL'] = '../filemanager/browser/default/browser.html?Type=Image&Connector=connectors/jsp/connector';
	oFCKeditor.Config['LinkBrowserURL'] = '../filemanager/browser/default/browser.html?Connector=connectors/jsp/connector'
	oFCKeditor.Config['FlashBrowserURL'] = '../filemanager/browser/default/browser.html?Type=Flash&Connector=connectors/jsp/connector'
	oFCKeditor.Config['ImageUploadURL'] = '../filemanager/upload/simpleuploader?Type=Image'
	oFCKeditor.Config['LinkUploadURL'] = '../filemanager/upload/simpleuploader?Type=File'
	oFCKeditor.Config['FlashUploadURL'] = '../filemanager/upload/simpleuploader?Type=Flash'
	oFCKeditor.Width = width;
	oFCKeditor.Height = height;
	oFCKeditor.Value = value;
	oFCKeditor.ReplaceTextarea();
}

function changeFckValue(instanceName, value) {
	var oEditor = FCKeditorAPI.GetInstance(instanceName);
	oEditor.SetData(value);
}

function enterNumber() {
	if (!(event.keyCode == 46) && !(event.keyCode == 8)
			&& !(event.keyCode == 37) && !(event.keyCode == 39))
		if (!((event.keyCode >= 48 && event.keyCode <= 57) || (event.keyCode >= 96 && event.keyCode <= 105)))
			event.returnValue = false;
}

function enterFloat() {
	if (!(event.keyCode == 46) && !(event.keyCode == 8)
			&& !(event.keyCode == 37) && !(event.keyCode == 39))
		if (!((event.keyCode >= 48 && event.keyCode <= 57) || (event.keyCode >= 96 && event.keyCode <= 105)))
			event.returnValue = false;
	if (event.keyCode == 190) {
		event.returnValue = true;
	}
}

// 是否是float型数据
function isPlusFloat(str) {
	var patrn = /^([+]?)\d*\.\d+$/;
	return patrn.test(str);
}

// 判断是否是整型数据
function isPlusInteger(str) {
	var patrn = /^([+]?)(\d+)$/;
	return patrn.test(str);
}

function clearForm(formName) {
	var formObj = document.forms[formName];
	var formEl = formObj.elements;

	for (var i = 0; i < formEl.length; i++) {
		var element = formEl[i];

		// 过滤掉分页的下拉框
		if (element.name == 'page.newPageNo') {
			continue;
		}
		if (element.name == 'page.pageSize') {
			continue;
		}

		if (element.type == 'submit') {
			continue;
		}
		if (element.type == 'reset') {
			continue;
		}
		if (element.type == 'button') {
			continue;
		}
		if (element.type == 'hidden') {
			continue;
		}

		if (element.type == 'text') {
			element.value = '';
		}
		if (element.type == 'textarea') {
			element.value = '';
		}
		if (element.type == 'checkbox') {
			element.checked = false;
		}
		if (element.type == 'radio') {
			element.checked = false;
		}
		if (element.type == 'select-multiple') {
			element.selectedIndex = -1;
		}
		if (element.type == 'select-one') {
			element.selectedIndex = -1;
		}
	}
}

function strLen(s) {
	var l = 0;
	var a = s.split("");
	for (var i = 0; i < a.length; i++) {
		if (a[i].charCodeAt(0) < 299) {
			l++;
		} else {
			l += 2;
		}
	}
	return l;
}

/**
 * 弹出消息窗口
 * msg: 需要弹出的消息，默认为：操作成功
 * 
 */
function yxShowMessage(message) {
	var tmp = null;
	if(message == undefined || message == null || message.length < 1)
	{
		tmp = '操作成功';
	}
	else
	{
		tmp = message;
	}
	var msg = 	'<div id="jumpDiv" style="cursor: default" align="left">' 
				+'<div id="titlewindow" style="width:100%">'
				+ '<div id="titlewindow_top"><span >提示信息</span></div>' 
				+ '<div id="titlewindow_main"><span>'
				+ tmp
				+ '</span></div>' 
				+ '<div id="titlewindow_foot">' 
				+ '<label><input type="button" class="button" id="jump2ListBtn" value="确定" /></label>' 
				+ '</div></div></div>';
	
	$('#jump2ListBtn').live('click',function() {
			$.unblockUI();
	    	$('#titlewindow_main').empty();
	    	$('#titlewindow_foot').empty();
     });
    
	$.blockUI({
		message : msg,
		fadeOut : 200, // 淡出时间
		fadeIn : 100, // 淡入时间
		css : {
			backgroundColor : '#fff',
			border : '0px solid #aaa'
		},
		overlayCSS : {
			backgroundColor : '#eee'
		}
	});
}

/**
 * 弹出消息窗口，然后跳转到指定action
 * msg: 需要弹出的消息，默认为：操作成功
 * 
 */
function yxShowMessage2Jump(message,formId,action) {

	var tmp = null;
	if(message == undefined || message == null || message.length < 1)
	{
		tmp = '操作成功';
	}
	else
	{
		tmp = message;
	}
	var msg = 	'<div id="jumpDiv" style="cursor: default" align="left">' 
				+'<div id="titlewindow" style="width:100%">'
				+ '<div id="titlewindow_top"><span >提示信息</span></div>' 
				+ '<div id="titlewindow_main"><span>'
				+ tmp
				+ '</span></div>' 
				+ '<div id="titlewindow_foot">' 
				+ '<label><input type="button" class="button" id="jump2ListBtn" value="确定" />' 
				+ '<input id="backButton" class="cancel" type="submit"  style="display:none;"/></label>' 
				+ '</div></div></div>';
	
	$('#jump2ListBtn').live('click',function() {
			$.unblockUI();
	    	
	    	var jForm = $('#' + formId);
	    	jForm.attr("action", action);	
	    	$('#backButton').click();
	    	
	    	//TODO liwei@也许放在这里的代码做了无用功
	    	$('#titlewindow_main').empty();
	    	$('#titlewindow_foot').empty();
     });
    
	$.blockUI({
		message : msg,
		fadeOut : 200, // 淡出时间
		fadeIn : 100, // 淡入时间
		css : {
			backgroundColor : '#fff',
			border : '0px solid #aaa'
		},
		overlayCSS : {
			backgroundColor : '#eee'
		}
	});
}

function showInfo(msg, callback, type)
{
	if(!msg)
	{
		return ;
	}
    var div ="<div class=\"bd_dialog\" style=\"height:180px; width:300px;\"><div id=\"popwindow_top\">"+
                  " <span class=\"bd_dialog_title\">提示信息</span>"+
                  "  <span class=\"bd_dialog_close\" id='_dialogClose'/>"+
                  "  </div>"+
                  "  <div class=\"bd_tab_cont_s\" style=\"height:120px; width:280px;\">" +
                  "  <div class='doc-pop-del' style=\"padding-top: 20px;\">" +msg+
                  "  <br><br><input id='yes_b' class='pop_btn_short ml12' type='button' value='确定' /> "+
                 "   </div>"+
                 "   </div></div>";

    $.blockUI({
         message:div,
         css: {
                width:     '300px',
                top:    '30%',  //距离上边的比例
                left:   '40%',  //距离左边的比例
                border: '0px'
            },
            fadeOut:  200,      //淡出时间
            fadeIn: 100         //淡入时间
    });
    
    //交换X图片
	$("#x_close").hover(
		function(){
			$(this).attr("src",getRootPath() +"/portal/images/Close-2.gif");
		},
		function(){
			$(this).attr("src",getRootPath() +"/portal/images/Close-1.gif");
		}
	);
	
	//关闭DIV窗口
	$("#_dialogClose").click(
		function(){
			 $.unblockUI();
			 if(callback) callback();
		}
	);
	//关闭DIV窗口
	$("#yes_b").click(
		function(){
			 $.unblockUI();
			 if(callback) callback();
		}
	);
	
}
Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
	// millisecond
	}
	if (/(y+)/.test(format))
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4
				- RegExp.$1.length));
	for (var k in o)
		if (new RegExp("(" + k + ")").test(format))
			format = format.replace(RegExp.$1, RegExp.$1.length == 1
					? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
	return format;
}

function getRootPath()
{
    var strPath = window.location.pathname;
    var postPath = strPath.substring(0,strPath.substr(1).indexOf('/') + 1);
    return postPath;
    //return "http://192.168.1.208:8080/YXLIB";
}

/**
 * div弹窗
 */
var divW;	//DIV宽度
var divH;	//DIV高度
var clientH;	//浏览器高度
var clientW;	//浏览器宽度
var divHtml;	//DIV内塔
var div_X;	//DIV横坐标
var div_Y;	//DIV纵坐标
//锁定背景屏幕
function lockScreen(){
	if($("#divLock").length == 0){	//判断DIV是否存在
		clientH = $(window).height();	//浏览器高度
		clientW = $(window).width();	//浏览器宽度
		//var docH = $("body").height();	//网页高度
		//var docW = $("body").width();	//网页宽度
		//var bgW = clientW > docW ? clientW : docW;	//取有效宽
		//var bgH = clientH > docH ? clientH : docH;	//取有效高
		$("body").append("<div id='divLock'></div>")	//增加DIV
		$("#divLock").height(clientH);
		$("#divLock").width(clientW);
		$("#divLock").css("display","block");
		$("#divLock").css("background-color","#eee");
		$("#divLock").css("position","fixed");
		$("#divLock").css("z-index","100");
		$("#divLock").css("top","0px");
		$("#divLock").css("left","0px");
		$("#divLock").css("opacity","0.5");
	}
	else{
		clientH = $(window).height();	//浏览器高度
		clientW = $(window).width();	//浏览器宽度
		$("#divLock").height(clientH);
		$("#divLock").width(clientW);
	}
}
//返回弹出的DIV的坐标
function divOpen(){
	var minTop = 80;	//弹出的DIV记顶部的最小距离
	if($("#divWindow").length == 0){
		clientH = $(window).height();	//浏览器高度
		clientW = $(window).width();	//浏览器宽度
		div_X = (clientW - divW)/2;	//DIV横坐标
		div_Y = (clientH - divH)/2;	//DIV纵坐标
		div_X += window.document.documentElement.scrollLeft;	//DIV显示的实际横坐标
		div_Y += window.document.documentElement.scrollTop;	//DIV显示的实际纵坐标
		if(div_Y < minTop)
		{
			div_Y = minTop;
		}
		
		var msg = '<div id="divWindow">'+divHtml+'</div>';	
		$("body").append(msg);	//增加DIV
		//divWindow的样式
		$("#divWindow").css("position","absolute");
		$("#divWindow").css("z-index","200");
		$("#divWindow").css("left",(div_X + "px"));	//定位DIV的横坐标
		$("#divWindow").css("top",(div_Y + "px"));	//定位DIV的纵坐标
		//$("#divWindow").css("opacity","1");
		$("#divWindow").width(divW);
		$("#divWindow").height(divH);
		$("#divWindow").css("background-color","#FFFFFF");
		//$("#divWindow").css("border","solid 1px #333333");
		
	}
	else{
		clientH = $(window).height();	//浏览器高度
		clientW = $(window).width();	//浏览器宽度
		div_X = (clientW - divW)/2;	//DIV横坐标
		div_Y = (clientH - divH)/2;	//DIV纵坐标
		div_X += window.document.documentElement.scrollLeft;	//DIV显示的实际横坐标
		div_Y += window.document.documentElement.scrollTop;	//DIV显示的实际纵坐标
		if(div_Y < minTop){
			div_Y = minTop;
		}
		$("#divWindow").css("left",(div_X + "px"));	//定位DIV的横坐标
		$("#divWindow").css("top",(div_Y + "px"));	//定位DIV的纵坐标
	}
}
//清除背景锁定
function clearLockScreen(){
	$("#divLock").remove();
}

//清除DIV窗口
function clearDivWindow(){
	$("#divWindow").remove();
}

/**
 * 生成div窗口
 * @param {} h 窗口高度
 * @param {} w 窗口宽度
 * @param {} m 窗口的显示内容（HTML代码）
 */
function DivWindowOpen(h,w,m){
	divW = w+4;
	divH = h+4;
	divHtml = m;
	lockScreen();	//锁定背景
	divOpen();
}
/**
 * 移除窗口
 */
function clearDivWindowAndLockScreen()
{
	$("#divLock").remove();
	$("#divWindow").remove();
}

function subStr(str, len) {
	if(!str){return "";}
	var str_length = 0;
	var str_len = 0;
	str_cut = new String();
	str_len = str.length;
	for (var i = 0; i < str_len; i++) {
		a = str.charAt(i);
		str_length++;
		if (escape(a).length > 4) {
			// 中文字符的长度经编码之后大于4
			str_length++;
		}
		str_cut = str_cut.concat(a);
		if (str_length >= len) {
			str_cut = str_cut.concat("...");
			return str_cut;
		}
	}
	// 如果给定字符串小于指定长度，则返回源字符串；
	if (str_length < len) {
		return str;
	}
}
/**
 * 
 * @param {} width
 * @param {} height
 * @param {} title
 * @param {} url
 */
function openWindow(width, height, title, url){
	if(!width) widht = 480;
 	if(!height) height = 350;
	var html= '<div class="bd_dialog" style="height:' + height +'px; width:'+ width+'px;">' +
					     '<div id="popwindow_top">'+
	                     '  <span class="bd_dialog_title">' + title + '</span>'+
	                     '	<span class="bd_dialog_close"/>'+
	                     '</div>'+
	                     '<div class="bd_tab_cont_s">' +
                     	 '	<iframe frameborder="0" scrolling="no" height="'+(height-50)+'px" width="'+(width-20)+'" src='+url+' />' +
	             		 '</div>' +
         			 '</div>'; 
	 
     DivWindowOpen(height, width, html);
     $('.bd_dialog_close').click(function(){
     	 //关闭弹窗和蒙板
    	clearDivWindowAndLockScreen();
     });
}
