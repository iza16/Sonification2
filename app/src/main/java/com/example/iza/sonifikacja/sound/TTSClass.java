package com.example.iza.sonifikacja.sound;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.HashMap;
import java.util.Locale;

/**
 * Created by Iza on 2016-04-11.
 */
public class TTSClass implements TextToSpeech.OnInitListener, TextToSpeech.OnUtteranceCompletedListener {
    public interface EndOfSpeechInterface {

        void endOfSpeechSelection();

    }

    private final int MY_DATA_CHECK_CODE = 12345670;
    private Activity c;
    private TextToSpeech tts;
    private Vibrator v;
    private TTSClass.EndOfSpeechInterface myInterface = null;

    public TTSClass(Activity c) {
        this.c = c;
        v = (Vibrator) c.getSystemService(Context.VIBRATOR_SERVICE);

    }

    public void speak(String text)
    {
        myInterface = null;
        speak(text, "OTHER");
    }

    private void speak(String text, String param) {
        if (tts != null)

        {
            HashMap<String, String> myHashAlarm = new HashMap<String, String>();
            myHashAlarm.put(TextToSpeech.Engine.KEY_PARAM_STREAM, String.valueOf(AudioManager.STREAM_MUSIC));
            myHashAlarm.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, param);
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, myHashAlarm);
            Log.d("TTS", text);
        }
    }

    private void closeTTS() {
        try {
            if (tts != null) {
                tts.stop();
                tts.shutdown();
                tts = null;
            }
        } catch (Exception e) {

        }
    }

    public void onResume() {
        if (tts == null) {
            Intent checkTTSIntent = new Intent();
            checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
            c.startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);
        }

    }

    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                // the user has the necessary data - create the TTS
                tts = new TextToSpeech(c, this);
            } else {
                // no data - install it now
                Intent installTTSIntent = new Intent();
                installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                c.startActivity(installTTSIntent);
            }
            return true;
        }
        return false;
    }

    @Override
    public void onInit(int status) {
        System.out.println("TTS init:"+status);
        // check for successful instantiation
        if (status == TextToSpeech.SUCCESS) {
            if (tts.isLanguageAvailable(Locale.US) == TextToSpeech.LANG_AVAILABLE)
                tts.setLanguage(Locale.US);

            tts.setOnUtteranceCompletedListener(this);

        }
        else if (status == TextToSpeech.ERROR) {
        }

    }

    public void onPause() {
        System.out.println("tts pause");

    }

    public void onDestroy() {
        closeTTS();

    }

    public void vibrate() {
        // Vibrate for 500 milliseconds
        v.vibrate(50);

    }

    @Override
    public void onUtteranceCompleted(String arg0) {
        System.out.println("tts completed:"+arg0);
        if (myInterface!=null && arg0.compareTo("SEL")==0)
        {
            myInterface.endOfSpeechSelection();
        }

    }

    public void speakSel(String text, TTSClass.EndOfSpeechInterface aInterface) {
        myInterface = aInterface;
        speak(text, "SEL");
    }
}
