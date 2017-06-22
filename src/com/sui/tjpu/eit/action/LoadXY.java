package com.sui.tjpu.eit.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;

import com.sui.tjpu.eit.Activator;
/**
 * 加载像素值的位置信息，分为X和Y
 * 
 * @author 碎.浪
 *
 */
public class LoadXY{
	
	double[] temp=null;
	Scanner in;
	int Index;
	
	public double[] position_x(){
		temp=new double[812];
		File file;
		String path = null;
		String str;
		Scanner in=null;
		int Index=0;
		Bundle bundle = Platform.getBundle(Activator.PLUGIN_ID);
		URL url_x = bundle.getResource("Source/x.txt");
		try {
			path=FileLocator.toFileURL(url_x).getPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	   file=new File(path);
	   if (file.isFile() && file.exists()) { // 判断文件是否存在
		   try {
		   		 in = new Scanner(file);
		   		 while (in.hasNextLine())
		           {
		               str = in.nextLine();
		               temp[Index]=Double.parseDouble(str);		             
		               Index++;
		            }         
				}catch (FileNotFoundException e) {
		             e.printStackTrace();
		        }
		      finally{		    	  		      
		       	in.close(); 		       	
		        }	   		 
	   }
				
		return temp;
	
	}
	public double[] position_y(){
		temp=new double[812];
		File file;
		String path = null;
		String str;
		Scanner in=null;
		int Index=0;
		Bundle bundle = Platform.getBundle(Activator.PLUGIN_ID);
		URL url_y = bundle.getResource("Source/y.txt");
		try {
			path=FileLocator.toFileURL(url_y).getPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	   file=new File(path);
	   if (file.isFile() && file.exists()) { // 判断文件是否存在
		   try {
		   		 in = new Scanner(file);
		   		 while (in.hasNextLine())
		           {
		               str = in.nextLine();
		               temp[Index]=Double.parseDouble(str);		             
		               Index++;
		            }         
				}catch (FileNotFoundException e) {
		             e.printStackTrace();
		        }
		      finally{		    	  		      
		       	in.close(); 		       	
		        }	   		 
	   }	
		return temp;
	
	}
	
	
}