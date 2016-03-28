package model;

import donnees.Carte;
import donnees.Classe;
import donnees.Extension;
import donnees.Rarete;
import model.ListeCartesTableModel.TypeColonne;


public class ListeCartesModel {
	private Model model;
	private ListeCartesTableModel listeCarteTM;
	
	private int critereMana;
	private Classe critereClasse;
	private Extension critereExtension;
	
	
	public ListeCartesModel(Model model) {
		this.model = model;
		critereMana = -1;
		critereClasse = Classe.All;
		critereExtension = Extension.ALL;
		
		initListeCartesTableModel();
	}
	
	
	
	public ListeCartesTableModel getListeCartesTableModel() {
		return listeCarteTM;
	}
	
	public void changeCriteres(int coutMana, Classe classe, Extension ext) {
		critereMana = coutMana;
		critereClasse = classe;
		critereExtension = ext;
		actualiseTable();
	}
	
	
	public void creerCarte(String nom, int mana, Classe classe, Carte.Type type, Rarete rarete, Extension ext) {
		model.getListeCartes().ajouterCarte(new Carte(nom, mana, classe, type, rarete, ext));
		actualiseTable();
	}
	
	public void supprimerCarte(Carte c) {
		model.getListeCartes().supprimerCarte(c);
		actualiseTable();
	}
	
	public void supprimerAllCartes() {
		model.supprimerAllCartes();
		actualiseTable();
	}
	
	public void resetCollection() {
		for (Carte c : model.getListeCartes()) {
			c.resetPossessions();
		}
		actualiseTable();
	}
	
	
	public void switchTable(boolean editMode) {
		if (editMode) {
			TypeColonne[] types = {TypeColonne.CoutMana, TypeColonne.Nom, TypeColonne.Classe, TypeColonne.Extension, TypeColonne.Suppression};
			listeCarteTM.changeColonnes(types);
		}
		else {
			TypeColonne[] types = {TypeColonne.CoutMana, TypeColonne.Nom, TypeColonne.Classe, 
									TypeColonne.Extension, TypeColonne.Collection, TypeColonne.Interet};
			listeCarteTM.changeColonnes(types);
		}
	}
	
	
	private void actualiseTable() {
		ListeCartes liste = new ListeCartes();
		
		for (Carte c : model.getListeCartes()) {
			if (critereMana == -1 || c.getMana() == critereMana || (critereMana == 7 && c.getMana() >= critereMana)) {
				if (critereClasse == Classe.All || c.getClasse()  == critereClasse) {
					if (critereExtension.equals(Extension.ALL) || c.getExtension().equals(critereExtension)) {
						liste.ajouterCarte(c);
					}
				}
			}
		}
		
		listeCarteTM.changeListe(liste);
	}
	
	
	private void initListeCartesTableModel() {
		TypeColonne[] types = {TypeColonne.CoutMana, TypeColonne.Nom, TypeColonne.Classe, 
				TypeColonne.Extension, TypeColonne.Collection, TypeColonne.Interet};
		
		listeCarteTM = new ListeCartesTableModel(types, new ListeCartes());
	}
}





