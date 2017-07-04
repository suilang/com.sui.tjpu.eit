package com.sui.tjpu.eit.action;

import com.sui.tjpu.eit.MyCalculateParameter;

import Jama.Matrix;

public class Calculate {

	private Matrix x ;
	private Matrix X;
	private Matrix d;
	private Matrix DT;
	private Matrix AT;
	private Matrix r;
	private Matrix normr2;
	private double[][] result;
	private Matrix Ad;
	private Matrix alpha;
	private Matrix s;
	private Matrix nor_new;
	private Matrix beta;
	Matrix alpha_temp, alpha_temp1, beta_temp;
	private double al_temp;
    private MyCalculateParameter mycalpara;
    
	public Calculate(){
		
	}
	
	public Calculate(MyCalculateParameter mycalpara){
		this.mycalpara=mycalpara;
		AT = mycalpara.getCirs().transpose();
	}
	public Calculate(Matrix A) {
		AT = A.transpose();
	}

	 
/**
  *共轭梯度法
  *传入值：
  *    矩阵A：灵敏度矩阵
  *    矩阵b：测量电压值
  *    k：迭代次数
  *返回值：
  *    result：812*1的二维数组
  *
  *
  */
	public double[][] cgls(Matrix A, Matrix b, int k) {
		AT = A.transpose();
		// X = new Matrix(812, k);
        x=new Matrix(zeros());
		d = AT.times(b);
		r = b.copy();
		DT = d.transpose();
		normr2 = DT.times(d);
		for (int i = 0; i < k; i++) {
			Ad = A.times(d);
			alpha = normr2.arrayRightDivide(Ad.transpose().times(Ad));
			al_temp = alpha.get(0, 0);
			alpha_temp = new Matrix(812, 1, al_temp);

			x = x.plus(alpha_temp.arrayTimes(d));
			if (i == k - 1)
				continue;
			alpha_temp1 = new Matrix(208, 1, al_temp);
			r = r.minus(alpha_temp1.arrayTimes(Ad));
			alpha_temp = null;
			alpha_temp1 = null;
			s = AT.times(r);
			nor_new = s.transpose().times(s);
			beta = nor_new.arrayRightDivide(normr2);
			normr2 = nor_new;
			beta_temp = new Matrix(812, 1, beta.get(0, 0));
			d = s.plus(beta_temp.arrayTimes(d));
		}

		result = x.getArrayCopy();
		close();
		
		
		return result;

	}

	void close(){
		
		 
	  
	        d=null;
	    
	        r=null;
	        normr2=null;
	        Ad=null;
	        alpha=null;
	        s=null;
	        nor_new=null;
	        beta=null;
	     
	        alpha_temp=null;
	        alpha_temp1=null;
	        beta_temp=null;
	      
	}
	
	public double[][] zeros(){
		double[][] result=new double[812][1]; 
		for(int i=0;i<812;i++){
			result[i][0]=0;
		}
		return result;
		
	}
}