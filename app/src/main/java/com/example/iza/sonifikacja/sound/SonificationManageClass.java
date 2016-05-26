package com.example.iza.sonifikacja.sound;

import android.content.Context;
import android.util.Log;

import com.example.iza.sonifikacja.R;
import com.example.iza.sonifikacja.pictures.parameters.Parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Iza on 2016-04-26.
 */
public class SonificationManageClass {

    private static ArrayList<SonificationClass> scList = new ArrayList<>();
    private float maxVolume = 1;
    private int maxRate = 44100;

    public SonificationManageClass(Context context) {
        if(scList.isEmpty()) {
            scList.add(0, new SonificationClass(context, R.raw.wektor_000));
            scList.add(1, new SonificationClass(context, R.raw.wektor_001));
            scList.add(2, new SonificationClass(context, R.raw.wektor_010));
            scList.add(3, new SonificationClass(context, R.raw.wektor_011));
            scList.add(4, new SonificationClass(context, R.raw.wektor_100));
            scList.add(5, new SonificationClass(context, R.raw.wektor_101));
            scList.add(6, new SonificationClass(context, R.raw.wektor_110));
            scList.add(7, new SonificationClass(context, R.raw.wektor_111));
            scList.add(8, new SonificationClass(context, R.raw.cisza));
            scList.add(9, new SonificationClass(context, R.raw.cisza));
            scList.add(10, new SonificationClass(context, R.raw.cisza));
            scList.add(11, new SonificationClass(context, R.raw.cisza));
        }
        maxVolume = SonificationClass.getMaxVolume();
        maxRate = SonificationClass.getMaxRate();

        setVolumeMin();
        playAll();
    }

   public void moduleSounds(List<Parameter> paramList){
/*
        for(int i = 0; i < scList.size(); i++) {
            scList.get(i).changeRate((int) paramList.get(i).getFrequency()*maxRate*1/2);
            scList.get(i).changeVolume(paramList.get(i).getAmplitude()*maxVolume);
        }
*/
       scList.get(0).changeVolume(paramList.get(0).getAmplitude()*maxVolume);
       if(paramList.get(0).getAmplitude() == 0){
           Log.i("dd", "00");
       }
    }

    public void setVolumeMin(){
        for(int i = 0; i < scList.size(); i++) {
            scList.get(i).changeVolume(0);
        }
    }
    private void playAll(){
        for(SonificationClass sc : scList) {
            sc.setIsPlaying(true);
            if(sc.getState() == Thread.State.NEW) {
                sc.start();
            }
        }
    }

    public void turnOffAll(){
       setVolumeMin();
    }
}
