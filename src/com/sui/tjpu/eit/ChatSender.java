package com.sui.tjpu.eit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

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
			Shell parentShell = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getShell();
			MessageDialog.openWarning(parentShell, "Warning",
					"Ŀ��IP��Ч������������");
			return;
		}
		this.AimPort = Integer.parseInt(AimPort);
	}

	public void onlineSendStop() {
		exit = false;
	}


	
	public void run() {
		try {
			System.err.println("���������߳�");
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
				
				synchronized (this) {
					try {
						// don't make loop too tight, or not enough time
						// to process window messages properly
						//sleep(500);
						wait();
					} catch (InterruptedException interruptedexception) {
						// we just quit on interrupt, so nothing required here
					}
				}
				if(exit==false){
					break;
				}
				socket.send(packet);
				System.err.println("��������");
			}
			// �ر� ��Դ
			socket.close();
			System.err.println("�رշ����߳�");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
