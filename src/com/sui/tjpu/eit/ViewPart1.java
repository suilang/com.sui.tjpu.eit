package com.sui.tjpu.eit;

import org.eclipse.swt.opengl.GLCanvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import com.jogamp.opengl.GL2;

import com.jogamp.opengl.GLProfile;

import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLDrawableFactory;
import com.jogamp.opengl.GL;

import com.jogamp.opengl.GL2ES1;
import com.jogamp.opengl.GL2GL3;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.opengl.GLData;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.PlatformUI;

public class ViewPart1 extends ViewPart {

	private Composite composite;
	 
    /** Widget that displays OpenGL content. */
    private GLCanvas glcanvas;
 
    /** Used to get OpenGL object that we need to access OpenGL functions. */
    private GLContext glcontext;
 
    /** Master rotation angle of the torus, in degrees. */
    private int rot = 0;
	public ViewPart1() {
		// TODO Auto-generated constructor stub
		
	}
	
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
		 GLProfile glprofile = GLProfile.get(GLProfile.GL2);
		 
	        composite = new Composite( parent, SWT.NONE );
	        composite.setLayout( new FillLayout() );
	 
	        GLData gldata = new GLData();
	        gldata.doubleBuffer = true;
	        glcanvas = new GLCanvas( composite, SWT.NO_BACKGROUND, gldata );
	        glcanvas.setCurrent();
	        glcontext = GLDrawableFactory.getFactory( glprofile ).createExternalGLContext();
	 
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
	 
	        (new Thread() {
	            public void run() {
	                while( (glcanvas != null) && !glcanvas.isDisposed() ) {
	                    render();
	                    try {
	                        // don't make loop too tight, or not enough time
	                        // to process window messages properly
	                        sleep( 1 );
	                    } catch( InterruptedException interruptedexception ) {
	                        // we just quit on interrupt, so nothing required here
	                    }
	                }
	            }
	        }).start();
	}
	protected void render() {
        PlatformUI.getWorkbench().getDisplay().syncExec( new Runnable() {
            public void run() {
                if( (glcanvas != null) && !glcanvas.isDisposed()) {
                    glcanvas.setCurrent();
                    glcontext.makeCurrent();
                    GL2 gl = glcontext.getGL().getGL2();
                    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
                    gl.glClearColor(.3f, .5f, .8f, 1.0f);
                    gl.glLoadIdentity();
                    gl.glTranslatef(0.0f, 0.0f, -10.0f);
                    //gl.glRotatef(0.15f * rot, 2.0f * rot, 10.0f * rot, 1.0f);
                    gl.glRotatef(0.3f * rot, 3.0f * rot, 1.0f * rot, 1.0f);
                    rot++;
                    gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2GL3.GL_LINE);
                    gl.glColor3f(0.9f, 0.9f, 0.9f);
                    drawTorus(gl, 1, 1.9f + ((float) Math.sin((0.004f * rot))), 15, 15);
                    glcanvas.swapBuffers();
                    glcontext.release();
                }
            }
        });
    }
	protected void drawTorus(GL2 gl, float r, float R, int nsides, int rings) {
        float ringDelta = 2.0f * (float) Math.PI / rings;
        float sideDelta = 2.0f * (float) Math.PI / nsides;
        float theta = 0.0f, cosTheta = 1.0f, sinTheta = 0.0f;
        for (int i = rings - 1; i >= 0; i--) {
            float theta1 = theta + ringDelta;
            float cosTheta1 = (float) Math.cos(theta1);
            float sinTheta1 = (float) Math.sin(theta1);
            gl.glBegin(GL2.GL_QUAD_STRIP);
            float phi = 0.0f;
            for (int j = nsides; j >= 0; j--) {
                phi += sideDelta;
                float cosPhi = (float) Math.cos(phi);
                float sinPhi = (float) Math.sin(phi);
                float dist = R + r * cosPhi;
                gl.glNormal3f(cosTheta1 * cosPhi, -sinTheta1 * cosPhi, sinPhi);
                gl.glVertex3f(cosTheta1 * dist, -sinTheta1 * dist, r * sinPhi);
                gl.glNormal3f(cosTheta * cosPhi, -sinTheta * cosPhi, sinPhi);
                gl.glVertex3f(cosTheta * dist, -sinTheta * dist, r * sinPhi);
            }
            gl.glEnd();
            theta = theta1;
            cosTheta = cosTheta1;
            sinTheta = sinTheta1;
        }
    }
	public void setFocus() {
		// TODO Auto-generated method stub

	}
	
    public void dispose() {
        glcanvas.dispose();
        super.dispose();
    }
}
