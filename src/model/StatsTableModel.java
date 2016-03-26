package model;

import javax.swing.table.AbstractTableModel;

import donnees.Carte;
import donnees.Classe;
import donnees.Rarete;


public class StatsTableModel extends AbstractTableModel {
	private static final long serialVersionUID = -9065509160887227194L;
	
	
	private String[] titres;
	private ListeCartes listeCartes;
	private boolean compteCarteNormale;
	private boolean compteCarteDoree;
	
	
	public StatsTableModel(ListeCartes liste, boolean compteCarteNormale, boolean compteCarteDoree) {
		String titres[] = {"Classe", "Commune", "Rare", "Epique", "Légendaire", "Total"};
		this.titres = titres;
		this.listeCartes = liste;
		this.compteCarteNormale = compteCarteNormale;
		this.compteCarteDoree = compteCarteDoree;
	}
	
	
	
	public int getColumnCount() {
		return titres.length;
	}

	public int getRowCount() {
		return Classe.values().length;
	}
	
	public String getColumnName(int col) {
		return titres[col];
	}
	
	public Class<?> getColumnClass(int col) {
		return String.class;
	}
	

	public Object getValueAt(int row, int col) {
		Classe classe = convertRowToClasse(row);
		Rarete rarete = convertColumnToRarete(col);
		
		
		if (rarete == null) {
			if (classe == Classe.All)
				return "Total";
			else
				return classe.toString();
		}
		
		
		int possedees = 0;
		int total = 0;
		
		
		for (Carte carte : listeCartes) {
			if ((carte.getClasse() == classe || classe == Classe.All) && (carte.getRarete() == rarete || rarete == Rarete.All)) {
				if (compteCarteNormale) {
					possedees += carte.getNbCarteNormalePossede();
					
					if (carte.getRarete() == Rarete.Legendaire)
						total++;
					else
						total += 2;
				}
				
				if (compteCarteDoree) {
					possedees += carte.getNbCarteDoreePossede();
					
					if (carte.getRarete() == Rarete.Legendaire)
						total++;
					else
						total += 2;
				}
			}
		}
		
		
		return new String(possedees + "/" + total);
	}
	
	public void setValueAt(Object value, int row, int col) {
		
	}
	
	
	public boolean isCellEditable(int row, int col) {
		return false;
	}
	
	
	
	public void changeCriteres(ListeCartes liste, boolean compteCarteNormale, boolean compteCarteDoree) {
		listeCartes = liste;
		this.compteCarteNormale = compteCarteNormale;
		this.compteCarteDoree = compteCarteDoree;
		fireTableDataChanged();
	}
	
	
	
	
	private Classe convertRowToClasse(int row) {
		int i = 0;
		
		for (Classe c : Classe.values()) {
			if (row == i++)
				return c;
		}
		
		return null;
	}
	
	private Rarete convertColumnToRarete(int col) {
		int i = 1;
		
		for (Rarete r : Rarete.values()) {
			if (col == i++)
				return r;
		}
		
		return null;
	}
}






