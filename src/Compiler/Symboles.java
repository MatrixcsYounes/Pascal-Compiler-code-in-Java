package Compiler;


public class Symboles {
private Tokens token;
private String nom;
private ClasseIdf classe;
private int adresse;


public Symboles(Tokens token, String nom, ClasseIdf c) {
	this.token = token;
	this.nom= nom;
	this.classe=c;
	this.adresse = -1;
}
public Symboles(Tokens token, String nom){
	this(token,nom,null);
}



	public ClasseIdf getClasse() {
		return classe;
	}

	public void setClasse(ClasseIdf classe) {
		this.classe = classe;
	}

	public Tokens getToken() {
	return token;
}
public void setToken(Tokens token) {
	this.token = token;
}
public String getNom() {
	return nom;
}
public void setNom(String nom) {
	this.nom = nom;
}

	public int getAdresse() {
		return adresse;
	}

	public void setAdresse(int adresse) {
		this.adresse = adresse;
	}
}
