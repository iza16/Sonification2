package com.example.iza.sonifikacja.pictures.filters;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class MeanRemoval implements ImageFilter{

	@Override
	public Mat createFiltr(Mat originalImage) {
		Mat kernel = new Mat(3, 3, CvType.CV_32FC1);
		//create
		float[] data = {-1, -1,-1, -1, 9, -1, -1, -1, -1};
		kernel.put(0, 0, data);
		Mat meenRemoval = new Mat();
		Imgproc.filter2D(originalImage, meenRemoval, -1, kernel);
		return meenRemoval;	
	}
	public String getName() {
		return this.getClass().getSimpleName();
	}

}
