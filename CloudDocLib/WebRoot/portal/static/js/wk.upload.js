var wk = wk || {};
wk.Category = function(A, B) {
	this.init(B);
	this._doc = A || null
};
baidu
		.extend(
				wk.Category.prototype,
				{
					popId : "wenkuCatePop",
					id : "classTableLevel",
					name : "class_table_level_",
					selectClass : "class-table",
					html : "",
					init : function(H) {
						if (!!wk.Category.prototype.inited || !class_level_1
								|| !class_level_2 || !class_level_3) {
							return
						}
						var B = wk.Category.prototype, F = this, E = "";
						var D = H || "上传的文档";
						E += "<h5>为您" + D + "选择一个合适的分类</h5>";
						E += '<div id="selectContainer">';
						for ( var C = 1; C <= 3; C++) {
							E += '<select id="' + this.id + C + '" name="'
									+ this.name + C + '" size="9" class="'
									+ this.selectClass + '">';
							E += "</select>"
						}
						E += "</div>";
						E += '<div id="confirmHolder"><input class="pop_btn_short" type="button" id="confirmButton" value="\u786E\u5B9A" /></div>';
						var A = B.container = document.createElement("div");
						A.id = "wenkuCategoryCotainer";
						A.innerHTML = '<div id="' + B.id + '">' + E + "</div>";
						A.style.display = "none";
						document.body.appendChild(A);
						B.firstSelect = G(this.id + "1");
						B.secondSelect = G(this.id + "2");
						B.thirdSelect = G(this.id + "3");
						B.confirmBtn = G("confirmButton");
						this.buildFirstOption();
						this.buildSecondOption(class_level_1[0][0]);
						B.inited = true
					},
					show : function(B) {
						if (!!wk.Category.active) {
							return
						}
						var C = '<div id="' + this.popId + '"></div>', D = this, A = wk.Category.prototype;
						wk.Category._changeHandler = function() {
							D.changeHandler.call(D, this.value)
						};
						wk.Category._confirmHandler = function() {
							D.confirmHandler.call(D)
						};
						baidu.on(A.firstSelect, "change",
								wk.Category._changeHandler);
						baidu.on(A.secondSelect, "change",
								wk.Category._changeHandler);
						baidu.on(A.thirdSelect, "change",
								wk.Category._changeHandler);
						baidu.on(A.confirmBtn, "click",
								wk.Category._confirmHandler);
						pop.show("选择分类", {
							info : C,
							width : 500,
							height : 320,
							onclosed : function() {
								D.closeHandler.call(D)
							}
						});
						if (B) {
							this.setValue(B)
						}
						G(this.popId).appendChild(G(this.id));
						wk.Category.active = true
					},
					getValue : function(D) {
						var A = [], C;
						for ( var B = 1; B < 4; B++) {
							C = G(this.id + B);
							C.value != "" ? A.push(C.value.replace(/-\d+/, ""))
									: A.push("0")
						}
						return D ? A[D - 1] : A
					},
					getText : function(D) {
						var A = [], C;
						for ( var B = 1; B < 4; B++) {
							C = G(this.id + B);
							(!!C.value && C.value.indexOf("0-") != 0) ? A
									.push(C[C.selectedIndex].text) : ""
						}
						return D ? A[D - 1] : A
					},
					setValue : function(E) {
						var E = E.split("-"), D, A = E.length;
						for ( var C = 1; C <= A; C++) {
							D = G(this.id + C);
							for ( var B = 0, F = D.length; B < F; B++) {
								if (D[B].value == E[C - 1] + "-" + C) {
									D[B].selected = true;
									this.changeHandler(D[B].value)
								}
							}
						}
					},
					buildFirstOption : function() {
						this.buildOption(class_level_1, 1, this.firstSelect)
					},
					buildSecondOption : function(A) {
						this.buildOption(this.getSubLevelData(A, 1), 2,
								this.secondSelect, true);
						baidu.hide(this.thirdSelect);
						this.thirdSelect.length = 0
					},
					buildThirdOption : function(A) {
						this.buildOption(this.getSubLevelData(A, 2), 3,
								this.thirdSelect, true)
					},
					buildOption : function(F, I, E, D) {
						if (!F) {
							baidu.hide(E);
							return
						}
						var C = 0, B = 0, A = F.length, H;
						E.length = 0;
						if (D) {
							E.options[C++] = new Option("\u4E0D\u9009", "0-"
									+ I)

						}
						for (; B < A; B++) {
							_opt = F[B];
							E.options[C++] = new Option(_opt[_opt.length - 1],
									_opt[_opt.length - 2] + "-" + I)
						}
						E.length = C;
						E.options[0].selected = true;
						baidu.show(E)
					},
					getSubLevelData : function(F, C) {
						var E = [], B = (C == 1) ? class_level_2
								: class_level_3;
						for ( var D = 0, A = B.length; D < A; D++) {
							if (F == B[D][0]) {
								E.push(B[D])
							}
						}
						return E.length > 0 ? E : false
					},
					changeHandler : function(A) {
						var C = A.replace(/\d+-/, ""), B = A
								.replace(/-\d+/, "");
						if (C == 1) {
							this.buildSecondOption(B)
						} else {
							if (C == 2) {
								this.buildThirdOption(B)
							}
						}
						this.fireEvent("categoryChange", this.getValue(), this
								.getText())
					},
					closeHandler : function() {
						var A = wk.Category.prototype;
						this.container.appendChild(G(this.id));
						wk.Category.active = false;
						this.fireEvent("categoryClose");
						baidu.un(A.firstSelect, "change",
								wk.Category._changeHandler);
						baidu.un(A.secondSelect, "change",
								wk.Category._changeHandler);
						baidu.un(A.thirdSelect, "change",
								wk.Category._changeHandler);
						baidu.un(A.confirmBtn, "click",
								wk.Category._confirmHandler)
					},
					confirmHandler : function() {
						pop.hide();
						this.fireEvent("categoryConfirm", this.getValue(), this
								.getText())
					},
					fireEvent : function() {
						if (!this._doc) {
							return
						}
						this._doc.fireEvent.apply(this._doc, arguments)
					}
				});
var wk = wk || {};
wk.TagSuggestion = function(A) {
	baidu.extend(this, A);
	this.init()
};
baidu
		.extend(
				wk.TagSuggestion.prototype,
				{
					input : null,
					tagList : null,
					doc : null,
					inputId : "",
					tagListId : "",
					init : function() {
						var B = this;
						if (!this.inputId || !this.tagListId) {
							return
						}
						this.input = G(this.inputId);
						this.tagList = G(this.tagListId);
						baidu.on(this.input, "focus", function() {
							window.LastTagIns = B;
							baidu.sug = function(C) {
								if (C.s.length > 5) {
									C.s = C.s.slice(0, 5)
								}
								B.sugIns.giveData(C.q, C.s)
							}
						});
						var A = function() {
						};
						this.sugIns = baidu.suggestion.create(this.input, {
							onhighlight : A,
							onhide : A,
							onpick : A,
							onconfirm : A,
							getData : function(D) {
								if (D == "") {
									return false
								}
								var C = "http://nssug.baidu.com/su?wd="
										+ encodeURIComponent(D) + "&t="
										+ (new Date()).getTime()
										+ "&prod=iknow-team";
								baidu.loadJS(C)
							}
						})
					},
					pickHandler : function(D, A, C, B) {
						this.lastStr = C
					},
					confirmHandler : function(D, A, C, B) {
						this.input.focus();
						this.lastStr = C
					},
					getTagList : function(B) {
						if (!B) {
							return
						}
						var A = "http://wenku.baidu.com/async?ct=20026&cid1="
								+ B[0]
								+ "&cid2="
								+ B[1]
								+ "&cid3="
								+ B[2]
								+ "&callback=window.LastActiveDoc.tagSuggestion.parseTagList&_t="
								+ (new Date()).getTime();
						baidu.loadJS(A)
					},
					parseTagList : function(D) {
						if (!this.doc || !D) {
							return
						}
						var E = this.doc.getEl("sug-tags"), B = "";
						B += '<li><span class="reco-kw">推荐关键词：</span></li>';
						for ( var C = 0, A = D.length; C < A; C++) {
							B += '<li><a href="#addTag">' + D[C] + "</a></li>"
						}
						E.innerHTML = B
					}
				});
var wk = wk || {};
wk.upload = {
	tips : {
		title_tip : "请输入标题",
		summary_tip : "简要介绍资料的主要内容，以获得更多的关注",
		no_title : "请填写文档标题",
		title2long : "标题过长，请删减",
		no_summary : "请简要填写文档介绍",
		summary2long : "文档介绍过长，请删减",
		unaccept_chars : "抱歉，您输入的字符我们暂不支持，请修改",
		kw2long : "每个关键词最多10个汉字",
		kw_tip : "选填，多个关键词用逗号分隔",
		kw2many : "您最多可以添加5个关键词",
		sfilter : '您的标题不符合规范，请修改后提交。<span style="color:black;">查看<a target="_blank" href="http://www.baidu.com/search/wenku/help.html#文库协议">帮助</a></span>',
		filter : '<span style="color:black;">您的文档标题是不是可以更丰富一下呢？查看<a target="_blank" href="http://www.baidu.com/search/wenku/help.html#文库协议">帮助</a></span>',
		no_category : "请选择文档分类"
	},
	docSet : {},
	init : function() {
		var A = this;
		this.addEvent("startUpload", this.startHandler);
		this.addEvent("setUploaderHeight", this.setUploaderHeight);
		this.addEvent("fileSuccess", this.fileSuccessHandler);
		this.addEvent("fileFail", this.fileFailHandler);
		this.addEvent("submitFlag", this.allCompleteHandler);
		this.addEvent("cancelFile", this.cancelFileHandler);
		this.addEvent("deleteFile", this.deleteFileHandler);
		this.addEvent("reUpload", this.reUploadHandler);
		this.addEvent("jumpTo", this.jumpToHandler);
		this.addEvent("errorTips", this.flashErrorHandler);
		this.addEvent("pageInit", this.pageInitHandler);
		this.addEvent("seriesDoc", function(C, B) {
			if (B == 1) {
				wk.upload.fillall.show()
			}
		});
		baidu.on(G("uploadForm"), "submit", function(B) {
			A.submitCheck(B)
		});
		baidu.on(G("showFillall"), "click", function() {
			wk.upload.fillall.show()
		});
		baidu.on(G("close-fillall"), "click", function() {
			wk.upload.fillall.hide()
		});
		baidu.on(G("clip-form"), "click", function() {
			if (!wk.upload.isUnitHide) {
				A.clipForm("hide")
			} else {
				A.clipForm("show")
			}
		});
		baidu.on(G("submitBtn"), "mouseover", function() {
			if (G("submitBtn").disabled == "disabled") {
				return
			}
			baidu.addClass(G("submitBtn"), "hover")
		});
		baidu.on(G("submitBtn"), "mouseout", function() {
			baidu.removeClass(G("submitBtn"), "hover")
		})
	},
	changeStep : function(B) {
		var A = (B == 1) ? 2 : 1;
		baidu.hide("step" + A);
		baidu.show("step" + B)
	},
	clipForm : function(B) {
		var B = B || "hide";
		for ( var A in this.docSet) {
			this.docSet[A].clipForm(B)
		}
		if (G("clip-form")) {
			G("clip-form").innerHTML = B == "hide" ? "全部展开" : "全部收起"
		}
		wk.upload.isUnitHide = B == "hide" ? true : false
	},
	startHandler : function(I, A) {
		var D = "", K, H = A.length, B, J = cookie.get("LASTCID"), C = this;
		if (!A) {
			return
		}
		this.changeStep(2);
		for ( var F = 0; F < H; F++) {
			K = A[F];
			D += (new wk.upload.DocUnit(K.title, K.index)).renderForm()
		}
		G("docUnitHolder").innerHTML = D;
		for ( var E in this.docSet) {
			this.docSet[E].bindFormEvent();
			J = ""
		}
		if (H == 1) {
			baidu.addClass(this.docSet[0].getEl("doc-holder"), "only-one");
			baidu.hide(this.docSet[0].getEl("head"));
			baidu.hide(G("upload-tips"));
			G("fill-one").innerHTML = "填写文档信息"
		}
		setTimeout(
				function() {
					var M = C.docSet[0].category.getValue(), L = "http://wenku.baidu.com/async?ct=20026&cid1="
							+ M[0]
							+ "&cid2="
							+ M[1]
							+ "&cid3="
							+ M[2]
							+ "&callback=wk.upload.initKwSug&_t="
							+ (new Date()).getTime();
					baidu.loadJS(L)
				}, 0);
		window.onbeforeunload = function() {
			if (wk.upload._formOk) {
				return
			}
			return "离开页面会丢失已经填写的信息。"
		}
	},
	initKwSug : function(E) {
		var C = "";
		C += '<li><span class="reco-kw">推荐关键词：</span></li>';
		for ( var D = 0, A = E.length; D < A; D++) {
			C += '<li><a href="#addTag">' + E[D] + "</a></li>"
		}
		for ( var B in this.docSet) {
			this.docSet[B].getEl("sug-tags").innerHTML = C
		}
	},
	pageInitHandler : function() {
		this.changeStep(1)
	},
	cancelFileHandler : function(B, A) {
		this.docSet[A].cancelFile()
	},
	deleteFileHandler : function(B, A) {
		this.docSet[A].deleteFile()
	},
	fileSuccessHandler : function(B, A) {
		this.docSet[A.index].uploadSuccess(A.docid)
	},
	fileFailHandler : function(B, A) {
		this.docSet[A].uploadError()
	},
	reUploadHandler : function(B, A) {
		this.docSet[A.index].reUpload(A)
	},
	jumpToHandler : function(B, A) {
		wk.upload.clipForm("hide");
		wk.upload.docSet[A].clipForm("show");
		window.location.hash = "#doc-holder-" + A
	},
	flashErrorHandler : function(H, F) {
		var C = "", E, A = F.docs.length, D = 20 * A + 130, B;
		C += '<h5 style="margin:0 3px 5px;text-align:left;">' + F.reason
				+ "</h5>";
		C += '<ul style="text-align:left;">';
		for (E = 0; E < A; E++) {
			B = F.docs[E].split(".");
			B = B[B.length - 1];
			C += '<li style="height:21px;width:340px;overflow:hidden;white-space:nowrap;list-style:none inside"><span title="'
					+ B
					+ '" class="'
					+ B
					+ ' icon"></span>'
					+ F.docs[E]
					+ "</li>"
		}
		C += "</ul>";
		pop.show("文库提示", {
			info : C,
			width : 420,
			height : D
		});
		G("popContent").valign = "top"
	},
	isLogin : function() {
		var A = cookie.get("BDUSS");
		if (A) {
			return 1
		}
		setTimeout(function() {
			login.check(function() {
				var B = cookie.get("BDUSS");
				baidu.swf.getMovie("uploader").NS_IK_doUpload(B)
			})
		}, 0);
		return 0
	},
	allCompleteHandler : function(C, A) {
		var B = G("submitBtn");
		if (A == 1) {
			B.removeAttribute("disabled");
			baidu.removeClass(B, "button-disable")
		} else {
			B.disabled = "disabled";
			baidu.addClass(B, "button-disable")
		}
	},
	submitCheck : function(B) {
		wk.upload.isInputError = false;
		for ( var A in this.docSet) {
			this.docSet[A].submitCheck()
		}
		baidu.preventDefault(B);
		baidu.stopPropagation(B);
		if (wk.upload.isInputError) {
			return
		}
		wk.upload._formOk = true;
		log.send("submit", "uploaddesc");
		setTimeout(function() {
			document.uploadForm.submit()
		}, 500)
	},
	setUploaderHeight : function(B, A) {
		baidu.swf.getMovie("uploader").height = A
	}
};
baidu.enableCustomEvent(wk.upload);
wk.upload.DocUnit = function(B, A) {
	if (!B || typeof A == "undefined") {
		return
	}
	this.parseTitle(B);
	this.index = A;
	wk.upload.docSet[A] = this;
	this.init()
};
baidu
		.extend(
				wk.upload.DocUnit.prototype,
				{
					title : "",
					index : 0,
					type : "",
					varPre : "%#_",
					keywordSp : ",",
					_isFormShow : true,
					init : function() {
						this.addEvent("categoryConfirm", this.categoryHandler)
					},
					parseTitle : function(C) {
						var B = C.split("."), A = B.length;
						this.type = B[A - 1];
						B.pop();
						this.title = B.join(".")
					},
					renderForm : function() {
						var A = this._formTplCache.replace(new RegExp(
								this.varPre + "docID", "g"), this.index);
						A = A.replace(
								new RegExp(this.varPre + "docTitle", "g"),
								this.title);
						A = A.replace(new RegExp(this.varPre + "docType", "g"),
								this.type);
						return A
					},
					bindFormEvent : function(B) {
						var D = this, A, C;
						this.category = new wk.Category(D);
						if (B) {
							this.category.setValue(B)
						}
						baidu.on(this.getEl("cate-set"), "click", function() {
							D.category.show();
							D.clearTip("cate-input")
						});
						this.tagSuggestion = new wk.TagSuggestion({
							inputId : this.getEl("keyword"),
							tagListId : "tag-list",
							doc : this
						});
						baidu.on(this.getEl("title"), "focus", function() {
							D.clearTip("title");
							D.titleCheck()
						});
						baidu.on(this.getEl("title"), "keyup", function() {
							baidu.removeClass(D.getEl("title"), "input-error");
							D.clearTip("title");
							D.titleCheck()
						});
						baidu.on(this.getEl("title"), "blur", function() {
							D.clearTip("title");
							D.titleCheck(true, 0)
						});
						baidu.on(this.getEl("summary"), "focus", function() {
							D.summaryCheck()
						});
						baidu.on(this.getEl("summary"), "keyup", function() {
							baidu
									.removeClass(D.getEl("summary"),
											"input-error");
							D.summaryCheck()
						});
						baidu.on(this.getEl("summary"), "blur", function() {
							D.clearTip("summary")
						});
						baidu.on(this.getEl("keyword"), "focus", function() {
							D.keywordCheck()
						});
						baidu.on(this.getEl("keyword"), "keyup", function() {
							baidu
									.removeClass(D.getEl("summary"),
											"input-error");
							D.keywordCheck()
						});
						baidu.on(this.getEl("keyword"), "blur", function() {
							D.clearTip("keyword")
						});
						baidu.on(this.getEl("sug-tags"), "click", function(E) {
							D.addSugTag(E)
						});
						baidu.on(this.getEl("priceList"), "change", function() {
							D.getEl("nonFree").checked = true
						});
						baidu.on(this.getEl("charge"), "change", function() {
							if (D.getEl("charge").checked) {
								D.getEl("priceList").options[0].selected = true
							}
						});
						baidu.on(this.getEl("head"), "click", function() {
							D.clipForm()
						});
						baidu.on(this.getEl("head"), "mouseover", function() {
							baidu.addClass(D.getEl("head"), "hover")
						});
						baidu.on(this.getEl("head"), "mouseout", function() {
							baidu.removeClass(D.getEl("head"), "hover")
						})
					},
					getEl : function(A) {
						return !!A ? G(A + "-" + this.index) : false
					},
					getTipEl : function(B) {
						if (!B) {
							return
						}
						if (typeof B == "string") {
							B = this.getEl(B)
						}
						var C = baidu.GT("*", B.parentNode);
						for ( var D = 0, A = C.length; D < A; D++) {
							if (/\berror-tip\b/.test(C[D].className)) {
								return C[D]
							}
						}
					},
					clipForm : function(D, F) {
						var E = this.getEl("doc-holder");
						if (!this._isFormShow || D == "show") {
							if (wk.upload._focusDoc) {
								baidu.removeClass(wk.upload._focusDoc
										.getEl("doc-holder"), "doc-focus")
							}
							baidu.removeClass(E, "hide");
							this._isFormShow = true;
							if (!F) {
								try {
									this.getEl("summary").focus()
								} catch (B) {
								}
							}
							baidu.addClass(E, "doc-focus");
							wk.upload._focusDoc = this
						} else {
							baidu.addClass(E, "hide");
							baidu.removeClass(E, "doc-focus");
							this._isFormShow = false;
							var A = this.getEl("title").value, C = this
									.getEl("titleBar");
							C.innerHTML = '<span class="title-bar" id="titleBar-0"><span title="'
									+ this.type
									+ '" class="'
									+ this.type
									+ ' icon"></span>' + A + "</span>"
						}
						if (D == "hide") {
							baidu.addClass(E, "hide");
							this._isFormShow = false;
							var A = this.getEl("title").value, C = this
									.getEl("titleBar");
							C.innerHTML = '<span class="title-bar" id="titleBar-0"><span title="'
									+ this.type
									+ '" class="'
									+ this.type
									+ ' icon"></span>' + A + "</span>"
						}
					},
					showTip : function(B, C) {
						var A = this.getTipEl(B);
						A.innerHTML = C;
						baidu.addClass(A, "not-error")
					},
					showError : function(B, C) {
						var A = this.getTipEl(B);
						A.innerHTML = C;
						baidu.removeClass(A, "not-error")
					},
					clearTip : function(B) {
						var A = this.getTipEl(B);
						A.innerHTML = ""
					},
					isKorean : function(B) {
						for ( var A = 0; A < B.length; A++) {
							var C = B.charCodeAt(A);
							if ((C > 12592) && (C < 12687)) {
								return true
							}
							if ((C >= 44032) && (C <= 55203)) {
								return true
							}
						}
						return false
					},
					hasSpecialChars : function(C) {
						var A = false;
						var B = [ /\\/, /\//, /:/, /\*/, /\?/, /"/, /</, />/,
								/\|/, /;/ ];
						baidu.each(B, function(E, D) {
							if (E.test(C)) {
								A = true
							}
						});
						return A
					},
					titleCheck : function(C, I) {
						var A = this.getEl("title"), D = 100;
						if (this.hasSpecialChars(A.value)
								|| this.isKorean(A.value)) {
							this.showError(A, wk.upload.tips.unaccept_chars);
							return
						}
						var B = D - A.value.byteLength();
						if (B >= 0) {
							this.showTip(A, "你还可以输入" + parseInt(B / 2) + "字")
						} else {
							var E = A.value;
							while (E.byteLength() > D) {
								E = E.substring(0, E.length - 1)
							}
							A.value = E
						}
						var J = this.getEl("title");
						this.isInputError = false;
						var F = J.value.replace(/(^\s+)|(\s+$)/g, "");
						var H = 0;
						if (F == "") {
							this.isInputError = true;
							wk.upload.isInputError = true;
							baidu.addClass(J, "input-error");
							this.showError(J, wk.upload.tips.no_title)
						} else {
							if (this.isKorean(J.value)) {
								this.isInputError = true;
								wk.upload.isInputError = true;
								baidu.addClass(J, "input-error");
								this
										.showError(J,
												wk.upload.tips.unaccept_chars)
							} else {
								if (J.value.byteLength() > 100) {
									this.isInputError = true;
									wk.upload.isInputError = true;
									baidu.addClass(J, "input-error");
									this
											.showError(J,
													wk.upload.tips.title2long)
								} else {
									if (F.search(/^[\x00-\x7f]$/) != -1) {
										this.isInputError = true;
										wk.upload.isInputError = true;
										baidu.addClass(J, "input-error");
										this.showError(J,
												wk.upload.tips.sfilter);
										H = 1
									} else {
										if (F
												.search(/^第([\x00-\x7f]*|.{0,4})(部分|课|单元|卷|章|课时|届|套题|周|讲|回)([\x00-\x7f]{0,4}|.{0,1})$/) != -1) {
											this.isInputError = true;
											wk.upload.isInputError = true;
											baidu.addClass(J, "input-error");
											this.showError(J,
													wk.upload.tips.sfilter);
											H = 2
										} else {
											if (F
													.search(/([a-z0-9])+\.(com|cn|mobi|tel|asia|org|name|me|info|com\.cn|net\.cn|org\.cn|gov\.cn|cc|hk|biz|tv)([^a-z]|$)/i) != -1
													&& F
															.search(/openoffice\.org/i) == -1) {
												this.isInputError = true;
												wk.upload.isInputError = true;
												baidu
														.addClass(J,
																"input-error");
												this.showError(J,
														wk.upload.tips.sfilter);
												H = 3
											} else {
												if (F.search(/新建.*文档/) != -1) {
													this.isInputError = true;
													wk.upload.isInputError = true;
													baidu.addClass(J,
															"input-error");
													this
															.showError(
																	J,
																	wk.upload.tips.sfilter);
													H = 4
												} else {
													if (F
															.search(/^([\x00-\x40]|[\x5b-\x60]|[\x7b-\x7f])*$/) != -1) {
														this.isInputError = true;
														wk.upload.isInputError = true;
														baidu.addClass(J,
																"input-error");
														this
																.showError(
																		J,
																		wk.upload.tips.sfilter);
														H = 5
													} else {
														if (F
																.search(/^[a-z0-9 ]{0,2}chap(ter)?[ 0-9]*$/i) != -1) {
															this.isInputError = true;
															wk.upload.isInputError = true;
															baidu
																	.addClass(
																			J,
																			"input-error");
															this
																	.showError(
																			J,
																			wk.upload.tips.sfilter);
															H = 6
														} else {
															if (function(N) {
																var M = N.length;
																var K = null, L = 0;
																for (; L < M; L++) {
																	Asc = N
																			.charCodeAt(L);
																	if (Asc != 32) {
																		if (Asc > 128) {
																			return false
																		}
																		if (K == null) {
																			K = Asc
																		}
																		if (K != Asc) {
																			break
																		}
																	}
																}
																if (M == L) {
																	return true
																}
																return false
															}(F)) {
																this.isInputError = true;
																wk.upload.isInputError = true;
																baidu
																		.addClass(
																				J,
																				"input-error");
																this
																		.showError(
																				J,
																				wk.upload.tips.sfilter);
																H = 7
															} else {
																if (F
																		.search(/^(附|复)件+/) != -1) {
																	this.isInputError = true;
																	wk.upload.isInputError = true;
																	baidu
																			.addClass(
																					J,
																					"input-error");
																	this
																			.showError(
																					J,
																					wk.upload.tips.sfilter);
																	H = 8
																} else {
																	if (F
																			.search(/^[\u4e00-\u9fa5]$/) != -1) {
																		this
																				.showError(
																						J,
																						wk.upload.tips.filter);
																		H = 9
																	} else {
																		if (F
																				.search(/^[A-Za-z]+$/) != -1) {
																			this
																					.showError(
																							J,
																							wk.upload.tips.filter);
																			H = 10
																		} else {
																			if (F
																					.search(/^[0-9]+[\u4e00-\u9fa5]+$/) != -1) {
																				this
																						.showError(
																								J,
																								wk.upload.tips.filter);
																				H = 11
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
						if (C) {
							log.send("filter", "titlefilter", {
								p : H,
								from : I
							})
						}
					},
					summaryCheck : function() {
						var C = this.getEl("summary"), A = 400;
						if (!C.focusFlag) {
							C.value = "";
							C.focusFlag = true;
							baidu.removeClass(C, "input-init")
						}
						if (this.isKorean(C.value)) {
							this.showError(C, wk.upload.tips.unaccept_chars);
							return
						}
						var D = A - C.value.byteLength();
						if (D >= 0) {
							this.showTip(C, "你还可以输入" + parseInt(D / 2) + "字")
						} else {
							var B = C.value;
							while (B.byteLength() > A) {
								B = B.substring(0, B.length - 1)
							}
							C.value = B
						}
					},
					keywordCheck : function() {
						var B = this.getEl("keyword"), A = 5, D = B.value, C = this.keywordSp;
						if (!B.focusFlag) {
							B.value = "";
							B.focusFlag = true;
							baidu.removeClass(B, "input-init")
						}
						if (this.isKorean(B.value)) {
							this.showError(B, wk.upload.tips.unaccept_chars)
						}
						D = D.replace(/[,\s，]+/, C, "g");
						D = D.split(C);
						if (D.length > A) {
							B.value = D.slice(0, 5).join(C);
							this.showError("keyword", wk.upload.tips.kw2many)
						}
					},
					submitCheck : function() {
						if (this.isUploadError) {
							return
						}
						var D = this.getEl("title"), E = this.getEl("summary"), A = this
								.getEl("keyword"), C = this.getEl("cid1");
						this.isInputError = false;
						this.titleCheck(true, 1);
						var F = D.value.replace(/(^\s+)|(\s+$)/g, "");
						if (E.value.replace(/(^\s+)|(\s+$)/g, "") == ""
								|| E.value == wk.upload.tips.summary_tip
								|| this.isKorean(E.value)) {
							this.isInputError = true;
							wk.upload.isInputError = true;
							baidu.addClass(E, "input-error");
							if (this.isKorean(E.value)) {
								this
										.showError(E,
												wk.upload.tips.unaccept_chars)
							} else {
								this.showError(E, wk.upload.tips.no_summary)
							}
						}
						if (this.isKorean(A.value)) {
							this.isInputError = true;
							wk.upload.isInputError = true;
							baidu.addClass(A, "input-error");
							this.showError(A, wk.upload.tips.unaccept_chars)
						}
						if (C.value == "0") {
							this.isInputError = true;
							wk.upload.isInputError = true;
							var B = this.getEl("cate-input");
							this.showError(B, wk.upload.tips.no_category)
						}
						if (this.isInputError) {
							this.clipForm("show", true)
						} else {
							this.clipForm("hide");
							if (A.value == wk.upload.tips.kw_tip) {
								A.value = ""
							}
						}
					},
					categoryHandler : function(B, A, D) {
						this.getEl("cate-input").innerHTML = D.join("->");
						this.getEl("cate-input").style.display = "";
						this.getEl("cid1").value = A[0];
						this.getEl("cid2").value = A[1];
						this.getEl("cid3").value = A[2];
						window.LastActiveDoc = this;
						cookie.set("LASTCID", A.join("-"));
						var C = this;
						window.setTimeout(function() {
							C.tagSuggestion.getTagList(A)
						}, 0)
					},
					uploadError : function() {
						baidu.addClass(this.getEl("doc-holder"), "iserror");
						this.disableForm()
					},
					cancelFile : function() {
						this.disableForm()
					},
					deleteFile : function() {
						baidu.hide(this.getEl("doc-holder"));
						this.isUploadError = true
					},
					reUpload : function(A) {
						this.parseTitle(A.title);
						this.getEl("title").value = this.title;
						this.getEl("titleBar").innerHTML = '<span title="'
								+ this.type + '" class="' + this.type
								+ ' icon"></span>' + this.title;
						this.enableForm()
					},
					disableForm : function() {
						this.getEl("title").disabled = "disabled";
						this.getEl("cate-input").disabled = "disabled";
						this.getEl("summary").disabled = "disabled";
						this.getEl("keyword").disabled = "disabled";
						this.getEl("charge").disabled = "disabled";
						this.getEl("nonFree").disabled = "disabled";
						this.getEl("priceList").disabled = "disabled";
						this.isUploadError = true
					},
					enableForm : function() {
						this.getEl("title").removeAttribute("disabled");
						this.getEl("cate-input").removeAttribute("disabled");
						this.getEl("summary").removeAttribute("disabled");
						this.getEl("keyword").removeAttribute("disabled");
						this.getEl("charge").removeAttribute("disabled");
						this.getEl("nonFree").removeAttribute("disabled");
						this.getEl("priceList").removeAttribute("disabled");
						this.isUploadError = false
					},
					uploadSuccess : function(A) {
						this.getEl("docId").value = A;
						baidu.removeClass(this.getEl("doc-holder"), "iserror");
						this.enableForm();
						this.isUploadError = false
					},
					addSugTag : function(D) {
						D = D || window.event;
						target = D.target || D.srcElement;
						if (target.tagName.toLowerCase() != "a"
								|| target.selected) {
							return
						}
						var A = this.getEl("keyword");
						if (!A.focusFlag) {
							A.value = "";
							A.focusFlag = true;
							baidu.removeClass(A, "input-init")
						}
						var C = A.value, B = this.keywordSp;
						C = C.replace(/[,\s，]+/, B, "g");
						C = C.replace(new RegExp(B + "+$"), "");
						C = C.split(B);
						if (!C[0]) {
							C = []
						}
						if (C.length > 0 && C.length < 5) {
							C.push(target.innerHTML);
							A.value = C.join(B);
							target.selected = true;
							baidu.addClass(target, "used-tag")
						} else {
							if (C.length >= 5) {
								A.value = C.slice(0, 5).join(B);
								this.showError("keyword",
										wk.upload.tips.kw2many)
							} else {
								A.value = target.innerHTML;
								target.selected = true;
								baidu.addClass(target, "used-tag")
							}
						}
					}
				});
baidu.enableCustomEvent(wk.upload.DocUnit.prototype);
wk.upload.fillall = {
	init : function() {
		var A = this;
		this.category = new wk.Category(this);
		baidu.on(this.getEl("cate-set"), "click", function(B) {
			A.category.show()
		});
		this.tagSuggestion = new wk.TagSuggestion({
			inputId : this.getEl("keyword"),
			tagListId : "tag-list",
			doc : this
		});
		baidu.on(this.getEl("summary"), "focus", function() {
			A.summaryCheck()
		});
		baidu.on(this.getEl("summary"), "keyup", function() {
			baidu.removeClass(A.getEl("summary"), "input-error");
			A.summaryCheck()
		});
		baidu.on(this.getEl("summary"), "blur", function() {
			A.clearTip("summary")
		});
		baidu.on(this.getEl("keyword"), "focus", function() {
			A.keywordCheck()
		});
		baidu.on(this.getEl("keyword"), "keyup", function() {
			baidu.removeClass(A.getEl("summary"), "input-error");
			A.keywordCheck()
		});
		baidu.on(this.getEl("keyword"), "blur", function() {
			A.clearTip("keyword")
		});
		baidu.on(this.getEl("priceList"), "change", function() {
			A.getEl("nonFree").checked = true
		});
		baidu.on(this.getEl("charge"), "change", function() {
			if (A.getEl("charge").checked) {
				A.getEl("priceList").options[0].selected = true
			}
		});
		baidu.on(this.getEl("sug-tags"), "click", function(B) {
			A.addSugTag(B)
		});
		this.addEvent("categoryConfirm", this.categoryHandler);
		baidu.on(this.getEl("button"), "click", function() {
			A.fillDocForm()
		})
	},
	show : function() {
		if (!this.inited) {
			this.init();
			this.inited = true
		}
		wk.upload.clipForm("hide");
		baidu.show(this.getEl("fill"))
	},
	hide : function() {
		baidu.hide(this.getEl("fill"))
	},
	getEl : function(A) {
		return !!A ? G("all-" + A) : false
	},
	fillDocForm : function() {
		var K = wk.upload.docSet, E = this.getEl("cate-input").innerHTML, C = this
				.getEl("cid1").value, B = this.getEl("cid2").value, P = this
				.getEl("cid3").value, N = this.getEl("summary").value, F = this
				.getEl("keyword").value, L = this.getEl("charge"), J = this
				.getEl("priceList").selectedIndex, M = 0, I = {}, H, O, D, A;
		for (H in K) {
			O = K[H];
			if (E) {
				O.getEl("cate-input").innerHTML = E;
				O.getEl("cate-input").style.display = "";
				O.getEl("cid1").value = C;
				O.getEl("cid2").value = B;
				O.getEl("cid3").value = P;
				M++
			}
			if (N && N != wk.upload.tips.summary_tip) {
				D = O.getEl("summary");
				D.value = N;
				D.focusFlag = true;
				baidu.removeClass(D, "input-init");
				O.clearTip(D);
				baidu.removeClass(D, "input-error");
				M++
			}
			if (F && F != wk.upload.tips.kw_tip) {
				D = O.getEl("keyword");
				D.value = F;
				D.focusFlag = true;
				baidu.removeClass(D, "input-init");
				O.clearTip(D);
				baidu.removeClass(D, "input-error");
				M++
			}
			if (L.checked) {
				O.getEl("priceList").options[0].selected = true;
				O.getEl("charge").checked = "checked"
			} else {
				O.getEl("priceList").options[J].selected = true;
				O.getEl("nonFree").checked = "checked"
			}
			if (M == 3) {
				A = "已成功应用！"
			} else {
				A = "已成功应用除空信息外的填写内容！";
				I = {
					width : 240
				}
			}
			wk.note.show(A, I);
			if (!this._applyed) {
				log.send("submit", "batchsummary");
				this._applyed = true
			}
			setTimeout(function() {
				wk.note.hide()
			}, 3000)
		}
	},
	getTipEl : wk.upload.DocUnit.prototype.getTipEl,
	showTip : wk.upload.DocUnit.prototype.showTip,
	showError : wk.upload.DocUnit.prototype.showError,
	clearTip : wk.upload.DocUnit.prototype.clearTip,
	summaryCheck : wk.upload.DocUnit.prototype.summaryCheck,
	isKorean : wk.upload.DocUnit.prototype.isKorean,
	hasSpecialChars : wk.upload.DocUnit.prototype.hasSpecialChars,
	keywordCheck : wk.upload.DocUnit.prototype.keywordCheck,
	keywordSp : wk.upload.DocUnit.prototype.keywordSp,
	categoryHandler : wk.upload.DocUnit.prototype.categoryHandler,
	addSugTag : wk.upload.DocUnit.prototype.addSugTag
};
baidu.enableCustomEvent(wk.upload.fillall);
wk.note = (function() {
	var E, J, I, B, L = 0;
	var M = null;
	var A;
	function D(N) {
		var O = baidu.body();
		B = N.width || 130;
		E = N.left || Math.round(O.scrollLeft + (O.viewWidth - B) / 2 - 147);
		J = N.top || Math.round(O.scrollTop + O.viewHeight);
		A = N.after || function() {
		}
	}
	function K(O, N) {
		if (wk.note.isShow) {
			return
		}
		D(N);
		if (M) {
			H()
		}
		M = document.createElement("div");
		M.className = "tip";
		M.innerHTML = [
				"<div class='tip-top'><div class='tip-r1'></div><div class='tip-r2'></div></div>",
				"<div class='tip-body'><div class='tip-body-note'>",
				O,
				"</div></div>",
				"<div class='tip-bottom'><div class='tip-r3'></div><div class='tip-r4'></div></div>" ]
				.join("");
		M.style.left = E + "px";
		M.style.width = B + "px";
		M.style.top = J + "px";
		document.body.insertBefore(M, document.body.firstChild);
		var P = baidu.body();
		L = Math.round(P.scrollTop + (P.viewHeight - M.scrollHeight) / 2);
		J = L + 50;
		M.style.top = J + "px";
		M.style.zoom = 1;
		C(0);
		I = setInterval(F, 20);
		wk.note.isShow = true
	}
	function F() {
		if (!M) {
			return
		}
		if (J <= L) {
			J = L;
			clearInterval(I);
			setTimeout(A, 2000);
			C(100);
			return
		}
		J -= 5;
		M.style.top = J + "px";
		opacity += 5;
		C(opacity)
	}
	function C(O) {
		if (!M) {
			return
		}
		try {
			M.style.opacity = O / 100;
			if (M.style.filter) {
				M.style.filter = "alpha(opacity=" + O + ")"
			}
		} catch (N) {
		}
		opacity = O
	}
	function H() {
		if (!M) {
			return
		}
		clearInterval(I);
		document.body.removeChild(M);
		M.style.display = "none";
		M = null;
		wk.note.isShow = false
	}
	return {
		show : K,
		hide : H
	}
})();