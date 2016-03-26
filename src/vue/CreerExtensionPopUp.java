package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class CreerExtensionPopUp extends JDialog {
	private static final long serialVersionUID = -8130753264223938638L;
	
	
	private boolean sendData;
	
	private JTextField nomTF;
	private JCheckBox aventureCB;
	private JCheckBox standardCB;
	private JLabel erreurLb;
	
	
	
	public CreerExtensionPopUp(JFrame frame) {
		super(frame, "Ajouter une extension", true);
		
		this.setSize(400, 210);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		sendData = false;
		init();
	}
	
	
	public boolean showPopUp() {
		this.setVisible(true);
		return sendData;
	}
	
	
	public String getNomExtension() {
		if (sendData)
			return nomTF.getText();

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
		JLabel titre = new JLabel("Ajouter une extension");
		titre.setFont(new Font("Arial", Font.BOLD, 24));
		panTitre.add(titre);
		panel.add(panTitre, BorderLayout.NORTH);
		
		
		
		// PANEL SUD : BOUTONS
		JPanel panBoutons = new JPanel();
		
		JButton validerButton = new JButton("Ajouter");
		validerButton.setFont(new Font("Arial", Font.BOLD, 14));
		validerButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if (verifieChamps()) {
					sendData = true;
					setVisible(false);
				}
				else {
					setSize(400, 225);
					erreurLb.setVisible(true);
				}
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
		
		
		JPanel panInfoErreur = new JPanel();
		erreurLb = new JLabel("Champ nom requis !");
		erreurLb.setForeground(Color.red);
		erreurLb.setVisible(false);
		panInfoErreur.add(erreurLb);
		panInfos.add(panInfoErreur);
		
		JPanel panInfoNom = new JPanel();
		JLabel nomLb = new JLabel("Nom : ");
		panInfoNom.add(nomLb);
		nomTF = new JTextField();
		nomTF.setColumns(15);
		panInfoNom.add(nomTF);
		panInfos.add(panInfoNom);
		
		JPanel panInfoAventure = new JPanel();
		JLabel aventureLb = new JLabel("Aventure : ");
		panInfoAventure.add(aventureLb);
		aventureCB = new JCheckBox();
		panInfoAventure.add(aventureCB);
		panInfos.add(panInfoAventure);
		
		JPanel panInfoStandard = new JPanel();
		JLabel standardLb = new JLabel("Standard : ");
		panInfoStandard.add(standardLb);
		standardCB = new JCheckBox();
		panInfoStandard.add(standardCB);
		panInfos.add(panInfoStandard);
		
		
		panel.add(panInfos, BorderLayout.CENTER);
		
		
		
		this.setContentPane(panel);
	}
	
	
	private boolean verifieChamps() {
		if (nomTF.getText().equals(""))
			return false;
		
		return true;
	}
}




