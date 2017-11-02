package astion
{
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.geom.Rectangle;
	import flash.text.TextField;
	import flash.ui.Mouse;
	import flash.ui.MouseCursor;
	
	import mx.controls.Alert;
	import mx.core.UIComponent;
	import mx.managers.IFocusManagerComponent;
 
 
	/**
	 * ���޸���boxWidth,boxHeightʱ����
	 */
	[Event(name = "resize", type = "flash.events.Event")]
 
	/**
	 * ��λ�øı�󴥷�,���û���ֳɵĳ���,��ֱ���� "box_move"
	 * @example	addEventListener("box_move", moveHandler);
	 */
	[Event(name = "box_move", type = "flash.events.Event")]
 
	/**
	 * ����һ����4����8�����ŵ�����ź���, ���Ʊ��ι���һ����Ч��<br />
	 * ��������϶�<br />
	 * ���϶����ŵ�ı�,����resize�������ĳߴ��,����resize�¼�<br />
	 * ���ı�λ�ú�,����move�¼�<br />
	 * �����õ�flashplayer10�������ʽ,������fp10����<br />
	 * 
	 * ��Flex��ʹ��<br />
	 * �������flex��Ӧ��,�뽫�����޸�Ϊ UIComponent <br />
	 * ע��������ʹ�õ�boxWidth,boxHeight, �벻Ҫ����width,height<br/>
	 * 
	 */
	public class ScaleBox extends UIComponent implements IFocusManagerComponent
	{
		private var _dragEnable:Boolean = true;// �Ƿ���϶�
		private var _scaleEnable:Boolean = true;//�Ƿ������
		private var _is8dot:Boolean = true;	// �Ƿ�8�����Ƶ�, falseΪ4��, Ĭ��8��
		private var _boxWidth:Number;
		private var _boxHeight:Number;
 
		private var changeW:int;			// ��ǰ���Ƶ� -1:�� , 0:�м�  1:�ұ�
		private var changeH:int;			// ��ǰ���Ƶ� -1:�� , 0:�м�  1:�±�
 
		private var isMove:Boolean = false;	// �Ƿ��϶�
		
		private var isCopy:Boolean = false;
		
		private var lockX:Number;
		private var lockY:Number;
 
		private var bottomRightX:Number;	// ���½�x����(��������ռ�)
		private var bottomRightY:Number;	// ���½�y����(��������ռ�)
 
		private var dotList:Array = [];		// ���Ƶ�����
		[Bindable]
		public var scaleBoxName:String = " ";
		//ԭͼ��ʵ��Сʱ��x��y��width��height��
		public var _x:int;
		public var _y:int;
		public var _width:int;
		public var _height:int;
 
		// ��ΪscaleBox�������õ�, ���Է��� ��graphics ���,���ܺܺõĴ��� over out�¼�
		public var hitSprite:Sprite;
		private var txt:TextField;
		public function ScaleBox(boxWidth:Number = 100, boxHeight:Number = 100, is8dot:Boolean = true) 
		{
			super();
			initUI();
			// ��graphics����,���ܺܺõ���Ӧ over out �¼�, �� ���ñ�����������¼�
			mouseEnabled = false; 
			doubleClickEnabled = true;
			addMouseCursorHandler();
			addEventListener(MouseEvent.MOUSE_DOWN, mouseDownHandler);
			this.is8dot = is8dot;
			resize(boxWidth, boxHeight);
		}
 
		/**
		 * ���� box �ߴ� 
		 * @param	boxWidth	<b>	Number	</b> ��� >= 1
		 * @param	boxHeight	<b>	Number	</b> �߶� >= 1
		 * @eventType	flash.events.Event
		 */
		public function resize(boxWidth:Number, boxHeight:Number):void
		{
			if (isNaN(boxWidth) || isNaN(boxHeight)) return;
			if (boxWidth < 1 || boxHeight < 1) return;
 
			_boxWidth  = boxWidth;
			_boxHeight = boxHeight;
			bottomRightX = x + boxWidth;
			bottomRightY = y + boxHeight;
 
			txt.text = int(boxWidth) + " * " + int(boxHeight)+"("+scaleBoxName+")";
			var halfW:Number = boxWidth  * .5;
			var halfH:Number = boxHeight * .5;
 
			for (var i:int = 0; i < 8; i++)
			{
				var dot:Dot = dotList[i];
				dot.x = halfW * dot.lx
				dot.y = halfH * dot.ly
			}
 
			this.graphics.clear();
			this.graphics.lineStyle(1,0xff0000);
			this.graphics.drawRect(0, 0, boxWidth, boxHeight);
			this.graphics.endFill();
			hitSprite.width = boxWidth;
			hitSprite.height = boxHeight;
			dispatchEvent(new Event(Event.RESIZE));
		}
 
		/**
		 * ���ź��ӵĿ��
		 * @default 100
		 */
		public function get boxWidth():Number { return _boxWidth; }
		/**
		 * ���ź��ӵĿ��
		 * @default 100
		 */
		public function set boxWidth(value:Number):void 
		{
			resize(value, _boxHeight);
		}
 
		/**
		 * ���ź��ӵĸ߶�
		 * @default 100
		 */
		public function get boxHeight():Number { return _boxHeight; }
		/**
		 * ���ź��ӵĸ߶�
		 * @default 100
		 */
		public function set boxHeight(value:Number):void 
		{
			resize(_boxWidth, value)
		}
 
		override public function set x(value:Number):void
		{
			if (isNaN(value)) return;
 			//trace("set x: " + value);
			super.x = value;
			bottomRightX = value + _boxWidth;
			dispatchEvent(new Event("box_move"));
		}
 
		override public function set y(value:Number):void
		{
			if (isNaN(value)) return;
 			//trace("set y: " + value);
			super.y = value;
			bottomRightY = value + _boxHeight;
			dispatchEvent(new Event("box_move"));
		}
 
		/**
		 * �Ƿ�8�����ŵ�, 4����8��,
		 * @default	true  
		 */
		public function get is8dot():Boolean { return _is8dot; }
		/**
		 * �Ƿ�8�����ŵ�, 4����8��,
		 * @default	true  
		 */
		public function set is8dot(value:Boolean):void 
		{
			if (_scaleEnable && _is8dot != value)
			{
				_is8dot = value;
				dotList[1].visible = value;
				dotList[3].visible = value;
				dotList[5].visible = value;
				dotList[7].visible = value;
			}
		}
 
		/**
		 * �Ƿ����������Ŵ�С
		 * @default true
		 */
		public function get scaleEnable():Boolean { return _scaleEnable; }
		/**
		 * �Ƿ����������Ŵ�С
		 * @default true
		 */
		public function set scaleEnable(value:Boolean):void 
		{
			if (_scaleEnable != value)
			{
				_scaleEnable = value;
				for each(var dot:Object in dotList)
				{
					dot.visible = value;
				}
				if (value && !is8dot)
				{
					_is8dot = !value;
					is8dot = value;
				}
			}
		}
 
		/**
		 * �Ƿ���϶�
		 * @default true
		 */
		public function get dragEnable():Boolean { return _dragEnable; }
		/**
		 * �Ƿ���϶�
		 * @default true
		 */
		public function set dragEnable(value:Boolean):void 
		{
			if (_dragEnable != value)
			{
				_dragEnable = value;
 
				if (value) addChildAt(hitSprite, 0);
				else removeChild(hitSprite);
			}
		}
 		
		private function mouseOverHandler(e:MouseEvent):void 
		{
			if (e.target is Dot)
			{
				Mouse.cursor = MouseCursor.BUTTON;
			}
			else
			{
				Mouse.cursor = MouseCursor.HAND;
			}   
		}
 
		private function rollOutHandler(e:MouseEvent):void 
		{
			Mouse.cursor = MouseCursor.AUTO;
			Mouse.show();
		}
 
		private function mouseDownHandler(e:MouseEvent):void 
		{
			if(isCopy)
			{
				return;
			}
			
			if (e.target is Dot)
			{
				isMove = false;
				var dot:Dot = e.target as Dot;
				changeW = dot.lx -1;
				changeH = dot.ly -1;
			}else
			{
				isMove = true;
				//lockX = mouseX;
				//lockY = mouseY;

				//Alert.show(parent.x+", "+parent.y+", "+parent.width+", "+parent.height);
				//todo:������ק�ķ�Χ
				var range:Rectangle = new Rectangle(parent.x,parent.y,parent.width,parent.height);
				//������ק��Χ
				this.startDrag(false,null);
			}
 
			removeMouseCursorHandler();
			stage.addEventListener(MouseEvent.MOUSE_UP, mouseUpHandler);
			stage.addEventListener(MouseEvent.MOUSE_MOVE, mouseMoveHandler);
		}
 
		private function mouseUpHandler(e:MouseEvent):void 
		{
			this.stopDrag();
			stage.removeEventListener(MouseEvent.MOUSE_UP, mouseUpHandler);
			stage.removeEventListener(MouseEvent.MOUSE_MOVE, mouseMoveHandler);
			addMouseCursorHandler();
			resize(_boxWidth,_boxHeight);
		}
 
		private function mouseMoveHandler(e:MouseEvent):void 
		{
			if(isCopy)
			{
				return;
			}
			
			// �϶�
			if (isMove)
			{
				//x = parent.mouseX - lockX;
				//y = parent.mouseY - lockY;
			}
			// ����
			else
			{
				var w:Number, h:Number;
				var tx:Number = x;
				var ty:Number = y;
				if (changeW < 0)
				{
					tx = mouseX + tx;
				} 
				else if (changeW > 0) 
				{
					bottomRightX = mouseX + tx;
				}
				if (changeH < 0) 
				{
					ty = mouseY + ty;
				}
				else if (changeH > 0) 
				{
					bottomRightY = mouseY + ty;
				}
				w = bottomRightX - tx;
				h = bottomRightY - ty;
				if (0 == w || 0 == h) return;
 
				var tem:Number;
				if (w < 0)
				{
					w = -w;
					changeW = -changeW;
					tem = tx;
					tx = bottomRightX;
					bottomRightX = tem;
				}
				if (h < 0)
				{
					h = -h;
					changeH = -changeH;
					tem = ty;
					ty = bottomRightY;
					bottomRightY = tem;
				}
				super.x = tx;
				super.y = ty;
				dispatchEvent(new Event("box_move"));
				resize(w, h);
			}
 		}
		// ������ָ�봦����
		private function addMouseCursorHandler():void
		{
			addEventListener(MouseEvent.MOUSE_OVER, mouseOverHandler);
			addEventListener(MouseEvent.ROLL_OUT,  rollOutHandler);
		}
		// ɾ�����ָ�봦����
		private function removeMouseCursorHandler():void
		{
			removeEventListener(MouseEvent.MOUSE_OVER, mouseOverHandler);
			removeEventListener(MouseEvent.ROLL_OUT,  rollOutHandler);
		}
 
 		
 		public function removeMouseMoveEventHandler():void
 		{
 			isCopy = true;
 		}
 
 		public function addMouseMoveEventHandler():void
 		{
 			isCopy = false;
 		}
 
		private function initUI():void
		{
			txt = new TextField();
			txt.wordWrap = false;
			txt.multiline = false;
			txt.width = 160;
			txt.mouseEnabled = false;
			txt.text = " ";
			txt.x = x;
			txt.y = -txt.textHeight;
			addChild(txt);
 
			hitSprite = new Sprite();
			hitSprite.graphics.beginFill(0, 0);
			hitSprite.graphics.drawRect(0, 0, 10, 10);
			hitSprite.graphics.endFill();
			addChild(hitSprite);
 
			for (var i:int = 0; i < 8; i++)
			{
				var dot:Dot = new Dot();
				addChild(dot);
				dotList.push(dot);
 
				// ���� λ��
				dot.lx = i % 3;
				dot.ly = i / 3;
				if (4 == i)
				{
					dot.lx = 2;
					dot.ly = 2;
				}
			}
		}
	}
}
