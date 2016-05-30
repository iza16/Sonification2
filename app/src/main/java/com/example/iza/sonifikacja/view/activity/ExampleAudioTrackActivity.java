package com.example.iza.sonifikacja.view.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.SeekBar;

import com.example.iza.sonifikacja.R;
import com.example.iza.sonifikacja.sound.SonificationClass;

import org.opencv.android.OpenCVLoader;

public class ExampleAudioTrackActivity extends Activity {

    public float maxVolume = 1;
    public int maxRate = 44100;
    public SeekBar seekBarSound1;
    public SeekBar seekBarSound2;
    public SeekBar seekBarSound3;
    public SeekBar seekBarSound4;
    public SeekBar seekBarSound5;
    public SeekBar seekBarSound6;
    public SeekBar seekBarSound7;
    public SeekBar seekBarSound8;
    public SeekBar seekBarSound9;
    public SeekBar seekBarSound10;
    public SeekBar seekBarSound11;
    public SeekBar seekBarSound12;
    public SonificationClass sc1;
    public SonificationClass sc2;
    public SonificationClass sc3;
    public SonificationClass sc4;
    public SonificationClass sc5;
    public SonificationClass sc6;
    public SonificationClass sc7;
    public SonificationClass sc8;
    public SonificationClass sc9;
    public SonificationClass sc10;
    public SonificationClass sc11;
    public SonificationClass sc12;
    public SurfaceView sv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_first_sonification);
        if (!OpenCVLoader.initDebug()) {
            Log.e(this.getClass().getSimpleName(), "  OpenCVLoader.initDebug(), not working.");
        } else {
            Log.d(this.getClass().getSimpleName(), "  OpenCVLoader.initDebug(), working.");
        }

        seekBarSound1 = (SeekBar) findViewById(R.id.seekBar);
        seekBarSound2 = (SeekBar) findViewById(R.id.seekBar2);
        seekBarSound3 = (SeekBar) findViewById(R.id.seekBar3);
        seekBarSound4 = (SeekBar) findViewById(R.id.seekBar4);
        seekBarSound5 = (SeekBar) findViewById(R.id.seekBar5);
        seekBarSound6 = (SeekBar) findViewById(R.id.seekBar6);
        seekBarSound7 = (SeekBar) findViewById(R.id.seekBar7);
        seekBarSound8 = (SeekBar) findViewById(R.id.seekBar8);
        seekBarSound9 = (SeekBar) findViewById(R.id.seekBar9);
        seekBarSound10 = (SeekBar) findViewById(R.id.seekBar10);
        seekBarSound11 = (SeekBar) findViewById(R.id.seekBar11);
        seekBarSound12 = (SeekBar) findViewById(R.id.seekBar12);

        sc1 = new SonificationClass(getApplicationContext(), R.raw.wektor_000);
        sc2 = new SonificationClass(getApplicationContext(), R.raw.wektor_001);
        sc3 = new SonificationClass(getApplicationContext(), R.raw.wektor_010);
        sc4 = new SonificationClass(getApplicationContext(), R.raw.wektor_011);
        sc5 = new SonificationClass(getApplicationContext(), R.raw.wektor_100);
        sc6 = new SonificationClass(getApplicationContext(), R.raw.wektor_101);
        sc7 = new SonificationClass(getApplicationContext(), R.raw.wektor_110);
        sc8 = new SonificationClass(getApplicationContext(), R.raw.wektor_111);
        sc9 = new SonificationClass(getApplicationContext(), R.raw.noise_low_freq);
        sc10 = new SonificationClass(getApplicationContext(), R.raw.noise_mid_freq);
        sc11 = new SonificationClass(getApplicationContext(), R.raw.noise_high_freq);
        sc12 = new SonificationClass(getApplicationContext(), R.raw.skladowa1);

        maxVolume = SonificationClass.getMaxVolume();
        maxRate = SonificationClass.getMaxRate();

        sc1.changeVolume(maxVolume/2);
        sc2.changeVolume(maxVolume/2);
        sc3.changeVolume(maxVolume/2);
        sc4.changeVolume(maxVolume/2);
        sc5.changeVolume(maxVolume/2);
        sc6.changeVolume(maxVolume/2);
        sc7.changeVolume(maxVolume/2);
        sc8.changeVolume(maxVolume/2);
        sc9.changeVolume(maxVolume/2);
        sc10.changeVolume(maxVolume/2);
        sc11.changeVolume(maxVolume/2);
        sc12.changeVolume(maxVolume/2);

        sc1.setIsPlaying(true);
        sc2.setIsPlaying(true);
        sc3.setIsPlaying(true);
        sc4.setIsPlaying(true);
        sc5.setIsPlaying(true);
        sc6.setIsPlaying(true);
        sc7.setIsPlaying(true);
        sc8.setIsPlaying(true);
        sc9.setIsPlaying(true);
        sc10.setIsPlaying(true);
        sc11.setIsPlaying(true);
        sc12.setIsPlaying(true);

        sc1.start();
        sc2.start();
        sc3.start();
        sc4.start();
        sc5.start();
        sc6.start();
        sc7.start();
        sc8.start();
        sc9.start();
        //sc10.start();
    //    sc11.start();
        sc12.start();

        seekBarSound1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                float progerss = seekBar.getProgress();
                sc1.changeVolume(progerss / 10);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarSound2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                float progerss = seekBar.getProgress();
                sc2.changeVolume(progerss / 10);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarSound3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                float progerss = seekBar.getProgress();
                sc3.changeVolume(progerss / 10);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarSound4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                float progerss = seekBar.getProgress();
                sc4.changeVolume(progerss / 10);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarSound5.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                float progerss = seekBar.getProgress();
                sc5.changeVolume(progerss / 10);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarSound6.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                float progerss = seekBar.getProgress();
                sc6.changeVolume(progerss / 10);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarSound7.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                float progerss = seekBar.getProgress();
                sc7.changeVolume(progerss/10);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarSound8.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                float progerss = seekBar.getProgress();
                sc8.changeVolume(progerss / 10);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarSound9.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                float progerss = seekBar.getProgress();
                sc9.changeVolume(progerss / 10);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarSound10.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                float progerss = seekBar.getProgress();
                sc10.changeVolume(progerss / 10);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarSound11.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                float progerss = seekBar.getProgress();
                sc11.changeVolume(progerss / 10);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarSound12.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                float progerss = seekBar.getProgress();
                sc12.changeVolume(progerss/10);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
}


}
