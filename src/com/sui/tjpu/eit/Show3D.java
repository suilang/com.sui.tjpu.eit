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
                glu.gluOrtho2D(0.0, 500.0, 0.0, 300.0); 
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
//	        gl.glLineWidth(2);
	        gl.glEnable(GL.GL_DEPTH_TEST);
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
//	                    gl.glTranslatef(0.0f, 0.0f, -10.0f);
	                    gl.glColor3f(0.9f, 0.9f, 0.9f);
	                  	                    
	                    gl.glPointSize(5.0f); // 设置点的大小  
	                    drawTorus(gl, 15);
	                    glcanvas.swapBuffers();
	                    glcontext.release();
				}
			}
		});
	}

	protected void drawTorus(GL2 gl,float pointsize) {
		float red = (float) (Math.random() * 1.0f); // 随机红  
		  
        float green = (float) (Math.random() * 1.0f); // 随机绿  
  
        float blue = (float) (Math.random() * 1.0f); // 随机蓝  
        
        for (int i = 0; i < 50; i++) { // 画点  
        	  
            red -= .09f; // 红色值递减  
  
            green -= .12f; // 绿色值递减  
  
            blue -= .15f; // 蓝色值递减  
  
            if (red < 0.15) {  
                red = 1.0f;  
            }  
  
            if (green < 0.15) {  
                green = 1.0f;  
            }  
  
            if (blue < 0.15) {  
                blue = 1.0f;  
            }  
  
            gl.glColor3f(red, green, blue); // 设置GL的画图颜色，也就是画刷的颜色  
  
            gl.glBegin(GL.GL_POINTS);  
  
            gl.glVertex2i(i * 10, 150); // 画点由glBegin(GL.GL_POINTS)开始，glEnd()结束  
  
            gl.glEnd();  
  
        } 

	}
	
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
