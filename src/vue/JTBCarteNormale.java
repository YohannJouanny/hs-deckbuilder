package vue;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JToggleButton;


public class JTBCarteNormale extends JToggleButton {
	private static final long serialVersionUID = 7715021569588491307L;
	
	
	private Image imgSelected;
	private Image imgUnselected;
	
	
	
	public JTBCarteNormale() {
		imgSelected = new ImageIcon("resources/buttons/JTBN_selected.png").getImage();
		imgUnselected = new ImageIcon("resources/buttons/JTBN_unselected.png").getImage();
		
		this.setBorderPainted(false);
		this.setSize(20, 20);
	}
	
	
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		if (isSelected()) {
			g2d.drawImage(imgSelected, 0, 0, this.getWidth(), this.getHeight(), this);
		}
		else {
			g2d.drawImage(imgUnselected, 0, 0, this.getWidth(), this.getHeight(), this);
		}
	}
}


