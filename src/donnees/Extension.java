package donnees;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;


@SuppressWarnings("unchecked")
public class Extension implements Serializable {
	private static final long serialVersionUID = 2411331385491870348L;
	
	public static final Extension ALL = new Extension("Tous les jeux", false, false);
	private static ArrayList<Extension> listeExt = new ArrayList<Extension>();
	
	static {
		try {
			ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File("extensions.data"))));
			listeExt = (ArrayList<Extension>)ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			createExtension("Jeu de base", true, true);
			createExtension("Classique", false, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static ArrayList<Extension> values() {
		return listeExt;
	}
	
	
	public static Extension createExtension(String nom, boolean isAdv, boolean isStandard) {
		Extension ext = new Extension(nom, isAdv, isStandard);
		listeExt.add(ext);
		return ext;
	}
	
	public static void deleteExtension(Extension ext) {
		listeExt.remove(ext);
	}
	
	
	
	

	private String nom;
	private boolean isAventure;
	private boolean isStandard;

	private Extension (String nom, boolean isAdv, boolean isStandard) {
		this.nom = nom;
		this.isAventure = isAdv;
		this.isStandard = isStandard;
	}
	
	
	public String getNom() {
		return nom;
	}
	
	public boolean isAventure() {
		return isAventure;
	}
	
	public boolean isStandard() {
		return isStandard;
	}
	
	public void edit(boolean isAventure, boolean isStandard) {
		this.isAventure = isAventure;
		this.isStandard = isStandard;
	}
	

	public String toString() {
		return nom;
	}
	
	
	public boolean equals(Object o) {
		if (o == null)
			return false;
		
		if (o.getClass() != Extension.class)
			return false;
		
		
		return ((Extension)o).getNom().equals(nom);
	}
}




