package vue;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import model.PityTimerModel;


public class ArchiverTimerTableCell extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
	private static final long serialVersionUID = -5530955620427683861L;
	
	
	private Object editorValue;
	private JPanel renderPanel;
	private JPanel editPanel;
	
	
	public ArchiverTimerTableCell(PityTimerModel model) {
		renderPanel = new JPanel();
		renderPanel.setBackground(Color.white);
		
		JButton renderButton = new JButton("Archiver");
		renderPanel.add(renderButton);
		
		
		editPanel = new JPanel();
		editPanel.setBackground(Color.white);
		
		JButton editButton = new JButton("Archiver");
		editButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				model.archiverTimer((int)editorValue);
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
