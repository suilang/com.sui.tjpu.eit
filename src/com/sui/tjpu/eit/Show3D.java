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
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLDrawableFactory;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;

public class Show3D extends ViewPart {

	public static final String ID = "com.sui.tjpu.eit.showimage";

	private Control control;
	private MyCalculateParameter mycalpara;
	
	private Composite composite;

	/** Widget that displays OpenGL content. */
	private GLCanvas glcanvas;

	/** Used to get OpenGL object that we need to access OpenGL functions. */
	private GLContext glcontext;

	 double[][] paintdate;
	 float[] rgb;
	 
	public Show3D() {
		// TODO Auto-generated constructor stub
		control = (Control) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage()
				.findView("com.sui.tjpu.eit.control");
		
		mycalpara = control.getMycalpara();
		//calculate = new Calculate();
		
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
               // glu.gluOrtho2D(0.0, 500.0, 0.0, 300.0); 
 //               glu.gluPerspective(45.0f, fAspect, 0.7f, 400.0f);
                gl.glOrtho(-50, 50, -50, 50, -50, 50);
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
//	        gl.glHint(GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
	        gl.glClearDepth(1.0);
//	        gl.glLineWidth(2);
	        gl.glEnable(GL.GL_DEPTH_TEST);
//	        gl.glDepthMask(GL.GL_FALSE);
	        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA); //ָ����Ϻ���  
	        gl.glEnable(GL.GL_BLEND);     //���û��״̬  
	        glcontext.release();
	        
		(new Thread() {
            public void run() {
                while( (glcanvas != null) &&!glcanvas.isDisposed() ) {
                    render();
                    try {
                        // don't make loop too tight, or not enough time
                        // to process window messages properly
                        sleep( 1000 );
                    } catch( InterruptedException interruptedexception ) {
                        // we just quit on interrupt, so nothing required here
                    }
                }
            }
        }).start();
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
					
					 gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
	                    gl.glClearColor(.3f, .5f, .8f, 1.0f);
	                    gl.glLoadIdentity();
	                    gl.glTranslatef(0.0f, 0.0f, -10.0f);
	                    gl.glColor3f(0.9f, 0.9f, 0.9f);
	                    gl.glRotatef(0f,0.5f,1.0f,0.5f);    
	                	float pointsize=isize/30.0f;
	                    gl.glPointSize(pointsize); // ���õ�Ĵ�С  
	                   
//	                    gl.glTranslatef (-5.0f,-4.0f,-13.0f);  
	                  
	                    gl.glColor3f(1.0f, 1.0f, 1.0f);  
	                    drawTorus(gl, 15);
	                   
	                    
	                    glcanvas.swapBuffers();
	                    glcontext.release();
				}
			}
		});
	}

	protected void drawTorus(GL2 gl,float pointsize) {
		
		
		 int j=0;
			gl.glBegin(GL2.GL_POINTS);
			paintdate=mycalpara.getPaintdate();
			 gl.glPushMatrix();
			for(j=0;j<50;j++){
			   
			for (int i = 0; i < 812; i++) {
				 rgb=RGBS(paintdate[i][0]*mycalpara.getAmplify()*-1);
				 //System.out.println(paintdate[i][0]);
				// gl.glColor3f(rgb[0], rgb[1], rgb[2]);
				 gl.glColor4f(rgb[0], rgb[1], rgb[2],0.5f);
				// System.out.println(rgb[0]+"+"+rgb[1]+"+"+rgb[2]);
				// gl.glVertex2f((float)MyCalculateParameter.paint_X[i]*48*pointsize,(float)MyCalculateParameter.paint_Y[i]*48*pointsize);
				 //gl.glVertex2f((float)MyCalculateParameter.paint_X[i]*48,(float)MyCalculateParameter.paint_Y[i]*48);
				 gl.glVertex3f((float)MyCalculateParameter.paint_X[i]*20,(float)MyCalculateParameter.paint_Y[i]*20, (float)j);// a0��  
				// //������glBegin(GL.GL_POINTS)��ʼ��glEnd()����
				
			}
			
			 }			

			 
			 
			 gl.glPopMatrix();  
			gl.glEnd();

			
	}
	
	/*
	 * ���������ֵ��ֵ��ɫֵ
	 */
	public float[] RGBS(double  color){
		float[] color_temp=new float[3];
		if(color>200){
			color_temp[0]=(float)135/255;
			color_temp[1]=0;
			color_temp[2]=0;
		}else{
			color_temp[0]=0f;
			color_temp[1]=0f;
			color_temp[2]=(float)135/255;
		}
//		if(color>0)
//		{
//			if(color<127)
//			{
//				color_temp[0]=(float) (127+color)/255;
//				color_temp[1]=1f;
//				color_temp[2]=(float)(127-color)/255;
//			}
//			else if(color<382)
//			{
//				color_temp[0]=1f;
//				color_temp[1]=(float)(382-color)/255;
//				color_temp[2]=0f;
//			}
//			else if(color<500)
//			{
//				color_temp[0]=(float) (637-color)/255;
//				color_temp[1]=0f;
//				color_temp[2]=0f;
//				
//			}
//			else
//			{
//				color_temp[0]=(float)135/255;
//				color_temp[1]=0;
//				color_temp[2]=0;
//			}
//		}
//		else if(color==0)
//		{
//			color_temp[0]=(float)127/255;
//			color_temp[1]=color_temp[0];
//			color_temp[2]=color_temp[0];
//		}
//		else {
//			if(color>-127)
//			{
//				color_temp[0]=(float) (127+color)/255;
//				color_temp[1]=1f;
//				color_temp[2]=(float)(127-color)/255;
//			}
//			else if(color>-382)
//			{
//				color_temp[0]=0f;
//				color_temp[1]=(float)(382-color)/255;
//				color_temp[2]=1f;
//			}
//			else if(color>-500)
//			{
//				color_temp[0]=0;
//				color_temp[1]=0f;
//				color_temp[2]=(float) (637-color)/255;
//			}
//			else
//			{
//				color_temp[0]=0f;
//				color_temp[1]=0f;
//				color_temp[2]=(float)135/255;
//			}
//		}
//		if(color_temp[0]==0&&color_temp[1]==0&&color_temp[2]==0)
//		{
//			System.out.println(color);
//		}
		return color_temp;
	}
	
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
