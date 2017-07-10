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
			Shell parentShell = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getShell();
			MessageDialog.openWarning(parentShell, "Warning",
					"目标IP无效，请重新输入");
			return;
		}
		this.AimPort = Integer.parseInt(AimPort);
	}

	public void onlineSendStop() {
		exit = false;
	}


	
	public void run() {
		try {
			System.err.println("启动发送线程");
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
				System.err.println("发送数据");
			}
			// 关闭 资源
			socket.close();
			System.err.println("关闭发送线程");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
