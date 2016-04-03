package vue;

import java.awt.BorderLayout;
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
import javax.swing.table.DefaultTableCellRenderer;

import model.StatsModel;
import donnees.Extension;


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
		panCritere.setLayout(new BoxLayout(panCritere, BoxLayout.Y_AXIS));
		
		
		JLabel critEspace1 = new JLabel("     "); // Allignement par rapport à la bordure droite
		critEspace1.setAlignmentX(LEFT_ALIGNMENT);
		panCritere.add(critEspace1);
		
		JLabel critEspace2 = new JLabel(" ");
		panCritere.add(critEspace2);
		
		
		JPanel panCritExt = new JPanel();
		panCritExt.setLayout(new BoxLayout(panCritExt, BoxLayout.Y_AXIS));
		
		JLabel extensionLb = new JLabel("Extensions :       "); // Allignement par rapport aux check box
		extensionLb.setAlignmentX(RIGHT_ALIGNMENT);
		panCritExt.add(extensionLb);
		
		
		// Extensions
		extAllChB = new JCheckBox(Extension.ALL.toString() + "  "); // Espace avec la check box
		extAllChB.setHorizontalTextPosition(JCheckBox.LEFT);
		extAllChB.setAlignmentX(RIGHT_ALIGNMENT);
		panCritExt.add(extAllChB);
		
		listeExtChB = new ArrayList<JCheckBox>();
		for (Extension ext : Extension.values()) {
			JCheckBox extChB = new JCheckBox(ext.toString() + "  ");
			extChB.setHorizontalTextPosition(JCheckBox.LEFT);
			extChB.setAlignmentX(RIGHT_ALIGNMENT);
			extChB.addActionListener(createActualiseTableauActionListener());
			extChB.addActionListener(createDeselectAllExtActionListener());
			panCritExt.add(extChB);
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
		
		panCritere.add(panCritExt);
		
		
		JLabel critEspace3 = new JLabel(" ");
		panCritere.add(critEspace3);
		
		JLabel critEspace4 = new JLabel(" ");
		panCritere.add(critEspace4);
		
		
		JPanel panCritCarte = new JPanel();
		panCritCarte.setLayout(new BoxLayout(panCritCarte, BoxLayout.Y_AXIS));
		
		JLabel carteLb = new JLabel("Cartes :      "); // Allignement par rapport aux check box
		carteLb.setAlignmentX(RIGHT_ALIGNMENT);
		panCritCarte.add(carteLb);
		
		carteNChB = new JCheckBox("Normales   ");
		carteNChB.setHorizontalTextPosition(JCheckBox.LEFT);
		carteNChB.setAlignmentX(RIGHT_ALIGNMENT);
		carteNChB.addActionListener(createActualiseTableauActionListener());
		panCritCarte.add(carteNChB);
		
		carteDChB = new JCheckBox("Dorées   ");
		carteDChB.setHorizontalTextPosition(JCheckBox.LEFT);
		carteDChB.setAlignmentX(RIGHT_ALIGNMENT);
		carteDChB.addActionListener(createActualiseTableauActionListener());
		panCritCarte.add(carteDChB);
		
		panCritere.add(panCritCarte);
		
		
		this.add(panCritere, BorderLayout.EAST);
		
		
		
		
		
		// PANEL CENTRAL : TABLEAU DE STATS
		JTable tableauStats = new JTable(model.getStatsTableModel());
		tableauStats.setCellSelectionEnabled(false);
		tableauStats.setRowHeight(30);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		tableauStats.setDefaultRenderer(String.class, centerRenderer);
		
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








