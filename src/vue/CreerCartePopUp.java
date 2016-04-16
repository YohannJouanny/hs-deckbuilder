package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import donnees.Carte;
import donnees.Classe;
import donnees.Extension;
import donnees.Rarete;


public class CreerCartePopUp extends JDialog {
	private static final long serialVersionUID = -5488199971022994447L;
	

	private boolean sendData;
	
	private JLabel erreurLb;
	private JTextField nomTF;
	private JTextField manaTF;
	private JComboBox<Classe> classeCB;
	private JComboBox<Carte.Type> typeCB;
	private JComboBox<Rarete> rareteCB;
	private JComboBox<Extension> extensionCB;
	
	
	
	public CreerCartePopUp(JFrame frame) {
		super(frame, "Créer une carte", true);
		
		this.setSize(400, 400);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		sendData = false;
		init();
	}
	
	
	public boolean showPopUp() {
		this.setVisible(true);
		return sendData;
	}
	
	
	public String getNomCarte() {
		if (sendData)
			return nomTF.getText();

		return null;
	}
	
	public int getCoutManaCarte() {
		if (sendData)
			return Integer.parseInt(manaTF.getText());

		return -1;
	}
	
	public Classe getClasseCarte() {
		if (sendData)
			return (Classe)classeCB.getSelectedItem();

		return null;
	}
	
	public Carte.Type getTypeCarte() {
		if (sendData)
			return (Carte.Type)typeCB.getSelectedItem();

		return null;
	}
	
	public Rarete getRareteCarte() {
		if (sendData)
			return (Rarete)rareteCB.getSelectedItem();

		return null;
	}
	
	public Extension getExtensionCarte() {
		if (sendData)
			return (Extension)extensionCB.getSelectedItem();

		return null;
	}
	
	
	
	private void init() {
		JPanel panel = new JPanel(new BorderLayout());
		
		// PANEL NORD : TITRE
		JPanel panTitre = new JPanel();
		JLabel titre = new JLabel("Créer une carte");
		titre.setFont(new Font("Arial", Font.BOLD, 24));
		panTitre.add(titre);
		panel.add(panTitre, BorderLayout.NORTH);
		
		
		
		// PANEL SUD : BOUTONS
		JPanel panBoutons = new JPanel();
		
		JButton validerButton = new JButton("Valider");
		validerButton.setFont(new Font("Arial", Font.BOLD, 14));
		validerButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if (verifieChamps()) {
					sendData = true;
					setVisible(false);
				}
				else {
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
		erreurLb = new JLabel("Erreur : certains champs sont vide ou invalide !");
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
		
		JPanel panInfoMana = new JPanel();
		JLabel manaLb = new JLabel("Coût en Mana : ");
		panInfoMana.add(manaLb);
		manaTF = new JTextField();
		manaTF.setColumns(2);
		panInfoMana.add(manaTF);
		panInfos.add(panInfoMana);
		
		JPanel panInfoClasse = new JPanel();
		JLabel classeLb = new JLabel("Classe : ");
		panInfoClasse.add(classeLb);
		classeCB = new JComboBox<Classe>();
		for (Classe c : Classe.values()) {
			if (c != Classe.All)
				classeCB.addItem(c);
		}
		classeCB.setSelectedItem(null);
		panInfoClasse.add(classeCB);
		panInfos.add(panInfoClasse);
		
		JPanel panInfoType = new JPanel();
		JLabel typeLb = new JLabel("Type : ");
		panInfoType.add(typeLb);
		typeCB = new JComboBox<Carte.Type>();
		for (Carte.Type t : Carte.Type.values()) {
			typeCB.addItem(t);
		}
		typeCB.setSelectedItem(null);
		panInfoType.add(typeCB);
		panInfos.add(panInfoType);
		
		JPanel panInfoRarete = new JPanel();
		JLabel rareteLb = new JLabel("Rareté : ");
		panInfoRarete.add(rareteLb);
		rareteCB = new JComboBox<Rarete>();
		for (Rarete r :Rarete.values()) {
			if (r != Rarete.All)
				rareteCB.addItem(r);
		}
		rareteCB.setSelectedItem(null);
		panInfoRarete.add(rareteCB);
		panInfos.add(panInfoRarete);
		
		JPanel panInfoExt = new JPanel();
		JLabel extensionLb = new JLabel("Extension : ");
		panInfoExt.add(extensionLb);
		extensionCB = new JComboBox<Extension>();
		for (Extension ext : Extension.values()) {
			extensionCB.addItem(ext);
		}
		extensionCB.setSelectedItem(null);
		panInfoExt.add(extensionCB);
		panInfos.add(panInfoExt);
		
		panel.add(panInfos, BorderLayout.CENTER);
		

		
		this.setContentPane(panel);
	}
	
	
	private boolean verifieChamps() {
		if (nomTF.getText().equals(""))
			return false;
		
		if (manaTF.getText().equals(""))
			return false;
		
		if (classeCB.getSelectedItem() == null)
			return false;
		
		if (typeCB.getSelectedItem() == null)
			return false;
		
		if (rareteCB.getSelectedItem() == null)
			return false;
		
		if (extensionCB.getSelectedItem() == null)
			return false;
		
		
		
		try {
			Integer.parseInt(manaTF.getText());
		}
		catch (NumberFormatException e) {
			return false;
		}
		
		return true;
	}
}







