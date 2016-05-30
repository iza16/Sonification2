package  com.example.iza.sonifikacja.pictures.image_operations;

import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import android.util.Log;

public class HistogramRGB{
	private int mHistSizeNum = 256;
	private List <Mat> listOfChannels;
	private float v;
	private float []val;
	private List<Mat> getListOfChannels(Mat originalImage){
		
		MatOfInt mHistSize = new MatOfInt(mHistSizeNum);
		List<Mat> hsv_planes = new ArrayList<Mat>();
		//if(originalImage.channels()>1){
			Core.split(originalImage, hsv_planes);
		
			MatOfInt mChannels[] = new MatOfInt[] { new MatOfInt(0), new MatOfInt(1),new MatOfInt(2) };
			MatOfFloat histogramRanges = new MatOfFloat(0f, 256f);
			
			Mat hist_b = new Mat();
			Mat hist_g = new Mat();
			Mat hist_r = new Mat();
			Imgproc.calcHist(hsv_planes, mChannels[0],new Mat(), hist_b,mHistSize, histogramRanges);
			Imgproc.calcHist(hsv_planes, mChannels[1],new Mat(), hist_g,mHistSize, histogramRanges);		
			Imgproc.calcHist(hsv_planes, mChannels[2],new Mat(), hist_r,mHistSize, histogramRanges);
			
			listOfChannels = new ArrayList<>(3);
			listOfChannels.add(hist_r);
			listOfChannels.add(hist_g);
			listOfChannels.add(hist_b);
		/*}else{
			
			listOfChannels = new ArrayList<Mat>();
			listOfChannels.add(originalImage);
			Mat histogram = new Mat();
			MatOfFloat ranges=new MatOfFloat(0,256);
			MatOfInt histSize = new MatOfInt(256);
			Imgproc.calcHist(
			                listOfChannels, 
			                new MatOfInt(0), 
			                new Mat(), 
			                histogram , 
			                histSize , 
			                ranges);
		}*/
		return listOfChannels;	
	}
	public float calculateMedian(Mat originalImage) {
		listOfChannels = getListOfChannels(originalImage);
		val = new float [mHistSizeNum];
	 	for(int i = 0; i<mHistSizeNum;i++){
	 		//if(listOfChannels.size() ==3){
		 		v = (float)((0.3*listOfChannels.get(0).get(i, 0)[0]
		 				+0.59*listOfChannels.get(1).get(i, 0)[0]
		 				+0.11*listOfChannels.get(2).get(i, 0)[0])/(originalImage.rows()*originalImage.cols()));
	 		/*}else{
	 			v = (float)((listOfChannels.get(0).get(i, 0)[0])/(originalImage.rows()*originalImage.cols()));
	 			
	 		}*/
	 		
			v *= 100000;
	        v = Math.round(v);
	        v /= 100000;
			
	        val[i] =  v;      
		}	
		float mean = 0;
		for(int i = 0; i<mHistSizeNum;i++){
			mean += (i*val[i]);
		}
		
		return mean/256;
	}
	public float calculateSTD(Mat originalImage) {
		float mean = calculateMedian(originalImage);
		float odch = 0;
		for(int i =0; i< mHistSizeNum; i++){
			odch+= Math.pow((i-mean),2)*val[i];
		}
		return (float) (Math.sqrt(odch)/256);
	}
	public Mat drawHistogramRGB(Mat originalImage, int hist_w, int hist_h){
		List <Mat> listOfChannels = getListOfChannels(originalImage);
	   
	    long bin_w = Math.round((double) hist_w / 256);
	    Mat histImage = new Mat( hist_h, hist_w, CvType.CV_8UC3, new Scalar( 0,0,0,0) );
	    Mat hist_r=listOfChannels.get(0);
	    Mat hist_g=listOfChannels.get(1);
	    Mat hist_b=listOfChannels.get(2);
	    Core.normalize(hist_r, hist_r, histImage.rows(), 0, Core.NORM_INF);
	    Core.normalize(hist_g, hist_g, histImage.rows(), 0, Core.NORM_INF);
	    Core.normalize(hist_b, hist_b, histImage.rows(), 0, Core.NORM_INF);  
	    Point p1, p2;
	    for(int i = 1; i < 256; i++){
	    	p1 = new Point(bin_w * (i - 1),hist_h -(Math.round(0.3*hist_r.get(i - 1, 0)[0])+Math.round(0.59*hist_g.get(i - 1, 0)[0])+Math.round(0.11*hist_b.get(i - 1, 0)[0])));
	        p2 = new Point(bin_w * (i), hist_h - (Math.round(0.3*hist_r.get(i, 0)[0])+Math.round(0.59*hist_g.get(i, 0)[0])+Math.round(0.11*hist_b.get(i, 0)[0])));
	    	Core.line(histImage, p1, p2, new Scalar(255,0,0), 2, 8, 0);
	    	/*Point p1 = new Point(bin_w * (i - 1),(hist_h - Math.round(hist_r.get(i - 1, 0)[0])));
	        Point p2 = new Point(bin_w * (i), (hist_h - Math.round(hist_r.get(i, 0)[0])));
	    	Core.line(histImage, p1, p2, new Scalar(255,0,0), 2, 8, 0);
	    		    	
	    	Point p3 = new Point(bin_w * (i - 1), (hist_h - Math.round(hist_g.get(i - 1, 0)[0])));
	        Point p4 = new Point(bin_w * (i), (hist_h - Math.round(hist_g.get(i, 0)[0])));
	        Core.line(histImage, p3, p4, new Scalar(0, 255,0), 2, 8, 0);
	
	        Point p5 = new Point(bin_w * (i - 1), hist_h - Math.round(hist_b.get(i - 1, 0)[0]));
	        Point p6 = new Point(bin_w * (i), hist_h - Math.round(hist_b.get(i, 0)[0]));
	        Core.line(histImage, p5, p6, new Scalar(0, 0, 255), 2, 8, 0);
	        */
	    } 
	    return histImage;
	}
	
}