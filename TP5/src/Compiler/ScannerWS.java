package Compiler;

import java.io.IOException;
import java.util.ArrayList;

public class ScannerWS extends Scanner {
    private ArrayList<Symboles> tableSymb;
    private int placeSymb;
    private int offset;

    public ScannerWS(String file) throws Exception, ErreurLexicale {
        super(file);
        this.tableSymb = new ArrayList<Symboles>();
        this.offset = -1;
    }

    public ArrayList<Symboles> getTableSymb() {
        return tableSymb;
    }

    public void setTableSymb(ArrayList<Symboles> tableSymb) {
        this.tableSymb = tableSymb;
    }

    public int getPlaceSymb() {
        return placeSymb;
    }

    public void setPlaceSymb(int placeSymb) {
        this.placeSymb = placeSymb;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void initMotsCles() {
        super.initMotsCles();
    }

    public Tokens codageLex(String mot) throws IOException {
        return super.codagelex(mot);
    }

    public void enterSymb(ClasseIdf c) {
        this.tableSymb.add(new Symboles(this.get_symbCour().getToken(), this.get_symbCour().getNom(),c));
         if (c == ClasseIdf.CONSTS || c == ClasseIdf.VARS){
            ++offset;
            this.tableSymb.get(this.tableSymb.size()-1).setAdresse(this.getOffset());
         }
    }

    public void chercherSymb() {
        for (var i = 0; i < this.tableSymb.size(); i++) {
            if (this.get_symbCour().getToken().equals(this.tableSymb.get(i).getToken())
                    && this.get_symbCour().getNom().equals(this.tableSymb.get(i).getNom())) {
                this.placeSymb = i;
                return;
            }
        }
        this.placeSymb = -1;
    }
}

