package com.sui.tjpu.eit;

import java.util.ArrayList;
import java.util.Observable;

import Jama.Matrix;

public class MyCalculateParameter extends Observable{
	
	private Matrix Cirs;
	private Matrix kong;
	private ArrayList wu;
	
	
	
	public Matrix getCirs() {
		return Cirs;
	}
	public void setCirs(Matrix cirs) {
		Cirs = cirs;
	}
	public Matrix getKong() {
		return kong;
	}
	public void setKong(Matrix kong) {
		this.kong = kong;
	}
	
	public ArrayList getWu() {
		return wu;
	}
	public void setWu(ArrayList wu) {
		this.wu = wu;
	}
	
	
}