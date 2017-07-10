package com.sui.tjpu.eit;

import org.eclipse.ui.PlatformUI;

/**
 * 描述：控制界面参数模型，包括： amplify：放大倍数，重建完成后对运算结果进行放大，以对应相应的像素值。
 * algorithm：算法类型，选择使用不同的算法进行图像重建。
 * 
 * 
 * @author 张鹏程
 * 
 */
public class MyControlModel {
	
	private Configure config;
	

	private String[] algorithm = { "迭代", "单步", "预迭代" };

	private int Frame=1;

	public MyControlModel() {

		
		
	}

	/**
	 * 发送采集数据命令
	 * @param iskong
	 * 为真，则下一帧数据放到空场矩阵中，否则放置到物场矩阵
	 */
//	void onlineSend(boolean iskong){
//		config.getOnline().send(iskong);
//	}
	
	
	
	public int getFrame() {
		return Frame;
	}

	public void setFrame(int frame) {
		Frame = frame;
	}

	public String[] getCombStr() {
		return algorithm;
	}

	public void setCombStr(String[] algorithm) {
		this.algorithm = algorithm;
	}

}