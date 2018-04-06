package vue;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import donnees.Extension;
import model.PityTimerModel;


public class PityTimerPanel extends MainFramePanel {
	private static final long serialVersionUID = 1737318795075436139L;
	
	
	private PityTimerModel model;
	private boolean archiveMode;
	
	private JScrollPane tableauTimerCourants;
	private JScrollPane tableauTimerArchives;
	private JComboBox<Extension> selectExt;
	private JButton incButton;
	private JButton decButton;
	private JButton resetButton;
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
			if (!ext.isAventure())
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
		JPanel panBoutonsTimer = new JPanel(new GridLayout(10, 1));
		
		JPanel panVoidButton = new JPanel();
		panBoutonsTimer.add(panVoidButton);
		
		JPanel panVoid2Button = new JPanel();
		panBoutonsTimer.add(panVoid2Button);
		
		JPanel panIncButton = new JPanel();
		incButton = new JButton("Timer +");
		incButton.setFont(new Font("Arial", Font.BOLD, 20));
		incButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				model.incrementeTimer();
			}
		});
		panIncButton.add(incButton);
		panBoutonsTimer.add(panIncButton);
		
		JPanel panVoid3Button = new JPanel();
		panBoutonsTimer.add(panVoid3Button);
		
		JPanel panDecButton = new JPanel();
		decButton = new JButton("Timer -");
		decButton.setFont(new Font("Arial", Font.BOLD, 16));
		decButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				model.decrementeTimer();
			}
		});
		panDecButton.add(decButton);
		panBoutonsTimer.add(panDecButton);
		
		JPanel panRestButton = new JPanel();
		resetButton = new JButton("Annuler");
		resetButton.setFont(new Font("Arial", Font.BOLD, 16));
		resetButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				model.reloadData();
			}
		});
		panRestButton.add(resetButton);
		panBoutonsTimer.add(panRestButton);
		
		panControl.add(panBoutonsTimer, BorderLayout.CENTER);
		
		
		// Panel Est : Boutons switch
		JPanel panBoutonsSwitch = new JPanel();
		panBoutonsSwitch.setLayout(new BoxLayout(panBoutonsSwitch, BoxLayout.Y_AXIS));
		
		JPanel panExportButton = new JPanel();
		exportButton = new JButton("Exporter CSV");
		exportButton.setFont(new Font("Arial", Font.BOLD, 16));
		exportButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				model.exportCSV();
			}
		});
		panExportButton.add(exportButton);
		panBoutonsSwitch.add(panExportButton);
		exportButton.setVisible(false);
		
		JPanel panArchivesButton = new JPanel();
		archivesButton = new JButton("Archives");
		archivesButton.setFont(new Font("Arial", Font.BOLD, 16));
		archivesButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				switchMode();
			}
		});
		panArchivesButton.add(archivesButton);
		panBoutonsSwitch.add(panArchivesButton);
		
		JPanel panRetourButton = new JPanel();
		retourButton = new JButton("Retour");
		retourButton.setFont(new Font("Arial", Font.BOLD, 16));
		retourButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				switchMode();
			}
		});
		panRetourButton.add(retourButton);
		panBoutonsSwitch.add(panRetourButton);
		retourButton.setVisible(false);
		
		panControl.add(panBoutonsSwitch, BorderLayout.SOUTH);
		
		
		
		this.add(panControl, BorderLayout.EAST);
		
		
		
		
		
		
		
		
		// PANEL CENTRAL : TABLEAU DE PITY TIMER - COURANT
		JTable tableTimerCourants = new JTable(model.getPityTimerTableModel());
		tableTimerCourants.setCellSelectionEnabled(false);
		tableTimerCourants.setRowHeight(37);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		tableTimerCourants.setDefaultRenderer(String.class, centerRenderer);
		
		ArchiverTimerTableCell tableCellArchiver = new ArchiverTimerTableCell(model);
		tableTimerCourants.setDefaultRenderer(int.class, tableCellArchiver);
		tableTimerCourants.setDefaultEditor(int.class, tableCellArchiver);
		
		tableauTimerCourants = new JScrollPane(tableTimerCourants);
		this.add(tableauTimerCourants, BorderLayout.CENTER);
		
		
		// PANEL CENTRAL : TABLEAU DE PITY TIMER - ARCHIVES
		JTable tableTimerArchives = new JTable(model.getPityTimerArchivesTableModel());
		tableTimerArchives.setCellSelectionEnabled(false);
		tableTimerArchives.setRowHeight(30);
		tableTimerArchives.setDefaultRenderer(String.class, new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				super.getTableCellRendererComponent(tableTimerArchives, value, isSelected, hasFocus, row, column);
				this.setHorizontalAlignment(JLabel.CENTER);
				
				if (row == 0)  {
					this.setFont(new Font("Arial", Font.BOLD, 14));
				}
				return this;
			}
		});
		
		tableauTimerArchives = new JScrollPane(tableTimerArchives);
	}
	
	
	
	
	private void switchMode() {
		if (archiveMode) {
			archiveMode = false;
			incButton.setVisible(true);
			decButton.setVisible(true);
			resetButton.setVisible(true);
			exportButton.setVisible(false);
			archivesButton.setVisible(true);
			retourButton.setVisible(false);
			this.remove(tableauTimerArchives);
			this.add(tableauTimerCourants, BorderLayout.CENTER);
		}
		else {
			archiveMode = true;
			incButton.setVisible(false);
			decButton.setVisible(false);
			resetButton.setVisible(false);
			exportButton.setVisible(true);
			archivesButton.setVisible(false);
			retourButton.setVisible(true);
			this.remove(tableauTimerCourants);
			this.add(tableauTimerArchives, BorderLayout.CENTER);
		}
	}
	

}




