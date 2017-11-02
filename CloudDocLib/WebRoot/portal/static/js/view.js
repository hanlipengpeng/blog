var view = {
	changeDocInfo : function(C) {
		var B = (/Category/.test(C)) ? "更改分类" : "更改下载分数";
		var A = (/Category/.test(C)) ? 0 : 1;
		pop.show(B, {
			url : "/static/html/changeDocInfo.html?ct=" + A + "&docid="
					+ DOC_INFO.doc_id + "&score=" + DOC_INFO.price,
			width : 440,
			height : 270
		})
	},
	download : function() {
		function B() {
			pop.hide();
			document.downloadForm.submit()
		}
		if (DOC_INFO.price > DOC_INFO.wealth) {
			var D = "", A = "javascript:";
			if (DOC_INFO.level > 1) {
				A = DOC_INFO.url + "/album/new"
			} else {
				D = "<span>（二级以上用户才可创建文辑）</span>"
			}
			var C = '<div class="downloadInfo"><h2>下载提示：下载此文档需要消耗'
					+ DOC_INFO.price
					+ '财富值</h2><div class="error-info">您当前的财富值为'
					+ DOC_INFO.wealth
					+ '，不足以支付下载此文档哦!</div><div class="line"></div><div class="info"><p class="f-14">您可以通过以下几种途径获取财富值：</p><ul><li>1.上传文档</li><li>2.评价文档<span>（为他人上传的文档打分）</span></li><li>3.创建文辑<span>（把优质的文档按照某个主题集合成一个专辑）</span></li><li>4.评价文辑<span>（为他人创建的文辑打分）</span></li></ul><div class="button"><span class="upload-doc">&nbsp;&nbsp;&nbsp;&nbsp;</span><a href="#" id="uploadDoc" >上传文档</a><span class="create-album" id="createAlbumIcon" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><a href="'
					+ A
					+ '" id="createAlbum" >创建文辑</a>'
					+ D
					+ '</div><div>更多财富值详情请查看<a href="http://www.baidu.com/search/wenku/help.html" target="_blank">文库帮助</a></div><div class="act"><input type="button" class="pop_btn_short ml12" value="关闭" onclick="pop.hide()"></div></div></div>';
			pop.show("提示", {
				info : C,
				width : 405,
				height : 405
			});
			baidu.on("uploadDoc", "click", uploadDoc)
		} else {
			if (DOC_INFO.price == 0) {
				var C = '<div class="dl-info"><div class="doc-meta"><h5><span class="'
						+ DOC_INFO.doc_type
						+ ' icon" title="'
						+ DOC_INFO.doc_type
						+ '"></span>'
						+ DOC_INFO.doc_name
						+ '</h5>文件大小：<span class="doc-size">'
						+ DOC_INFO.doc_size
						+ '</span></div><div class="dl-notice"><p>这是一篇免费文档,您可以直接下载。</p><p>下载以后也别忘记贡献一份力哦！</p></div></div><div class="dl-act"><input type="button" id="confirmDownload" class="pop_btn_short" value="下载"></div>';
				pop.show("提示", {
					info : C,
					width : 405,
					height : 270
				})
			} else {
				if (DOC_INFO.price <= DOC_INFO.wealth) {
					var C = '<div class="dl-info"><h5>'
							+ DOC_INFO.doc_name
							+ '</h5><div class="doc-meta"><span class="doc-size">文件大小：'
							+ DOC_INFO.doc_size
							+ '</span><span class="doc-price">所需积分：<span title="下载需要财富值'
							+ DOC_INFO.price
							+ '分" class="icon big_score"></span>'
							+ DOC_INFO.price
							+ '</span></div><div class="dl-notice">本次下载将耗费财富值<strong>'
							+ DOC_INFO.price
							+ '</strong>分，您确定下载么？</div></div><div class="dl-act"><input type="button" id="confirmDownload" class="pop_btn_short" value="确认"><input type="button" class="pop_btn_short ml12" value="取消" onclick="pop.hide()"></div>';
					pop.show("提示", {
						info : C,
						width : 405,
						height : 270
					})
				}
			}
		}
		baidu.on("confirmDownload", "click", B)
	},
	cang : function(A) {
		window
				.open("http://cang.baidu.com/do/add?it="
						+ encodeURIComponent(document.title) + "&iu="
						+ encodeURIComponent(location.href)
						+ "&tn=文库&fr=wk#nw=1", "_s",
						"scrollbars=no,width=600,height=450,right=75,top=20,status=no,resizable=yes");
		pop.show("提示", {
			url : "/static/html/empty.html",
			width : 420,
			height : 250
		});
		document.AddToStore.submit();
		baidu.preventDefault(A)
	},
	shareDoc : function() {
		pop.show("分享文档", {
			url : "/static/html/shareDoc.html",
			width : 446,
			height : 345
		})
	},
	addToAlbum : function() {
		baidu.ajax
				.post(
						"/album/async",
						"type=user_album",
						function(xhr, responseText) {
							var obj = eval("(" + xhr.responseText + ")"), html = "";
							if (obj.length == 0) {
								var msg = '<p>您还没有文辑，请<a href="/album/new">创建文辑</a></p>';
								pop.show("提示", {
									info : msg,
									width : 340,
									height : 200
								});
								return
							}
							for (i = 0; i < obj.length; i++) {
								if (i == 0) {
									html += '<p><input id="album'
											+ obj[i].album_id
											+ '" onfocus="this.blur()" name="radio" type="radio" id="radio" value="radio" checked="checked" />'
											+ obj[i].title + "</p>"
								} else {
									html += '<p><input id="album'
											+ obj[i].album_id
											+ '" onfocus="this.blur()" name="radio" type="radio" id="radio" value="radio" />'
											+ obj[i].title + "</p>"
								}
							}
							var msg = '<p style="text-align:left;line-height:150%;padding-bottom:2px;margin-top:-15px;">把 <b>'
									+ DOC_INFO.doc_name
									+ '</b> 加入文辑</p><form id="addDocs" method="post" action="/album/submit" name="addDocs" target="popIframe"><div class="album-list" id="albumList">'
									+ html
									+ '</div><input type="submit" id="confirmAlbum" class="pop_btn_short" value="确认"><input type="button" class="pop_btn_short ml12" value="取消" onclick="pop.hide()"><input type="hidden" value="20017" name="ct" id="ct"><input type="hidden" id="viewAlbumId" value="" name="album_id"><input type="hidden" value="http://wenku.baidu.com/view/'
									+ DOC_INFO.doc_id
									+ '.html" id="viewDocsId" name="docs[0]"></form>';
							pop.show("加入文辑", {
								info : msg,
								width : 400,
								height : 280
							});
							baidu.on("addDocs", "submit", view.docsSubmit)
						})
	},
	docsSubmit : function(B) {
		baidu.preventDefault(B);
		baidu.stopPropagation(B);
		var A = G("albumList").getElementsByTagName("input"), C;
		for (i = 0; i < A.length; i++) {
			if (A[i].checked == true) {
				G("viewAlbumId").value = A[i].id.slice(5)
			}
		}
		pop.show("提示", {
			url : "/static/html/empty.html",
			width : 340,
			height : 220
		});
		document.addDocs.submit();
		baidu.preventDefault(B)
	},
	showHideSummary : function(A) {
		if (A == 1) {
			baidu.hide("summary");
			baidu.show("full-summary")
		} else {
			baidu.show("summary");
			baidu.hide("full-summary")
		}
	},
	jumpToReader : function() {
		this.scrollToEl("readerContainer")
	},
	scrollToEl : function(A) {
		if (view._isScrolling) {
			return
		}
		view._isScrolling = true;
		var A = baidu.G(A);
		this._winot = baidu.body().scrollTop;
		this._elot = baidu.dom.getPosition(A).top;
		this._scunit = 30;
		view.scrolling = function() {
			if (view._elot - view._winot < 0) {
				view._isScrolling = false;
				return
			}
			var B = view._scunit;
			if (view._elot - view._winot < view._scunit) {
				B = 1
			}
			window.setTimeout(function() {
				window.scrollTo(0, view._winot = view._winot + B);
				view.scrolling()
			}, 0)
		};
		view.scrolling()
	},
	getViewedPage : function() {
		var B = DOC_INFO.doc_id;
		if (!B) {
			return "0"
		}
		var A = cookie.get("viewedPg");
		if (!A) {
			return "0"
		}
		var C = new RegExp(B + "=([0-9]+)");
		return C.exec(A) ? C.exec(A)[1] : "0"
	},
	saveViewedPage : function() {
		var B = DOC_INFO.doc_id;
		if (!B) {
			return
		}
		var D = baidu.swf.getMovie("reader").NS_IK_getPagethOnClose();
		if (!D || D <= 1) {
			return
		}
		var A = cookie.get("viewedPg");
		if (!A) {
			A = B + "=" + D
		} else {
			if (this.getViewedPage(B) != "0") {
				var C = new RegExp(B + "=[0-9]+[|]?");
				A = A.replace(C, "");
				A = A.replace(/[|]$/, "");
				A += ((A.indexOf("|") > -1) ? "|" : "") + B + "=" + D
			} else {
				if (A.split("|").length >= 10) {
					A = A.replace(/^\w+=\d+\|/, "")
				}
				A += "|" + B + "=" + D
			}
		}
		cookie.set("viewedPg", A, cookie.defaultExpires)
	}
};
function Rate(ele, sendparams, url,file_id,evelType) {
	var _ele = G(ele), _score, int_score, is_odd;
	var _sendparams = sendparams || false;
	var _url = url || false;
	var fileId = file_id;
	rate_flag = false;
	var tip = [ "很差", "较差", "还行", "推荐", "力荐", "请点击星星选择", "谢谢您的评价!", "您已评价" ];
	function create(score) {
		int_score = parseInt(score);
		is_odd = (score > int_score);
		var html = "";
		if (int_score > 5) {
			return
		}
		var real_score = (is_odd) ? int_score + ".5" : int_score + ".0";
		html += '<span id="rateScore" class="score">' + real_score + "</span>";
		html += '<span id="rateStar" class="star-list">';
		for ( var j = 1; j <= 5; j++) {
			html += item(real_score)
		}
		html += "</span>";
		html += '<span class="rate-tip" id="rateTip"></span>';
		_ele.innerHTML = html;
		update(int_score, is_odd);
		binding(_ele)
	}
	function setvalue(score) {
		int_score = parseInt(score);
		is_odd = (score > int_score);
		var html = "";
		if (int_score > 5) {
			return
		}
		var real_score = (is_odd) ? int_score + ".5" : int_score + ".0";
		html += '<span id="rateScoreValue" class="score">' + real_score
				+ "</span>";
		html += '<span id="rateStarValue" class="star-list">';
		for ( var j = 1; j <= 5; j++) {
			html += item(real_score)
		}
		html += "</span>";
		_ele.innerHTML = html;
		update(int_score, is_odd, "rateStarValue")
	}
	function update(num, _odd) {
		var obj = baidu.GT("span", G("rateStar"));
		if (arguments.length == 3) {
			obj = baidu.GT("span", G(arguments[2]))
		}
		for ( var k = 0; k < num; k++) {
			obj[k].className = "icon star-big-on"
		}
		if (_odd) {
			obj[k].className = "icon star-big-half";
			k++
		}
		for ( var m = k; m < 5; m++) {
			obj[m].className = "icon star-big-off"
		}
	}
	function binding(_ele) {
		var stars = baidu.GT("span", G("rateStar"));
		for ( var i = 0; i < stars.length; i++) {
			baidu.on(stars[i], "mouseover", (function(num) {
				return function() {
					if (rate_flag) {
						return false
					}
					update(num + 1, false);
					G("rateTip").innerHTML = tip[num]
				}
			})(i));
			baidu.on(stars[i], "click", (function(n) {
				return function() {
					if (rate_flag) {
						return false
					}
					rate_flag = true;
					send_data(n + 1)
				}
			})(i))
		}
		baidu.on("rateStar", "mouseout", function() {
			if (rate_flag) {
				return false
			}
			update(int_score, is_odd);
			G("rateTip").innerHTML = ""
		})
	}
	function send_data(v) {
		
		var params;
		$.ajax({
            url: getRootPath() + '/portal/library/libraryAction!toSetFileEvelValue.action',
            type: 'get',
            dataType: 'json',
            timeout: 60000,
            data:{
            	id: fileId,
            	evelValue:v,
            	evelType:evelType
            },
            error: function(){
                alert("服务异常");
            },
            success: function(data){
                var o=eval(data);
                if (o.success) {
					create(o.averageValue);
					G("rateTip").innerHTML = tip[6];
					G("rateCount").innerHTML = parseInt(G("rateCount").innerHTML) + 1
				} else {
					G("rateTip").innerHTML = tip[7];
					update(int_score, is_odd)
				}
				setTimeout(function() {
					G("rateTip").innerHTML = ""
				}, 2000)
            }
        });
        return ;
		baidu.ajax.post(
						getRootPath() + '/portal/library/libraryAction!toSetFileEvelValue.action',
						{
            	id: fileId,
            	evelValue:v
            },
						function(xhr) {
							var obj = eval(xhr.responseText);
							if (obj[0]["status"] == "0") {
								create(obj[0].value_average);
								G("rateTip").innerHTML = tip[6];
								G("rateCount").innerHTML = parseInt(G("rateCount").innerHTML) + 1
							} else {
								G("rateTip").innerHTML = tip[7];
								update(int_score, is_odd)
							}
							setTimeout(function() {
								G("rateTip").innerHTML = ""
							}, 2000)
						})
	}
	function item(score) {
		return '<span title="文档得分:' + score
				+ '分" class="icon star-big-off"></span>'
	}
	this.create = create;
	this.setvalue = setvalue
}
function docsSubmit(A) {
	baidu.preventDefault(A);
	baidu.stopPropagation(A);
	alert(1);
	pop.show("提示", {
		url : "/static/html/empty.html",
		width : 420,
		height : 250
	});
	document.addDocs.submit();
	baidu.preventDefault(A)
};