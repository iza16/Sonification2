package com.example.iza.sonifikacja.view.activity;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;

import com.example.iza.sonifikacja.R;
import com.example.iza.sonifikacja.interfaces.MenuDimensions;
import com.example.iza.sonifikacja.view.viewer.MenuView;

public class StartActivity extends MenuView{

    public boolean isAutosonification = false;
    public boolean isInteractiveSonification = false;
    public boolean isConfiguration = false;

    public StartActivity(Context context, MainActivity mainActivity) {
        super(context);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        setWindowHeight(displayMetrics.heightPixels);
        setWindowWidth(displayMetrics.widthPixels);
        setMainActivity(mainActivity);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();

        if(event.getAction()==MotionEvent.ACTION_MOVE)
        {
            if(x <= getWindowWidth()/3 && !isAutosonification)
            {
                Log.i("informacja", getContext().getString(R.string.menu_autosonification));
                getMainActivity().getTTS().speak(getContext().getString(R.string.menu_autosonification));
                isAutosonification = true;
                isInteractiveSonification = false;
                isConfiguration = false;
            }

            if(getWindowWidth()/3 < x && x < (2*getWindowWidth()/3) && !isInteractiveSonification)
            {
                Log.i("informacja", getContext().getString(R.string.menu_interactive_sonification));
                getMainActivity().getTTS().speak(getContext().getString(R.string.menu_interactive_sonification));
                isAutosonification = false;
                isInteractiveSonification = true;
                isConfiguration = false;
            }

            if(x >= (2*getWindowWidth()/3) && !isConfiguration)
            {
                Log.i("informacja",getContext().getString(R.string.menu_configuration));
                getMainActivity().getTTS().speak(getContext().getString(R.string.menu_configuration));
                isAutosonification = false;
                isInteractiveSonification = false;
                isConfiguration = true;
            }

        }

        if(event.getAction()==MotionEvent.ACTION_DOWN)
        {
            if(x <= getWindowWidth()/3)
            {
                Log.i("informacja",getContext().getString(R.string.menu_autosonification));
                getMainActivity().getTTS().speak(getContext().getString(R.string.menu_autosonification));
            }

            if(getWindowWidth()/3 < x && x < (2*getWindowWidth()/3))
            {
                Log.i("informacja",getContext().getString(R.string.menu_interactive_sonification));
                getMainActivity().getTTS().speak(getContext().getString(R.string.menu_interactive_sonification));
            }

            if(x >= (2*getWindowWidth()/3))
            {
                Log.i("informacja",getContext().getString(R.string.menu_configuration));
                getMainActivity().getTTS().speak(getContext().getString(R.string.menu_configuration));
            }

        }
        if(event.getAction()==MotionEvent.ACTION_UP)
        {
            if(x <= getWindowWidth()/3)
            {
                Log.i("informacja", getContext().getString(R.string.menu_autosonification));
            }

            if(getWindowWidth()/3 < x && x < (2*getWindowWidth()/3))
            {
                Log.i("informacja", getContext().getString(R.string.menu_interactive_sonification));
                getMainActivity().setContentView(new ChooseModeActivity(getContext(), getMainActivity()));
            }

            if(x >= (2*getWindowWidth()/3))
            {
                Log.i("informacja", getContext().getString(R.string.menu_configuration));
            }

        }

        return true;
    }
}
