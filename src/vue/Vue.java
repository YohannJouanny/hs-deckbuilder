package vue;

import model.Model;


public class Vue {
	
	public enum Ecran {
		Liste,
		Stats,
		PityTimer;
	}
	
	
	private Model model;
	private MainFrame fenetre;
	private MainFramePanel panelActif;
	private ListeCartesPanel panelListe;
	private StatsPanel panelStats;
	private PityTimerPanel panelPityTimer;
	
	
	
	public Vue(Model model) {
		this.model = model;
		fenetre = new MainFrame(this, model);
		buildPanels();
		
		changeEcran(Ecran.Liste);
		fenetre.setVisible(true);
	}
	
	
	
	private void buildPanels() {
		panelListe = new ListeCartesPanel(model.getListeCartesModel());
		panelStats = new StatsPanel(model.getStatsModel());
		panelPityTimer = new PityTimerPanel(model.getPityTimerModel());
	}
	
	public void rebuildPanels() {
		buildPanels();
		
		if(panelActif.getClass() == ListeCartesPanel.class) 
			changeEcran(Ecran.Liste);
		else if (panelActif.getClass() == StatsPanel.class) 
			changeEcran(Ecran.Stats);
		else if (panelActif.getClass() == PityTimerPanel.class) 
			changeEcran(Ecran.PityTimer);
		else 
			changeEcran(Ecran.Liste);
	}
	
	
	public void changeEcran(Ecran ecran) {
		switch(ecran) {
			case Liste:
				panelActif = panelListe;
				break;
			case Stats:
				panelActif = panelStats;
				break;
			case PityTimer:
				panelActif = panelPityTimer;
				break;
			default:
				break;
		}
		
		panelActif.refresh();
		fenetre.setContentPane(panelActif);
		panelActif.updateUI();
	}
	
	
	
	
	
	
	public void saveModel() {
		model.saveModel();
	}
	
	
}


