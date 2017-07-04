package com.sui.tjpu.eit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.opengl.GLCanvas;
import org.eclipse.swt.opengl.GLData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2ES1;
import com.jogamp.opengl.GL2GL3;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLDrawableFactory;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;
import com.sui.tjpu.eit.action.Calculate;

import Jama.Matrix;

public class ShowImage extends ViewPart{

	public static final String ID = "com.sui.tjpu.eit.showimage";

	private Control control;
	private MyCalculateParameter mycalpara;
	
	private Composite composite;

	/** Widget that displays OpenGL content. */
	private GLCanvas glcanvas;

	/** Used to get OpenGL object that we need to access OpenGL functions. */
	private GLContext glcontext;

	float[] rgb;
	int rot = 0;
    double[][] paintdate;
    RunPaint runpaint;
    
	public RunPaint getRunpaint() {
		return runpaint;
	}

	public ShowImage() {
		// TODO Auto-generated constructor stub
		control = (Control) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage()
				.findView("com.sui.tjpu.eit.control");
		control.setShowImage(this);
		mycalpara = control.getMycalpara();
		//calculate = new Calculate();
		runpaint=new RunPaint(this,control);
		
	}

	public void createPartControl(Composite parent) {
		
		
		// TODO Auto-generated method stub
		GLProfile glprofile = GLProfile.get(GLProfile.GL2);

		composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new FillLayout());

		GLData gldata = new GLData();
		gldata.doubleBuffer = true;
		glcanvas = new GLCanvas(composite, SWT.NO_BACKGROUND, gldata);
		glcanvas.setCurrent();
		glcontext = GLDrawableFactory.getFactory(glprofile)
				.createExternalGLContext();

		glcanvas.addListener(SWT.Resize, new Listener() {
			public void handleEvent(Event event) {
				Rectangle bounds = glcanvas.getBounds();
				float fAspect = (float) bounds.width / (float) bounds.height;
				glcanvas.setCurrent();
				glcontext.makeCurrent();
				GL2 gl = glcontext.getGL().getGL2();
				 gl.glViewport(0, 0, bounds.width, bounds.height);
	                gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
	                gl.glLoadIdentity();
	                
	                GLU glu = new GLU();
	                glu.gluOrtho2D(-50*fAspect, 50*fAspect, -50,50); 
//	                glu.gluPerspective(45.0f, fAspect, 0.5f, 400.0f);
	                gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
	                gl.glLoadIdentity();
	                glcontext.release();
				wakepaint();
			}
		});


		glcontext.makeCurrent();
		GL2 gl = glcontext.getGL().getGL2();
		gl.setSwapInterval(1);
	
		//gl.glColor3f(1.0f, 0.0f, 0.0f);
//		gl.glHint(GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
		gl.glClearDepth(1.0);
//		gl.glLineWidth(2);
		gl.glEnable(GL.GL_DEPTH_TEST);
		
		glcontext.release();
		runpaint.start();
	}

	protected void render() {
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
			public void run() {
				if ((glcanvas != null) && !glcanvas.isDisposed()) {
					glcanvas.setCurrent();
					glcontext.makeCurrent();
					Rectangle rect=glcanvas.getClientArea();
					int iWidth = rect.width;
			        int iHeight =rect.height;
			        int isize=Math.min(iWidth, iHeight);
			        
					GL2 gl = glcontext.getGL().getGL2();		
					
					gl.glClearColor(.3f, .5f, .8f, 1.0f);				
					gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
					gl.glLoadIdentity();

					float pointsize=isize/30.0f;
					 gl.glMatrixMode(GL2.GL_MODELVIEW);
					
					  gl.glPointSize(pointsize);      
					drawTorus(gl);
					
					glcanvas.swapBuffers();
					glcontext.release();
				}
			}
		});
	}

	protected void drawTorus(GL2 gl) {
		
		gl.glBegin(GL2.GL_POINTS);
		paintdate=mycalpara.getPaintdate();
		
		for (int i = 0; i < 812; i++) {
			 rgb=RGBS(paintdate[i][0]*mycalpara.getAmplify()*-1);
			 //System.out.println(paintdate[i][0]);
			 gl.glColor3f(rgb[0], rgb[1], rgb[2]);
			// System.out.println(rgb[0]+"+"+rgb[1]+"+"+rgb[2]);
			// gl.glVertex2f((float)MyCalculateParameter.paint_X[i]*48*pointsize,(float)MyCalculateParameter.paint_Y[i]*48*pointsize);
			 gl.glVertex2f((float)MyCalculateParameter.paint_X[i]*48,(float)MyCalculateParameter.paint_Y[i]*48);
			// //画点由glBegin(GL.GL_POINTS)开始，glEnd()结束
			
		}
		
		gl.glEnd();

	}

	public void setFocus() {
		// TODO Auto-generated method stub

	}

	public void dispose() {
		glcanvas.dispose();
		super.dispose();
	}
	
	public void wakepaint(){
		
		synchronized (runpaint) {
			runpaint.notify();
		}
	}
	public void runpaint() {
		
		runpaint.start();
//		// TODO Auto-generated method stub
//		(new Thread() {
//			public void run() {
//				while ((glcanvas != null) && !glcanvas.isDisposed()) {
//					render();
//					try {
//						// don't make loop too tight, or not enough time
//						// to process window messages properly
//						//sleep(500);
//						wait();
//					} catch (InterruptedException interruptedexception) {
//						// we just quit on interrupt, so nothing required here
//					}
//				}
//			}
//		}).start();
	}

	/*
	 * 依据运算的值赋值颜色值
	 */
	public float[] RGBS(double  color){
		float[] color_temp=new float[3];
		if(color>0)
		{
			if(color<127)
			{
				color_temp[0]=(float) (127+color)/255;
				color_temp[1]=1f;
				color_temp[2]=(float)(127-color)/255;
			}
			else if(color<382)
			{
				color_temp[0]=1f;
				color_temp[1]=(float)(382-color)/255;
				color_temp[2]=0f;
			}
			else if(color<500)
			{
				color_temp[0]=(float) (637-color)/255;
				color_temp[1]=0f;
				color_temp[2]=0f;
				
			}
			else
			{
				color_temp[0]=(float)135/255;
				color_temp[1]=0;
				color_temp[2]=0;
			}
		}
		else if(color==0)
		{
			color_temp[0]=(float)127/255;
			color_temp[1]=color_temp[0];
			color_temp[2]=color_temp[0];
		}
		else {
			if(color>-127)
			{
				color_temp[0]=(float) (127+color)/255;
				color_temp[1]=1f;
				color_temp[2]=(float)(127-color)/255;
			}
			else if(color>-382)
			{
				color_temp[0]=0f;
				color_temp[1]=(float)(382-color)/255;
				color_temp[2]=1f;
			}
			else if(color>-500)
			{
				color_temp[0]=0;
				color_temp[1]=0f;
				color_temp[2]=(float) (637-color)/255;
			}
			else
			{
				color_temp[0]=0f;
				color_temp[1]=0f;
				color_temp[2]=(float)135/255;
			}
		}
		if(color_temp[0]==0&&color_temp[1]==0&&color_temp[2]==0)
		{
			System.out.println(color);
		}
		return color_temp;
	}
	
}

class RunPaint extends Thread {
	private ShowImage showimage;
	private Calculate calculate;
	//private Control control;
	private MyCalculateParameter mycalpara;
	public  RunPaint(ShowImage showimage,Control control){
		this.showimage=showimage;
		this.mycalpara=control.getMycalpara();
		calculate=new Calculate();
	}
	
	 public void run() {
		 
		 while(!isInterrupted()){
			 if(mycalpara.isAllFlag()){
				mycalpara.setPaintdate(calculate.cgls(mycalpara.getCirs(),
						mycalpara.getB(), 40));
			 }
//				 mycalpara.changeCurrentIndex(true);	
					showimage.render();	
			 
			synchronized (this) {
					try {
						// don't make loop too tight, or not enough time
						// to process window messages properly
						//sleep(500);
						wait();
					} catch (InterruptedException interruptedexception) {
						// we just quit on interrupt, so nothing required here
					}
					
			}
	
				}
		 }
 
}
