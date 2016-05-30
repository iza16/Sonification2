package com.example.iza.sonifikacja.view.surface;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.iza.sonifikacja.pictures.image_operations.ImageOperations;
import com.example.iza.sonifikacja.pictures.test.LoadImage;
import com.example.iza.sonifikacja.server.DownloadImageClass;
import com.example.iza.sonifikacja.view.thread.DrawThread;

import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;

/**
 * Created by Iza on 2016-04-26.
 */
public class ImageSurfaceView extends SurfaceView {


    private int imageSize;
    private String pictureName;
    private Bitmap myBitmap;
    private DrawThread drawThread;
    private SurfaceHolder holder;


    public ImageSurfaceView(Context context, int y1) {
        super(context);
        imageSize = y1;
        myBitmap = DownloadImageClass.Read(pictureName);
        drawThread = new DrawThread(this);
        this.holder = getHolder();
        this.holder.setFixedSize(y1,y1);
        this.holder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                drawThread.setRunning(false);
                while (retry)
                {
                    try
                    {
                        drawThread.join();
                        retry = false;
                    }
                    catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder)
            {
                drawThread.setRunning(true);
                drawThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
            }
        });
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        Mat image;
        myBitmap = DownloadImageClass.Read(pictureName);
        try {
            if(null != myBitmap) {
                ImageOperations io = new ImageOperations();
                image = io.getFilteredMat(new Mat (myBitmap.getHeight(), myBitmap.getWidth(), CvType.CV_8UC2));

                Utils.bitmapToMat(myBitmap, image);

                double downSampleRatio = LoadImage.calculateSubSampleSize(image, imageSize, imageSize);
                Mat newImage = new Mat();
                Imgproc.resize(image, newImage, new Size(), downSampleRatio, downSampleRatio, Imgproc.INTER_AREA);
                Bitmap bitMap = Bitmap.createBitmap(newImage.cols(), newImage.rows(), Bitmap.Config.ARGB_8888);

                Utils.matToBitmap(newImage, bitMap);
                canvas.drawColor(Color.WHITE);
                Rect dest = new Rect(0, 0, imageSize, imageSize);
                Paint paint = new Paint();
                paint.setFilterBitmap(true);

                canvas.drawBitmap(bitMap, null, dest, paint); //draw your bitmap
            } else {
                DownloadImageClass.SaveImage(pictureName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPictureName(String name) {
        pictureName = name;
    }
}