
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Ship {
	private Bitmap bitmap;
	private int x;		// center x coordinate
	private int y;		// center y coordinate
	private int width;
	private int height;
	
	public Ship(int x, int y, Bitmap b) {
		this.x = x;
		this.y = y;
		bitmap = b;
		width = b.getWidth();
		height = b.getHeight();
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void draw(Canvas canvas) {
		canvas.drawBitmap(bitmap, x - width/2, y - height/2, null);
	}
}
