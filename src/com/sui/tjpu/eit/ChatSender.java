package com.sui.tjpu.eit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

//Ⱥ�ķ��Ͷ�
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
			// ����udp�ķ���
			DatagramSocket socket = new DatagramSocket();
			// ׼�����ݣ������ݷ�װ�����ݰ��з���
			String data = "������ҵ�һ��udp������..";
			BufferedReader keyReader = new BufferedReader(
					new InputStreamReader(System.in));

			DatagramPacket packet = null;
			while (exit) {
				// �����ݷ�װ ���������ݰ��У�Ȼ�������ݡ�
				packet = new DatagramPacket(data.getBytes(),
						data.getBytes().length, AimIP, AimPort);
				// �����ݷ��ͳ�ȥ
				socket.send(packet);
			}
			// �ر� ��Դ
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
