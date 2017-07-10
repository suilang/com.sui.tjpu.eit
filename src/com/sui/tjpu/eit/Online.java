/**
 * 
 */
package com.sui.tjpu.eit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;

import org.eclipse.ui.PlatformUI;

/**
 * 
 * @author 碎.浪
 * 
 */
public class Online {

	private DatagramSocket socket;
	String AimIP;
	String AimPort;
	ChatReceive chatReceive;
	ChatSender chatSender;
	MyConfigureModel myconfig;
	Control control;
	MyCalculateParameter mycalpara;
	private boolean startflag = false;// 用于确认网络是否开启

	public boolean isStartflag() {
		return startflag;
	}

	public void setStartflag(boolean startflag) {
		this.startflag = startflag;
	}

	public Online(MyConfigureModel myconfig) {
		control = (Control) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage()
				.findView("com.sui.tjpu.eit.control");
		mycalpara = control.getMycalpara();

		this.AimIP = myconfig.getAimIP();
		this.AimPort = myconfig.getAimPort();
		this.myconfig = myconfig;
	}

	/**
	 * 打开网络连接，启动发送和接收线程
	 * 
	 * @throws IOException
	 */
	public void OnlineStrat() throws IOException {
		startflag = true;
		System.err.println("打开网络端口");
		chatReceive = new ChatReceive(mycalpara, AimPort);
		chatReceive.start();

		chatSender = new ChatSender(AimIP, AimPort);
		chatSender.start();
	}

	/**
	 * 停止发送和接收线程，不销毁自身
	 */
	public void stopconnect() {
		startflag = false;
		try {
			chatReceive.onlineReceiveStop();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		chatSender.onlineSendStop();
		send(true);
	}

	/**
	 * 发送指令 是否采集空场，若是，则标志位为true，网络连接数据放置到空场中，并将空场存储为矩阵格式
	 */

	public void send(boolean iskong) {
		if (startflag == true) {
			chatReceive.setkongFlag(iskong);
			synchronized (chatSender) {
				chatSender.notify();
			}
		}
	}

	public static String getLocalAddress() {
		String ip = "";
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ip;
	}

	public static String getV4IP() {
		String ip = "";
		String chinaz = "http://ip.chinaz.com";

		StringBuilder inputLine = new StringBuilder();
		String read = "";
		URL url = null;
		HttpURLConnection urlConnection = null;
		BufferedReader in = null;
		try {
			url = new URL(chinaz);
			urlConnection = (HttpURLConnection) url.openConnection();
			in = new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream(), "UTF-8"));
			while ((read = in.readLine()) != null) {
				inputLine.append(read + "\r\n");
			}
			// System.out.println(inputLine.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		// Pattern p = Pattern.compile("\\<dd class\\=\"fz24\">(.*?)\\<\\/dd>");
		// Matcher m = p.matcher(inputLine.toString());
		// if(m.find()){
		// String ipstr = m.group(1);
		// ip = ipstr;
		// //System.out.println(ipstr);
		// }
		return ip;
	}

}