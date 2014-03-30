package com.android.pennplay;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public class Ship {
	private Bitmap currentBitmap;	// currently assumes bitmap is rectangular
	private Bitmap[] bitmaps;
	private int x;		// center x coordinate
	
	private int y;		// center y coordinate
	private int tempY;  //holds the height of the highest wave ridden
	
	private int width;
	private int height;
	private int state;	// current state
	
	public boolean updated;   //holds whether ship has already been changed by a wave
	
	public boolean crashed; 
	
	private int timer;
	public int score;
	
	public Ship(int x, int y, Bitmap b, Bitmap[] maps) {
		this.x = x;
		this.y = y;
		currentBitmap = b;
		width = b.getWidth();
		height = b.getHeight();
		state = 0;
		bitmaps = maps;
		
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
	
	public void update(){
	    timer = (timer+1)%100;
	    
	    if(timer%10 == 0 && !crashed)
	        score ++;
	    
	    if(crashed){
	        MainGamePanel.endGame = true;
	        if(timer < 5){
	            state = 0;
	            updateCurrentBitmap();
	        }
	        if(timer < 10){
                state = 1;
                updateCurrentBitmap();
            }
	        
	        if(timer >= 10){
	            state = 2;
	            MainGamePanel.pause();
	            Log.i("ship", "pause");
	        }
	    }
	}
	
	public void crash(){
	    crashed = true;
	    timer = 0;
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
	    if(state != 2)
	        canvas.drawBitmap(currentBitmap, x - width/2, y - height-5, null);
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
	}
}
