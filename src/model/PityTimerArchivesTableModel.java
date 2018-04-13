package model;

import java.text.NumberFormat;

import javax.swing.table.AbstractTableModel;


public class PityTimerArchivesTableModel  extends AbstractTableModel{
	private static final long serialVersionUID = -1950144874661881063L;
	
	
	
	private int[][] timerArchives;
	private double[] timerMoyenne;
	
	
	public PityTimerArchivesTableModel(int[][] timerArchives) {
		this.timerArchives = timerArchives;
		computeTimerMoyenne();
	}
	
	
	public int getColumnCount() {
		return timerArchives.length;
	}
	
	public int getRowCount() {
		int maxRows = 0;
		
		for (int[] data : timerArchives) {
			if (maxRows < data.length)
				maxRows = data.length;
		}
		
		return maxRows + 1;
	}
	
	public String getColumnName(int col) {
		return PityTimerModel.rarete[col];
	}
	
	public Class<?> getColumnClass(int col) {
		return String.class;
	}
	
	public Object getValueAt(int row, int col) {
		if (row == 0) {
			return doubleToString(timerMoyenne[col]);
		}
		
		row--;
		if (row >= timerArchives[col].length) {
			return "";
		}
		
		return timerArchives[col][row];
	}
	
	public void setValueAt(Object value, int row, int col) {
		
	}
	
	public boolean isCellEditable(int row, int col) {
		return false;
	}
	
	@Override
	public void fireTableDataChanged() {
		computeTimerMoyenne();
		super.fireTableDataChanged();
	}
	
	public void updateData(int[][] timerArchives) {
		this.timerArchives = timerArchives;
		fireTableDataChanged();
	}
	
	private void computeTimerMoyenne()  {
		timerMoyenne = new double[timerArchives.length];
		
		for (int i = 0; i < timerArchives.length; i++)  {
			if (timerArchives[i].length == 0) {
				timerMoyenne[i] = 0.0;
				continue;
			}
			
			int somme = 0;
			for (int j = 0; j < timerArchives[i].length; j++)  {
				somme += timerArchives[i][j];
			}
			
			timerMoyenne[i] = (double)somme / (double)timerArchives[i].length;
		}
	}
	
	private String doubleToString(double value) {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(0);
		nf.setMaximumFractionDigits(2);
		
		return nf.format(value);
	}
}
