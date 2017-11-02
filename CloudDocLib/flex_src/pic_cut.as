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

//������ͼƬ��ַ�������������ק����ѡ������
private var fileReference: FileReference = new FileReference();

//�����ϴ����ļ����������� 
private var allowFileTypes : Array;
/**
* ��ʼ����Ϣ
*/ 
private function init(): void
{
	Security.allowDomain("*");
	fileReference.addEventListener(Event.COMPLETE,fileReferenceCompleteHandler);
	
	var imageTypes:FileFilter = new FileFilter("ͼƬ (*.jpg,*.gif,*.png)", "*.jpg;*.gif; *.png;");
	allowFileTypes = new Array(imageTypes);
	
	rightleft.source = "images/leftright.png";
	leftright.source = "images/topfoot.png";
	
	//��ȡ��ǰ�û���id
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
 * ����϶���ͼ��λ�ñ��
 * 
 * 1�����Ͻǣ�2�����Ͻǣ�3�����½ǣ�4�����½ǣ�
 */ 
private var _curDragCorner:int;

private var leftright : Image = new Image();
private var rightleft : Image = new Image();

private var cutCan:Canvas = new Canvas();
private function loaderCompleteHandler(e:Event):void
{
    var bitmap:Bitmap = Bitmap(loader.content);
    bitmapData = bitmap.bitmapData;
    //Ԥ��ͼƬ
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
	//������ʾѡ���
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
			//����jsˢ��ҳ��
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
//ͼƬ��ͼ���Ƿ��϶����
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
	 * �˴����õ�����ֵ����borderCan�����cutCan��
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
	//�������Ϊ����
   	borderCan.useHandCursor=true;
   	borderCan.buttonMode=true;
   	var component:UIComponent=e.currentTarget as UIComponent;
	//������ק��Χ
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
	//trace("����϶�========"+(this.mainCan.mouseX)+", "+(this.mainCan.mouseY));
	//��곬��������������ʽ�������ק���á�
	if(this.mainCan.mouseX<0 || this.mainCan.mouseY<0 || this.mainCan.mouseX>this.mainCan.width || this.mainCan.mouseY>this.mainCan.height)
	{
		_borderDragFlag = false;
		cutCan.stopDrag();
		CursorManager.removeAllCursors();
		return;
	}
	//�Ƿ�����ק״̬
	if(_borderDragFlag)
	{
		/**
		 * 
		 * �˴������޸�borderCan������ ��boderCan������ֵ�������cutCan��ֵ��
		 * 
		 */
    	//trace("ԭ���꣺"+borderCan.y+", "+borderCan.x+", "+cutCan.y+", "+cutCan.x);
    	var dis:int = 0 ;
		//���Ͻ�
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
		//���Ͻ�
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
		
		//���½�
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
		//���½�
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
		
    	//trace("�����꣺"+borderCan.y+", "+borderCan.x+", "+cutCan.y+", "+cutCan.x);
    	//trace("��С��"+borderCan.height+", "+borderCan.width+", "+cutCan.height+", "+cutCan.width);
    	setCornerPosition();
	}
}

private var w:Number = 0;
private var h:Number = 0;
private function getImg():void
{               
	w = borderCan.width;
    h = borderCan.height;
    //�����ͼʱ����귴����ק����������
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
     
    //��bitmapdataȫ����ȡԤ��ͼƬ������
    var initBD:BitmapData = new BitmapData(preUploadImage.width,preUploadImage.height);
    initBD.draw(preUploadImage);
    _bytearray = new ByteArray();
    //����ΪҪ��ȡ����               
    var re:Rectangle = new Rectangle(finalX,finalY,w,h);
    _bytearray = initBD.getPixels(re); //��ȡ����ѡ��������ؼ���
    var imgBD:BitmapData = new BitmapData(w,h);
    _bytearray.position=0;
	//����ģ���ǰ��bytearray.positionΪ��󳤶ȣ�Ҫ��Ϊ��0��ʼ��ȡ
    var fillre:Rectangle = new Rectangle(0,0,w,h);
    imgBD.setPixels(fillre,_bytearray);
	//����ȡ�������ؼ��ϴ����µ�bitmapdata���С�ͽ�ȡ����һ��
	//showImg(imgBD);//��ʾͼƬ
	//��ͼƬ��ʾ��С����
	previewImg.source = new Bitmap(imgBD);
	//�ϴ��ļ�
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
    //��ȡ���û���id������ȥ���ص�ǰ�û���ͷ��
    var url:String = Application.application.url;
    var requestUrl:String = url.substr(0,url.indexOf("flex_app")) + "portal/user/userInfoAction!loadUserPhotoInfo.action";
    var request:URLRequest = new URLRequest(requestUrl + "?id=" + _userId);
    var _loader:Loader=new Loader();
    _loader.contentLoaderInfo.addEventListener(Event.COMPLETE,function(e:Event):void{
			previewImg.source=e.currentTarget.content;
    });
    //��url ������������Ļ� ʹ��encodeURI���� ���û�� ����Բ���  
    _loader.load(request);
}

private function servletFaultHandler(event:FaultEvent):void
{
    var fault:Fault = event.fault;
    var s:String = (fault.faultDetail!=null) ? fault.faultDetail : fault.faultString;
    Alert.show(s,'��ʾ');
}
// ActionScript file
