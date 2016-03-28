package model;

import java.util.ArrayList;

import donnees.Carte;
import donnees.Classe;
import donnees.Extension;
import donnees.Rarete;


public class StatsModel {
	private Model model;
	private StatsTableModel statsTM;
	
	
	public StatsModel(Model model) {
		this.model = model;
		
		String[] titres = {"Classe", "Commune", "Rare", "Epique", "Légendaire", "Total"};
		statsTM = new StatsTableModel(titres , createDataTable(new ArrayList<Extension>(), false, false));
	}
	
	
	
	public StatsTableModel getStatsTableModel() {
		return statsTM;
	}
	
	public void changeCriteres(ArrayList<Extension> critereExt, boolean critereCN, boolean critereCD) {
		statsTM.updateData(createDataTable(critereExt, critereCN, critereCD));
	}
	
	
	
	
	private String[][] createDataTable(ArrayList<Extension> critereExt, boolean critereCN, boolean critereCD) {
		ListeCartes liste = new ListeCartes();
		
		for (Carte c : model.getListeCartes()) {
			for (Extension ext : critereExt) {
				if (c.getExtension().equals(ext)) {
					liste.ajouterCarte(c);
					break;
				}
			}
		}
		
		
		String[][] res = new String[Classe.values().length][Rarete.values().length + 1];
		
		for (int i = 0; i < res.length; i++) {
			if (Classe.values()[i] != Classe.All)
				res[i][0] = Classe.values()[i].toString();
			else
				res[i][0] = "Total";
		}
		
		for (int i = 0; i < res.length; i++) {
			for (int j = 0; j < Rarete.values().length; j++) {
				res[i][j+1] = calculVal(liste, critereCN, critereCD, Classe.values()[i], Rarete.values()[j]);
			}
		}
		
		
		return res;
	}
	
	
	private String calculVal(ListeCartes liste, boolean critereCN, boolean critereCD, Classe classe, Rarete rarete) {
		int possedees = 0;
		int total = 0;
		
		
		for (Carte carte : liste) {
			if ((carte.getClasse() == classe || classe == Classe.All) && (carte.getRarete() == rarete || rarete == Rarete.All)) {
				if (critereCN) {
					possedees += carte.getNbCarteNormalePossede();
					
					if (carte.getRarete() == Rarete.Legendaire)
						total++;
					else
						total += 2;
				}
				
				if (critereCD) {
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
}





