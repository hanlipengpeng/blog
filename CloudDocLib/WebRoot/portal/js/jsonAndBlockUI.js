//��Ʒ����
	$(function() {
		var config = {
	        inputName: "#commodityCode",
	        url: '/EUCMS/common/autoDDAction!searchCommodityCode.action',
	        minChars: 1,
	        max: 20,
	        multiple: false,
	        resultItem: function(data, row, value){
				$('#commodityCode').val('��'+row.result+'��'+row.value);
	        }
//	        ,
//	        formatItem: function(item){ //�б���ʾ��ʽ
//                return item.result+"("+item.value+")";
//            }
	    };
		$.yxautocomplate(config);
	});
	//���ֱ���
	$(function() {
		var config = {
	        inputName: "#currencyCode",
	        url: '/EUCMS/common/autoDDAction!searchCurrencyCode.action',
	        minChars: 1,
	        max: 20,
	        multiple: false,
	        resultItem: function(data, row, value){
			$('#currencyCode').val('��'+row.result+'��'+row.value);
	        }
//	        ,
//	        formatItem: function(item){ //�б���ʾ��ʽ
//                return item.result+"("+item.value+")";
//            }
	    };
		$.yxautocomplate(config);
	});
	//��������
	$(function() {
		var config = {
	        inputName: "#taxkindCode",
	        url: '/EUCMS/common/autoDDAction!searchTaxkindCode.action',
	        minChars: 1,
	        max: 20,
	        multiple: false,
	        resultItem: function(data, row, value){
			$('#taxkindCode').val('��'+row.result+'��'+row.value);
	        }
//	        ,
//	        formatItem: function(item){ //�б���ʾ��ʽ
//                return item.result+"("+item.value+")";
//            }
	    };
		$.yxautocomplate(config);
	});
	//��λ
		$(function() {
		var config = {
	        inputName: "#unitSymbol",
	        url: '/EUCMS/common/autoDDAction!searchUnitSymbol.action',
	        minChars: 1,
	        max: 20,
	        multiple: false,
	        resultItem: function(data, row, value){
			//$('#unitSymbol').val('��'+row.result+'��'+row.value);
	        }
//	        ,
//	        formatItem: function(item){ //�б���ʾ��ʽ
//                return item.result+"("+item.value+")";
//            }
	    };
		$.yxautocomplate(config);
	});
	//���Ҵ���
	$(function() {
		var config = {
	        inputName: "#countryCode",
	        url: '/EUCMS/common/autoDDAction!searchCountryCode.action',
	        minChars: 1,
	        max: 20,
	        multiple: false,
	        resultItem: function(data, row, value){
				$('#countryCode').val('��'+row.result+'��'+row.value);
	        }
//	        ,
//	        formatItem: function(item){ //�б���ʾ��ʽ
//                return item.result;
//            }
	    };
		$.yxautocomplate(config);
	});
	//���õ�������
	$(document).ready(function(){
		//���Ҵ��뵯������
        $('#countryCodePopBtn').click(function(){
            $.blockUI({
            	//������Ƕ��url���������ڴ�С
            	message: '<iframe height="465" width="600" scrolling="no" frameborder="0" src="/EUCMS/basesetting/CountryCodeAction!searchPopCountryCode.action" ></iframe>',
            	css: {
            		top:	'10%',	//�����ϱߵı���
					left:	'10%'	,//������ߵı���
					border:    '0px'
            	},
            	fadeOut:  200,		//����ʱ��
            	fadeIn: 100			//����ʱ��
            });
        });
        //���Ҵ��뵯������
        $('#currencyCodePopBtn').click(function(){
            $.blockUI({
            	//������Ƕ��url���������ڴ�С
            	message: '<iframe height="465" width="600" scrolling="no" frameborder="0" src="/EUCMS/basesetting/CurrencyCodeAction!searchPopCurrencyInfo.action" ></iframe>',
            	css: {
            		top:	'10%',	//�����ϱߵı���
					left:	'10%'	,//������ߵı���
					border:    '0px'
            	},
            	fadeOut:  200,		//����ʱ��
            	fadeIn: 100			//����ʱ��
            });
        });
        //�������ʵ�������
        $('#taxkindCodePopBtn').click(function(){
            $.blockUI({
            	//������Ƕ��url���������ڴ�С
            	message: '<iframe height="465" width="600" scrolling="no" frameBorder=0 src="/EUCMS/basesetting/TaxkindInfoAction!searchPopTaxkindCode.action"></iframe>',
            	css: {
            		top:	'10%',	//�����ϱߵı���
					left:	'10%'	,//������ߵı���
					border:    '0px'
            	},
            	fadeOut:  200,		//����ʱ��
            	fadeIn: 100			//����ʱ��
            });
        });
        //��λ��������
        $('#unitSymbolPopBtn').click(function(){
            $.blockUI({
            	//������Ƕ��url���������ڴ�С
            	message: '<iframe height="465" width="600" scrolling="no" frameBorder=0 src="/EUCMS/basesetting/unitInfoAction!searchPopUnitSymbol.action"></iframe>',
            	css: {
            		top:	'10%',	//�����ϱߵı���
					left:	'10%'	,//������ߵı���
					border:    '0px'
            	},
            	fadeOut:  200,		//����ʱ��
            	fadeIn: 100			//����ʱ��
            });
        });
        //��Ʒ���뵯������
        $('#commodityCodePopBtn').click(function(){
            $.blockUI({
            	//������Ƕ��url���������ڴ�С
            	message: '<iframe height="465" width="600" scrolling="no" frameBorder=0 src="/EUCMS/basesetting/customCommodityAction!searchPopCommodityCode.action"></iframe>',
            	css: {
            		top:	'10%',	//�����ϱߵı���
					left:	'10%',	//������ߵı���
					border:	'0px'
            	},
            	fadeOut:  200,		//����ʱ��
            	fadeIn: 100			//����ʱ��
            });
        });
    });
    
    //���ù��Ҵ���,���رյ�������
    function setCountryCode(code)
    {
    	if(code != undefined)
    	{
    		$("#countryCode").val(code);
    		$("#credenceManagementAction").validate().element(document.getElementById("countryCode"));
    	}
    	$.unblockUI();
    }
    //���ñ��ִ���,���رյ�������
    function setCurrencyCode(code)
    {
    	if(code != undefined)
    	{
    		$("#currencyCode").val(code);
    		$("#credenceManagementAction").validate().element(document.getElementById("currencyCode"));
    	}
    	$.unblockUI();
    }
    //������������,���رյ�������
    function setTaxkindCode(code)
    {
    	if(code != undefined)
    	{
    		$("#taxkindCode").val(code);
    		$("#credenceManagementAction").validate().element(document.getElementById("taxkindCode"));
    	}
    	$.unblockUI();
    }
      //���õ�λ,���رյ�������
    function setUnitSymbol(code)
    {
    	if(code != undefined)
    	{
    		$("#unitSymbol").val(code);
    		$("#credenceManagementAction").validate().element(document.getElementById("unitSymbol"));
    	}
    	$.unblockUI();
    }
    //������Ʒ���룬���رյ�������
    function setCommodityCode(code)
    {
    	if(code != undefined)
    	{
    		$("#commodityCode").val(code);
    		$("#credenceManagementAction").validate().element(document.getElementById("commodityCode"));
    	}
    	$.unblockUI();
    }
    //�رյ�������
    function closeClick()
    {
    	$.unblockUI();
    }