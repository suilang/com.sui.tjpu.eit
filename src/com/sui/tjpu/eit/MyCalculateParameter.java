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