package com.sui.tjpu.eit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

//群聊发送端
public class ChatSender extends Thread {

	private InetAddress AimIP;
	private int AimPort;
	private boolean exit = true;

	public ChatSender(String AimIP, String AimPort) {
		try {
			this.AimIP = InetAddress.getByName(AimIP);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.AimPort = Integer.parseInt(AimPort);
	}

	public void onlineSendStop() {
		exit = false;
	}

	public void run() {
		try {
			// 建立udp的服务
			DatagramSocket socket = new DatagramSocket();
			// 准备数据，把数据封装到数据包中发送
			String data = "这个是我第一个udp的例子..";
			BufferedReader keyReader = new BufferedReader(
					new InputStreamReader(System.in));

			DatagramPacket packet = null;
			while (exit) {
				// 把数据封装 到数据数据包中，然后发送数据。
				packet = new DatagramPacket(data.getBytes(),
						data.getBytes().length, AimIP, AimPort);
				// 把数据发送出去
				socket.send(packet);
			}
			// 关闭 资源
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
