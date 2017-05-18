package com.sui.tjpu.eit;


import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class Configure extends ViewPart {

	private MyConfigureModel myconf;
	
	public Configure() {
		// TODO Auto-generated constructor stub
	}

	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
		myconf=new MyConfigureModel();
		
	}

	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
