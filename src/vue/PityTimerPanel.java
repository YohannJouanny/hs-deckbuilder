package vue;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import donnees.Classe;
import donnees.Extension;
import model.PityTimerModel;


public class PityTimerPanel extends MainFramePanel {
	private static final long serialVersionUID = 1737318795075436139L;
	
	
	private PityTimerModel model;
	private boolean archiveMode;
	
	private JComboBox<Extension> selectExt;
	private JButton incButton;
	private JButton decButton;
	private JButton exportButton;
	private JButton archivesButton;
	private JButton retourButton;
	
	
	
	public PityTimerPanel(PityTimerModel model) {
		this.model = model;
		archiveMode = false;
		initPanel();
	}
	
	
	
	public void refresh() {
		if (archiveMode)
			switchMode();
		
		selectExt.setSelectedIndex(0);
	}
	
	
	
	
	private void initPanel() {
		this.setLayout(new BorderLayout());
		
		
		// PANEL NORD : TITRE
		JPanel panTitre = new JPanel();
		JLabel titre = new JLabel("Pity Timer");
		titre.setFont(new Font("Arial", Font.BOLD, 28));
		panTitre.add(titre);
		this.add(panTitre, BorderLayout.NORTH);
		
		
		
		
		
		// PANEL EST : EXTENSION ET BOUTONS
		JPanel panControl = new JPanel(new BorderLayout());
		
		
		// Panel Est : Critere extension
		JPanel panCritExt = new JPanel();
		panCritExt.setLayout(new BoxLayout(panCritExt, BoxLayout.Y_AXIS));
		
		JLabel selectExtLb = new JLabel("Extension :");
		selectExtLb.setAlignmentX(CENTER_ALIGNMENT);
		panCritExt.add(selectExtLb);
		
		JPanel panCritExtCB = new JPanel();
		selectExt = new JComboBox<Extension>();
		for (Extension ext : Extension.values()) {
			if (!ext.isAventure() && ext.isStandard())
				selectExt.addItem(ext);
		}
		selectExt.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				model.changeExtension((Extension)selectExt.getSelectedItem());
			}
		});
		panCritExtCB.add(selectExt);
		panCritExt.add(panCritExtCB);
		
		panControl.add(panCritExt, BorderLayout.NORTH);
		
		
		// Panel Est : Boutons Timer
		JPanel panBoutonsTimer = new JPanel();
		
		incButton = new JButton("Incrémenter le timer");
		incButton.setFont(new Font("Arial", Font.BOLD, 16));
		incButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				model.incrementeTimer();
			}
		});
		panBoutonsTimer.add(incButton);
		
		decButton = new JButton("Décrémenter le timer");
		decButton.setFont(new Font("Arial", Font.BOLD, 16));
		panBoutonsTimer.add(decButton);
		
		panControl.add(panBoutonsTimer, BorderLayout.CENTER);
		
		
		// Panel Est : Boutons switch
		JPanel panBoutonsSwitch = new JPanel();
		
		exportButton = new JButton("Exporter en CVS");
		exportButton.setFont(new Font("Arial", Font.BOLD, 16));
		panBoutonsSwitch.add(exportButton);
		exportButton.setVisible(false);
		
		archivesButton = new JButton("Archives");
		archivesButton.setFont(new Font("Arial", Font.BOLD, 16));
		archivesButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				switchMode();
			}
		});
		panBoutonsSwitch.add(archivesButton);
		
		retourButton = new JButton("Retour");
		retourButton.setFont(new Font("Arial", Font.BOLD, 16));
		retourButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				switchMode();
			}
		});
		panBoutonsSwitch.add(retourButton);
		retourButton.setVisible(false);
		
		panControl.add(panBoutonsSwitch, BorderLayout.SOUTH);
		
		
		
		this.add(panControl, BorderLayout.EAST);
		
		
		
		
		
		
		
		
		// PANEL CENTRAL : TABLEAU DE STATS
		JTable tableauStats = new JTable(model.getPityTimerTableModel());
		tableauStats.setCellSelectionEnabled(false);
		tableauStats.setRowHeight(30);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		tableauStats.setDefaultRenderer(String.class, centerRenderer);
		
		this.add(new JScrollPane(tableauStats), BorderLayout.CENTER);
	}
	
	
	
	
	private void switchMode() {
		if (archiveMode) {
			archiveMode = false;
			incButton.setVisible(true);
			decButton.setVisible(true);
			exportButton.setVisible(false);
			archivesButton.setVisible(true);
			retourButton.setVisible(false);
		}
		else {
			archiveMode = true;
			incButton.setVisible(false);
			decButton.setVisible(false);
			exportButton.setVisible(true);
			archivesButton.setVisible(false);
			retourButton.setVisible(true);
		}
	}
	

}




