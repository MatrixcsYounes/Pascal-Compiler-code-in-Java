package net.mips.interpreter;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import Compiler.ErreurLexicale;
import Compiler.CodesErr;
public class ScannerPcode {

    static public final char EOF = '\0';
    ArrayList<Symboles> motCles;
    Symboles symbCour;
    char carCour;
    FileReader fluxSour;


    public ScannerPcode(String file) throws IOException, ErreurLexicale {
        File file1 = new File(file);
        if (!file1.exists()) {
            throw new ErreurLexicale(CodesErr.FIC_VID_ERR);
        } else {
            this.fluxSour = new FileReader(file);
            this.motCles = new ArrayList<>();
        }
    }


    public ArrayList<Symboles> get_motcles() {
        return motCles;
    }

    public Symboles get_symbCour() {
        return this.symbCour;
    }

    public char get_carCour() {
        return this.carCour;
    }


    public void initMotsCles() {
        this.motCles.add(new Symboles("NUM", Mnemonique.NUM));
        this.motCles.add(new Symboles("EOF", Mnemonique.EOF));
    }


    public Mnemonique codagelex(String mot) throws IOException {
        Symboles sym = new Symboles(mot.toUpperCase(), Mnemonique.valueOf(mot.toUpperCase()));
        if (motCles.contains(sym)){
            throw new Error("Pcode est invalide!");
        }else return sym.token;
    }


    public void lireCar() throws IOException {
        if (this.fluxSour.ready()) {
            this.carCour = (char) this.fluxSour.read();
        } else {
            this.carCour = EOF;
        }
    }


    public void lireNombre() throws Exception {
        StringBuilder s = new StringBuilder();
        while (Character.isDigit(this.carCour) && this.carCour != ' ') {
            s.append(this.carCour);
            lireCar();
        }
        this.symbCour = new Symboles(s.toString(), Mnemonique.NUM);
    }


    public void liremot() throws Exception {
        StringBuilder s = new StringBuilder();
        while (Character.isLetter(this.carCour) || Character.isDigit(this.carCour) && this.carCour != ' ') {
            s.append(this.carCour);
            lireCar();
        }
        this.symbCour = new Symboles(s.toString(), codagelex(s.toString()));
    }

    public void symbsuiv() throws Exception {
        while (Character.isWhitespace(this.carCour)) this.lireCar();
        if (Character.isLetter(this.carCour)) this.liremot();
        else if (Character.isDigit(this.carCour)) this.lireNombre();
    }
}
