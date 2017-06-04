package com.sui.tjpu.eit;
/**
 * 描述：控制界面参数模型，包括：
 * amplify：放大倍数，重建完成后对运算结果进行放大，以对应相应的像素值。
 * algorithm：算法类型，选择使用不同的算法进行图像重建。
 * 
 * 
 * @author 张鹏程
 *
 */
public class MyControlModel{
	
	private String amplify;
	private String[] algorithm={"迭代","单步","预迭代"};
	
	public MyControlModel(){
		
		this.amplify="5000";
		
		
	}
	
	public String getAmplify() {
		return amplify;
	}

	public void setAmplify(String amplify) {
		this.amplify = amplify;
	}
	
	public String[] getCombStr() {
		return algorithm;
	}

	public void setCombStr(String[] algorithm) {
		this.algorithm = algorithm;
	}
	
	
	
}