package com.sui.tjpu.eit;

import java.util.ArrayList;

import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.StatusLineContributionItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
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
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.sui.tjpu.eit.action.Calculate;

import Jama.Matrix;

public class Control extends ViewPart {

	private Text amplifyText;
	private Text IterationText;
	private Text FrameText;
	private Text currentframeText;
	private Text totalframeText;

	private Button btnNewButton3;

	private MyControlModel mycontrol;

	private Configure config;

	private MyCalculateParameter mycalpara;
	private ShowImage showImage;
	PaintRun paintrun;
	private Calculate calculate;

	// 面板变量
	private Composite composite_all;
	private StackLayout mainsl;
	private Group compositelocal;
	private Composite compositeonline;

	
	public static final String ID = "com.sui.tjpu.eit.control";
	public Configure getConfig() {
		return config;
	}

	public void setConfig(Configure config) {
		this.config = config;
	}

	public MyControlModel getMycontrol() {
		return mycontrol;
	}

	public void setMycontrol(MyControlModel mycontrol) {
		this.mycontrol = mycontrol;
	}

	public MyCalculateParameter getMycalpara() {
		return mycalpara;
	}

	public void setMycalpara(MyCalculateParameter mycalpara) {
		this.mycalpara = mycalpara;
	}

	public void setShowImage(ShowImage showImage) {
		this.showImage = showImage;
	}

	public ShowImage getShowImage() {
		return showImage;
	}

	public Calculate getCalculate() {
		return calculate;
	}

	public void setCalculate(Calculate calculate) {
		this.calculate = calculate;
	}

	// public ArrayList getWubuf() {
	// return wubuf;
	// }
	//
	// public void setWubuf(ArrayList wubuf) {
	// this.wubuf = wubuf;
	// }

	public Control() {
		// TODO Auto-generated constructor stub

		mycontrol = new MyControlModel();
		mycalpara = new MyCalculateParameter(this);

		paintrun = new PaintRun(this);

	}

	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new FormLayout());

		Composite composite1 = new Composite(composite, SWT.NONE);
		composite1.setLayout(new GridLayout(2, false));
		FormData fd_composite = new FormData();
		fd_composite.top = new FormAttachment(0);
		fd_composite.left = new FormAttachment(0, 5);
		fd_composite.bottom = new FormAttachment(0, 35);
		fd_composite.right = new FormAttachment(0, 241);
		composite1.setLayoutData(fd_composite);

		// 选择算法
		Label lblNewLabel = new Label(composite1, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblNewLabel.setText("Algorithm");

		Combo algorithmCombo = new Combo(composite1, SWT.NONE);
		algorithmCombo.setItems(mycontrol.getCombStr());
		algorithmCombo.select(0);
		algorithmCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		// 放大倍数
		Composite composite_1 = new Composite(composite, SWT.NONE);
		composite_1.setLayout(new GridLayout(3, false));
		FormData fd_composite_1 = new FormData();
		fd_composite_1.bottom = new FormAttachment(composite1, 110, SWT.BOTTOM);
		fd_composite_1.top = new FormAttachment(composite1, 6);
		fd_composite_1.right = new FormAttachment(composite1, 0, SWT.RIGHT);
		fd_composite_1.left = new FormAttachment(0, 5);
		composite_1.setLayoutData(fd_composite_1);

		Label lblNewLabel_1 = new Label(composite_1, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblNewLabel_1.setText("Amplify");

		amplifyText = new Text(composite_1, SWT.BORDER);
		amplifyText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		amplifyText.setText(String.valueOf(mycalpara.getAmplify()));
		Button btnNewButton = new Button(composite_1, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				mycalpara.setAmplify(Integer.parseInt(amplifyText.getText()
						.toString()));
			}
		});
		btnNewButton.setText("Entry");
		// 迭代次数
		Label lblNewLabel_2 = new Label(composite_1, SWT.NONE);
		lblNewLabel_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblNewLabel_2.setText("Iteration");

		IterationText = new Text(composite_1, SWT.BORDER);
		IterationText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		IterationText.setText(String.valueOf(mycalpara.getIte_number()));
		Button btnNewButton1 = new Button(composite_1, SWT.NONE);
		btnNewButton1.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				mycalpara.setIte_number(Integer.parseInt(IterationText
						.getText().toString()));
			}
		});
		btnNewButton1.setText("Entry");

		// 帧数
		Label lblFrame = new Label(composite_1, SWT.NONE);
		lblFrame.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblFrame.setText("frame");

		FrameText = new Text(composite_1, SWT.BORDER);
		FrameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));
		FrameText.setText(String.valueOf(mycontrol.getFrame()));

		Button btnEntry = new Button(composite_1, SWT.NONE);
		btnEntry.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				mycontrol.setFrame(Integer.parseInt(FrameText.getText()));
			}
		});
		btnEntry.setText("Entry");

		/****************************** 堆栈面板 **********************************/
		composite_all = new Composite(composite, SWT.NONE);
		FormData fd_composite_all = new FormData();
		fd_composite_all.bottom = new FormAttachment(composite_1, 160,
				SWT.BOTTOM);
		fd_composite_all.top = new FormAttachment(composite_1, 6);
		fd_composite_all.right = new FormAttachment(composite_1, 0, SWT.RIGHT);
		fd_composite_all.left = new FormAttachment(0, 1);
		composite_all.setLayoutData(fd_composite_all);
		mainsl = new StackLayout();
		composite_all.setLayout(mainsl);
		//
		/****************************** 本地显示控制 **********************************/

		compositelocal = new Group(composite_all, SWT.NONE);
		compositelocal.setLayout(new FormLayout());

		Composite composite2 = new Composite(compositelocal, SWT.NONE);
		composite2.setLayout(new GridLayout(3, false));
		FormData fd_composite2 = new FormData();
		fd_composite2.top = new FormAttachment(0);
		fd_composite2.left = new FormAttachment(0, 3);
		fd_composite2.bottom = new FormAttachment(0, 65);
		fd_composite2.right = new FormAttachment(0, 238);
		composite2.setLayoutData(fd_composite2);
		// 显示当前帧数，可以设置

		Label lblNewLabel_3 = new Label(composite2, SWT.NONE);
		lblNewLabel_3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblNewLabel_3.setText("currentframe");

		currentframeText = new Text(composite2, SWT.BORDER);
		currentframeText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		currentframeText
				.setText(String.valueOf(mycalpara.getCurrentIndex() + 1));

		Button btnNewButton2 = new Button(composite2, SWT.NONE);
		btnNewButton2.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				mycalpara.setCurrentIndex(Integer.parseInt(IterationText
						.getText().toString()));
			}
		});
		btnNewButton2.setText("Entry");

		// 总帧数
		Label lblNewLabel_4 = new Label(composite2, SWT.NONE);
		lblNewLabel_4.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblNewLabel_4.setText("Totalframe");

		totalframeText = new Text(composite2, SWT.BORDER);
		totalframeText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		totalframeText.setText(String.valueOf(mycalpara.getTotalIndex()));

		// 开始结束按钮
		Composite composite_2 = new Composite(compositelocal, SWT.NONE);
		composite_2.setLayout(new GridLayout(2, false));
		FormData fd_composite_2 = new FormData();
		fd_composite_2.bottom = new FormAttachment(composite2, 75, SWT.BOTTOM);
		fd_composite_2.top = new FormAttachment(composite2, 6);
		fd_composite_2.right = new FormAttachment(0, 241);
		fd_composite_2.left = new FormAttachment(0, 4);
		composite_2.setLayoutData(fd_composite_2);

		btnNewButton3 = new Button(composite_2, SWT.NONE);
		GridData gd_btnNewButton3 = new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1);
		gd_btnNewButton3.widthHint = 100;
		btnNewButton3.setLayoutData(gd_btnNewButton3);
		btnNewButton3.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				btnNewButton3.setEnabled(false);
				paintStart();

			}
		});
		btnNewButton3.setText("start");

		Button btnNewButton4 = new Button(composite_2, SWT.NONE);
		GridData gd_btnNewButton4 = new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1);
		gd_btnNewButton4.widthHint = 100;
		btnNewButton4.setLayoutData(gd_btnNewButton4);
		btnNewButton4.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				paintStop();
				btnNewButton3.setEnabled(true);
			}
		});
		btnNewButton4.setText("stop");
		// 下一张
		Button btnNewButton5 = new Button(composite_2, SWT.NONE);
		GridData gd_btnNewButton5 = new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1);
		gd_btnNewButton5.widthHint = 100;
		btnNewButton5.setLayoutData(gd_btnNewButton5);
		btnNewButton5.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {

				calParam(true);
			}
		});
		btnNewButton5.setText("next");

		/****************************** 网络显示控制 ****************************************/
		compositeonline = new Group(composite_all, SWT.NONE);
		compositeonline.setLayout(new FormLayout());
		// TODO Auto-generated method stub

		Composite composite_3 = new Composite(compositeonline, SWT.NONE);
		FormData fd_composite_3 = new FormData();
		fd_composite_3.top = new FormAttachment(0);
		fd_composite_3.left = new FormAttachment(0, 10);
		fd_composite_3.right = new FormAttachment(100, -13);
		composite_3.setLayoutData(fd_composite_3);

		Button setKongButton_1 = new Button(composite_3, SWT.NONE);
		setKongButton_1.setBounds(0, 10, 80, 27);
		setKongButton_1.setText("\u91C7\u7A7A\u573A");
		setKongButton_1.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				if(config.getOnline().isStartflag()){
				config.getOnline().send(true);
				config.updatekong2Text();
				}else{
				//	updatestateline("no online connect");
				}
			}
		});

		Button setwuButton1 = new Button(composite_3, SWT.NONE);
		setwuButton1.setBounds(121, 10, 80, 27);
		setwuButton1.setText("\u91C7\u7269\u573A");
		setwuButton1.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				if(config.getOnline().isStartflag()){
					config.getOnline().send(false);
				}else{
			//		updatestateline("no online connect");
				}
				
			}
		});

		mainsl.topControl = compositelocal;
	}
	
	private void updatestateline(String message){
		 IStatusLineManager statusline = this.getViewSite().getActionBars().getStatusLineManager();
		 StatusLineContributionItem statusItem =
				 new StatusLineContributionItem(message);
		 statusline.add(statusItem);
	}

	/**
	 * 当本地计算参数加载完成后，设置总帧数，唤醒绘图线程，显示第一帧
	 */
	public void ParamAllDone() {

		totalframeText.setText(String.valueOf(mycalpara.getTotalIndex()));

		showImage.wakepaint();
	}

	public void onlineParamAllDone() {
		showImage.wakepaint();
	}

	/**
	 * 更改当前帧数，更新UI面板，唤醒绘图线程，显示下一帧
	 * 
	 * @param direction
	 *            为true时帧数增加，否则减少
	 */
	public void calParam(boolean direction) {
		// mycalpara.setPaintdate(calculate.cgls(mycalpara.getCirs(),
		// mycalpara.getB(currentIndex), 40));
		mycalpara.changeCurrentIndex(direction);
		updatecurrentIndexText();
		showImage.wakepaint();

	}

	/**
	 * 启动自动绘图线程
	 */
	public void paintStart() {

		paintrun.start();

	}

	/**
	 * 停止自动绘图线程
	 */
	public void paintStop() {
		paintrun.stoprun();
	}

	public void setFocus() {
		// TODO Auto-generated method stub

	}

	/**
	 * 更新当前帧数
	 */
	public void updatecurrentIndexText() {
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
			public void run() {
				currentframeText.setText(String.valueOf(mycalpara
						.getCurrentIndex() + 1));
			}

		});
	}

	/**
	 * 改变控制面板 true：本地连接 false：网络连接
	 */
	public void changeComposite(boolean flag) {
		if (flag == true) {
			mainsl.topControl = compositelocal;
		} else {
			mainsl.topControl = compositeonline;
		}
		composite_all.layout(true);
	}
}

class PaintRun extends Thread {
	Control control;
	private boolean exit = true;

	PaintRun(Control control) {

		this.control = control;
	}

	public void run() {
		while (exit) {
			if (MyConfigureModel.connectFlag == true) {
				control.calParam(true);
			} else {
				control.getConfig().getOnline().send(false);
			}

			try {
				Thread.sleep(1000 / control.getMycontrol().getFrame());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void stoprun() {
		exit = false;
	}

}