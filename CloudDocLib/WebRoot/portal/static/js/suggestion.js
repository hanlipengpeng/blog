window.baidu = window.baidu || {
	version : "1-0-0",
	emptyFn : function() {
	}
};
baidu.isString = function(A) {
	return (typeof A == "object" && A && A.constructor == String)
			|| typeof A == "string"
};
baidu.G = function() {
	for ( var A = [], C = arguments.length - 1; C > -1; C--) {
		var B = arguments[C];
		A[C] = null;
		if (typeof B == "object" && B && B.dom) {
			A[C] = B.dom
		} else {
			if ((typeof B == "object" && B && B.tagName) || B == window
					|| B == document) {
				A[C] = B
			} else {
				if (baidu.isString(B) && (B = document.getElementById(B))) {
					A[C] = B
				}
			}
		}
	}
	return A.length < 2 ? A[0] : A
};
baidu.getCurrentStyle = function(B, E) {
	var C = null;
	if (!(B = baidu.G(B))) {
		return null
	}
	if (C = B.style[E]) {
		return C
	} else {
		if (B.currentStyle) {
			C = B.currentStyle[E]
		} else {
			var D = B.nodeType == 9 ? B : B.ownerDocument || B.document;
			if (D.defaultView && D.defaultView.getComputedStyle) {
				var A = D.defaultView.getComputedStyle(B, "");
				if (A) {
					C = A[E]
				}
			}
		}
	}
	return C
};
baidu.trim = function(A, B) {
	if (B == "left") {
		return A.replace(/(^[\s\t\xa0\u3000]+)/g, "")
	}
	if (B == "right") {
		return A.replace(new RegExp("[\\u3000\\xa0\\s\\t]+\x24", "g"), "")
	}
	return A.replace(new RegExp(
			"(^[\\s\\t\\xa0\\u3000]+)|([\\u3000\\xa0\\s\\t]+\x24)", "g"), "")
};
baidu.addClass = function(B, A) {
	if (!(B = baidu.G(B))) {
		return null
	}
	A = baidu.trim(A);
	if (!new RegExp("(^| )" + A.replace(/(\W)/g, "\\\x241") + "( |\x24)")
			.test(B.className)) {
		B.className = baidu.trim(B.className.split(/\s+/).concat(A).join(" "))
	}
};
baidu.ac = baidu.addClass;
baidu.extend = function(C, B) {
	if (C && B && typeof (B) == "object") {
		for ( var A in B) {
			C[A] = B[A]
		}
	}
	return C
};
baidu.browser = baidu.browser || {};
(function() {
	var B = navigator.userAgent;
	baidu.firefox = baidu.browser.firefox = /firefox\/(\d+\.\d)/i.test(B) ? parseFloat(RegExp["\x241"])
			: 0;
	baidu.ie = baidu.browser.ie = /msie (\d+\.\d)/i.test(B) ? parseFloat(RegExp["\x241"])
			: 0;
	baidu.opera = baidu.browser.opera = /opera\/(\d+\.\d)/i.test(B) ? parseFloat(RegExp["\x241"])
			: 0;
	baidu.safari = baidu.browser.safari = (/(\d+\.\d)(\.\d)?\s+safari/i.test(B) && !/chrome/i
			.test(B)) ? parseFloat(RegExp["\x241"]) : 0;
	try {
		baidu.browser.maxthon = /(\d+\.\d)/.test(external.max_version) ? parseFloat(RegExp["\x241"])
				: 0
	} catch (A) {
		baidu.browser.maxthon = 0
	}
	baidu.maxthon = baidu.browser.maxthon;
	baidu.isGecko = baidu.browser.isGecko = /gecko/i.test(B)
			&& !/like gecko/i.test(B);
	baidu.isStrict = baidu.browser.isStrict = document.compatMode == "CSS1Compat";
	baidu.isWebkit = baidu.browser.isWebkit = /webkit/i.test(B)
})();
baidu.each = function(C, A) {
	if (typeof A != "function") {
		return C
	}
	if (C) {
		var B;
		if (C.length === undefined) {
			for ( var F in C) {
				if (F in {}) {
					continue
				}
				B = A.call(C[F], C[F], F);
				if (B == "break") {
					break
				}
			}
		} else {
			for ( var E = 0, D = C.length; E < D; E++) {
				B = A.call(C[E], C[E], E);
				if (B == "break") {
					break
				}
			}
		}
	}
	return C
};
baidu.dom = baidu.dom || {};
baidu.isElement = function(A) {
	if (A === undefined || A === null) {
		return false
	}
	return A && A.nodeName && A.nodeType == 1
};
baidu.isDocument = function(A) {
	if (A === undefined || A === null) {
		return false
	}
	return A && A.nodeType == 9
};
baidu.dom.getDocument = function(A) {
	if (baidu.isElement(A) || baidu.isDocument(A)) {
		return A.nodeType == 9 ? A : A.ownerDocument || A.document
	} else {
		throw new Error(
				"[baidu.dom.getDocument] param must be Element or Document")
	}
};
baidu.isElement = function(A) {
	return A && A.nodeType == 1
};
baidu.dom.getPosition = function(G) {
	G = baidu.G(G);
	if (!baidu.isElement(G)) {
		throw new Error("[baidu.dom.getPosition] param must be Element")
	}
	var D = baidu.dom.getDocument(G);
	var E = baidu.isGecko > 0 && D.getBoxObjectFor
			&& baidu.getCurrentStyle(G, "position") == "absolute"
			&& (G.style.top === "" || G.style.left === "");
	var C = {
		left : 0,
		top : 0
	};
	var A = (baidu.ie && !baidu.isStrict) ? D.body : D.documentElement;
	if (G == A) {
		return C
	}
	var H = null;
	var F;
	if (G.getBoundingClientRect) {
		F = G.getBoundingClientRect();
		C.left = F.left
				+ Math.max(D.documentElement.scrollLeft, D.body.scrollLeft);
		C.top = F.top + Math.max(D.documentElement.scrollTop, D.body.scrollTop);
		C.left -= D.documentElement.clientLeft;
		C.top -= D.documentElement.clientTop;
		if (baidu.ie && !baidu.isStrict) {
			C.left -= 2;
			C.top -= 2
		}
	} else {
		if (D.getBoxObjectFor && !E) {
			F = D.getBoxObjectFor(G);
			var B = D.getBoxObjectFor(A);
			C.left = F.screenX - B.screenX;
			C.top = F.screenY - B.screenY
		} else {
			H = G;
			do {
				C.left += H.offsetLeft;
				C.top += H.offsetTop;
				if (baidu.isWebkit > 0
						&& baidu.getCurrentStyle(H, "position") == "fixed") {
					C.left += D.body.scrollLeft;
					C.top += D.body.scrollTop;
					break
				}
				H = H.offsetParent
			} while (H && H != G);
			if (baidu.opera > 0
					|| (baidu.isWebkit > 0 && baidu.getCurrentStyle(G,
							"position") == "absolute")) {
				C.top -= D.body.offsetTop
			}
			H = G.offsetParent;
			while (H && H != D.body) {
				C.left -= H.scrollLeft;
				if (!baidu.opera || H.tagName != "TR") {
					C.top -= H.scrollTop
				}
				H = H.offsetParent
			}
		}
	}
	return C
};
baidu.encodeHTML = function(A) {
	return A.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;")
			.replace(/"/g, "&quot;")
};
baidu.ie = /msie (\d+\.\d)/i.test(navigator.userAgent) ? parseFloat(RegExp["\x241"])
		: 0;
baidu.setAttr = function(E, B, D) {
	var A = {
		cellpadding : "cellPadding",
		cellspacing : "cellSpacing",
		colspan : "colSpan",
		rowspan : "rowSpan",
		valign : "vAlign",
		height : "height",
		width : "width",
		usemap : "useMap",
		frameborder : "frameBorder"
	};
	if (E = baidu.G(E)) {
		if (baidu.isString(B)) {
			if (B == "style") {
				E.style.cssText = D
			} else {
				if (B == "class") {
					E.className = D
				} else {
					if (B == "for") {
						E.htmlFor = D
					} else {
						if (B in A) {
							E.setAttribute(A[B], D)
						} else {
							E[B] = D
						}
					}
				}
			}
		} else {
			for ( var C in B) {
				if (typeof B[C] != "function") {
					baidu.setAttr(E, C, B[C])
				}
			}
		}
	}
};
baidu.dom.setProperties = function(A, B) {
	A = baidu.G(A);
	baidu.each(B, function(C, D) {
		baidu.setAttr(A, D, C)
	})
};
baidu.dom.create = function(A, B) {
	A = String(A);
	if (A == "") {
		throw new Error("[baidu.dom.create] param tagName can not be empty")
	}
	if (baidu.ie && B && B.name) {
		A = "<" + A + ' name="' + baidu.encodeHTML(B.name) + '">'
	}
	var C = document.createElement(A);
	if (B) {
		baidu.dom.setProperties(C, B)
	}
	return C
};
baidu.suggestion = baidu.suggestion || {};
baidu.suggestion.create = function(A, B) {
	return new baidu.suggestion.Suggestion(A, B)
};
baidu.suggestion.Suggestion = function(C, A) {
	var G = this;
	G.options = {
		onpick : function() {
		},
		onconfirm : function() {
		},
		onhighlight : function() {
		},
		onhide : function() {
		},
		view : null,
		getData : false,
		prepend_html : "",
		append_html : "",
		class_prefix : "tangram_sug_"
	};
	baidu.extend(G.options, A);
	if (!(C = baidu.G(C))) {
		return null
	}
	if (C.id) {
		G.options._inputId = C.id
	} else {
		C.id = G.options._inputId = G.options.class_prefix + "ipt"
				+ new Date().getTime()
	}
	var L = (document.compatMode == "BackCompat");
	function B(N) {
		return baidu.G(N)
	}
	function K(N, O, P) {
		if (baidu.ie) {
			N.attachEvent("on" + O, (function(Q) {
				return function() {
					P.call(Q)
				}
			})(N))
		} else {
			N.addEventListener(O, P, false)
		}
	}
	function M(N) {

		if (baidu.ie) {
			N.returnValue = false
		} else {
			N.preventDefault()
		}
	}
	function J(R, O) {
		var P = document.styleSheets;
		if (!P || P.length <= 0) {
			var N = document.createElement("STYLE");
			N.type = "text/css";
			var Q = document.getElementsByTagName("HEAD")[0];
			Q.appendChild(N)
		}
		P = document.styleSheets;
		P = P[P.length - 1];
		if (baidu.ie) {
			P.addRule(R, O)
		} else {
			P.insertRule(R + " { " + O + " }", P.cssRules.length)
		}
	}
	G.dispose = function() {
		clearInterval(G.circleTimer);
		document.body.removeChild(B(G._sugEle))
	};
	var H = (function() {
		function O(T) {
			var R = this.__MSG_QS__;
			if (!R[T]) {
				R[T] = []
			}
			for ( var S = 1, P = arguments.length, Q; S < P; S++) {
				R[T].push(arguments[S])
			}
		}
		function N(Q) {
			var R = this.__MSG_QS__[Q.type];
			if (R == null) {
				return
			}
			for ( var S = 0, P = R.length; S < P; S++) {
				R[S].receiveMessage(Q)
			}
		}
		return {
			initialize : function(P) {
				P.__MSG_QS__ = {};
				P.addMessageReceiver = O;
				P.dispatchMessage = N;
				return P
			}
		}
	})();
	var D = (function() {
		var S = C;
		var Q;
		G.circleTimer = 0;
		var b = 0;
		var T = "";
		var W = "";
		var Z = "";
		var U;
		var X = false;
		var V = true;
		function N() {
			if (V) {
				D.dispatchMessage({
					type : "start"
				});
				V = false
			}
		}
		function a(c) {
			if (V) {
				D.dispatchMessage({
					type : "start"
				});
				V = false
			}
			c = c || window.event;
			if (c.keyCode == 9 || c.keyCode == 27) {
				D.dispatchMessage({
					type : "hide_div"
				})
			}
			if (c.keyCode == 13) {
				M(c);
				D.dispatchMessage({
					type : "key_enter"
				})
			}
			if (Q.style.display != "none") {
				if (c.keyCode == 38) {
					M(c);
					D.dispatchMessage({
						type : "key_up",
						val : W
					})
				}
				if (c.keyCode == 40) {
					D.dispatchMessage({
						type : "key_down",
						val : W
					})
				}
			} else {
				if (c.keyCode == 38 || c.keyCode == 40) {
					D.dispatchMessage({
						type : "need_data",
						wd : S.value
					})
				}
			}
		}
		function R() {
			if (B(G.options._inputId) == null) {
				G.dispose()
			}
			var c = S.value;
			if (c == T && c != "" && c != Z && c != U) {
				if (b == 0) {
					b = setTimeout(function() {
						D.dispatchMessage({
							type : "need_data",
							wd : c
						})
					}, 100)
				}
			} else {
				clearTimeout(b);
				b = 0;
				if (c == "" && T != "") {
					D.dispatchMessage({
						type : "hide_div"
					})
				}
				T = c;
				if (c != U) {
					W = c
				}
				if (Z != S.value) {
					Z = ""
				}
			}
		}
		function O() {
			G.circleTimer = setInterval(R, 10)
		}
		function Y() {
			if (X) {
				window.event.cancelBubble = true;
				window.event.returnValue = false;
				X = false
			}
		}
		function P(c) {
			S.blur();
			S.setAttribute("autocomplete", c);
			S.focus()
		}
		S.setAttribute("autocomplete", "off");
		K(S, "keydown", a);
		K(S, "mousedown", N);
		K(S, "beforedeactivate", Y);
		return H.initialize({
			receiveMessage : function(c) {
				switch (c.type) {
				case "div_ready":
					Q = c.sdiv;
					Z = S.value;
					O();
					break;
				case "clk_confirm":
					W = T = c.wd;
					G.options.onpick(c.oq, c.rsp, c.wd, c.html);
				case "ent_confirm":
					S.blur();
					G.options.onconfirm(c.oq, c.rsp, c.wd, c.html);
					break;
				case "pick_word":
					U = c.selected;
					break;
				case "mousedown_tr":
					X = true;
					break
				}
			}
		})
	})();
	var I = (function() {
		var P;
		var X = C;
		var Z;
		var e = -1;
		var N;
		var a;
		var d;
		var T;
		function b() {
			var n = Z.rows;
			for ( var m = 0; m < n.length; m++) {
				n[m].className = G.options.class_prefix + "ml"
			}
		}
		function g() {
			if (typeof (Z) != "undefined" && Z != null
					&& P.style.display != "none") {
				var n = Z.rows;
				for ( var m = 0; m < n.length; m++) {
					if (n[m].className == G.options.class_prefix + "mo") {
						return [ m, N[m], a[m] ]
					}
				}
			}
			return [ -1, "" ]
		}
		function R() {
			if (P.style.display == "none") {
				return null
			}
			T.style.display = "none";
			P.style.display = "none";
			G.options.onhide()
		}
		G._hide = R;
		function h() {
			var m = g();
			G.options.onhighlight(X.value, m[0], m[1], m[2])
		}
		function l(m) {
			b();
			Z.rows[m].className = G.options.class_prefix + "mo"
		}
		G._highlight = l;
		function j() {
			var m = g();
			G.options.onpick(X.value, m[0], m[1], m[2])
		}
		function f(n) {
			var m = N && typeof N[n] != "undefined" ? N[n] : n;
			I.dispatchMessage({
				type : "pick_word",
				selected : m
			});
			X.value = m
		}
		G._pick = f;
		function O() {
			b();
			this.className = G.options.class_prefix + "mo";
			h()
		}
		function Y(m) {
			I.dispatchMessage({
				type : "mousedown_tr"
			});
			if (!baidu.ie) {
				m.stopPropagation();
				m.preventDefault();
				return false
			}
		}
		function c(m) {
			var n = m;
			return function() {
				R();
				I.dispatchMessage({
					type : "clk_confirm",
					oq : X.value,
					wd : N[n],
					rsp : n,
					html : a[n]
				})
			}
		}
		function S() {
			var n = [ X.offsetWidth, X.offsetHeight ];
			var m = baidu.dom.getPosition(X);
			return [ (m.top + n[1] - 1), m.left, (n[0]) ]
		}
		function V() {
			var m = typeof G.options.view == "function" ? G.options.view()
					: S();
			P.style.display = T.style.display = "block";
			T.style.top = m[0] + "px";
			T.style.left = m[1] + "px";
			T.style.width = m[2] + "px";
			var r = parseFloat(baidu.getCurrentStyle(P, "marginTop")) || 0;
			var n = parseFloat(baidu.getCurrentStyle(P, "marginLeft")) || 0;
			P.style.top = (m[0] - r) + "px";
			P.style.left = (m[1] - n) + "px";
			if (baidu.ie && L) {
				P.style.width = m[2] + "px"
			} else {
				var o = parseFloat(baidu.getCurrentStyle(P, "borderLeftWidth")) || 0;
				var p = parseFloat(baidu.getCurrentStyle(P, "borderRightWidth")) || 0;
				var q = parseFloat(baidu.getCurrentStyle(P, "marginRight")) || 0;
				P.style.width = (m[2] - o - p - n - q) + "px"
			}
			T.style.height = P.offsetHeight + "px"
		}
		function U() {
			Z = baidu.dom.create("TABLE", {
				cellSpacing : 0,
				cellPadding : 2
			});
			var p = baidu.dom.create("tbody");
			Z.appendChild(p);
			for ( var q = 0, r = a.length; q < r; q++) {
				var o = p.insertRow(-1);
				K(o, "mouseover", O);
				K(o, "mouseout", b);
				K(o, "mousedown", Y);
				K(o, "click", c(q));
				var m = o.insertCell(-1);
				m.innerHTML = a[q]
			}
			var n = baidu.dom.create("div", {
				"class" : G.options.class_prefix + "pre"
			});
			n.innerHTML = G.options.prepend_html;
			var s = baidu.dom.create("div", {
				"class" : G.options.class_prefix + "app"
			});
			s.innerHTML = G.options.append_html;
			P.innerHTML = "";
			P.appendChild(n);
			P.appendChild(Z);
			P.appendChild(s);
			V()
		}
		function Q() {
			var m = g();
			var n = m[0] == -1 ? d : m[1];
			I.dispatchMessage({
				type : "ent_confirm",
				oq : X.value,
				wd : n,
				rsp : m[0],
				html : m[2]
			})
		}
		function W(m) {
			e = g()[0];
			b();
			if (e == 0) {
				f(m);
				e--
			} else {
				if (e == -1) {
					e = N.length
				}
				e--;
				l(e);
				h();
				f(e);
				j()
			}
		}
		function i(m) {
			e = g()[0];
			b();
			if (e == N.length - 1) {
				f(m);
				e = -1
			} else {
				e++;
				l(e);
				h();
				f(e);
				j()
			}
		}
		function k(m) {
			N = [];
			a = [];
			for ( var n = 0, o = m.length; n < o; n++) {
				if (m[n].input != undefined) {
					N[n] = m[n].input;
					a[n] = m[n].selection
				} else {
					a[n] = N[n] = m[n]
				}
			}
		}
		return H.initialize({
			receiveMessage : function(m) {
				switch (m.type) {
				case "div_ready":
					P = m.sdiv;
					T = m.frm;
					break;
				case "give_data":
					d = m.data;
					k(m.word);
					if (N.length != 0) {
						U()
					} else {
						R()
					}
					break;
				case "key_enter":
					Q();
					break;
				case "key_up":
					W(m.val);
					break;
				case "key_down":
					i(m.val);
					break;
				case "clk_confirm":
					f(m.rsp);
				case "hide_div":
				case "mousedown_outside":
				case "window_blur":
				case "ent_confirm":
					R();
					break;
				case "need_resize":
					V();
					break
				}
			}
		})
	})();
	var F = (function() {
		var P = {};
		function N(Q) {
			if (typeof P[Q] == "undefined") {
				G.options.getData && G.options.getData(Q)
			} else {
				F.dispatchMessage({
					type : "give_data",
					word : Q,
					data : P[Q]
				})
			}
		}
		G._giveData = function(Q, R) {
			G.dispatchMessage({
				type : "response_data",
				word : Q,
				data : R
			})
		};
		function O(Q, R) {
			P[Q] = R;
			F.dispatchMessage({
				type : "give_data",
				word : Q,
				data : R
			})
		}
		return H.initialize({
			receiveMessage : function(Q) {
				switch (Q.type) {
				case "response_data":
					O(Q.data);
					break;
				case "need_data":
					N(Q.wd);
					break
				}
			}
		})
	})();
	var E = (function() {
		var Q = C;
		var N;
		var U;
		function P() {
			if (N.offsetWidth != 0 && Q.offsetWidth != N.offsetWidth) {
				E.dispatchMessage({
					type : "need_resize"
				})
			}
		}
		function R() {
			N = baidu.dom.create("DIV", {
				id : G.options.class_prefix + new Date().getTime(),
				"class" : G.options.class_prefix + "wpr"
			});
			N.style.display = "none";
			document.body.appendChild(N);
			U = baidu.dom.create("IFRAME");
			baidu.addClass(U, G.options.class_prefix + "sd");
			U.style.display = "none";
			U.style.position = "absolute";
			U.style.borderWidth = "0px";
			N.parentNode.insertBefore(U, N)
		}
		function T(W) {
			W = W || window.event;
			var V = W.target || W.srcElement;
			if (V == Q) {
				return
			}
			while (V = V.parentNode) {
				if (V == N) {
					return
				}
			}
			E.dispatchMessage({
				type : "mousedown_outside"
			})
		}
		function O() {
			E.dispatchMessage({
				type : "window_blur"
			})
		}
		function S() {
			E.dispatchMessage({
				type : "div_ready",
				sdiv : N,
				frm : U
			});
			setInterval(P, 100);
			K(document, "mousedown", T);
			K(window, "blur", O);
			G._sugEle = N
		}
		return H.initialize({
			receiveMessage : function(V) {
				switch (V.type) {
				case "start":
					S();
					break;
				case "init":
					R();
					break
				}
			}
		})
	})();
	H.initialize(G);
	D.addMessageReceiver("need_data", F);
	D.addMessageReceiver("close_div", I);
	D.addMessageReceiver("key_enter", I);
	D.addMessageReceiver("key_up", I);
	D.addMessageReceiver("key_down", I);
	D.addMessageReceiver("hide_div", I);
	D.addMessageReceiver("start", E);
	F.addMessageReceiver("give_data", I);
	G.addMessageReceiver("response_data", F);
	G.addMessageReceiver("init", E);
	I.addMessageReceiver("clk_confirm", D, I);
	I.addMessageReceiver("ent_confirm", D, I);
	I.addMessageReceiver("pick_word", D);
	I.addMessageReceiver("mousedown_tr", D);
	E.addMessageReceiver("mousedown_outside", I);
	E.addMessageReceiver("need_resize", I);
	E.addMessageReceiver("div_ready", D, I);
	E.addMessageReceiver("window_blur", I);
	G.dispatchMessage({
		type : "init"
	});
	return {
		getElement : function() {
			return G._sugEle
		},
		getData : G.options.getData,
		pick : G._pick,
		hide : G._hide,
		giveData : G._giveData,
		highlight : G._highlight
	}
};