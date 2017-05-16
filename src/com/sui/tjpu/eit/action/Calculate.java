package com.sui.tjpu.eit.action;

import Jama.Matrix;

public class Calculate {

	Matrix x = new Matrix(812, 1);
	Matrix X;
	Matrix d;
	Matrix DT;
	Matrix AT;
	Matrix r;
	Matrix normr2;
	double[][] result;
	double[] temp = new double[812];
	Matrix Ad;
	Matrix alpha;
	Matrix s;
	Matrix nor_new;
	Matrix beta;
	Matrix alpha_temp, alpha_temp1, beta_temp;
	double al_temp;

	public Calculate(Matrix A) {
		AT = A.transpose();
	}

	/**
  *
  */
	public double[][] cgls(Matrix A, Matrix b, int k) {

		// X = new Matrix(812, k);

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
		return result;

	}

}