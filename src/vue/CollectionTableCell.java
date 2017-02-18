package vue;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import donnees.Carte;
import donnees.Rarete;


public class CollectionTableCell extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
	private static final long serialVersionUID = -2416250974731984047L;
	
	
	private Object editorValue;
	
	
	public CollectionTableCell() {
		
	}
	
	
	
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		editorValue = value;
		Carte carte = (Carte)value;
		
		int panelWidth = table.getColumnModel().getColumn(column).getPreferredWidth();
		int panelHeight = table.getRowHeight();
		int panelHorizontalCenter = panelWidth/2 + 3; //Offset de règlage bidouille
		
		
		
		JPanel panel = new JPanel(null);
		panel.setBackground(Color.white);
		panel.setSize(panelWidth, panelHeight);
		
		
		// BOUTON CARTE NORMALE 1
		JTBCarteNormale button1N = new JTBCarteNormale();
		button1N.setLocation(panelHorizontalCenter - 4 - 20 - 8 - 20, 5);
		
		if (carte.getNbCarteNormalePossede() >= 1)
			button1N.setSelected(true);
		
		button1N.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if (button1N.isSelected()) {
					carte.ajouterCarteNormale();
				}
				else {
					carte.enleverCarteNormale();
					carte.enleverCarteNormale();
				}
				
				fireEditingStopped();
			}
		});
		
		panel.add(button1N);
		
		
		
		// BOUTON CARTE NORMALE 2
		if (carte.getRarete() != Rarete.Legendaire) {
			JTBCarteNormale button2N = new JTBCarteNormale();
			button2N.setLocation(panelHorizontalCenter - 4 - 20, 5);
			
			if (carte.getNbCarteNormalePossede() >= 2)
				button2N.setSelected(true);
			
			button2N.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					if (button2N.isSelected()) {
						carte.ajouterCarteNormale();
						carte.ajouterCarteNormale();
					}
					else {
						carte.enleverCarteNormale();
					}
					
					fireEditingStopped();
				}
			});
			
			panel.add(button2N);
		}
		else {
			button1N.setLocation(panelHorizontalCenter - 4 - 20, 5);
		}
		
		
		
		// BOUTON CARTE DOREE 1
		JTBCarteDoree button1D = new JTBCarteDoree();
		button1D.setLocation(panelHorizontalCenter + 4, 5);
		
		if (carte.getNbCarteDoreePossede() >= 1)
			button1D.setSelected(true);
		
		button1D.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if (button1D.isSelected()) {
					carte.ajouterCarteDoree();
				}
				else {
					carte.enleverCarteDoree();
					carte.enleverCarteDoree();
				}
				
				fireEditingStopped();
			}
		});
		
		panel.add(button1D);
		
		
		
		// BOUTON CARTE DOREE 2
		if (carte.getRarete() != Rarete.Legendaire) {
			JTBCarteDoree button2D = new JTBCarteDoree();
			button2D.setLocation(panelHorizontalCenter + 4 + 20 + 8, 5);
			
			if (carte.getNbCarteDoreePossede() >= 2)
				button2D.setSelected(true);
			
			button2D.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					if (button2D.isSelected()) {
						carte.ajouterCarteDoree();
						carte.ajouterCarteDoree();
					}
					else {
						carte.enleverCarteDoree();
					}
					
					fireEditingStopped();
				}
			});
			
			panel.add(button2D);
		}
		
		
		return panel;
	}
	
	
	public Object getCellEditorValue() {
		return editorValue;
	}
	
	
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		Carte carte = (Carte) value;
		
		int panelWidth = table.getColumnModel().getColumn(column).getPreferredWidth();
		int panelHeight = table.getRowHeight();
		int panelHorizontalCenter = panelWidth/2 + 3; //Offset de règlage bidouille
		
		
		
		JPanel panel = new JPanel(null);
		panel.setBackground(Color.white);
		panel.setSize(panelWidth, panelHeight);
		
		
		// BOUTON CARTE NORMALE 1
		JTBCarteNormale button1N = new JTBCarteNormale();
		button1N.setLocation(panelHorizontalCenter - 4 - 20 - 8 - 20, 5);
		
		if (carte.getNbCarteNormalePossede() >= 1)
			button1N.setSelected(true);
		
		panel.add(button1N);

		
		
		// BOUTON CARTE NORMALE 2
		if (carte.getRarete() != Rarete.Legendaire) {
			JTBCarteNormale button2N = new JTBCarteNormale();
			button2N.setLocation(panelHorizontalCenter - 4 - 20, 5);
			
			if (carte.getNbCarteNormalePossede() >= 2)
				button2N.setSelected(true);
			
			panel.add(button2N);
		}
		else {
			button1N.setLocation(panelHorizontalCenter - 4 - 20, 5);
		}
		
		
		
		// BOUTON CARTE DOREE 1
		JTBCarteDoree button1D = new JTBCarteDoree();
		button1D.setLocation(panelHorizontalCenter + 4, 5);
		
		if (carte.getNbCarteDoreePossede() >= 1)
			button1D.setSelected(true);
		
		panel.add(button1D);
		
		
		
		// BOUTON CARTE DOREE 2
		if (carte.getRarete() != Rarete.Legendaire) {
			JTBCarteDoree button2D = new JTBCarteDoree();
			button2D.setLocation(panelHorizontalCenter + 4 + 20 + 8, 5);
			
			if (carte.getNbCarteDoreePossede() >= 2)
				button2D.setSelected(true);
			
			panel.add(button2D);
		}
		
		
		return panel;
	}
}
