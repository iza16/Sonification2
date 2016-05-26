package com.example.iza.sonifikacja.view.surface;

import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;

import com.example.iza.sonifikacja.R;
import com.example.iza.sonifikacja.sound.SonificationClass;
import com.example.iza.sonifikacja.view.viewer.MenuView;
import com.example.iza.sonifikacja.view.viewer.ModeView;

import java.io.InputStream;

public class SupervisedModeSurfaceView extends MenuView {

    private Context context;
    public SupervisedModeSurfaceView(Context context) {
        super(context);
        this.context = context;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        setWindowHeight(displayMetrics.heightPixels);
        setWindowWidth(displayMetrics.widthPixels);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();

        if(event.getAction()==MotionEvent.ACTION_DOWN)
        {

        }

        return true;
    }
}
