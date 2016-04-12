package com.example.iza.sonifikacja.view.viewer;

import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceView;

/**
 * Created by Iza on 2016-04-11.
 */
public abstract class ModeView extends SurfaceView {

    public ModeView(Context context) {
        super(context);
    }

    @Override
    public abstract boolean onTouchEvent(MotionEvent event);
}
