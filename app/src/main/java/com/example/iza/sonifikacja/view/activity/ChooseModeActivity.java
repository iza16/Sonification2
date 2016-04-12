package com.example.iza.sonifikacja.view.activity;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;

import com.example.iza.sonifikacja.R;
import com.example.iza.sonifikacja.interfaces.MenuDimensions;
import com.example.iza.sonifikacja.view.viewer.MenuView;

public class ChooseModeActivity extends MenuView {

    public boolean isFreeMode = false;
    public boolean isSupervisedMode = false;
    public boolean isTutorialMode = false;

    public ChooseModeActivity(Context context, MainActivity mainActivity) {
        super(context);

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        setWindowHeight(displayMetrics.heightPixels);
        setWindowWidth(displayMetrics.widthPixels);
        setMainActivity(mainActivity);
        getMainActivity().getTTS().speak(getContext().getString(R.string.menu_mode));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();

        if(event.getAction()==MotionEvent.ACTION_MOVE)
        {
            if(x <= getWindowWidth()/3 && !isFreeMode)
            {
                Log.i("informacja", getContext().getString(R.string.menu_free_mode));
                getMainActivity().getTTS().speak(getContext().getString(R.string.menu_free_mode));
                isFreeMode = true;
                isSupervisedMode = false;
                isTutorialMode = false;
            }

            if(getWindowWidth()/3 < x && x < (2*getWindowWidth()/3) && !isSupervisedMode)
            {
                Log.i("informacja", getContext().getString(R.string.menu_supervised_mode));
                getMainActivity().getTTS().speak(getContext().getString(R.string.menu_supervised_mode));
                isFreeMode = false;
                isSupervisedMode = true;
                isTutorialMode = false;
            }

            if(x >= (2*getWindowWidth()/3) && !isTutorialMode)
            {
                Log.i("informacja",getContext().getString(R.string.menu_tutorial_mode));
                getMainActivity().getTTS().speak(getContext().getString(R.string.menu_tutorial_mode));
                isFreeMode = false;
                isSupervisedMode = false;
                isTutorialMode = true;
            }

        }

        if(event.getAction()==MotionEvent.ACTION_DOWN)
        {
            if(x <= getWindowWidth()/3)
            {
                Log.i("informacja",getContext().getString(R.string.menu_free_mode));
                getMainActivity().getTTS().speak(getContext().getString(R.string.menu_free_mode));
            }

            if(getWindowWidth()/3 < x && x < (2*getWindowWidth()/3))
            {
                Log.i("informacja",getContext().getString(R.string.menu_supervised_mode));
                getMainActivity().getTTS().speak(getContext().getString(R.string.menu_supervised_mode));
            }

            if(x >= (2*getWindowWidth()/3))
            {
                Log.i("informacja",getContext().getString(R.string.menu_tutorial_mode));
                getMainActivity().getTTS().speak(getContext().getString(R.string.menu_tutorial_mode));
            }

        }
        if(event.getAction()==MotionEvent.ACTION_UP)
        {
            if(x <= getWindowWidth()/3)
            {
                Log.i("informacja", getContext().getString(R.string.menu_free_mode));
            }

            if(getWindowWidth()/3 < x && x < (2*getWindowWidth()/3))
            {
                Log.i("informacja", getContext().getString(R.string.menu_supervised_mode));
                getMainActivity().setContentView(new SupervisedModeActivity(getContext()));
            }

            if(x >= (2*getWindowWidth()/3))
            {
                Log.i("informacja", getContext().getString(R.string.menu_tutorial_mode));
            }

        }

        return true;
    }
}
