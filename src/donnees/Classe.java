package donnees;


public enum Classe {
	Druide ("Druide"),
	Chasseur ("Chasseur"),
	Mage ("Mage"),
	Paladin ("Paladin"),
	Pretre ("Pr�tre"),
	Voleur ("Voleur"),
	Chaman ("Chaman"),
	Demoniste ("D�moniste"),
	Guerrier ("Guerrier"),
	Neutre ("Neutre"),
	All ("Toutes les classes");
	
	
	private String nom;
	
	private Classe (String nom) {
		this.nom = nom;
	}
	
	public String toString() {
		return nom;
	}
}
