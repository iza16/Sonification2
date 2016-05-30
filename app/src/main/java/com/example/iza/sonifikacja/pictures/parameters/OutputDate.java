package com.example.iza.sonifikacja.pictures.parameters;

import java.util.List;
import org.opencv.core.Mat;

public class OutputDate {
	Mat mat;
	List <Parameter> listOfParameters;
	public OutputDate(Mat mat,List <Parameter> listOfParameters) {
		this.mat = mat;
		this.listOfParameters = listOfParameters;
	}
	public Mat getMatFromParametersData(){
		return mat;
	}
	public List<Parameter> getMapFromP(){
		return listOfParameters;
	}	


}
