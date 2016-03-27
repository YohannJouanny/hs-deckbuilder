package model;

import javax.swing.table.AbstractTableModel;


public class PityTimerTableModel extends AbstractTableModel{
	private static final long serialVersionUID = -7411554303956296047L;
	
	
	private enum TypeColonne {
		Rarete,
		ValMax,
		ValActuelle,
		Action;
	}
	
	
	private TypeColonne[] types;
	private int[] timerCourants;
	
	
	public PityTimerTableModel(int[] timerCourants) {
		TypeColonne types[] = {TypeColonne.Rarete, TypeColonne.ValMax, TypeColonne.ValActuelle, TypeColonne.Action};
		this.types = types;
		this.timerCourants = timerCourants;
	}
	
	
	
	public int getColumnCount() {
		return types.length;
	}
	
	public int getRowCount() {
		return timerCourants.length;
	}
	
	public String getColumnName(int col) {
		switch (types[col]) {
			case Rarete:
				return "Rareté";
			case ValMax:
				return "Valeur Max";
			case ValActuelle:
				return "Valeur actuelle";
			case Action:
				return "Action";
			default:
				return null;
		}
	}
	
	public Class<?> getColumnClass(int col) {
		switch (types[col]) {
			case Rarete:
				return String.class;
			case ValMax:
				return String.class;
			case ValActuelle:
				return String.class;
			case Action:
				return int.class;
			default:
				return null;
		}
	}
	
	
	public Object getValueAt(int row, int col) {
		switch (types[col]) {
			case Rarete:
				return PityTimerModel.rarete[row];
			case ValMax:
				return PityTimerModel.timerMax[row];
			case ValActuelle:
				return timerCourants[row];
			case Action:
				return row;
			default:
				return null;
		}
	}
	
	public void setValueAt(Object value, int row, int col) {
		
	}
	
	
	public boolean isCellEditable(int row, int col) {
		if (types[col] == TypeColonne.Action)
			return true;
		
		return false;
	}
	
	
	
	public void updateData(int[] timerCourants) {
		this.timerCourants = timerCourants;
		fireTableDataChanged();
	}
}




