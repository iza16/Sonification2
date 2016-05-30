package com.example.iza.sonifikacja.pictures.image_operations;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.iza.sonifikacja.pictures.filters.ImageFilter;
import com.example.iza.sonifikacja.pictures.parameters.Parameter;
import com.example.iza.sonifikacja.pictures.test.OriginalMatSingleton;

public class ImageOperations {
	private int image_x, image_y, x_width, y_height;
	OriginalMatSingleton originalMatSingleton = OriginalMatSingleton.getInstance();
	Mat originalMat = originalMatSingleton.getOriginalMat();
	ImageFilter currentlyFilter;
	Map <String,ImageFilter> currentlyFilters;
	Mat currentMat, currentMultiMat;
	//Metoda do konwertacji Map na Bitmap
	public Bitmap convertToBitmapImage(Mat image){
		Bitmap bitMap = Bitmap.createBitmap(image.cols(),image.rows(),Bitmap.Config.RGB_565);
		Utils.matToBitmap(image, bitMap);
		return bitMap;
	}
	//Metoda do wyciecia obszaru zainteresowania z podaniem rozmiaru obszaru
	public Mat getRoiOfOriginalMat(int xStart, int width, int yStart, int height){
	      return originalMat.submat(yStart, height , xStart, width);	
	}
	//Metoda do wyciecia obszaru zainteresowania ze stalym obszarem
	public Mat getRoiOfOriginalMat(float norm_x, float norm_y){
		int matHeight = originalMatSingleton.getHeightOfOriginalMat();
	    int matWidth = originalMatSingleton.getWidthOfOriginalMat();
	    if ((norm_x>=0 && norm_x <=1) && (norm_y>=0 && norm_y<=1)){
		    int x = (int)Math.round(norm_x*matWidth);
		    int y = (int)Math.round(norm_y*matHeight);
		    Log.d("****", "x="+x);
		    Log.d("****", "y="+y);
			int scalValue = 15/*matWidth/25*/;
			if(x >= scalValue && (x <= matWidth - scalValue)){
				image_x = x-scalValue;
				x_width = image_x+2*scalValue;
			}else if ((x >= 0) && (x < scalValue)){
				image_x = 0;
				x_width = image_x+scalValue;
			}else if ((x > matWidth - scalValue) && (x <= matWidth)){
				image_x = x-scalValue;
				x_width = matWidth;		
			}
			
			if((y >= scalValue) && (y <= matHeight - scalValue)){
				image_y = y-scalValue;
				y_height = image_y+2*scalValue;
			}else if ((y >= 0) && (y < scalValue)){
				image_y = 0;
				y_height = image_y+y+scalValue;
			}else if ((y > matHeight - scalValue) && (y <= matHeight)){
				image_y = y-scalValue;
				y_height = matHeight;		
			}
		}
	    Log.d("!!!RozmiarMacierzy", ""+matHeight+"x"+matWidth);
		Mat m = originalMat.submat(image_y, y_height , image_x, x_width);
		Log.d("!!!RozmiarMacierzy", ""+m.rows()+"x"+m.cols());
		return m;
	}
	//Metoda do pobrania listy parametrow z obszaru po zadaniu x i y
	public List<Parameter> getListOfParameters(float x, float y/*, ImageFilter filter*/){
		//currentlyFilter = originalMatSingleton.getCurrentlyFilter();
		currentlyFilters = originalMatSingleton.getCurrentlyFilters();
		/*warunek sprawdzający*/
		if(currentlyFilters.size() == 0){
			currentMultiMat = getRoiOfOriginalMat(x, y);
		}else{
			currentMultiMat = getMultiFilteredMat(getRoiOfOriginalMat(x, y));
		}
		float meanValue = getMedianAndStdFromMat(currentMultiMat).getX();
		float stdValue = getMedianAndStdFromMat(currentMultiMat).getY();
		List<Parameter> listOfParameters = new ArrayList<>(11);
		Parameter parameterMean = new Parameter(meanValue, 1);
		Parameter parameterStd = new Parameter(stdValue, 1);
		listOfParameters.add(parameterMean);
		listOfParameters.add(parameterStd);
		for(int i = 2; i<12;i++){
			listOfParameters.add(new Parameter(meanValue, 1));
		}
		return listOfParameters;
	}
	//Metoda do przyblizenia z obszaru zainteresowania z zadaniem macierzy do analizy i krotnosci powiekszenia
	public Mat zoomInMat(Mat originalImage, int value) throws InputMismatchException, IllegalArgumentException{
		Mat doubleRoi = new Mat();
		Size doubleSize = new Size(originalImage.width()*value,originalImage.height()*value);
	    Imgproc.resize(originalImage,doubleRoi,doubleSize);
	    return doubleRoi;
	}
	//Metoda do przyblizenia z obszaru zainteresowania z zadaniem macierzy do analizy i krotnosci pomniejszenia
	public Mat zoomOutMat (Mat originalImage, int value)throws InputMismatchException,IllegalArgumentException{
		Mat doubleRoi = new Mat();
		Size doubleSize = new Size(originalImage.width()/value,originalImage.height()/value);
	    Imgproc.resize(originalImage,doubleRoi,doubleSize);
	    return doubleRoi;
	}
	//Metoda do obliczania wartosci sredniej i odchylenia
	public TwoFloatValues getMedianAndStdFromMat(Mat originalImage){
		MatOfDouble mean = new MatOfDouble();
		MatOfDouble stddev = new MatOfDouble();
		Core.meanStdDev(originalImage, mean , stddev);
		float d, std;
		if(originalImage.channels()>1){
			d = (float)((0.11*mean.get(0,0)[0]+0.59*mean.get(1,0)[0]+0.3*mean.get(2,0)[0])/256);
			
			std = (float)((0.11*stddev.get(0,0)[0]+0.59*stddev.get(1,0)[0]+0.3*stddev.get(2,0)[0])/256);
		}else{
			d = (float)((mean.get(0,0)[0])/(256));
			
			std = (float)((stddev.get(0,0)[0])/(256));
		}
		return new TwoFloatValues(round(d), round(std));
	}
	//Metoda do obliczania wartosci sredniej z zadanych wspolrzednych centrum roi x,y
	public float getMedianValueFromMat(int x, int y) {
		Scalar scalar = Core.mean(getRoiOfOriginalMat(x, y));
		return Math.round((0.11*scalar.val[0]+0.59*scalar.val[1]+0.3*scalar.val[2]));	
	}
	//Metoda zaokraglajaca float do 6 liczb po przecinku
	public float round(float in){
		in *= 100000;
		in = Math.round(in);
		in /= 100000;
		return in;
	}
	//Metoda do filtrowania, argument 1 - obszar analizowany
	public Mat getFilteredMat(Mat originalImage){
		currentlyFilter = originalMatSingleton.getCurrentlyFilter();
		//Log.d("!!!Image", ""+originalImage);
		return currentlyFilter.createFiltr(originalImage);
	}
	
	public Mat getMultiFilteredMat(Mat originalImage){
		currentlyFilters = originalMatSingleton.getCurrentlyFilters();

		Log.d("!!!Filtry", ""+currentlyFilters);
		
		Iterator<Map.Entry<String, ImageFilter>> entries = currentlyFilters.entrySet().iterator();
		
		while (entries.hasNext()){
		    Entry<String, ImageFilter> entry = entries.next();
		    originalImage = entry.getValue().createFiltr(originalImage);
		}
		return originalImage;
	}

	
	/*Metody nieaktualne
	//Wycinanie na macierzy
	public Mat cutPartOfMat(Mat originalImage, int xStart, int width, int yStart, int height){
	      return originalImage.submat(yStart, height , xStart, width);	
	}
	//Wyliczenie mean
	public float getMedianValueFromMat(Mat originalImage) {
		Scalar scalar = Core.mean(originalImage);
		return Math.round((0.11*scalar.val[0]+0.59*scalar.val[1]+0.3*scalar.val[2]));	
	}	
	*/
}
