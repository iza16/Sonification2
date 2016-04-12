package com.example.iza.sonifikacja.sound;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

import com.example.iza.sonifikacja.R;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * Created by Iza on 2016-04-11.
 */
public class SonificationClass {
    private final double duration = 15; // seconds
    private final int sampleRate = 15000;
    private final int numSamples = (int) (duration * sampleRate);
    private byte[] generatedSndOn = new byte[2 * numSamples];
    private byte generatedSndNear[] = new byte[2 * numSamples];
    public final AudioTrack audioTrackOn;
    public final AudioTrack audioTrackNear;
    Context context;

    public SonificationClass(Context c) {
        audioTrackOn = new AudioTrack(AudioManager.STREAM_MUSIC,
                sampleRate, AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT, numSamples,
                AudioTrack.MODE_STATIC);
        audioTrackNear = new AudioTrack(AudioManager.STREAM_MUSIC,
                sampleRate, AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT, numSamples,
                AudioTrack.MODE_STATIC);

        this.context = c;
        genToneNearLine();
        genToneOnLine();


    }

    public void genToneOnLine() {

        InputStream is = context.getResources().openRawResource(R.raw.test);
        try {
            generatedSndOn = new byte[is.available()];

            BufferedInputStream bis = new BufferedInputStream(is, 8000);
            DataInputStream dis = new DataInputStream(bis);      //  Create a DataInputStream to read the audio data from the saved file

            int i = 0;                                                          //  Read the file into the "music" array
            while (dis.available() > 0) {
                generatedSndOn[i] = dis.readByte();                                      //  This assignment does not reverse the order
                i++;
            }

            dis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void genToneNearLine() {

        InputStream is = context.getResources().openRawResource(R.raw.test2);
        try {
            generatedSndNear = new byte[is.available()];

            BufferedInputStream bis = new BufferedInputStream(is, 8000);
            DataInputStream dis = new DataInputStream(bis);      //  Create a DataInputStream to read the audio data from the saved file

            int i = 0;                                                          //  Read the file into the "music" array
            while (dis.available() > 0) {
                generatedSndNear[i] = dis.readByte();                                      //  This assignment does not reverse the order
                i++;
            }

            dis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void playSoundOnLine() {

        audioTrackOn.play();
    }

    public void playSoundNearLine() {


        audioTrackNear.play();
    }

    public void generate() {
        genToneNearLine();
        genToneOnLine();
        audioTrackOn.write(generatedSndOn, 0, generatedSndOn.length);
        audioTrackNear.write(generatedSndNear, 0, generatedSndNear.length);

    }

    public void playAll(){
        playSoundNearLine();
        playSoundOnLine();
    }
}