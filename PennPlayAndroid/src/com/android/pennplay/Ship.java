package com.android.pennplay;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Ship {
	private Bitmap currentBitmap;	// currently assumes bitmap is rectangular
	private Bitmap[] bitmaps;
	private int x;		// center x coordinate
	private int y;		// center y coordinate
	private int width;
	private int height;
	private int state;	// current state
	private int level;	// state 0
	private int rising;  // state 1
	private int falling;  // state 2
	
	public Ship(int x, int y, Bitmap b) {
		this.x = x;
		this.y = y;
		currentBitmap = b;
		width = b.getWidth();
		height = b.getHeight();
		state = 0;
		level = 0;
		rising = 1;
		falling = 2;
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
		canvas.drawBitmap(currentBitmap, x - width/2, y - height/2, null);
	}
	
	public Bitmap getCurrentBitmap() {
		return currentBitmap;
	}
	
	// updates location of this ship to be on the wave
	// changes bitmap of this ship
	public void changeState(Wave w) {
		if (w.inBoundingBox(this)) {
			Bitmap bWave = w.getBitmap();
			int yWave = Water.height - Water.defHeight - w.getHeight();
			try {
				int adjustedWidth = x - (w.getLoc() - bWave.getWidth()/2);
				int adjustedHeight = y - Water.height - Water.defHeight - w.getHeight();
				int color = bWave.getPixel(adjustedWidth, adjustedHeight);
				while (color != Color.TRANSPARENT) { // not sure which color constant to pick...
					if (y > yWave) {
						y--;
						state = rising;
					}
					else if (y == yWave) {
						y++;
						state = level;
					}
					else {
						y++;
						state = falling;
					}
					adjustedHeight = y - Water.height - Water.defHeight - w.getHeight();
					color = bWave.getPixel(adjustedWidth, adjustedHeight);
				}
			}
			catch (IllegalArgumentException e) {
			}
		}
		else {
		}
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
