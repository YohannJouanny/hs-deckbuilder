package vue;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import app.Main;
import model.Model;
import vue.Vue.Ecran;


public class MainFrame extends JFrame {
	private static final long serialVersionUID = 299928316462382629L;
	

	public MainFrame(Vue vue, Model model) {
		this.setTitle("Hearthstone Stats");
		this.setSize(900, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		
		
		this.addWindowListener(new WindowListener(){
			public void windowActivated(WindowEvent arg0) {}
			public void windowClosed(WindowEvent arg0) {}
			
			
			public void windowClosing(WindowEvent e) {
				vue.saveModel();
			}
			
			public void windowDeactivated(WindowEvent arg0) {}
			public void windowDeiconified(WindowEvent arg0) {}
			public void windowIconified(WindowEvent arg0) {}
			public void windowOpened(WindowEvent arg0) {}
		});
		
		
		
		JMenuBar menubar = new JMenuBar();
		
		JMenu menuFenetre = new JMenu("Fen�tre");
		JMenuItem itemListe = new JMenuItem("Liste des cartes");
		JMenuItem itemStats = new JMenuItem("Collection");
		JMenuItem itemStatsBooster = new JMenuItem("Statistiques de booster");
		JMenuItem itemPityTimer = new JMenuItem("Pity timer");
		JMenuItem itemPreferences = new JMenuItem("Pr�f�rences");
		JMenuItem itemQuitter = new JMenuItem("Quitter");
		
		
		itemListe.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				vue.changeEcran(Ecran.Liste);
			}
		});
		
		itemStats.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				vue.changeEcran(Ecran.Stats);
			}
		});
		
		itemStatsBooster.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				vue.changeEcran(Ecran.StatsBooster);
			}
		});
		
		itemPityTimer.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				vue.changeEcran(Ecran.PityTimer);
			}
		});
		
		itemPreferences.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				PreferencesPopup popup = new PreferencesPopup(null, model.getConfig());
				
				if(popup.showPopUp()) {
					model.changeConfig(popup.getConfig());
					vue.rebuildPanels();
				}
			}
		});
		
		itemQuitter.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				vue.saveModel();
				System.exit(0);
			}
		});
		
		
		menuFenetre.add(itemListe);
		menuFenetre.add(itemStats);
		menuFenetre.add(itemStatsBooster);
		menuFenetre.add(itemPityTimer);
		menuFenetre.addSeparator();
		menuFenetre.add(itemPreferences);
		menuFenetre.addSeparator();
		menuFenetre.add(itemQuitter);
		menubar.add(menuFenetre);
		
		
		
		JMenu menuExt = new JMenu("Extension");
		JMenuItem itemAddExt = new JMenuItem("Ajouter");
		JMenuItem itemEditExt = new JMenuItem("Editer");
		JMenuItem itemDelExt = new JMenuItem("Supprimer");
		
		itemAddExt.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				CreerExtensionPopUp pop = new CreerExtensionPopUp(null);
				
				if(pop.showPopUp())
				{
					model.creerExtension(pop.getNomExtension(), pop.getIsAventure(), pop.getIsStandard());
					vue.rebuildPanels();
				}
			}
		});
		
		itemEditExt.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				EditerExtensionPopUp pop = new EditerExtensionPopUp(null);
				
				if(pop.showPopUp())
				{
					model.editerExtension(pop.getExtension(), pop.getIsAventure(), pop.getIsStandard());
					vue.rebuildPanels();
				}
			}
		});
		
		itemDelExt.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				SupprimerExtensionPopUp pop = new SupprimerExtensionPopUp(null);
				
				if(pop.showPopUp())
				{
					model.supprimerExtention(pop.getExtension());
					vue.rebuildPanels();
				}
			}
		});
		
		menuExt.add(itemAddExt);
		menuExt.add(itemEditExt);
		menuExt.add(itemDelExt);
		menubar.add(menuExt);
		
		JLabel numVersionLb = new JLabel("Version " + Main.VERSION + "    ");
		numVersionLb.setFont(new Font("Arial", Font.PLAIN, 12));
		menubar.add(Box.createHorizontalGlue());
		menubar.add(numVersionLb);
		
		this.setJMenuBar(menubar);
	}
}




