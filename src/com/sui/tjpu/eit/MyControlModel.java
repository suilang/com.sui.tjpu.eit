package com.sui.tjpu.eit;

import org.eclipse.ui.PlatformUI;

/**
 * ���������ƽ������ģ�ͣ������� amplify���Ŵ������ؽ���ɺ�����������зŴ��Զ�Ӧ��Ӧ������ֵ��
 * algorithm���㷨���ͣ�ѡ��ʹ�ò�ͬ���㷨����ͼ���ؽ���
 * 
 * 
 * @author ������
 * 
 */
public class MyControlModel {
	
	private Configure config;
	

	private String[] algorithm = { "����", "����", "Ԥ����" };

	private int Frame=1;

	public MyControlModel() {

		
		
	}

	/**
	 * ���Ͳɼ���������
	 * @param iskong
	 * Ϊ�棬����һ֡���ݷŵ��ճ������У�������õ��ﳡ����
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