package com.android.pennplay;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.media.MediaPlayer;

public class Water {

    private Paint paint;
    public static int height;
    public static int width;
    
    public static int defHeight;
    
    private Bitmap mWave;
    private Bitmap waveShape;
    
    private ArrayList<Wave> waves;
    private Wave curWave;
    
    public Water(int w, int h, Bitmap wave, Bitmap wShape){
        width = w;
        height = h;
        
        defHeight = height/4;
        
        paint = new Paint();
        paint.setColor(Color.rgb(35, 29, 67));
        
        mWave = wave;
        waveShape = wShape;
        waves = new ArrayList<Wave>();
    }
    
    public void update(Ship ship){
        ship.resetRidden();
        for(int i = 0; i < waves.size(); i++){
            Wave w = waves.get(i);
            w.update();
            
            if(w.getHeight() > height - defHeight + 50 || w.getLoc() < -w.getBitmap().getWidth()){
                waves.remove(i); 
            }
            //collision detection with ship
            ship.rideWave(w);
        }
    }
    
    public void draw(Canvas canvas){
        for(int i = 0; i < waves.size(); i++){
            waves.get(i).draw(canvas);
        }
    }
    
    public void onClick(int x){

        /*
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw);
		mp.start();                                 // starts wave sound, sound is from https://www.youtube.com/watch?v=4gGuoP8F10g
	    */
	
        if(curWave != null){
            curWave.setRising(false);
        }  
        
        curWave = new Wave(x, mWave, waveShape);
        waves.add(curWave);
    }
    
    public void onRelease(){
        if(curWave != null){
            curWave.setRising(false);
            curWave = null;
        }  
    }
}
