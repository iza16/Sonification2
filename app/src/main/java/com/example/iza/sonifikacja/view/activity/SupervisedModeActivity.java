package com.example.iza.sonifikacja.view.activity;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;

import com.example.iza.sonifikacja.R;
import com.example.iza.sonifikacja.sound.SonificationClass;
import com.example.iza.sonifikacja.view.viewer.MenuView;
import com.example.iza.sonifikacja.view.viewer.ModeView;

import java.io.InputStream;

public class SupervisedModeActivity extends MenuView {

    public SonificationClass sc;

    public SupervisedModeActivity(Context context) {
        super(context);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        setWindowHeight(displayMetrics.heightPixels);
        setWindowWidth(displayMetrics.widthPixels);
        sc = new SonificationClass(getContext());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        sc = new SonificationClass(getContext());
        if(event.getAction()==MotionEvent.ACTION_DOWN)
        {
            if(x <= getWindowWidth()/3)
            {
                sc.generate();
                sc.playSoundOnLine();
            }

            if(getWindowWidth()/3 < x && x < (2*getWindowWidth()/3))
            {
                sc.generate();
                sc.playAll();
            }

            if(x >= (2*getWindowWidth()/3))
            {
                sc.generate();
                sc.playSoundNearLine();
            }

        }

        return true;
    }
}
