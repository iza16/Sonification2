package com.example.iza.sonifikacja.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.example.iza.sonifikacja.R;
import com.example.iza.sonifikacja.sound.TTSClass;

public class MainActivity extends Activity {


    private TTSClass mTTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide the Title Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Hide the Status Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setWindowAnimations(android.R.style.Animation_Toast);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setTTS(new TTSClass(this));
        setContentView(new StartActivity(this.getApplicationContext(),this));

    }

    @Override
    protected void onResume() {
        mTTS.onResume();
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
