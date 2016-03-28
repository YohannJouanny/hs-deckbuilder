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
		Collection,
		Suppression,
		Interet;
	}
	
	
	private TypeColonne[] types;
	private ListeCartes listeCartes;
	
	
	public ListeCartesTableModel(TypeColonne[] types, ListeCartes liste) {
		this.types = types;
		this.listeCartes = liste;
	}
	
	
	
	public int getColumnCount() {
		return types.length;
	}

	public int getRowCount() {
		return listeCartes.size();
	}
	
	public String getColumnName(int col) {
		switch (types[col]) {
			case Nom:
				return "Nom";
			case CoutMana:
				return "Mana";
			case Classe:
				return "Classe";
			case Rarete:
				return "Rareté";
			case Extension:
				return "Extension";
			case Collection:
				return "Collection";
			case Suppression:
				return "Supprimer carte";
			case Interet:
				return "Intérêt";
			default:
				return null;
		}
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
			case Collection:
				return Carte.class;
			case Suppression:
				return Carte.class;
			case Interet:
				return boolean.class;
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
			case Collection:
				return listeCartes.getCarte(row);
			case Suppression:
				return listeCartes.getCarte(row);
			case Interet:
				return listeCartes.getCarte(row);
			default:
				return null;
		}
	}
	
	public void setValueAt(Object value, int row, int col) {
		
	}
	
	
	public boolean isCellEditable(int row, int col) {
		switch (types[col]) {
			case Collection:
			case Suppression:
			case Interet:
				return true;
			default:
				return false;
		}
	}
	
	
	
	public void changeListe(ListeCartes liste) {
		listeCartes = liste;
		fireTableDataChanged();
	}
	
	public void changeColonnes(TypeColonne[] types) {
		this.types = types;
		this.fireTableStructureChanged();
	}
}




