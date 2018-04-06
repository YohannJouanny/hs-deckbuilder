package vue;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import model.StatsBoosterModel;


public class StatsBoosterPanel extends MainFramePanel {
	private static final long serialVersionUID = 126742579298707257L;
	
	
	private StatsBoosterModel model;
	
	private JComboBox<StatsBoosterModel.TypeTableau> selectTab;
	
	
	public StatsBoosterPanel(StatsBoosterModel model) {
		this.model = model;
		initPanel();
	}
	
	
	
	public void refresh() {
		model.refreshData();
		selectTab.setSelectedItem(StatsBoosterModel.TypeTableau.NombreCarte);
	}
	
	
	
	private void initPanel() {
		this.setLayout(new BorderLayout());
		
		
		// PANEL NORD : TITRE
		JPanel panEntete = new JPanel(new BorderLayout());
		
		JPanel panTitre = new JPanel();
		JLabel titre = new JLabel("Statistiques de booster");
		titre.setFont(new Font("Arial", Font.BOLD, 28));
		panTitre.add(titre);
		panEntete.add(panTitre, BorderLayout.NORTH);
		
		
		
		// Panel Critere : Critere tableau
		JPanel panCritere = new JPanel(new BorderLayout());
		
		JPanel panCritTab = new JPanel();
		panCritTab.setLayout(new BoxLayout(panCritTab, BoxLayout.Y_AXIS));
		
		JLabel selectTabLb = new JLabel("Tableau :");
		selectTabLb.setAlignmentX(CENTER_ALIGNMENT);
		panCritTab.add(selectTabLb);
		
		JPanel panCritTabCB = new JPanel();
		selectTab = new JComboBox<StatsBoosterModel.TypeTableau>();
		for (StatsBoosterModel.TypeTableau t : StatsBoosterModel.TypeTableau.values()) {
			selectTab.addItem(t);
		}
		
		selectTab.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				model.changeTableau((StatsBoosterModel.TypeTableau)selectTab.getSelectedItem());
			}
		});
		
		panCritTabCB.add(selectTab);
		panCritTab.add(panCritTabCB);
		
		panCritere.add(panCritTab, BorderLayout.NORTH);
		panEntete.add(panCritere, BorderLayout.EAST);
		
		
		this.add(panEntete, BorderLayout.NORTH);
		
		
		
		
		
		// PANEL CENTRAL : TABLEAU DE STATS
		JTable tableauStats = new JTable(model.getStatsTableModel());
		tableauStats.setCellSelectionEnabled(false);
		tableauStats.setRowHeight(35);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		tableauStats.setDefaultRenderer(String.class, new StatsBoosterStringTableCell());
		
		this.add(new JScrollPane(tableauStats), BorderLayout.CENTER);
	}
	
}



