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
		rarete[2] = "Commune dorée";
		rarete[3] = "Rare dorée";
		rarete[4] = "Epique dorée";
		rarete[5] = "Legendaire dorée";
		
		timerMax[0] = 10;
		timerMax[1] = 39;
		timerMax[2] = 24;
		timerMax[3] = 28;
		timerMax[4] = 137;
		timerMax[5] = 305;
	}
	
	
	
	private PityTimerTableModel pityTimerTM;
	private PityTimerArchivesTableModel pityTimerArchivesTM;
	
	private Extension extension;
	private int[][] timerCourants;
	private int[][][] timerArchives;
	
	
	public PityTimerModel() {
		this.extension = Extension.values().get(0);
		this.timerCourants = new int[Extension.values().size()][rarete.length];
		this.timerArchives = new int[Extension.values().size()][rarete.length][];
		loadPityTimerData();
		
		pityTimerTM = new PityTimerTableModel(timerCourants[Extension.values().indexOf(extension)]);
		pityTimerArchivesTM = new PityTimerArchivesTableModel(timerArchives[Extension.values().indexOf(extension)]);
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
		
		for (int i = 0; i < timerCourants[indexExt].length; i++) {
			timerCourants[indexExt][i]++;
		}
		
		pityTimerTM.fireTableDataChanged();
	}
	
	public void decrementeTimer() {
		int indexExt = Extension.values().indexOf(extension);
		
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
		this.timerCourants = new int[Extension.values().size()][rarete.length];
		this.timerArchives = new int[Extension.values().size()][rarete.length][];
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
	
	
	
	
	
	private void loadPityTimerData() {
		try {
			ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File("pitytimer.data"))));
			timerCourants = (int[][])ois.readObject();
			timerArchives = (int[][][])ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			initArchives();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void savePityTimerData() {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File("pitytimer.data"))));
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
	private void loadManualy() {
		//Classique
		timerCourants[1][0] = 0;
		timerCourants[1][1] = 0;
		timerCourants[1][2] = 0;
		timerCourants[1][3] = 0;
		timerCourants[1][4] = 0;
		timerCourants[1][5] = 0;
		
		
		initArchives();
		
		timerArchives[1][1] = new int[1];
		timerArchives[1][1][0] = 0;
	}
	
	
	
	
	
}




