package model;

import java.io.Serializable;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.Iterator;

import donnees.Carte;
import donnees.Classe;
import donnees.Extension;
import donnees.Rarete;


public class ListeCartes implements Iterable<Carte>, Serializable {
	private static final long serialVersionUID = 1714055671612386187L;
	
	
	private ArrayList<Carte> liste;
	
	
	public ListeCartes() {
		liste = new ArrayList<Carte>();
	}
	
	public ListeCartes(ListeCartes liste) {
		this.liste = new ArrayList<Carte>();
		
		for (Carte c : liste) {
			this.liste.add(c);
		}
		
		this.liste.sort(null);
	}
	
	
	
	public int size() {
		return liste.size();
	}
	
	public Carte getCarte(int index) {
		return liste.get(index);
	}
	
	public void ajouterCarte(Carte c) {
		liste.add(c);
	}
	
	public void supprimerCarte(Carte c) {
		liste.remove(c);
	}

	public Iterator<Carte> iterator() {
		return liste.iterator();
	}
	
	public void sort() {
		liste.sort(null);
	}
	
	public ListeCartes getListeFiltree(Extension extension, Classe classe, Rarete rarete) {
		return getListeFiltree(extension, classe, rarete, null);
	}
	
	public ListeCartes getListeFiltree(Extension extension, Classe classe, Rarete rarete, String nom) {
		ListeCartes res = new ListeCartes();
		String nomCritere = (nom != null && !nom.trim().isEmpty()) ? Normalizer.normalize(nom.toUpperCase().trim(), Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "") : null;
		
		for (Carte c : liste) {
			if (!extension.equals(Extension.ALL) && !c.getExtension().equals(extension)) {
				continue;
			}
			
			if (classe != Classe.All && c.getClasse() != classe) {
				continue;
			}
			
			if (rarete != Rarete.All && c.getRarete() != rarete) {
				continue;
			}
			
			if (nomCritere != null) {
				String nomCarte = Normalizer.normalize(c.getNom().toUpperCase(), Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
				
				if (!nomCarte.contains(nomCritere)) {
					continue;
				}
			}
			
			res.ajouterCarte(c);
		}
		
		res.sort();
		return res;
	}
	
	public boolean hasAllLegendaire(Extension ext) {
		ListeCartes list = this.getListeFiltree(ext, Classe.All, Rarete.Legendaire);
		
		for (Carte c : list) {
			if (!c.hasMaxCarte()) {
				return false;
			}
		}
		
		return true;
	}
	
	public int getNombreCarteNonPossedees(Extension ext, Rarete rarete) {
		ListeCartes list = this.getListeFiltree(ext, Classe.All, rarete);
		int res = 0;
		
		for (Carte c : list) {
			if (!c.hasMaxCarte()) {
				res++;
			}
		}
		
		return res;
	}
}




