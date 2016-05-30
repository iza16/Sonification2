package com.example.iza.sonifikacja.view.thread;

import android.graphics.Canvas;
import android.view.SurfaceView;

import com.example.iza.sonifikacja.view.surface.ImageSurfaceView;

/**
 * Created by Iza on 2016-04-26.
 */
public class DrawThread  extends Thread {

    static final long FPS = 10;
    private SurfaceView view;


    private boolean running = false;

    public DrawThread(SurfaceView view) {
        this.view = view;

    }


    public void setRunning(boolean run) {
        running = run;
    }

    @Override
    public void run() {
        long ticksPS = 1000 / FPS;
        long startTime;
        long sleepTime;

        while (running) {
            Canvas c = null;
            startTime = System.currentTimeMillis();
            try {
                c = view.getHolder().lockCanvas();
                synchronized (view.getHolder()) {
                    if (c!=null)  view.draw(c);


                }
            } finally {
                if (c != null) {
                    try {
                        view.getHolder().unlockCanvasAndPost(c);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }

            sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
            try {
                if (sleepTime > 0)
                    sleep(sleepTime);
                else
                    sleep(10);

            } catch (Exception e) {
            }

        }

    }

}