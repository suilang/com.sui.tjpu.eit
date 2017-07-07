package com.sui.tjpu.eit;

import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
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
import org.eclipse.swt.widgets.Group;
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

	private Text cirs1Text;
	private Text kong1Text;
	private Text wu1Text;

	private Text cirs2Text;
	private Text kong2Text;
	private Text wu2Text;
	private Text AimIPText;
	private Text AimPortText;

	private Control control;
	private MyCalculateParameter mycalpara;
	private Online online;

	// 面板变量
	Composite composite_all;
	StackLayout mainsl;
	Composite composite1;
	Composite composite2;

	public MyConfigureModel getMyconf() {
		return myconf;
	}

	public void ConnLocal() {

	}

	public void ConnOnline() {

		if (online == null) {
			online = new Online(myconf);
		}
		if(!online.isStartflag()){
		try {
			online.OnlineStrat();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("网络启动错误");
		}
		}
	}

	public Configure() {

		// TODO Auto-generated constructor stub
		control = (Control) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage()
				.findView("com.sui.tjpu.eit.control");
		mycalpara = control.getMycalpara();
		LoadXY loadxy = new LoadXY();
		MyCalculateParameter.paint_X = loadxy.position_x();
		MyCalculateParameter.paint_Y = loadxy.position_y();
	}

	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub

		myconf = new MyConfigureModel();
		MyProoerty.setMyconf(myconf);
		// 堆栈布局
		composite_all = new Composite(parent, SWT.NONE);
		mainsl = new StackLayout();
		composite_all.setLayout(mainsl);
		// 本地连接配置界面
		composite1 = new Composite(composite_all, SWT.NONE);
		composite1.setLayout(new FormLayout());
		Composite composite = new Composite(composite1, SWT.NONE);
		FormData fd_composite = new FormData();
		fd_composite.top = new FormAttachment(0);
		fd_composite.left = new FormAttachment(0, 5);
		fd_composite.bottom = new FormAttachment(0, 101);
		fd_composite.right = new FormAttachment(0, 241);
		composite.setLayoutData(fd_composite);

		composite.setLayout(new GridLayout(3, false));

		Label CirsLabel1 = new Label(composite, SWT.NONE);
		CirsLabel1.setAlignment(SWT.CENTER);
		GridData gd_lblCirsLabel1 = new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1);
		gd_lblCirsLabel1.widthHint = 30;
		CirsLabel1.setLayoutData(gd_lblCirsLabel1);
		CirsLabel1.setText("Cirs");

		cirs1Text = new Text(composite, SWT.BORDER);
		GridData gd_cirs = new GridData(SWT.CENTER, SWT.CENTER, true, false, 1,
				1);
		gd_cirs.widthHint = 130;
		cirs1Text.setLayoutData(gd_cirs);
		if (myconf.getCirsText() != null) {
			cirs1Text.setText(myconf.getCirsText());
			OpenFile of = new OpenFile();
			Matrix Cirs = of.LoadCirs(myconf.getCirsText());
			mycalpara.setCirs(Cirs);
		}

		Button cirsButton1 = new Button(composite, SWT.NONE);
		GridData gd_cirsButton1 = new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1);
		gd_cirsButton1.widthHint = 50;
		cirsButton1.setLayoutData(gd_cirsButton1);
		cirsButton1.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				Shell parentShell = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell();
				OpenFile of = new OpenFile();
				of.LoadFile(parentShell, "sl");
				if (of.getSelect() != null) {
					Matrix Cirs = of.LoadCirs(of.getSelect());
					if (Cirs != null) {
						cirs1Text.setText(of.getSelect());
						mycalpara.setCirs(Cirs);
					}
				}

			}
		});
		cirsButton1.setText("Load");

		Label U0Label1 = new Label(composite, SWT.NONE);
		U0Label1.setAlignment(SWT.CENTER);
		U0Label1.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		U0Label1.setText("U0");

		kong1Text = new Text(composite, SWT.BORDER);
		GridData gd_kong1 = new GridData(SWT.CENTER, SWT.CENTER, true, false,
				1, 1);
		gd_kong1.widthHint = 130;
		kong1Text.setLayoutData(gd_kong1);
		if (myconf.getKongText() != null) {
			kong1Text.setText(myconf.getKongText());
			OpenFile of = new OpenFile();
			Matrix Kong = of.Loadkong(myconf.getKongText());
			mycalpara.setKong(Kong);
		}

		Button kongButton1 = new Button(composite, SWT.NONE);
		GridData gd_kongButton1 = new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1);
		gd_kongButton1.widthHint = 50;
		kongButton1.setLayoutData(gd_kongButton1);
		kongButton1.setText("Load");
		kongButton1.addSelectionListener(new SelectionAdapter() { // 按钮监听函数

					public void widgetSelected(SelectionEvent e) {
						Shell parentShell = PlatformUI.getWorkbench()
								.getActiveWorkbenchWindow().getShell();
						OpenFile of = new OpenFile();
						of.LoadFile(parentShell, "sl");
						if (of.getSelect() != null) {
							Matrix Kong = of.Loadkong(of.getSelect());
							if (Kong != null) {
								kong1Text.setText(of.getSelect());
								mycalpara.setKong(Kong);
							}
						}

					}
				});

		Label U1Label1 = new Label(composite, SWT.NONE);
		U1Label1.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		U1Label1.setAlignment(SWT.CENTER);
		U1Label1.setText("U1");

		wu1Text = new Text(composite, SWT.BORDER);
		GridData gd_wu = new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1);
		gd_wu.widthHint = 130;
		wu1Text.setLayoutData(gd_wu);

		Button wuButton1 = new Button(composite, SWT.NONE);
		GridData gd_wuButton1 = new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1);
		gd_wuButton1.widthHint = 50;
		wuButton1.setLayoutData(gd_wuButton1);
		wuButton1.setText("Load");
		wuButton1.addSelectionListener(new SelectionAdapter() { // 按钮监听函数

					public void widgetSelected(SelectionEvent e) {
						Shell parentShell = PlatformUI.getWorkbench()
								.getActiveWorkbenchWindow().getShell();
						OpenFile of = new OpenFile();
						of.LoadFile(parentShell, "sl");
						if (of.getSelect() != null) {
							ArrayList Wu = of.Loadwu(of.getSelect());
							if (Wu != null) {
								wu1Text.setText(of.getSelect());
								mycalpara.setWubuf(Wu);
							}
						}

					}
				});

		
		/************************** 网络连接面板***********************************************************/

		composite2 = new Composite(composite_all, SWT.NONE);
		composite2.setLayout(new FormLayout());

		// Composite composite2_1 = new Composite(composite2, SWT.NONE);
		Group composite2_1 = new Group(composite2, SWT.NONE);
		FormData fd_composite2_1 = new FormData();
		fd_composite2_1.top = new FormAttachment(0);
		fd_composite2_1.left = new FormAttachment(0, 5);
		fd_composite2_1.bottom = new FormAttachment(0, 170);
		fd_composite2_1.right = new FormAttachment(0, 241);
		composite2_1.setLayoutData(fd_composite2_1);

		composite2_1.setLayout(new GridLayout(3, false));

		composite2_1.setText("网络连接");

		Label CirsLabel2 = new Label(composite2_1, SWT.NONE);
		CirsLabel2.setAlignment(SWT.CENTER);
		GridData gd_lblCirsLabel2 = new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1);
		gd_lblCirsLabel2.widthHint = 30;
		CirsLabel2.setLayoutData(gd_lblCirsLabel2);
		CirsLabel2.setText("Cirs");

		cirs2Text = new Text(composite2_1, SWT.BORDER);
		GridData gd_cirs2 = new GridData(SWT.CENTER, SWT.CENTER, true, false,
				1, 1);
		gd_cirs2.widthHint = 130;
		cirs2Text.setLayoutData(gd_cirs2);
		if (myconf.getOnlinecirsText() != null) {
			cirs2Text.setText(myconf.getOnlinecirsText());
			OpenFile of = new OpenFile();
			Matrix Cirs = of.LoadCirs(myconf.getOnlinecirsText());
			mycalpara.setOnlineCirs(Cirs);
		}

		Button cirsButton2 = new Button(composite2_1, SWT.NONE);
		GridData gd_cirsButton2 = new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1);
		gd_cirsButton2.widthHint = 50;
		cirsButton2.setLayoutData(gd_cirsButton2);
		cirsButton2.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				Shell parentShell = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell();
				OpenFile of = new OpenFile();
				of.LoadFile(parentShell, "sl");
				if (of.getSelect() != null) {
					Matrix Cirs = of.LoadCirs(of.getSelect());
					if (Cirs != null) {
						cirs2Text.setText(of.getSelect());
						mycalpara.setOnlineCirs(Cirs);
					}
				}

			}
		});
		cirsButton2.setText("Load");

		Label U0Label2 = new Label(composite2_1, SWT.NONE);
		U0Label2.setAlignment(SWT.CENTER);
		U0Label2.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		U0Label2.setText("U0");

		kong2Text = new Text(composite2_1, SWT.BORDER);
		GridData gd_kong2 = new GridData(SWT.CENTER, SWT.CENTER, true, false,
				1, 1);
		gd_kong2.widthHint = 130;
		kong2Text.setLayoutData(gd_kong2);
		if (myconf.getOnlinekongText() != null) {
			kong2Text.setText(myconf.getOnlinekongText());
			OpenFile of = new OpenFile();
			Matrix Kong = of.Loadkong(myconf.getOnlinekongText());
			mycalpara.setOnlinekong(Kong);
		}

		Button kongButton2 = new Button(composite2_1, SWT.NONE);
		GridData gd_kongButton2 = new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1);
		gd_kongButton2.widthHint = 50;
		kongButton2.setLayoutData(gd_kongButton2);
		kongButton2.setText("Load");
		kongButton2.addSelectionListener(new SelectionAdapter() { // 按钮监听函数

					public void widgetSelected(SelectionEvent e) {
						Shell parentShell = PlatformUI.getWorkbench()
								.getActiveWorkbenchWindow().getShell();
						OpenFile of = new OpenFile();
						of.LoadFile(parentShell, "sl");
						if (of.getSelect() != null) {
							Matrix Kong = of.Loadkong(of.getSelect());
							if (Kong != null) {
								kong2Text.setText(of.getSelect());
								mycalpara.setOnlinekong(Kong);
							}
						}

					}
				});

		Label AimIPLabel = new Label(composite2_1, SWT.NONE);
		AimIPLabel.setAlignment(SWT.CENTER);
		AimIPLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		AimIPLabel.setText("AimIP");

		AimIPText = new Text(composite2_1, SWT.BORDER);
		GridData gd_AimIPText = new GridData(SWT.CENTER, SWT.CENTER, true,
				false, 1, 1);
		gd_AimIPText.widthHint = 130;
		AimIPText.setLayoutData(gd_AimIPText);
		if (myconf.getAimIP() != null) {
			AimIPText.setText(myconf.getAimIP());

		}

		Label spaceLabel = new Label(composite2_1, SWT.NONE);
		spaceLabel.setAlignment(SWT.CENTER);
		spaceLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		spaceLabel.setText("    ");

		Label AimPortLabel = new Label(composite2_1, SWT.NONE);
		AimPortLabel.setAlignment(SWT.CENTER);
		AimPortLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		AimPortLabel.setText("AimPort");

		AimPortText = new Text(composite2_1, SWT.BORDER);
		GridData gd_AimPortText = new GridData(SWT.CENTER, SWT.CENTER, true,
				false, 1, 1);
		gd_AimPortText.widthHint = 130;
		AimPortText.setLayoutData(gd_AimPortText);
		if (myconf.getAimPort() != null) {
			AimPortText.setText(myconf.getAimPort());

		}

		Label spaceLabel2 = new Label(composite2_1, SWT.NONE);
		spaceLabel2.setAlignment(SWT.CENTER);
		spaceLabel2.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		spaceLabel2.setText("    ");
		
		
		Button AimIPButton = new Button(composite2_1, SWT.NONE);
		GridData gd_AimIPButton = new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1);
		gd_AimIPButton.widthHint = 50;
		AimIPButton.setLayoutData(gd_AimIPButton);
		AimIPButton.setText("Connect");
		AimIPButton.addSelectionListener(new SelectionAdapter() { // 按钮监听函数

					public void widgetSelected(SelectionEvent e) {
						if (AimIPText.getText() != ""
								&& AimIPText.getText() != null) {
							myconf.setAimIPText(AimIPText.getText());
						} else {
							Shell parentShell = PlatformUI.getWorkbench()
									.getActiveWorkbenchWindow().getShell();
							MessageDialog.openWarning(parentShell, "Warning",
									"请输入目标IP和目标Port");
							return;
						}
						if (AimPortText.getText() != ""
								&& AimPortText.getText() != null) {
							myconf.setAimPort(AimPortText.getText());
						} else {
							Shell parentShell = PlatformUI.getWorkbench()
									.getActiveWorkbenchWindow().getShell();
							MessageDialog.openWarning(parentShell, "Warning",
									"请输入目标IP和目标Port");
							return;
						}
						ConnOnline();

					}
				});

		mainsl.topControl = composite1;

		Button StopButton = new Button(composite2_1, SWT.NONE);
		GridData gd_StopButton = new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1);
		gd_StopButton.widthHint = 50;
		StopButton.setLayoutData(gd_StopButton);
		StopButton.setText("Stop");
		StopButton.addSelectionListener(new SelectionAdapter() { // 按钮监听函数

					public void widgetSelected(SelectionEvent e) {
						online.stopconnect();
					}
				});
	}

	/*
	 * 改变控制面板 true：本地连接 false：网络连接
	 */
	public void changeComposite(boolean flag) {
		if (flag == true) {
			mainsl.topControl = composite1;
		} else {
			mainsl.topControl = composite2;
		}
		composite_all.layout(true);
	}

	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
