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
public class MyConfigureModel{
	
	private String cirsText;
	private String kongText;
	private String wuText;
	
	
	public MyConfigureModel(){
		this.cirsText="";
		this.kongText="";
		this.wuText="";
		
		Bundle bundle = Platform.getBundle(Activator.PLUGIN_ID);
		URL url_cirs= bundle.getResource("Source/Cirs.sl");
		try {
			File file=new File(FileLocator.toFileURL(url_cirs).getPath());
			if (file.isFile() && file.exists()) { // �ж��ļ��Ƿ����
				this.cirsText=file.getPath();
			}else{
				this.cirsText="";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		URL url_kong= bundle.getResource("Source/kong.sl");
		try {
			File file=new File(FileLocator.toFileURL(url_kong).getPath());
			if (file.isFile() && file.exists()) { // �ж��ļ��Ƿ����
				this.kongText=file.getPath();
			}
			else{
				this.kongText="";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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