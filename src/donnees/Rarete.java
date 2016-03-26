package donnees;


public enum Rarete {
	Commune ("Commune"),
	Rare ("Rare"),
	Epique ("Epique"),
	Legendaire ("L�gendaire"),
	All ("");
	
	
	private String nom;
	
	private Rarete (String nom) {
		this.nom = nom;
	}
	
	public String toString() {
		return nom;
	}
}
