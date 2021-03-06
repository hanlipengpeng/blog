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
	for ( var A = [], B = arguments.length - 1; B > -1; B--) {
		var C = arguments[B];
		A[B] = null;
		if (typeof C == "object" && C && C.dom) {
			A[B] = C.dom
		} else {
			if ((typeof C == "object" && C && C.tagName) || C == window
					|| C == document) {
				A[B] = C
			} else {
				if (baidu.isString(C) && (C = document.getElementById(C))) {
					A[B] = C
				}
			}
		}
	}
	return A.length < 2 ? A[0] : A
};
baidu.each = function(F, B) {
	if (typeof B != "function") {
		return F
	}
	if (F) {
		var A;
		if (F.length === undefined) {
			for ( var C in F) {
				if (C in {}) {
					continue
				}
				A = B.call(F[C], F[C], C);
				if (A == "break") {
					break
				}
			}
		} else {
			for ( var D = 0, E = F.length; D < E; D++) {
				A = B.call(F[D], F[D], D);
				if (A == "break") {
					break
				}
			}
		}
	}
	return F
};
baidu.hide = function() {
	baidu.each(arguments, function(A) {
		if (A = baidu.G(A)) {
			A.style.display = "none"
		}
	})
};
baidu.show = function() {
	baidu.each(arguments, function(A) {
		if (A = baidu.G(A)) {
			A.style.display = ""
		}
	})
};
baidu.trim = function(B, A) {
	if (A == "left") {
		return B.replace(/(^[\s\t\xa0\u3000]+)/g, "")
	}
	if (A == "right") {
		return B.replace(new RegExp("[\\u3000\\xa0\\s\\t]+\x24", "g"), "")
	}
	return B.replace(new RegExp(
			"(^[\\s\\t\\xa0\\u3000]+)|([\\u3000\\xa0\\s\\t]+\x24)", "g"), "")
};
baidu.addClass = function(A, B) {
	if (!(A = baidu.G(A))) {
		return null
	}
	B = baidu.trim(B);
	if (!new RegExp("(^| )" + B.replace(/(\W)/g, "\\\x241") + "( |\x24)")
			.test(A.className)) {
		A.className = baidu.trim(A.className.split(/\s+/).concat(B).join(" "))
	}
};
baidu.ac = baidu.addClass;
baidu.removeClass = function(A, B) {
	if (!(A = baidu.G(A))) {
		return
	}
	B = baidu.trim(B);
	var C = A.className.replace(new RegExp("(^| +)"
			+ B.replace(/(\W)/g, "\\\x241") + "( +|\x24)", "g"), "\x242");
	if (A.className != C) {
		A.className = baidu.trim(C)
	}
};
baidu.rc = baidu.removeClass;
baidu.on = function(I, F, E, H) {
	if (!(I = baidu.G(I)) || typeof E != "function") {
		return I
	}
	F = F.replace(/^on/i, "").toLowerCase();
	function B(J) {
		return J || window.event
	}
	var D = function(J) {
		E.call(D.src, B(J))
	};
	D.src = I;
	var C = baidu.on._listeners;
	var A = [ I, F, E, D ];
	C[C.length] = A;
	if (I.attachEvent) {
		I.attachEvent("on" + F, D)
	} else {
		if (I.addEventListener) {
			I.addEventListener(F, D, false)
		}
	}
	return I
};
baidu.on._listeners = [];
baidu.un = function(B, F, K) {
	if (!(B = this.G(B)) || typeof K != "function") {
		return B
	}
	F = F.replace(/^on/i, "").toLowerCase();
	var H = baidu.on._listeners;
	if (!K) {
		var I;
		for ( var C = 0, E = H.length; C < E; C++) {
			I = H[C];
			if (I && I[0] === B && I[1] === F) {
				baidu.un(B, F, I[2])
			}
		}
		return B
	}
	function A(O, N, Q) {
		for ( var P = 0, M = H.length; P < M; ++P) {
			var L = H[P];
			if (L && L[2] === K && L[0] === B && L[1] === F) {
				return P
			}
		}
		return -1
	}
	var D = A(B, F, K);
	var J = null;
	if (D >= 0) {
		J = H[D]
	} else {
		return B
	}
	if (B.detachEvent) {
		B.detachEvent("on" + F, J[3])
	} else {
		if (B.removeEventListener) {
			B.removeEventListener(F, J[3], false)
		}
	}
	delete J[3];
	delete J[2];
	H.splice(D, 1);
	return B
};
baidu.body = function() {
	var A = 0, J = 0, E = 0, C = 0, B = 0, K = 0;
	var F = window, D = document, I = D.documentElement;
	A = I.clientWidth || D.body.clientWidth;
	J = F.innerHeight || I.clientHeight || D.body.clientHeight;
	C = D.body.scrollTop || I.scrollTop;
	E = D.body.scrollLeft || I.scrollLeft;
	B = Math.max(D.body.scrollWidth, I.scrollWidth || 0);
	K = Math.max(D.body.scrollHeight, I.scrollHeight || 0, J);
	return {
		scrollTop : C,
		scrollLeft : E,
		documentWidth : B,
		documentHeight : K,
		viewWidth : A,
		viewHeight : J
	}
};
baidu.browser = baidu.browser || {};
(function() {
	var A = navigator.userAgent;
	baidu.firefox = baidu.browser.firefox = /firefox\/(\d+\.\d)/i.test(A) ? parseFloat(RegExp["\x241"])
			: 0;
	baidu.ie = baidu.browser.ie = /msie (\d+\.\d)/i.test(A) ? parseFloat(RegExp["\x241"])
			: 0;
	baidu.opera = baidu.browser.opera = /opera\/(\d+\.\d)/i.test(A) ? parseFloat(RegExp["\x241"])
			: 0;
	baidu.safari = baidu.browser.safari = (/(\d+\.\d)(\.\d)?\s+safari/i.test(A) && !/chrome/i
			.test(A)) ? parseFloat(RegExp["\x241"]) : 0;
	try {
		baidu.browser.maxthon = /(\d+\.\d)/.test(external.max_version) ? parseFloat(RegExp["\x241"])
				: 0
	} catch (B) {
		baidu.browser.maxthon = 0
	}
	baidu.maxthon = baidu.browser.maxthon;
	baidu.isGecko = baidu.browser.isGecko = /gecko/i.test(A)
			&& !/like gecko/i.test(A);
	baidu.isStrict = baidu.browser.isStrict = document.compatMode == "CSS1Compat";
	baidu.isWebkit = baidu.browser.isWebkit = /webkit/i.test(A)
})();
baidu.getCurrentStyle = function(A, C) {
	var E = null;
	if (!(A = baidu.G(A))) {
		return null
	}
	if (E = A.style[C]) {
		return E
	} else {
		if (A.currentStyle) {
			E = A.currentStyle[C]
		} else {
			var D = A.nodeType == 9 ? A : A.ownerDocument || A.document;
			if (D.defaultView && D.defaultView.getComputedStyle) {
				var B = D.defaultView.getComputedStyle(A, "");
				if (B) {
					E = B[C]
				}
			}
		}
	}
	return E
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
baidu.dom.getPosition = function(D) {
	D = baidu.G(D);
	if (!baidu.isElement(D)) {
		throw new Error("[baidu.dom.getPosition] param must be Element")
	}
	var H = baidu.dom.getDocument(D);
	var F = baidu.isGecko > 0 && H.getBoxObjectFor
			&& baidu.getCurrentStyle(D, "position") == "absolute"
			&& (D.style.top === "" || D.style.left === "");
	var I = {
		left : 0,
		top : 0
	};
	var B = (baidu.ie && !baidu.isStrict) ? H.body : H.documentElement;
	if (D == B) {
		return I
	}
	var C = null;
	var E;
	if (D.getBoundingClientRect) {
		E = D.getBoundingClientRect();
		I.left = E.left
				+ Math.max(H.documentElement.scrollLeft, H.body.scrollLeft);
		I.top = E.top + Math.max(H.documentElement.scrollTop, H.body.scrollTop);
		I.left -= H.documentElement.clientLeft;
		I.top -= H.documentElement.clientTop;
		if (baidu.ie && !baidu.isStrict) {
			I.left -= 2;
			I.top -= 2
		}
	} else {
		if (H.getBoxObjectFor && !F) {
			E = H.getBoxObjectFor(D);
			var A = H.getBoxObjectFor(B);
			I.left = E.screenX - A.screenX;
			I.top = E.screenY - A.screenY
		} else {
			C = D;
			do {
				I.left += C.offsetLeft;
				I.top += C.offsetTop;
				if (baidu.isWebkit > 0
						&& baidu.getCurrentStyle(C, "position") == "fixed") {
					I.left += H.body.scrollLeft;
					I.top += H.body.scrollTop;
					break
				}
				C = C.offsetParent
			} while (C && C != D);
			if (baidu.opera > 0
					|| (baidu.isWebkit > 0 && baidu.getCurrentStyle(D,
							"position") == "absolute")) {
				I.top -= H.body.offsetTop
			}
			C = D.offsetParent;
			while (C && C != H.body) {
				I.left -= C.scrollLeft;
				if (!baidu.opera || C.tagName != "TR") {
					I.top -= C.scrollTop
				}
				C = C.offsetParent
			}
		}
	}
	return I
};
baidu.ajax = baidu.ajax || {};
baidu.ajax.request = function(E, Q) {
	Q = Q || {};
	var L = Q.data || "";
	var I = !(Q.async === false);
	var K = Q.username || "";
	var O = Q.password || "";
	var B = Q.method || "GET";
	var H = Q.headers || {};
	var D = {};
	var J;
	for (J in Q) {
		if (J.indexOf("on") === 0) {
			D[J] = Q[J]
		}
	}
	H["X-Request-By"] = "baidu.ajax";
	try {
		var P = C();
		if ((new RegExp("^get\x24", "i")).test(B)) {
			var A = (E.indexOf("?") >= 0 ? "&" : "?") + "b"
					+ (new Date()).getTime() + "=1";
			E += A
		}
		if (K) {
			P.open(B, E, I, K, O)
		} else {
			P.open(B, E, I)
		}
		if (I) {
			P.onreadystatechange = M
		}
		if ((new RegExp("^post\x24", "i")).test(B)) {
			P.setRequestHeader("Content-Type",
					"application/x-www-form-urlencoded")
		} else {
		}
		for (J in H) {
			if (H.hasOwnProperty(J)) {
				P.setRequestHeader(J, H[J])
			}
		}
		F("beforerequest");
		P.send(L);
		if (!I) {
			M()
		}
	} catch (N) {
		F("failure")
	}
	return P;
	function M() {
		if (P.readyState == 4) {
			try {
				var S = P.status
			} catch (R) {
				F("failure");
				return
			}
			F("status");
			F(S);
			if ((S >= 200 && S < 300) || S == 304 || S == 1223) {
				F("success")
			} else {
				F("failure")
			}
			window.setTimeout(function() {
				P.onreadystatechange = new Function();
				if (I) {
					P = null
				}
			}, 0)
		}
	}
	function C() {
		return window.ActiveXObject ? new ActiveXObject("Microsoft.XMLHTTP")
				: new XMLHttpRequest()
	}
	function F(S) {
		S = "on" + S;
		var R = D[S];
		var T = baidu.ajax[S];
		if ("function" == typeof R) {
			if (S != "onstatus") {
				R(P)
			} else {
				R(P, P.status)
			}
		} else {
			if ("function" == typeof T) {
				if (S == "onsuccess") {
					return
				}
				if (S != "onstatus") {
					T(P)
				} else {
					T(P, P.status)
				}
			}
		}
	}
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
baidu.swf = baidu.swf || {};
baidu.swf.getMovie = function(A) {
	return document[A] || window[A]
};
baidu.swf.getVersion = function() {
	var E = navigator;
	if (E.plugins && E.mimeTypes.length) {
		var A = E.plugins["Shockwave Flash"];
		if (A && A.description) {
			return A.description.replace(/([a-zA-Z]|\s)+/, "").replace(
					/(\s)+r/, ".")
					+ ".0"
		}
	} else {
		if (window.ActiveXObject && !window.opera) {
			for ( var B = 10; B >= 2; B--) {
				try {
					var D = new ActiveXObject("ShockwaveFlash.ShockwaveFlash."
							+ B);
					if (D) {
						return B + ".0.0";
						break
					}
				} catch (C) {
				}
			}
		}
	}
};
baidu.swf.createHTML = function(Q) {
	Q = Q || {};
	var I = baidu.swf.getVersion(), P = 1;
	var F = Q.ver || "6.0.0", E, C;
	if (I) {
		I = I.split(".");
		F = F.split(".");
		for ( var D = 0; D < 3; D++) {
			E = parseInt(I[D], 10);
			C = parseInt(F[D], 10);
			if (C < E) {
				break
			} else {
				if (C > E) {
					P = 0;
					break
				}
			}
		}
	} else {
		P = 0
	}
	if (!P) {
		return ""
	}
	var K = Q.vars;
	var D, B, H, O;
	var J = [ "classid", "codebase", "id", "width", "height", "align" ];
	Q.align = Q.align || "middle";
	Q.classid = "clsid:d27cdb6e-ae6d-11cf-96b8-444553540000";
	Q.codebase = "http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0";
	Q.movie = Q.url || "";
	delete Q.vars;
	delete Q.url;
	if (baidu.isString(K)) {
		Q.flashvars = K
	} else {
		var M = [];
		for (B in K) {
			O = K[B];
			if (O) {
				M.push(B + "=" + encodeURIComponent(O))
			}
		}
		Q.flashvars = M.join("&")
	}
	var L = [ "<object " ];
	for (D = 0, H = J.length; D < H; D++) {
		O = J[D];
		L.push(" ", O, '="', Q[O], '"')
	}
	L.push(">");
	var A = {
		wmode : 1,
		scale : 1,
		quality : 1,
		play : 1,
		loop : 1,
		menu : 1,
		salign : 1,
		bgcolor : 1,
		base : 1,
		allowscriptaccess : 1,
		allownetworking : 1,
		allowfullscreen : 1,
		seamlesstabbing : 1,
		devicefont : 1,
		swliveconnect : 1,
		flashvars : 1,
		movie : 1
	};
	for (B in Q) {
		O = Q[B];
		if (A[B] && O) {
			L.push('<param name="' + B + '" value="' + O + '" />')
		}
	}
	Q.src = Q.movie;
	Q.name = Q.id;
	delete Q.id;
	delete Q.movie;
	delete Q.classid;
	delete Q.codebase;
	Q.type = "application/x-shockwave-flash";
	Q.pluginspage = "http://www.macromedia.com/go/getflashplayer";
	L.push("<embed");
	var N;
	for (B in Q) {
		O = Q[B];
		if (O) {
			if ((new RegExp("^salign\x24", "i")).test(B)) {
				N = O;
				continue
			}
			L.push(" ", B, '="', O, '"')
		}
	}
	if (N) {
		L.push(' salign="', N, '"')
	}
	L.push("></embed></object>");
	return L.join("")
};
baidu.swf.create = function(A, D) {
	A = A || {};
	var B = baidu.swf.createHTML(A);
	var C = true;
	if (D && baidu.isString(D)) {
		D = document.getElementById(D)
	}
	if (B.length <= 0) {
		B = A.errorMessage || "";
		C = false
	}
	if (D) {
		D.innerHTML = B
	} else {
		document.write(B)
	}
	return C
};
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
	for ( var A = [], B = arguments.length - 1; B > -1; B--) {
		var C = arguments[B];
		A[B] = null;
		if (typeof C == "object" && C && C.dom) {
			A[B] = C.dom
		} else {
			if ((typeof C == "object" && C && C.tagName) || C == window
					|| C == document) {
				A[B] = C
			} else {
				if (baidu.isString(C) && (C = document.getElementById(C))) {
					A[B] = C
				}
			}
		}
	}
	return A.length < 2 ? A[0] : A
};
baidu.each = function(F, B) {
	if (typeof B != "function") {
		return F
	}
	if (F) {
		var A;
		if (F.length === undefined) {
			for ( var C in F) {
				if (C in {}) {
					continue
				}
				A = B.call(F[C], F[C], C);
				if (A == "break") {
					break
				}
			}
		} else {
			for ( var D = 0, E = F.length; D < E; D++) {
				A = B.call(F[D], F[D], D);
				if (A == "break") {
					break
				}
			}
		}
	}
	return F
};
baidu.hide = function() {
	baidu.each(arguments, function(A) {
		if (A = baidu.G(A)) {
			A.style.display = "none"
		}
	})
};
baidu.show = function() {
	baidu.each(arguments, function(A) {
		if (A = baidu.G(A)) {
			A.style.display = ""
		}
	})
};
baidu.trim = function(B, A) {
	if (A == "left") {
		return B.replace(/(^[\s\t\xa0\u3000]+)/g, "")
	}
	if (A == "right") {
		return B.replace(new RegExp("[\\u3000\\xa0\\s\\t]+\x24", "g"), "")
	}
	return B.replace(new RegExp(
			"(^[\\s\\t\\xa0\\u3000]+)|([\\u3000\\xa0\\s\\t]+\x24)", "g"), "")
};
baidu.addClass = function(A, B) {
	if (!(A = baidu.G(A))) {
		return null
	}
	B = baidu.trim(B);
	if (!new RegExp("(^| )" + B.replace(/(\W)/g, "\\\x241") + "( |\x24)")
			.test(A.className)) {
		A.className = baidu.trim(A.className.split(/\s+/).concat(B).join(" "))
	}
};
baidu.ac = baidu.addClass;
baidu.removeClass = function(A, B) {
	if (!(A = baidu.G(A))) {
		return
	}
	B = baidu.trim(B);
	var C = A.className.replace(new RegExp("(^| +)"
			+ B.replace(/(\W)/g, "\\\x241") + "( +|\x24)", "g"), "\x242");
	if (A.className != C) {
		A.className = baidu.trim(C)
	}
};
baidu.rc = baidu.removeClass;
baidu.on = function(I, F, E, H) {
	if (!(I = baidu.G(I)) || typeof E != "function") {
		return I
	}
	F = F.replace(/^on/i, "").toLowerCase();
	function B(J) {
		return J || window.event
	}
	var D = function(J) {
		E.call(D.src, B(J))
	};
	D.src = I;
	var C = baidu.on._listeners;
	var A = [ I, F, E, D ];
	C[C.length] = A;
	if (I.attachEvent) {
		I.attachEvent("on" + F, D)
	} else {
		if (I.addEventListener) {
			I.addEventListener(F, D, false)
		}
	}
	return I
};
baidu.on._listeners = [];
baidu.un = function(B, F, K) {
	if (!(B = this.G(B)) || typeof K != "function") {
		return B
	}
	F = F.replace(/^on/i, "").toLowerCase();
	var H = baidu.on._listeners;
	if (!K) {
		var I;
		for ( var C = 0, E = H.length; C < E; C++) {
			I = H[C];
			if (I && I[0] === B && I[1] === F) {
				baidu.un(B, F, I[2])
			}
		}
		return B
	}
	function A(O, N, Q) {
		for ( var P = 0, M = H.length; P < M; ++P) {
			var L = H[P];
			if (L && L[2] === K && L[0] === B && L[1] === F) {
				return P
			}
		}
		return -1
	}
	var D = A(B, F, K);
	var J = null;
	if (D >= 0) {
		J = H[D]
	} else {
		return B
	}
	if (B.detachEvent) {
		B.detachEvent("on" + F, J[3])
	} else {
		if (B.removeEventListener) {
			B.removeEventListener(F, J[3], false)
		}
	}
	delete J[3];
	delete J[2];
	H.splice(D, 1);
	return B
};
baidu.body = function() {
	var A = 0, J = 0, E = 0, C = 0, B = 0, K = 0;
	var F = window, D = document, I = D.documentElement;
	A = I.clientWidth || D.body.clientWidth;
	J = F.innerHeight || I.clientHeight || D.body.clientHeight;
	C = D.body.scrollTop || I.scrollTop;
	E = D.body.scrollLeft || I.scrollLeft;
	B = Math.max(D.body.scrollWidth, I.scrollWidth || 0);
	K = Math.max(D.body.scrollHeight, I.scrollHeight || 0, J);
	return {
		scrollTop : C,
		scrollLeft : E,
		documentWidth : B,
		documentHeight : K,
		viewWidth : A,
		viewHeight : J
	}
};
baidu.browser = baidu.browser || {};
(function() {
	var A = navigator.userAgent;
	baidu.firefox = baidu.browser.firefox = /firefox\/(\d+\.\d)/i.test(A) ? parseFloat(RegExp["\x241"])
			: 0;
	baidu.ie = baidu.browser.ie = /msie (\d+\.\d)/i.test(A) ? parseFloat(RegExp["\x241"])
			: 0;
	baidu.opera = baidu.browser.opera = /opera\/(\d+\.\d)/i.test(A) ? parseFloat(RegExp["\x241"])
			: 0;
	baidu.safari = baidu.browser.safari = (/(\d+\.\d)(\.\d)?\s+safari/i.test(A) && !/chrome/i
			.test(A)) ? parseFloat(RegExp["\x241"]) : 0;
	try {
		baidu.browser.maxthon = /(\d+\.\d)/.test(external.max_version) ? parseFloat(RegExp["\x241"])
				: 0
	} catch (B) {
		baidu.browser.maxthon = 0
	}
	baidu.maxthon = baidu.browser.maxthon;
	baidu.isGecko = baidu.browser.isGecko = /gecko/i.test(A)
			&& !/like gecko/i.test(A);
	baidu.isStrict = baidu.browser.isStrict = document.compatMode == "CSS1Compat";
	baidu.isWebkit = baidu.browser.isWebkit = /webkit/i.test(A)
})();
baidu.getCurrentStyle = function(A, C) {
	var E = null;
	if (!(A = baidu.G(A))) {
		return null
	}
	if (E = A.style[C]) {
		return E
	} else {
		if (A.currentStyle) {
			E = A.currentStyle[C]
		} else {
			var D = A.nodeType == 9 ? A : A.ownerDocument || A.document;
			if (D.defaultView && D.defaultView.getComputedStyle) {
				var B = D.defaultView.getComputedStyle(A, "");
				if (B) {
					E = B[C]
				}
			}
		}
	}
	return E
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
baidu.dom.getPosition = function(D) {
	D = baidu.G(D);
	if (!baidu.isElement(D)) {
		throw new Error("[baidu.dom.getPosition] param must be Element")
	}
	var H = baidu.dom.getDocument(D);
	var F = baidu.isGecko > 0 && H.getBoxObjectFor
			&& baidu.getCurrentStyle(D, "position") == "absolute"
			&& (D.style.top === "" || D.style.left === "");
	var I = {
		left : 0,
		top : 0
	};
	var B = (baidu.ie && !baidu.isStrict) ? H.body : H.documentElement;
	if (D == B) {
		return I
	}
	var C = null;
	var E;
	if (D.getBoundingClientRect) {
		E = D.getBoundingClientRect();
		I.left = E.left
				+ Math.max(H.documentElement.scrollLeft, H.body.scrollLeft);
		I.top = E.top + Math.max(H.documentElement.scrollTop, H.body.scrollTop);
		I.left -= H.documentElement.clientLeft;
		I.top -= H.documentElement.clientTop;
		if (baidu.ie && !baidu.isStrict) {
			I.left -= 2;
			I.top -= 2
		}
	} else {
		if (H.getBoxObjectFor && !F) {
			E = H.getBoxObjectFor(D);
			var A = H.getBoxObjectFor(B);
			I.left = E.screenX - A.screenX;
			I.top = E.screenY - A.screenY
		} else {
			C = D;
			do {
				I.left += C.offsetLeft;
				I.top += C.offsetTop;
				if (baidu.isWebkit > 0
						&& baidu.getCurrentStyle(C, "position") == "fixed") {
					I.left += H.body.scrollLeft;
					I.top += H.body.scrollTop;
					break
				}
				C = C.offsetParent
			} while (C && C != D);
			if (baidu.opera > 0
					|| (baidu.isWebkit > 0 && baidu.getCurrentStyle(D,
							"position") == "absolute")) {
				I.top -= H.body.offsetTop
			}
			C = D.offsetParent;
			while (C && C != H.body) {
				I.left -= C.scrollLeft;
				if (!baidu.opera || C.tagName != "TR") {
					I.top -= C.scrollTop
				}
				C = C.offsetParent
			}
		}
	}
	return I
};
baidu.ajax = baidu.ajax || {};
baidu.ajax.request = function(E, Q) {
	Q = Q || {};
	var L = Q.data || "";
	var I = !(Q.async === false);
	var K = Q.username || "";
	var O = Q.password || "";
	var B = Q.method || "GET";
	var H = Q.headers || {};
	var D = {};
	var J;
	for (J in Q) {
		if (J.indexOf("on") === 0) {
			D[J] = Q[J]
		}
	}
	H["X-Request-By"] = "baidu.ajax";
	try {
		var P = C();
		if ((new RegExp("^get\x24", "i")).test(B)) {
			var A = (E.indexOf("?") >= 0 ? "&" : "?") + "b"
					+ (new Date()).getTime() + "=1";
			E += A
		}
		if (K) {
			P.open(B, E, I, K, O)
		} else {
			P.open(B, E, I)
		}
		if (I) {
			P.onreadystatechange = M
		}
		if ((new RegExp("^post\x24", "i")).test(B)) {
			P.setRequestHeader("Content-Type",
					"application/x-www-form-urlencoded")
		} else {
		}
		for (J in H) {
			if (H.hasOwnProperty(J)) {
				P.setRequestHeader(J, H[J])
			}
		}
		F("beforerequest");
		P.send(L);
		if (!I) {
			M()
		}
	} catch (N) {
		F("failure")
	}
	return P;
	function M() {
		if (P.readyState == 4) {
			try {
				var S = P.status
			} catch (R) {
				F("failure");
				return
			}
			F("status");
			F(S);
			if ((S >= 200 && S < 300) || S == 304 || S == 1223) {
				F("success")
			} else {
				F("failure")
			}
			window.setTimeout(function() {
				P.onreadystatechange = new Function();
				if (I) {
					P = null
				}
			}, 0)
		}
	}
	function C() {
		return window.ActiveXObject ? new ActiveXObject("Microsoft.XMLHTTP")
				: new XMLHttpRequest()
	}
	function F(S) {
		S = "on" + S;
		var R = D[S];
		var T = baidu.ajax[S];
		if ("function" == typeof R) {
			if (S != "onstatus") {
				R(P)
			} else {
				R(P, P.status)
			}
		} else {
			if ("function" == typeof T) {
				if (S == "onsuccess") {
					return
				}
				if (S != "onstatus") {
					T(P)
				} else {
					T(P, P.status)
				}
			}
		}
	}
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
baidu.swf = baidu.swf || {};
baidu.swf.getMovie = function(A) {
	return document[A] || window[A]
};
baidu.swf.getVersion = function() {
	var E = navigator;
	if (E.plugins && E.mimeTypes.length) {
		var A = E.plugins["Shockwave Flash"];
		if (A && A.description) {
			return A.description.replace(/([a-zA-Z]|\s)+/, "").replace(
					/(\s)+r/, ".")
					+ ".0"
		}
	} else {
		if (window.ActiveXObject && !window.opera) {
			for ( var B = 10; B >= 2; B--) {
				try {
					var D = new ActiveXObject("ShockwaveFlash.ShockwaveFlash."
							+ B);
					if (D) {
						return B + ".0.0";
						break
					}
				} catch (C) {
				}
			}
		}
	}
};
baidu.swf.createHTML = function(Q) {
	Q = Q || {};
	var I = baidu.swf.getVersion(), P = 1;
	var F = Q.ver || "6.0.0", E, C;
	if (I) {
		I = I.split(".");
		F = F.split(".");
		for ( var D = 0; D < 3; D++) {
			E = parseInt(I[D], 10);
			C = parseInt(F[D], 10);
			if (C < E) {
				break
			} else {
				if (C > E) {
					P = 0;
					break
				}
			}
		}
	} else {
		P = 0
	}
	if (!P) {
		return ""
	}
	var K = Q.vars;
	var D, B, H, O;
	var J = [ "classid", "codebase", "id", "width", "height", "align" ];
	Q.align = Q.align || "middle";
	Q.classid = "clsid:d27cdb6e-ae6d-11cf-96b8-444553540000";
	Q.codebase = "http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0";
	Q.movie = Q.url || "";
	delete Q.vars;
	delete Q.url;
	if (baidu.isString(K)) {
		Q.flashvars = K
	} else {
		var M = [];
		for (B in K) {
			O = K[B];
			if (O) {
				M.push(B + "=" + encodeURIComponent(O))
			}
		}
		Q.flashvars = M.join("&")
	}
	var L = [ "<object " ];
	for (D = 0, H = J.length; D < H; D++) {
		O = J[D];
		L.push(" ", O, '="', Q[O], '"')
	}
	L.push(">");
	var A = {
		wmode : 1,
		scale : 1,
		quality : 1,
		play : 1,
		loop : 1,
		menu : 1,
		salign : 1,
		bgcolor : 1,
		base : 1,
		allowscriptaccess : 1,
		allownetworking : 1,
		allowfullscreen : 1,
		seamlesstabbing : 1,
		devicefont : 1,
		swliveconnect : 1,
		flashvars : 1,
		movie : 1
	};
	for (B in Q) {
		O = Q[B];
		if (A[B] && O) {
			L.push('<param name="' + B + '" value="' + O + '" />')
		}
	}
	Q.src = Q.movie;
	Q.name = Q.id;
	delete Q.id;
	delete Q.movie;
	delete Q.classid;
	delete Q.codebase;
	Q.type = "application/x-shockwave-flash";
	Q.pluginspage = "http://www.macromedia.com/go/getflashplayer";
	L.push("<embed");
	var N;
	for (B in Q) {
		O = Q[B];
		if (O) {
			if ((new RegExp("^salign\x24", "i")).test(B)) {
				N = O;
				continue
			}
			L.push(" ", B, '="', O, '"')
		}
	}
	if (N) {
		L.push(' salign="', N, '"')
	}
	L.push("></embed></object>");
	return L.join("")
};
baidu.swf.create = function(A, D) {
	A = A || {};
	var B = baidu.swf.createHTML(A);
	var C = true;
	if (D && baidu.isString(D)) {
		D = document.getElementById(D)
	}
	if (B.length <= 0) {
		B = A.errorMessage || "";
		C = false
	}
	if (D) {
		D.innerHTML = B
	} else {
		document.write(B)
	}
	return C
};
function G(A) {
	return baidu.G(A)
}
baidu.preventDefault = function(A) {
	A = A || window.event;
	if (A.preventDefault) {
		A.preventDefault()
	} else {
		A.returnValue = false
	}
};
baidu.stopPropagation = function(A) {
	A = A || window.event;
	if (A.stopPropagation) {
		A.stopPropagation()
	} else {
		A.cancelBubble = true
	}
};
baidu.GT = function(A, B) {
	var C = B || document;
	return C.getElementsByTagName(A)
};
baidu.ges = function(B, C, H) {
	var I = (H) ? G(H) : document;
	var E = baidu.GT(B, I);
	var F = [];
	for ( var D = 0; D < E.length; D++) {
		for ( var A in C) {
			if (!(E[D][A] && C[A] == E[D][A])) {
				break
			}
			F.push(E[D])
		}
	}
	if (F.length == 0) {
		return false
	} else {
		if (F.length == 1) {
			return F[0]
		}
	}
	return F
};
baidu.loadJS = function(A, D) {
	if (!A) {
		return
	}
	var B = baidu.GT("head")[0];
	var C = document.createElement("script");
	C.type = "text/javascript";
	C.src = A;
	if (D) {
		if (D.id) {
			if (G(D.id) && G(D.id).tagName.toLowerCase() == "script") {
				baidu.rm(D.id)
			}
			C.id = D.id
		}
	}
	B.appendChild(C);
	C = null
};
baidu.proxy = function() {
	var C = [];
	for ( var B = 0, A = arguments.length; B < A; B++) {
		C[B] = arguments[B]
	}
	var D = C[0];
	C.shift();
	return function() {
		D.apply(null, C)
	}
};
baidu.breakWord = function(A, B) {
	baidu.each(A, function(F) {
		if (F.offsetWidth > B) {
			var C = 1, E = F.innerHTML;
			while (F.offsetWidth > B) {
				var D = "";
				D += E.substr(0, E.length - C) + "<br>";
				D += E.substr(E.length - C, E.length);
				F.innerHTML = D;
				C++
			}
		}
	})
};
baidu.insertWbr = function(D, A) {
	if (D.length <= A) {
		return D
	}
	var B = document.createElement("textarea");
	try {
		B.innerHTML = D
	} catch (E) {
		B.value = D
	}
	var C = B.value;
	C = C.replace(new RegExp("(\\S{" + A + "})", "img"), "$1<wbr>");
	C = C.replace(/<wbr>/img, "\u0001").replace(/<br>/img, "\u0002").replace(
			/</img, "&lt;").replace(/>/img, "&gt;")
			.replace(/\u0002/img, "<br>").replace(/\u0001/img, "<wbr>");
	return C
};
baidu.C = function(C, B) {
	var A = document.createElement(C);
	for (p in B) {
		A[p] = B[p]
	}
	return A
};
baidu.rm = function() {
	baidu.each(arguments, function(A) {
		if (A = baidu.G(A)) {
			A.parentNode.removeChild(A)
		}
	})
};
baidu.sug = function(A) {
	sug_opt.getdata(A.q, A.s);
	return {
		query : A.q,
		arr : A.s
	}
};
String.prototype.byteLength = function() {
	return this.replace(/[^\u0000-\u007f]/g, "\u0061\u0061").length
};
String.prototype.trim = function() {
	return this.replace(/(^[\s\t\xa0\u3000]+)|([\u3000\xa0\s\t]+$)/g, "")
};
function $(A) {
	if (/^#/.test(A)) {
	}
}
baidu.extend = function(C, A) {
	for ( var B in A) {
		if (A.hasOwnProperty(B)) {
			C[B] = A[B]
		}
	}
};
baidu.enableCustomEvent = function(A) {
	var B = {
		addEvent : function(E, D) {
			if (typeof D != "function") {
				return
			}
			!this.__listeners && (this.__listeners = {});
			var C = this.__listeners;
			typeof C[E] != "object" && (C[E] = []);
			C[E].push(D)
		},
		removeEvent : function(F, E) {
			var D = this.__listeners;
			if (!D[F]) {
				return
			}
			if (!E) {
				D[F] = []
			}
			for ( var C in D[F]) {
				if (D[F][C] == E) {
					D[F][C] = null
				}
			}
		},
		fireEvent : function(E) {
			!this.__listeners && (this.__listeners = {});
			var D = this.__listeners;
			if (typeof D[E] == "object") {
				for ( var C in D[E]) {
					D[E][C] && D[E][C].apply(this, arguments)
				}
			}
		}
	};
	baidu.extend(A, B)
};
document
		.write("<style>#shadowDiv{display:none;width:100%;height:100%;position:absolute;top:0:left:0;background:#000;filter: alpha(opacity=25);opacity:0.25;z-index:65534}#popDiv{display:none;position:absolute;width:450px;height:300px;z-index:65535;background:#fff;line-height:20px}#popBody{display:none;text-align:center;padding:20px;font-size:14px}.pop_r1,.pop_r2,.pop_r3,.pop_r4{width:10px;height:5px;display:bolck;overflow:hidden;background:url(http://img.baidu.com/img/iknow/docshare/bg_popup.gif)}.pop_r1{background-position:0 -62px;float:left;margin-top:-3px}.pop_r2{background-position:-10px -62px;float:right;margin-top:-3px}.pop_r3{height:10px;background-position:0 -67px;float:left}.pop_r4{height:10px;background-position:-10px -67px;float:right}.pop_bottom{background:url(http://img.baidu.com/img/iknow/docshare/bg_popup.gif) repeat-x 0 -82px}.pop_bg1{width:10px;background:url(http://img.baidu.com/img/iknow/docshare/bg_popup2.gif) repeat-y}.pop_bg2{width:10px;background:url(http://img.baidu.com/img/iknow/docshare/bg_popup2.gif) repeat-y right center}.pop_holder1{width:100%;height:100%;background:#fff;border:2px solid #68995a;position:absolute;top:0;left:0;z-index:1}.pop_holder2{width:100%;height:100%;background:#000;position:absolute;left:7px;top:7px;filter:alpha(opacity=25);opacity:0.25}.pop_title{height:33px;line-height:33px;padding:0 10px;color:#2f5124;font-size:14px;font-weight:bold;background:url(http://img.baidu.com/img/iknow/docshare/bg_popup.gif) repeat-x}.pop_close{width:19px;height:19px;display:block;float:right;background:url(http://img.baidu.com/img/iknow/docshare/bg_popup.gif) no-repeat 0 -38px;border:0;margin:5px 0 0 0;padding:0;cursor:pointer}.tasknote{padding-left:10px;position:absolute;z-index:100;}.tasknote .arrow{background:url(http://img.baidu.com/img/iknow/task/arrow.gif) 0 0 no-repeat;overflow:hidden;position:absolute;top:9px;left:0;z-index:1;}.tasknote .side{width:11px;height:21px;}.tasknote .up{top:0;left:40px;width:21px;height:11px;}.tasknote .down{bottom:2px;top:auto;left:40px;width:21px;height:11px;background-position:0 -10px}.tasknotebody{background:#FFF9E3; border:1px solid #FBE5A3; width:100px; padding-left:4px; font-size:12px; }.noticeInfo{background:url(http://img.baidu.com/img/iknow/task/note.gif) no-repeat 0 center; padding-left:20px; margin:8px 8px 0; line-height:22px; }.noticelist{ margin:0;padding:0; margin-left:26px; }.noticelist li{ margin:0;padding:0; line-height:22px; }.tasknote .close{width:13px;height:13px;background:url(http://img.baidu.com/img/iknow/task/close.gif) no-repeat  0 0;overflow:hidden;position:absolute;top:2px;right:2px;cursor:pointer;z-index:2};</style>");
var pop = {
	onOk : function() {
	},
	onclosed : function() {
	},
	create : function() {
		if (!G("popDiv")) {
			var B = document.createElement("div");
			B.id = "shadowDiv";
			var C = document.createElement("div");
			C.id = "popDiv";
			C.style.width = C.style.height = "500px";
			var A = document.createElement("iframe");
			A.id = "shadowIframe";
			A.style.filter = "alpha(opacity=0)";
			A.style.opacity = "0";
			A.style.position = "absolute";
			var D = "";
			D += '<div class="pop_holder1">';
			D += '<table border="0" cellspacing="0" cellpadding="0" style="width:100%;height:100%">';
			D += '<tr><td height="35"valign="top" colspan="3"><div class="pop_title"><input type="button" id="dialogBoxClose" onclick="pop.hide();return false" title="关闭" class="pop_close"><span id="dialogBoxTitle">提示</span></div><div><div class="pop_r1"></div><div class="pop_r2"></div></div></td></tr>';
			D += '<tr><td class="pop_bg1">&nbsp;</td><td id="popContent">';
			D += '<div id="popBody"></div>';
			D += '<div id="ifrDiv" style="height:100%"><iframe id="popIframe" name="popIframe" width="100%" frameborder="0" height="100%" scrolling="no" src="about:blank"></iframe></div>';
			D += '</td><td class="pop_bg2">&nbsp;</td></tr>';
			D += '<tr><td height="10" valign="top" colspan="3" class="pop_bottom"><div><div class="pop_r3"></div><div class="pop_r4"></div></div></td></tr>';
			D += "</table>";
			D += "</div>";
			D += '<div id="pop_holder2" class="pop_holder2"></div>';
			C.innerHTML = D;
			document.body.insertBefore(A, document.body.firstChild);
			document.body.insertBefore(C, document.body.firstChild);
			document.body.insertBefore(B, document.body.firstChild)
		}
	},
	resize : function(B, A, L) {
		pop.create();
		var D = baidu.body();
		var P = D.scrollTop;
		var M = D.documentWidth || 500;
		var E = D.documentHeight || 500;
		var O = G("shadowDiv");
		var J = G("popDiv");
		var C = G("popContent");
		var I = G("shadowIframe");
		var N = A || parseInt(J.style.width, 10) || 500;
		var F = L || parseInt(J.style.height, 10) || 500;
		O.style.width = M + "px";
		O.style.height = E + "px";
		O.style.left = O.style.top = "0px";
		I.style.width = (M - 20) + "px";
		I.style.height = (E - 20) + "px";
		I.style.top = I.style.left = "5px";
		var K = (D.viewWidth - N) / 2;
		var H = (D.viewHeight - F) / 2 + P;
		if (K < 1) {
			K = P
		}
		if (H < 1) {
			H = "20"
		}
		J.style.left = K + "px";
		J.style.top = H + "px";
		J.style.width = N + "px";
		J.style.height = F + "px";
		C.style.height = (F - 45) + "px";
		G("pop_holder2").style.height = F + "px"
	},
	hide : function() {
		pop.onclosed();
		try {
			baidu.hide("popDiv", "shadowDiv", "shadowIframe");
			G("shadowIframe").style.width = "0px";
			G("shadowIframe").style.height = "0px"
		} catch (B) {
		}
		try {
			G("popIframe").src = "about:blank"
		} catch (A) {
			try {
				document.frames.popIframe.location = "about:blank"
			} catch (A) {
			}
		}
		if (G("readerContainer")) {
			G("readerContainer").style.visibility = "visible"
		}
		baidu.un(window, "resize", pop.resize);
		baidu.un(document, "onkeydown", pop.keyListener)
	},
	keyListener : function(B) {
		B = window.event || B;
		var A = B.which || B.keyCode;
		if (27 == A) {
			pop.hide()
		}
	},
	config : function(F, A) {
		if (F) {
			G("dialogBoxTitle").innerHTML = F
		}
		pop.onclosed = A.onclosed || function() {
		};
		if (A.url) {
			if (A.url != true) {
				try {
					G("popIframe").src = A.url
				} catch (D) {
					try {
						document.frames.popIframe.location = A.url
					} catch (D) {
					}
				}
			}
			G("ifrDiv").style.display = "block";
			baidu.hide("popBody")
		} else {
			if (A.info) {
				baidu.hide("ifrDiv");
				G("popBody").style.display = "block";
				G("popBody").innerHTML = A.info
			}
		}
		var B = A.width || 450;
		var E = A.height || 400;
		var C = G("popDiv");
		C.style.width = B + "px";
		C.style.height = E + "px"
	},
	show : function(B, A) {
		pop.create();
		pop.config(B, A);
		pop.resize();
		G("popDiv").style.display = "block";
		G("shadowDiv").style.display = "block";
		G("shadowIframe").style.display = "";
		baidu.on(window, "resize", pop.resize);
		baidu.on(document, "onkeydown", pop.keyListener);
		if (G("readerContainer")) {
			G("readerContainer").style.visibility = "hidden"
		}
	},
	confirm : function(C, B) {
		pop.onOk = baidu.proxy(function(D) {
			pop.onclosed = function() {
			};
			pop.hide();
			D()
		}, B.ok || pop.hide);
		var A = "<span class='f14'>$2$</span><br><br><p align='center'><input type='button'value='$0$' onclick='pop.onOk();'>&nbsp;&nbsp;&nbsp;&nbsp;<input type='button'value='$1$' onclick='pop.hide()'></p>";
		A = A.format(B.okInfo || "确定", B.cancelInfo || "取消", C);
		pop.show(B.title || "信息确认", {
			info : A,
			width : (B.width || 300),
			height : (B.height || 150),
			onclosed : B.cancel
		})
	}
};
var log = {};
log.imageReq = function(A) {
	var C = "doclog_" + (new Date()).getTime();
	var B = window[C] = new Image();
	B.onload = B.onerror = function() {
		window[C] = null
	};
	B.src = A;
	B = null
};
log.send = function(D, B, A) {
	var C = (new Date()).getTime();
	var E = [ "http://" + location.host + "/tongji/" + D + ".html?type=" + B,
			"t=" + C ];
	if (A) {
		baidu.each(A, function(H, F) {
			E.push(F + "=" + H)
		})
	}
	log.imageReq(E.join("&"))
};
log.nslog = function(B, D, A) {
	var C = (new Date()).getTime();
	var E = [ "http://nsclick.baidu.com/v.gif?pid=112",
			"url=" + encodeURIComponent(B), "type=" + D, "t=" + C ];
	baidu.each(A, function(H, F) {
		E.push(F + "=" + H)
	});
	log.imageReq(E.join("&"))
};
var SHOW_FILTEL = [ "您的账号因为上传了含广告性质的文档而被封禁，暂时不能进行任何操作",
		"您的账号因为上传了大量低质量的文档而被封禁，暂时不能进行任何操作", "您的账号因为涉嫌刷分作弊而被封禁，暂时不能进行任何操作",
		"您的账号因为上传了包含色情、反动等不健康内容的文档而被封禁，暂时不能进行任何操作",
		"您的账号因为上传了包含违规内容的文档而被封禁，暂时不能进行任何操作" ];
login = {
	hide : function(A) {
		if (parent.pop) {
			parent.pop.hide();
			if (A) {
				parent.location.reload(true)
			}
		} else {
			history.go(-1)
		}
	},
	onLoginSuccess : function() {
		window.location.reload(true)
	},
	onLoginFailed : function() {
		window.location = "http://passport.baidu.com/?login&tpl=ik&u="
				+ escape("http://zhidao.baidu.com/")
	},
	defalutLoginSuccess : function() {
		window.location.reload(true)
	},
	verlogin : function(hasLogin, notLogin, patchment) {
		function onOk(req, hasLogin) {
			var res = eval("(" + req.responseText + ")");
			if (res[0]["isLogin"] == "1") {
				if (res[0]["isLock"] == "1") {
					var isNA = (res[0]["lockDay"] == 0) ? "" : "，封禁天数"
							+ res[0]["lockDay"] + "天，请耐心等待解封";
					switch (res[0]["lockReason"]) {
					case "1":
						pop
								.show(
										"提示",
										{
											info : "<p>"
													+ SHOW_FILTEL[0]
													+ isNA
													+ "。</p><br><input type='button' class='pop_btn_short' onclick='pop.hide()' value='确 定'>",
											width : 440,
											height : 270
										});
						return;
						break;
					case "2":
						pop
								.show(
										"提示",
										{
											info : "<p>"
													+ SHOW_FILTEL[1]
													+ isNA
													+ "。</p><br><input type='button' class='pop_btn_short' onclick='pop.hide()' value='确 定'>",
											width : 440,
											height : 270
										});
						return;
						break;
					case "3":
						pop
								.show(
										"提示",
										{
											info : "<p>"
													+ SHOW_FILTEL[2]
													+ isNA
													+ "。</p><br><input type='button' class='pop_btn_short' onclick='pop.hide()' value='确 定'>",
											width : 440,
											height : 270
										});
						return;
						break;
					case "4":
						pop
								.show(
										"提示",
										{
											info : "<p>"
													+ SHOW_FILTEL[3]
													+ isNA
													+ "。</p><br><input type='button' class='pop_btn_short' onclick='pop.hide()' value='确 定'>",
											width : 440,
											height : 270
										});
						return;
						break;
					default:
						pop
								.show(
										"提示",
										{
											info : "<p>"
													+ SHOW_FILTEL[4]
													+ isNA
													+ "。</p><br><input type='button' class='pop_btn_short' onclick='pop.hide()' value='确 定'>",
											width : 440,
											height : 270
										});
						return
					}
				} else {
					if (hasLogin) {
						hasLogin()
					}
					return true
				}
			} else {
				login.log(hasLogin, patchment)
			}
			return false
		}
		baidu.ajax.get("/login?_t=" + (new Date()).getTime(), function(req) {
			onOk(req, hasLogin)
		})
	},
	check : function(hasLogin, notLogin, patchment) {
		function onOk(req, hasLogin, notLogin) {
			var res = eval("(" + req.responseText + ")");
			if (res[0]["isLogin"] == "1") {
				if (hasLogin) {
					hasLogin()
				}
				return true
			}
			if (notLogin) {
				notLogin()
			} else {
				login.log(hasLogin, patchment)
			}
			return false
		}
		baidu.ajax.get("/login?_t=" + (new Date()).getTime(), function(req) {
			onOk(req, hasLogin, notLogin)
		})
	},
	log : function(B, A) {
		if (B) {
			login.onLoginSuccess = login.defalutLoginSuccess
		} else {
			login.onLoginSuccess = login.defalutLoginSuccess
		}
		A = "?" + (A ? A + "&" : "") + "t=" + (new Date()).getTime();
		pop.show("登录", {
			url : "/static/html/userlogin.html" + A,
			width : 400,
			height : 360
		})
	}
};
var cookie = {
	defaultExpires : new Date(Date.parse("Jan 1, 2026")),
	defaultPath : "/",
	defaultDomain : location.host.replace(/:\d+/, ""),
	set : function(F, H, B, C, A, E) {
		var D = F + "=" + encodeURIComponent(H);
		if (B) {
			D += "; expires=" + B.toGMTString()
		}
		if (C) {
			D += "; path=" + C
		}
		if (A) {
			D += "; domain=" + A
		}
		if (E) {
			D += "; secure"
		}
		document.cookie = D
	},
	get : function(C) {
		var B = "(?:; )?" + C + "=([^;]*);?";
		var A = new RegExp(B);
		if (A.test(document.cookie)) {
			return decodeURIComponent(RegExp["$1"])
		} else {
			return null
		}
	},
	del : function(A) {
		cookie.set(A, "", new Date(0))
	}
};
function classTable(F) {
	var B = "classTable";
	var C = null;
	var H = (F == 1) ? false : true;
	function I(N) {
		N = N || B;
		B = N;
		if (typeof class_level_1 == "undefined") {
			return
		}
		var O = "";
		for ( var M = 1; M <= 3; M++) {
			var P = (M == 0) ? "short" : "long";
			if (M > 1) {
				O += '<span class="class_arr icon"></span>'
			}
			O += '<select id="classTableLevel' + M
					+ '" name="class_table_level_' + M + '" size="9" class="'
					+ P + '">';
			O += "</select>"
		}
		baidu.G(N).innerHTML = O;
		A(class_level_1, false, 1, G("classTableLevel1"));
		A(L(class_level_1[0][0], 1), H, 2, G("classTableLevel2"));
		A(L(class_level_2[0][1], 2), H, 3, G("classTableLevel3"));
		C = baidu.GT("span", G("classTable"))[1];
		if (H) {
			baidu.hide("classTableLevel3");
			baidu.hide(C)
		}
		J("classTableLevel1", "classTableLevel2", "classTableLevel3");
		sug_opt.getSugTag()
	}
	function A(N, O, S, R) {
		var Q = 0;
		if (O) {
			R.options[0] = new Option("不选", "0-" + S);
			Q++
		}
		for ( var P = 0; P < N.length; P++) {
			var M = N[P];
			R.options[Q] = new Option(M[M.length - 1], M[M.length - 2] + "-"
					+ S);
			Q++
		}
		R.length = Q;
		R.options[0].selected = true
	}
	function J() {
		var M = arguments;
		baidu.each(M, function(N) {
			baidu.on(N, "change", function() {
				E(this.value)
			})
		})
	}
	function E(M) {
		if (M == "") {
			baidu.hide(P, C);
			sug_opt.getSugTag();
			return false
		}
		var S = M.replace(/-\d+/, "");
		var R = M.replace(/\d+-/, "");
		if (R == 3) {
			sug_opt.getSugTag();
			return false
		}
		var N = parseInt(R) + 1;
		var P = G("classTableLevel" + N);
		var O = L(S, R);
		if (R == 1) {
			var Q = L(O[0][1], R + 1)
		}
		if (!H) {
			setVersion(D()[1])
		}
		if (O && O.length != 0) {
			A(O, H, N, P);
			if (!H && R == 1) {
				A(Q, H, 3, G("classTableLevel3"))
			}
			if (N == 3) {
				baidu.show("classTableLevel3", C)
			} else {
				if (H) {
					G("classTableLevel3").options[0].selected = true;
					baidu.hide("classTableLevel3", C)
				}
			}
		} else {
			G("classTableLevel3").options[0].selected = true;
			baidu.hide(P, C)
		}
		sug_opt.getSugTag()
	}
	function L(Q, O) {
		if (O == 3) {
			return false
		}
		var M = [];
		var P = (O == 1) ? class_level_2 : class_level_3;
		for ( var N = 0; N < P.length; N++) {
			if (Q == P[N][0]) {
				M.push(P[N])
			}
		}
		return M
	}
	function D() {
		var M = [];
		for ( var N = 1; N < 4; N++) {
			var P = G("classTableLevel" + N);
			var O = (P.value != "") ? P.value.replace(/-\d+/, "") : 0;
			M.push(O)
		}
		return M
	}
	function K(Q) {
		var M = Q.split("-");
		for ( var O = 1; O <= M.length; O++) {
			var P = baidu.GT("option", G("classTableLevel" + O));
			for ( var N = 0; N < P.length; N++) {
				if (P[N].value == M[O - 1] + "-" + O) {
					P[N].selected = true;
					E(P[N].value)
				}
			}
		}
	}
	this.create = I;
	this.get = D;
	this.set = K
}
var global = {};
global.myiknow = (function() {
	var C, B, A = 0;
	return function() {
		C = C || baidu.G("usrbar");
		B = B || baidu.G("nav_extra");
		var E = baidu.G("my_home"), F = C.scrollHeight, D = 0;
		do {
			D += E.offsetLeft
		} while (E = E.offsetParent);
		D = D - 6;
		B.style.display = "block";
		B.style.left = D + "px";
		B.style.top = A + "px"
	}
})();
global.statusMsg = function(A) {
	var B = "http://msg.baidu.com/ms?ct=18&cm=3&tn=bmSelfUsrStat&mpn=13227114&un="
			+ A;
	if (typeof redmsg != "undefined") {
		if (!redmsg || redmsg.length < 1) {
			return
		}
		baidu.G("mnum").innerHTML = redmsg
	} else {
		baidu.loadJS(B);
		setTimeout(function() {
			global.statusMsg(A)
		}, 100)
	}
};
global.checkSearchBoxQuery = function() {
	var B = [ "topSearchBox", "bottomSearchBox" ];
	for ( var A = 0; A < B.length; A++) {
		if (baidu.G(B[A])) {
			baidu.on(baidu.G(B[A]), "submit", function(C) {
				if (document[this.name].word.value.trim() == "") {
					pop.show("提示", {
						url : "/static/html/emptySearchQuery.html?form="
								+ this.name,
						width : 440,
						height : 270
					})
				} else {
					this.submit()
				}
				baidu.preventDefault(C)
			})
		}
	}
};
global.go = function(E, D, F) {
	if (document.ftop.word.value.length > 0) {
		var C = E.href;
		var B = encodeURIComponent(document.ftop.word.value);
		if (C.indexOf("q=") != -1) {
			E.href = C.replace(new RegExp("q=[^&$]*"), "q=" + B)
		} else {
			var A = D ? "&" : "?";
			E.href = E.href + A + "q=" + B
		}
	}
	if (F) {
		log.nslog(E.href, "100", {
			to : F
		})
	}
};
function gourl(A) {
	window.open(A)
};