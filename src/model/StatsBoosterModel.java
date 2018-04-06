package model;

import java.text.NumberFormat;
import java.util.ArrayList;

import donnees.Carte;
import donnees.Classe;
import donnees.Extension;
import donnees.Rarete;


public class StatsBoosterModel {
	
	public enum TypeTableau {
		NombreCarte("Nombre de cartes"),
		ProbaRarete("Taux de drop par rareté"),
		CoutPoussiere("Couts en poussières"),
		StatsNormale("Stats de complétion de cartes normales"),
		StatsDoree("Stats de complétion de cartes dorées"),
		StatsInteressante("Stats de complétion de cartes intéressantes"),
		ProbaCoffre("Taux de drop sur les coffres"),
		MoyPoussiere("Valeur moyenne des booster en poussières");
		
		
		private String nom;
		
		private TypeTableau (String nom) {
			this.nom = nom;
		}
		
		public String toString() {
			return nom;
		}
	}
	
	
	private Model model;
	private StatsTableModel statsTM;
	
	
	private int[][] nombreCarteData;
	private double[][] probaRareteData;
	private int[][] coutPoussiereData;
	
	private int[][] nbcNormaleData;
	private double[][] pbNormaleData;
	private int[][] cpNormaleData;
	
	private int[][] nbcDoreeData;
	private double[][] pbDoreeData;
	private int[][] cpDoreeData;
	
	private int[][] nbcInteressanteData;
	private double[][] pbInteressanteData;
	private int[][] cpInteressanteData;
	
	private double[][] probaCoffreData;
	private double[] moyPoussiereData;
	
	
	
	public StatsBoosterModel(Model model) {
		this.model = model;
		statsTM = new StatsTableModel(new String[0], new String[0][0]);
		
		computeAllData();
		setAsTable_NombreCarte();
	}
	
	
	
	public StatsTableModel getStatsTableModel() {
		return statsTM;
	}
	
	
	public void changeTableau(TypeTableau type) {
		switch (type) {
			case NombreCarte:
				setAsTable_NombreCarte();
				break;
			case ProbaRarete:
				setAsTable_ProbaRarete();
				break;
			case CoutPoussiere:
				setAsTable_CoutPoussiere();
				break;
			case StatsNormale:
				setAsTable_StatsNormale();
				break;
			case StatsDoree:
				setAsTable_StatsDoree();
				break;
			case StatsInteressante:
				setAsTable_StatsInteressante();
				break;
			case ProbaCoffre:
				setAsTable_ProbaCoffre();
				break;
			case MoyPoussiere:
				setAsTable_MoyPoussiere();
				break;
			default:
				setAsTable_NombreCarte();
				break;
		}
	}
	
	
	public void refreshData() {
		computeAllData();
	}
	
	
	
	
	
	private void computeAllData() {
		computeNombreCarteData();
		computeProbaRareteData();
		computeCoutPoussiereData();
		
		computeNbcNormaleData();
		computePbNormaleData();
		computeCpNormaleData();
		
		computeNbcDoreeData();
		computePbDoreeData();
		computeCpDoreeData();
		
		computeNbcInteressanteData();
		computePbInteressanteData();
		computeCpInteressanteData();
		
		computeProbaCoffreData();
		computeMoyPoussiereData();
	}
	
	
	
	
	private void computeNombreCarteData() {
		ArrayList<Extension> exts = getExtensionsWithBooster();
		
		nombreCarteData = new int[exts.size() + 1][Rarete.values().length];
		
		
		for (int j = 0; j < Rarete.values().length; j++) {
			Rarete rarete = Rarete.values()[j];
			int total = 0;
			
			for (int i = 0; i < exts.size(); i++) {
				Extension ext = exts.get(i);
				int compteur = model.getListeCartes().getListeFiltree(ext, Classe.All, rarete).size();
				
				nombreCarteData[i][j] = compteur;
				total += compteur;
			}
			
			nombreCarteData[exts.size()][j] = total;
		}
	}
	
	
	private void setAsTable_NombreCarte() {
		ArrayList<Extension> exts = getExtensionsWithBooster();
		
		String[] titres = {"Extension", "Commune", "Rare", "Epique", "Légendaire", "Total"};
		String[][] table = new String[exts.size() + 1][Rarete.values().length + 1];
		
		
		for (int i = 0; i < exts.size() + 1; i++) {
			if (i == exts.size())
				table[i][0] = "Total";
			else
				table[i][0] = exts.get(i).toString();
			
			for (int j = 0; j < Rarete.values().length; j++) {
				table[i][j+1] = "" + nombreCarteData[i][j];
			}
		}
		
		
		statsTM.updateTitres(titres);
		statsTM.updateData(table);
	}
	
	
	
	
	
	private void computeProbaRareteData() {
		probaRareteData = new double[3][Rarete.values().length];
		
		probaRareteData[0][0] = 0.7014;
		probaRareteData[0][1] = 0.2151;
		probaRareteData[0][2] = 0.0419;
		probaRareteData[0][3] = 0.0100;
		probaRareteData[0][4] = 0.9684;
		
		probaRareteData[1][0] = 0.0149;
		probaRareteData[1][1] = 0.0133;
		probaRareteData[1][2] = 0.0025;
		probaRareteData[1][3] = 0.0009;
		probaRareteData[1][4] = 0.0316;
		
		probaRareteData[2][0] = 0.7163;
		probaRareteData[2][1] = 0.2284;
		probaRareteData[2][2] = 0.0444;
		probaRareteData[2][3] = 0.0109;
		probaRareteData[2][4] = 1.0000;
	}
	
	
	private void setAsTable_ProbaRarete() {
		String[] titres = {"Type carte", "Commune", "Rare", "Epique", "Légendaire", "Total"};
		String[][] table = new String[3][Rarete.values().length + 1];
		
		
		table[0][0] = "Normale";
		table[1][0] = "Dorée";
		table[2][0] = "Toutes";
		
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < Rarete.values().length; j++) {
				table[i][j+1] = doubleToPercent(probaRareteData[i][j]);
			}
		}
		
		
		statsTM.updateTitres(titres);
		statsTM.updateData(table);
	}
	
	
	
	
	
	private void computeCoutPoussiereData() {
		coutPoussiereData = new int[4][Rarete.values().length - 1];
		
		coutPoussiereData[0][0] = 40;
		coutPoussiereData[0][1] = 100;
		coutPoussiereData[0][2] = 400;
		coutPoussiereData[0][3] = 1600;
		
		coutPoussiereData[1][0] = 400;
		coutPoussiereData[1][1] = 800;
		coutPoussiereData[1][2] = 1600;
		coutPoussiereData[1][3] = 3200;
		
		coutPoussiereData[2][0] = 5;
		coutPoussiereData[2][1] = 20;
		coutPoussiereData[2][2] = 100;
		coutPoussiereData[2][3] = 400;
		
		coutPoussiereData[3][0] = 50;
		coutPoussiereData[3][1] = 100;
		coutPoussiereData[3][2] = 400;
		coutPoussiereData[3][3] = 1600;
	}
	
	
	private void setAsTable_CoutPoussiere() {
		String[] titres = {"", "Commune", "Rare", "Epique", "Légendaire"};
		String[][] table = new String[4][Rarete.values().length];
		
		
		table[0][0] = "Craft normal";
		table[1][0] = "Craft dorée";
		table[2][0] = "Déraft normal";
		table[3][0] = "Décraft dorée";
		
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < Rarete.values().length - 1; j++) {
				table[i][j+1] = ""  + coutPoussiereData[i][j];
			}
		}
		
		
		statsTM.updateTitres(titres);
		statsTM.updateData(table);
	}
	
	
	
	
	
	private void computeNbcNormaleData() {
		ArrayList<Extension> exts = getExtensionsWithBooster();
		
		nbcNormaleData = new int[exts.size() + 1][Rarete.values().length];
		
		
		for (int j = 0; j < Rarete.values().length; j++) {
			Rarete rarete = Rarete.values()[j];
			int total = 0;
			
			for (int i = 0; i < exts.size(); i++) {
				Extension ext = exts.get(i);
				int compteur = 0;
				
				for (Carte c : model.getListeCartes().getListeFiltree(ext, Classe.All, rarete)) {
					if (!c.hasMaxCarteNormale())
						compteur++;
				}
				
				nbcNormaleData[i][j] = compteur;
				total += compteur;
			}
			
			nbcNormaleData[exts.size()][j] = total;
		}
	}
	
	private void computePbNormaleData() {
		ArrayList<Extension> exts = getExtensionsWithBooster();
		
		pbNormaleData = new double[exts.size()][Rarete.values().length + 1];
		
		
		for (int i = 0; i < exts.size(); i++) {
			Extension ext = exts.get(i);
			double total = 0.0;
			
			for (int j = 0; j < Rarete.values().length - 1; j++) {
				double proba = 0.0;
				
				if (nombreCarteData[i][j] != 0) {
					if (Rarete.values()[j] == Rarete.Legendaire && !model.getListeCartes().hasAllLegendaire(ext)) {
						proba = probaRareteData[0][j];
					}
					else {
						proba = ((double)nbcNormaleData[i][j]/(double)nombreCarteData[i][j])*probaRareteData[0][j];
					}
				}
				
				pbNormaleData[i][j] = proba;
				total += proba;
			}
			
			pbNormaleData[i][Rarete.values().length - 1] = total;
			pbNormaleData[i][Rarete.values().length] = (1.0 - Math.pow((1.0 - total), 5));
		}
	}
	
	private void computeCpNormaleData() {
		ArrayList<Extension> exts = getExtensionsWithBooster();
		
		cpNormaleData = new int[exts.size() + 1][Rarete.values().length];
		
		
		for (int i = 0; i < exts.size(); i++) {
			Extension ext = exts.get(i);
			int total = 0;
			
			for (int j = 0; j < Rarete.values().length - 1; j++) {
				Rarete rarete = Rarete.values()[j];
				int nbcTotal = 0, nbcPossedees = 0;
				
				for (Carte c : model.getListeCartes().getListeFiltree(ext, Classe.All, rarete)) {
					if (c.getRarete() != Rarete.Legendaire) 
						nbcTotal += 2;
					else
						nbcTotal += 1;
					
					nbcPossedees += c.getNbCarteNormalePossede();
				}
				
				cpNormaleData[i][j] = (nbcTotal - nbcPossedees) * coutPoussiereData[0][j];
				total += cpNormaleData[i][j];
			}
			
			cpNormaleData[i][Rarete.values().length - 1] = total;
		}
		
		for (int j = 0; j < Rarete.values().length; j++) {
			int total = 0;
			
			for (int i = 0; i < exts.size(); i++) {
				total += cpNormaleData[i][j];
			}
			
			cpNormaleData[exts.size()][j] = total;
		}
	}
	
	
	
	private void setAsTable_StatsNormale() {
		ArrayList<Extension> exts = getExtensionsWithBooster();
		
		String[] titres = {"Extension", "Commune", "Rare", "Epique", "Légendaire", "Total", "Booster bon"};
		String[][] table = new String[exts.size() + 1][Rarete.values().length + 2];
		
		
		for (int i = 0; i < exts.size() + 1; i++) {
			if (i == exts.size())
				table[i][0] = "Total";
			else
				table[i][0] = exts.get(i).toString();
			
			for (int j = 0; j < Rarete.values().length; j++) {
				table[i][j+1] = "" + nbcNormaleData[i][j];
				
				if (i < exts.size())
					table[i][j+1] += "  (" + doubleToPercent(pbNormaleData[i][j]) + ")";
				
				table[i][j+1] += "\n" + cpNormaleData[i][j];
			}
			
			if (i < exts.size())
				table[i][Rarete.values().length + 1] = doubleToPercent(pbNormaleData[i][Rarete.values().length]);
			else
				table[i][Rarete.values().length + 1] = "";
		}
		
		
		statsTM.updateTitres(titres);
		statsTM.updateData(table);
	}
	
	
	
	
	
	private void computeNbcDoreeData() {
		ArrayList<Extension> exts = getExtensionsWithBooster();
		
		nbcDoreeData = new int[exts.size() + 1][Rarete.values().length];
		
		
		for (int j = 0; j < Rarete.values().length; j++) {
			Rarete rarete = Rarete.values()[j];
			int total = 0;
			
			for (int i = 0; i < exts.size(); i++) {
				Extension ext = exts.get(i);
				int compteur = 0;
				
				for (Carte c : model.getListeCartes().getListeFiltree(ext, Classe.All, rarete)) {
					if (!c.hasMaxCarteDoree())
						compteur++;
				}
				
				nbcDoreeData[i][j] = compteur;
				total += compteur;
			}
			
			nbcDoreeData[exts.size()][j] = total;
		}
	}
	
	private void computePbDoreeData() {
		ArrayList<Extension> exts = getExtensionsWithBooster();
		
		pbDoreeData = new double[exts.size()][Rarete.values().length + 1];
		
		
		for (int i = 0; i < exts.size(); i++) {
			Extension ext = exts.get(i);
			double total = 0.0;
			
			for (int j = 0; j < Rarete.values().length - 1; j++) {
				double proba = 0.0;
				
				if (nombreCarteData[i][j] != 0) {
					if (Rarete.values()[j] == Rarete.Legendaire && !model.getListeCartes().hasAllLegendaire(ext)) {
						proba = probaRareteData[1][j];
					}
					else {
						proba = ((double)nbcDoreeData[i][j]/(double)nombreCarteData[i][j])*probaRareteData[1][j];
					}
				}
				
				pbDoreeData[i][j] = proba;
				total += proba;
			}
			
			pbDoreeData[i][Rarete.values().length - 1] = total;
			pbDoreeData[i][Rarete.values().length] = (1.0 - Math.pow((1.0 - total), 5));
		}
	}
	
	private void computeCpDoreeData() {
		ArrayList<Extension> exts = getExtensionsWithBooster();
		
		cpDoreeData = new int[exts.size() + 1][Rarete.values().length];
		
		
		for (int i = 0; i < exts.size(); i++) {
			Extension ext = exts.get(i);
			int total = 0;
			
			for (int j = 0; j < Rarete.values().length - 1; j++) {
				Rarete rarete = Rarete.values()[j];
				int nbcTotal = 0, nbcPossedees = 0;
				
				for (Carte c : model.getListeCartes().getListeFiltree(ext, Classe.All, rarete)) {
					if (c.getRarete() != Rarete.Legendaire) 
						nbcTotal += 2;
					else
						nbcTotal += 1;
					
					nbcPossedees += c.getNbCarteDoreePossede();
				}
				
				cpDoreeData[i][j] = (nbcTotal - nbcPossedees) * coutPoussiereData[1][j];
				total += cpDoreeData[i][j];
			}
			
			cpDoreeData[i][Rarete.values().length - 1] = total;
		}
		
		for (int j = 0; j < Rarete.values().length; j++) {
			int total = 0;
			
			for (int i = 0; i < exts.size(); i++) {
				total += cpDoreeData[i][j];
			}
			
			cpDoreeData[exts.size()][j] = total;
		}
	}
	
	
	
	private void setAsTable_StatsDoree() {
		ArrayList<Extension> exts = getExtensionsWithBooster();
		
		String[] titres = {"Extension", "Commune", "Rare", "Epique", "Légendaire", "Total", "Booster bon"};
		String[][] table = new String[exts.size() + 1][Rarete.values().length + 2];
		
		
		for (int i = 0; i < exts.size() + 1; i++) {
			if (i == exts.size())
				table[i][0] = "Total";
			else
				table[i][0] = exts.get(i).toString();
			
			for (int j = 0; j < Rarete.values().length; j++) {
				table[i][j+1] = "" + nbcDoreeData[i][j];
				
				if (i < exts.size())
					table[i][j+1] += "  (" + doubleToPercent(pbDoreeData[i][j]) + ")";
				
				table[i][j+1] += "\n" + cpDoreeData[i][j];
			}
			
			if (i < exts.size())
				table[i][Rarete.values().length + 1] = doubleToPercent(pbDoreeData[i][Rarete.values().length]);
			else
				table[i][Rarete.values().length + 1] = "";
		}
		
		
		statsTM.updateTitres(titres);
		statsTM.updateData(table);
	}
	
	
	
	
	
	private void computeNbcInteressanteData() {
		ArrayList<Extension> exts = getExtensionsWithBooster();
		
		nbcInteressanteData = new int[exts.size() + 1][Rarete.values().length];
		
		
		for (int j = 0; j < Rarete.values().length; j++) {
			Rarete rarete = Rarete.values()[j];
			int total = 0;
			
			for (int i = 0; i < exts.size(); i++) {
				Extension ext = exts.get(i);
				int compteur = 0;
				
				for (Carte c : model.getListeCartes().getListeFiltree(ext, Classe.All, rarete)) {
					if (c.isInteressante() && !c.hasMaxCarte()) {
						compteur++;
					}
				}
				
				nbcInteressanteData[i][j] = compteur;
				total += compteur;
			}
			
			nbcInteressanteData[exts.size()][j] = total;
		}
	}
	
	private void computePbInteressanteData() {
		ArrayList<Extension> exts = getExtensionsWithBooster();
		
		pbInteressanteData = new double[exts.size()][Rarete.values().length + 1];
		
		
		for (int i = 0; i < exts.size(); i++) {
			Extension ext = exts.get(i);
			double total = 0.0;
			
			for (int j = 0; j < Rarete.values().length - 1; j++) {
				double proba = 0.0;
				
				if (nombreCarteData[i][j] != 0) {
					if (Rarete.values()[j] == Rarete.Legendaire && !model.getListeCartes().hasAllLegendaire(ext)) {
						double nbCarteNonPossedees = model.getListeCartes().getNombreCarteNonPossedees(ext, Rarete.Legendaire);
						proba = ((double)nbcInteressanteData[i][j]/nbCarteNonPossedees)*probaRareteData[2][j];
					}
					else {
						proba = ((double)nbcInteressanteData[i][j]/(double)nombreCarteData[i][j])*probaRareteData[2][j];
					}
				}
				
				pbInteressanteData[i][j] = proba;
				total += proba;
			}
			
			pbInteressanteData[i][Rarete.values().length - 1] = total;
			pbInteressanteData[i][Rarete.values().length] = (1.0 - Math.pow((1.0 - total), 5));
		}
	}
	
	private void computeCpInteressanteData() {
		ArrayList<Extension> exts = getExtensionsWithBooster();
		
		cpInteressanteData = new int[exts.size() + 1][Rarete.values().length];
		
		
		for (int i = 0; i < exts.size(); i++) {
			Extension ext = exts.get(i);
			int total = 0;
			
			for (int j = 0; j < Rarete.values().length - 1; j++) {
				Rarete rarete = Rarete.values()[j];
				int nbcTotal = 0, nbcPossedees = 0;
				
				for (Carte c : model.getListeCartes().getListeFiltree(ext, Classe.All, rarete)) {
					if (c.isInteressante()) {
						int temp = c.getNbCarteNormalePossede() + c.getNbCarteDoreePossede();
						
						if (c.getRarete() != Rarete.Legendaire) {
							nbcTotal += 2;
							
							if (temp > 2)
								temp = 2;
						}
						else {
							nbcTotal += 1;
							
							if (temp > 1)
								temp = 1;
						}
						
						nbcPossedees += temp;
					}
				}
				
				cpInteressanteData[i][j] = (nbcTotal - nbcPossedees) * coutPoussiereData[0][j];
				total += cpInteressanteData[i][j];
			}
			
			cpInteressanteData[i][Rarete.values().length - 1] = total;
		}
		
		for (int j = 0; j < Rarete.values().length; j++) {
			int total = 0;
			
			for (int i = 0; i < exts.size(); i++) {
				total += cpInteressanteData[i][j];
			}
			
			cpInteressanteData[exts.size()][j] = total;
		}
	}
	
	
	
	private void setAsTable_StatsInteressante() {
		ArrayList<Extension> exts = getExtensionsWithBooster();
		
		String[] titres = {"Extension", "Commune", "Rare", "Epique", "Légendaire", "Total", "Booster bon"};
		String[][] table = new String[exts.size() + 1][Rarete.values().length + 2];
		
		
		for (int i = 0; i < exts.size() + 1; i++) {
			if (i == exts.size())
				table[i][0] = "Total";
			else
				table[i][0] = exts.get(i).toString();
			
			for (int j = 0; j < Rarete.values().length; j++) {
				table[i][j+1] = "" + nbcInteressanteData[i][j];
				
				if (i < exts.size())
					table[i][j+1] += "  (" + doubleToPercent(pbInteressanteData[i][j]) + ")";
				
				table[i][j+1] += "\n" + cpInteressanteData[i][j];
			}
			
			if (i < exts.size())
				table[i][Rarete.values().length + 1] = doubleToPercent(pbInteressanteData[i][Rarete.values().length]);
			else
				table[i][Rarete.values().length + 1] = "";
		}
		
		
		statsTM.updateTitres(titres);
		statsTM.updateData(table);
	}
	
	
	
	
	
	private void computeProbaCoffreData() {
		probaCoffreData = new double[2][Rarete.values().length - 1];
		
		
		for (int j = 0; j < Rarete.values().length - 1; j++) {
			int nbcTotal = 0, nbcDoree = 0, nbcInt = 0;
			
			for (Carte c : model.getListeCartes().getListeFiltree(Extension.ALL, Classe.All, Rarete.values()[j])) {
				if ((!c.getExtension().isAventure()) && c.getExtension().isStandard()) {
					nbcTotal++;
					
					if (!c.hasMaxCarteDoree())
						nbcDoree++;
					
					if (c.isInteressante()) {
						int nbcPossedee = c.getNbCarteNormalePossede() + c.getNbCarteDoreePossede();
						
						if (c.getRarete() != Rarete.Legendaire) {
							if (nbcPossedee < 2)
								nbcInt++;
						}
						else {
							if (nbcPossedee < 1)
								nbcInt++;
						}
					}
				}
			}
			
			
			if (nbcTotal != 0) {
				probaCoffreData[0][j] = (double)nbcDoree/(double)nbcTotal;
				probaCoffreData[1][j] = (double)nbcInt/(double)nbcTotal;
			}
			else {
				probaCoffreData[0][j] = 0.0;
				probaCoffreData[1][j] = 0.0;
			}
		}
	}
	
	
	
	private void setAsTable_ProbaCoffre() {
		String[] titres = {"Type carte", "Commune", "Rare", "Epique", "Légendaire"};
		String[][] table = new String[2][Rarete.values().length];
		
		
		table[0][0] = "Dorée";
		table[1][0] = "Intéressante";
		
		
		for (int j = 0; j < Rarete.values().length - 1; j++) {
			table[0][j+1] = doubleToPercent(probaCoffreData[0][j]);
			table[1][j+1] = doubleToPercent(probaCoffreData[1][j]);
		}
		
		
		statsTM.updateTitres(titres);
		statsTM.updateData(table);
	}
	
	
	
	
	
	private void computeMoyPoussiereData() {
		ArrayList<Extension> exts = getExtensionsWithBooster();
		
		moyPoussiereData = new double[exts.size()];
		
		
		for (int i = 0; i < exts.size(); i++) {
			double[][] tabMoy = new double[4][Rarete.values().length - 1];
			
			for (int j = 0; j < Rarete.values().length - 1; j++) {
				if (nombreCarteData[i][j] != 0) {
					tabMoy[0][j] = (((double)nbcNormaleData[i][j]/(double)nombreCarteData[i][j]) * (double)probaRareteData[0][j]) 
									* (double)coutPoussiereData[0][j];
					tabMoy[1][j] = (((double)nbcDoreeData[i][j]/(double)nombreCarteData[i][j]) * (double)probaRareteData[1][j]) 
									* (double)coutPoussiereData[1][j];
					tabMoy[2][j] = ((1.0 - (double)nbcNormaleData[i][j]/(double)nombreCarteData[i][j]) * (double)probaRareteData[0][j]) 
									* (double)coutPoussiereData[2][j];
					tabMoy[3][j] = ((1.0 - (double)nbcDoreeData[i][j]/(double)nombreCarteData[i][j]) * (double)probaRareteData[1][j]) 
									* (double)coutPoussiereData[3][j];
				}
				else {
					tabMoy[0][j] = 0.0;
					tabMoy[1][j] = 0.0;
					tabMoy[2][j] = 0.0;
					tabMoy[3][j] = 0.0;
				}
			}
			
			moyPoussiereData[i] = sommeTableau(tabMoy) * 5.0;
		}
	}
	
	
	
	private void setAsTable_MoyPoussiere() {
		ArrayList<Extension> exts = getExtensionsWithBooster();
		
		String[] titres = {"Extension", "Moyenne par booster"};
		String[][] table = new String[exts.size()][2];
		
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
		
		
		for (int i = 0; i < exts.size(); i++) {
			table[i][0] = exts.get(i).toString();
			table[i][1] = nf.format(moyPoussiereData[i]);
		}
		
		
		statsTM.updateTitres(titres);
		statsTM.updateData(table);
	}
	
	
	
	
	
	
	
	
	
	
	private ArrayList<Extension> getExtensionsWithBooster() {
		ArrayList<Extension> res = new ArrayList<Extension>();
		
		for (Extension ext : Extension.values()) {
			if (!ext.isAventure())
				res.add(ext);
		}
		
		return res;
	}
	
	
	
	private String doubleToPercent(double val) {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
		
		return nf.format(val*100.0) + "%";
	}
	
	
	private double sommeTableau(double[][] tab) {
		double res = 0.0;
		
		for (int i = 0; i < tab.length; i++) {
			for (int j = 0; j < tab[0].length; j++) {
				res += tab[i][j];
			}
		}
		
		return res;
	}
	
}





