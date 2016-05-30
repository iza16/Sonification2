package com.example.iza.sonifikacja.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.iza.sonifikacja.R;
import com.example.iza.sonifikacja.server.DownloadImageClass;
import com.example.iza.sonifikacja.sound.SonificationManageClass;
import com.example.iza.sonifikacja.view.fragment.GestureFragment;
import com.example.iza.sonifikacja.view.fragment.DisplayImageFragment;

import org.opencv.android.OpenCVLoader;

import java.util.Arrays;

public class FreeModeSonificationActivity extends FragmentActivity {

    private static final String TAG = "Sonification";
    public SonificationManageClass sonificationManage;
    public LinearLayout ll;
    public LinearLayout ll2;
    public FrameLayout fl;
    public FrameLayout fl2;
    public DisplayImageFragment svf;
    public GestureFragment gf;
    public int x;
    public int y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_second_sonification1);

            sonificationManage = new SonificationManageClass(getApplicationContext());
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            y = displayMetrics.heightPixels;
            x = displayMetrics.widthPixels;
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.select_dialog_item,MainActivity.downloadImageClass.listitems);
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Dialog);
            builder.setTitle("LISTA");

            builder.setAdapter(adapter, new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    String selectedPosition = Arrays.asList(MainActivity.downloadImageClass.arrayStrings).get(which);
                    Toast.makeText(getApplicationContext(), selectedPosition, Toast.LENGTH_SHORT).show();
                    try {
                        MainActivity.downloadImageClass.namePicture = selectedPosition;
                        svf.setPictureName(MainActivity.downloadImageClass.namePicture);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();

            ll = (LinearLayout) findViewById(R.id.linear_layout);


            fl = (FrameLayout) findViewById(R.id.fragment_container);
        if (fl != null) {
            fl.getLayoutParams().height = y;
            fl.getLayoutParams().width = y;
            svf = DisplayImageFragment.newInstance(y);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, svf).commit();
            svf.setSonificationManage(sonificationManage);
        }

       fl2 = (FrameLayout) findViewById(R.id.gesture_fragment_container);
        if (fl2 != null) {
            fl2.getLayoutParams().height = y;
            fl2.getLayoutParams().width = x-y;
            gf = GestureFragment.newInstance(x,y);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.gesture_fragment_container, gf).commit();
        }
    }


    @Override
    public void onResume()
    {
        super.onResume();
        sonificationManage.turnOffAll();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        sonificationManage.turnOffAll();
    }

}
