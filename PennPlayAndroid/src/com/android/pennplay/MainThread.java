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
    
    //FPS stuff
    private final static int MAX_FPS = 50;
    private final static int MAX_FRAME_SKIPS=5;
    private final static int FRAME_PERIOD = 1000/MAX_FPS;
    
    
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
        long beginTime;
        long timeDiff;
        int sleepTime;
        int framesSkipped;
        
        sleepTime = 0;
        
        while(mRunning){
            //update game state
            
            //render state
            Canvas canvas = null;
            //try locking canvas for exclusive pixel editing on surface
            try{
                canvas = mSurfaceHolder.lockCanvas();
                synchronized(mSurfaceHolder){
                    beginTime = System.currentTimeMillis();
                    framesSkipped = 0;
                    
                    mGamePanel.update();
                    mGamePanel.onDraw(canvas);
                    
                    timeDiff = System.currentTimeMillis() - beginTime;
                    sleepTime = (int)(FRAME_PERIOD - timeDiff);
                    
                    if(sleepTime > 0){
                        try{
                            Thread.sleep(sleepTime);
                        } catch(InterruptedException e){ }
                    }
                    
                    while(sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS){
                        mGamePanel.update();
                        sleepTime += FRAME_PERIOD;
                        framesSkipped ++;
                    }
                }
            } finally {
                if(canvas != null)
                    mSurfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }
}
