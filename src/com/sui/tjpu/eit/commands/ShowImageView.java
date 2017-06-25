package com.sui.tjpu.eit.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class ShowImageView extends AbstractHandler implements IHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		// TODO Auto-generated method stub
		try {  
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("com.sui.tjpu.eit.showimage");  
			} catch (PartInitException e) {  
			e.printStackTrace();  
			}  
		return null;
	}

}
