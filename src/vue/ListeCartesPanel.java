package vue;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import donnees.Carte;
import donnees.Classe;
import donnees.Extension;
import donnees.Rarete;
import model.ListeCartesModel;


public class ListeCartesPanel extends MainFramePanel {
	private static final long serialVersionUID = -3115756566264191400L;
	
	
	private ListeCartesModel model;
	private boolean editMode;
	
	private JTable tableauCartes;
	private JComboBox<Classe> selectClasse;
	private JComboBox<Rarete> selectRarete;
	private JComboBox<Extension> selectExt;
	private ButtonGroup manaRBGroup;
	private JButton editButton;
	private JButton resetButton;
	private JButton addCarteButton;
	private JButton deleteAllButton;
	private JButton retourButton;
	
	
	
	public ListeCartesPanel(ListeCartesModel model) {
		this.model = model;
		editMode = false;
		initPanel();
	}
	
	
	
	public void refresh() {
		if (editMode)
			switchMode();
		else
			refreshTableau();
		
		selectClasse.setSelectedItem(Classe.All);
		manaRBGroup.getElements().nextElement().setSelected(true);
		selectExt.setSelectedItem(Extension.ALL);
	}
	
	
	
	private void initPanel() {
		this.setLayout(new BorderLayout());
		
		
		// PANEL NORD : TITRE
		JPanel panTitre = new JPanel();
		JLabel titre = new JLabel("Liste des cartes");
		titre.setFont(new Font("Arial", Font.BOLD, 28));
		panTitre.add(titre);
		this.add(panTitre, BorderLayout.NORTH);
		
		
		// PANEL SUD : BOUTONS
		JPanel panBoutons = new JPanel();
		
		editButton = new JButton("Editer la liste");
		editButton.setFont(new Font("Arial", Font.BOLD, 16));
		editButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				switchMode();
			}
		});
		panBoutons.add(editButton);
		
		resetButton = new JButton("Vider la collection");
		resetButton.setFont(new Font("Arial", Font.BOLD, 16));
		resetButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir vider la collection ?",
						"Vider la collection",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE);

				if (option == JOptionPane.OK_OPTION)
					model.resetCollection();
			}
		});
		panBoutons.add(resetButton);
		
		addCarteButton = new JButton("Ajouter une carte");
		addCarteButton.setFont(new Font("Arial", Font.BOLD, 16));
		addCarteButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				CreerCartePopUp pop = new CreerCartePopUp(null);
				
				if(pop.showPopUp())
				{
					model.creerCarte( pop.getNomCarte(),
										pop.getCoutManaCarte(),
										pop.getClasseCarte(),
										pop.getTypeCarte(),
										pop.getRareteCarte(),
										pop.getExtensionCarte());
				}
			}
		});
		panBoutons.add(addCarteButton);
		addCarteButton.setVisible(false);
		
		deleteAllButton = new JButton("Supprimer toutes les cartes");
		deleteAllButton.setFont(new Font("Arial", Font.BOLD, 16));
		deleteAllButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir supprimer touts les cartes ?",
															"Supprimer toutes les cartes",
															JOptionPane.YES_NO_OPTION,
															JOptionPane.WARNING_MESSAGE);
				
				if (option == JOptionPane.OK_OPTION)
					model.supprimerAllCartes();
			}
		});
		panBoutons.add(deleteAllButton);
		deleteAllButton.setVisible(false);
		
		retourButton = new JButton("Retour");
		retourButton.setFont(new Font("Arial", Font.BOLD, 16));
		retourButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				switchMode();
			}
		});
		panBoutons.add(retourButton);
		retourButton.setVisible(false);
		
		this.add(panBoutons, BorderLayout.SOUTH);
		
		
		
		
		// PANEL EST : CRITERES DE RECHERCHE
		JPanel panCritere = new JPanel(new BorderLayout());
		
		
		// Panel Critere : Critere classe
		JPanel panCritClasse = new JPanel();
		panCritClasse.setLayout(new BoxLayout(panCritClasse, BoxLayout.Y_AXIS));
		
		JLabel selectClasseLb = new JLabel("Classe :");
		selectClasseLb.setAlignmentX(CENTER_ALIGNMENT);
		panCritClasse.add(selectClasseLb);
		
		JPanel panCritClasseCB = new JPanel();
		selectClasse = new JComboBox<Classe>();
		selectClasse.addItem(Classe.All);
		for (Classe c : Classe.values()) {
			if (c != Classe.All)
				selectClasse.addItem(c);
		}
		selectClasse.addActionListener(createActualiseListeActionListener());
		panCritClasseCB.add(selectClasse);
		panCritClasse.add(panCritClasseCB);
		
		
		panCritere.add(panCritClasse, BorderLayout.NORTH);
		
		
		
		JPanel panCritSud = new JPanel();
		panCritSud.setLayout(new BoxLayout(panCritSud, BoxLayout.Y_AXIS));
		
		// Panel Critere : Critere rarete
		JPanel panCritRarete = new JPanel();
		panCritRarete.setLayout(new BoxLayout(panCritRarete, BoxLayout.Y_AXIS));
		
		JLabel selectRareteLb = new JLabel("Rareté :");
		selectRareteLb.setAlignmentX(CENTER_ALIGNMENT);
		panCritRarete.add(selectRareteLb);
		
		JPanel panCritRareteCB = new JPanel();
		selectRarete = new JComboBox<Rarete>();
		selectRarete.addItem(Rarete.All);
		for (Rarete rarete : Rarete.values()) {
			if (rarete != Rarete.All)
				selectRarete.addItem(rarete);
		}
		selectRarete.addActionListener(createActualiseListeActionListener());
		panCritRareteCB.add(selectRarete);
		panCritRarete.add(panCritRareteCB);
		
		panCritSud.add(panCritRarete);
		
				
		// Panel Critere : Critere extension
		JPanel panCritExt = new JPanel();
		panCritExt.setLayout(new BoxLayout(panCritExt, BoxLayout.Y_AXIS));
		
		JLabel selectExtLb = new JLabel("Extension :");
		selectExtLb.setAlignmentX(CENTER_ALIGNMENT);
		panCritExt.add(selectExtLb);
		
		JPanel panCritExtCB = new JPanel();
		selectExt = new JComboBox<Extension>();
		selectExt.addItem(Extension.ALL);
		for (Extension ext : Extension.values()) {
			selectExt.addItem(ext);
		}
		selectExt.addActionListener(createActualiseListeActionListener());
		panCritExtCB.add(selectExt);
		panCritExt.add(panCritExtCB);
		
		panCritSud.add(panCritExt);
		panCritere.add(panCritSud, BorderLayout.SOUTH);
		
		
		// Panel Critere : Critere cout en mana
		JPanel panCritMana = new JPanel();
		panCritMana.setLayout(new BoxLayout(panCritMana, BoxLayout.Y_AXIS));
		
		JLabel manaMargeLb = new JLabel(" ");
		panCritMana.add(manaMargeLb);
		
		
		JLabel coutManaLb = new JLabel("Coût en Mana :");
		coutManaLb.setAlignmentX(CENTER_ALIGNMENT);
		panCritMana.add(coutManaLb);
		
		JRBCoutMana manaToutRB = new JRBCoutMana("Tout", -1);
		manaToutRB.setSelected(true);
		manaToutRB.addActionListener(createActualiseListeActionListener());
		panCritMana.add(manaToutRB);
		
		JRBCoutMana mana0RB = new JRBCoutMana("0 ", 0);
		mana0RB.addActionListener(createActualiseListeActionListener());
		panCritMana.add(mana0RB);
		
		JRBCoutMana mana1RB = new JRBCoutMana("1 ", 1);
		mana1RB.addActionListener(createActualiseListeActionListener());
		panCritMana.add(mana1RB);
		
		JRBCoutMana mana2RB = new JRBCoutMana("2 ", 2);
		mana2RB.addActionListener(createActualiseListeActionListener());
		panCritMana.add(mana2RB);
		
		JRBCoutMana mana3RB = new JRBCoutMana("3 ", 3);
		mana3RB.addActionListener(createActualiseListeActionListener());
		panCritMana.add(mana3RB);
		
		JRBCoutMana mana4RB = new JRBCoutMana("4 ", 4);
		mana4RB.addActionListener(createActualiseListeActionListener());
		panCritMana.add(mana4RB);
		
		JRBCoutMana mana5RB = new JRBCoutMana("5 ", 5);
		mana5RB.addActionListener(createActualiseListeActionListener());
		panCritMana.add(mana5RB);
		
		JRBCoutMana mana6RB = new JRBCoutMana("6 ", 6);
		mana6RB.addActionListener(createActualiseListeActionListener());
		panCritMana.add(mana6RB);
		
		JRBCoutMana mana7RB = new JRBCoutMana("7+", 7);
		mana7RB.addActionListener(createActualiseListeActionListener());
		panCritMana.add(mana7RB);
		
		manaRBGroup = new ButtonGroup();
		manaRBGroup.add(manaToutRB);
		manaRBGroup.add(mana0RB);
		manaRBGroup.add(mana1RB);
		manaRBGroup.add(mana2RB);
		manaRBGroup.add(mana3RB);
		manaRBGroup.add(mana4RB);
		manaRBGroup.add(mana5RB);
		manaRBGroup.add(mana6RB);
		manaRBGroup.add(mana7RB);
		
		panCritere.add(panCritMana, BorderLayout.CENTER);
		
		
		this.add(panCritere, BorderLayout.EAST);
		
		
		
		
		// PANEL CENTRAL : LISTE DES CARTES
		tableauCartes = new JTable(model.getListeCartesTableModel());
		tableauCartes.setFillsViewportHeight(true);
		tableauCartes.setCellSelectionEnabled(false);
		tableauCartes.setRowHeight(30);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		tableauCartes.setDefaultRenderer(int.class, centerRenderer);
		tableauCartes.setDefaultRenderer(Classe.class, centerRenderer);
		tableauCartes.setDefaultRenderer(Extension.class, centerRenderer);
		
		CollectionTableCell tableCellCollec = new CollectionTableCell();
		tableauCartes.setDefaultRenderer(Carte.class, tableCellCollec);
		tableauCartes.setDefaultEditor(Carte.class, tableCellCollec);
		
		InteretTableCell tableCellInteret = new InteretTableCell();
		tableauCartes.setDefaultRenderer(boolean.class, tableCellInteret);
		tableauCartes.setDefaultEditor(boolean.class, tableCellInteret);
		
		RareteTableCell rareteTableCell = new RareteTableCell();
		tableauCartes.setDefaultRenderer(Rarete.class, rareteTableCell);
		
		
		this.add(new JScrollPane(tableauCartes), BorderLayout.CENTER);
	}
	
	
	
	
	
	
	private ActionListener createActualiseListeActionListener() {
		return new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				int cout = getSelectedManaButton().getValue();
				Classe classe = (Classe)selectClasse.getSelectedItem();
				Rarete rarete = (Rarete)selectRarete.getSelectedItem();
				Extension ext = (Extension)selectExt.getSelectedItem();
				
				model.changeCriteres(cout, classe, rarete, ext);
			}
		};
	}
	
	
	private JRBCoutMana getSelectedManaButton() {
		for (Enumeration<AbstractButton> buttons = manaRBGroup.getElements(); buttons.hasMoreElements();) {
			AbstractButton button = buttons.nextElement();
			
			if (button.isSelected())
				return (JRBCoutMana)button;
		}
		
		return null;
	}
	
	
	private void switchMode() {
		if (editMode) {
			editMode = false;
			editButton.setVisible(true);
			resetButton.setVisible(true);
			addCarteButton.setVisible(false);
			deleteAllButton.setVisible(false);
			retourButton.setVisible(false);
		}
		else {
			editMode = true;
			editButton.setVisible(false);
			resetButton.setVisible(false);
			addCarteButton.setVisible(true);
			deleteAllButton.setVisible(true);
			retourButton.setVisible(true);
		}
		
		refreshTableau();
	}
	
	
	private void refreshTableau() {
		model.switchTable(editMode);
		setColumnsPreferredSize();
		
		if (editMode) {
			SupprimerCarteTableCell tableCell = new SupprimerCarteTableCell(model);
			tableauCartes.setDefaultRenderer(Carte.class, tableCell);
			tableauCartes.setDefaultEditor(Carte.class, tableCell);
		}
		else {
			CollectionTableCell tableCellCollec = new CollectionTableCell();
			tableauCartes.setDefaultRenderer(Carte.class, tableCellCollec);
			tableauCartes.setDefaultEditor(Carte.class, tableCellCollec);
		}
	}
	
	
	private void setColumnsPreferredSize() {
		if (editMode) {
			tableauCartes.getColumnModel().getColumn(0).setPreferredWidth(42);
			tableauCartes.getColumnModel().getColumn(1).setPreferredWidth(210);
			tableauCartes.getColumnModel().getColumn(2).setPreferredWidth(85);
			tableauCartes.getColumnModel().getColumn(3).setPreferredWidth(40);
			tableauCartes.getColumnModel().getColumn(4).setPreferredWidth(151);
			tableauCartes.getColumnModel().getColumn(5).setPreferredWidth(134);
		}
		else {
			tableauCartes.getColumnModel().getColumn(0).setPreferredWidth(40);
			tableauCartes.getColumnModel().getColumn(1).setPreferredWidth(190);
			tableauCartes.getColumnModel().getColumn(2).setPreferredWidth(75);
			tableauCartes.getColumnModel().getColumn(3).setPreferredWidth(40);
			tableauCartes.getColumnModel().getColumn(4).setPreferredWidth(145);
			tableauCartes.getColumnModel().getColumn(5).setPreferredWidth(127);
			tableauCartes.getColumnModel().getColumn(6).setPreferredWidth(40);
			
		}
	}

}





