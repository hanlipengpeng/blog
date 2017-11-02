<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>营销文档系统搜索_<s:property value="kw" />
		</title>
		<meta name="description"
			content="在线互动式文档分享平台，在这里，您可以和千万网友分享自己手中的文档，全文阅读其他用户的文档，同时，也可以利用分享文档获取的积分下载文档 " />
		<link type="text/css" rel="stylesheet"
			href="<%=path%>/portal/static/css/as.css" />
		<link type="text/css" rel="stylesheet"
			href="<%=path%>/portal/static/css/public.css" />
		<link type="text/css" rel="stylesheet"
			href="<%=path%>/portal/static/css/blockui.css" />
		<script type="text/javascript" language="javascript"
			src="<%=path%>/portal/js/jquery-1.4.4.min.js"></script>
		<script type="text/javascript" language="javascript"
			src="<%=path%>/portal/js/pub.js"></script>
		<script type="text/javascript" language="javascript"
			src="<%=path%>/portal/js/jquery.blockUI.js"></script>
		<script type="text/javascript">

	var fileType = '<s:property  value="type" />';
	
	$(document).ready(function(){
	
	       switch(fileType)
	       {
	       	case 'all':
	       		$("input[type=radio][value=all]").attr("checked",'checked');
	       		break;
	       	case 'doc':
	       		$("input[type=radio][value=doc]").attr("checked",'checked');
	       		break;
	       case 'pdf':
	       		$("input[type=radio][value=pdf]").attr("checked",'checked');
	       		break;
	       case 'ppt':
	       		$("input[type=radio][value=ppt]").attr("checked",'checked');
	       		break;
	       case 'xls':
	       		$("input[type=radio][value=xls]").attr("checked",'checked');
	       		break;
	       	case 'txt':
	       		$("input[type=radio][value=txt]").attr("checked",'checked');
	       		break;
	       	default:
	       		$("input[type=radio][value=all]").attr("checked",'checked');
	       		break;
	       }
	    
	});
	
    
    //关闭弹出窗口
    function closeClick(){
        $.unblockUI();
    }

	function login(){
		sort(0);
	}
	function logout(){
	    sort(0);
	}
	
	function sort(type){
	    $("#od").val(type);
	    $("#newPageNo").val(1);
	    $("#topSearchBox").submit();
	}
	function toPage(pageNo){
	    $("#newPageNo").val(pageNo);
	    $("#topSearchBox").submit();
	}
	
	//提交搜索请求
    function search(){
        $("#topSearchBox").submit();
    }
    
    function bottonSearch(){
    	$("#bottomSearchBox").submit();
    }
</script>
	</head>
	<body>
		<div id="contant">
	    <div id="top_body">
	    <form action="<%=path%>/portal/search.action" name="ftop" id="topSearchBox" method="post">
	            <input type="hidden" id="od" name="od" value="<s:property  value='od' />"></input>
	            <input type="hidden" id="newPageNo" name="page.newPageNo" value="<s:property  value='page.newPageNo' />"></input>
	      <table width="986" height="114" border="0" align="left" cellpadding="0" cellspacing="0">
	        <tr>
	          <td width="600" height="49"><a href="<%=path %>/portal/homepage/homePageAction!showMainInfo.action"><img src="<%=path%>/portal/images/index_top_logo.jpg" width="600" height="87" alt="营销文档系统" title="营销文档系统" style="border:0px;" /></a></td>
	          <td width="587" valign="top" align="right" style="padding-top:4px; color:#ffffff;"></td>
	        </tr>
	        <tr>
	          <td height="35" colspan="2"><table border="0" cellspacing="4" cellpadding="0" style="width:722px;">
	            <tr>
	              <td width="330"><input type="radio" name="type" value="all" checked id="all"/>全部&nbsp;&nbsp;
	              <input type="radio" name="type" value="doc" id="doc"/>DOC&nbsp;&nbsp;<input type="radio" name="type" value="pdf" id="pdf"/>PDF&nbsp;&nbsp;
	              <input type="radio" name="type" value="ppt" id="ppt"/>PPT&nbsp;&nbsp;<input type="radio" name="type" value="xls" id="xls"/>XLS&nbsp;&nbsp;
	              <input type="radio" name="type" value="txt" id="txt"/>TXT</td>
	              <td width="310"><input type="text" name="kw" id="nkw" class="index_seach_input" style="width:99%;" maxlength="256" tabindex="1" value="<s:property  value='kw' />" /></td>
	              <td width="84" align="center"><img src="<%=path%>/portal/images/seach_button.png" width="75" height="25" title="搜索文档" alt="搜索文档" style="border:0px; cursor:pointer;" onClick="javascript:search();"/></td>
	            </tr>
	          </table></td>
	        </tr>
	      </table>
	      </form>
	    </div>
			<div id="wrapper">
				<div class="nav">
					<a href="<%=path%>/portal">营销文档系统</a> &nbsp;&gt;&nbsp; 搜索结果
					<span>关于"<b><s:property value="kw" />
					</b>"的检索结果共<s:property value="page.totalCount" />条,当前页显示<s:property
							value="page.start+1" />-<s:property value="page.currentPageEnd" />,共耗时<s:property
							value="tc" />
					</span>
				</div>
				<table width="100%" border="0" cellspacing="0" cellpadding="0" id="main" class="main">
					<tr>
						<td class="mod_lefttop_w"></td>
						<td class="mod_centertop_w"></td>
						<td class="mod_righttop_w"></td>
					</tr>
					<tr>
						<td class="mod_left"></td>
						<td height="60" class="mod_center"
							style="padding: 0px 5px 20px 5px;">
							<div class="filter">
								排序：
								<s:if test="od==0">
									<b>相关性</b>  |
				</s:if>
								<s:else>
									<a href="#" onclick="javascript:sort(0)">相关性</a>| 
				</s:else>
								<s:if test="od==1">
									<b>最多下载</b> |
				</s:if>
								<s:else>
									<a href="#" onclick="javascript:sort(1)">最多下载</a>| 
				</s:else>
								<s:if test="od==2">
									<b>最新上传</b>
								</s:if>
								<s:else>
									<a href="#" onclick="javascript:sort(2)">最新上传</a>
								</s:else>

							</div>
							<s:iterator id="pageDataList" status="rowstatus" value="page.data">
								<dl>
									<dt>
										<span title="<s:property value='fileExtName' />"
											class="<s:property value='fileExtName' />  icon"></span>
										<a href="<%=path%>/portal/fileview/fileViewAction!toViewPage.action?libFileId=<s:property  value='sid'/>"
											target="_blank"><s:property escape="false" value='fileTitle' /></a>
										<span class="ml12 gray"><s:date name="uploadDate" format="yyyy-MM-dd" />
										</span>
									</dt>
									<dd>
										<p class="summary">
											<s:property escape="false" value="bestFragment" />
											...
										</p>
										<p class="detail">
											贡献者:
											<a href="<%=path%>/portal/user/userInfoAction!showUserInfo.action?libUserId=<s:property value='uploadBy'/>"
                                                target="_blank"> <s:property value="uploadByName" />
                                            </a>
											<b>|</b> 下载:
											<s:property value="downloadNum" />
											次
											<b>|</b>

											<s:set name="oncounter" value="evalValue/1" />
											<s:if test="evalValue%1==0">
												<s:set name="halfcounter" value="0" />
											</s:if>
											<s:else>
												<s:set name="halfcounter" value="1" />
											</s:else>
											<s:set name="offcounter" value="5-#oncounter-#halfcounter" />

											<s:bean name="org.apache.struts2.util.Counter" id="oncount">
												<s:param name="first" value="1" />
												<s:param name="last" value="oncounter" />
												<s:iterator>
													<span class="icon star-small-on"
														title="文档得分: <s:property value='evalValue' /> 分"></span>
												</s:iterator>
											</s:bean>

											<s:bean name="org.apache.struts2.util.Counter" id="halfcount">
												<s:param name="first" value="1" />
												<s:param name="last" value="halfcounter" />
												<s:iterator>
													<span class="icon star-small-half"
														title="文档得分: <s:property value='evalValue' /> 分"></span>
												</s:iterator>
											</s:bean>

											<s:bean name="org.apache.struts2.util.Counter" id="offcount">
												<s:param name="first" value="1" />
												<s:param name="last" value="offcounter" />
												<s:iterator>
													<span class="icon star-small-off"
														title="文档得分: <s:property value='evalValue' /> 分"></span>
												</s:iterator>
											</s:bean>

											<s:property value="evalNum" />
											人评
											<b>|</b> 共
											<s:property value="pageNum" />
											页
										</p>
									</dd>
								</dl>
							</s:iterator>
							<div class="mt f14 page">
								<s:if test="page != null">
									<s:if test="page.startPageNo > 1">
										<a style="cursor: pointer; color: #0000b6"
											onclick="javascript:toPage(1);">[首页]</a>
									</s:if>
									<s:if test="page.currentPageNo > 1">
										<a style="cursor: pointer; color: #0000b6"
											onclick="javascript:toPage(<s:property value='%{page.currentPageNo - 1}'/>);">[上一页]</a>
									</s:if>
									<s:bean name="org.apache.struts2.util.Counter" id="pages">
										<s:param name="first" value="page.startPageNo" />
										<s:param name="last" value="page.endPageNo" />
										<s:iterator status="status">
											<s:if
												test="page.currentPageNo != (page.startPageNo + #status.index)">
												<a style="cursor: pointer; color: #0000b6"
													onclick="javascript:toPage(<s:property value='%{page.startPageNo + #status.index}'/>);">
													[<s:property value="%{page.startPageNo + #status.index}" />]</a>
											</s:if>
											<s:else>
				                   [<s:property
													value="%{page.startPageNo + #status.index}" />]
				               </s:else>
										</s:iterator>
									</s:bean>
									<s:if test="page.currentPageNo < page.totalPageCount">
										<a style="cursor: pointer; color: #0000b6"
											onclick="javascript:toPage(<s:property value='%{page.currentPageNo +1}'/>);">[下一页]</a>
									</s:if>
									<s:if test="page.endPageNo < page.totalPageCount">
										<a style="cursor: pointer; color: #0000b6"
											onclick="javascript:toPage(<s:property value='page.totalPageCount'/>);">[尾页]</a>
									</s:if>
								</s:if>
							</div>
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
							<td class="mod_center">
								<div class="content">
									<div class="upload" id="upload"></div>
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
				<div class="clear"></div>
				<form action="search.action" name="fbottom" id="bottomSearchBox"
					method="post">
					<div class="mt12 sbai">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td class="mod_lefttop_b"></td>
								<td class="mod_centertop_b"></td>
								<td class="mod_righttop_b"></td>
							</tr>
							<tr>
								<td class="mod_left_b"></td>
								<td height="60" class="mod_center_b">
								
								<table width="661" border="0" align="center" cellpadding="0" cellspacing="4" style=" margin-top:15px;">
					            <tr>
					              <td width="305" nowrap="nowrap">
					              <input type="radio" name="type" value="all" checked id="ball"/>全部&nbsp;&nbsp;
					              <input type="radio" name="type" value="doc" id="bdoc"/>DOC&nbsp;&nbsp;<input type="radio" name="type" value="pdf" id="bpdf"/>PDF&nbsp;&nbsp;
					              <input type="radio" name="type" value="ppt" id="bppt"/>PPT&nbsp;&nbsp;<input type="radio" name="type" value="xls" id="bxls"/>XLS&nbsp;&nbsp;
					              <input type="radio" name="type" value="txt" id="btxt"/>TXT
					              <td width="248"><input type="text" class="index_seach_input" style="width:99%;" maxlength="256" tabindex="1" value="<s:property  value='kw' />" size="42" name="kw" id="bskw"/></td>
					              <td width="92" align="center"><img src="<%=path%>/portal/images/seach_button.png" width="75" height="25" title="搜索文档" alt="搜索文档" style="border:0px; cursor:pointer;" onClick="javascript:bottonSearch();"/>
					              </td>
					            </tr>
					          </table>	
								</td>
								<td class="mod_right_b"></td>
							</tr>
							<tr>
								<td class="mod_leftfoot_b"></td>
								<td class="mod_centerfoot_b"></td>
								<td class="mod_rightfoot_b"></td>
							</tr>
						</table>
					</div>
				</form>
			</div>
		</div>
	</body>
</html>