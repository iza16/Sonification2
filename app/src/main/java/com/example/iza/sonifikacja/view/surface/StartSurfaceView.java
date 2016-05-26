package com.example.iza.sonifikacja.view.surface;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.example.iza.sonifikacja.R;
import com.example.iza.sonifikacja.view.activity.ExampleAudioTrackActivity;
import com.example.iza.sonifikacja.view.activity.MainActivity;
import com.example.iza.sonifikacja.view.activity.FreeModeSonificationActivity;
import com.example.iza.sonifikacja.view.thread.DrawThread;
import com.example.iza.sonifikacja.view.viewer.MenuView;

public class StartSurfaceView extends MenuView{

    public boolean isAutosonification = false;
    public boolean isInteractiveSonification = false;
    public boolean isConfiguration = false;
    private SurfaceHolder holder;
    private DrawThread drawThread;
    private Paint paint;

    public StartSurfaceView(Context context, MainActivity mainActivity) {
        super(context);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        setWindowHeight(displayMetrics.heightPixels);
        setWindowWidth(displayMetrics.widthPixels);
        setMainActivity(mainActivity);
        drawThread = new DrawThread(this);
        paint = new Paint();
        this.holder = getHolder();
        this.holder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                drawThread.setRunning(false);
                while (retry) {
                    try {
                        drawThread.join();
                        retry = false;
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                drawThread.setRunning(true);
                drawThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
            }
        });
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
                Intent intent = new Intent(getMainActivity(), FreeModeSonificationActivity.class);
                drawThread.setRunning(false);
                getMainActivity().startActivity(intent);
            }

            if(getWindowWidth()/3 < x && x < (2*getWindowWidth()/3))
            {
                Log.i("informacja", getContext().getString(R.string.menu_interactive_sonification));
               // getMainActivity().setContentView(new ChooseModeSurfaceView(getContext(), getMainActivity()));
            }

            if(x >= (2*getWindowWidth()/3))
            {
                Log.i("informacja", getContext().getString(R.string.menu_configuration));
                Intent intent = new Intent(getMainActivity(), ExampleAudioTrackActivity.class);
                getMainActivity().startActivity(intent);
            }

        }
        return true;
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        int x = getWindowWidth();
        int y= getWindowHeight();
        paint.setColor(Color.WHITE);
        paint.setTextSize(50);
        canvas.drawLine(x / 3, 0, x / 3, y, paint);
        canvas.drawLine((2*x)/3,0,(2*x)/3,y,paint);
        canvas.drawText("Sonifikacja",x/9,y/2,paint);
        canvas.drawText("Suwaki",(7*x)/9,y/2,paint);
    }
}
