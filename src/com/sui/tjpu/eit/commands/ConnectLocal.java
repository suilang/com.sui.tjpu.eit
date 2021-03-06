package com.sui.tjpu.eit.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import com.sui.tjpu.eit.Configure;
import com.sui.tjpu.eit.Control;
import com.sui.tjpu.eit.MyConfigureModel;

public class ConnectLocal extends AbstractHandler implements IHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		// TODO Auto-generated method stub
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
		IWorkbenchPage page = window.getActivePage();
		Configure config = (Configure) page.findView(Configure.ID);
		Control control = (Control) page.findView(Control.ID);
		if(MyConfigureModel.connectFlag == false){
			MyConfigureModel.connectFlag = true;
			config.changeComposite(true);
			control.changeComposite(true);
		}
		return null;
	}

}
