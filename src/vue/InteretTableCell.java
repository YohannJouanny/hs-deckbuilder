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


public class InteretTableCell extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
	private static final long serialVersionUID = 2276384355334574357L;
	
	
	private Object editorValue;
	
	
	public InteretTableCell() {
		
	}
	
	

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		editorValue = value;
		Carte carte = (Carte)value;
		
		int panelWidth = table.getColumnModel().getColumn(column).getPreferredWidth();
		int panelHeight = table.getRowHeight();
		int panelHorizontalCenter = panelWidth/2;
		
		
		JPanel panel = new JPanel(null);
		panel.setBackground(Color.white);
		panel.setSize(panelWidth, panelHeight);
		
		
		JTBInteret button = new JTBInteret();
		button.setLocation(panelHorizontalCenter - 10, 5);
		
		if (carte.isInteressante())
			button.setSelected(true);
		
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if (button.isSelected()) {
					carte.setInteressante(true);
				}
				else {
					carte.setInteressante(false);
				}
				
				fireEditingStopped();
			}
		});
		
		panel.add(button);
		
		
		return panel;
	}
	
	public Object getCellEditorValue() {
		return editorValue;
	}
	
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		Carte carte = (Carte)value;
		
		int panelWidth = table.getColumnModel().getColumn(column).getPreferredWidth();
		int panelHeight = table.getRowHeight();
		int panelHorizontalCenter = panelWidth/2;
		
		
		JPanel panel = new JPanel(null);
		panel.setBackground(Color.white);
		panel.setSize(panelWidth, panelHeight);
		
		
		JTBInteret button = new JTBInteret();
		button.setLocation(panelHorizontalCenter - 10, 5);
		
		if (carte.isInteressante())
			button.setSelected(true);
		
		panel.add(button);
		
		
		return panel;
	}
}
