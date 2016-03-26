package model;

import java.util.ArrayList;

import donnees.Carte;
import donnees.Extension;


public class StatsModel {
	private Model model;
	private StatsTableModel statsTM;
	
	private ArrayList<Extension> critereExt;
	private boolean critereCN;
	private boolean critereCD;
	
	
	public StatsModel(Model model) {
		this.model = model;
		critereExt = new ArrayList<Extension>();
		critereCN = false;
		critereCD = false;
		
		statsTM = new StatsTableModel(new ListeCartes(), critereCN, critereCD);
	}
	
	
	
	public StatsTableModel getStatsTableModel() {
		return statsTM;
	}
	
	public void changeCriteres(ArrayList<Extension> critereExt, boolean critereCN, boolean critereCD) {
		this.critereExt = critereExt;
		this.critereCN = critereCN;
		this.critereCD = critereCD;
		actualiseTable();
	}
	
	
	private void actualiseTable() {
		ListeCartes liste = new ListeCartes();
		
		for (Carte c : model.getListeCartes()) {
			for (Extension ext : critereExt) {
				if (c.getExtension().equals(ext)) {
					liste.ajouterCarte(c);
					break;
				}
			}
		}
		
		statsTM.changeCriteres(liste, critereCN, critereCD);
	}
}





