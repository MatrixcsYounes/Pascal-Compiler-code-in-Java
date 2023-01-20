package Compiler;

import net.mips.interpreter.Instruction;
import net.mips.interpreter.Mnemonique;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class ParserWS extends Parser{
    private ArrayList<Instruction> pcode;
    private PrintWriter fluxCible;
    public ParserWS(String file , OutputStream out) throws Exception {
        super(file);
        this.scanner = new ScannerWS(file);
        this.pcode = new ArrayList<>();

        this.fluxCible = new PrintWriter(out, true);
    }

    public ArrayList<Instruction> getPcode() {
        return pcode;
    }

    public void setPcode(ArrayList<Instruction> pcode) {
        this.pcode = pcode;
    }

    public PrintWriter getFluxCible() {
        return fluxCible;
    }

    public void setFluxCible(PrintWriter fluxCible) {
        this.fluxCible = fluxCible;
    }
    public void generer1(Mnemonique mnemonique){
        this.pcode.add(new Instruction(mnemonique));
    }
    public void generer2(Mnemonique mnemonique, int operande){
        this.pcode.add(new Instruction(mnemonique, operande));
    }
    public void savePcode() {
        Mnemonique[] val = {Mnemonique.INT, Mnemonique.LDA, Mnemonique.LDI};
        for (Instruction i : this.pcode) {
            this.fluxCible.println(Arrays.asList(val).contains(i.getMne()) ? (i.getMne() + " " + i.getSuite()) : i.getMne());
        }

    }
    public void program() throws Exception {
        testeAccept(Tokens.PROGRAM_TOKEN, CodesErr.PROGRAMM_ERR);
        testeInsere(Tokens.ID_TOKEN,ClasseIdf.PROGRAM, CodesErr.ID_ERR);
        testeAccept(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
        block();
        this.generer1(Mnemonique.HLT);
        testeAccept(Tokens.PNT_TOKEN, CodesErr.PNT_ERR);

    }
    public void consts() throws Exception {
        if (this.scanner.get_symbCour().getToken() == Tokens.CONST_TOKEN) {
            testeAccept(Tokens.CONST_TOKEN, CodesErr.CONST_ERR);
            do {
                testeInsere(Tokens.ID_TOKEN,ClasseIdf.CONSTS ,CodesErr.ID_ERR);
                generer2(Mnemonique.LDA, ((ScannerWS)(this.getScanner())).getOffset());
                testeAccept(Tokens.EG_TOKEN, CodesErr.EG_ERR);
                generer2(Mnemonique.LDI,Integer.parseInt(this.getScanner().get_symbCour().getNom()));
                testeAccept(Tokens.NUM_TOKEN, CodesErr.NUM_ERR);
                generer1(Mnemonique.STO);
                testeAccept(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
            }
            while (this.scanner.symbCour.getToken() == Tokens.ID_TOKEN);
        }
    }
    public void vars() throws Exception {
        if (this.scanner.get_symbCour().getToken() == Tokens.VAR_TOKEN) {
            testeAccept(Tokens.VAR_TOKEN, CodesErr.VAR_ERR);
            testeInsere(Tokens.ID_TOKEN,ClasseIdf.VARS ,CodesErr.ID_ERR);
            generer2(Mnemonique.LDA,((ScannerWS)(this.getScanner())).getOffset());
            while (this.scanner.get_symbCour().getToken() == Tokens.VIR_TOKEN) {
                testeAccept(Tokens.VIR_TOKEN, CodesErr.VIR_ERR);
                testeInsere(Tokens.ID_TOKEN,ClasseIdf.VARS ,CodesErr.ID_ERR);
            }
            testeAccept(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
        }
    }
    public void block() throws Exception {
        ((ScannerWS) this.getScanner()).setOffset(-1);
        consts();
        vars();
        this.pcode.add(0,new Instruction(Mnemonique.INT, ((ScannerWS) this.getScanner()).getOffset()));
        insts();
    }
    public void affec() throws Exception {
        testeCherche(Tokens.ID_TOKEN, CodesErr.ID_ERR);
        generer2(Mnemonique.LDA, ((ScannerWS) this.getScanner()).getTableSymb().get(((ScannerWS)this.getScanner()).getPlaceSymb()).getAdresse());

        if (((ScannerWS)this.getScanner()).getTableSymb().get(((ScannerWS)this.getScanner()).getPlaceSymb()).getClasse()
                == ClasseIdf.CONSTS)
            throw new ErreurSemantique(CodesErr.ID_NOT_DEFINED_ERR);

        testeAccept(Tokens.AFFEC_TOKEN, CodesErr.AFFEC_ERR);
        this.expr();
        generer1(Mnemonique.STO);
    }
    public void fact() throws Exception {
        switch (this.scanner.symbCour.getToken()) {
            case ID_TOKEN -> {
                testeCherche(Tokens.ID_TOKEN, CodesErr.ID_ERR);
                generer2(Mnemonique.LDA, ((ScannerWS) this.getScanner()).getTableSymb().get(((ScannerWS) this.getScanner()).getPlaceSymb()).getAdresse());
                generer1(Mnemonique.LDV);
            }
            case NUM_TOKEN -> {
                generer2(Mnemonique.LDI, Integer.parseInt(this.getScanner().get_symbCour().getNom()));
                testeAccept(Tokens.NUM_TOKEN, CodesErr.NUM_ERR);
            }
            case PARG_TOKEN -> {
                testeAccept(Tokens.PARG_TOKEN, CodesErr.PARG_ERR);
                expr();
                testeAccept(Tokens.PARD_TOKEN, CodesErr.PARD_ERR);
            }
            default -> {
                throw new ErreurSyntaxique(CodesErr.CAR_INC_ERR);
            }
        }
    }
    @Override
    public void term() throws Exception {
        fact();
        while (this.scanner.get_symbCour().getToken() == Tokens.MUL_TOKEN
                || this.scanner.get_symbCour().getToken() == Tokens.DIV_TOKEN) {
            var it = this.getScanner().get_symbCour().getToken();
            mulop();
            fact();
            generer1( it == Tokens.MUL_TOKEN ? Mnemonique.MUL : Mnemonique.DIV);
        }
    }
    @Override
    public void expr() throws Exception {
        term();
        while (this.scanner.get_symbCour().getToken() == Tokens.PLUS_TOKEN
                || this.scanner.get_symbCour().getToken() == Tokens.MOINS_TOKEN) {
            var it = this.getScanner().symbCour.getToken();
            addop();
            term();
            generer1(it == Tokens.PLUS_TOKEN ? Mnemonique.ADD : Mnemonique.SUB);
        }
    }

    @Override
    public void ecrire() throws Exception {

            testeAccept(Tokens.WRITE_TOKEN, CodesErr.WRITE_ERR);
            testeAccept(Tokens.PARG_TOKEN, CodesErr.PARG_ERR);
            expr();
            generer1(Mnemonique.PRN);
            while (this.scanner.get_symbCour().getToken() == Tokens.VIR_TOKEN) {
                testeAccept(Tokens.VIR_TOKEN, CodesErr.VIR_ERR);
                expr();
                generer1(Mnemonique.PRN);
            }
            testeAccept(Tokens.PARD_TOKEN, CodesErr.PARD_ERR);

    }

    public void lire() throws Exception {
            testeAccept(Tokens.READ_TOKEN, CodesErr.READ_ERR);
            testeAccept(Tokens.PARG_TOKEN, CodesErr.PARG_ERR);
            testeCherche(Tokens.ID_TOKEN, CodesErr.ID_ERR);
            if (((ScannerWS)this.getScanner()).getTableSymb().get(((ScannerWS)this.getScanner()).getPlaceSymb()).getClasse()
                    == ClasseIdf.CONSTS)
                throw new ErreurSemantique(CodesErr.ID_NOT_DEFINED_ERR);
            generer2(Mnemonique.LDA, ((ScannerWS) this.getScanner()).getTableSymb().get(((ScannerWS) this.getScanner()).getPlaceSymb()).getAdresse());
            generer1(Mnemonique.INN);
            while (this.scanner.get_symbCour().getToken() == Tokens.VIR_TOKEN) {
                testeAccept(Tokens.VIR_TOKEN, CodesErr.VIR_ERR);
                testeCherche(Tokens.ID_TOKEN, CodesErr.ID_ERR);
                if (((ScannerWS)this.getScanner()).getTableSymb().get(((ScannerWS)this.getScanner()).getPlaceSymb()).getClasse()
                        == ClasseIdf.CONSTS)
                    throw new ErreurSemantique(CodesErr.ID_NOT_DEFINED_ERR);
                generer2(Mnemonique.LDA, ((ScannerWS) this.getScanner()).getTableSymb().get(((ScannerWS) this.getScanner()).getPlaceSymb()).getAdresse());
                generer1(Mnemonique.STO);
            }
            testeAccept(Tokens.PARD_TOKEN, CodesErr.PARD_ERR);

    }
    public void testeInsere(Tokens tokens, ClasseIdf c, CodesErr err) throws Exception {
        if (tokens == this.getScanner().get_symbCour().getToken()){
            ((ScannerWS)this.scanner).chercherSymb();
            if (((ScannerWS)this.scanner).getPlaceSymb() == -1){
                ((ScannerWS)this.scanner).enterSymb(c);
                this.scanner.symbsuiv();
            }
            else throw new ErreurSemantique(CodesErr.ID_ALREADY_FOUND_ERR);
        }
        else throw new ErreurSemantique(err);
    }
    public void testeCherche(Tokens tokens, CodesErr err) throws Exception{
        if (tokens == this.getScanner().get_symbCour().getToken()){
            ((ScannerWS)this.scanner).chercherSymb();
            if (((ScannerWS)this.scanner).getPlaceSymb() == -1){
                throw new ErreurSemantique(CodesErr.ID_NOT_DEFINED_ERR);
            }
            if (((ScannerWS)this.getScanner()).getTableSymb().get(((ScannerWS)this.getScanner()).getPlaceSymb()).getClasse()
                    == ClasseIdf.PROGRAM) throw new ErreurSemantique(CodesErr.ID_ALREADY_FOUND_ERR);
            this.getScanner().symbsuiv();
        }
        else throw new ErreurSemantique(err);
    }

}
