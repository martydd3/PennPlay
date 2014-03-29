package com.android.pennplay;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * 
 * @author Martin
 * MainThread handles calling the MainGamePanel's update and onDraw functions
 * starts when MainGamePanel is created in SurfaceCreated
 */

public class MainThread extends Thread{

    private SurfaceHolder mSurfaceHolder;
    private MainGamePanel mGamePanel;
    
    public MainThread(SurfaceHolder surfaceHolder, MainGamePanel gamePanel){
        super();
        this.mSurfaceHolder = surfaceHolder;
        this.mGamePanel = gamePanel;
    }
    
    //holds the state of game
    private boolean mRunning;
    public void setRunning(boolean running){
        mRunning = running;
    }
    
    @SuppressLint("WrongCall")
    @Override
    public void run(){
        while(mRunning){
            //update game state
            
            //render state
            Canvas canvas = null;
            //try locking canvas for exclusive pixel editing on surface
            try{
                canvas = mSurfaceHolder.lockCanvas();
                synchronized(mSurfaceHolder){
                    //Log.i("MainThread", "draw");
                    mGamePanel.update();
                    mGamePanel.onDraw(canvas);
                }
            } finally {
                if(canvas != null)
                    mSurfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }
}
