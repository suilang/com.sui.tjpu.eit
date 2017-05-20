package com.sui.tjpu.eit.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import Jama.Matrix;

public class OpenFile {

	String select;
	File filepath;

	public String getSelect() {
		return select;
	}

	public void LoadFile(Shell shell) {

		FileDialog fileDialog = new FileDialog(shell);
		fileDialog.setText("Select File");
		select = fileDialog.open();
	}

	public void LoadFile(Shell shell, String type) {

		FileDialog fileDialog = new FileDialog(shell);
		fileDialog.setText("Select File");
		if (type.equals("txt")) {
			fileDialog.setFilterExtensions(new String[] { "*.txt" });
			fileDialog.setFilterNames(new String[] { "Textfiles(*.txt)" });
		}
		if (type.equals("sl")) {
			fileDialog.setFilterExtensions(new String[] { "*.sl" });
			fileDialog.setFilterNames(new String[] { "Textfiles(*.sl)" });
		}
		select = fileDialog.open();
		;
	}

	// public void LoadFile(String path) {
	// Scanner in;
	// filepath = new File("e:/S.txt");
	//
	// }

	/**
	 * ���������Ⱦ���
	 */
	public Matrix LoadCirs(String path) {
		Scanner in = null;
		Matrix result = null;
		double[][] A = new double[208][812];
		String str;// ÿһ�еĻ���
		int line = 0;// ����λ������
		int row = 0;
		File file = new File(path);

		if (file.isFile() && file.exists()) { // �ж��ļ��Ƿ����
			try {
				in = new Scanner(file);

				while (in.hasNextLine()) {
					str = in.nextLine();
					splitt(A, str, line, row);
				}
				result = new Matrix(A);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				// JOptionPane.showMessageDialog(null,
				// "�����Ⱦ����ȡʧ��!", "������Ϣ", JOptionPane.INFORMATION_MESSAGE);
				Shell parentShell = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell();
				MessageDialog.openWarning(parentShell, "Warning",
						"�����Ⱦ����ȡʧ��!�����ļ��Ƿ����");
			} finally {
				in.close();
			}
			return result;
		} else {
			Shell parentShell = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getShell();
			MessageDialog.openWarning(parentShell, "Warning", "�����ļ��Ƿ����");

		}

		return null;

	}

	public void splitt(double[][] A, String str, int line, int row) {
		if (row < 812) {
			A[line][row] = Double.parseDouble(str);
			line++;
			if (line == 208) {
				line = 0;
				row++;
			}
		}
	}

	/**
	 * ���ؿճ�����
	 */
	public Matrix Loadkong(String path) {
		Scanner in = null;
		Matrix result = null;
		int index = 0;
		File file = new File(path);
		double[][] B = new double[208][1];
		if (file.isFile() && file.exists()) { // �ж��ļ��Ƿ����
			try {

				in = new Scanner(file);
				String str;
				while (in.hasNextLine()) {
					str = in.nextLine();
					B[index++][0] = Double.parseDouble(str);

				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();

				// JOptionPane.showMessageDialog(null,
				// "�ճ������ȡʧ��!", "������Ϣ", JOptionPane.INFORMATION_MESSAGE);
				Shell parentShell = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell();
				MessageDialog.openWarning(parentShell, "Warning",
						"�ճ������ȡʧ��!�����ļ��Ƿ����");
			} finally {
				result = new Matrix(B);
				in.close();
			}

		} else {
			Shell parentShell = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getShell();
			MessageDialog.openWarning(parentShell, "Warning", "�����ļ��Ƿ����");
		}
		return result;

	}

	public ArrayList Loadwu(String path) {
		ArrayList wubuff = new ArrayList();
		double[][] C = new double[208][1];
		int index = 0;
		
		Scanner in = null;
		File file = new File(path);
		if (file.isFile() && file.exists()) { // �ж��ļ��Ƿ����
			try {

				in = new Scanner(file);
				String str;
				while (in.hasNextLine()) {
					str = in.nextLine();
					C[index++][0] = Double.parseDouble(str);
					if (index == 208) {
						wubuff.add(C);
						C = new double[208][1];
						index = 0;
					}

				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				Shell parentShell = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell();
				MessageDialog.openWarning(parentShell, "Warning",
						"�ﳡ�����ȡʧ��!�����ļ��Ƿ����");
			} finally {
				
				in.close();
			}
		} else {
			Shell parentShell = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getShell();
			MessageDialog.openWarning(parentShell, "Warning", "�����ļ��Ƿ����");
		}
		return wubuff;
	}
}