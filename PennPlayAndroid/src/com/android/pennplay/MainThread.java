package com.android.pennplay;

import java.text.DecimalFormat;

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
    
    //Displaying FPS
    private DecimalFormat df = new DecimalFormat("0.##");
    private final static int STAT_INTERVAL = 1000;
    private final static int FPS_HISTORY_NR = 10;
    private long lastStatusStore = 0;
    private long statusIntervalTimer = 0l;
    private long totalFramesSkipped = 0l;
    private long framesSkippedPerStatCycle = 0l;
    
    private int frameCountPerStatCycle = 0;
    private long totalFrameCount = 0l;
    private double fpsStore[];
    private long statsCount = 0;
    private double averageFps = 0.0;
    
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
        
        fpsStore = new double[FPS_HISTORY_NR];
        for(int i = 0; i < FPS_HISTORY_NR; i++){
            fpsStore[i]=0;
        }
        
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
                    
                    if(framesSkipped > 0)
                        Log.d("MainThread", "Skipped: " + framesSkipped);
                    
                    framesSkippedPerStatCycle += framesSkipped;
                    
                    frameCountPerStatCycle ++;
                    totalFrameCount ++;
                    
                    statusIntervalTimer += (System.currentTimeMillis() - statusIntervalTimer);
                    
                    if(statusIntervalTimer >= lastStatusStore + STAT_INTERVAL){
                        double actualFps = (double)(frameCountPerStatCycle/(STAT_INTERVAL/1000));
                        fpsStore[(int)statsCount%FPS_HISTORY_NR]=actualFps;
                        statsCount ++;
                        
                        double totalFps = 0;
                        for(int i = 0; i < FPS_HISTORY_NR; i++){
                            totalFps += fpsStore[i];
                        }
                        
                        if(statsCount < FPS_HISTORY_NR){
                            averageFps = totalFps/statsCount;
                        }
                        else{
                            averageFps = totalFps/FPS_HISTORY_NR;
                        }
                        
                        totalFramesSkipped += framesSkippedPerStatCycle;
                        framesSkippedPerStatCycle = 0;
                        statusIntervalTimer = 0;
                        frameCountPerStatCycle = 0; 
                        
                        statusIntervalTimer = System.currentTimeMillis();
                        lastStatusStore = statusIntervalTimer;
                        
                        mGamePanel.setAvgFps("FPS: " + df.format(averageFps));
                    }
                }
            } finally {
                if(canvas != null)
                    mSurfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }
}
