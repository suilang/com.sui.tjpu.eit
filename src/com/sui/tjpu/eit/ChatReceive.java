package com.sui.tjpu.eit;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;


public class ChatReceive extends Thread {
	
	private int Aimport;
	public ChatReceive(String AimPort){
		this.Aimport=Integer.parseInt(AimPort);
	}
	
	public void run() {
		try {
			//建立udp的服务,要监听一个端口
			DatagramSocket socket = new DatagramSocket(Aimport);
			//准备空的数据包存储数据
			byte[] buf = new byte[1024];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			boolean flag = true;
			while(flag){
				socket.receive(packet);
				// packet.getAddress() 获取对方数据 包的IP地址对象。
				trans(buf);
			}
			//关闭资源
			socket.close();
		
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}

	 void trans(byte[] temp){
		 int i=0;
		// double[] buff=new double[208];
//		 for(i=0;i<416;i+=2){
//			 V.nowdata[i/2][0]=temp[i]<<8|temp[i+1];
//		 }
//		 if(F.OnlineRecordFlag)
//		 {
//			 for(i=0;i<208;i++){
//				 V.Onlinebuff.add(V.nowdata[i]);
//			 }
//			 V.OnlineIndex++;
//			 onct.textField_1.setText(String.valueOf(V.OnlineIndex));
//		 }
		 
		 
	 }
}
