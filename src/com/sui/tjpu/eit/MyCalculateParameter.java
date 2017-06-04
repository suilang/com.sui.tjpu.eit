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
	
	public ArrayList getWu() {
		return wu;
		
	}
	public void setWu(ArrayList wu) {
		this.wu = wu;
		wuFlag=true;
		 JudgeAll();
	}
	
	private void JudgeAll(){
		if(CirsFlag=true && kongFlag == true && wuFlag == true){
			control.ParamAllDone();
		}
	}
	
}