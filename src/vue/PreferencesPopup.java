package vue;

import java.awt.BorderLayout;
import java.awt.Component;
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

import donnees.Config;

public class PreferencesPopup  extends JDialog {
	private static final long serialVersionUID = 1L;

	
	private boolean sendData;
	private JCheckBox hideWildExtensionsCb;
	
	
	public PreferencesPopup(JFrame frame, Config config) {
		super(frame, "Préférences", true);
		
		this.setSize(400, 200);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		sendData = false;
		init();
		initForm(config);
	}
	
	private void init() {
		JPanel panel = new JPanel(new BorderLayout());
		
		// PANEL NORD : TITRE
		JPanel panTitre = new JPanel();
		JLabel titre = new JLabel("Préférences");
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
		
		JPanel panInfosVoid1 = new JPanel();
		panInfos.add(panInfosVoid1);
		
		JLabel hideWildExtensionsLb = new JLabel("Cacher les extensions non Standard dans les listes : ");
		hideWildExtensionsCb = new JCheckBox();
		JPanel panInfoHideWildExt = new JPanel();
		panInfoHideWildExt.setAlignmentY(Component.CENTER_ALIGNMENT);
		panInfoHideWildExt.add(hideWildExtensionsLb);
		panInfoHideWildExt.add(hideWildExtensionsCb);
		panInfos.add(panInfoHideWildExt);
		
		JPanel panInfosVoid2 = new JPanel();
		panInfos.add(panInfosVoid2);
		
		panel.add(panInfos, BorderLayout.CENTER);
		
		
		this.setContentPane(panel);
	}
	
	private void initForm(Config config) {
		hideWildExtensionsCb.setSelected(config.isHideWildExtensions());
	}
	
	
	public boolean showPopUp() {
		this.setVisible(true);
		return sendData;
	}
	
	public Config getConfig() {
		if (!sendData) {
			return null;
		}
		
		Config config = new Config();
		config.setHideWildExtensions(hideWildExtensionsCb.isSelected());
		return config;
	}
}
