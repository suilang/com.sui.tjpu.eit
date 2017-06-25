package com.sui.tjpu.eit;



import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.plugin.AbstractUIPlugin;


public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

	private IWorkbenchWindow window;
	private TrayItem trayItem;
	private Image trayImage;
	private final static String COMMAND_ID = "com.sui.tjpu.eit.commands.Exit";
	
    public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        super(configurer);
    }

    public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
        return new ApplicationActionBarAdvisor(configurer);
    }
    
    public void preWindowOpen() {
        IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
        configurer.setInitialSize(new Point(900, 700));
        configurer.setShowCoolBar(false);
        configurer.setShowStatusLine(false);
        
    }
    
    public void postWindowOpen() { 
    	super.postWindowOpen();
    	window = getWindowConfigurer().getWindow();
    
    	Rectangle screenSize =Display.getDefault().getClientArea();//获得屏幕大小和程序窗口大小
    	Rectangle clientSize=window.getShell().getBounds();    
    	window.getShell().setLocation((screenSize.width-clientSize.width)/2, (screenSize.height-clientSize.height)/2);//使程序窗口放在屏幕中心
    	
    	trayItem = initTaskItem(window); 
	    if (trayItem != null) {
	    	createMinimize(); // Create exit and about action on the icon
	    	hookPopupMenu(); 
	    	}
	
	    }
    
    private void createMinimize() { 
    	window.getShell().addShellListener(new ShellAdapter() {
    	public void shellIconified(ShellEvent e) { 
    		window.getShell().setVisible(false);
    	} 
    	});
    	trayItem.addListener(SWT.DefaultSelection, new Listener() { 
    		public void handleEvent(Event event) {
    			Shell shell = window.getShell(); 
    			if (!shell.isVisible()) {
    				shell.setVisible(true); 
    				window.getShell().setMinimized(false);
    			} 
    		}
    	});

    }
    private void hookPopupMenu() {
		trayItem.addListener(SWT.MenuDetect, new Listener() { 
			public void handleEvent(Event event) {
				
		    	Menu menu = new Menu(window.getShell(), SWT.POP_UP); // Creates a new menu item that terminates the program
		    	// when selected
		    	MenuItem exit = new MenuItem(menu, SWT.NONE); exit.setText("Exit");
		    	exit.addListener(SWT.Selection, new Listener() { 
		    		public void handleEvent(Event event) {
			        	// Lets call our command 
			    		IHandlerService handlerService = (IHandlerService) window.getService(IHandlerService.class); 
			    		try {
			    			handlerService.executeCommand(COMMAND_ID, null); 
			    			} 
			    		catch (Exception ex) {
			    			throw new RuntimeException(COMMAND_ID); 
			    		}
		    		}
		    	});
				// We need to make the menu visible 
		    	menu.setVisible(true);
			} 
		});
	}
    
    private TrayItem initTaskItem(IWorkbenchWindow window) { 
		final Tray tray = window.getShell().getDisplay().getSystemTray();
		TrayItem trayItem = new TrayItem(tray, SWT.NONE);
		trayImage = AbstractUIPlugin.imageDescriptorFromPlugin("com.sui.tjpu.eit", "icons/tubiao.png").createImage(); 
		trayItem.setImage(trayImage);
		trayItem.setToolTipText("TJPU-EIT"); 
		return trayItem;
	}
	
	public void dispose() {
		if (trayImage != null) { 
			trayImage.dispose();
			trayItem.dispose();
		}
	} 
}
