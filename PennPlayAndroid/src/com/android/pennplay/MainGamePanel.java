package com.android.pennplay;

import android.content.Context;
import android.graphics.Canvas;
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
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry){
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

        //handle user interface here
        
        return super.onTouchEvent(event);
    }
    
    @Override
    protected void onDraw(Canvas canvas){
        //do drawing here, called by MainThread
        //MainThread handles frame rate and skipped frames
    }
    
    public void update(){
        //Perform calculations of game elements needed for every game cycle here
    }
}
