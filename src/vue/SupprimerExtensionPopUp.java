package vue;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import donnees.Extension;


public class SupprimerExtensionPopUp extends JDialog {
	private static final long serialVersionUID = 7284383979033572065L;
	
	
	private boolean sendData;
	
	private JComboBox<Extension> extensionCB;
	
	
	
	public SupprimerExtensionPopUp(JFrame frame) {
		super(frame, "Supprimer une extension", true);
		
		this.setSize(400, 150);
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
	
	
	
	private void init() {
		JPanel panel = new JPanel(new BorderLayout());
		
		// PANEL NORD : TITRE
		JPanel panTitre = new JPanel();
		JLabel titre = new JLabel("Supprimer une extension");
		titre.setFont(new Font("Arial", Font.BOLD, 24));
		panTitre.add(titre);
		panel.add(panTitre, BorderLayout.NORTH);
		
		
		
		// PANEL SUD : BOUTONS
		JPanel panBoutons = new JPanel();
		
		JButton validerButton = new JButton("Valider");
		validerButton.setFont(new Font("Arial", Font.BOLD, 14));
		validerButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir supprimer cette extension ?\n"
						+ "(Toutes les cartes de cette extension seront supprimées)",
						"Suppression d'une extension",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE);

				if (option == JOptionPane.OK_OPTION) {
					sendData = true;
					setVisible(false);
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
		
		
		JPanel panInfoExt = new JPanel();
		JLabel extLb = new JLabel("Extension : ");
		panInfoExt.add(extLb);
		extensionCB = new JComboBox<Extension>();
		for (Extension ext : Extension.values()) {
			extensionCB.addItem(ext);
		}
		panInfoExt.add(extensionCB);
		panInfos.add(panInfoExt);
		
		
		panel.add(panInfos, BorderLayout.CENTER);
		
		
		
		this.setContentPane(panel);
	}

}
