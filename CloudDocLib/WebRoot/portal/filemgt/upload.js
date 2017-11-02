
var _swfHeight;
/**
 * 动态设置swf上传控件的高度
 * @param {} id 页面id
 * @param {} height 高度
 */
function setFlashHeight(id,height)
{
	_swfHeight = height;
	//如果你同时使用 OBJECT 和 EMBED 标签， 每个属性和参数都要使用同样的值，以确保影像在跨浏览器播放时一致
	document.getElementById("upload").style.height=height+"px";
	if(document.getElementById("uploadEmbed"))
		document.getElementById("uploadEmbed").style.height=height+"px";
}
/**
 * 更新文档上传后在后台生成的id
 * @param {} index 文档的序号
 * @param {} id 文档的后台id
 */
function updateFileId(index,id,status)
{
	//alert(document.getElementById('fileList['+index+'].hdfsFileId')+", "+id);
	if(status=='Y')
	{
		document.getElementById('fileList['+index+'].hdfsFileId').value=id;
		document.getElementById('submitBtn').disabled = false;
		document.getElementById('submitBtn').className = "input-button";
	    $("#title-"+index).rules("add", { 
		       	required: true,
		       	maxlength : 100,
	           	messages:{
		        	required: "<br/>请填写文档标题",
		        	maxlength: "<br/>标题不能超过100个字符"
		       	}
	   	});
		   
		$("#fileList_"+index+"_fileCategoryId").rules("add", {
				required: true,
	           	messages:{
		           	required: "<br/>请选择文档类别"
		       	}
	   	});  
	   	
	   	$("#fileList_" + index + "_secretLvlId").rules("add", {
	   		required: true,
	           	messages:{
		           	required: "<br/>请选择文档密级"
		       	}
	   	});
	}
	if(status=='N')
	{
		document.getElementById('doc-holder-'+index).disabled=true;
		document.getElementById('fileList_'+index+'_fileDesc').disabled=true;
		document.getElementById('title-'+index).disabled=true;
		document.getElementById('keyword-'+index).disabled=true;
		document.getElementById('cate-input-'+index).disabled=true;
		document.getElementById('fileList['+index+'].fileExtName').disabled=true;
		document.getElementById('fileList['+index+'].hdfsFileId').disabled=true;
		document.getElementById('chooseReceiveImg'+index).style.display = 'none';//.disabled = true;
	}
}
/**
 * 快速定位到form表单
 * @param {} index form表单的位置
 */
function locateFormById(index)
{
	//alert("locate:"+index);
	window.location.hash = "#anchor_"+index;
	document.getElementById('title-'+index).focus();
}
/**
 * 关闭当前页面
 */
function page_close()
{
	if (confirm("您确定要关闭本页吗？\n离开当前页面会丢失已经填写的信息"))
	{
		window.location =getRootPath()+"/portal/filemgt/upload.jsp";
	}
}
/**
 * 移除form表单
 * @param {} id
 */
function removeFormById(index)
{
	//alert("delete:"+index);
	var id = "doc-holder-"+index;
	$("#"+id).remove();
}
//关闭弹出窗口
function close_Click(){
    //$.unblockUI();
    clearDivWindowAndLockScreen();
    showSwf();
}
/**
 * 验证form提交表单
 */
function validateForm()
{
	return true;
	$(".input-text").each(function()
	    	{
	    		var v = $(this).val();
	    	    var next = $(this).next();
	    	    if(!v)
    	    	{
    	    		next.attr('innerText',"必填项");
    	    	}
    	    	else
    	    	{
    	    		next.attr('innerText',"");
    	    	}
		    }
    );
}


$(document).ready(function(){
	$("#uploadForm").validate({
        //错误的显示位置
        errorPlacement: function(error, element) {
            error.appendTo(element.parent());
        },
        //是否在获得焦点时清除错误
        focusCleanup: true,
        focusInvalid: false
    });
});

/**
 * 增加文档的详细信息表单
 * 
 * @param {} type 本次上传的文件个数为多个还是一个 (1:表示一个；2:表示多个)
 * @param {} index id的序号
 * @param {} title 文档名称
 */
function addFileForm(type,index,title)
{
	var from = document.getElementById('doc-holder-'+index);
	//根据id去判断是否已存在该form，如果存在则更新;不存在则新添加。
	if(from)
	{
		//设新标题、组件可用。
		
		document.getElementById('doc-holder-'+index).disabled=false;
		document.getElementById('fileList_'+index+'_fileDesc').disabled=false;
		document.getElementById('title-'+index).disabled=false;
		document.getElementById('title-'+index).value = title;
		document.getElementById('keyword-'+index).disabled=false;
		document.getElementById('cate-input-'+index).disabled=false;
		document.getElementById('fileList['+index+'].fileExtName').disabled=false;
		document.getElementById('fileList['+index+'].hdfsFileId').disabled=false;
		document.getElementById('chooseReceiveImg'+index).style.display = 'block';
		return ;
	}
	else
	{
		document.getElementById('step1').style.display = 'none';
		document.getElementById('step2').style.display = 'block';
		var fileName = title.substring(0,title.lastIndexOf('.'));
		var extName = title.substring(title.lastIndexOf('.')+1,title.length);
		var o = $('#docUnitHolder');
		var my = document.createElement("div");
		
		my.id='doc-holder-'+index;
		my.className ='doc-unit';
		o.append(my);
		var innerHTML = '<div id="form-'+index+'" class="form-holder clearfix">'+
							'<a style="display: block;" name="anchor_'+index+'"></a>'+
							'<dl class="form-title">'+
							'	<dt>'+
							'		标题'+
							'	</dt>'+
							'	<dd>'+
							'		<input class="input-text" id="title-'+index+'" name="fileList['+index+'].fileTitle"'+
							'			value="'+fileName+'" type="text" >'+
							'		<p class="error-tip"></p>'+
							'	</dd>'+
							'</dl>'+
							'<dl class="form-sumary">'+
							'<span style="display:none">'+
							'	<dt >'+
							'		介绍'+
							'	</dt>'+
							'	<dd style="display:none">'+
							'		<textarea class="input-text" id="fileList_'+index+'_fileDesc" name="fileList['+index+'].fileDesc"'+
							'			></textarea>'+//class="input-text"
							'		<p class="error-tip"></p>'+
							'	</dd>'+
							'</span>'+
							
				
							'<dl class="form-tag">'+
							'	<dt>'+
							'		关键词'+
							'	</dt>'+
							'	<dd>'+
							'		<input class="input-text" name="fileList['+index+'].fileKeyword"'+//class="input-text input-init" 
							'			value="" id="keyword-'+index+'" autocomplete="off"'+
							'			type="text">'+
							'		<p class="error-tip"></p>'+
							'	</dd>'+
							'</dl>'+
							
							
							'<dl class="form-tag">'+
							'	<dt>'+
							'		分类'+
							'	</dt>'+
							'	<dd>'+
							'		<input class="input-text" name="fileList['+index+'].fileCategoryId"'+//class="input-text input-init" 
							'			value="" id="cate-set-'+index+'" autocomplete="off"'+
							'			type="text">'+
							'		<p class="error-tip"></p>'+
							'	</dd>'+
							'</dl>'+
							
							'<input id="fileList['+index+'].sid" name="fileList['+index+'].sid" value="0" type="hidden" />'+
							'<input id="fileList['+index+'].hdfsFileId" name="fileList['+index+'].hdfsFileId" value="0" type="hidden" />'+
							'<input id="fileList['+index+'].fileExtName" name="fileList['+index+'].fileExtName" value="'+extName+'" type="hidden" />'+
						'</div>';
		my.innerHTML = innerHTML;
	}
}
/**
 * 应用到下列所有文档处理
 * 
 */
function applyToAllDoc()
{
	var category = document.getElementById('fileList_all_fileCategoryId').value;
	var docDesc = document.getElementById('fileList_all_fileDesc').value;
	var keyword = document.getElementById('keyword-all').value;
	
	var formLength = 0;
     for(var i=0;i<document.getElementById("docUnitHolder").childNodes.length;i++){  
         if(document.getElementById("docUnitHolder").childNodes[i].nodeType == 1){  
             formLength++;  
         }  
     }  
        
	//遍历所有的form表单，查找出对应的index
	for(var i=0;i<formLength;i++)
	{
    	setDocInfo(i,docDesc,keyword,category);
	}
}
/**
 * 设置文档的基本信息
 * @param {} index 表单索引
 * @param {} d 简介
 * @param {} k 关键字
 * @param {} sg 分享群组
 * @param {} su 分享用户
 * @param {} sn 分享显示名称
 * @param {} p 价格
 * @param {} c 类别
 * @param {} cd 类别描述
 * @param {} freeFlag 是否免费标记
 */
function setDocInfo(index,d,k,c)
{
	//如果当前的表单为可用的则赋值
	if(document.getElementById('doc-holder-'+index) && !document.getElementById('doc-holder-'+index).disabled)
	{
		//简介
		document.getElementById('fileList_'+index+'_fileDesc').value=d;
		//关键字
		document.getElementById('keyword-'+index).value=k;
		//类别
		if(cd!=null && cd!='')
		{
			document.getElementById('fileList_'+index+'_fileCategoryId').value = c;
			//设置类别显示内容
			document.getElementById('cate-input-'+index).innerHTML = c;
			document.getElementById('cate-input-'+index).style.display = 'block';
		}
	}
}
/**
 * 关闭一起填写
 * 
 */
function closeFillAll()
{
	document.getElementById('all-fill').style.display = 'none';
}
/**
 * 打开一起填写
 */
function openFillAll()
{
	document.getElementById('all-fill').style.display = 'block';
}
//隐藏swf播放器
function hideSwf()
{
	document.getElementById('uploaderContainer').style.height = _swfHeight+"px";
	document.getElementById('upload').style.height = 0+"px";
	if(document.getElementById('uploadEmbed')){document.getElementById('uploadEmbed').style.height = 0+"px";}
}
//显示swf播放器
function showSwf()
{
	document.getElementById('upload').style.height = _swfHeight+"px";
	if(document.getElementById('uploadEmbed')){document.getElementById('uploadEmbed').style.height = _swfHeight+"px";}
}