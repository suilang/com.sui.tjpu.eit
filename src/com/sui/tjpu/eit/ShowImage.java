package com.sui.tjpu.eit;

import org.eclipse.swt.SWT;
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
	private Calculate calculate;
	private Composite composite;

	/** Widget that displays OpenGL content. */
	private GLCanvas glcanvas;

	/** Used to get OpenGL object that we need to access OpenGL functions. */
	private GLContext glcontext;

	float[] rgb;
	int rot = 0;
    double[][] paintdate;
	public ShowImage() {
		// TODO Auto-generated constructor stub
		control = (Control) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage()
				.findView("com.sui.tjpu.eit.control");
		control.setShowImage(this);
		//calculate = new Calculate();
	}

	public void createPartControl(Composite parent) {
		
		mycalpara = control.getMycalpara();
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
				glu.gluPerspective(45.0f, fAspect, 0.5f, 400.0f);
				gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
				gl.glLoadIdentity();
				glcontext.release();
			}
		});

		glcontext.makeCurrent();
		GL2 gl = glcontext.getGL().getGL2();
		gl.setSwapInterval(1);
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glHint(GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
		gl.glClearDepth(1.0);
		gl.glLineWidth(2);
		gl.glEnable(GL.GL_DEPTH_TEST);
		glcontext.release();

		
	}

	protected void render() {
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
			public void run() {
				if ((glcanvas != null) && !glcanvas.isDisposed()) {
					glcanvas.setCurrent();
					glcontext.makeCurrent();
					GL2 gl = glcontext.getGL().getGL2();
					gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
					gl.glClearColor(.3f, .5f, .8f, 1.0f);
					gl.glLoadIdentity();
					gl.glTranslatef(0.0f, 0.0f, -10.0f);
					// gl.glRotatef(0.15f * rot, 2.0f * rot, 10.0f * rot, 1.0f);
					// gl.glRotatef(0.3f * rot, 3.0f * rot, 1.0f * rot, 1.0f);
					gl.glViewport(0, 0, 250, 250);
					GLU glu=new GLU(); ;
					glu.gluOrtho2D(-120.0, 120.0, -125.0, 125.0);        //ʹ����ϵͳ������GL�� 
					 gl.glMatrixMode(GL2.GL_MODELVIEW);
					gl.glColor3f(0.9f, 0.9f, 0.9f);
					  gl.glPointSize(6.0f);      
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
			 rgb=RGBS(paintdate[i][0]*control.getAmplify()*-1);
			 //System.out.println(paintdate[i][0]);
			 gl.glColor3f(rgb[0], rgb[1], rgb[2]);
			// System.out.println(rgb[0]+"+"+rgb[1]+"+"+rgb[2]);
			 gl.glVertex2f((float)MyCalculateParameter.paint_X[i]*148,(float)MyCalculateParameter.paint_Y[i]*148);
			// //������glBegin(GL.GL_POINTS)��ʼ��glEnd()����
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

	public void runpaint() {
		System.err.println("sss");
		// TODO Auto-generated method stub
		(new Thread() {
			public void run() {
				while ((glcanvas != null) && !glcanvas.isDisposed()) {
					render();
					try {
						// don't make loop too tight, or not enough time
						// to process window messages properly
						sleep(1000);
					} catch (InterruptedException interruptedexception) {
						// we just quit on interrupt, so nothing required here
					}
				}
			}
		}).start();
	}

	/*
	 * ���������ֵ��ֵ��ɫֵ
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

