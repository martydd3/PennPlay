package com.example.pennplay;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Cloud {
	private Bitmap bitmap;
	private int x;		// center x coordinate
	private int y;		// center y coordinate
	private int width;
	private int height;
	private int vx; 	// velocity of rock in x direction

	
	public Cloud(int x, int y, int vx, Bitmap b) {
		this.x = x;
		this.y = y;
		bitmap = b;
		width = b.getWidth();
		height = b.getHeight();
		this.vx = vx;
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
	
	public boolean inBoundingBox(Ship s) {
		
		int sx1 = s.getX() - s.getWidth()/2; //left x
		int sx2 = s.getX() + s.getWidth()/2; //right x
		int sy1 = s.getY() - s.getHeight()/2; // bottom y
		int sy2 = s.getY() + s.getHeight()/2; // top y
		int rx1 = x - width/2;
		int rx2 = x + width/2;
		int ry1 = y - height/2;
		int ry2 = y + height/2;
		if (sx2 < rx1 || sx1 > rx2 || sy1 > ry2 || sy2 < ry1) {
			return false;
		}
		else {
			return true;
		}
		
	}
	
	public void updateX() {
		x = x + vx;
	}
	
	public void draw(Canvas canvas) {
		canvas.drawBitmap(bitmap, x - width/2, y - height/2, null);
	}
}
