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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import Jama.Matrix;

import com.sui.tjpu.eit.action.LoadXY;
import com.sui.tjpu.eit.action.OpenFile;

public class Configure extends ViewPart {

	public static final String ID = "com.sui.tjpu.eit.configure";

	private MyConfigureModel myconf;
	

	private Text cirs;
	private Text kong;
	private Text wu;


	private Control control;
	private MyCalculateParameter mycalpara;
    private Online online;
	
	public MyConfigureModel getMyconf() {
		return myconf;
	}
	
	public void ConnLocal(){
		
	}
	
	public void ConnOnline(){
		
		online = new Online(myconf);
	
	}
	
	public Configure() {
		// TODO Auto-generated constructor stub
		control = (Control) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage()
				.findView("com.sui.tjpu.eit.control");
		mycalpara = control.getMycalpara();
		LoadXY loadxy=new LoadXY();
		MyCalculateParameter.paint_X=loadxy.position_x();
		MyCalculateParameter.paint_Y=loadxy.position_y();
	}

	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub

		
		myconf = new MyConfigureModel();

		Composite composite1 = new Composite(parent, SWT.NONE);
		composite1.setLayout(new FormLayout());
		Composite composite = new Composite(composite1, SWT.NONE);
		FormData fd_composite = new FormData();
		fd_composite.top = new FormAttachment(0);
		fd_composite.left = new FormAttachment(0, 5);
		fd_composite.bottom = new FormAttachment(0, 101);
		fd_composite.right = new FormAttachment(0, 241);
		composite.setLayoutData(fd_composite);

		composite.setLayout(new GridLayout(3, false));

		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setAlignment(SWT.CENTER);
		GridData gd_lblNewLabel = new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1);
		gd_lblNewLabel.widthHint = 30;
		lblNewLabel.setLayoutData(gd_lblNewLabel);
		lblNewLabel.setText("Cirs");

		cirs = new Text(composite, SWT.BORDER);
		GridData gd_cirs = new GridData(SWT.CENTER, SWT.CENTER, true, false, 1,
				1);
		gd_cirs.widthHint = 130;
		cirs.setLayoutData(gd_cirs);
		if (myconf.getCirsText() != null) {
			cirs.setText(myconf.getCirsText());
			OpenFile of = new OpenFile();
			Matrix Cirs = of.LoadCirs(myconf.getCirsText());
			mycalpara.setCirs(Cirs);
		}

		Button cirsButton = new Button(composite, SWT.NONE);
		GridData gd_cirsButton = new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1);
		gd_cirsButton.widthHint = 50;
		cirsButton.setLayoutData(gd_cirsButton);
		cirsButton.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				Shell parentShell = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell();
				OpenFile of = new OpenFile();
				of.LoadFile(parentShell, "sl");
				if (of.getSelect() != null) {
					Matrix Cirs = of.LoadCirs(of.getSelect());
					if (Cirs != null) {
						cirs.setText(of.getSelect());
						mycalpara.setCirs(Cirs);
					}
				}

			}
		});
		cirsButton.setText("Load");

		Label lblNewLabel_1 = new Label(composite, SWT.NONE);
		lblNewLabel_1.setAlignment(SWT.CENTER);
		lblNewLabel_1.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		lblNewLabel_1.setText("U0");

		kong = new Text(composite, SWT.BORDER);
		GridData gd_kong = new GridData(SWT.CENTER, SWT.CENTER, true, false, 1,
				1);
		gd_kong.widthHint = 130;
		kong.setLayoutData(gd_kong);
		if (myconf.getKongText() != null) {
			kong.setText(myconf.getKongText());
			OpenFile of = new OpenFile();
			Matrix Kong = of.Loadkong(myconf.getKongText());
			mycalpara.setKong(Kong);
		}

		Button kongButton = new Button(composite, SWT.NONE);
		GridData gd_kongButton = new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1);
		gd_kongButton.widthHint = 50;
		kongButton.setLayoutData(gd_kongButton);
		kongButton.setText("Load");
		kongButton.addSelectionListener(new SelectionAdapter() { // °´Å¥¼àÌýº¯Êý

					public void widgetSelected(SelectionEvent e) {
						Shell parentShell = PlatformUI.getWorkbench()
								.getActiveWorkbenchWindow().getShell();
						OpenFile of = new OpenFile();
						of.LoadFile(parentShell, "sl");
						if (of.getSelect() != null) {
							Matrix Kong = of.Loadkong(of.getSelect());
							if (Kong != null) {
								kong.setText(of.getSelect());
								mycalpara.setKong(Kong);
							}
						}

					}
				});

		Label lblNewLabel_2 = new Label(composite, SWT.NONE);
		lblNewLabel_2.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		lblNewLabel_2.setAlignment(SWT.CENTER);
		lblNewLabel_2.setText("U1");

		wu = new Text(composite, SWT.BORDER);
		GridData gd_wu = new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1);
		gd_wu.widthHint = 130;
		wu.setLayoutData(gd_wu);

		Button wuButton = new Button(composite, SWT.NONE);
		GridData gd_wuButton = new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1);
		gd_wuButton.widthHint = 50;
		wuButton.setLayoutData(gd_wuButton);
		wuButton.setText("Load");
		wuButton.addSelectionListener(new SelectionAdapter() { // °´Å¥¼àÌýº¯Êý

			public void widgetSelected(SelectionEvent e) {
				Shell parentShell = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell();
				OpenFile of = new OpenFile();
				of.LoadFile(parentShell, "sl");
				if (of.getSelect() != null) {
					ArrayList Wu = of.Loadwu(of.getSelect());
					if (Wu != null) {
						wu.setText(of.getSelect());
						mycalpara.setWubuf(Wu);
					}
				}

			}
		});

		// TODO Auto-generated method stub

	}

	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
