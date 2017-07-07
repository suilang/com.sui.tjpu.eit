package com.sui.tjpu.eit;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

/**
 * ���ã��洢����ҳ��Ĳ���
 * 
 * 
 * 
 * 
 * @author ������
 * 
 */
public class MyConfigureModel {

	private String cirsText;
	private String kongText;
	private String wuText;

	private String LocalIP;
	private String LocalSock;
	private String LocalState;

	private String AimIP = "192.168.1.178";
	private String AimPort = "30000";
	private String onlinekongText;
	private String onlinecirsText;

	private boolean connectFlag = true;// true���������ӣ�false������������

	

	public boolean isConnectFlag() {
		return connectFlag;
	}

	public void setConnectFlag(boolean connectFlag) {
		this.connectFlag = connectFlag;
	}

	public String getLocalIP() {
		return LocalIP;
	}

	public void setLocalIP(String localIP) {
		this.LocalIP = localIP;
	}

	public String getLocalSock() {
		return LocalSock;
	}

	public void setLocalSock(String localSock) {
		LocalSock = localSock;
	}

	public String getLocalState() {
		return LocalState;
	}

	public void setLocalState(String localState) {
		LocalState = localState;
	}

	public String getAimIP() {
		return AimIP;
	}

	public void setAimIPText(String aimIP) {
		AimIP = aimIP;
	}

	public String getAimPort() {
		return AimPort;
	}

	public void setAimPort(String aimPort) {
		AimPort = aimPort;
	}

	public String getOnlinekongText() {
		return onlinekongText;
	}

	public void setOnlinekongText(String onlinekongText) {
		this.onlinekongText = onlinekongText;
	}

	public MyConfigureModel() {
		this.cirsText = "";
		this.kongText = "";
		this.wuText = "";
		InitFile();

	}

	/**
	 * �ڳ�ʼ��ʱ�����Դ�洢����������Ӧ��Դ������� ������ ����ʱ�ճ����ﳡ�������Ⱦ��� ����ʱ�������Ⱦ��󡢿ճ�
	 */
	private void InitFile() {
		URL url_cirs = null;
		Bundle bundle = Platform.getBundle(Activator.PLUGIN_ID);

		url_cirs = bundle.getResource("Source/Local/Cirs.sl");

		try {
			File file = new File(FileLocator.toFileURL(url_cirs).getPath());
			if (file.isFile() && file.exists()) { // �ж��ļ��Ƿ����
				this.cirsText = file.getPath();
			} else {
				this.cirsText = null;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			this.cirsText = null;
		}

		URL url_kong = bundle.getResource("Source/Local/kong.sl");
		try {
			File file = new File(FileLocator.toFileURL(url_kong).getPath());
			if (file.isFile() && file.exists()) { // �ж��ļ��Ƿ����
				this.kongText = file.getPath();
			} else {
				this.kongText = null;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			this.kongText = null;
		}

		
		URL url_onlinecirs = bundle.getResource("Source/Online/Cirs.sl");

		try {
			File file = new File(FileLocator.toFileURL(url_onlinecirs).getPath());
			if (file.isFile() && file.exists()) { // �ж��ļ��Ƿ����
				this.onlinecirsText = file.getPath();
				
			} else {
				this.onlinecirsText = null;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			this.onlinecirsText = null;
		}
		
		URL url_onlinekong = bundle.getResource("Source/Online/kong.sl");
		try {
			File file = new File(FileLocator.toFileURL(url_onlinekong)
					.getPath());
			if (file.isFile() && file.exists()) { // �ж��ļ��Ƿ����
				this.onlinekongText = file.getPath();
			} else {
				this.onlinekongText = null;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			this.onlinekongText = null;
		}
	}

	/**
	 * 
	 * @param cirsText
	 * @param kongText
	 */

	public MyConfigureModel(String cirsText, String kongText) {
		this.cirsText = cirsText;
		this.kongText = kongText;
	}

	public String getCirsText() {
		return cirsText;
	}

	public void setCirsText(String cirsText) {
		this.cirsText = cirsText;
	}

	public String getKongText() {
		return kongText;
	}

	public void setKongText(String kongText) {
		this.kongText = kongText;
	}

	public String getWuText() {
		return wuText;
	}

	public void setWuText(String wuText) {
		this.wuText = wuText;
	}

	public String getOnlinecirsText() {
		return onlinecirsText;
	}

	public void setOnlinecirsText(String onlinecirsText) {
		this.onlinecirsText = onlinecirsText;
	}

}