package com.android.pennplay;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;

public class Wave {

    private int mX;
    private int mHeight;
    private int maxHeight;
    private int mV;
    
    private Bitmap mWave;
    private Bitmap mShape;
    
    private boolean mRising;
    
    public Wave(int x, Bitmap wave, Bitmap shape){  
        mX = x - wave.getWidth()/2;
        mHeight = Water.height-Water.defHeight+30;
        mWave = wave;
        mShape = shape;
        mRising = true;
        mV = -10;
        
        maxHeight = Water.height-Water.defHeight - mShape.getHeight()+100;
    }
    
    public void draw(Canvas canvas){
        canvas.drawBitmap(mWave, mX, mHeight, null);
    }
    
    public void update(){
        if(mRising && mHeight > maxHeight){
            mHeight -= 10;
        }
        else if(!mRising){
            mHeight += 6;
        }
        
        mX += mV;
    }
    
    public void setRising(Boolean b){
        mRising = b;
    }
    
    public int moveShip(Ship ship){
        int shipX =ship.getX() - mX;
        int shipY = ship.getY()-mHeight;

        if(shipX >= 0 && shipX < mShape.getWidth() && shipY >= 0 && shipY < mShape.getHeight()){
            int[] pixels = new int[mShape.getHeight()];
            Bitmap column = Bitmap.createBitmap(mShape, shipX, 0, 1, mShape.getHeight());
            column.getPixels(pixels, 0, column.getWidth(), 0, 0, 1, column.getHeight());
            
            if(shipX > mShape.getWidth()/3)
                mV = -6;
            
            int i = 0;
            while(i < pixels.length && pixels[i] == Color.TRANSPARENT)
                i++;
            
            //if new height is above waterline
            if(mHeight + i < Water.height-Water.defHeight+20){
                return mHeight + i+20;
            }      
            else{
                return Water.height-Water.defHeight+20;
            }
        }          
        else{
            return Water.height- Water.defHeight+20;
        }
    }
    
    public boolean inWave(Ship ship){
        return ship.getX() > mX && ship.getX()-mX < mShape.getWidth();
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
}
