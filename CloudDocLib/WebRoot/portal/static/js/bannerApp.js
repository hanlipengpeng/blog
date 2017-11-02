(function() {
	var baidu = baidu || {
		version : "1-3-1"
	};
	baidu.guid = "$BAIDU$";
	window[baidu.guid] = window[baidu.guid] || {};
	baidu.ajax = baidu.ajax || {};
	baidu.ajax.request = function(D, O) {
		function J() {
			if (N.readyState == 4) {
				try {
					var Q = N.status
				} catch (P) {
					E("failure");
					return
				}
				E(Q);
				if ((Q >= 200 && Q < 300) || Q == 304 || Q == 1223) {
					E("success")
				} else {
					E("failure")
				}
				window.setTimeout(function() {
					N.onreadystatechange = new Function();
					if (G) {
						N = null
					}
				}, 0)
			}
		}
		function C() {
			if (window.ActiveXObject) {
				try {
					return new ActiveXObject("Msxml2.XMLHTTP")
				} catch (P) {
					try {
						return new ActiveXObject("Microsoft.XMLHTTP")
					} catch (P) {
					}
				}
			}
			if (window.XMLHttpRequest) {
				return new XMLHttpRequest()
			}
		}
		function E(Q) {
			Q = "on" + Q;
			var P = B[Q], R = baidu.ajax[Q];
			if (P) {
				if (Q != "onsuccess") {
					P(N)
				} else {
					P(N, N.responseText)
				}
			} else {
				if (R) {
					if (Q == "onsuccess") {
						return
					}
					R(N)
				}
			}
		}
		O = O || {};
		var I = O.data || "", G = !(O.async === false), H = O.username || "", M = O.password
				|| "", A = (O.method || "GET").toUpperCase(), F = O.headers
				|| {}, B = {}, L, N;
		for (L in O) {
			B[L] = O[L]
		}
		F["X-Request-By"] = "baidu.ajax";
		try {
			N = C();
			if (A == "GET") {
				D += (D.indexOf("?") >= 0 ? "&" : "?");
				if (I) {
					D += I + "&";
					I = null
				}
				if (O.noCache) {
					D += "b" + (new Date()).getTime() + "=1"
				}
			}
			if (H) {
				N.open(A, D, G, H, M)
			} else {
				N.open(A, D, G)
			}
			if (G) {
				N.onreadystatechange = J
			}
			if (A == "POST") {
				N.setRequestHeader("Content-Type",
						"application/x-www-form-urlencoded")
			}
			for (L in F) {
				if (F.hasOwnProperty(L)) {
					N.setRequestHeader(L, F[L])
				}
			}
			E("beforerequest");
			N.send(I);
			if (!G) {
				J()
			}
		} catch (K) {
			E("failure")
		}
		return N
	};
	baidu.ajax.get = function(B, A) {
		return baidu.ajax.request(B, {
			onsuccess : A
		})
	};
	baidu.ajax.post = function(B, C, A) {
		return baidu.ajax.request(B, {
			onsuccess : A,
			method : "POST",
			data : C
		})
	};
	baidu.array = baidu.array || {};
	baidu.array.each = function(F, D) {
		var C, E, B, A = F.length;
		if ("function" == typeof D) {
			for (B = 0; B < A; B++) {
				E = F[B];
				C = D.call(F, E, B);
				if (C === false) {
					break
				}
			}
		}
		return F
	};
	baidu.each = baidu.array.each;
	baidu.array.filter = function(G, E) {
		var C = [], B = 0, A = G.length, F, D;
		if ("function" == typeof E) {
			for (D = 0; D < A; D++) {
				F = G[D];
				if (true === E.call(G, F, D)) {
					C[B++] = F
				}
			}
		}
		return C
	};
	baidu.array.remove = function(C, D) {
		var A = C.length, B = D;
		if ("function" != typeof D) {
			B = function(E) {
				return D === E
			}
		}
		while (A--) {
			if (true === B.call(C, C[A], A)) {
				C.splice(A, 1)
			}
		}
	};
	baidu.array.removeAt = function(B, A) {
		return B.splice(A, 1)[0]
	};
	baidu.browser = baidu.browser || {};
	if (/msie (\d+\.\d)/i.test(navigator.userAgent)) {
		baidu.ie = baidu.browser.ie = document.documentMode
				|| parseFloat(RegExp["\x241"])
	}
	if (/firefox\/(\d+\.\d)/i.test(navigator.userAgent)) {
		baidu.browser.firefox = parseFloat(RegExp["\x241"])
	}
	if (/opera\/(\d+\.\d)/i.test(navigator.userAgent)) {
		baidu.browser.opera = parseFloat(RegExp["\x241"])
	}
	if ((/(\d+\.\d)(\.\d)?\s+safari/i.test(navigator.userAgent) && !/chrome/i
			.test(navigator.userAgent))) {
		baidu.browser.safari = parseFloat(RegExp["\x241"])
	}
	baidu.browser.isGecko = /gecko/i.test(navigator.userAgent)
			&& !/like gecko/i.test(navigator.userAgent);
	baidu.browser.isWebkit = /webkit/i.test(navigator.userAgent);
	baidu.browser.isStrict = document.compatMode == "CSS1Compat";
	baidu.cookie = baidu.cookie || {};
	baidu.cookie._isValidKey = function(A) {
		return (new RegExp(
				'^[^\\x00-\\x20\\x7f\\(\\)<>@,;:\\\\\\"\\[\\]\\?=\\{\\}\\/\\u0080-\\uffff]+\x24'))
				.test(A)
	};
	baidu.cookie.getRaw = function(B) {
		if (baidu.cookie._isValidKey(B)) {
			var C = new RegExp("(^| )" + B + "=([^;]*)(;|\x24)"), A = C
					.exec(document.cookie);
			if (A) {
				return A[2] || null
			}
		}
		return null
	};
	baidu.cookie.setRaw = function(C, D, B) {
		if (!baidu.cookie._isValidKey(C)) {
			return
		}
		B = B || {};
		var A = B.expires;
		if ("number" == typeof B.expires) {
			A = new Date();
			A.setTime(A.getTime() + B.expires)
		}
		document.cookie = C + "=" + D + (B.path ? "; path=" + B.path : "")
				+ (A ? "; expires=" + A.toGMTString() : "")
				+ (B.domain ? "; domain=" + B.domain : "")
				+ (B.secure ? "; secure" : "")
	};
	baidu.cookie.get = function(A) {
		var B = baidu.cookie.getRaw(A);
		if ("string" == typeof B) {
			B = decodeURIComponent(B);
			return B
		}
		return null
	};
	baidu.cookie.set = function(B, C, A) {
		baidu.cookie.setRaw(B, encodeURIComponent(C), A)
	};
	baidu.cookie.remove = function(B, A) {
		A = A || {};
		A.expires = new Date(0);
		baidu.cookie.setRaw(B, "", A)
	};
	baidu.sio = baidu.sio || {};
	baidu.sio._removeScriptTag = function(B) {
		if (B.clearAttributes) {
			B.clearAttributes()
		} else {
			for ( var A in B) {
				if (B.hasOwnProperty(A)) {
					delete B[A]
				}
			}
		}
		if (B && B.parentNode) {
			B.parentNode.removeChild(B)
		}
		B = null
	};
	baidu.sio.callByServer = function(A, H, I) {
		I = I || {};
		var E = document.createElement("SCRIPT"), D = "bd__cbs__", C = typeof H, G, F, B = I.charset;
		if ("string" == C) {
			G = H
		} else {
			if ("function" == C) {
				while (1) {
					G = D + Math.floor(Math.random() * 2147483648).toString(36);
					if (!window[G]) {
						window[G] = function() {
							try {
								H.apply(window, arguments)
							} finally {
								baidu.sio._removeScriptTag(E);
								window[G] = null
							}
						};
						break
					}
				}
			}
		}
		if ("string" == typeof G) {
			A = A.replace(/(\?|&)callback=[^&]*/, "\x241callback=" + G);
			if (A.search(/(\?|&)callback=/) < 0) {
				A += (A.indexOf("?") < 0 ? "?" : "&");
				A += "callback=" + G
			}
		}
		E.setAttribute("type", "text/javascript");
		B && E.setAttribute("charset", B);
		E.setAttribute("src", A);
		document.getElementsByTagName("head")[0].appendChild(E)
	};
	baidu.dom = baidu.dom || {};
	baidu.dom.g = function(A) {
		if ("string" == typeof A || A instanceof String) {
			return document.getElementById(A)
		} else {
			if (A && A.nodeName && (A.nodeType == 1 || A.nodeType == 9)) {
				return A
			}
		}
		return null
	};
	baidu.g = baidu.G = baidu.dom.g;
	baidu.string = baidu.string || {};
	(function() {
		var A = new RegExp(
				"(^[\\s\\t\\xa0\\u3000]+)|([\\u3000\\xa0\\s\\t]+\x24)", "g");
		baidu.string.trim = function(B) {
			return String(B).replace(A, "")
		}
	})();
	baidu.trim = baidu.string.trim;
	baidu.string.escapeReg = function(A) {
		return String(A).replace(
				new RegExp("([.*+?^=!:\x24{}()|[\\]/\\\\])", "g"), "\\\x241")
	};
	baidu.dom.q = function(H, E, B) {
		var I = [], D = baidu.string.trim, G, F, A, C;
		if (!(H = D(H))) {
			return null
		}
		if ("undefined" == typeof E) {
			E = document
		} else {
			E = baidu.dom.g(E);
			if (!E) {
				return I
			}
		}
		B && (B = D(B).toUpperCase());
		if (E.getElementsByClassName) {
			A = E.getElementsByClassName(H);
			G = A.length;
			for (F = 0; F < G; F++) {
				C = A[F];
				if (B && C.tagName != B) {
					continue
				}
				I[I.length] = C
			}
		} else {
			H = new RegExp("(^|\\s)" + baidu.string.escapeReg(H) + "(\\s|\x24)");
			A = B ? E.getElementsByTagName(B) : (E.all || E
					.getElementsByTagName("*"));
			G = A.length;
			for (F = 0; F < G; F++) {
				C = A[F];
				H.test(C.className) && (I[I.length] = C)

			}
		}
		return I
	};
	baidu.q = baidu.Q = baidu.dom.q;
	baidu.dom.insertHTML = function(E, A, D) {
		E = baidu.dom.g(E);
		if (E.insertAdjacentHTML) {
			E.insertAdjacentHTML(A, D)
		} else {
			var B = E.ownerDocument.createRange();
			B.setStartBefore(E);
			var C = B.createContextualFragment(D), G = E.parentNode, F;
			switch (A.toUpperCase()) {
			case "BEFOREBEGIN":
				G.insertBefore(C, E);
				break;
			case "AFTERBEGIN":
				E.insertBefore(C, E.firstChild);
				break;
			case "BEFOREEND":
				E.appendChild(C);
				break;
			case "AFTEREND":
				(F = E.nextSibling) ? G.insertBefore(C, F) : G.appendChild(C)
			}
		}
		return E
	};
	baidu.insertHTML = baidu.dom.insertHTML;
	baidu.dom._NAME_ATTRS = (function() {
		var A = {
			cellpadding : "cellPadding",
			cellspacing : "cellSpacing",
			colspan : "colSpan",
			rowspan : "rowSpan",
			valign : "vAlign",
			usemap : "useMap",
			frameborder : "frameBorder"
		};
		if (baidu.browser.ie < 8) {
			A["for"] = "htmlFor";
			A["class"] = "className"
		} else {
			A.htmlFor = "for";
			A.className = "class"
		}
		return A
	})();
	baidu.dom.getAttr = function(B, A) {
		B = baidu.dom.g(B);
		if ("style" == A) {
			return B.style.cssText
		}
		A = baidu.dom._NAME_ATTRS[A] || A;
		return B.getAttribute(A)
	};
	baidu.getAttr = baidu.dom.getAttr;
	baidu.dom.setAttr = function(B, A, C) {
		B = baidu.dom.g(B);
		if ("style" == A) {
			B.style.cssText = C
		} else {
			A = baidu.dom._NAME_ATTRS[A] || A;
			B.setAttribute(A, C)
		}
		return B
	};
	baidu.setAttr = baidu.dom.setAttr;
	baidu.dom.setAttrs = function(C, A) {
		C = baidu.dom.g(C);
		for ( var B in A) {
			baidu.dom.setAttr(C, B, A[B])
		}
		return C
	};
	baidu.setAttrs = baidu.dom.setAttrs;
	baidu.dom._styleFixer = baidu.dom._styleFixer || {};
	baidu.dom._styleFilter = baidu.dom._styleFilter || [];
	baidu.dom._styleFilter.filter = function(B, E, F) {
		for ( var A = 0, D = baidu.dom._styleFilter, C; C = D[A]; A++) {
			if (C = C[F]) {
				E = C(B, E)
			}
		}
		return E
	};
	baidu.string.toCamelCase = function(A) {
		if (A.indexOf("-") < 0 && A.indexOf("_") < 0) {
			return A
		}
		return A.replace(/[-_][^-_]/g, function(B) {
			return B.charAt(1).toUpperCase()
		})
	};
	baidu.dom.getStyle = function(C, B) {
		var F = baidu.dom;
		C = F.g(C);
		B = baidu.string.toCamelCase(B);
		var E = C.style[B];
		if (!E) {
			var A = F._styleFixer[B], D = C.currentStyle
					|| (baidu.browser.ie ? C.style : getComputedStyle(C, null));
			if ("string" == typeof A) {
				E = D[A]
			} else {
				if (A && A.get) {
					E = A.get(C, D)
				} else {
					E = D[B]
				}
			}
		}
		if (A = F._styleFilter) {
			E = A.filter(B, E, "get")
		}
		return E
	};
	baidu.getStyle = baidu.dom.getStyle;
	baidu.dom.setStyle = function(C, B, D) {
		var E = baidu.dom, A;
		C = E.g(C);
		B = baidu.string.toCamelCase(B);
		if (A = E._styleFilter) {
			D = A.filter(B, D, "set")
		}
		A = E._styleFixer[B];
		(A && A.set) ? A.set(C, D) : (C.style[A || B] = D);
		return C
	};
	baidu.setStyle = baidu.dom.setStyle;
	baidu.dom.setStyles = function(B, C) {
		B = baidu.dom.g(B);
		for ( var A in C) {
			baidu.dom.setStyle(B, A, C[A])
		}
		return B
	};
	baidu.setStyles = baidu.dom.setStyles;
	baidu.dom.addClass = function(D, E) {
		D = baidu.dom.g(D);
		var B = baidu.string.trim, C = B(E).split(/\s+/), A = C.length;
		E = D.className.split(/\s+/).join(" ");
		while (A--) {
			(new RegExp("(^| )" + C[A] + "( |\x24)")).test(E) && C.splice(A, 1)
		}
		D.className = B(E + " " + C.join(" "));
		return D
	};
	baidu.addClass = baidu.dom.addClass;
	baidu.dom.removeClass = function(B, C) {
		B = baidu.dom.g(B);
		var A = baidu.string.trim;
		B.className = A(B.className.split(/\s+/).join("  ").replace(
				new RegExp(
						"(^| )(" + A(C).split(/\s+/).join("|") + ")( |\x24)",
						"g"), " ").replace(/\s+/g, " "));
		return B
	};
	baidu.removeClass = baidu.dom.removeClass;
	baidu.dom.show = function(A) {
		A = baidu.dom.g(A);
		A.style.display = "";
		return A
	};
	baidu.show = baidu.dom.show;
	baidu.dom.hide = function(A) {
		A = baidu.dom.g(A);
		A.style.display = "none";
		return A
	};
	baidu.hide = baidu.dom.hide;
	baidu.dom.toggle = function(A) {
		A = baidu.dom.g(A);
		A.style.display = A.style.display == "none" ? "" : "none";
		return A
	};
	baidu.dom.remove = function(A) {
		A = baidu.dom.g(A);
		(tmpEl = A.parentNode) && tmpEl.removeChild(A)
	};
	baidu.dom.getDocument = function(A) {
		A = baidu.dom.g(A);
		return A.nodeType == 9 ? A : A.ownerDocument || A.document
	};
	baidu.dom.getPosition = function(C) {
		var K = baidu.dom.getDocument(C), F = baidu.browser, H = baidu.dom.getStyle;
		C = baidu.dom.g(C);
		var E = F.isGecko > 0 && K.getBoxObjectFor
				&& H(C, "position") == "absolute"
				&& (C.style.top === "" || C.style.left === "");
		var I = {
			left : 0,
			top : 0
		};
		var G = (F.ie && !F.isStrict) ? K.body : K.documentElement;
		if (C == G) {
			return I
		}
		var L = null;
		var D, J, B, M;
		if (C.getBoundingClientRect) {
			D = C.getBoundingClientRect();
			I.left = Math.floor(D.left)
					+ Math.max(K.documentElement.scrollLeft, K.body.scrollLeft);
			I.top = Math.floor(D.top)
					+ Math.max(K.documentElement.scrollTop, K.body.scrollTop);
			I.left -= K.documentElement.clientLeft;
			I.top -= K.documentElement.clientTop;
			J = K.body;
			B = parseInt(H(J, "border-left-width"));
			M = parseInt(H(J, "border-top-width"));
			if (F.ie && !F.isStrict) {
				I.left -= isNaN(B) ? 2 : B;
				I.top -= isNaN(M) ? 2 : M
			}
		} else {
			if (K.getBoxObjectFor && !E) {
				D = K.getBoxObjectFor(C);
				var A = K.getBoxObjectFor(G);
				I.left = D.screenX - A.screenX;
				I.top = D.screenY - A.screenY
			} else {
				L = C;
				do {
					I.left += L.offsetLeft;
					I.top += L.offsetTop;
					if (F.isWebkit > 0 && H(L, "position") == "fixed") {
						I.left += K.body.scrollLeft;
						I.top += K.body.scrollTop;
						break
					}
					L = L.offsetParent
				} while (L && L != C);
				if (F.opera > 0
						|| (F.isWebkit > 0 && H(C, "position") == "absolute")) {
					I.top -= K.body.offsetTop
				}
				L = C.offsetParent;
				while (L && L != K.body) {
					I.left -= L.scrollLeft;
					if (!b.opera || L.tagName != "TR") {
						I.top -= L.scrollTop
					}
					L = L.offsetParent
				}
			}
		}
		return I
	};
	baidu.dom.children = function(B) {
		B = baidu.dom.g(B);
		for ( var A = [], C = B.firstChild; C; C = C.nextSibling) {
			if (C.nodeType == 1) {
				A.push(C)
			}
		}
		return A
	};
	baidu.dom.ready = function() {
		var C = false, E = false, D = [];
		function A() {
			if (!C) {
				C = true;
				for ( var G = 0, F = D.length; G < F; G++) {
					D[G]()
				}
			}
		}
		function B() {
			if (E) {
				return
			}
			E = true;
			var I = document, G = window, F = baidu.browser.opera;
			if (I.addEventListener && !F) {
				I.addEventListener("DOMContentLoaded", F ? function() {
					if (C) {
						return
					}
					for ( var J = 0; J < I.styleSheets.length; J++) {
						if (I.styleSheets[J].disabled) {
							setTimeout(arguments.callee, 0);
							return
						}
					}
					A()
				} : A, false)
			} else {
				if (baidu.browser.ie && G == top) {
					(function() {
						if (C) {
							return
						}
						try {
							I.documentElement.doScroll("left")
						} catch (J) {
							setTimeout(arguments.callee, 0);
							return
						}
						A()
					})()
				} else {
					if (baidu.browser.safari) {
						var H;
						(function() {
							if (C) {
								return
							}
							if (I.readyState != "loaded"
									&& I.readyState != "complete") {
								setTimeout(arguments.callee, 0);
								return
							}
							if (H === undefined) {
								H = 0;
								var M = I.getElementsByTagName("style");
								var K = I.getElementsByTagName("link");
								if (M) {
									H += M.length
								}
								if (K) {
									for ( var L = 0, J = K.length; L < J; L++) {
										if (K[L].getAttribute("rel") == "stylesheet") {
											H++
										}
									}
								}
							}
							if (I.styleSheets.length != H) {
								setTimeout(arguments.callee, 0);
								return
							}
							A()
						})()
					}
				}
			}
			G.attachEvent ? G.attachEvent("onload", A) : G.addEventListener(
					"load", A, false)
		}
		return function(F) {
			B();
			C ? F() : (D[D.length] = F)
		}
	}();
	baidu.dom._g = function(A) {
		if ("string" == typeof A || A instanceof String) {
			return document.getElementById(A)
		}
		return A
	};
	baidu._g = baidu.dom._g;
	baidu.dom.insertBefore = function(D, C) {
		var B, A;
		B = baidu.dom._g;
		D = B(D);
		C = B(C);
		A = C.parentNode;
		if (A) {
			A.insertBefore(D, C)
		}
		return D
	};
	baidu.dom.insertAfter = function(D, C) {
		var B, A;
		B = baidu.dom._g;
		D = B(D);
		C = B(C);
		A = C.parentNode;
		if (A) {
			A.insertBefore(D, C.nextSibling)
		}
		return D
	};
	baidu.dom._styleFilter[baidu.dom._styleFilter.length] = {
		get : function(C, D) {
			if (/color/i.test(C) && D.indexOf("rgb(") != -1) {
				var E = D.split(",");
				D = "#";
				for ( var B = 0, A; A = E[B]; B++) {
					A = parseInt(A.replace(/[^\d]/gi, ""), 10).toString(16);
					D += A.length == 1 ? "0" + A : A
				}
				D = D.toUpperCase()
			}
			return D
		}
	};
	baidu.dom._styleFilter[baidu.dom._styleFilter.length] = {
		set : function(A, B) {
			if (B.constructor == Number
					&& !/zIndex|fontWeight|opacity|zoom|lineHeight/i.test(A)) {
				B = B + "px"
			}
			return B
		}
	};
	baidu.dom._styleFixer.display = baidu.browser.ie && baidu.browser.ie < 8 ? {
		set : function(A, B) {
			A = A.style;
			if (B == "inline-block") {
				A.display = "inline";
				A.zoom = 1
			} else {
				A.display = B
			}
		}
	}
			: baidu.browser.firefox && baidu.browser.firefox < 3 ? {
				set : function(A, B) {
					A.style.display = B == "inline-block" ? "-moz-inline-box"
							: B
				}
			} : null;
	baidu.dom._styleFixer["float"] = baidu.browser.ie ? "styleFloat"
			: "cssFloat";
	baidu.dom._styleFixer.opacity = baidu.browser.ie ? {
		get : function(A) {
			var B = A.style.filter;
			B && B.indexOf("opacity=") >= 0 ? (parseFloat(B
					.match(/opacity=([^)]*)/)[1]) / 100)
					+ "" : "1"
		},
		set : function(A, C) {
			var B = A.style;
			B.filter = (B.filter || "").replace(/alpha\([^\)]*\)/gi, "")
					+ (C == 1 ? "" : "alpha(opacity=" + C * 100 + ")");
			B.zoom = 1
		}
	} : null;
	baidu.dom._styleFixer.textOverflow = (function() {
		var B = {};
		function A(E) {
			var F = E.length;
			if (F > 0) {
				F = E[F - 1];
				E.length--
			} else {
				F = null
			}
			return F
		}
		function C(E, F) {
			E[baidu.browser.firefox ? "textContent" : "innerText"] = F
		}
		function D(M, H, Q) {
			var J = baidu.browser.ie ? M.currentStyle || M.style
					: getComputedStyle(M, null), P = J.fontWeight, O = "font-family:"
					+ J.fontFamily
					+ ";font-size:"
					+ J.fontSize
					+ ";word-spacing:"
					+ J.wordSpacing
					+ ";font-weight:"
					+ ((parseInt(P) || 0) == 401 ? 700 : P)
					+ ";font-style:"
					+ J.fontStyle + ";font-variant:" + J.fontVariant, E = B[O];
			if (!E) {
				J = M.appendChild(document.createElement("div"));
				J.style.cssText = "float:left;" + O;
				E = B[O] = [];
				for (L = 0; L < 256; L++) {
					L == 32 ? (J.innerHTML = "&nbsp;") : C(J, String
							.fromCharCode(L));
					E[L] = J.offsetWidth
				}
				C(J, "一");
				E[256] = J.offsetWidth;
				C(J, "一一");
				E[257] = J.offsetWidth - E[256] * 2;
				E[258] = E[".".charCodeAt(0)] * 3 + E[257] * 3;
				M.removeChild(J)
			}
			for ( var K = M.firstChild, N = E[256], G = E[257], F = E[258], S = [], Q = Q ? F
					: 0; K; K = K.nextSibling) {
				if (H < Q) {
					M.removeChild(K)
				} else {
					if (K.nodeType == 3) {
						for ( var L = 0, R = K.nodeValue, I = R.length; L < I; L++) {
							J = R.charCodeAt(L);
							S[S.length] = [ H, K, L ];
							H -= (L ? G : 0) + (J < 256 ? E[J] : N);
							if (H < Q) {
								break
							}
						}
					} else {
						J = K.tagName;
						if (J == "IMG" || J == "TABLE") {
							J = K;
							K = K.previousSibling;
							M.removeChild(J)
						} else {
							S[S.length] = [ H, K ];
							H -= K.offsetWidth
						}
					}
				}
			}
			if (H < Q) {
				while (J = A(S)) {
					H = J[0];
					K = J[1];
					J = J[2];
					if (K.nodeType == 3) {
						if (H >= F) {
							K.nodeValue = K.nodeValue.substring(0, J) + "...";
							return true
						} else {
							if (!J) {
								M.removeChild(K)
							}
						}
					} else {
						if (D(K, H, true)) {
							return true
						} else {
							M.removeChild(K)
						}
					}
				}
				M.innerHTML = ""
			}
		}
		return {
			get : function(F, G) {
				var E = baidu.browser;
				return (E.opera ? G.OTextOverflow
						: E.firefox ? F._baiduOverflow : G.textOverflow)
						|| "clip"
			},
			set : function(F, H) {
				var E = baidu.browser;
				if (F.tagName == "TD" || F.tagName == "TH" || E.firefox) {
					F._baiduHTML && (F.innerHTML = F._baiduHTML);
					if (H == "ellipsis") {
						F._baiduHTML = F.innerHTML;
						var I = document.createElement("div"), G = F
								.appendChild(I).offsetWidth;
						F.removeChild(I);
						D(F, G)
					} else {
						F._baiduHTML = ""
					}
				}
				I = F.style;
				E.opera ? (I.OTextOverflow = H)
						: E.firefox ? (F._baiduOverflow = H)
								: (I.textOverflow = H)
			}
		}
	})();
	baidu.event = baidu.event || {};
	baidu.event._unload = function() {
		var C = baidu.event._listeners, A = C.length, B = !!window.removeEventListener, E, D;
		while (A--) {
			E = C[A];
			if (E[1] == "unload") {
				continue
			}
			D = E[0];
			if (D.removeEventListener) {
				D.removeEventListener(E[1], E[3], false)
			} else {
				if (D.detachEvent) {
					D.detachEvent("on" + E[1], E[3])
				}
			}
		}
		if (B) {
			window.removeEventListener("unload", baidu.event._unload, false)
		} else {
			window.detachEvent("onunload", baidu.event._unload)
		}
	};
	if (window.attachEvent) {
		window.attachEvent("onunload", baidu.event._unload)
	} else {
		window.addEventListener("unload", baidu.event._unload, false)
	}
	baidu.event._listeners = baidu.event._listeners || [];
	baidu.event.on = function(B, D, E) {
		D = D.replace(/^on/i, "");
		B = baidu.dom._g(B);
		var C = function(F) {
			E.call(B, F)
		}, A = baidu.event._listeners;
		A[A.length] = [ B, D, E, C ];
		if (B.addEventListener) {
			B.addEventListener(D, C, false)
		} else {
			if (B.attachEvent) {
				B.attachEvent("on" + D, C)
			}
		}
		return B
	};
	baidu.on = baidu.event.on;
	baidu.event.un = function(C, D, F) {
		C = baidu.dom._g(C);
		D = D.replace(/^on/i, "");
		var B = baidu.event._listeners, A = B.length, G = !F, E;
		while (A--) {
			E = B[A];
			if (E[1] === D && E[0] === C && (G || E[2] === F)) {
				if (C.removeEventListener) {
					C.removeEventListener(D, E[3], false)
				} else {
					if (C.detachEvent) {
						C.detachEvent("on" + D, E[3])
					}
				}
				B.splice(A, 1)
			}
		}
		return C
	};
	baidu.un = baidu.event.un;
	baidu.event.EventArg = function(C, E) {
		E = E || window;
		C = C || E.event;
		var D = E.document;
		this.target = C.target || C.srcElement;
		this.keyCode = C.which || C.keyCode;
		for ( var A in C) {
			var B = C[A];
			if ("function" != typeof B) {
				this[A] = B
			}
		}
		if (!this.pageX && this.pageX !== 0) {
			this.pageX = (C.clientX || 0)
					+ (D.documentElement.scrollLeft || D.body.scrollLeft);
			this.pageY = (C.clientY || 0)
					+ (D.documentElement.scrollTop || D.body.scrollTop)
		}
		this._event = C
	};
	baidu.event.EventArg.prototype.preventDefault = function() {
		if (this._event.preventDefault) {
			this._event.preventDefault()
		} else {
			this._event.returnValue = false
		}
		return this
	};
	baidu.event.EventArg.prototype.stopPropagation = function() {
		if (this._event.stopPropagation) {
			this._event.stopPropagation()
		} else {
			this._event.cancelBubble = true
		}
		return this
	};
	baidu.event.EventArg.prototype.stop = function() {
		return this.stopPropagation().preventDefault()
	};
	baidu.event.get = function(A, B) {
		return new baidu.event.EventArg(A, B)
	};
	baidu.json = baidu.json || {};
	baidu.json.stringify = (function() {
		var B = {
			"\b" : "\\b",
			"\t" : "\\t",
			"\n" : "\\n",
			"\f" : "\\f",
			"\r" : "\\r",
			'"' : '\\"',
			"\\" : "\\\\"
		};
		function A(F) {
			if (/["\\\x00-\x1f]/.test(F)) {
				F = F.replace(/["\\\x00-\x1f]/g, function(G) {
					var H = B[G];
					if (H) {
						return H
					}
					H = G.charCodeAt();
					return "\\u00" + Math.floor(H / 16).toString(16)
							+ (H % 16).toString(16)
				})
			}
			return '"' + F + '"'
		}
		function D(K) {
			var G = [ "[" ], H = K.length, F, I, J;
			for (I = 0; I < H; I++) {
				J = K[I];
				switch (typeof J) {
				case "undefined":
				case "function":
				case "unknown":
					break;
				default:
					if (F) {
						G.push(",")
					}
					G.push(baidu.json.stringify(J));
					F = 1
				}
			}
			G.push("]");
			return G.join("")
		}
		function C(F) {
			return F < 10 ? "0" + F : F
		}
		function E(F) {
			return '"' + F.getFullYear() + "-" + C(F.getMonth() + 1) + "-"
					+ C(F.getDate()) + "T" + C(F.getHours()) + ":"
					+ C(F.getMinutes()) + ":" + C(F.getSeconds()) + '"'
		}
		return function(J) {
			switch (typeof J) {
			case "undefined":
				return "undefined";
			case "number":
				return isFinite(J) ? String(J) : "null";
			case "string":
				return A(J);
			case "boolean":
				return String(J);
			default:
				if (J === null) {
					return "null"
				} else {
					if (J instanceof Array) {
						return D(J)
					} else {
						if (J instanceof Date) {
							return E(J)
						} else {
							var G = [ "{" ], I = baidu.json.stringify, F, H;
							for (key in J) {
								if (J.hasOwnProperty(key)) {
									H = J[key];
									switch (typeof H) {
									case "undefined":
									case "unknown":
									case "function":
										break;
									default:
										if (F) {
											G.push(",")
										}
										F = 1;
										G.push(I(key) + ":" + I(H))
									}
								}
							}
							G.push("}");
							return G.join("")
						}
					}
				}
			}
		}
	})();
	baidu.json.parse = function(A) {
		if (!/^[\],:{}\s]*$/
				.test(A
						.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g, "@")
						.replace(
								/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g,
								"]").replace(/(?:^|:|,)(?:\s*\[)+/g, ""))) {
			return null
		}
		return window.JSON && window.JSON.parse ? window.JSON.parse(A)
				: (new Function("return " + A))()
	};
	baidu.lang = baidu.lang || {};
	baidu.lang.isString = function(A) {
		return "[object String]" == Object.prototype.toString.call(A)
	};
	baidu.isString = baidu.lang.isString;
	baidu.lang.isObject = function(A) {
		return "function" == typeof A || !!(A && "object" == typeof A)
	};
	baidu.isObject = baidu.lang.isObject;
	baidu.lang.isNumber = function(A) {
		return "[object Number]" == Object.prototype.toString.call(A)
	};
	baidu.lang.isArray = function(A) {
		return "[object Array]" == Object.prototype.toString.call(A)
	};
	baidu.lang.isElement = function(A) {
		return !!(A && A.nodeName && A.nodeType == 1)
	};
	baidu.lang.guid = function() {
		return "TANGRAM__" + (window[baidu.guid]._counter++).toString(36)
	};
	window[baidu.guid]._counter = window[baidu.guid]._counter || 1;
	window[baidu.guid]._instances = window[baidu.guid]._instances || {};
	baidu.lang.Class = function(A) {
		this.guid = A || baidu.lang.guid();
		window[baidu.guid]._instances[this.guid] = this
	};
	window[baidu.guid]._instances = window[baidu.guid]._instances || {};
	baidu.lang.Class.prototype.dispose = function() {
		delete window[baidu.guid]._instances[this.guid];
		for ( var A in this) {

			if (typeof this[A] != "function") {
				delete this[A]
			}
		}
		this.disposed = true
	};
	baidu.lang.Class.prototype.toString = function() {
		return "[object " + (this._className || "Object") + "]"
	};
	baidu.lang.Event = function(A, B) {
		this.type = A;
		this.returnValue = true;
		this.target = B || null;
		this.currentTarget = null
	};
	baidu.lang.Class.prototype.addEventListener = function(D, C, B) {
		if (typeof C != "function") {
			return
		}
		!this.__listeners && (this.__listeners = {});
		var A = this.__listeners, E;
		if (typeof B == "string" && B) {
			if (/[^\w\-]/.test(B)) {
				throw ("nonstandard key:" + B)
			} else {
				C.hashCode = B;
				E = B
			}
		}
		D.indexOf("on") != 0 && (D = "on" + D);
		typeof A[D] != "object" && (A[D] = {});
		E = E || baidu.lang.guid();
		C.hashCode = E;
		A[D][E] = C
	};
	baidu.lang.Class.prototype.removeEventListener = function(C, B) {
		if (typeof B == "function") {
			B = B.hashCode
		} else {
			if (typeof B != "string") {
				return
			}
		}
		!this.__listeners && (this.__listeners = {});
		C.indexOf("on") != 0 && (C = "on" + C);
		var A = this.__listeners;
		if (!A[C]) {
			return
		}
		A[C][B] && delete A[C][B]
	};
	baidu.lang.Class.prototype.dispatchEvent = function(D, A) {
		if ("string" == typeof D) {
			D = new baidu.lang.Event(D)
		}
		!this.__listeners && (this.__listeners = {});
		A = A || {};
		for ( var C in A) {
			typeof D[C] == "undefined" && (D[C] = A[C])
		}
		var C, B = this.__listeners, E = D.type;
		D.target = D.target || this;
		D.currentTarget = this;
		E.indexOf("on") != 0 && (E = "on" + E);
		typeof this[E] == "function" && this[E].apply(this, arguments);
		if (typeof B[E] == "object") {
			for (C in B[E]) {
				B[E][C].apply(this, arguments)
			}
		}
		return D.returnValue
	};
	baidu.lang.module = function(name, module, owner) {
		var packages = name.split("."), len = packages.length - 1, packageName, i = 0;
		if (!owner) {
			try {
				if (!(new RegExp("^[a-zA-Z_\x24][a-zA-Z0-9_\x24]*\x24"))
						.test(packages[0])) {
					throw ""
				}
				owner = eval(packages[0]);
				i = 1
			} catch (e) {
				owner = window
			}
		}
		for (; i < len; i++) {
			packageName = packages[i];
			if (!owner[packageName]) {
				owner[packageName] = {}
			}
			owner = owner[packageName]
		}
		if (!owner[packages[len]]) {
			owner[packages[len]] = module
		}
	};
	baidu.lang.inherits = function(G, E, D) {
		var C, F, A = G.prototype, B = new Function();
		B.prototype = E.prototype;
		F = G.prototype = new B();
		for (C in A) {
			F[C] = A[C]
		}
		G.prototype.constructor = G;
		G.superClass = E.prototype;
		if ("string" == typeof D) {
			F._className = D
		}
	};
	baidu.inherits = baidu.lang.inherits;
	baidu.object = baidu.object || {};
	baidu.object.extend = function(C, A) {
		for ( var B in A) {
			if (A.hasOwnProperty(B)) {
				C[B] = A[B]
			}
		}
		return C
	};
	baidu.extend = baidu.object.extend;
	baidu.object.clone = (function(A) {
		return function(F) {
			var C = F, D, B;
			if (!F || F instanceof Number || F instanceof String
					|| F instanceof Boolean) {
				return C
			} else {
				if (F instanceof Array) {
					C = [];
					var E = 0;
					for (D = 0, B = F.length; D < B; D++) {
						C[E++] = baidu.object.clone(F[D])
					}
				} else {
					if ("object" == typeof F) {
						if (A[Object.prototype.toString.call(F)]) {
							return C
						}
						C = {};
						for (D in F) {
							if (F.hasOwnProperty(D)) {
								C[D] = baidu.object.clone(F[D])
							}
						}
					}
				}
			}
			return C
		}
	})({
		"[object Function]" : 1,
		"[object RegExp]" : 1,
		"[object Date]" : 1,
		"[object Error]" : 1
	});
	baidu.string.getByteLength = function(A) {
		return String(A).replace(/[^\x00-\xff]/g, "ci").length
	};
	baidu.string.format = function(C, A) {
		C = String(C);
		var B = Array.prototype.slice.call(arguments, 1), D = Object.prototype.toString;
		if (B.length) {
			B = B.length == 1 ? (A !== null
					&& (/\[object Array\]|\[object Object\]/.test(D.call(A))) ? A
					: B)
					: B;
			return C.replace(/#\{(.+?)\}/g, function(E, G) {
				var F = B[G];
				if ("[object Function]" == D.call(F)) {
					F = F(G)
				}
				return ("undefined" == typeof F ? "" : F)
			})
		}
		return C
	};
	baidu.format = baidu.string.format;
	baidu.string.encodeHTML = function(A) {
		return String(A).replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(
				/>/g, "&gt;").replace(/"/g, "&quot;")
	};
	baidu.encodeHTML = baidu.string.encodeHTML;
	baidu.string.decodeHTML = function(A) {
		var B = String(A).replace(/&quot;/g, '"').replace(/&lt;/g, "<")
				.replace(/&gt;/g, ">").replace(/&amp;/g, "&");
		return B.replace(/&#([\d]+);/g, function(D, C) {
			return String.fromCharCode(parseInt(C, 10))
		})
	};
	baidu.decodeHTML = baidu.string.decodeHTML;
	baidu.url = baidu.url || {};
	baidu.url.getQueryValue = function(B, C) {
		var D = new RegExp("(^|&|\\?|#)" + baidu.string.escapeReg(C)
				+ "=([^&]*)(&|\x24)", "");
		var A = B.match(D);
		if (A) {
			return A[2]
		}
		return null
	};
	var BannerAlternate = (function() {
		var alternateURL = [
				{
					linkURL : "http://www.baidu.com/search/wenku/huodong/teach.html?fr=fr_banner_teach",
					linkTitle : "2010文库教案大赛",
					picURL : "http://img.baidu.com/img/iknow/docshare/rsp-url-img.jpg",
					picTitle : "2010文库教案大赛"
				},
				{
					linkURL : "http://www.baidu.com/search/wenku/yinengjing/index.html",
					linkTitle : "灵魂的自由",
					picURL : "http://img.baidu.com/img/iknow/docshare/freeinmind.png",
					picTitle : "灵魂的自由"
				} ];
		var Config = {
			FIRST : 1,
			SECOND : 2
		};
		var isBannerIndexExist = function(index) {
			if (index) {
				return true
			} else {
				return false
			}
		};
		var isBannerIndexInRange = function(index) {
			if (index == Config.FIRST || index == Config.SECOND) {
				return true
			} else {
				return false
			}
		};
		return {
			execute : function() {
				var bannerContainer = baidu.Q("rsp-url")[0];
				var bannerEl = bannerContainer.getElementsByTagName("img")[0];
				var bannerIndex = baidu.cookie.get("bannerindex");
				if (isBannerIndexExist(bannerIndex)
						&& isBannerIndexInRange(bannerIndex)) {
					bannerContainer.innerHTML = baidu.string.format(baidu
							.G("bannerTemplate").value,
							alternateURL[bannerIndex % Config.SECOND]);
					baidu.cookie.remove("bannerindex");
					baidu.cookie.set("bannerindex",
							(bannerIndex % Config.SECOND) + Config.FIRST)
				} else {
					bannerIndex = Config.SECOND;
					bannerContainer.innerHTML = baidu.string.format(baidu
							.G("bannerTemplate").value,
							alternateURL[bannerIndex % Config.SECOND]);
					baidu.cookie.set("bannerindex",
							(bannerIndex % Config.SECOND) + Config.FIRST)
				}
			}
		}
	})();
	baidu.on(window, "load", function(e) {
		BannerAlternate.execute()
	})
})();