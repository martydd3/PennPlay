package com.android.pennplay;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
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

    private static MainThread thread;
    
    //Game entities
    private Water water;
    private Ship ship;
    
    private String avgFps;
    
    private int rockTime;
    private ArrayList<Rock> rocks;
    
    private Paint paint;
    private Rect rect;
    
    public static boolean endGame; 
    
    private Bitmap[] crashBits;
    
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
        crashBits = new Bitmap[2];
        crashBits[0] = BitmapFactory.decodeResource(getResources(), R.drawable.crash1);
        crashBits[1] = BitmapFactory.decodeResource(getResources(), R.drawable.crash2);
        
        water = new Water(getWidth(), getHeight(), BitmapFactory.decodeResource(getResources(), R.drawable.wave),
                BitmapFactory.decodeResource(getResources(), R.drawable.shape));
        ship = new Ship(getWidth()/2, getHeight()-Water.defHeight, 
                BitmapFactory.decodeResource(getResources(), R.drawable.ship), crashBits);
        
        rockTime = (int) (Math.random()*50)+25;
        rocks = new ArrayList<Rock>();
        
        rect = new Rect(0, Water.height-Water.defHeight, getWidth(), getHeight());
        
        paint = new Paint();
        paint.setARGB(255, 255, 255, 255);
        
        endGame = false;
  
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
            
            if(thread.mPause){
                endGame = false;
                
                rocks = new ArrayList<Rock>();
                water = new Water(getWidth(), getHeight(), BitmapFactory.decodeResource(getResources(), R.drawable.wave),
                        BitmapFactory.decodeResource(getResources(), R.drawable.shape)); 
                ship = new Ship(getWidth()/2, getHeight()-Water.defHeight, 
                        BitmapFactory.decodeResource(getResources(), R.drawable.ship), crashBits);
                
                thread.onResume();
            }
        }
        else if(event.getAction() == MotionEvent.ACTION_UP){
            Log.i("mainGamePanel", "actionUp");
            water.onRelease();
        }       
        return super.onTouchEvent(event);
    }
    
    public static void pause(){
        thread.onPause();
    }
    
    public static void start(){
        thread.onResume();
    }
    
    @Override
    protected void onDraw(Canvas canvas){
        //Log.i("mainGamePanel", "onDraw");
        canvas.drawColor(Color.BLACK);
        
        ship.draw(canvas);
        
        water.draw(canvas); 
        
        for(int i = 0; i < rocks.size(); i++){
            rocks.get(i).draw(canvas);
        }
        
        
        paint.setColor(Color.rgb(35, 29, 67));
        canvas.drawRect(rect, paint);
        
        
        paint.setColor(Color.WHITE);
        if(avgFps != null){
            canvas.drawText(avgFps, this.getWidth()-70, 20, paint);
        }
        
        canvas.drawText("score: " + ship.score, 30, 20, paint);
        
        if(endGame){
            canvas.drawText("Game Over", getWidth()/2-25, getHeight()/2 - 10, paint);
        }
    }
    
    public void update(){
        water.update(ship);
        rockTime--;
        
        if(rockTime <= 0){
            rocks.add(new Rock(getWidth()+100, (int)((Water.height-Water.defHeight-250)*Math.random()+200), -8,
                    BitmapFactory.decodeResource(getResources(), R.drawable.rock)));
            
            rockTime = (int) (Math.random()*50)+25;
        }
        
        for(int i = 0; i < rocks.size(); i++){
            Rock r = rocks.get(i);
            r.updateX();
            
            if(r.intersectsShip(ship)){
                ship.crash();
            }
            
            if(r.getX() < -200)
                rocks.remove(i);
        }
        
        ship.update();
    }
    
    public void setAvgFps(String avgFps){
        this.avgFps = avgFps;
    }
}
