package model;

import javax.swing.table.AbstractTableModel;


public class PityTimerArchivesTableModel  extends AbstractTableModel{
	private static final long serialVersionUID = -1950144874661881063L;
	
	
	
	private int[][] timerArchives;
	
	
	public PityTimerArchivesTableModel(int[][] timerArchives) {
		this.timerArchives = timerArchives;
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
		
		return maxRows;
	}
	
	public String getColumnName(int col) {
		return PityTimerModel.rarete[col];
	}
	
	public Class<?> getColumnClass(int col) {
		return String.class;
	}
	
	
	public Object getValueAt(int row, int col) {
		if (row >= timerArchives[col].length)
			return "";
		
		return timerArchives[col][row];
	}
	
	public void setValueAt(Object value, int row, int col) {
		
	}
	
	
	public boolean isCellEditable(int row, int col) {
		return false;
	}
	
	
	
	public void updateData(int[][] timerArchives) {
		this.timerArchives = timerArchives;
		fireTableDataChanged();
	}
	
	
}
