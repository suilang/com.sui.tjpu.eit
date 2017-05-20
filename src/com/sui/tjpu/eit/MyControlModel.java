package com.sui.tjpu.eit;

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