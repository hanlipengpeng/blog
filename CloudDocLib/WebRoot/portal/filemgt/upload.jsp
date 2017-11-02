<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>营销文档系统-文档分享平台</title>
		<meta name="description" content="在线互动式文档分享平台" />
		<link type="text/css" rel="stylesheet" href="../static/css/upload.css" />
		<link type="text/css" rel="stylesheet" href="../static/css/public.css" />
		<link type="text/css" rel="stylesheet" href="<%=path%>/portal/static/css/blockui.css" />
		<script type="text/javascript" src="../static/js/doc.js"></script>
		<script type="text/javascript" language="javascript" src="<%=path%>/portal/js/jquery-1.4.4.min.js"></script>
		<script type="text/javascript" language="javascript" src="<%=path%>/portal/js/jquery.validate.js"></script>
		<script type="text/javascript" language="javascript" src="<%=path%>/portal/js/jquery.blockUI.js"></script>
		<script type="text/javascript" language="javascript" src="<%=path%>/portal/js/pub.js"></script>
		<script type="text/javascript" src="upload.js"></script>
		<script type="text/javascript">
			/**
			* 获取上传地址
			*/
			function getURi()
			{
				var uri = '<%=request.getRequestURL()%>';
				var root = getRootPath();
				var index = uri.indexOf(root);
				return uri.substr(0,index)+root+"/FileUploaded";
			}
			//设置全局变量类别id
			var categoryIndex;
			/**
			* 获取根路径
			*/
			function getRootPath()
			{
				return "<%=path%>";
			}
		    //关闭弹出窗口
		    function closeClick(){
		        //$.unblockUI();
		        close_Click();
		    }
		    
		</script>
	</head>
	<body>
	<div id="index_top_body">
		<div id="contant">
			<div id="wrapper">
				<div class="nav">
					营销文档系统&nbsp;&gt;&nbsp; 上传文档
				</div>
				<table width="100%" border="0" cellspacing="0" cellpadding="0" id="main" class="main">
					<tr>
						<td class="mod_lefttop_w"></td>
						<td class="mod_centertop_w"></td>
						<td class="mod_righttop_w"></td>
					</tr>
					<tr>
						<td height="60" class="mod_center">
							<div id="uploaderContainer">
								<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
									id="upload" style="width: 100%; height: 100%;"
									codebase="http://fpdownload.macromedia.com/get/flashplayer/current/swflash.cab">
									<param name="movie" value="../../flex_app/upload.swf" />
									<param name="quality" value="high" />
									<param name="bgcolor" value="#869ca7" />
									<param name="allowScriptAccess" value="sameDomain" />
									<param name="wmode" value="window"/>
									<embed src="../../flex_app/upload.swf" quality="high"
										height="200" id="uploadEmbed" bgcolor="#869ca7" width="695"
										name="upload" align="middle" play="true" loop="false"
										quality="high" allowScriptAccess="sameDomain" wmode="window"
										type="application/x-shockwave-flash"
										pluginspage="http://www.adobe.com/go/getflashplayer">
									</embed>
								</object>
							</div>
							
							<div id="step1">
								<h3 class="header">
									文档上传须知
								</h3>
								<!-- <p>
									请上传大小小于64M的附件
								</p>
								<p>
									最多支持每次同时上传10份文档
								</p> -->
								<p>
									为了保证文档能正常显示，我们支持以下格式的文档上传
								</p>
								<table cellpadding="0" width="600" cellspacing="0" border="0">
									<tbody>
										<tr>
											<td class="r">
												MS Office文档
											</td>
											<td valign="top">
												<span class="icon doc"></span> doc,docx &nbsp;&nbsp;
												<span class="icon ppt"></span> ppt,pptx &nbsp;&nbsp;
												<span class="icon xls"></span> xls,xlsx &nbsp;&nbsp;
											</td>
										</tr>
										<tr>
											<td class="r">
												WPS Office文档
											</td>
											<td valign="top">
												<span class="icon wps"></span> wps &nbsp;&nbsp;
												<span class="icon et"></span> et &nbsp;&nbsp;
												<span class="icon dps"></span> dps&nbsp;&nbsp;
											</td>
										</tr>
										<tr>
											<td class="r">
												PDF
											</td>
											<td>
												<span class="icon pdf"></span> pdf
											</td>
										</tr>
										<tr>
											<td class="r">
												纯文本
											</td>
											<td>
												<span class="icon txt"></span> txt
											</td>
										</tr>
									</tbody>
								</table>
							</div>
								<div id="step2" class="multi-upload" style="display: none;">
									<p class="upload-tips" id="upload-tips">
										为了方便资料被更多用户浏览和下载，请耐心补充以下信息。如果您上传的是一系列文件，可以
										<a href="#allfill" id="showFillall" onclick="openFillAll()">一起填写信息</a>。
									</p>
									<div id="all-fill" style="display: none">
										<h3>
											<a id="close-fillall" href="###" onClick="closeFillAll()">收起</a>一起填写
										</h3>
										<div class="form-holder clearfix">
											<dl class="form-category" style="height:45px;">
												<dt>
													分类
												</dt>
												<dd>
													<!--<span id="cate-input-all" style="display: none;"></span>-->
													<input id="fileList_all_fileCategoryId" name="all-category">
													<span class="error-tip"></span>
												</dd>
											</dl>
											
											<dl class="form-sumary" >
												<dt>
													介绍
												</dt>

												<dd>
													<textarea id="fileList_all_fileDesc"  class="input-text"></textarea>
													<p class="error-tip"></p>
												</dd>
											</dl>
											
											<dl class="form-tag">
												<dt>
													关键词
												</dt>
												<dd>
													<input type="text" class="input-text" 
														value="" id="keyword-all" autocomplete="off" />
													<p class="error-tip"></p>
												</dd>
											</dl>
											
										</div>

										<div class="all-submit">
											<a href="###" id="all-button" onclick="applyToAllDoc()">应用到下列所有文档</a>
										</div>
									</div>
									<h3 id="fill-one">
										<a href="###" id="clip-form"></a>分别填写
									</h3>
									<s:form name="uploadForm" id="uploadForm"
										namespace="/portal/filemgt" action="fileMgtAction" >
										<div id="docUnitHolder">
											<!-- 此处动态增加文档信息内容表单  button-disable-->
										</div>
										<div id="submitHolder">
											<s:submit value="提交" cssClass="input-button button-disable"
														disabled="true" id="submitBtn" method="submitFileInfo" />
										</div>
									</s:form>
								</div>
								<div id="doc-unit-tpl" style="display: none"></div>
						</td>
						<td class="mod_right"></td>
					</tr>
					<tr>
						<td class="mod_leftfoot"></td>
						<td class="mod_centerfoot"></td>
						<td class="mod_rightfoot"></td>
					</tr>
				</table>
				
				<div id="aside" class="aside">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="mod_lefttop_w"></td>
							<td class="mod_centertop_w"></td>
							<td class="mod_righttop_w"></td>
						</tr>
						<tr>
							<td class="mod_left"></td>
							<td class="mod_center" style="padding: 10px 5px;">
								 <div class="guide content">
								 	<p > 如果上传文档过程中有任何问题请联系系统管理员。</p>
									<!--<p>请勿在未经授权的情况下上传任何涉及著作权侵权的文档，除非文档完全由您个人创作
									</p>
									<p>"<font color="blue">版权提示</font>"页面可帮助您确定您的文档是否侵犯了他人的版权
									</p>
									<p>点击上传文档即表示您确认该文档不违反文档分享的<font color="blue">使用条款</font>，并且您拥有该文档的所有版权或者上传文档的授权
									</p>
                                    <p class="last"> 如果上传文档过程中有任何问题请<font color="blue">查看帮助</font>。
									</p>-->
								</div> 
							</td>
							<td class="mod_right"></td>
						</tr>
						<tr>
							<td class="mod_leftfoot">
								&nbsp;
							</td>
							<td class="mod_centerfoot">
								&nbsp;
							</td>
							<td class="mod_rightfoot">
								&nbsp;
							</td>
						</tr>
					</table>
				</div>
				</div>
			</div>
	</body>
</html>