package astion
{
	import flash.display.Sprite;
 
	// ¿ØÖÆµã
	public class Dot extends Sprite
	{
	 
		public var lx:int;
		public var ly:int;
	 
		public function Dot()
		{
			graphics.lineStyle(1, 0xFF0000);
			graphics.beginFill(0xFF0000);
			graphics.drawRect( -3, -3, 6, 6);
			graphics.endFill();
			cacheAsBitmap = true;
		}
	}
}