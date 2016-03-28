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

import model.ListeCartesModel;
import donnees.Carte;


public class SupprimerCarteTableCell extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
	private static final long serialVersionUID = -7114543454028106741L;
	
	
	private Object editorValue;
	private JPanel renderPanel;
	private JPanel editPanel;
	
	
	public SupprimerCarteTableCell(ListeCartesModel model) {
		renderPanel = new JPanel(null);
		renderPanel.setBackground(Color.white);
		
		SupprimerButton renderButton = new SupprimerButton();
		renderButton.setLocation(57,5);
		renderPanel.add(renderButton);
		
		
		editPanel = new JPanel(null);
		editPanel.setBackground(Color.white);
		
		SupprimerButton editButton = new SupprimerButton();
		editButton.setLocation(57,5);
		editButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				fireEditingStopped();
				model.supprimerCarte((Carte)editorValue);
			}
		});
		editPanel.add(editButton);
	}
	
	

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		editorValue = value;
		return editPanel;
	}
	
	public Object getCellEditorValue() {
		return editorValue;
	}
	
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		return renderPanel;
	}
	
}
