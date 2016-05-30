package com.example.iza.sonifikacja.pictures.filters;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class Canny implements ImageFilter{
	@Override
	public Mat createFiltr(Mat originalImage) {
		Mat gray = new Mat();
		Imgproc.cvtColor(originalImage, gray, Imgproc.COLOR_RGB2GRAY);
		Mat edgeImage=new Mat();
		Imgproc.Canny(gray, edgeImage, 100, 200);
		return edgeImage;
	}	
	
	public String getName() {
		return this.getClass().getSimpleName();
	}
}
