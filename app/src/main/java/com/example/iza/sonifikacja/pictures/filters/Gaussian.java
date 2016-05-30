package com.example.iza.sonifikacja.pictures.filters;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class Gaussian implements ImageFilter{

	@Override
	public Mat createFiltr(Mat originalImage) {
		Mat gaussian=new Mat();
		Size size=new Size(9,9);
		Imgproc.GaussianBlur(originalImage, gaussian, size,0);
		return gaussian;
	}	
	public String getName() {
		return this.getClass().getSimpleName();
	}
}
