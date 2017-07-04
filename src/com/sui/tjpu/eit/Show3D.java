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
		// calculate = new Calculate();

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
				// glu.gluPerspective(45.0f, fAspect, 0.9f, 400.0f);
				gl.glOrtho(-50, 50, -50, 50, -50, 50);
				//gl.glOrtho(-100, 100, -100, 100, -50, 50);
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
		// gl.glHint(GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
		gl.glClearDepth(1.0);
		gl.glLineWidth(2);
		gl.glEnable(GL.GL_DEPTH_TEST);
		// gl.glDepthMask(GL.GL_FALSE);
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA); // 指定混合函数
		gl.glEnable(GL.GL_BLEND); // 启用混合状态
		SetupLights(gl);
		glcontext.release();

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

	protected void render() {
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
			public void run() {
				if ((glcanvas != null) && !glcanvas.isDisposed()) {
					glcanvas.setCurrent();
					glcontext.makeCurrent();
					Rectangle rect = glcanvas.getClientArea();
					int iWidth = rect.width;
					int iHeight = rect.height;
					int isize = Math.min(iWidth, iHeight);

					GL2 gl = glcontext.getGL().getGL2();

					gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
					gl.glClearColor(.3f, .5f, .8f, 1.0f);
					gl.glLoadIdentity();

					float pointsize = isize / 20.0f;
					gl.glPointSize(pointsize); // 设置点的大小

					// gl.glTranslatef (-5.0f,-4.0f,-13.0f);

					// gl.glColor3f(1.0f, 1.0f, 1.0f);
					drawTorus(gl, 15);

					glcanvas.swapBuffers();
					glcontext.release();
				}
			}
		});
	}

	protected void drawTorus(GL2 gl, float pointsize) {

		gl.glColor4f(0, 0, 10, 0.3f);
		gl.glPointSize(2);
		int j = 0;
		paintdate = mycalpara.getPaintdate();
		gl.glPushMatrix();
		gl.glTranslatef(-25.0f, 0.0f, 0.0f);

		gl.glRotatef(-45f, 0.0f, -1.0f, 0.0f);
		gl.glRotatef(-30f, -1.0f, 0.0f, 0.0f);

		gl.glPointSize(10f); // 设置点的大小
		paint2d(gl);

		paintpark(gl);

		// gl.glEnd();
		gl.glPopMatrix();

	}

	public void paint2d(GL2 gl) {
		gl.glBegin(GL.GL_POINTS);
for(int j=0;j<50;j++){
		for (int i = 0; i < 811; i++) {
			if(((paintdate[i][0]*mycalpara.getAmplify()*-1>200)&&(paintdate[i+1][0]*mycalpara.getAmplify()*-1<200))||((paintdate[i][0]*mycalpara.getAmplify()*-1<200)&&(paintdate[i+1][0]*mycalpara.getAmplify()*-1>200))){
			rgb = RGBS(paintdate[i][0] * mycalpara.getAmplify() * -1);

			// System.out.println(paintdate[i][0] * mycalpara.getAmplify() * -1);
			// gl.glColor3f(rgb[0], rgb[1], rgb[2]);
			gl.glColor4f(rgb[0], rgb[1], rgb[2], 0.5f);
			// gl.glVertex2f((float)MyCalculateParameter.paint_X[i]*48,(float)MyCalculateParameter.paint_Y[i]*48);
			gl.glVertex3f((float) MyCalculateParameter.paint_X[i] * 24,
					(float) MyCalculateParameter.paint_Y[i] * 24, (float) 50-j);// a0点
			// square(gl,(float)MyCalculateParameter.paint_X[i]*20,(float)MyCalculateParameter.paint_Y[i]*20,49);
			}
		}
	}
gl.glEnd();
	}

	/*
	 * 依据运算的值赋值颜色值
	 */
 
	public float[] RGBS(double color) {
		float[] color_temp = new float[3];
//		if (color > 200) {
//			color_temp[0] = (float) 135 / 255;
//			color_temp[1] = (float)135 / 255;
//			color_temp[2] = (float)135 / 255;
//		} else {
//			color_temp[0] = 0f;
//			color_temp[1] = 135/255f;
//			color_temp[2] = (float) 135 / 255;
//		}
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

	public void paintpark(GL2 gl) {
		gl.glColor4f(0, 0, 0, 0.5f);
		gl.glBegin(GL.GL_TRIANGLE_FAN);// 扇形连续填充三角形串
		gl.glVertex3f(0, 0, 0.0f);
		for (int i = 0; i <= 390; i += 10) {
			float p = (float) (i * 3.14 / 180);
			gl.glVertex3f((float) Math.sin(p) * 23.25f, (float) Math.cos(p) * 23.25f,
					0.0f);// 园轨迹
		}
		gl.glEnd();
		gl.glBegin(GL.GL_LINE_LOOP);// 扇形连续填充三角形串

		for (int i = 0; i <= 390; i += 10) {
			float p = (float) (i * 3.14 / 180);
			gl.glVertex3f((float) Math.sin(p) * 23.25f, (float) Math.cos(p) * 23.25f,
					50.0f);// 园轨迹
		}
		gl.glEnd();

		gl.glBegin(GL.GL_LINES);// 扇形连续填充三角形串
		gl.glVertex3f((float) 0, 23, 0.0f);// 园轨迹
		gl.glVertex3f((float) 0, 23, 50.0f);// 园轨迹
		gl.glEnd();
		gl.glBegin(GL.GL_LINES);// 扇形连续填充三角形串
		gl.glVertex3f((float) 0, -23, 0.0f);// 园轨迹
		gl.glVertex3f((float) 0, -23, 50.0f);// 园轨迹
		gl.glEnd();
		gl.glBegin(GL.GL_LINES);// 扇形连续填充三角形串
		gl.glVertex3f((float) 23, 0, 0.0f);// 园轨迹
		gl.glVertex3f((float) 23, 0, 50.0f);// 园轨迹
		gl.glEnd();
		gl.glBegin(GL.GL_LINES);// 扇形连续填充三角形串
		gl.glVertex3f((float) -23, 0, 0.0f);// 园轨迹
		gl.glVertex3f((float) -23, 0, 50.0f);// 园轨迹
		gl.glEnd();

	}

	public void square(GL2 gl, float position_x, float position_y,
			float position_z) {

		gl.glBegin(GL2.GL_POLYGON);// 填充凸多边形
		gl.glVertex3f(position_x, position_y, position_z);// a点
		gl.glVertex3f(position_x + 3, position_y, position_z);// b点
		gl.glVertex3f(position_x + 3, position_y + 3, position_z);// c点
		gl.glVertex3f(position_x, position_y + 3, position_z);// d点
		gl.glEnd();
	}

	void SetupLights(GL2 gl)  
	{  
	    float ambientLight[]  = {0.2f,  0.2f,  0.2f,  1.0f};//环境光  
	    float diffuseLight[]  = {0.9f,  0.9f,  0.9f,  1.0f};//漫反射  
	    float specularLight[] = {1.0f,  1.0f,  1.0f,  1.0f};//镜面光  
	    float lightPos[]      = {0.0f, 80.0f, 60.0f, 0.0f};//光源位置  
	 
	    gl.glEnable(GL2.GL_LIGHTING);                              //启用光照  
	    gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambientLight,0);     //设置环境光源  
	    gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuseLight,0);     //设置漫反射光源  
	    gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, specularLight,0);   //设置镜面光源  
	    gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPos,0);        //设置灯光位置  
	    gl.glEnable(GL2.GL_LIGHT0);                                //打开第一个灯光  
	  
	    gl.glEnable(GL2.GL_COLOR_MATERIAL);                        //启用材质的颜色跟踪  
	    gl.glColorMaterial(GL2.GL_BACK, GL2.GL_AMBIENT_AND_DIFFUSE);  //指定材料着色的面  
	    gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, specularLight,0); //指定材料对镜面光的反应  
	    gl.glMateriali(GL2.GL_FRONT, GL2.GL_SHININESS, 100);           //指定反射系数  
	}  
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
