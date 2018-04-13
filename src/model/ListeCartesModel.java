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
	private Rarete critereRarete;
	private Extension critereExtension;
	private String critereNom;
	
	
	public ListeCartesModel(Model model) {
		this.model = model;
		critereMana = -1;
		critereClasse = Classe.All;
		critereRarete = Rarete.All;
		critereExtension = Extension.ALL;
		critereNom = "";
		
		initListeCartesTableModel();
	}
	
	
	public boolean getParamHideWildExt() {
		return model.getConfig().isHideWildExtensions();
	}
	
	public ListeCartesTableModel getListeCartesTableModel() {
		return listeCarteTM;
	}
	
	public void changeCriteres(int coutMana, Classe classe, Rarete rarete, Extension ext, String nom) {
		critereMana = coutMana;
		critereClasse = classe;
		critereRarete = rarete;
		critereExtension = ext;
		critereNom = nom;
		actualiseTable();
	}
	
	
	public void creerCarte(String nom, int mana, Classe classe, Carte.Type type, Rarete rarete, Extension ext) {
		model.getListeCartes().ajouterCarte(new Carte(nom, mana, classe, type, rarete, ext));
		model.getListeCartes().sort();
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
			TypeColonne[] types = {TypeColonne.CoutMana, TypeColonne.Nom, TypeColonne.Classe, TypeColonne.Rarete, 
									TypeColonne.Extension, TypeColonne.Suppression};
			listeCarteTM.changeColonnes(types);
		}
		else {
			TypeColonne[] types = {TypeColonne.CoutMana, TypeColonne.Nom, TypeColonne.Classe, TypeColonne.Rarete,
									TypeColonne.Extension, TypeColonne.Collection, TypeColonne.Interet};
			listeCarteTM.changeColonnes(types);
		}
	}
	
	
	private void actualiseTable() {
		ListeCartes liste = new ListeCartes();
		
		for (Carte c : model.getListeCartes().getListeFiltree(critereExtension, critereClasse, critereRarete, critereNom)) {
			if (critereMana == -1 || c.getMana() == critereMana || (critereMana == 7 && c.getMana() >= critereMana)) {
				liste.ajouterCarte(c);
			}
		}
		
		liste.sort();
		listeCarteTM.changeListe(liste);
	}
	
	
	private void initListeCartesTableModel() {
		TypeColonne[] types = {TypeColonne.CoutMana, TypeColonne.Nom, TypeColonne.Classe, TypeColonne.Rarete,
								TypeColonne.Extension, TypeColonne.Collection, TypeColonne.Interet};
		
		listeCarteTM = new ListeCartesTableModel(types, new ListeCartes());
	}
}





