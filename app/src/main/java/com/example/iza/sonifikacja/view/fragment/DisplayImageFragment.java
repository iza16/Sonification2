package com.example.iza.sonifikacja.view.fragment;

import android.annotation.TargetApi;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.iza.sonifikacja.pictures.image_operations.ImageOperations;
import com.example.iza.sonifikacja.sound.SonificationManageClass;
import com.example.iza.sonifikacja.view.surface.ImageSurfaceView;


public class DisplayImageFragment extends Fragment {
    private static final String WIDTH = "width";
    private static final String PICTURE_NAME = "name";

    private ImageSurfaceView view;
    private int imageSize;
    private String pictureName;
    StringBuilder moveInfo = new StringBuilder();
    private SonificationManageClass sonificationManage;
    public DisplayImageFragment() {
        // Required empty public constructor
    }

    public static DisplayImageFragment newInstance(int y) {
        DisplayImageFragment fragment = new DisplayImageFragment();
        Bundle args = new Bundle();
        args.putInt(WIDTH, y);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            imageSize = getArguments().getInt(WIDTH);
            pictureName = getArguments().getString(PICTURE_NAME);
        }
    }

       @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        moveInfo.append("UserName: User1");

        view = new ImageSurfaceView(getActivity().getApplicationContext(),(int) (imageSize/2.54));
        view.setOnTouchListener(new View.OnTouchListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            public boolean onTouch(View v, MotionEvent event) {
                float x = event.getX();
                float y = event.getY();
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    ImageOperations imageOperations = new ImageOperations();
                    //sonificationManage.moduleSounds(imageOperations.getListOfParameters((int) x, (int) y));
                    sonificationManage.moduleSounds(imageOperations.getListOfParameters( x/imageSize,  y/imageSize));
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    sonificationManage.setVolumeMin();
                }

                moveInfo.append("y: " + y )
                        .append(" x: " + x )
                        .append(" eventType: " + MotionEvent.actionToString(event.getAction()))
                        .append(" eventTime: " + event.getEventTime())
                        .append(" downTime: " +event.getDownTime())
                        .append("\n");

                return true;
            }
        });
        return view;
    }



    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setPictureName(String name){
        pictureName = name;
        view.setPictureName(pictureName);
        moveInfo.append(" PictureName: " + pictureName)
                .append("\n");
    }

    public void setSonificationManage(SonificationManageClass sonificationManage) {
        this.sonificationManage = sonificationManage;
    }



}
