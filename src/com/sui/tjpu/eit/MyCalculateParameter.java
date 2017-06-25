package com.sui.tjpu.eit;

import java.util.ArrayList;
import java.util.Observable;

import Jama.Matrix;
/**
 * �������ؽ�ͼ����������Լ����ر�־λ����Ӧ�����ؽ�����־λָʾ������������ɣ����Խ������㡣
 * 
 * Cirs�������Ⱦ���Matrix����
 * kong���ճ�����Matrix����
 * wu���ﳡ�����б�ArrayList����
 * CirsFlag�������Ⱦ��������ɱ�־λ��boolean����
 * kongFlag���ճ����������ɱ�־λ��boolean����
 * wuFlag���ﳡ�����б������ɱ�־λ��boolean����
 * 
 * @author ������
 *
 */
public class MyCalculateParameter {
	
	private Matrix Cirs;
	private Matrix kong;
	private ArrayList wu;
	private double[][] paintdate;
	private Matrix b;
	private int Ite_number;
	private int amplify;
	private int currentIndex=0; // ��ǰ֡��
	private int totalIndex=1; // ��֡��

	static  double[] paint_X,paint_Y;

	


	private boolean CirsFlag=false;
	private boolean kongFlag=false;
	private boolean wuFlag=false;
	private boolean allFlag=false;
	

	private Control control;
	
	public MyCalculateParameter(Control control){
		this.control=control;
		this.amplify=5000;
		this.Ite_number=40;
		this.paintdate=zeros();
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
		totalIndex=wu.size();
		 JudgeAll();
	}
	
	public double[][] getPaintdate() {
		
		return paintdate;
		
	}
	public void setPaintdate(double[][] paintdate) {
		this.paintdate = paintdate;
		
	}
	
	public Matrix getB() {
	
		b=new Matrix((double[][]) wu.get(currentIndex));
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
	
	public int getIte_number() {
		return Ite_number;
	}

	public void setIte_number(int ite_number) {
		Ite_number = ite_number;
	}
	
	public int getAmplify() {
		return amplify;
	}
	public void setAmplify(int amplify) {
		this.amplify = amplify;
	}
	
	public boolean isAllFlag() {
		return allFlag;
	}
	
	public int getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}
	
	public void changeCurrentIndex(boolean flag){
		if(flag){
			currentIndex++;
			if(currentIndex>(totalIndex-1)){
				currentIndex=0;
			}
		}else{
			currentIndex--;
			if(currentIndex<0){
				currentIndex=totalIndex-1;
			}
		}
	}
	public int getTotalIndex() {
		return totalIndex;
	}
	
	private void JudgeAll(){
		if(CirsFlag=true && kongFlag == true && wuFlag == true){
			allFlag=true;
			control.ParamAllDone();
		}
	}
	
	public double[][] zeros(){
		double[][] result=new double[812][1]; 
		for(int i=0;i<812;i++){
			result[i][0]=0;
		}
		return result;
		
	}
	
}