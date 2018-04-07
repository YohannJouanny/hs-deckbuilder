package vue;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import donnees.Extension;
import model.StatsModel;


public class StatsPanel extends MainFramePanel {
	private static final long serialVersionUID = 3243027104317707040L;
	

	private StatsModel model;
	
	private ArrayList<JCheckBox> listeExtChB;
	private JCheckBox extAllChB;
	private JCheckBox carteNChB;
	private JCheckBox carteDChB;
	
	
	
	public StatsPanel(StatsModel model) {
		this.model = model;
		initPanel();
	}

	
	
	public void refresh() {
		extAllChB.setSelected(true);
		for (JCheckBox extChB : listeExtChB) {
			extChB.setSelected(true);
		}
		carteNChB.setSelected(true);
		carteDChB.setSelected(false);
		
		model.changeCriteres(getCriteresExtension(), carteNChB.isSelected(), carteDChB.isSelected());
	}
	
	
	
	private void initPanel() {
		this.setLayout(new BorderLayout());
		
		
		// PANEL NORD : TITRE
		JPanel panTitre = new JPanel();
		JLabel titre = new JLabel("Collection");
		titre.setFont(new Font("Arial", Font.BOLD, 28));
		panTitre.add(titre);
		this.add(panTitre, BorderLayout.NORTH);
		
		
		
		
		
		// PANEL EST : CRITERES DE SELECTION
		JPanel panCritere = new JPanel();
		panCritere.setLayout(new BorderLayout());
		
		
		// Extensions
		JPanel panCritExt = new JPanel();
		panCritExt.setLayout(new BoxLayout(panCritExt, BoxLayout.Y_AXIS));
		
		JPanel panCritExtLb = new JPanel();
		panCritExtLb.setLayout(new BoxLayout(panCritExtLb, BoxLayout.Y_AXIS));
		JLabel extensionLb = new JLabel("Extensions : ");
		extensionLb.setAlignmentX(Component.CENTER_ALIGNMENT);
		panCritExtLb.add(extensionLb);
		JLabel critExtVoid1 = new JLabel(" ");
		panCritExtLb.add(critExtVoid1);
		panCritExt.add(panCritExtLb);
		
		
		JPanel panCritExtCb = new JPanel();
		panCritExtCb.setLayout(new BoxLayout(panCritExtCb, BoxLayout.Y_AXIS));
		
		extAllChB = new JCheckBox(Extension.ALL.toString() + "  "); // Espace avec la check box
		extAllChB.setHorizontalTextPosition(JCheckBox.LEFT);
		extAllChB.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panCritExtCb.add(extAllChB);
		
		listeExtChB = new ArrayList<JCheckBox>();
		for (Extension ext : Extension.values()) {
			JCheckBox extChB = new JCheckBox(ext.toString() + "  ");
			extChB.setHorizontalTextPosition(JCheckBox.LEFT);
			extChB.setAlignmentX(Component.RIGHT_ALIGNMENT);
			extChB.addActionListener(createActualiseTableauActionListener());
			extChB.addActionListener(createDeselectAllExtActionListener());
			panCritExtCb.add(extChB);
			listeExtChB.add(extChB);
		}
		
		extAllChB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if (extAllChB.isSelected()) {
					for (JCheckBox extChB : listeExtChB) {
						extChB.setSelected(true);
					}
				}
				else {
					for (JCheckBox extChB : listeExtChB) {
						extChB.setSelected(false);
					}
				}
				
				model.changeCriteres(getCriteresExtension(), carteNChB.isSelected(), carteDChB.isSelected());
			}
		});
				
		JScrollPane panExtScroller = new JScrollPane(panCritExtCb);
		panExtScroller.setPreferredSize(new Dimension(200, 330));
		panExtScroller.setBorder(null);
		panExtScroller.getVerticalScrollBar().setUnitIncrement(10);
		panCritExt.add(panExtScroller);
		
        panCritere.add(panCritExt, BorderLayout.NORTH);
        
        
		// Type de carte
		JPanel panCritCarte = new JPanel();
		panCritCarte.setLayout(new BoxLayout(panCritCarte, BoxLayout.Y_AXIS));
		
		JLabel carteLb = new JLabel("Cartes :                   "); // Allignement au centre ne fonctionne pas
		carteLb.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panCritCarte.add(carteLb);
		
		JLabel critCarteVoid1 = new JLabel("   "); // Ajout d'un offset à droite
		critCarteVoid1.setAlignmentX(Component.LEFT_ALIGNMENT);
		panCritCarte.add(critCarteVoid1);
		
		carteNChB = new JCheckBox("Normales   ");
		carteNChB.setHorizontalTextPosition(JCheckBox.LEFT);
		carteNChB.setAlignmentX(Component.RIGHT_ALIGNMENT);
		carteNChB.addActionListener(createActualiseTableauActionListener());
		panCritCarte.add(carteNChB);
		
		carteDChB = new JCheckBox("Dorées   ");
		carteDChB.setHorizontalTextPosition(JCheckBox.LEFT);
		carteDChB.setAlignmentX(Component.RIGHT_ALIGNMENT);
		carteDChB.addActionListener(createActualiseTableauActionListener());
		panCritCarte.add(carteDChB);
		
		JLabel critCarteVoid2 = new JLabel(" ");
		panCritCarte.add(critCarteVoid2);
		
		panCritere.add(panCritCarte, BorderLayout.SOUTH);
		
		this.add(panCritere, BorderLayout.EAST);
		
		
		
		
		// PANEL CENTRAL : TABLEAU DE STATS
		JTable tableauStats = new JTable(model.getStatsTableModel());
		tableauStats.setCellSelectionEnabled(false);
		tableauStats.setRowHeight(43);
		
		StatsStringTableCell stringRenderer = new StatsStringTableCell();
		tableauStats.setDefaultRenderer(String.class, stringRenderer);
		
		this.add(new JScrollPane(tableauStats), BorderLayout.CENTER);
	}
	
	
	
	
	private ActionListener createActualiseTableauActionListener() {
		return new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				model.changeCriteres(getCriteresExtension(), carteNChB.isSelected(), carteDChB.isSelected());
			}
		};
	}
	
	private ActionListener createDeselectAllExtActionListener() {
		return new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if (extAllChB.isSelected())
					extAllChB.setSelected(false);

				if (!extAllChB.isSelected() && isAllExtSelected())
					extAllChB.setSelected(true);
			}
		};
	}
	
	private boolean isAllExtSelected() {
		for (JCheckBox extChB : listeExtChB) {
			if (!extChB.isSelected()) {
				return false;
			}
		}
		
		return true;
	}
	
	
	private ArrayList<Extension> getCriteresExtension() {
		ArrayList<Extension> exts = new ArrayList<Extension>();
		Extension[] tabExt = Extension.values().toArray(new Extension[0]);
		
		int i = 0;
		
		for (JCheckBox extChB : listeExtChB) {
			if (extChB.isSelected())
				exts.add(tabExt[i]);
			
			i++;
		}
		
		return exts;
	}
}








