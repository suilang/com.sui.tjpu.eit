package com.sui.tjpu.eit;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import com.sui.tjpu.eit.action.Calculate;

import Jama.Matrix;

public class Control extends ViewPart {

	
	private Text amplifyText;	
	private int amplify;
	private int Ite_number;
	
	private MyControlModel mycontrol;
	private ShowImage showImage;
	private MyCalculateParameter mycalpara;
	
	

	private Calculate calculate;
	
	public MyCalculateParameter getMycalpara() {
		return mycalpara;
	}

	public void setMycalpara(MyCalculateParameter mycalpara) {
		this.mycalpara = mycalpara;
	}

	public int getIte_number() {
		return Ite_number;
	}

	public void setIte_number(int ite_number) {
		Ite_number = ite_number;
	}
	
	public int getAmplify(){
		 amplify=Integer.parseInt(mycontrol.getAmplify());
		return amplify;
		
	}
	
	public void setShowImage(ShowImage showImage){
		this.showImage=showImage;
	}
//	public ArrayList getWubuf() {
//		return wubuf;
//	}
//
//	public void setWubuf(ArrayList wubuf) {
//		this.wubuf = wubuf;
//	}
	
	public Control() {
		// TODO Auto-generated constructor stub
		
		
	}

	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
		
		mycontrol=new MyControlModel();
		mycalpara=new MyCalculateParameter(this);
		Composite composite1 = new Composite(parent, SWT.NONE);
		composite1.setLayout(new FormLayout());
		
		Composite composite = new Composite(composite1, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		FormData fd_composite = new FormData();
		fd_composite.top = new FormAttachment(0);
		fd_composite.left = new FormAttachment(0, 5);
		fd_composite.bottom = new FormAttachment(0, 40);
		fd_composite.right = new FormAttachment(0, 241);
		composite.setLayoutData(fd_composite);
		
		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("Algorithm");
		
		Combo algorithmCombo = new Combo(composite, SWT.NONE);
		algorithmCombo.setItems(mycontrol.getCombStr());
		algorithmCombo.select(0);
		algorithmCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Composite composite_1 = new Composite(composite1, SWT.NONE);
		composite_1.setLayout(new GridLayout(3, false));
		FormData fd_composite_1 = new FormData();
		fd_composite_1.bottom = new FormAttachment(composite, 46, SWT.BOTTOM);
		fd_composite_1.top = new FormAttachment(composite, 6);
		fd_composite_1.right = new FormAttachment(composite, 0, SWT.RIGHT);
		fd_composite_1.left = new FormAttachment(0, 5);
		composite_1.setLayoutData(fd_composite_1);
		
		Label lblNewLabel_1 = new Label(composite_1, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setText("Amplify");
		
		amplifyText = new Text(composite_1, SWT.BORDER);
		amplifyText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		amplifyText.setText(mycontrol.getAmplify());
		Button btnNewButton = new Button(composite_1, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
		
			public void widgetSelected(SelectionEvent e) {
				mycontrol.setAmplify(amplifyText.getText());
			}
		});
		btnNewButton.setText("Entry");
		
		
	}

	public void ParamAllDone(){
		calculate=new Calculate(mycalpara.getCirs());
		mycalpara.setPaintdate(calculate.cgls(mycalpara.getCirs(), mycalpara.getB(0), 40));
		showImage.runpaint();
	}
	public void setFocus() {
		// TODO Auto-generated method stub
        showImage.runpaint();
	}

}
