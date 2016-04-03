package model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import donnees.Carte;
import donnees.Extension;


public class Model {
	private ListeCartes listeCartes;
	private ListeCartesModel modelLC;
	private StatsModel modelStats;
	private PityTimerModel modelPT;
	private StatsBoosterModel modelSB;
	
	
	public Model() {
		listeCartes = new ListeCartes();
		loadModel();
		
		modelLC = new ListeCartesModel(this);
		modelStats = new StatsModel(this);
		modelSB = new StatsBoosterModel(this);
		modelPT = new PityTimerModel();
	}
	
	
	
	public ListeCartes getListeCartes() {
		return listeCartes;
	}
	
	public void supprimerAllCartes() {
		listeCartes = new ListeCartes();
	}
	
	
	
	public ListeCartesModel getListeCartesModel() {
		return modelLC;
	}
	
	public StatsModel getStatsModel() {
		return modelStats;
	}
	
	public StatsBoosterModel getStatsBoosterModel() {
		return modelSB;
	}
	
	public PityTimerModel getPityTimerModel() {
		return modelPT;
	}
	
	
	
	public void creerExtension(String nom, boolean isAdv, boolean isStandard) {
		Extension.createExtension(nom, isAdv, isStandard);
		modelPT.notifyAjoutExtension();
	}
	
	public void editerExtension(Extension ext, boolean isAventure, boolean isStandard) {
		ext.edit(isAventure, isStandard);
	}
	
	public void supprimerExtention(Extension ext) {
		int indexExt = Extension.values().indexOf(ext);
		
		
		for (int i = 0; i < listeCartes.size(); i++) {
			Carte c = listeCartes.getCarte(i);
			
			if (c.getExtension().equals(ext)) {
				listeCartes.supprimerCarte(c);
				i--;
			}
				
		}
		
		
		Extension.deleteExtension(ext);
		modelPT.notifySuppressionExtension(indexExt);
	}
	
	
	
	
	
	public void loadModel() {
		loadExtensions();
		loadListe();
		refreshListeCarteExtPointer();
	}
	
	public void saveModel() {
		saveExtensions();
		saveListe();
		modelPT.savePityTimerData();
	}
	
	
	private void loadExtensions() {
		
	}
	
	private void saveExtensions() {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File("extensions.data"))));
			oos.writeObject(Extension.values());
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void loadListe() {
		try {
			ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File("liste.data"))));
			listeCartes = (ListeCartes)ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			// Do nothing, not an error
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void saveListe() {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File("liste.data"))));
			oos.writeObject(listeCartes);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	private void refreshListeCarteExtPointer() {
		for (Carte c : listeCartes) {
			c.refreshExtensionPointer();
		}
	}
	
}




