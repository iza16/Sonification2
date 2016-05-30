package com.example.iza.sonifikacja.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;

import com.example.iza.sonifikacja.server.DownloadImageClass;
import com.example.iza.sonifikacja.sound.TTSClass;
import com.example.iza.sonifikacja.view.surface.StartSurfaceView;

public class MainActivity extends Activity {


    private TTSClass mTTS;
    public static DownloadImageClass downloadImageClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide the Status Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setWindowAnimations(android.R.style.Animation_Toast);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setTTS(new TTSClass(this));

        downloadImageClass = new DownloadImageClass(getApplicationContext());


        setContentView(new StartSurfaceView(this.getApplicationContext(), this));
        Log.i("","");

    }

    @Override
    protected void onResume() {
        mTTS.onResume();
        setContentView(new StartSurfaceView(this.getApplicationContext(), this));
        super.onResume();
    }

    @Override
    protected void onPause() {
        mTTS.onPause();
        super.onPause();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mTTS != null)
            if (mTTS.onActivityResult(requestCode, resultCode, data))
                return;
    }


    @Override
    protected void onDestroy() {
        if (mTTS != null)
            mTTS.onDestroy();

        super.onDestroy();
    }

    public TTSClass getTTS() {
        return mTTS;
    }

    public void setTTS(TTSClass mTTS) {
        this.mTTS = mTTS;
    }
}
