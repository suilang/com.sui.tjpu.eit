package com.sui.tjpu.eit;
/**
 * ���������ƽ������ģ�ͣ�������
 * amplify���Ŵ������ؽ���ɺ�����������зŴ��Զ�Ӧ��Ӧ������ֵ��
 * algorithm���㷨���ͣ�ѡ��ʹ�ò�ͬ���㷨����ͼ���ؽ���
 * 
 * 
 * @author ������
 *
 */
public class MyControlModel{
	
	private String amplify;
	private String[] algorithm={"����","����","Ԥ����"};
	
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