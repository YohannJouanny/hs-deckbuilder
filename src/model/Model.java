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
import donnees.Config;
import donnees.Extension;


public class Model {
	private Config config;
	private ListeCartes listeCartes;
	private ListeCartesModel modelLC;
	private StatsModel modelStats;
	private PityTimerModel modelPT;
	private StatsBoosterModel modelSB;
	
	
	public Model() {
		loadModel();
		
		modelLC = new ListeCartesModel(this);
		modelStats = new StatsModel(this);
		modelSB = new StatsBoosterModel(this);
		modelPT = new PityTimerModel(this);
	}
	
	

	public Config getConfig() {
		return config;
	}
	
	public void changeConfig(Config config) {
		this.config = config;
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
		loadConfig();
		loadExtensions();
		loadListe();
		refreshListeCarteExtPointer();
	}
	
	public void saveModel() {
		saveConfig();
		saveExtensions();
		saveListe();
		modelPT.savePityTimerData();
	}
	
	private void loadConfig() {
		try {
			ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File("config.data"))));
			config = (Config)ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			config = new Config();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void saveConfig() {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File("config.data"))));
			oos.writeObject(config);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			listeCartes = new ListeCartes();
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




