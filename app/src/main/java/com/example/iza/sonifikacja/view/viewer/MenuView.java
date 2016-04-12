package com.example.iza.sonifikacja.view.viewer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceView;

import com.example.iza.sonifikacja.interfaces.MenuDimensions;
import com.example.iza.sonifikacja.view.activity.MainActivity;

/**
 * Created by Iza on 2016-04-11.
 */
public abstract class MenuView extends SurfaceView implements MenuDimensions{


    private int windowWidth;
    private int windowHeight;
    private MainActivity mainActivity;

    public MenuView(Context context) {
        super(context);
    }

    @Override
    public abstract boolean onTouchEvent(MotionEvent event);

    public int getWindowWidth() {
        return windowWidth;
    }

    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }


    public int getWindowHeight() {
        return windowHeight;
    }

    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }

    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }


}
