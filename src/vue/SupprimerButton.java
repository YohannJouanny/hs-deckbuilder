package vue;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;


public class SupprimerButton extends JButton {
	private static final long serialVersionUID = -2142206315282304138L;
	
	
	private Image icone;
	
	
	public SupprimerButton() {
		icone = new ImageIcon("resources/icons/buttons/Button_delete.png").getImage();
		
		this.setBorderPainted(false);
		this.setSize(20, 20);
	}
	
	
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(icone, 0, 0, this.getWidth(), this.getHeight(), this);
	}
}

