import flash.display.Loader;
import flash.events.MouseEvent;
import flash.net.FileReference;
import flash.net.URLRequest;
import flash.ui.Mouse;
import flash.utils.setTimeout;

import mx.containers.Canvas;
import mx.containers.TitleWindow;
import mx.controls.Alert;
import mx.controls.Image;
import mx.core.UIComponent;
import mx.events.CloseEvent;
import mx.graphics.codec.PNGEncoder;
import mx.managers.CursorManager;
import mx.managers.PopUpManager;
import mx.rpc.Fault;
import mx.rpc.events.FaultEvent;
import mx.rpc.events.ResultEvent;
private var initX:Number;
private var initY:Number;
private var borderCan:Canvas;
private var byteArray:ByteArray;
private var _bytearray:ByteArray;
private var bitmapData:BitmapData;
private var loader:Loader=new Loader();

//测试用图片地址，可以用鼠标拖拽进行选择区域
private var fileReference: FileReference = new FileReference();

//允许上传的文件的类型数组 
private var allowFileTypes : Array;
/**
* 初始化信息
*/ 
private function init(): void
{
	Security.allowDomain("*");
	fileReference.addEventListener(Event.COMPLETE,fileReferenceCompleteHandler);
	
	var imageTypes:FileFilter = new FileFilter("图片 (*.jpg,*.gif,*.png)", "*.jpg;*.gif; *.png;");
	allowFileTypes = new Array(imageTypes);
	
	rightleft.source = "images/leftright.png";
	leftright.source = "images/topfoot.png";
	
	//获取当前用户的id
	callSessionServlet();
}

private function fileReferenceCompleteHandler(e:Event):void
{
    byteArray = fileReference.data;
    loader.contentLoaderInfo.addEventListener(Event.COMPLETE,loaderCompleteHandler);
    loader.loadBytes(byteArray);
}

private function fileReferenceSelectHandler(e:Event):void
{
    fileReference.load();
}

private function browseFile():void
{
	fileReference.browse(allowFileTypes);
    fileReference.addEventListener(Event.SELECT,fileReferenceSelectHandler);
}
private var rightUp:Image = new Image();
private var rightDown:Image = new Image();
private var leftUp:Image = new Image();
private var leftDown:Image = new Image();
/**
 * 鼠标拖动截图框位置标记
 * 
 * 1：左上角；2：右上角；3：右下角；4：左下角；
 */ 
private var _curDragCorner:int;

private var leftright : Image = new Image();
private var rightleft : Image = new Image();

private var cutCan:Canvas = new Canvas();
private function loaderCompleteHandler(e:Event):void
{
    var bitmap:Bitmap = Bitmap(loader.content);
    bitmapData = bitmap.bitmapData;
    //预览图片
    preUploadImage.source = bitmap;
    
    if(borderCan!=null)
	{
    	removeBorderCan();
	}
	
  	addBorderCan();
  	initBorderCorner();
  	setCornerPosition();
  	enableSaveBtn();
}

private function enableSaveBtn():void
{
	saveBtn.enabled = true;
}

private var _canvasDistanceValue:int = 30;
private var _initBorderCanSize:int = 120;
private function addBorderCan():void
{
	//立即显示选择框
    var initX:int = preUploadImage.x;
    var initY:int = preUploadImage.y;
	
	borderCan =  new Canvas();
   	borderCan.setStyle("verticalScrollPolicy","off");
   	borderCan.setStyle("horizontalScrollPolicy","off");
	borderCan.addEventListener(MouseEvent.MOUSE_DOWN,borderCanDownHandle);
    borderCan.addEventListener(MouseEvent.MOUSE_UP,borderCanUpHandle);
    borderCan.addEventListener(MouseEvent.MOUSE_OVER,borderCanOverHandle);
  	borderCan.width = _initBorderCanSize;
  	borderCan.height = _initBorderCanSize;
  	borderCan.setStyle("borderStyle","solid");
  	borderCan.setStyle("borderColor","red");
  	
   	borderCan.x = initX/2+_canvasDistanceValue/2;
  	borderCan.y = initY/2+_canvasDistanceValue/2;
  	
  	cutCan = new Canvas();
  	cutCan.setStyle("verticalScrollPolicy","off");
   	cutCan.setStyle("horizontalScrollPolicy","off");
	//cutCan.addEventListener(MouseEvent.MOUSE_DOWN,borderCanDownHandle);
    //cutCan.addEventListener(MouseEvent.MOUSE_UP,borderCanUpHandle);
  	cutCan.width = _initBorderCanSize+_canvasDistanceValue;
  	cutCan.height = _initBorderCanSize+_canvasDistanceValue;
  	cutCan.setStyle("borderStyle","none");
  	cutCan.setStyle("borderColor","green");
  	cutCan.x = initX/2;
  	cutCan.y = initY/2;
  	cutCan.addChild(borderCan);
  	this.mainCan.addChild(cutCan);
}
[Embed(source="images/topfoot.png")]
private var wnCursor:Class;
[Embed(source="images/leftright.png")]
private var wsCursor:Class;

private function initBorderCorner():void
{
	rightUp.source = 'images/righttop.png';
	rightDown.source = 'images/rightfoot.png';
	leftUp.source = 'images/lefttop.png';
	leftDown.source = 'images/leftfoot.png';
	
	rightUp.addEventListener(MouseEvent.MOUSE_OVER,rightUpOverHandle);
	rightUp.addEventListener(MouseEvent.MOUSE_MOVE,rightUpMoveHandle);
	rightUp.addEventListener(MouseEvent.MOUSE_DOWN,mouseDownHandle);
	rightUp.addEventListener(MouseEvent.MOUSE_OUT,rightUpOutHandle);
	rightUp.addEventListener(MouseEvent.MOUSE_UP,rightUpUpHandle);
	
	rightDown.addEventListener(MouseEvent.MOUSE_OVER,rightDownOverHandle);
	rightDown.addEventListener(MouseEvent.MOUSE_MOVE,rightDownMoveHandle);
	rightDown.addEventListener(MouseEvent.MOUSE_DOWN,mouseDownHandle);
	rightDown.addEventListener(MouseEvent.MOUSE_OUT,rightDownOutHandle);
	
	
	leftUp.addEventListener(MouseEvent.MOUSE_OVER,leftUpOverHandle);
	leftUp.addEventListener(MouseEvent.MOUSE_MOVE,leftUpMoveHandle);
	leftUp.addEventListener(MouseEvent.MOUSE_DOWN,mouseDownHandle);
	leftUp.addEventListener(MouseEvent.MOUSE_OUT,leftUpOutHandle);

	leftDown.addEventListener(MouseEvent.MOUSE_OVER,leftDownOverHandle);
	leftDown.addEventListener(MouseEvent.MOUSE_MOVE,leftDownMoveHandle);
	leftDown.addEventListener(MouseEvent.MOUSE_DOWN,mouseDownHandle);
	leftDown.addEventListener(MouseEvent.MOUSE_OUT,leftDownOutHandle);
	
	cutCan.addChild(rightUp);
   	cutCan.addChild(rightDown);
   	cutCan.addChild(leftDown);
  	cutCan.addChild(leftUp);
}

private function removeBorderCan():void
{
	
	this.mainCan.removeChild(cutCan);
	cutCan.removeEventListener(MouseEvent.MOUSE_DOWN,borderCanDownHandle);
    cutCan.removeEventListener(MouseEvent.MOUSE_UP,borderCanUpHandle);
	cutCan = null;
}

private function uploadImage(imgBD:BitmapData):void
{
	var req:URLRequest = new URLRequest();
	req.method = URLRequestMethod.POST;
	req.data = new PNGEncoder().encode(imgBD);
	req.contentType="application/octet-stream";
	var url:String = Application.application.url;
    var requestUrl:String = url.substr(0,url.indexOf("flex_app")) + "ImageFileUploaded?id="+_userId;
	req.url = requestUrl;
	var loader:URLLoader = new URLLoader;
	loader.dataFormat = URLLoaderDataFormat.BINARY;
	loader.load(req);
	
	setTimeout(function ():void{
			//调用js刷新页面
			ExternalInterface.call('refresh');},
	500);
}

private function rightUpOverHandle(e:MouseEvent):void
{
	rightleft.x = rightUp.x-2;
	rightleft.y = rightUp.y-3;
	cutCan.addChildAt(rightleft,cutCan.getChildIndex(rightUp)-1);
	Mouse.hide();
}
private function rightUpOutHandle(e:MouseEvent):void
{
	cutCan.removeChild(rightleft);
	Mouse.show();
}
private function leftDownOverHandle(e:MouseEvent):void
{
	rightleft.x = leftDown.x-3;
	rightleft.y = leftDown.y-2;
	cutCan.addChildAt(rightleft,cutCan.getChildIndex(leftDown)-1);
	Mouse.hide();
}
private function leftDownOutHandle(e:MouseEvent):void
{
	cutCan.removeChild(rightleft);
	CursorManager.removeAllCursors();
	Mouse.show();
	borderCan.useHandCursor=true;
   	borderCan.buttonMode=true;
}

private var _x : Number;
private var _y : Number;
private function mouseDownHandle(e:MouseEvent):void
{
	_x = preUploadImage.mouseX;
    _y = preUploadImage.mouseY;
}
private function rightUpUpHandle(e:MouseEvent):void
{
}
private function leftDownMoveHandle(e:MouseEvent):void
{
	cutCan.stopDrag();
	if(e.buttonDown)
	{
		CursorManager.setCursor(wsCursor);
    	_curDragCorner = 4;
    	_borderDragFlag = true;
	}
	
}
private function rightDownMoveHandle(e:MouseEvent):void
{
	cutCan.stopDrag();
	if(e.buttonDown)
	{
		CursorManager.setCursor(wnCursor);
    	_borderDragFlag = true;
    	_curDragCorner = 3;
	}
	
}
private function rightDownOverHandle(e:MouseEvent):void
{
	leftright.x = rightDown.x-3;
	leftright.y = rightDown.y-3;
	cutCan.addChildAt(leftright,cutCan.getChildIndex(rightDown)-1);
	Mouse.hide();
}
private function rightDownOutHandle(e:MouseEvent):void
{
	cutCan.removeChild(leftright);
	Mouse.show();
}
private function leftUpOverHandle(e:MouseEvent):void
{
	leftright.x = leftUp.x-3;
	leftright.y = leftUp.y-3;
	cutCan.addChildAt(leftright,cutCan.getChildIndex(leftUp)-1);
	Mouse.hide();
}
private function leftUpOutHandle(e:MouseEvent):void
{
	cutCan.removeChild(leftright);
	Mouse.show();
}
private function leftUpMoveHandle(e:MouseEvent):void
{
	cutCan.stopDrag();
	if(e.buttonDown)
	{
		CursorManager.setCursor(wnCursor);
    	_borderDragFlag = true;
    	_curDragCorner = 1;
	}
}
//图片截图框是否拖动标记
private var _borderDragFlag:Boolean = false;

private function rightUpMoveHandle(e:MouseEvent):void
{
	cutCan.stopDrag();
	if(e.buttonDown)
	{
		CursorManager.setCursor(wsCursor);
    	_curDragCorner = 2;
    	_borderDragFlag = true;
	}
}
private function setCornerPosition():void
{
	/**
	 * 此处设置的坐标值都是borderCan相对于cutCan的
	 */ 
	var b_x:int = borderCan.x;
    var b_y:int = borderCan.y;
    
	rightUp.x = b_x+borderCan.width-2;
   	rightUp.y = b_y-4;
   	
   	rightDown.x = b_x+borderCan.width-2;
   	rightDown.y = b_y+borderCan.height-2;
   	
   	leftDown.x = b_x-4;
   	leftDown.y = b_y+borderCan.height-2;
   	
   	leftUp.x = b_x-4;
   	leftUp.y = b_y-4;
   	
}

private function borderCanDownHandle(e:MouseEvent):void
{
	//设置鼠标为手型
   	borderCan.useHandCursor=true;
   	borderCan.buttonMode=true;
   	var component:UIComponent=e.currentTarget as UIComponent;
	//定义拖拽范围
	var range:Rectangle = new Rectangle(preUploadImage.x-_canvasDistanceValue/2,preUploadImage.y-_canvasDistanceValue/2,
			preUploadImage.width+_canvasDistanceValue-cutCan.height-1,preUploadImage.height+_canvasDistanceValue-cutCan.width-1);
	UIComponent(component.parent).startDrag(false,range);
}

private function borderCanUpHandle(e:MouseEvent):void
{
   	var component:UIComponent=e.currentTarget as UIComponent;
	UIComponent(component.parent).stopDrag();
}

private function borderCanOverHandle(e:MouseEvent):void
{
	borderCan.useHandCursor=true;
   	borderCan.buttonMode=true;
}
private function downHander(e:MouseEvent):void
{
	this.addEventListener(MouseEvent.MOUSE_MOVE,moveHandler);
    this.addEventListener(MouseEvent.MOUSE_UP,upHander);
}
 
private var upX:Number;
private var upY:Number;
private function upHander(e:MouseEvent):void
{
	CursorManager.removeAllCursors();
	Mouse.show();
	if(borderCan!=null)
	{
		borderCan.useHandCursor=true;
   		borderCan.buttonMode=true;
   		_borderDragFlag = false;
	}
}

private var _borderCanChangeSizePer:int = 1;
private var _minBorderCanSize:int = 5;
private function moveHandler(e:MouseEvent):void
{
	//trace("鼠标拖动========"+(this.mainCan.mouseX)+", "+(this.mainCan.mouseY));
	//鼠标超出了面板则鼠标样式、面板拖拽重置。
	if(this.mainCan.mouseX<0 || this.mainCan.mouseY<0 || this.mainCan.mouseX>this.mainCan.width || this.mainCan.mouseY>this.mainCan.height)
	{
		_borderDragFlag = false;
		cutCan.stopDrag();
		CursorManager.removeAllCursors();
		return;
	}
	//是否在拖拽状态
	if(_borderDragFlag)
	{
		/**
		 * 
		 * 此处不用修改borderCan的坐标 （boderCan的坐标值是相对于cutCan的值）
		 * 
		 */
    	//trace("原坐标："+borderCan.y+", "+borderCan.x+", "+cutCan.y+", "+cutCan.x);
    	var dis:int = 0 ;
		//左上角
		if(_curDragCorner==1)
		{
			CursorManager.setCursor(wnCursor);
			dis = (this.mainCan.mouseX-_x)>0?-_borderCanChangeSizePer:_borderCanChangeSizePer;
			_x = this.mainCan.mouseX;
			_y = this.mainCan.mouseY;
			if(cutCan.y - dis<=-_canvasDistanceValue/2)
			{
				return;
			}
			
			if(borderCan.width + dis<_minBorderCanSize)
			{
				return;
			}
			
	    	cutCan.y = cutCan.y - dis;
			cutCan.x = cutCan.x - dis;
	    	borderCan.width = borderCan.width + dis;
	    	borderCan.height = borderCan.height + dis;
	    	cutCan.width = cutCan.width+dis;
	    	cutCan.height = cutCan.height +dis;
		}
		//右上角
		if(_curDragCorner==2)
		{
			CursorManager.setCursor(wsCursor);
			dis = (this.mainCan.mouseX-_x)>0?_borderCanChangeSizePer:-_borderCanChangeSizePer;
	    	_x = this.mainCan.mouseX;
			_y = this.mainCan.mouseY;
			if(cutCan.y - dis<=-_canvasDistanceValue/2)
			{
				return;
			}
			
			if(borderCan.width + dis<_minBorderCanSize)
			{
				return;
			}
			
			cutCan.y = cutCan.y - dis;
	    	borderCan.width = borderCan.width + dis;
	    	borderCan.height = borderCan.height + dis;
	    	cutCan.width = cutCan.width+dis;
	    	cutCan.height = cutCan.height +dis;
		}
		
		//右下角
		if(_curDragCorner==3)
		{
			CursorManager.setCursor(wnCursor);
			dis = (this.mainCan.mouseX-_x)>0?_borderCanChangeSizePer:-_borderCanChangeSizePer;
			_x = this.mainCan.mouseX;
			_y = this.mainCan.mouseY;
			if(cutCan.x + borderCan.width+dis+_canvasDistanceValue/2>this.mainCan.width-3)
			{
				return;
			}
			
			if(cutCan.y + borderCan.height+dis+_canvasDistanceValue/2>this.mainCan.height-3)
			{
				return;
			}
			
			if(borderCan.width + dis<_minBorderCanSize)
			{
				return;
			}
			
	    	borderCan.width = borderCan.width + dis;
	    	borderCan.height = borderCan.height + dis;
	    	cutCan.width = cutCan.width+dis;
	    	cutCan.height = cutCan.height +dis;
		}
		//左下角
		if(_curDragCorner==4)
		{
			CursorManager.setCursor(wsCursor);
			dis = (this.mainCan.mouseX-_x)>0?-_borderCanChangeSizePer:_borderCanChangeSizePer;
			_x = this.mainCan.mouseX;
			_y = this.mainCan.mouseY;
			if(cutCan.x + borderCan.width+dis+_canvasDistanceValue/2>this.mainCan.width-3)
			{
				return;
			}
			if(cutCan.y + borderCan.height+dis+_canvasDistanceValue/2>this.mainCan.height-3)
			{
				return;
			}
			
			if(borderCan.width + dis<_minBorderCanSize)
			{
				return;
			}
			
			cutCan.x = cutCan.x - dis;
	    	borderCan.width = borderCan.width + dis;
	    	borderCan.height = borderCan.height + dis;
	    	cutCan.width = cutCan.width+dis;
	    	cutCan.height = cutCan.height +dis;
		}
		
    	//trace("新坐标："+borderCan.y+", "+borderCan.x+", "+cutCan.y+", "+cutCan.x);
    	//trace("大小："+borderCan.height+", "+borderCan.width+", "+cutCan.height+", "+cutCan.width);
    	setCornerPosition();
	}
}

private var w:Number = 0;
private var h:Number = 0;
private function getImg():void
{               
	w = borderCan.width;
    h = borderCan.height;
    //处理截图时候鼠标反向拖拽的坐标问题
    var finalX:Number = cutCan.x+_canvasDistanceValue/2;//borderCan.x;
    var finalY:Number = cutCan.y+_canvasDistanceValue/2;//borderCan.y;
    if(w<0)
    {
        finalX = borderCan.x + w;
        w= -w;
    }
    if(h<0)
    {
        finalY = borderCan.y + h;
        h= -h;
    }
     
    //用bitmapdata全部获取预览图片的像素
    var initBD:BitmapData = new BitmapData(preUploadImage.width,preUploadImage.height);
    initBD.draw(preUploadImage);
    _bytearray = new ByteArray();
    //矩形为要截取区域               
    var re:Rectangle = new Rectangle(finalX,finalY,w,h);
    _bytearray = initBD.getPixels(re); //截取出所选区域的像素集合
    var imgBD:BitmapData = new BitmapData(w,h);
    _bytearray.position=0;
	//必须的，当前的bytearray.position为最大长度，要设为从0开始读取
    var fillre:Rectangle = new Rectangle(0,0,w,h);
    imgBD.setPixels(fillre,_bytearray);
	//将截取出的像素集合存在新的bitmapdata里，大小和截取区域一样
	//showImg(imgBD);//显示图片
	//将图片显示在小框中
	previewImg.source = new Bitmap(imgBD);
	//上传文件
	uploadImage(imgBD);
}
             
private function showImg(e:BitmapData):void
{
	var t:TitleWindow = new TitleWindow();
    t.showCloseButton=true;
    t.addEventListener(CloseEvent.CLOSE,closeWindow);
    t.width = w+t.getStyle("borderThickness");
    t.height = h+t.getStyle("borderThickness")+t.getStyle("headerHeight");
    var img:Image = new Image();
    img.width = w;
    img.height = h;
    img.source = new Bitmap(e);
    t.addChild(img);
    PopUpManager.addPopUp(t,this,true);
    PopUpManager.centerPopUp(t);
}
private function closeWindow(e:CloseEvent):void
{           
    var t:TitleWindow = e.currentTarget as TitleWindow;                   
    PopUpManager.removePopUp(t);               
}
private function callSessionServlet():void
{
    jspServlet.request.scope = "portalSession";
    jspServlet.send(); 
}

private var _userId:String;
private function servletResultHandler(event:ResultEvent):void
{     
    _userId  = event.result as String;
    //获取到用户的id后，立即去加载当前用户的头像
    var url:String = Application.application.url;
    var requestUrl:String = url.substr(0,url.indexOf("flex_app")) + "portal/user/userInfoAction!loadUserPhotoInfo.action";
    var request:URLRequest = new URLRequest(requestUrl + "?id=" + _userId);
    var _loader:Loader=new Loader();
    _loader.contentLoaderInfo.addEventListener(Event.COMPLETE,function(e:Event):void{
			previewImg.source=e.currentTarget.content;
    });
    //当url 中有中文字体的话 使用encodeURI方法 如果没有 则可以不加  
    _loader.load(request);
}

private function servletFaultHandler(event:FaultEvent):void
{
    var fault:Fault = event.fault;
    var s:String = (fault.faultDetail!=null) ? fault.faultDetail : fault.faultString;
    Alert.show(s,'提示');
}
// ActionScript file
