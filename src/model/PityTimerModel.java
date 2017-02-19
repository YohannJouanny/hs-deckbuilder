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
		//loadManualy();
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
		int[][] tempTC = timerCourants;
		int[][][] tempTA = timerArchives;
		
		timerCourants = new int[Extension.values().size()][rarete.length];
		timerArchives = new int[Extension.values().size()][rarete.length][];
		initArchives();
		
		
		for (int i = 0; i < Extension.values().size() - 1; i++) {
			timerCourants[i] = tempTC[i];
			timerArchives[i] = tempTA[i];
		}
		
		savePityTimerData();
	}
	
	
	public void notifySuppressionExtension(int indexExt) {
		int[][] tempTC = timerCourants;
		int[][][] tempTA = timerArchives;
		
		timerCourants = new int[Extension.values().size()][rarete.length];
		timerArchives = new int[Extension.values().size()][rarete.length][];
		initArchives();
		
		
		int j = 0;
		
		for (int i = 0; i < Extension.values().size() + 1; i++) {
			if (i != indexExt) {
				timerCourants[j] = tempTC[i];
				timerArchives[j] = tempTA[i];
				j++;
			}
		}
		
		
		savePityTimerData();
	}
	
	
	
	
	
	private void loadPityTimerData() {
		timerCourants = new int[Extension.values().size()][rarete.length];
		timerArchives = new int[Extension.values().size()][rarete.length][];
		initArchives();
		
		try {
			ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File("pitytimer.data"))));
			
			int[][] tempTC = (int[][])ois.readObject();
			int[][][] tempTA = (int[][][])ois.readObject();
			
			
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
		timerCourants = new int[Extension.values().size()][rarete.length];
		timerArchives = new int[Extension.values().size()][rarete.length][];
		initArchives();
		
		
		
		//Classique
		timerCourants[1][0] = 7;
		timerCourants[1][1] = 11;
		timerCourants[1][2] = 16;
		timerCourants[1][3] = 1;
		timerCourants[1][4] = 58;
		timerCourants[1][5] = 13;
		
		
		timerArchives[1][0] = new int[15];
		timerArchives[1][0][0] = 7;
		timerArchives[1][0][1] = 7;
		timerArchives[1][0][2] = 6;
		timerArchives[1][0][3] = 4;
		timerArchives[1][0][4] = 2;
		timerArchives[1][0][5] = 7;
		timerArchives[1][0][6] = 2;
		timerArchives[1][0][7] = 2;
		timerArchives[1][0][8] = 8;
		timerArchives[1][0][9] = 3;
		timerArchives[1][0][10] = 3;
		timerArchives[1][0][11] = 6;
		timerArchives[1][0][12] = 0;
		timerArchives[1][0][13] = 10;
		timerArchives[1][0][14] = 8;
		timerArchives[1][1] = new int[6];
		timerArchives[1][1][0] = 5;
		timerArchives[1][1][1] = 6;
		timerArchives[1][1][2] = 23;
		timerArchives[1][1][3] = 11;
		timerArchives[1][1][4] = 15;
		timerArchives[1][1][5] = 5;
		timerArchives[1][2] = new int[5];
		timerArchives[1][2][0] = 8;
		timerArchives[1][2][1] = 6;
		timerArchives[1][2][2] = 18;
		timerArchives[1][2][3] = 21;
		timerArchives[1][2][4] = 17;
		timerArchives[1][3] = new int[5];
		timerArchives[1][3][0] = 22;
		timerArchives[1][3][1] = 12;
		timerArchives[1][3][2] = 26;
		timerArchives[1][3][3] = 7;
		timerArchives[1][3][4] = 19;
		timerArchives[1][4] = new int[1];
		timerArchives[1][4][0] = 29;
		timerArchives[1][5] = new int[1];
		timerArchives[1][5][0] = 74;
		
		
		
		//GVG
		timerCourants[3][0] = 1;
		timerCourants[3][1] = 13;
		timerCourants[3][2] = 4;
		timerCourants[3][3] = 8;
		timerCourants[3][4] = 39;
		timerCourants[3][5] = 7;
		
		
		timerArchives[3][0] = new int[5];
		timerArchives[3][0][0] = 8;
		timerArchives[3][0][1] = 8;
		timerArchives[3][0][2] = 9;
		timerArchives[3][0][3] = 3;
		timerArchives[3][0][4] = 6;
		timerArchives[3][1] = new int[0];
		timerArchives[3][2] = new int[2];
		timerArchives[3][2][0] = 17;
		timerArchives[3][2][1] = 11;
		timerArchives[3][3] = new int[1];
		timerArchives[3][3][0] = 22;
		timerArchives[3][4] = new int[0];
		timerArchives[3][5] = new int[0];
		
		
		
		//TGT
		timerCourants[5][0] = 8;
		timerCourants[5][1] = 0;
		timerCourants[5][2] = 10;
		timerCourants[5][3] = 3;
		timerCourants[5][4] = 37;
		timerCourants[5][5] = 64;
		
		
		timerArchives[5][0] = new int[10];
		timerArchives[5][0][0] = 8;
		timerArchives[5][0][1] = 9;
		timerArchives[5][0][2] = 9;
		timerArchives[5][0][3] = 1;
		timerArchives[5][0][4] = 9;
		timerArchives[5][0][5] = 3;
		timerArchives[5][0][6] = 4;
		timerArchives[5][0][7] = 4;
		timerArchives[5][0][8] = 1;
		timerArchives[5][0][9] = 8;
		timerArchives[5][1] = new int[4];
		timerArchives[5][1][0] = 16;
		timerArchives[5][1][1] = 10;
		timerArchives[5][1][2] = 21;
		timerArchives[5][1][3] = 17;
		timerArchives[5][2] = new int[5];
		timerArchives[5][2][0] = 1;
		timerArchives[5][2][1] = 9;
		timerArchives[5][2][2] = 14;
		timerArchives[5][2][3] = 10;
		timerArchives[5][2][4] = 20;
		timerArchives[5][3] = new int[7];
		timerArchives[5][3][0] = 4;
		timerArchives[5][3][1] = 6;
		timerArchives[5][3][2] = 3;
		timerArchives[5][3][3] = 11;
		timerArchives[5][3][4] = 23;
		timerArchives[5][3][5] = 5;
		timerArchives[5][3][6] = 9;
		timerArchives[5][4] = new int[1];
		timerArchives[5][4][0] = 27;
		timerArchives[5][5] = new int[0];

		
		
		//OG
		timerCourants[7][0] = 1;
		timerCourants[7][1] = 1;
		timerCourants[7][2] = 12;
		timerCourants[7][3] = 20;
		timerCourants[7][4] = 80;
		timerCourants[7][5] = 113;
		
		
		timerArchives[7][0] = new int[22];
		timerArchives[7][0][0] = 6;
		timerArchives[7][0][1] = 6;
		timerArchives[7][0][2] = 8;
		timerArchives[7][0][3] = 5;
		timerArchives[7][0][4] = 9;
		timerArchives[7][0][5] = 8;
		timerArchives[7][0][6] = 1;
		timerArchives[7][0][7] = 5;
		timerArchives[7][0][8] = 6;
		timerArchives[7][0][9] = 0;
		timerArchives[7][0][10] = 8;
		timerArchives[7][0][11] = 4;
		timerArchives[7][0][12] = 8;
		timerArchives[7][0][13] = 3;
		timerArchives[7][0][14] = 3;
		timerArchives[7][0][15] = 3;
		timerArchives[7][0][16] = 7;
		timerArchives[7][0][17] = 8;
		timerArchives[7][0][18] = 7;
		timerArchives[7][0][19] = 1;
		timerArchives[7][0][20] = 0;
		timerArchives[7][0][21] = 6;
		timerArchives[7][1] = new int[5];
		timerArchives[7][1][0] = 18;
		timerArchives[7][1][1] = 13;
		timerArchives[7][1][2] = 22;
		timerArchives[7][1][3] = 33;
		timerArchives[7][1][4] = 26;
		timerArchives[7][2] = new int[9];
		timerArchives[7][2][0] = 8;
		timerArchives[7][2][1] = 7;
		timerArchives[7][2][2] = 14;
		timerArchives[7][2][3] = 17;
		timerArchives[7][2][4] = 13;
		timerArchives[7][2][5] = 7;
		timerArchives[7][2][6] = 7;
		timerArchives[7][2][7] = 5;
		timerArchives[7][2][8] = 23;
		timerArchives[7][3] = new int[6];
		timerArchives[7][3][0] = 22;
		timerArchives[7][3][1] = 16;
		timerArchives[7][3][2] = 18;
		timerArchives[7][3][3] = 7;
		timerArchives[7][3][4] = 15;
		timerArchives[7][3][5] = 15;
		timerArchives[7][4] = new int[1];
		timerArchives[7][4][0] = 33;
		timerArchives[7][5] = new int[0];
		
		
		
		//Gadgetzan
		timerCourants[9][0] = 3;
		timerCourants[9][1] = 15;
		timerCourants[9][2] = 12;
		timerCourants[9][3] = 12;
		timerCourants[9][4] = 2;
		timerCourants[9][5] = 109;
		
		
		timerArchives[9][0] = new int[17];
		timerArchives[9][0][0] = 7;
		timerArchives[9][0][1] = 8;
		timerArchives[9][0][2] = 7;
		timerArchives[9][0][3] = 7;
		timerArchives[9][0][4] = 8;
		timerArchives[9][0][5] = 8;
		timerArchives[9][0][6] = 9;
		timerArchives[9][0][7] = 7;
		timerArchives[9][0][8] = 1;
		timerArchives[9][0][9] = 0;
		timerArchives[9][0][10] = 4;
		timerArchives[9][0][11] = 2;
		timerArchives[9][0][12] = 5;
		timerArchives[9][0][13] = 9;
		timerArchives[9][0][14] = 9;
		timerArchives[9][0][15] = 6;
		timerArchives[9][0][16] = 9;
		timerArchives[9][1] = new int[7];
		timerArchives[9][1][0] = 1;
		timerArchives[9][1][1] = 9;
		timerArchives[9][1][2] = 12;
		timerArchives[9][1][3] = 36;
		timerArchives[9][1][4] = 1;
		timerArchives[9][1][5] = 30;
		timerArchives[9][1][6] = 5;
		timerArchives[9][2] = new int[6];
		timerArchives[9][2][0] = 40;
		timerArchives[9][2][1] = 15;
		timerArchives[9][2][2] = 3;
		timerArchives[9][2][3] = 14;
		timerArchives[9][2][4] = 22;
		timerArchives[9][2][5] = 3;
		timerArchives[9][3] = new int[6];
		timerArchives[9][3][0] = 19;
		timerArchives[9][3][1] = 6;
		timerArchives[9][3][2] = 9;
		timerArchives[9][3][3] = 19;
		timerArchives[9][3][4] = 27;
		timerArchives[9][3][5] = 17;
		timerArchives[9][4] = new int[2];
		timerArchives[9][4][0] = 61;
		timerArchives[9][4][1] = 46;
		timerArchives[9][5] = new int[0];
	}
	
	
	
	
	
}




