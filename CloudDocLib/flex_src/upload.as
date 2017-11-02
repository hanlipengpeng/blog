// ActionScript file
import flash.events.*;
import flash.net.FileReference;
import flash.net.URLRequest;

import mx.collections.ArrayCollection;
import mx.containers.GridItem;
import mx.containers.GridRow;
import mx.containers.HBox;
import mx.containers.VBox;
import mx.controls.Alert;
import mx.controls.Image;
import mx.controls.Label;
import mx.controls.ProgressBar;
import mx.controls.Spacer;
import mx.events.IndexChangedEvent;
import mx.rpc.Fault;
import mx.rpc.events.FaultEvent;
import mx.rpc.events.ResultEvent;

private var file: FileReference;

private var fileList: FileReferenceList = new FileReferenceList();
//允许上传的文件的类型数组 
private var allowFileTypes : Array;
//最大上传文件数
private var maxFiles:int=10;
//最大上传文件大小(M)
private var maxSize:Number=64;
//文件上传请求对象 
private var requestUrl:String = null;
private var request: URLRequest = null;

//文件集                
[Bindable]
private var files :ArrayCollection = new ArrayCollection();
private var fileTmpObj :Object;
//当前选中的行
private var currentSelectedRowIndex :Number = -1;
//上传完的文件在files中的位置
private var uploadSuccessFileIndex : Number = -1;
//上传成功文件数
private var uploadSuccessNum:Number = 0;
//上传失败文件数
private var uploadFailureNum:Number = 0;
//正在上传文件
private var uploadingNum:Number = 0;
//文件名称最大长度
private var _fileNameMaxLength:Number = 50;

/**
 * 获取文件上传地址
 */ 
private function getUri():String
{
	var url:String = ExternalInterface.call('getURi');
	return url;
}

/**
 * 调用js的函数调整swf的高度
 */ 
private function setFlashHeight(height:Number):void
{
	ExternalInterface.call('setFlashHeight','upload',height);
}
/**
 * 掉用js方法增加文档信息表单
 */ 
private function addDocFileForm(type:Number,index:Number,title:String):void
{
	ExternalInterface.call('addFileForm',type,index,title);
}
/**
 * 文件上传成功后，更新文件对应的填写文档细信息的文档id
 * 
 */ 
private function updateHdfsFileId(index:Number,id:Number,status:String):void
{
	ExternalInterface.call('updateFileId',index,id,status);
}
/**
 * 填写文档信息快速定位
 */ 
private function locateFormById(index:Number):void
{
	ExternalInterface.call('locateFormById',index);
}
/**
 * 移除放弃了的文件的对应form
 */ 
private function removeJSPForm(index:Number):void
{
	ExternalInterface.call('removeFormById',index);
}
/**
 * 如果在第三个viewStack中删除文件时出现文件数为零时，关闭当前页面。
 */ 
private function pageClose():void
{
	if(files.length==0)
	{
		Application.application.height = 100;
		setFlashHeight(100);
		ExternalInterface.call('page_close');
	}
}
/**
 * 初始化信息
 */ 
private function init(): void
{
	Security.allowDomain("*");
	
	var url:String = Application.application.url;
    requestUrl = url.substr(0,url.indexOf("flex_app")) + "FileUploaded";
    //注册文件选择事件
	fileList.addEventListener(Event.SELECT, onSelect);
	//注册viewStack面板切换事件
	viewStack.addEventListener(IndexChangedEvent.CHANGE,refreshViewStack);
	
	/**
	 *  初始化允许上传的文件类型
	 */
    var allTypes:FileFilter = new FileFilter("所有文档（远行文库支持类型）", "*.doc;*.docx;*.ppt;*.pptx;*.xls;*.xlsx;*.txt;*.pdf;");
    var wordTypes:FileFilter = new FileFilter("MS word文档", "*.doc;*.docx");
    var pptTypes:FileFilter = new FileFilter("MS ppt文档", "*.ppt;*.pptx");
    var xlsTypes:FileFilter = new FileFilter("MS xls文档", "*.xls;*.xlsx");
    var pdfTypes:FileFilter = new FileFilter("PDF", "*.pdf");
    var txtTypes:FileFilter = new FileFilter("纯文本", "*.txt");
    allowFileTypes = new Array(allTypes, wordTypes, pptTypes, xlsTypes, pdfTypes, txtTypes);
}

private function callSessionServlet()
{
    jspServlet.request.scope = "portalSession";
    jspServlet.send(); 
}
            
private function servletResultHandler(event:ResultEvent):void{     
    var userId = event.result as String;
    
    if(userId==null || userId=='' || userId=='undefined')
    {
    	Alert.show("请用户登录系统","提示");
    	return;
    }
    
    //requestUrl = getUri();
    request = new URLRequest(requestUrl + "?id=" + userId);
    
    for(var i:int = 0 ;i<files.length;i++)
    {
        var flag :String= String(files[i].hasUploadFlag);
        
        if(flag=='' || flag==null || flag=='undefined')
        {
            //设置取消按钮可用
            Button(GridItem(GridRow(uploadFileGrid.getChildAt(i)).getChildAt(1)).getChildAt(1)).enabled = true;
            file = FileReference(files[i].fileReference);
            file.addEventListener(DataEvent.UPLOAD_COMPLETE_DATA,fileUploadCompletedHandle);
            file.upload(request);
            break;
        }
    }
}

private function servletFaultHandler(event:FaultEvent):void{     
    var fault:Fault = event.fault;     
    var s:String = (fault.faultDetail!=null) ? fault.faultDetail : fault.faultString;
    Alert.show(s,'提示');   
}

/**
 * 文件上传成功后的处理
 * 1、根据上传的结果改变行的现实
 * 2、上传下一个文件
 */ 
private function fileUploadCompletedHandle(e:DataEvent):void
{
	var f :FileReference = FileReference(e.target) ;
	//Alert.show("uploadCompleteData:"+e.data.toString());
	var result:String = e.data.toString();
	
	for(var i:int = 0 ;i<files.length;i++)
	{
		var fileName = FileReference(files[i].fileReference).name;
		var errorMsg :String;
		if(f.name == fileName)
		{
			uploadSuccessFileIndex = i;
			files[i].hasUploadFlag = 'Y';
			
			if(result=='文件已存在' || result=='未知异常')
			{
				errorMsg = "    抱歉，您的文档附件与已有文档重复，我们建议您分享其他文档。";
				//上传失败的处理
				fileUploadFailure(uploadSuccessFileIndex,errorMsg);
				//调用js方法设置文档的详细设置表单不可用
				updateHdfsFileId(files[i].index,Number(result),'N');
			}
			else if(result=='文件名称过长')
			{
				errorMsg = "    抱歉，您的文档附件文件名称过长，我们建议您修改文档名称。";
				//上传失败的处理
				fileUploadFailure(uploadSuccessFileIndex,errorMsg);
				//调用js方法设置文档的详细设置表单不可用
				updateHdfsFileId(files[i].index,Number(result),'N');
			}
			else
			{
				//上传成功的处理
				fileUploadSuccess(uploadSuccessFileIndex);
				//调用js方法设置文件的id
				updateHdfsFileId(files[i].index,Number(result),'Y');
			}
			
			uploadFile();
		    break;
		}
	}
}
/**
 * 添加文件处理
 */ 
public function addFiles(e:Event): void
{
    try
    {
		var success:Boolean = fileList.browse(this.allowFileTypes);
    }
    catch (error:Error)
    {
    	trace("Unable to browse for files."+error.toString());
    }
}

/**
 * 文件按钮选择处理
 */
private function onSelect(e: Event): void
{
	if(currentSelectedRowIndex==-1)
	{
		//如果是第一次切换则，直接界面切换。在viewstack的监听事件中处理增加行的操作。
		if(viewStack.selectedIndex==0)
		{
			viewStack.selectedIndex=1;
		}
		else
		{
			var errorMsg:String = '';
			for each(var file:FileReference in fileList.fileList)
			{
				//判断结果集中是否满10个文件
				if(files.length>=10)
				{
					break;
				}
				
				var errorFlag:Boolean = false;
				
				if(file.name.length>_fileNameMaxLength)
				{
					//Alert.show("文件名称长度不能大于50","提示");
					errorMsg = errorMsg + file.name.substr(0,10) + "... :名称长度不能大于50";
					errorFlag = true;
				}
				
				
				var flag:Boolean = checkSelctedFile(file);
				var o :Object = new Object();
				o.fileType = file.type;
				/*
				* 如果已存在在文件列表中，则不作处理
				*/
				if(flag)
				{
					continue;
				}
				else
				{
					o.fileName = file.name;
					var fileSize :Number = Math.round(file.size);
					
					if(fileSize>this.maxSize*1024*1024)
					{
						//Alert.show("附件的大小不能大于64M","提示");
						if(errorFlag)
						{
							errorMsg = errorMsg + ";大小不能大于64M\n";
						}
						else
						{
							errorMsg = file.name + ":大小不能大于64M\n";
							errorFlag = true;
						}
					}
					else
					{
						if(errorMsg)
						{
							errorMsg = errorMsg + "\n";
						}
					}
					
					if(errorFlag)
					{
						continue;
					}
					/*
					* 文件小于1M，显示K单位。
					*/
					if(fileSize/1024<1024)
					{
						o.fileSize =(fileSize/(1024)).toString().slice(0,4)+"K";
					}
					else
					{
						o.fileSize = (fileSize/(1024*1024)).toString().slice(0,4)+"M";
					}
					
					o.fileReference = file;
					o.index = files.length;
					files.addItem(o);
					
					fileTmpObj = o ;
					addRow(o);
				}
			}
			
			if(errorMsg!='')
			{
				Alert.show(errorMsg,'提示');	
			}
			//刷新文件大小统计信息
			refreshText();
		}
	}
	else if(currentSelectedRowIndex!=-1)
	{
		uploadOtherFile();
	}
	
	this.dispatchEvent(new Event("fileSize_change"));
}

/**
 * 校验选择的文件是否已在文件列表中
 */ 
private function checkSelctedFile(f:FileReference):Boolean
{
	for(var i:int = 0 ;i<files.length;i++)
	{
		var tmp:FileReference = FileReference(files[i].fileReference);
		if(tmp.name==f.name)
		{
			return true;
		}
	}
	
	return false;
}



/**
 * 文件上传事件处理
 * 1、上传标记为未上传的文件
 */
private function uploadFile():void
{
	try
	{
	    callSessionServlet();
	}
	catch (error:Error)
	{
		trace("上传失败");
	}
}
/**
 * 初始化点击上传按钮后的上传界面
 */ 
private function initFileUpload():void
{
	var docAddFormType:Number = -1;
	if(files.length==1)
	{
		docAddFormType = 1;
	}
	
	if(files.length>1)
	{
		docAddFormType = 2;
	}
	
	for(var i:int = 0 ;i<files.length;i++)
	{
		files[i].index = i;
		initUploadFileGrid(FileReference(files[i].fileReference));
		addDocFileForm(docAddFormType,i,FileReference(files[i].fileReference).name);
	}
	
	var height = files.length*42+92;
	Application.application.height = height;
	setFlashHeight(height);
	
	//数据显示完成后，开始上传数据；
  	uploadFile();
  	initUploadResultMsg();
}
/**
 * 初始化文件上传结果显示信息
 */ 
private function initUploadResultMsg():void
{
	uploadSuccessNum =0;
	uploadingNum = files.length;
	uploadFailureNum = 0;
	uploadingNumId.text = "正在上传："+uploadingNum+"份";
	uploadSuccessNumId.text = "成功上传："+uploadSuccessNum+"份";
	uploadFailureNumId.text = "失败上传："+uploadFailureNum+"份";
}
/**
 * 刷新文件上传结果信息
 */ 
private function refreshUploadResultMsg():void
{
	uploadSuccessNumId.text = "成功上传："+uploadSuccessNum+"份";
	uploadingNumId.text = "正在上传："+((files.length-uploadSuccessNum-uploadFailureNum)>0?(files.length-uploadSuccessNum-uploadFailureNum):0)+"份";
	uploadFailureNumId.text = "失败上传："+uploadFailureNum+"份";
}
/**
 * 点击上传按钮事件处理
 * 
 */ 
private function uploadFiles():void
{
	if(files.length==0)
	{
		Alert.show('请选择要上传的文件','提示');
		return;	
	}
	
	this.viewStack.selectedIndex = 2;
}
/**
 * 上传文件按钮点击事件处理
 * 
 */ 
private function btnUploadClick(): void
{
	
    try
    {
		var success:Boolean = fileList.browse(allowFileTypes);
    }
    catch (error:Error)
    {
        trace("Unable to browse for files."+error.toString());
    }
}

/**
 * 更新文件统计文本信息
 */ 
public function refreshText():void
{
	//重新统计附件大小，将原来的信息清0
	var fileSize:Number=0;
	for(var i:int=0;i<files.length;i++)
	{
		fileSize+=Math.round(FileReference(files[i].fileReference).size);
	}
	
	if(fileSize/1024<1024)
	{
		msgFileNum.text = files.length+"份";
		msgFileSize.text = (fileSize/(1024)).toString().slice(0,4)+"K";
	}
	else
	{
		msgFileNum.text = files.length+"份";
		msgFileSize.text = (fileSize/(1024*1024)).toString().slice(0,4)+"M";
	}
}

/**
 * 刷新文件统计文本信息
 */ 
private function refreshViewStack(e:Event):void
{
	if(viewStack.selectedIndex==1)
	{
		//addRow(fileTmpObj);
		var errorMsg:String = '';
		for(var i:Number=0;i<fileList.fileList.length;i++)
		{
			var file :FileReference = fileList.fileList[i];
			var errorFlag:Boolean = false;
			//判断结果集中是否满10个文件
			if(files.length>=10)
			{
				break;
			}
			if(file.name.length>_fileNameMaxLength)
			{
				//Alert.show("文件名称长度不能大于50","提示");
				errorMsg = errorMsg + file.name.substr(0,10) + "... :名称长度不能大于50";
				errorFlag = true;
			}
			
			var flag:Boolean = checkSelctedFile(file);
			var o :Object = new Object();
			o.fileType = file.type;
			/*
			* 如果已存在在文件列表中，则不作处理
			*/
			if(flag)
			{
				continue;
			}
			else
			{
				o.fileName = file.name;
				var fileSize :Number = Math.round(file.size);
				
				if(fileSize>this.maxSize*1024*1024)
				{
					//Alert.show("请上传大小小于64M的附件","提示");
					if(errorFlag)
					{
						errorMsg = errorMsg + ";大小不能大于64M\n";
					}
					else
					{
						errorMsg = file.name + ":大小不能大于64M\n";
						errorFlag = true;
					}
				}
				else
				{
					if(errorMsg)
					{
						errorMsg = errorMsg + "\n";
					}
				}
				
				if(errorFlag)
				{
					continue;
				}
				
				/*
				* 文件小于1M，显示K单位。
				*/
				if(fileSize/1024<1024)
				{
					o.fileSize =(fileSize/(1024)).toString().slice(0,4)+"K";
				}
				else
				{
					o.fileSize = (fileSize/(1024*1024)).toString().slice(0,4)+"M";
				}
				
				o.fileReference = file;
				files.addItem(o);
				fileTmpObj = o ;
				addRow(o);
			}
		}
		
		if(errorMsg!='')
		{
			Alert.show(errorMsg,'提示');
		}
		
		//刷新文件大小统计信息
		refreshText();
	}
	
	if(viewStack.selectedIndex==2)
	{
		initFileUpload();
	}
	
	if(viewStack.selectedIndex==0)
	{
		var height = 200;
		Application.application.height = height;
		setFlashHeight(height);
	}
}
/**
 * 添加文件时，添加行。
 */ 
private function addRow(o:Object):void
{
	if(o==null)
	{
		return ;	
	}
	var row:GridRow = new GridRow();
	row.styleName = "UploadTabList";
	row.setStyle("verticalAlign","middle");
  	row.height = 34;
    var fileNameItem:GridItem = new GridItem();
    fileNameItem.setStyle("paddingLeft",20);
    fileNameItem.setStyle("verticalAlign","middle");
    //设置无滚动条
    fileNameItem.verticalScrollPolicy = "off";
    fileNameItem.horizontalScrollPolicy = "off";
    fileNameItem.width = 380;
   
    //<mx:Image source="themes/skin_images/doc_ico/tword.png" />
    //先添加图标
    var img :Image = new Image();
    
    img.source="../../flex_app/themes/skin_images/doc_ico/t"+o.fileType+".png";
    //添加文件名
    var fileNameLable:Label = new Label();
    fileNameLable.text = o.fileName;
    fileNameLable.setStyle("fontWeight","bold");
    fileNameLable.setStyle("verticalAlign","middle");

 	fileNameItem.addChild(img);
    fileNameItem.addChild(fileNameLable);
    row.addChild(fileNameItem);
    //文件大小
    var fileSizeItem:GridItem = new GridItem();
  	fileSizeItem.setStyle("verticalAlign","middle");
  	fileSizeItem.setStyle("horizontalAlign","center");
  	fileSizeItem.width = 150;
    var fileSizeLabel:Label = new Label();
    fileSizeLabel.text = o.fileSize;
    fileSizeLabel.setStyle("textAlign","center");
    
    fileSizeItem.addChild(fileSizeLabel);
    row.addChild(fileSizeItem);
    //操作
    var operItem:GridItem = new GridItem();
    operItem.setStyle("verticalAlign","middle");
    operItem.setStyle("horizontalAlign","center");
    operItem.width = 137;
  	//删除按钮
    var btn:Button = new Button();
    btn.styleName = "DocDeleteBut";
    btn.height = 16;
    btn.width = 16;
    btn.label = "";
 	btn.addEventListener(MouseEvent.CLICK,function (e:MouseEvent):void{
 				var tRow = GridRow(Button(e.target).parent.parent);
 				
 				var index = fileGrid.getChildIndex(tRow);
 				if(files.length>1)
 				{
 					files.removeItemAt(index-1);
 					fileGrid.removeChild(tRow);
	 				var height = files.length*36+189;
			      	Application.application.height = height;
			      	//Alert.show("点击上传后的高度："+height);
	  				setFlashHeight(height);
	 				refreshText();
 				}
 				else
 				{
 					files.removeItemAt(index-1);
 					fileGrid.removeChild(tRow);
	 				var height = files.length*36+189;
			      	Application.application.height = height;
			      	//Alert.show("点击上传后的高度："+height);
	  				setFlashHeight(height);
	 				refreshText();
 					viewStack.selectedIndex=0;
 				}
 				
 			});
 			
    operItem.addChild(btn);
    row.addChild(operItem);
  	fileGrid.addChild(row);
  	o = null;
  	//计算表格高度
  	var height = files.length*36+189;
	Application.application.height = height;
  	//Alert.show("点击上传后的高度："+height);
  	setFlashHeight(height);
}
/**
 * 文件上传前，初始化文件上传表格。
 */ 
private function initUploadFileGrid(f:FileReference):void
{
	if(f==null)
	{
		return ;
	}
	var row:GridRow = new GridRow();
  	row.height = 40;
  	//row.enabled = false;
  	row.styleName='UploadTabList';
  	
    var item:GridItem = new GridItem();
  	item.width = 380;
  	item.setStyle("verticalAlign","middle");
  	item.setStyle("paddingLeft",20);
  	//设置无滚动条
    item.verticalScrollPolicy = "off";
    item.horizontalScrollPolicy = "off";
    //item.setStyle("horizontalAlign","center");
    //先添加图标
    var img :Image = new Image();
    img.source="../../flex_app/themes/skin_images/doc_ico/t"+f.type+".png";
    var fileNameLabel:Label = new Label();
    fileNameLabel.text = f.name;
    fileNameLabel.setStyle("fontWeight","bold");
    fileNameLabel.setStyle("verticalAlign","middle");
 
 	item.addChild(img);
    item.addChild(fileNameLabel);
    row.addChild(item);
    
    var item:GridItem = new GridItem();
    item.setStyle("verticalAlign","middle");
    item.setStyle("horizontalAlign","left");
  	item.width = 289;
    var bar:ProgressBar = new ProgressBar();
    bar.source = f;
    bar.minimum = 0;
    bar.maximum = 100;
    bar.percentWidth = 65;
    bar.labelPlacement = "center";
    bar.label = "%3%%";
//    bar.addEventListener(Event.COMPLETE,
//    		function (e:ProgressEvent):void{
//    			if(e.bytesLoaded/e.bytesTotal>0.95)
//		    	{
//		    		Button(GridItem(GridRow(ProgressBar(e.target).parent.parent).getChildAt(1)).getChildAt(1)).enabled = false;
//		    	}
//	});
    //增加监听处理，当文件上传超过95%时不可操作。
    bar.addEventListener(ProgressEvent.PROGRESS,
    		function (e:ProgressEvent):void{
    	if(e.bytesLoaded/e.bytesTotal>0.95)
    	{
    		Button(GridItem(GridRow(ProgressBar(e.target).parent.parent).getChildAt(1)).getChildAt(1)).enabled = false;
    	}
    });
    
    item.addChild(bar);
    var btn:Button = new Button();
    btn.label = "取消上传";
    btn.height = 21;
   	btn.width = 66;
    btn.styleName="OperatButton";
    btn.enabled = true;
    btn.addEventListener(MouseEvent.CLICK,function (e:MouseEvent):void{
 				var tRow :GridRow = GridRow(Button(e.target).parent.parent);
 				var index :Number= uploadFileGrid.getChildIndex(tRow);
 				if(files.length>0)
 				{
 					FileReference(files[index].fileReference).cancel();
 					files[index].hasUploadFlag = 'cancel';
 					uploadFile();
 				}
 				//取消之后切换按钮
 				var row:GridRow = new GridRow();
 				row.styleName='UploadTabList';
 				
		      	row.height = 40;
		      	
		        var item:GridItem = new GridItem();
		        item.setStyle("paddingLeft",20);
			    item.setStyle("verticalAlign","middle");
			    item.setStyle("horizontalAlign","left");
			    //设置无滚动条
			    item.verticalScrollPolicy = "off";
    			item.horizontalScrollPolicy = "off";
		  		item.width = 380;
		  		//先添加图标
			    var img :Image = new Image();
			    img.source="../../flex_app/themes/skin_images/doc_ico/t"+f.type+".png";
		        var fileNameLabel:Label = new Label();
		        fileNameLabel.text = f.name;
		        fileNameLabel.setStyle("fontWeight","bold");
			    fileNameLabel.setStyle("verticalAlign","middle");
		     	
		     	item.addChild(img);
		        item.addChild(fileNameLabel);
		        row.addChild(item);
		        var item:GridItem = new GridItem();
		        item.setStyle("verticalAlign","middle");
			    item.setStyle("horizontalAlign","center");
  				item.width = 289;
		        var guBtn:Button = new Button();
   				guBtn.label = "放弃上传";
   				guBtn.height = 21;
   				guBtn.width = 66;
   				guBtn.styleName="OperatButton";
   				guBtn.addEventListener(MouseEvent.CLICK,function (e:Event):void{
   					var tRow :GridRow = GridRow(Button(e.target).parent.parent);
     				var index :Number= uploadFileGrid.getChildIndex(tRow);
				  	//移除当前文件对应的js表单
				  	removeJSPForm(files[index].index);
				  	
     				if(files.length>0)
     				{
     					FileReference(files[index].fileReference).cancel();
     					files.removeItemAt(index);
     				}
     				uploadFileGrid.removeChildAt(index);
     				//刷新上传结果
				  	refreshUploadResultMsg();
				  	//判断是否要关闭当前页面
				  	pageClose();
   				});
   				var reBtn:Button = new Button();
   				reBtn.label = "重新上传";
   				reBtn.height = 21;
   				reBtn.width = 66;
   				reBtn.styleName="OperatButton";
   				reBtn.addEventListener(MouseEvent.CLICK,reUploadFile);
   				var otBtn:Button = new Button();
   				otBtn.label = "上传其他";
   				otBtn.height = 21;
   				otBtn.width = 66;
   				otBtn.styleName="OperatButton";
   				otBtn.addEventListener(MouseEvent.CLICK,function (e:Event):void{
   					var tRow :GridRow = GridRow(Button(e.target).parent.parent);
     				var index :Number= uploadFileGrid.getChildIndex(tRow);
     				currentSelectedRowIndex = index;
     				addFiles(e);
     				return ;
   				});
   				item.addChild(guBtn);
   				item.addChild(reBtn);
   				item.addChild(otBtn);
   				row.addChild(item);
   				
   				//移除原来的children后添加新生成的行
   				uploadFileGrid.removeChildAt(index);
   				uploadFileGrid.addChildAt(row,index);
 				
 			});
 
    item.addChild(btn);
    row.addChild(item);
  	uploadFileGrid.addChild(row);
}

/**
 * 重新上传事件处理
 */ 
private function reUploadFile(e:Event):void
{
	var tRow :GridRow = GridRow(Button(e.target).parent.parent);
 	var index :Number= uploadFileGrid.getChildIndex(tRow);
 	var f : FileReference = FileReference(files[index].fileReference);
 	//重置当前文件的上传状态
 	if(files.length>0)
 	{
 		files[index].hasUploadFlag = '';
 	}
 	//移除当前行
 	uploadFileGrid.removeChildAt(index);
 	//添加状态变更后的行
 	var row:GridRow = new GridRow();
 	row.styleName = "UploadTabList";
	row.setStyle("verticalAlign","middle");
  	row.height = 40;
  	
    var fileNameItem:GridItem = new GridItem();
    fileNameItem.setStyle("paddingLeft",20);
    fileNameItem.setStyle("verticalAlign","middle");
    fileNameItem.setStyle("horizontalAlign","left");
    //设置无滚动条
    fileNameItem.verticalScrollPolicy = "off";
    fileNameItem.horizontalScrollPolicy = "off";
  	fileNameItem.width = 380;
    var fileNameLabel:Label = new Label();
    fileNameLabel.text = f.name;
    fileNameLabel.setStyle("fontWeight","bold");
    fileNameLabel.setStyle("verticalAlign","middle");
 
    fileNameItem.addChild(fileNameLabel);
    row.addChild(fileNameItem);
    
    //进度条
    var barItem:GridItem = new GridItem();
    barItem.setStyle("verticalAlign","middle");
    barItem.setStyle("horizontalAlign","left");
  	barItem.width = 289;
    var bar:ProgressBar = new ProgressBar();
    bar.source = f;
    bar.minimum = 0;
    bar.maximum = 100;
    bar.percentWidth = 65;
    bar.labelPlacement = "center";
    bar.label = "%3%%";
    //增加监听处理，当文件上传超过95%时不可操作。
    bar.addEventListener(ProgressEvent.PROGRESS,
    		function (e:ProgressEvent):void{
    	if(e.bytesLoaded/e.bytesTotal>0.95)
    	{
    		Button(GridItem(GridRow(ProgressBar(e.target).parent.parent).getChildAt(1)).getChildAt(1)).enabled = false;
    	}
    });
    barItem.addChild(bar);
    //取消按钮
    var cBtn:Button = new Button();
    cBtn.label = "取消上传";
    cBtn.height = 21;
   	cBtn.width = 66;
    cBtn.styleName="OperatButton";
    cBtn.addEventListener(MouseEvent.CLICK,cancelFileUpload);
 	
    barItem.addChild(cBtn);
    row.addChild(barItem);
    
  	uploadFileGrid.addChildAt(row,index);
  	
  	/**
  	 * 判断是否要立即上传当前文件
  	 */ 
  	var flag:Boolean = false;
  	for(var i:int = 0 ;i<files.length;i++)
	{
		//只要不是当前文件，且状态不是为已上传和被取消；则不立即上传当前文件
		if(FileReference(files[i].fileReference).name!=f.name)
		{
			if((files[i].hasUploadFlag != 'Y' || files[i].hasUploadFlag != 'cancel'))
			{
				flag = false;
			}
			else
			{
				flag = true;
				return;
			}
		}
	}
	
	//如果选择文件后，前面所有的文件都上传完了，则立即上传当前选中的文件。
	if(!flag)
	{
		this.uploadFile();
	}
	
}
/**
 * 取消文件上传
 */ 
private function cancelFileUpload(e:Event):void
{
	var tRow :GridRow = GridRow(Button(e.target).parent.parent);
 	var index :Number= uploadFileGrid.getChildIndex(tRow);
 	var f : FileReference = FileReference(files[index].fileReference);
 	if(files.length>0)
 	{
 		FileReference(files[index].fileReference).cancel();
 	}
 	//取消之后切换按钮
 	var row:GridRow = new GridRow();
 	row.styleName="UploadTabList";
  	row.height = 40;
  	
    var fileNameItem:GridItem = new GridItem();
    fileNameItem.setStyle("paddingLeft",20);
    fileNameItem.setStyle("verticalAlign","middle");
    fileNameItem.setStyle("horizontalAlign","left");
    //设置无滚动条
    fileNameItem.verticalScrollPolicy = "off";
    fileNameItem.horizontalScrollPolicy = "off";
  	fileNameItem.width = 380;
    var fileNameLabel:Label = new Label();
    fileNameLabel.text = f.name;
    fileNameLabel.setStyle("fontWeight","bold");
    fileNameLabel.setStyle("verticalAlign","middle");
    //设置无水平滚动条
    fileNameLabel.setStyle("horizontalScrollPolicy","off");
 
    fileNameItem.addChild(fileNameLabel);
    row.addChild(fileNameItem);
    var item:GridItem = new GridItem();
    item.setStyle("verticalAlign","middle");
    item.setStyle("horizontalAlign","center");
  	item.width = 289;
    var guBtn:Button = new Button();
   	guBtn.label = "放弃上传";
   	guBtn.height = 21;
   	guBtn.width = 66;
   	guBtn.styleName="OperatButton";
   	guBtn.addEventListener(MouseEvent.CLICK,function (e:Event):void{
   		var tRow :GridRow = GridRow(Button(e.target).parent.parent);
 		var index :Number= uploadFileGrid.getChildIndex(tRow);
 		//移除当前文件对应的js表单
	  	removeJSPForm(files[index].index);
 		if(files.length>0)
 		{
 			FileReference(files[index].fileReference).cancel();
 			files.removeItemAt(index);
 		}
 		uploadFileGrid.removeChildAt(index);
 		//减少高度
 		var height = Application.application.height-42;
 		Application.application.height = height;
  		setFlashHeight(height);
  		//刷新上传结果
	  	refreshUploadResultMsg();
	  	//判断是否要关闭当前页面
		pageClose();
   	});
   	var reBtn:Button = new Button();
   	reBtn.label = "重新上传";
   	reBtn.height = 21;
   	reBtn.width = 66;
   	reBtn.styleName="OperatButton";
   	reBtn.addEventListener(MouseEvent.CLICK,reUploadFile);
   	var otBtn:Button = new Button();
   	otBtn.label = "上传其他";
   	otBtn.height = 21;
   	otBtn.width = 66;
   	otBtn.styleName="OperatButton";
   	otBtn.addEventListener(MouseEvent.CLICK,function (e:Event):void{
   		var tRow :GridRow = GridRow(Button(e.target).parent.parent);
 		var index :Number= uploadFileGrid.getChildIndex(tRow);
 		currentSelectedRowIndex = index;
 		addFiles(e);
 		return ;
   	});
   	item.addChild(guBtn);
   	item.addChild(reBtn);
   	item.addChild(otBtn);
   	row.addChild(item);
   	
   	///移除原先行然后添加新增加的行
   	uploadFileGrid.removeChildAt(index);
   	uploadFileGrid.addChildAt(row,index);
   	
}

/**
 * 上传其他处理
 */ 
private function uploadOtherFile():void
{
	if(fileList.fileList.length!=1)
	{
		Alert.show("至少且只能选择一个文件");
	}
	
	var f:FileReference;
	var oldIndex :int = files[currentSelectedRowIndex].index;
	files.removeItemAt(currentSelectedRowIndex);
	for each(var file:FileReference in fileList.fileList)
	{
		f  = file;
		var flag:Boolean = checkSelctedFile(file);
		var o :Object = new Object();
		/*
		* 如果已存在在文件列表中，则不作处理
		*/
		if(flag)
		{
			continue;
		}
		else
		{
			o.fileName = file.name;
			o.fileType = file.type;
			var fileSize :Number = Math.round(file.size);
			
			if(fileSize>this.maxSize*1024*1024)
			{
				Alert.show("请上传大小小于64M的附件","提示");
				return;
			}
			
			/*
			* 文件小于1M，显示K单位。
			*/
			if(fileSize/1024<1024)
			{
				o.fileSize =(fileSize/(1024)).toString().slice(0,4)+"K";
			}
			else
			{
				o.fileSize = (fileSize/(1024*1024)).toString().slice(0,4)+"M";
			}
			
			o.fileReference = file;
			o.index = oldIndex;
			files.addItemAt(o,currentSelectedRowIndex);
		}
	}
	
 	//添加状态变更后的行
 	var row:GridRow = new GridRow();
 	row.styleName="UploadTabList";
  	row.height = 40;
  	
    var item:GridItem = new GridItem();
    item.setStyle("paddingLeft",20);
    item.setStyle("verticalAlign","middle");
    item.setStyle("horizontalAlign","left");
    //设置无滚动条
    item.verticalScrollPolicy = "off";
	item.horizontalScrollPolicy = "off";
  	item.width = 380;
  	
  	//先添加图标
    var fileImg :Image = new Image();
    fileImg.source="../../flex_app/themes/skin_images/doc_ico/t"+f.type+".png";
    var fileNameLabel:Label = new Label();
    fileNameLabel.text = f.name;
    fileNameLabel.setStyle("fontWeight","bold");
    fileNameLabel.setStyle("verticalAlign","middle");
 
 	item.addChild(fileImg);
    item.addChild(fileNameLabel);
    row.addChild(item);
    
    var item:GridItem = new GridItem();
    item.setStyle("verticalAlign","middle");
    item.setStyle("horizontalAlign","left");
  	item.width = 289;
    var bar:ProgressBar = new ProgressBar();
    bar.source = f;
    bar.minimum = 0;
    bar.maximum = 100;
    bar.percentWidth = 65;
    bar.labelPlacement = "center";
    bar.label = "%3%%";
    bar.addEventListener(Event.COMPLETE,
    		function (e:Event):void{
    	//Alert.show("finished...");
	});
     //增加监听处理，当文件上传超过95%时不可操作。
    bar.addEventListener(ProgressEvent.PROGRESS,
    		function (e:ProgressEvent):void{
    	if(e.bytesLoaded/e.bytesTotal>0.95)
    	{
    		Button(GridItem(GridRow(ProgressBar(e.target).parent.parent).getChildAt(1)).getChildAt(1)).enabled = false;
    	}
    });
    item.addChild(bar);
    row.addChild(item);
    
    var cBtn:Button = new Button();
    cBtn.label = "取消上传";
    cBtn.height = 21;
   	cBtn.width = 66;
    cBtn.styleName="OperatButton";
    cBtn.addEventListener(MouseEvent.CLICK,cancelFileUpload);
 
    item.addChild(cBtn);
    row.addChild(item);
    ///先移除原来的行，然后添加新的行
    uploadFileGrid.removeChildAt(currentSelectedRowIndex);
  	uploadFileGrid.addChildAt(row,currentSelectedRowIndex);
  	
  	//同时更新jsp页面中对应的title
  	addDocFileForm(1,oldIndex,f.name);
  	
  	/**
  	 * 判断是否立即上传文档
  	 */ 
  	var flag:Boolean = false;
  	for(var i:int = 0 ;i<files.length;i++)
	{
		//只要不是当前文件，且状态不是为已上传和被取消；则不立即上传当前文件
		if(FileReference(files[i].fileReference).name!=f.name)
		{
			if((files[i].hasUploadFlag != 'Y' || files[i].hasUploadFlag != 'cancel'))
			{
				flag = false;
			}
			else
			{
				flag = true;
				return;
			}
		}
	}
	
	//如果选择文件后，前面所有的文件都上传完了，则立即上传当前选中的文件。
	if(!flag)
	{
		this.uploadFile();	
	}
}
/**
 * 文件上传成功后处理
 */ 
private function fileUploadSuccess(index:Number):void
{
 	var f : FileReference = FileReference(files[index].fileReference);
 	//移除当前行
 	uploadFileGrid.removeChildAt(index);
 	//添加状态变更后的行
 	var row:GridRow = new GridRow();
 	row.styleName="UploadTabList";
  	row.height = 40;
  	
    var item:GridItem = new GridItem();
    item.setStyle("paddingLeft",20);
    item.setStyle("verticalAlign","middle");
    item.setStyle("horizontalAlign","left");
    //设置无滚动条
    item.verticalScrollPolicy = "off";
    item.horizontalScrollPolicy = "off";
  	item.width = 380;
  	//先添加图标
    var fileImg :Image = new Image();
    fileImg.source="../../flex_app/themes/skin_images/doc_ico/t"+f.type+".png";
    var fileNameLabel:Label = new Label();
    fileNameLabel.text = f.name;
    fileNameLabel.setStyle("fontWeight","bold");
    fileNameLabel.setStyle("verticalAlign","middle");
    
 	item.addChild(fileImg);
    item.addChild(fileNameLabel);
    row.addChild(item);
    
    var item:GridItem = new GridItem();
    item.setStyle("verticalAlign","middle");
    item.setStyle("horizontalAlign","center");
  	item.width = 289;
  	var img :Image = new Image();
  	img.source = "../../flex_app/themes/skin_images/success.png";
    var label:Label = new Label();
    label.text = "文档成功上传，请填写文档信息";
    label.setStyle("fontSize",12);
    item.addChild(img);
    item.addChild(label);
    row.addChild(item);
    
    var eBtn:Button = new Button();
    eBtn.label = "编辑文档";
    eBtn.styleName="OperatButton";
    eBtn.addEventListener(MouseEvent.CLICK,function (e:Event):void{
    	var tRow :GridRow = GridRow(Button(e.target).parent.parent);
 		var index :Number= uploadFileGrid.getChildIndex(tRow);
 		locateFormById(files[index].index);
 		return ;
    });
 
    item.addChild(eBtn);
    row.addChild(item);
  	uploadFileGrid.addChildAt(row,index);
  	//更新上传统计信息
  	uploadSuccessNum++;
  	refreshUploadResultMsg();
}
/**
 * 文件上传失败处理
 * 1、将文件上传失败信息提示
 * 2、切换上传其他、放弃上传按钮
 */ 
private function fileUploadFailure(index :Number,errorMsg:String):void
{
	var f : FileReference = FileReference(files[index].fileReference);
 	//移除当前行
 	uploadFileGrid.removeChildAt(index);
 	//添加状态变更后的行
 	var row:GridRow = new GridRow();
 	row.styleName="UploadTabList";
  	row.height = 55;
  	
    var item:GridItem = new GridItem();
    item.setStyle("paddingLeft",20);
    item.setStyle("verticalAlign","middle");
    item.setStyle("horizontalAlign","left");
    //设置无水平滚动条
    item.verticalScrollPolicy = "off";
    item.horizontalScrollPolicy = "off";
    item.colSpan=2;
  	item.width = 669;
  	
  	var vbox :VBox = new VBox();
  	//vbox.styleName = "shibai";
   	vbox.width=669;
   	vbox.setStyle("horizontalGap",0);
   	vbox.setStyle("verticalGap",0);
   	var hbox:HBox = new HBox();
   	hbox.height=27;
   	hbox.setStyle("horizontalGap",5);
   	hbox.setStyle("verticalGap",0);
   	hbox.setStyle("paddingTop",5)
   	hbox.setStyle("paddingBottom",5)
  	//先添加图标
    var fileImg :Image = new Image();
    fileImg.source="../../flex_app/themes/skin_images/doc_ico/t"+f.type+".png";
    var label:Label = new Label();
    label.height = 40;
    label.width = 300;
    label.text = f.name;//+'\n    抱歉，您的文档附件与已有文档重复，我们建议您分享其他文档。';
 	label.setStyle("fontWeight","bold");//
 	label.setStyle("fontSize",12);
    hbox.addChild(fileImg);
    hbox.addChild(label);
    var spacer :Spacer = new Spacer();
   	spacer.width = 15;
   	hbox.addChild(spacer);
  	var img:Image = new Image();
  	img.source = "../../flex_app/themes/skin_images/defeated.png";
  	var errorLabel:Label = new Label();
  	errorLabel.text = "上传失败";
  	errorLabel.setStyle("fontSize",12);
  	errorLabel.setStyle("color","red");
  	errorLabel.setStyle("fontWeight","bold");
  	hbox.addChild(img);
  	hbox.addChild(errorLabel);
  	var otBtn:Button = new Button();
   	otBtn.label = "上传其他";
   	otBtn.height = 21;
   	otBtn.width = 66;
   	otBtn.styleName="OperatButton";
   	otBtn.addEventListener(MouseEvent.CLICK,function (e:Event):void{
   		var tRow :GridRow = GridRow(Button(e.target).parent.parent.parent.parent);
 		var index :Number= uploadFileGrid.getChildIndex(tRow);
 		currentSelectedRowIndex = index;
 		addFiles(e);
 		//重置当前上传失败的文档的统计信息
 		uploadFailureNum--;
 		return ;
   	});
    var guBtn:Button = new Button();
   	guBtn.label = "放弃上传";
   	guBtn.height = 21;
   	guBtn.width = 66;
   	guBtn.styleName="OperatButton";
   	guBtn.addEventListener(MouseEvent.CLICK,function (e:Event):void{
   		var tRow :GridRow = GridRow(Button(e.target).parent.parent.parent.parent);
 		var index :Number= uploadFileGrid.getChildIndex(tRow);
	  	//移除当前文件对应的js表单
	  	removeJSPForm(files[index].index);
 		if(files.length>0)
 		{
 			FileReference(files[index].fileReference).cancel();
 			files.removeItemAt(index);
 		}
 		uploadFileGrid.removeChildAt(index);
 		//刷新上传结果
 		uploadFailureNum--;
	  	refreshUploadResultMsg();
 		//减少高度
 		var height = Application.application.height-55;
 		Application.application.height = height;
  		setFlashHeight(height);
  		//判断是否要关闭当前页面
		pageClose();
   	});
   	
   	var spacer :Spacer = new Spacer();
   	spacer.width = 40;
   	//spacer.setStyle("width","100%");
   	hbox.addChild(spacer);
   	hbox.addChild(otBtn);
   	hbox.addChild(guBtn);
   	vbox.addChild(hbox);
   	
   	var error:Label = new Label();
  	//label.setStyle("verticalAlign","middle");
  	error.text = errorMsg;//;
  	error.setStyle("paddingBottom",5);
  	error.setStyle("paddingTop",0);
  	error.setStyle("fontSize",12);
  	error.setStyle("color","red");
  	error.setStyle("fontWeight","bold");
   	vbox.addChild(error);
   	item.addChild(vbox);
   	row.addChild(item);
    
  	uploadFileGrid.addChildAt(row,index);
  	
  	uploadFailureNum++;
  	refreshUploadResultMsg();
  	//设置上传失败后，高度。
 	var height = Application.application.height+15;
 	Application.application.height = height;
  	setFlashHeight(height);
}