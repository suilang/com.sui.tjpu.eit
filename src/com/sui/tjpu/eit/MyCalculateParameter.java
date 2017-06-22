package com.sui.tjpu.eit;

import java.util.ArrayList;
import java.util.Observable;

import Jama.Matrix;
/**
 * 描述：重建图像运算参数以及加载标志位，对应离线重建，标志位指示各参数加载完成，可以进行运算。
 * 
 * Cirs：灵敏度矩阵，Matrix类型
 * kong：空场矩阵，Matrix类型
 * wu：物场矩阵列表，ArrayList类型
 * CirsFlag：灵敏度矩阵加载完成标志位，boolean类型
 * kongFlag：空场矩阵加载完成标志位，boolean类型
 * wuFlag：物场矩阵列表加载完成标志位，boolean类型
 * 
 * @author 张鹏程
 *
 */
public class MyCalculateParameter {
	
	private Matrix Cirs;
	private Matrix kong;
	private ArrayList wu;
	private double[][] paintdate;
	private Matrix b;

	static  double[] paint_X,paint_Y;

	


	private boolean CirsFlag=false;
	private boolean kongFlag=false;
	private boolean wuFlag=false;
	
	private Control control;
	
	public MyCalculateParameter(Control control){
		this.control=control;
	}
	public Matrix getCirs() {
		return Cirs;
	}
	public void setCirs(Matrix cirs) {
		this.Cirs = cirs;
		CirsFlag=true;
		 JudgeAll();
	}
	public Matrix getKong() {
		return kong;
	}
	public void setKong(Matrix kong) {
		this.kong = kong;
		kongFlag=true;
		 JudgeAll();
	}
	
	public ArrayList getWubuf() {
		return wu;
		
	}
	public void setWubuf(ArrayList wu) {
		this.wu = wu;
		wuFlag=true;
		 JudgeAll();
	}
	
	public double[][] getPaintdate() {
		return paintdate;
	}
	public void setPaintdate(double[][] paintdate) {
		this.paintdate = paintdate;
	}
	
	public Matrix getB(int index) {
		b=new Matrix((double[][]) wu.get(index));
		b=b.minus(kong);
		return b;
	}
	
	public double[] getPaint_X() {
		return paint_X;
	}
	public void setPaint_X(double[] paint_X) {
		this.paint_X = paint_X;
	}
	public double[] getPaint_Y() {
		return paint_Y;
	}
	public void setPaint_Y(double[] paint_Y) {
		this.paint_Y = paint_Y;
	}
	
	
	private void JudgeAll(){
		if(CirsFlag=true && kongFlag == true && wuFlag == true){
			control.ParamAllDone();
		}
	}
	
}