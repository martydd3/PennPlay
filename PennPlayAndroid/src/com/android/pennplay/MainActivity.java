package com.android.pennplay;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * 
 * @author Martin
 * Main activity of the app, sets up the user interface
 */

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //Turn window title off
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //set window to full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(new MainGamePanel(this));
    }
}
