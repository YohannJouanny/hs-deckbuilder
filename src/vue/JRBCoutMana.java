package vue;

import javax.swing.JRadioButton;


public class JRBCoutMana extends JRadioButton {
	private static final long serialVersionUID = -7739780279664125557L;
	
	
	private int value;
	
	
	public JRBCoutMana(String text, int value) {
		super(text);
		this.value = value;
		
		this.setHorizontalTextPosition(LEFT);
		this.setAlignmentX(CENTER_ALIGNMENT);
	}
	
	
	public int getValue() {
		return value;
	}
}