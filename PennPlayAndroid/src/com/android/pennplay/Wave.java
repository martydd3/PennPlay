package com.android.pennplay;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;

public class Wave {

    private int mX;
    private int mHeight;
    
    private Bitmap mWave;
    
    private boolean mRising;
    
    public Wave(int x, Bitmap wave){
        mX = x - wave.getWidth()/2;
        mHeight = Water.height-Water.defHeight;
        mWave = wave;
        mRising = true;
    }
    
    public void draw(Canvas canvas){
        canvas.drawBitmap(mWave, mX, mHeight, null);
    }
    
    public void update(){
        if(mRising && mHeight > 0){
            mHeight -= 10;
        }
        else if(!mRising){
            mHeight += 10;
        }
        
        mX -= 15;
    }
    
    public void setRising(Boolean b){
        mRising = b;
    }
    
    public void moveShip(Ship ship){
        if(ship.getX() > mX && ship.getX()-mX < mWave.getWidth()){
            int shipX =ship.getX() - mX;
            int shipY = ship.getY()-mHeight;

            if(shipX >= 0 && shipX < mWave.getWidth() && shipY >= 0 && shipY < mWave.getHeight()){
                int[] pixels = new int[mWave.getHeight()];
                Bitmap column = Bitmap.createBitmap(mWave, shipX, 0, 1, mWave.getHeight());
                column.getPixels(pixels, 0, column.getWidth(), 0, 0, 1, column.getHeight());
                
                int i = 0;
                while(i < pixels.length && pixels[i] == Color.TRANSPARENT)
                    i++;
                
                if(mHeight + i < Water.height-Water.defHeight-20)
                    ship.setY(mHeight + i+20);
                else
                    ship.setY(Water.height-Water.defHeight+20);
            }
        }
        else{
            ship.setY(Water.height-Water.defHeight+20);
        }
    }
    
    public int getHeight(){
        return mHeight;
    }
    
    public int getLoc(){
        return mX;
    }
    
    public Bitmap getBitmap() {
        return mWave;
    }
    public boolean inBoundingBox(Ship s) {
    	int sx1 = s.getX();		
	int sy1 = s.getY(); 
	int rx1 = mX - mWave.getWidth()/2;
	int rx2 = mX + mWave.getWidth()/2;
	int ry1 = Water.height - Water.defHeight - mHeight;
	int ry2 = Water.height - Water.defHeight - mHeight + mWave.getHeight();
	if (sx1 <= rx1 || sx1 >= rx2 || sy1 >= ry2 || sy1 <= ry1) {
		return false;
	}
	else {
		return true;
	}
    }    
}
