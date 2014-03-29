package com.android.pennplay;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public class Wave {

    private int mX;
    private int mHeight;
    
    private Bitmap mWave;
    
    private boolean mRising;
    
    public Wave(int x, Bitmap wave){
        mX = x;
        mHeight = 0;
        mWave = wave;
        mRising = true;
    }
    
    public void draw(Canvas canvas){
        Log.i("Wave", "draw");
        canvas.drawBitmap(mWave, mX-(mWave.getWidth()/2), Water.height - Water.defHeight - mHeight, null);
    }
    
    public void update(){
        if(mRising){
            mHeight += 10;
        }
        else{
            mHeight -= 10;
        }
        
        mX -= 5;
    }
    
    public void setRising(Boolean b){
        mRising = b;
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
