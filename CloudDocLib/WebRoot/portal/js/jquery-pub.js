if (!jQuery) {
	alert('必须先引入jquery.js');
}

(function($) {
	$.yxautocomplate = function(config) {
		var options = {
			multiple : true, // 是否允许多选,如果单选置为false,否则会出现后面多一个","号
			dataType : "json",
			max : 20, // 列表长度
			minChars : 3, // 最小触发输入长度
			parse : function(data) {
				var rows = [];
				if(data && data.codes){
                    data = data.codes;
                    for (var i = 0; i < data.length; i++) {
                        rows[rows.length] = {
                            data : data[i],
                            value : data[i].value,
                            result : data[i].result
                        };
                    }
				}
				return rows;
			},
			formatItem : function(item) { // 列表显示方式
				return item.result + "&nbsp;&nbsp;&nbsp;&nbsp;" + item.value;
			}
		};

		// 将参数传进来的属性合并到options中
		if (config && typeof config == 'object') {
			for (var p in config) {
				options[p] = config[p];
			}
		}

		// 参数不完整
		if (!options.inputName || !options.url || !options.resultItem) {
			return;
		}

		$(options.inputName).autocomplete(options.url, options)
				.result(options.resultItem);
	};
})(jQuery);

/*
 * 通用函数，扩展JQuery。 
 * create: 2010.10.27
 */

jQuery.extend({
	// 格式化字符串
	formatString : function(txt) {
		var str = arguments[0];
		var index = 0;
		for (var i = 1; i < arguments.length; i++) {
			var obj = arguments[i];
			if (typeof(obj) == "object") {
				for (var key in obj) {
					if(obj[key] == null)
					{
						obj[key] = '';
					}
					str = str.replace(new RegExp("\\{" + key + "\\}", "gm"),obj[key]);
				}
			} else {
				str = str.replace(new RegExp("\\{" + (index) + "\\}", "gm"),
						obj);
				index++;
			}
		};
		return str;
	}
});

jQuery.extend({
	// 转换为json 字符串
	toJSON : function(object) {
		var type = typeof object;
		if ('object' == type) {
			if (Array == object.constructor)
				type = 'array';
			else if (RegExp == object.constructor)
				type = 'regexp';
			else
				type = 'object';
		}
		switch (type) {
			case 'undefined' :
			case 'unknown' :
				return;
				break;
			case 'function' :
			case 'boolean' :
			case 'regexp' :
				return object.toString();
				break;
			case 'number' :
				return isFinite(object) ? object.toString() : 'null';
				break;
			case 'string' :
				return '"'
						+ object.replace(/(\\|\")/g, "\\$1").replace(
								/\n|\r|\t/g, function() {
									var a = arguments[0];
									return (a == '\n') ? '\\n' : (a == '\r')
											? '\\r'
											: (a == '\t') ? '\\t' : ""
								}) + '"';
				break;
			case 'object' :
				if (object === null)
					return 'null';
				var results = [];
				for (var property in object) {
					var value = jQuery.toJSON(object[property]);
					if (value !== undefined)
						results.push(jQuery.toJSON(property) + ':' + value);
				}
				return '{' + results.join(',') + '}';
				break;
			case 'array' :
				var results = [];
				for (var i = 0; i < object.length; i++) {
					var value = jQuery.toJSON(object[i]);
					if (value !== undefined)
						results.push(value);
				}
				return '[' + results.join(',') + ']';
				break;
		}
	},
	// 转换为json 对象
	evalJSON : function(strJson) {
		return eval("(" + strJson + ")");
	}

});
