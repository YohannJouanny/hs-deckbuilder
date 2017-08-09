package donnees;

import java.io.Serializable;


public class Carte implements Comparable<Carte>, Serializable {
	private static final long serialVersionUID = 3660480832339357858L;


	public enum Type {
		Arme,
		Hero,
		Sort,
		Serviteur;
	}
	
	private class Exemplaires implements Serializable {	
		private static final long serialVersionUID = -8051128287696286379L;
		
		private int nbCarteNormale;
		private int nbCarteDoree;
		
		public Exemplaires() {
			nbCarteNormale = 0;
			nbCarteDoree = 0;
		}
		
		public void reset() {
			nbCarteNormale = 0;
			nbCarteDoree = 0;
		}
	}
	
	
	
	private String nom;
	private int mana;
	private Classe classe;
	private Type type;
	private Rarete rarete;
	private Extension extension;
	private Exemplaires exemplaires;
	private boolean isInteressante;
	
	
	
	public Carte(String nom, int mana, Classe classe, Type type, Rarete rarete, Extension ext) {
		this.nom = nom;
		this.mana = mana;
		this.classe = classe;
		this.type = type;
		this.rarete = rarete;
		this.extension = ext;
		exemplaires = new Exemplaires();
		this.isInteressante = false;
	}
	
	
	
	public String getNom() {
		return nom;
	}
	
	public int getMana() {
		return mana;
	}
	
	public Classe getClasse() {
		return classe;
	}
	
	public Rarete getRarete() {
		return rarete;
	}
	
	public Extension getExtension() {
		return extension;
	}
	
	public int getNbCarteNormalePossede() {
		return exemplaires.nbCarteNormale;
	}
	
	public int getNbCarteDoreePossede() {
		return exemplaires.nbCarteDoree;
	}
	
	public boolean isInteressante() {
		return isInteressante;
	}
	
	
	
	public boolean hasMaxCarteNormale() {
		if (exemplaires.nbCarteNormale >= 2 || (rarete == Rarete.Legendaire && exemplaires.nbCarteNormale >= 1))
			return true;
		
		return false;
	}
	
	public boolean hasMaxCarteDoree() {
		if (exemplaires.nbCarteDoree >= 2 || (rarete == Rarete.Legendaire && exemplaires.nbCarteDoree >= 1))
			return true;
		
		return false;
	}
	
	public boolean hasMaxCarte() {
		if ((exemplaires.nbCarteNormale + exemplaires.nbCarteDoree) >= 2 || 
				(rarete == Rarete.Legendaire && (exemplaires.nbCarteNormale + exemplaires.nbCarteDoree) >= 1)) {
			return true;
		}
		
		return false;
	}
	
	public void ajouterCarteNormale() {
		if (!hasMaxCarteNormale())
			exemplaires.nbCarteNormale++;
	}
	
	public void ajouterCarteDoree() {
		if (!hasMaxCarteDoree())
			exemplaires.nbCarteDoree++;
	}
	
	public void enleverCarteNormale() {
		if (exemplaires.nbCarteNormale > 0)
			exemplaires.nbCarteNormale--;
	}
	
	public void enleverCarteDoree() {
		if (exemplaires.nbCarteDoree > 0)
			exemplaires.nbCarteDoree--;
	}
	
	public void resetPossessions() {
		exemplaires.reset();
	}
	
	
	public void setInteressante(boolean arg) {
		isInteressante = arg;
	}
	
	
	
	public void refreshExtensionPointer() {
		for (Extension ext : Extension.values()) {
			if (extension.equals(ext)) {
				extension = ext;
				return;
			}
		}
		
		extension = Extension.createExtension(extension.getNom(), extension.isAventure(), extension.isStandard());
	}
	
	
	public boolean equals(Carte c) {
		if (!c.nom.equals(nom))
			return false;
		
		if (c.mana != mana)
			return false;
		
		if (c.classe != classe)
			return false;
		
		if (c.type != type)
			return false;
		
		if (c.rarete != rarete)
			return false;
		
		if (c.extension != extension)
			return false;
		
		return true;
	}
	
	
	public int compareTo(Carte c) {
		if (classe.compareTo(c.classe) != 0)
			return classe.compareTo(c.classe);
		
		if (mana - c.mana != 0)
			return mana - c.mana;
		
		return nom.compareTo(c.nom);
	}
}





