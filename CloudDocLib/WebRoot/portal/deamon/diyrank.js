function yxlib_diyrank(options){
	var options = options || {};
	this.page = options["page"] || 0;
	this.count = options["count"] || 40;
	this.sort = options["sort"] || "SYMBOL";
	this.order = options["order"] || "asc";
	this.query = options["query"] || "STYPE:EQA";
	this.jobListType = options["jobListType"] || 1;
	this.taskHost = options["taskHost"] || "unknown";
	this.host = options["host"] || "/xng/xngAction.action";
	this.ajaxmode = options["ajaxmode"] || "json";
	var _self = this;
	this.pagecount = 0;
	this.total = 0;
	this.list = [];
	this.taskList = [];
	this.onload = function(sender){
		// 回调函数
	}
	// 载入数据
	this.load = function(){
		var params = {"page":_self.page, "count":_self.count, "sort":_self.sort, "order":_self.order, "query":_self.query,"jobListType":_self.jobListType,"host":_self.taskHost};
		if(_self.ajaxmode=="json"){
			jQuery.getJSON(_self.host, params, function(json){
				// 更新信息
				_self.page = json.page;
				_self.total = json.total;
				_self.count = json.count;
				_self.pagecount = json.pagecount;
				_self.order = json.order;
				_self.list = json.list;
				_self.taskList = json.taskList;
				// 回调
				_self.onload(_self);
			});
		}else if(_self.ajaxmode="callback"){
			
		}
	}
	// 翻页
	this.gotopage = function(page){
		if(page>=0 && page<=_self.pagecount-1){
			_self.page = page;
			this.load();
			return true;
		}else{
			return false;
		}
	}
	// 下一页
	this.next = function(){
		return _self.gotopage(_self.page + 1);
	}
	// 上一页
	this.prev = function(){
		return _self.gotopage(_self.page - 1);
	}
	// 首页
	this.first = function(){
		return _self.gotopage(0);
	}
	// 末页
	this.last = function(){
		return _self.gotopage(_self.pagecount-1);
	}
}

//排序表格
function yxlib_tablesort(tableId, options){
	var _self = this;
	// 将字符串 "code:0600183;attr:price" 转成json对象 {"code":"0600183", "attr":"price"}
	function toMeta(s){
		var serial = s.split(/[:;]/), meta = {};
		for(var j=0; j<serial.length; j+=2)
			meta[serial[j]] = serial[j+1];
		return meta;
	}
	// 排序
	function sortRows(rows, index, order, type){
		_self.elemArray = [];
		for(var i=0; i< rows.length; i++){
			_self.elemArray.push(rows[i]);
			// 处理排序数据
			var value = jQuery(rows[i].cells[index]).text();
			if(type=="number"){
				value = value.replace(/,/g, "");
				if(isNaN(value)) value = Number.NEGATIVE_INFINITY;
			}else if(type=="percent"){
				value = parseFloat(value)/100;
				if(isNaN(value)) value = Number.NEGATIVE_INFINITY;
			}
			rows[i].setAttribute("_ntessortvalue_", value);
		}
		_self.elemArray.sort(function(a, b){
			var left = a.getAttribute("_ntessortvalue_");
			var right = b.getAttribute("_ntessortvalue_");
			if(type=="number" || type=="percent"){
				var l = Number(left), r = Number(right);
				return l < r ? -1 : (l > r ? 1 : 0);
			}else{
				return left < right ? -1 : (left > right ? 1 : 0);
			}
			return 0;
		});
		if(order != "asc"){
			_self.elemArray.reverse();
		}
		var tbody = _self.elemArray[0].parentNode;
		for(var i=0; i<_self.elemArray.length; i++){
			tbody.appendChild(_self.elemArray[i]);
		}
	}
	
	
	this.onclick = function(img, meta){}
	this.elemArray = [];
	this.dataRows = [];
	
	// 初始化
	this.init = function(){
		var ths = jQuery("#"+tableId+">thead th");
		var imgs = jQuery("#"+tableId+">thead img[_ntessort_]");
		
		// 初始化
		imgs.each(function(){
			var img = jQuery(this);
			var th = img.parent("th");
			var meta = toMeta(img.attr("_ntessort_"));
			if(typeof(meta["index"])=="undefined"){
				meta["index"] = ths.index(th);
			}
			meta["type"] =  meta["type"] || "number";
			img.data("meta", meta);
			var order = meta["order"];
			img.attr("src", order=="asc" ? options["imgasc"] : options["imgdesc"]);
		});
		// 委派事件
		imgs.mousedown(function(){
			var img = jQuery(this);
			imgs.each(function(index){
				var obj = jQuery(this);
				var order = obj.data("meta")["order"];
				obj.attr("src", order=="asc" ? options["imgasc"] : options["imgdesc"]);
			});
			var meta = img.data("meta");
			var order = meta["order"];
			img.attr("src", order=="asc" ? options["imgasc2"] : options["imgdesc2"]);
			// 回调
			_self.onclick(img, meta);
		});
	}
	// 排序
	this.dosort = function(img, meta){
		sortRows(_self.dataRows, meta["index"], meta["order"], meta["type"]);
	}
	// 刷新数据行
	this.refresh = function(){
		_self.dataRows = jQuery("#"+tableId+">tbody>tr");
	}
}
