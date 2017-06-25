package com.sui.tjpu.eit;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;



/**
 * 作用：存储配置页面的参数
 * 
 * 
 * 
 * 
 * @author 张鹏程
 *
 */
public class MyConfigureModel{
	
	private String cirsText;
	private String kongText;
	private String wuText;
	
	private String LocalIP;
	private String LocalSock;
	private String LocalState;
	
	private String AimIP;
	private String AimPort;
	
	
	
	private boolean connectFlag=true;//true代表本地连接，false代表网络连接
	
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

	
	
	public MyConfigureModel(){
		this.cirsText="";
		this.kongText="";
		this.wuText="";
		URL url_cirs=null;
		Bundle bundle = Platform.getBundle(Activator.PLUGIN_ID);

		url_cirs= bundle.getResource("Source/Cirs.sl");

		try {
			File file=new File(FileLocator.toFileURL(url_cirs).getPath());
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				this.cirsText=file.getPath();
			}else{
				this.cirsText=null;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(NullPointerException e){
			this.cirsText=null;
		}
		URL url_kong= bundle.getResource("Source/kong.sl");
		try {
			File file=new File(FileLocator.toFileURL(url_kong).getPath());
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				this.kongText=file.getPath();
			}
			else{
				this.kongText=null;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(NullPointerException e){
			this.kongText=null;
		}
		
	}
	
	public MyConfigureModel(String cirsText,String kongText){
		this.cirsText=cirsText;
		this.kongText=kongText;
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

	
}