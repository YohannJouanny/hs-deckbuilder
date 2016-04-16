package vue;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;


public class StatsStringTableCell implements TableCellRenderer {
	
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JPanel renderPanel = new JPanel(new GridLayout(0, 1));
		renderPanel.setBackground(Color.WHITE);
		
		
		String s = (String)value;
		
		JLabel label = new JLabel(s);
		label.setFont(new Font("Arial", Font.PLAIN, 12));
		label.setHorizontalAlignment(JLabel.CENTER);
		renderPanel.add(label);
		
		
		
		try {
			String[] tab = s.split("/");
			int valActuelle = Integer.parseInt(tab[0]);
			int valMax = Integer.parseInt(tab[1]);
			
			if (valMax > 0) {
				JProgressBar progressBar = new JProgressBar(0, valMax);
				progressBar.setValue(valActuelle);
				progressBar.setStringPainted(true);
				progressBar.setBorderPainted(false);
				progressBar.setBackground(Color.WHITE);
				int color = (int)((1.0 - (double)valActuelle/(double)valMax) * 255.0);
				progressBar.setForeground(new Color(0, 180, color));
				renderPanel.add(progressBar);
			}
		}
		catch (NumberFormatException e) {
			// On ne rajoute rien
		}
		
		
		return renderPanel;
	}

}
