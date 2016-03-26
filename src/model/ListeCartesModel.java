package model;

import donnees.Carte;
import donnees.Classe;
import donnees.Extension;
import donnees.Rarete;


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
	
	
	public void changeTitres(boolean editMode) {
		if (editMode) {
			String titres[] = {"Mana", "Nom", "Classe", "Extension", "Supprimer carte"};
			listeCarteTM.changeTitres(titres);
		}
		else {
			String titres[] = {"Mana", "Nom", "Classe", "Extension", "Collection"};
			listeCarteTM.changeTitres(titres);
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
		String titres[] = {"Mana", "Nom", "Classe", "Extension", "Collection"};
		ListeCartesTableModel.TypeColonne colonnes[] = { ListeCartesTableModel.TypeColonne.CoutMana,
															ListeCartesTableModel.TypeColonne.Nom,
															ListeCartesTableModel.TypeColonne.Classe,
															ListeCartesTableModel.TypeColonne.Extension,
															ListeCartesTableModel.TypeColonne.Action };
		
		listeCarteTM = new ListeCartesTableModel(titres, colonnes, new ListeCartes());
	}
}





