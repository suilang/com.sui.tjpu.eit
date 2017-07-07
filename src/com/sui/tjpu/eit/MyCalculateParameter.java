package com.sui.tjpu.eit;

import java.util.ArrayList;
import java.util.Observable;

import Jama.Matrix;

/**
 * 描述：重建图像运算参数以及加载标志位，对应离线重建，标志位指示各参数加载完成，可以进行运算。
 * 
 * Cirs：灵敏度矩阵，Matrix类型 kong：空场矩阵，Matrix类型 wu：物场矩阵列表，ArrayList类型
 * CirsFlag：灵敏度矩阵加载完成标志位，boolean类型 kongFlag：空场矩阵加载完成标志位，boolean类型
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
	private int Ite_number;
	private int amplify;
	private int currentIndex = 0; // 当前帧数
	private int totalIndex = 1; // 总帧数
	private MyConfigureModel myconf = MyProoerty.getMyconf();
	static double[] paint_X, paint_Y;

	private double[][] onlineBuff;

	private boolean CirsFlag = false;
	private boolean kongFlag = false;
	private boolean wuFlag = false;
	private boolean allFlag = false;

	/**
	 * 存储网络连接所需要的运算数据以及标志位
	 * 
	 */
	private Matrix onlineCirs;
	private Matrix onlinekong;
	private double[][] onlinewu;
	private boolean onlineCirsFlag = false;
	private boolean onlinekongFlag = false;
	private boolean onlinewuFlag = false;
	private boolean onlineallFlag = false;
	private boolean setkongFlag = false;// 是否采集空场，若是，则标志位为true，网络连接数据放置到空场中，并将空场存储

	public boolean isSetkongFlag() {
		return setkongFlag;
	}

	public void setSetkongFlag(boolean setkongFlag) {
		this.setkongFlag = setkongFlag;
	}

	private Control control;

	public MyCalculateParameter(Control control) {
		this.control = control;
		this.amplify = 5000;
		this.Ite_number = 40;
		this.paintdate = zeros();
	}



	/**
	 * 获得物场数据,自动判断连接位置，本地连接时返回当前帧与空场相减数据，网络连接时返回网络接收数据与空场相减值
	 * 
	 * @return
	 * 物场与空场相减的值，以矩阵形式返回
	 */
	public Matrix getB() {

		if (myconf.isConnectFlag() == true) {
			b = new Matrix((double[][]) wu.get(currentIndex));
			b = b.minus(kong);

		} else {
			b = new Matrix(onlineBuff);
			b = b.minus(onlinekong);
		}
		return b;
	}

	public double[] getPaint_X() {
		return paint_X;
	}

	public double[] getPaint_Y() {
		return paint_Y;
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

	/**
	 * 改变当前帧数值
	 * 
	 * @param flag
	 *            为true时帧数增加，否则减少
	 */
	public void changeCurrentIndex(boolean flag) {
		if (flag) {
			currentIndex++;
			if (currentIndex > (totalIndex - 1)) {
				currentIndex = 0;
			}
		} else {
			currentIndex--;
			if (currentIndex < 0) {
				currentIndex = totalIndex - 1;
			}
		}
	}

	public int getTotalIndex() {
		return totalIndex;
	}

	private void JudgeAll() {
		if (CirsFlag = true && kongFlag == true && wuFlag == true) {
			allFlag = true;
			control.ParamAllDone();
		}
	}

	public double[][] zeros() {
		double[][] result = new double[812][1];
		for (int i = 0; i < 812; i++) {
			result[i][0] = 0;
		}
		return result;

	}

	public Matrix getOnlineCirs() {
		return onlineCirs;
	}

	public void setOnlineCirs(Matrix onlineCirs) {
		this.onlineCirs = onlineCirs;
		onlineCirsFlag = true;
	}

	public Matrix getOnlinekong() {
		return onlinekong;
	}

	public void setOnlinekong(Matrix onlinekong) {
		this.onlinekong = onlinekong;
		onlinekongFlag = true;
	}

	public double[][] getOnlinewu() {
		return onlinewu;
	}

	public void setOnlinewu(double[][] onlinewu) {
		this.onlinewu = onlinewu;
	}

	public boolean isOnlineCirsFlag() {
		return onlineCirsFlag;
	}

	public void setOnlineCirsFlag(boolean onlineCirsFlag) {
		this.onlineCirsFlag = onlineCirsFlag;

	}

	public boolean isOnlinekongFlag() {
		return onlinekongFlag;
	}

	public void setOnlinekongFlag(boolean onlinekongFlag) {
		this.onlinekongFlag = onlinekongFlag;
	}

	public boolean isOnlinewuFlag() {
		return onlinewuFlag;
	}

	public void setOnlinewuFlag(boolean onlinewuFlag) {
		this.onlinewuFlag = onlinewuFlag;
	}

	public boolean isOnlineallFlag() {
		return onlineallFlag;
	}

	public void setOnlineallFlag(boolean onlineallFlag) {
		this.onlineallFlag = onlineallFlag;
	}
	
	public Matrix getCirs() {
		return Cirs;
	}

	public void setCirs(Matrix cirs) {
		this.Cirs = cirs;
		CirsFlag = true;
		JudgeAll();
	}

	public Matrix getKong() {
		return kong;
	}

	public void setKong(Matrix kong) {
		this.kong = kong;
		kongFlag = true;
		JudgeAll();
	}

	public ArrayList getWubuf() {
		return wu;

	}

	public void setWubuf(ArrayList wu) {
		this.wu = wu;
		wuFlag = true;
		totalIndex = wu.size();
		JudgeAll();
	}

	public double[][] getOnlineBuff() {
		return onlineBuff;
	}

	public void setOnlineBuff(double[][] onlineBuff) {
		this.onlineBuff = onlineBuff;
	}

	public double[][] getPaintdate() {

		return paintdate;

	}

	public void setPaintdate(double[][] paintdate) {
		this.paintdate = paintdate;

	}
}