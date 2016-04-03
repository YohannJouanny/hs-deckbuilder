package vue;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;


public class StringTableCell implements TableCellRenderer {
	
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JPanel renderPanel = new JPanel(new GridLayout(0, 1));
		renderPanel.setBackground(Color.WHITE);
		
		String obj = (String)value;
		String[] tab = obj.split("\n");
		
		
		for (String s : tab) {
			JLabel label = new JLabel(s);
			label.setFont(new Font("Arial", Font.PLAIN, 12));
			label.setHorizontalAlignment(JLabel.CENTER);
			renderPanel.add(label);
		}
		
		return renderPanel;
	}




}



