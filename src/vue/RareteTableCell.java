package vue;

import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import donnees.Rarete;
 
public class RareteTableCell implements TableCellRenderer {
	
	private ImageIcon imgCommune;
	private ImageIcon imgRare;
	private ImageIcon imgEpique;
	private ImageIcon imgLegendaire;
	
	
	public RareteTableCell() {
		imgCommune = new ImageIcon("resources/icons/Commune.png");
		imgRare = new ImageIcon("resources/icons/Rare.png");
		imgEpique = new ImageIcon("resources/icons/Epique.png");
		imgLegendaire = new ImageIcon("resources/icons/Legendaire.png");
	}
	
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		Rarete rarete = (Rarete)value;
		
		int panelWidth = table.getColumnModel().getColumn(column).getPreferredWidth();
		int panelHeight = table.getRowHeight();
		int panelHorizontalCenter = panelWidth/2 + 1; //Offset de règlage bidouille
		
		
		JPanel panel = new JPanel(null);
		panel.setBackground(Color.white);
		panel.setSize(panelWidth, panelHeight);
		
		JLabel icone = null;
		
		switch(rarete) {
			case Commune:
				icone = new JLabel(imgCommune);
				break;
			case Rare:
				icone = new JLabel(imgRare);
				break;
			case Epique:
				icone = new JLabel(imgEpique);
				break;
			case Legendaire:
				icone = new JLabel(imgLegendaire);
				break;
			case All:
				icone = new JLabel("");
				break;
		}
		
		icone.setSize(25, 30);
		icone.setLocation(panelHorizontalCenter - 10, 0);
		panel.add(icone);
		
		return panel;
	}

}
