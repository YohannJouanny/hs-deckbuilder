package model;

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
	
	
	
	private Model model;
	private PityTimerTableModel pityTimerTM;
	
	private int[][] timerCourants;
	private Extension extension;
	
	
	public PityTimerModel(Model model) {
		this.model = model;
		this.timerCourants = new int[Extension.values().size()][rarete.length];
		this.extension = Extension.values().get(0);
		
		loadTimerCourants();
		pityTimerTM = new PityTimerTableModel(timerCourants[Extension.values().indexOf(extension)]);
	}
	
	
	
	public PityTimerTableModel getPityTimerTableModel() {
		return pityTimerTM;
	}
	
	
	
	public void changeExtension(Extension ext) {
		extension = ext;
		pityTimerTM.updateData(timerCourants[Extension.values().indexOf(extension)]);
	}
	
	public void incrementeTimer() {
		int indexExt = Extension.values().indexOf(extension);
		
		for (int i = 0; i < rarete.length; i++) {
			timerCourants[indexExt][i]++;
		}
		
		pityTimerTM.fireTableDataChanged();
		//pityTimerTM.updateData(timerCourants);
	}
	
	
	public void archiverTimer(int indexRarete) {
		int indexExt = Extension.values().indexOf(extension);
		timerCourants[indexExt][indexRarete] = 0;
		
		pityTimerTM.fireTableDataChanged();
		
		//TODO
	}
	
	
	
	
	public void loadTimerCourants() {
		for (int i = 0; i < Extension.values().size(); i++) {
			for (int j = 0; j < rarete.length; j++) {
				timerCourants[i][j] = 0;
			}
		}
	}
	
}




