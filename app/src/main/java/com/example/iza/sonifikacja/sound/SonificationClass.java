package com.example.iza.sonifikacja.sound;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Build;

import com.example.iza.sonifikacja.R;
import com.example.iza.sonifikacja.interfaces.SonificationDimentions;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * Created by Iza on 2016-04-11.
 */
public class SonificationClass extends Thread implements SonificationDimentions {

    private final int sampleRate = 44100;
    private byte generatedSound[];
    private boolean isPlaying = false;
    private AudioTrack sound;
    private  InputStream is;
    Context context;
    int sklad;

    public SonificationClass(Context c, int sklad1) {
        sklad = sklad1;
        this.context = c;
        is = context.getResources().openRawResource(sklad);
        try {


            sound = new AudioTrack(AudioManager.STREAM_MUSIC,
                    sampleRate, AudioFormat.CHANNEL_OUT_MONO,
                    AudioFormat.ENCODING_PCM_16BIT, is.available()/2,
                    AudioTrack.MODE_STREAM);

            generateSoundFromFile();
            writeToBuffer();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void generateSoundFromFile() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String s;

        try {

            BufferedInputStream bis = new BufferedInputStream(is, 8000);
            DataInputStream dis = new DataInputStream(bis);      //  Create a DataInputStream to read the audio data from the saved file

            while (dis.available() > 0) {
                byteArrayOutputStream.write(is.read());
            }
            s = byteArrayOutputStream.toString();
            generatedSound = new byte[s.length()/2];
            int j =0;
            for (int i = 0; i<s.length();i=i+2){
                byte b = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                        + Character.digit(s.charAt(i+1), 16));
                generatedSound[j++] = b;
            }
            dis.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToBuffer() {
        sound.write(generatedSound, 0, generatedSound.length);
    }


    @Override
    public void run() {
        boolean isPl = true;
        while (true){
            if(isPlaying) {
                writeToBuffer();
                sound.play();
                if(sklad == R.raw.skladowa1) {
                    if (isPl) {
                        is = context.getResources().openRawResource(R.raw.skladowa1);
                        generateSoundFromFile();
                        isPl = false;
                    } else {
                        is = context.getResources().openRawResource(R.raw.noise_high_freq);
                        generateSoundFromFile();
                        isPl = true;
                    }
                }
            }
        }

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void changeVolume(float vol){
        sound.setVolume(vol);
    }

    public void changeRate(int rate) {
        sound.setPlaybackRate(rate*MAX_RATE);
    }

    public static float getMaxVolume(){
        return AudioTrack.getMaxVolume();
    }

    public static int getMaxRate(){
        return MAX_RATE;
    }
    public boolean isPlaying() {
        return isPlaying;
    }

    public void setIsPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }

    @Override
    public String toString(){
        return "SonificationClass" + context.getResources().openRawResource(sklad).toString();
    }
}