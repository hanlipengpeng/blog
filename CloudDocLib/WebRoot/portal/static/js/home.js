(function(){baidu.more=baidu.more||{};baidu.more.slide=(function(){this.config={width:100,speed:10,auto:true};var K=10;var E=0;var G=0;var N=false;var P=null;var O=null;var X="right";var M,D,C,B,S;var Y,U,K,J;function T(Z){var Z=Z||{};Y=Z.width||config.width;U=Z.speed||config.speed;if(Z.auto===false){J=false}else{J=config.auto}G=D.offsetHeight;if(G>=M.offsetHeight){C.innerHTML=D.innerHTML}}function W(d,b,a,e,Z,c){if(d!=""&&baidu.G(d)){M=baidu.G(d)}else{return }if(b!=""&&baidu.G(b)){D=baidu.G(b)}else{return }if(a!=""&&baidu.G(a)){C=baidu.G(a)}else{return }if(e!=""&&baidu.G(e)){B=baidu.G(e)}else{return }if(Z!=""&&baidu.G(Z)){S=baidu.G(Z)}else{return }T(c);if(G>=M.offsetHeight){baidu.on(B,"mousedown",V);baidu.on(B,"mouseup",H);baidu.on(B,"mouseout",H);baidu.on(S,"mousedown",L);baidu.on(S,"mouseup",A);baidu.on(S,"mouseout",A);if(J){M.onmouseover=function(){clearInterval(O)};M.onmouseout=R;R()}}}function V(){clearInterval(P);if(N){return }if(J){clearInterval(O)}N=true;X="left";P=setInterval(I,U)}function I(){if(M.scrollTop<=0){M.scrollTop=G}M.scrollTop-=K}function H(){if(X=="right"){return }clearInterval(P);if(M.scrollTop%Y!=0){E=-(M.scrollTop%Y);F()}else{N=false}if(J){R()}}function L(){clearInterval(P);if(N){return }if(J){clearInterval(O)}N=true;X="right";Q();P=setInterval(Q,U)}function Q(){if(M.scrollTop>=G){M.scrollTop=0}M.scrollTop+=K}function A(){if(X=="left"){return }clearInterval(P);if(M.scrollTop%Y!=0){E=Y-M.scrollTop%Y;F()}else{N=false}if(J){R()}}function R(){clearInterval(O);O=setInterval(function(){L();A()},3000)}function F(){if(E==0){N=false;return }var Z,a=K;if(Math.abs(E)<Y/2){a=Math.round(Math.abs(E/K));if(a<1){a=1}}if(E<0){if(E<-a){E+=a;Z=a}else{Z=-E;E=0}M.scrollTop-=Z;setTimeout(F,U)}else{if(E>a){E-=a;Z=a}else{Z=E;E=0}M.scrollTop+=Z;setTimeout(F,U)}}return{play:W,autoPlay:R,moveUp:V,moveUpStop:H,moveUpStop:H,moveDown:L,moveDownStop:A,moveDownStop:A}})();baidu.dom.ready(function(){var A={};A.hidden=function(){var G=baidu.q("tab-cate");for(i=0;i<G.length;i++){baidu.dom.removeClass(G[i],"cate-menu-on");baidu.dom.hide(baidu.dom.next(G[i]))}};A.show=function(G){if(!baidu.dom.hasClass(G,"cate-menu-on")){baidu.dom.addClass(G,"cate-menu-on");var H=baidu.dom.next(G);baidu.dom.show(H)}};var C=baidu.q("tab-cate");var E=baidu.q("popup-cate");baidu.array.each(C,function(H,G){baidu.event.on(H,"mouseover",function(J){A.show(H);var I=baidu.dom.next(H);baidu.event.on(I,"mouseover",function(K){if(!baidu.dom.hasClass(baidu.dom.prev(I),"cate-menu-on")){baidu.dom.addClass(baidu.dom.prev(I),"cate-menu-on")}baidu.dom.show(I)});baidu.event.on(I,"mouseout",function(K){baidu.dom.removeClass(baidu.dom.prev(I),"cate-menu-on");baidu.dom.hide(I)})});baidu.event.on(H,"mouseout",function(I){if(D(I,H)){baidu.dom.removeClass(H,"cate-menu-on");baidu.dom.hide(baidu.dom.next(H))}})});function D(I,H){if(I.type!="mouseout"&&I.type!="mouseover"){return false}var G=I.relatedTarget?I.relatedTarget:I.type=="mouseout"?I.toElement:I.fromElement;while(G&&G!=H){G=G.parentNode}return(G!=H)}var B=baidu.q("rank-tab");var F=baidu.q("rank-content");baidu.array.each(baidu.q("rank-tab"),function(H,G){var I=F[G];baidu.array.each(baidu.dom.children(H),function(L,K){var M=baidu.dom.children(I);var J=K;baidu.event.on(L,"mouseover",function(O){var N=baidu.dom.children(L.parentNode);for(K=0;K<N.length;K++){baidu.dom.removeClass(N[K],"tab-on");baidu.hide(M[K])}baidu.dom.addClass(L,"tab-on");baidu.show(M[J])})})})})})();