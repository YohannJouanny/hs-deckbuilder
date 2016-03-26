package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import donnees.Carte;


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
		liste.sort(null);
	}
	
	public void supprimerCarte(Carte c) {
		liste.remove(c);
	}


	public Iterator<Carte> iterator() {
		return liste.iterator();
	}
	
	
}




