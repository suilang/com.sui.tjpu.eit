package com.sui.tjpu.eit;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class Perspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible( false );
//		 String editorArea = layout.getEditorArea();
//		 layout.addView(ShowImage.ID, IPageLayout.LEFT, 0.7f, editorArea);
//		 layout.addView(Configure.ID, IPageLayout.BOTTOM, 0.3f, editorArea);
		
	}
}
