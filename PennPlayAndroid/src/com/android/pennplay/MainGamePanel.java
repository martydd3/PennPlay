package com.android.pennplay;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * 
 * @author Martin
 * Follows tutorial at http://www.javacodegeeks.com/2011/07/android-game-development-basic-game_05.html
 * 
 * GamePanel holds the logic that determines how the different objects in the game interacts
 */

public class MainGamePanel extends SurfaceView 
    implements SurfaceHolder.Callback{

    private MainThread thread;
    
    //holds the size of the screen;
    private int mWidth;
    private int mHeight;
    
    //Game entities
    private Water water;
    private Ship ship;
    
    public MainGamePanel(Context context) {
        super(context);
        
        //getHolder() will call surfaceChanged(...), surfaceCreated(...) etc
        //from their body
        getHolder().addCallback(this);
        
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {     
        //initialize game entities
        water = new Water(getWidth(), getHeight(), BitmapFactory.decodeResource(getResources(), R.drawable.wave));
        ship = new Ship(getWidth()/3, getHeight()-Water.defHeight, 
                BitmapFactory.decodeResource(getResources(), R.drawable.ship));
        
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry){
            thread.setRunning(false);
            try{
                thread.join();
                retry = false;
            } catch (InterruptedException e){
                //retry is still true. Loop around and try to stop thread again
            }
        }
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event){

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            water.onClick((int)event.getX());
        }
        else if(event.getAction() == MotionEvent.ACTION_UP){

        }
        
        return super.onTouchEvent(event);
    }
    
    @Override
    protected void onDraw(Canvas canvas){
        //Log.i("mainGamePanel", "onDraw");
        canvas.drawColor(Color.BLACK);
        ship.draw(canvas);
        water.draw(canvas); 
    }
    
    public void update(){
        water.update(ship);
    }
}
