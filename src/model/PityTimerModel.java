package model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import donnees.Extension;


public class PityTimerModel {
	
	public static final String[] rarete = new String[6];
	public static final int[] timerMax = new int[6];
	
	static {
		rarete[0] = "Epique";
		rarete[1] = "Legendaire";
		rarete[2] = "Commune dor�e";
		rarete[3] = "Rare dor�e";
		rarete[4] = "Epique dor�e";
		rarete[5] = "Legendaire dor�e";
		
		timerMax[0] = 10;
		timerMax[1] = 39;
		timerMax[2] = 24;
		timerMax[3] = 28;
		timerMax[4] = 137;
		timerMax[5] = 305;
	}
	
	
	private Model model;
	private PityTimerTableModel pityTimerTM;
	private PityTimerArchivesTableModel pityTimerArchivesTM;
	
	private Extension extension;
	private int[] boosterOuverts;
	private int[][] timerCourants;
	private int[][][] timerArchives;
	
	
	public PityTimerModel(Model model) {
		this.model = model;
		this.extension = Extension.values().get(0);
		loadPityTimerData();
		
		pityTimerTM = new PityTimerTableModel(timerCourants[Extension.values().indexOf(extension)]);
		pityTimerArchivesTM = new PityTimerArchivesTableModel(timerArchives[Extension.values().indexOf(extension)]);
	}
	
	
	public boolean getParamHideWildExt() {
		return model.getConfig().isHideWildExtensions();
	}
	
	public int getBoosterOuverts() {
		int indexExt = Extension.values().indexOf(extension);
		return boosterOuverts[indexExt];
	}
	
	public PityTimerTableModel getPityTimerTableModel() {
		return pityTimerTM;
	}
	
	public PityTimerArchivesTableModel getPityTimerArchivesTableModel() {
		return pityTimerArchivesTM;
	}
	
	
	
	public void changeExtension(Extension ext) {
		extension = ext;
		pityTimerTM.updateData(timerCourants[Extension.values().indexOf(extension)]);
		pityTimerArchivesTM.updateData(timerArchives[Extension.values().indexOf(extension)]);
	}
	
	public void incrementeTimer() {
		int indexExt = Extension.values().indexOf(extension);
		boosterOuverts[indexExt]++;
		
		for (int i = 0; i < timerCourants[indexExt].length; i++) {
			timerCourants[indexExt][i]++;
		}
		
		pityTimerTM.fireTableDataChanged();
	}
	
	public void decrementeTimer() {
		int indexExt = Extension.values().indexOf(extension);
		
		if (boosterOuverts[indexExt] > 0)
			boosterOuverts[indexExt]--;
		
		for (int i = 0; i < timerCourants[indexExt].length; i++) {
			if (timerCourants[indexExt][i] > 0)
				timerCourants[indexExt][i]--;
		}
		
		pityTimerTM.fireTableDataChanged();
	}
	
	
	public void archiverTimer(int indexRarete) {
		int indexExt = Extension.values().indexOf(extension);
		addDataToArchives(indexExt, indexRarete, timerCourants[indexExt][indexRarete]);
		timerCourants[indexExt][indexRarete] = 0;
		
		pityTimerTM.fireTableDataChanged();
		pityTimerArchivesTM.fireTableDataChanged();
	}
	
	
	private void addDataToArchives(int indexExt, int indexRarete, int val) {
		int[] data = timerArchives[indexExt][indexRarete];
		timerArchives[indexExt][indexRarete] = new int[data.length + 1];
		
		for (int i = 0; i < data.length; i++) {
			timerArchives[indexExt][indexRarete][i] = data[i];
		}
		
		timerArchives[indexExt][indexRarete][data.length] = val;
	}
	
	
	
	public void reloadData() {
		loadPityTimerData();
		
		pityTimerTM.updateData(timerCourants[Extension.values().indexOf(extension)]);
		pityTimerArchivesTM.updateData(timerArchives[Extension.values().indexOf(extension)]);
	}
	
	
	
	public void exportCSV() {
		try {
			FileWriter fw = new FileWriter(new File("archives.csv"));
			
			for (int i = 0; i < Extension.values().size(); i++) {
				if (Extension.values().get(i).isAventure())
					continue;
				
				fw.write("\"" + Extension.values().get(i) + "\";");
				
				for (int j = 0; j < rarete.length; j++) {
					fw.write("\"" + rarete[j] + "\";");
					
					for (int k = 0; k < timerArchives[i][j].length; k++) {
						fw.write("" + timerArchives[i][j][k] + ";");
					}
					
					fw.write("\n");
					fw.write(";");
				}
				
				fw.write("\n");
			}
			
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	public void notifyAjoutExtension() {
		int[] tempBO = boosterOuverts;
		int[][] tempTC = timerCourants;
		int[][][] tempTA = timerArchives;
		
		boosterOuverts = new int[Extension.values().size()];
		timerCourants = new int[Extension.values().size()][rarete.length];
		timerArchives = new int[Extension.values().size()][rarete.length][];
		initArchives();
		
		
		for (int i = 0; i < Extension.values().size() - 1; i++) {
			boosterOuverts[i] = tempBO[i];
			timerCourants[i] = tempTC[i];
			timerArchives[i] = tempTA[i];
		}
		
		savePityTimerData();
	}
	
	
	public void notifySuppressionExtension(int indexExt) {
		int[] tempBO = boosterOuverts;
		int[][] tempTC = timerCourants;
		int[][][] tempTA = timerArchives;
		
		boosterOuverts = new int[Extension.values().size()];
		timerCourants = new int[Extension.values().size()][rarete.length];
		timerArchives = new int[Extension.values().size()][rarete.length][];
		initArchives();
		
		
		int j = 0;
		
		for (int i = 0; i < Extension.values().size() + 1; i++) {
			if (i != indexExt) {
				boosterOuverts[i] = tempBO[i];
				timerCourants[j] = tempTC[i];
				timerArchives[j] = tempTA[i];
				j++;
			}
		}
		
		
		savePityTimerData();
	}
	
	
	
	
	
	private void loadPityTimerData() {
		boosterOuverts = new int[Extension.values().size()];
		timerCourants = new int[Extension.values().size()][rarete.length];
		timerArchives = new int[Extension.values().size()][rarete.length][];
		initArchives();
		
		try {
			ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File("pitytimer.data"))));
			
			int[] tempBO = (int[])ois.readObject();
			int[][] tempTC = (int[][])ois.readObject();
			int[][][] tempTA = (int[][][])ois.readObject();
			
			
			if (tempBO.length == Extension.values().size())
				boosterOuverts = tempBO;
			
			if (tempTC.length == Extension.values().size())
				timerCourants = tempTC;
			
			if (tempTA.length == Extension.values().size())
				timerArchives = tempTA;
			
			
			ois.close();
		} catch (FileNotFoundException e) {
			// Do nothing, not an error
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void savePityTimerData() {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File("pitytimer.data"))));
			oos.writeObject(boosterOuverts);
			oos.writeObject(timerCourants);
			oos.writeObject(timerArchives);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	private void initArchives() {
		for (int i = 0; i < Extension.values().size(); i++) {
			for (int j = 0; j < rarete.length; j++) {
				timerArchives[i][j] = new int[0];
			}
		}
	}
	
	
	
	@SuppressWarnings("unused")
	private void loadBoosterOuvertsManualy() {
		boosterOuverts = new int[Extension.values().size()];
		boosterOuverts[1] = 142;
		boosterOuverts[4] = 39;
		boosterOuverts[6] = 64;
		boosterOuverts[8] = 117;
		boosterOuverts[10] = 129;
		boosterOuverts[11] = 168;
		boosterOuverts[12] = 172;
		boosterOuverts[13] = 205;
	}
	
}




