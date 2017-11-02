//商品编码
	$(function() {
		var config = {
	        inputName: "#commodityCode",
	        url: '/EUCMS/common/autoDDAction!searchCommodityCode.action',
	        minChars: 1,
	        max: 20,
	        multiple: false,
	        resultItem: function(data, row, value){
				$('#commodityCode').val('（'+row.result+'）'+row.value);
	        }
//	        ,
//	        formatItem: function(item){ //列表显示方式
//                return item.result+"("+item.value+")";
//            }
	    };
		$.yxautocomplate(config);
	});
	//币种编码
	$(function() {
		var config = {
	        inputName: "#currencyCode",
	        url: '/EUCMS/common/autoDDAction!searchCurrencyCode.action',
	        minChars: 1,
	        max: 20,
	        multiple: false,
	        resultItem: function(data, row, value){
			$('#currencyCode').val('（'+row.result+'）'+row.value);
	        }
//	        ,
//	        formatItem: function(item){ //列表显示方式
//                return item.result+"("+item.value+")";
//            }
	    };
		$.yxautocomplate(config);
	});
	//征免性质
	$(function() {
		var config = {
	        inputName: "#taxkindCode",
	        url: '/EUCMS/common/autoDDAction!searchTaxkindCode.action',
	        minChars: 1,
	        max: 20,
	        multiple: false,
	        resultItem: function(data, row, value){
			$('#taxkindCode').val('（'+row.result+'）'+row.value);
	        }
//	        ,
//	        formatItem: function(item){ //列表显示方式
//                return item.result+"("+item.value+")";
//            }
	    };
		$.yxautocomplate(config);
	});
	//单位
		$(function() {
		var config = {
	        inputName: "#unitSymbol",
	        url: '/EUCMS/common/autoDDAction!searchUnitSymbol.action',
	        minChars: 1,
	        max: 20,
	        multiple: false,
	        resultItem: function(data, row, value){
			//$('#unitSymbol').val('（'+row.result+'）'+row.value);
	        }
//	        ,
//	        formatItem: function(item){ //列表显示方式
//                return item.result+"("+item.value+")";
//            }
	    };
		$.yxautocomplate(config);
	});
	//国家代码
	$(function() {
		var config = {
	        inputName: "#countryCode",
	        url: '/EUCMS/common/autoDDAction!searchCountryCode.action',
	        minChars: 1,
	        max: 20,
	        multiple: false,
	        resultItem: function(data, row, value){
				$('#countryCode').val('（'+row.result+'）'+row.value);
	        }
//	        ,
//	        formatItem: function(item){ //列表显示方式
//                return item.result;
//            }
	    };
		$.yxautocomplate(config);
	});
	//配置弹出窗口
	$(document).ready(function(){
		//国家代码弹出窗口
        $('#countryCodePopBtn').click(function(){
            $.blockUI({
            	//窗口内嵌的url，弹出窗口大小
            	message: '<iframe height="465" width="600" scrolling="no" frameborder="0" src="/EUCMS/basesetting/CountryCodeAction!searchPopCountryCode.action" ></iframe>',
            	css: {
            		top:	'10%',	//距离上边的比例
					left:	'10%'	,//距离左边的比例
					border:    '0px'
            	},
            	fadeOut:  200,		//淡出时间
            	fadeIn: 100			//淡入时间
            });
        });
        //货币代码弹出窗口
        $('#currencyCodePopBtn').click(function(){
            $.blockUI({
            	//窗口内嵌的url，弹出窗口大小
            	message: '<iframe height="465" width="600" scrolling="no" frameborder="0" src="/EUCMS/basesetting/CurrencyCodeAction!searchPopCurrencyInfo.action" ></iframe>',
            	css: {
            		top:	'10%',	//距离上边的比例
					left:	'10%'	,//距离左边的比例
					border:    '0px'
            	},
            	fadeOut:  200,		//淡出时间
            	fadeIn: 100			//淡入时间
            });
        });
        //征免性质弹出窗口
        $('#taxkindCodePopBtn').click(function(){
            $.blockUI({
            	//窗口内嵌的url，弹出窗口大小
            	message: '<iframe height="465" width="600" scrolling="no" frameBorder=0 src="/EUCMS/basesetting/TaxkindInfoAction!searchPopTaxkindCode.action"></iframe>',
            	css: {
            		top:	'10%',	//距离上边的比例
					left:	'10%'	,//距离左边的比例
					border:    '0px'
            	},
            	fadeOut:  200,		//淡出时间
            	fadeIn: 100			//淡入时间
            });
        });
        //单位弹出窗口
        $('#unitSymbolPopBtn').click(function(){
            $.blockUI({
            	//窗口内嵌的url，弹出窗口大小
            	message: '<iframe height="465" width="600" scrolling="no" frameBorder=0 src="/EUCMS/basesetting/unitInfoAction!searchPopUnitSymbol.action"></iframe>',
            	css: {
            		top:	'10%',	//距离上边的比例
					left:	'10%'	,//距离左边的比例
					border:    '0px'
            	},
            	fadeOut:  200,		//淡出时间
            	fadeIn: 100			//淡入时间
            });
        });
        //商品编码弹出窗口
        $('#commodityCodePopBtn').click(function(){
            $.blockUI({
            	//窗口内嵌的url，弹出窗口大小
            	message: '<iframe height="465" width="600" scrolling="no" frameBorder=0 src="/EUCMS/basesetting/customCommodityAction!searchPopCommodityCode.action"></iframe>',
            	css: {
            		top:	'10%',	//距离上边的比例
					left:	'10%',	//距离左边的比例
					border:	'0px'
            	},
            	fadeOut:  200,		//淡出时间
            	fadeIn: 100			//淡入时间
            });
        });
    });
    
    //设置国家代码,并关闭弹出窗口
    function setCountryCode(code)
    {
    	if(code != undefined)
    	{
    		$("#countryCode").val(code);
    		$("#credenceManagementAction").validate().element(document.getElementById("countryCode"));
    	}
    	$.unblockUI();
    }
    //设置币种代码,并关闭弹出窗口
    function setCurrencyCode(code)
    {
    	if(code != undefined)
    	{
    		$("#currencyCode").val(code);
    		$("#credenceManagementAction").validate().element(document.getElementById("currencyCode"));
    	}
    	$.unblockUI();
    }
    //设置征免性质,并关闭弹出窗口
    function setTaxkindCode(code)
    {
    	if(code != undefined)
    	{
    		$("#taxkindCode").val(code);
    		$("#credenceManagementAction").validate().element(document.getElementById("taxkindCode"));
    	}
    	$.unblockUI();
    }
      //设置单位,并关闭弹出窗口
    function setUnitSymbol(code)
    {
    	if(code != undefined)
    	{
    		$("#unitSymbol").val(code);
    		$("#credenceManagementAction").validate().element(document.getElementById("unitSymbol"));
    	}
    	$.unblockUI();
    }
    //设置商品编码，并关闭弹出窗口
    function setCommodityCode(code)
    {
    	if(code != undefined)
    	{
    		$("#commodityCode").val(code);
    		$("#credenceManagementAction").validate().element(document.getElementById("commodityCode"));
    	}
    	$.unblockUI();
    }
    //关闭弹出窗口
    function closeClick()
    {
    	$.unblockUI();
    }