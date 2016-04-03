package model;

import javax.swing.table.AbstractTableModel;


public class StatsTableModel extends AbstractTableModel {
	private static final long serialVersionUID = -9065509160887227194L;
	
	
	private String[] titres;
	private String[][] data;
	
	
	public StatsTableModel(String[] titres, String[][] data) {
		this.titres = titres;
		this.data = data;
	}
	
	public int getColumnCount() {
		return titres.length;
	}

	public int getRowCount() {
		return data.length;
	}
	
	public String getColumnName(int col) {
		return titres[col];
	}
	
	public Class<?> getColumnClass(int col) {
		return String.class;
	}
	

	public Object getValueAt(int row, int col) {
		return data[row][col];
	}
	
	public void setValueAt(Object value, int row, int col) {
		
	}
	
	
	public boolean isCellEditable(int row, int col) {
		return false;
	}
	
	
	public void updateTitres(String[] titres) {
		this.titres = titres;
		fireTableStructureChanged();
	}
	
	public void updateData(String[][] data) {
		this.data = data;
		fireTableDataChanged();
	}
	
}






