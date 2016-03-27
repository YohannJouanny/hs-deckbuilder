package model;

import javax.swing.table.AbstractTableModel;

import donnees.Carte;
import donnees.Classe;
import donnees.Extension;
import donnees.Rarete;


public class ListeCartesTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 4673222940684150220L;


	public enum TypeColonne {
		Nom,
		CoutMana,
		Classe,
		Rarete,
		Extension,
		Action;
	}
	
	
	private String[] titres;
	private TypeColonne[] types;
	private ListeCartes listeCartes;
	
	
	public ListeCartesTableModel(String[] titres, TypeColonne[] types, ListeCartes liste) {
		this.titres = titres;
		this.types = types;
		this.listeCartes = liste;
	}
	
	
	
	public int getColumnCount() {
		return titres.length;
	}

	public int getRowCount() {
		return listeCartes.size();
	}
	
	public String getColumnName(int col) {
		return titres[col];
	}
	
	public Class<?> getColumnClass(int col) {
		switch (types[col]) {
			case Nom:
				return String.class;
			case CoutMana:
				return int.class;
			case Classe:
				return Classe.class;
			case Rarete:
				return Rarete.class;
			case Extension:
				return Extension.class;
			case Action:
				return Carte.class;
			default:
				return null;
		}
	}
	

	public Object getValueAt(int row, int col) {
		switch (types[col]) {
			case Nom:
				return listeCartes.getCarte(row).getNom();
			case CoutMana:
				return listeCartes.getCarte(row).getMana();
			case Classe:
				return listeCartes.getCarte(row).getClasse();
			case Rarete:
				return listeCartes.getCarte(row).getRarete();
			case Extension:
				return listeCartes.getCarte(row).getExtension();
			case Action:
				return listeCartes.getCarte(row);
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
	
	
	
	public void changeListe(ListeCartes liste) {
		listeCartes = liste;
		fireTableDataChanged();
	}
	
	public void changeTitres(String[] titres) {
		this.titres = titres;
		this.fireTableStructureChanged();
	}
}




