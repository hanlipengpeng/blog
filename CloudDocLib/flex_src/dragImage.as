
import astion.ScaleBox;

import flash.events.KeyboardEvent;
import flash.events.MouseEvent;

import mx.controls.Alert;

private var isCtrlCopy:Boolean;  //是否按下ctrl复制 
private var isMouseCopy:Boolean; //是否单击鼠标复制

private var toOriginX:Number = 0;
private var toOriginY:Number = 0;
private var sourceWidth:Number = 100;
private var sourceHeigth:Number = 100;

private var copySource:ScaleBox;

public function mouseDownCopyHandler(event:MouseEvent):void
{
	isMouseCopy = true;
	
	if(event.currentTarget is ScaleBox)
	{
		var o:ScaleBox = (event.currentTarget) as ScaleBox;
		
		toOriginX = (mouseX - o.x);
		toOriginY = (mouseY - o.y);
		sourceWidth = o.boxWidth;
		sourceHeigth = o.boxHeight;
		copySource = o;
	}
	
	if(isCtrlCopy)
	{
		(event.currentTarget as ScaleBox).removeMouseMoveEventHandler();
	}
}  

public function mouseUpCopyHandler(event:MouseEvent):void
{
	
	if(isCtrlCopy)
	{
		var destScaleBox : ScaleBox = new ScaleBox(sourceWidth,sourceHeigth,true);
		copySource = destScaleBox;
		
		destScaleBox.x = (event.stageX - toOriginX);
		destScaleBox.y = (event.stageY - toOriginY);
		
		destScaleBox._width = img.width;
		destScaleBox._height = img.height;
		destScaleBox.width = 250;
		destScaleBox.height = 250;
		destScaleBox.visible = true;
		
		destScaleBox.addEventListener(MouseEvent.DOUBLE_CLICK,boxDoubleClickHandle);
		destScaleBox.addEventListener(KeyboardEvent.KEY_DOWN,keyDownBoardHandler);
		destScaleBox.addEventListener(KeyboardEvent.KEY_UP,keyUpBoardHandler);
		destScaleBox.addEventListener(MouseEvent.MOUSE_DOWN,mouseDownCopyHandler);
		destScaleBox.addEventListener(KeyboardEvent.KEY_DOWN,deleteScaleBoxHandle);
		destScaleBox.addEventListener(MouseEvent.MOUSE_OVER,mouseCopyOverHandler);
        destScaleBox.addEventListener(MouseEvent.MOUSE_OUT,mouseCopyOutHandler);
        
		canvas.addChild(destScaleBox);
		boxList.addItem(destScaleBox);
		canvas.setChildIndex(destScaleBox,1);
	}
	
	isCtrlCopy = false;
	(event.currentTarget as ScaleBox).addMouseMoveEventHandler();
}

/**
 *  监听ctrl键按下进行复制选择框
 * */
public function keyDownBoardHandler(event:KeyboardEvent):void
{
	if(event.ctrlKey)
	{
		isCtrlCopy = true;
	}
	
//	Alert.show(isMouseCopy + ":" + isCtrlCopy);
	
	if(isMouseCopy && isCtrlCopy)
	{
		(event.target as ScaleBox).removeMouseMoveEventHandler();
//			if(event.target is ScaleBox)
//			{
//				(event.target as ScaleBox).removeMouseMoveEventHandler();
//			}
	}
}


/**
 * 监听ctrl键弹起取消复制选择框
 * */
public function keyUpBoardHandler(event:KeyboardEvent) :void
{
	isCtrlCopy = false;
	if(event.currentTarget is ScaleBox)
	{
		(event.currentTarget as ScaleBox).addMouseMoveEventHandler();
	}
	copySource.addMouseMoveEventHandler();
}


public function mouseCopyOverHandler(event:MouseEvent):void
{
	isMouseCopy = true;
	if(event.currentTarget is ScaleBox)
	{
		var o:ScaleBox = event.currentTarget as ScaleBox;
		copySource = o;
		
		if(isCtrlCopy)
		{
			o.removeMouseMoveEventHandler();
		}
	}
}

public function mouseCopyOutHandler(event:MouseEvent):void
{
	isMouseCopy = false;
	
	if(event.currentTarget is ScaleBox)
	{
		var o:ScaleBox = event.currentTarget as ScaleBox;
		o.addMouseMoveEventHandler();
	}
}