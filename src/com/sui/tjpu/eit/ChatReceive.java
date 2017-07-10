package com.sui.tjpu.eit;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import Jama.Matrix;


public class ChatReceive extends Thread {
	
	private int Aimport;
	boolean flag = true;
	DatagramSocket socket;
	MyCalculateParameter mycalpara;
	private boolean kongFlag = false;// �Ƿ�ɼ��ճ������ǣ����־λΪtrue�������������ݷ��õ��ճ��У������ճ��洢
	
	public boolean iskongFlag() {
		return kongFlag;
	}

	public void setkongFlag(boolean kongFlag) {
		this.kongFlag = kongFlag;
	}

	public ChatReceive(String AimPort){
		this.Aimport=Integer.parseInt(AimPort);
	}
	
	public ChatReceive(MyCalculateParameter mycalpara,String AimPort){
		this.Aimport=Integer.parseInt(AimPort);
		this.mycalpara=mycalpara;
	}
	
	public void run() {
		try {
			System.err.println("���������߳�");
			//����udp�ķ���,Ҫ����һ���˿�
			DatagramSocket socket = new DatagramSocket(Aimport);
			
			//׼���յ����ݰ��洢����
			byte[] buf = new byte[1024];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			
			while(flag){
				try{
					socket.setSoTimeout(1000);
					socket.receive(packet);
					
				}catch(SocketTimeoutException e){
					continue;
				}
				
				// packet.getAddress() ��ȡ�Է����� ����IP��ַ����
				trans(buf);
				
			}
			//�ر���Դ
			socket.close();
		System.err.println("�رս����߳�");
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}

	public void onlineReceiveStop() throws SocketException{
		flag=false;
//		try{
//			socket.close();
//		}catch(NullPointerException e){
//			System.err.println("NullPointerException");
//			
//		}
	
		
	}
	 void trans(byte[] temp){
		 
		 int i=0;
		 
		 double[][] buff=new double[208][1];
		 for(i=0;i<416;i+=2){
			 
			 buff[i/2][0]=(double)(((temp[i]&0xff)*256)+(temp[i+1]&0xff))*3.3/65535;
		 }
		 if(kongFlag==true){
			 mycalpara.setOnlinekong(new Matrix(buff));
			 kongFlag=false;
		 }
		 else{
			 mycalpara.setOnlinewu(buff);
			 mycalpara.JudgeOnlineAll();
		 }
		
		 

		 
		 
	 }
}
