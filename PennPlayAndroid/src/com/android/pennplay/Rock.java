package com.android.pennplay;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;

public class Rock {
	private Bitmap bitmap;	// currently assumes bitmap is rectangular
	private int x;		// center x coordinate, can update
	private int y;		// center y coordinate, should be constant
	private int width;
	private int height;
	private int vx; 	// velocity of rock in x direction
	
	public Rock(int x, int y, int vx, Bitmap b) {
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
	
	public boolean intersectsShip(Ship s) {
	    int shipX =s.getX() - x;
        int shipY = s.getY()-y;

        if(shipX >= 0 && shipX < width && shipY >= 0 && shipY < height){
            int color = bitmap.getPixel(shipX, shipY);
            
            if(color == Color.TRANSPARENT)
                return true;
        }          
        return false;
	}
	
	public void updateX() {
		x = x + vx;
	}
	
	public void draw(Canvas canvas) {
		canvas.drawBitmap(bitmap, x - width/2, y, null);
	}
}

