package com.android.pennplay;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Ship {
	private Bitmap currentBitmap;	// currently assumes bitmap is rectangular
	private Bitmap[] bitmaps;
	private int x;		// center x coordinate
	
	private int y;		// center y coordinate
	private int tempY;  //holds the height of the highest wave ridden
	private boolean waveRidden;
	
	private int width;
	private int height;
	private int state;	// current state
	private int level;	// state 0
	private int rising;  // state 1
	private int falling;  // state 2
	
	public boolean updated;   //holds whether ship has already been changed by a wave
	
	public Ship(int x, int y, Bitmap b) {
		this.x = x;
		this.y = y+20;
		currentBitmap = b;
		width = b.getWidth();
		height = b.getHeight();
		state = 0;
		level = 0;
		rising = 1;
		falling = 2;
		
		updated = false;
	}
	
	public void rideWave(Wave w)
	{
	    if(w.inWave(this)){
	        int tY = w.moveShip(this);
	        if(tY < tempY)
	            tempY = tY;
	    }
	}
	
	public void resetRidden()
	{
	    y = tempY;	    
	    tempY = Water.height-Water.defHeight+20;
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
		canvas.drawBitmap(currentBitmap, x - width/2, y - height, null);
	}
	
	public Bitmap getCurrentBitmap() {
		return currentBitmap;
	}
	
	public void updateCurrentBitmap() {
		if (state == 0) {
			currentBitmap = bitmaps[0];
		}
		else if (state == 1) {
			currentBitmap = bitmaps[1];
		}
		else if (state == 2) {
			currentBitmap = bitmaps[2];
		}
	}
}
