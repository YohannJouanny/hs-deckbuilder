package vue;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import donnees.Extension;


public class EditerExtensionPopUp extends JDialog {
	private static final long serialVersionUID = -4490979486813507754L;
	
	
	private boolean sendData;
	
	private JComboBox<Extension> extensionCB;
	private JCheckBox aventureCB;
	private JCheckBox standardCB;
	
	
	
	public EditerExtensionPopUp(JFrame frame) {
		super(frame, "Editer une extension", true);
		
		this.setSize(400, 200);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		sendData = false;
		init();
	}
	
	
	public boolean showPopUp() {
		this.setVisible(true);
		return sendData;
	}
	
	
	public Extension getExtension() {
		if (sendData)
			return (Extension)extensionCB.getSelectedItem();

		return null;
	}
	
	public boolean getIsAventure() {
		if (sendData)
			return aventureCB.isSelected();

		return false;
	}
	
	public boolean getIsStandard() {
		if (sendData)
			return standardCB.isSelected();

		return false;
	}
	
	
	private void init() {
		JPanel panel = new JPanel(new BorderLayout());
		
		// PANEL NORD : TITRE
		JPanel panTitre = new JPanel();
		JLabel titre = new JLabel("Editer une extension");
		titre.setFont(new Font("Arial", Font.BOLD, 24));
		panTitre.add(titre);
		panel.add(panTitre, BorderLayout.NORTH);
		
		
		
		// PANEL SUD : BOUTONS
		JPanel panBoutons = new JPanel();
		
		JButton validerButton = new JButton("Valider");
		validerButton.setFont(new Font("Arial", Font.BOLD, 14));
		validerButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				sendData = true;
				setVisible(false);
			}
		});
		panBoutons.add(validerButton);
		
		JButton annulerButton = new JButton("Annuler");
		annulerButton.setFont(new Font("Arial", Font.BOLD, 14));
		annulerButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		panBoutons.add(annulerButton);
		
		panel.add(panBoutons, BorderLayout.SOUTH);
		
		
		
		// PANEL CENTRAL : INFORMATIONS
		JPanel panInfos = new JPanel();
		panInfos.setLayout(new BoxLayout(panInfos, BoxLayout.Y_AXIS));
		
		
		JPanel panInfoExt = new JPanel();
		JLabel extLb = new JLabel("Extension : ");
		panInfoExt.add(extLb);
		extensionCB = new JComboBox<Extension>();
		for (Extension ext : Extension.values()) {
			extensionCB.addItem(ext);
		}
		extensionCB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if (((Extension)extensionCB.getSelectedItem()).isAventure())
					aventureCB.setSelected(true);
				else
					aventureCB.setSelected(false);
				
				if (((Extension)extensionCB.getSelectedItem()).isStandard())
					standardCB.setSelected(true);
				else
					standardCB.setSelected(false);
			}
		});
		panInfoExt.add(extensionCB);
		panInfos.add(panInfoExt);
		
		JPanel panInfoAventure = new JPanel();
		JLabel aventureLb = new JLabel("Non collectable : ");
		panInfoAventure.add(aventureLb);
		aventureCB = new JCheckBox();
		if (((Extension)extensionCB.getSelectedItem()).isAventure())
			aventureCB.setSelected(true);
		panInfoAventure.add(aventureCB);
		panInfos.add(panInfoAventure);
		
		JPanel panInfoStandard = new JPanel();
		JLabel standardLb = new JLabel("Standard : ");
		panInfoStandard.add(standardLb);
		standardCB = new JCheckBox();
		if (((Extension)extensionCB.getSelectedItem()).isStandard())
			standardCB.setSelected(true);
		panInfoStandard.add(standardCB);
		panInfos.add(panInfoStandard);
		
		
		panel.add(panInfos, BorderLayout.CENTER);
		
		
		
		this.setContentPane(panel);
	}

}
