package com.android.pennplay;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class Water {

    //holds the maximum width and height allowed
    private int mWidth;
    private int mHeight;
    
    private double mDefHeight;
    
    private boolean mAddingWater;
    private int mAddLoc;
    private int pressedTime;
    
    private Paint paint;
    
    private double[] depth;
    
    public Water(int width, int height){
        mWidth = width;
        mHeight = height;
        
        depth = new double[mWidth];
        mDefHeight = mHeight/4.0;
        
        mAddingWater = false;
        mAddLoc = 0;
        pressedTime = 0;
        
        for(int i = 0; i < depth.length; i++){
            depth[i] = mDefHeight;
        }
        
        paint = new Paint();
        paint.setColor(Color.BLUE);
    }
    
    public void draw(Canvas canvas){
        //Log.i("water", "draw " + depth.length);
        for(int i = 0; i < depth.length; i++){
            canvas.drawRect(i, mHeight-(int)depth[i], (i+1), mHeight, paint);
        }
    }
    
    public void update(){
        if(mAddingWater)
            pressedTime +=10;
        
        //wavelength: 100px
        //waveheight: mHeight - mDefHeight
        
        for(int i = -200; i < 200; i++){
            int index = i + mAddLoc;
            double height = (mHeight-mDefHeight)*Math.cos(i/(20*Math.PI))+2*mDefHeight-mHeight+pressedTime;

            if(index < depth.length && index >= 0 && height >= depth[index]){
                depth[index] = height;
            }
        }
        
        for(int i = 0; i < depth.length; i++){
            if(depth[i] > mDefHeight)
                depth[i] -= 2;
            
            if(depth[i] < mDefHeight)
                depth[i] = mDefHeight;
        }
    }
    
    public void onPressed(float x){
        mAddingWater = true;
        mAddLoc = (int)x; 
        pressedTime = 0;
    }
    
    public void onRelease(){
        mAddingWater = false;
        mAddLoc = 0;
    }
}
