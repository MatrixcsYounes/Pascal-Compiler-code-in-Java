package net.mips.interpreter;

public class Symboles {
    String nom;
    Mnemonique token;

    public Symboles(){
        this.nom = null;
        this.token = null;
    }
    public Symboles(String nom, Mnemonique token) {
        this.nom = nom;
        this.token = token;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Mnemonique getToken() {
        return token;
    }

    public void setToken(Mnemonique token) {
        this.token = token;
    }
}
