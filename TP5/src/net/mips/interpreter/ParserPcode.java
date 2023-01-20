package net.mips.interpreter;
import net.mips.interpreter.InterpreterPcode;
public class ParserPcode {
    private ScannerPcode scanner;
    private InterpreterPcode interpreterPcode;
    public static final char EOF = '\0';


    public ScannerPcode getScanner() {
        return scanner;
    }
    public InterpreterPcode getInterpreterPcode() {
        return interpreterPcode;
    }
    public ParserPcode(String file) throws Exception {
        this.scanner = new ScannerPcode(file);
        this.interpreterPcode = new InterpreterPcode();
    }

    public void testeAccept(Mnemonique mnemonique) throws Exception {
        if (this.scanner.get_symbCour().getToken() == mnemonique) {
            if (this.scanner.get_carCour() != EOF) this.scanner.symbsuiv();
        } else throw new Error("Instruction Invalide!");
    }
    public void pcode() throws Exception {
        testeAccept(Mnemonique.INT);
        testeAccept(Mnemonique.NUM);
        interpreterPcode.loadMnemonic(Mnemonique.INT, Integer.parseInt(this.scanner.get_symbCour().getNom()));
        while (!(this.scanner.get_symbCour().getToken().equals(Mnemonique.INT) || this.scanner.get_symbCour().getToken().equals(Mnemonique.HLT)))
            this.instPcode();
        testeAccept(Mnemonique.HLT);

    }
    public void instPcode() throws Exception {
        switch (this.scanner.symbCour.getToken()) {
            case ADD -> {
                testeAccept(Mnemonique.ADD);
                interpreterPcode.loadMnemonic(Mnemonique.ADD);
            }
            case SUB -> {
                testeAccept(Mnemonique.SUB);
                interpreterPcode.loadMnemonic(Mnemonique.SUB);
            }
            case MUL -> {
                testeAccept(Mnemonique.MUL);
                interpreterPcode.loadMnemonic(Mnemonique.MUL);
            }
            case DIV -> {
                testeAccept(Mnemonique.DIV);
                interpreterPcode.loadMnemonic(Mnemonique.DIV);
            }
            case EQL -> {
                testeAccept(Mnemonique.EQL);
                interpreterPcode.loadMnemonic(Mnemonique.EQL);
            }
            case NEQ -> {
                testeAccept(Mnemonique.NEQ);
                interpreterPcode.loadMnemonic(Mnemonique.NEQ);
            }
            case GTR -> {
                testeAccept(Mnemonique.GTR);
                interpreterPcode.loadMnemonic(Mnemonique.GTR);
            }
            case LSS -> {
                testeAccept(Mnemonique.LSS);
                interpreterPcode.loadMnemonic(Mnemonique.LSS);
            }
            case GEQ -> {
                testeAccept(Mnemonique.GEQ);
                interpreterPcode.loadMnemonic(Mnemonique.GEQ);
            }
            case LEQ -> {
                testeAccept(Mnemonique.LEQ);
                interpreterPcode.loadMnemonic(Mnemonique.LEQ);
            }
            case PRN -> {
                testeAccept(Mnemonique.PRN);
                interpreterPcode.loadMnemonic(Mnemonique.PRN);
            }
            case INN -> {
                testeAccept(Mnemonique.INN);
                interpreterPcode.loadMnemonic(Mnemonique.INN);
            }
            case LDV -> {
                testeAccept(Mnemonique.LDV);
                interpreterPcode.loadMnemonic(Mnemonique.LDV);
            }
            case STO -> {
                testeAccept(Mnemonique.STO);
                interpreterPcode.loadMnemonic(Mnemonique.STO);
            }
            case LDI -> {
                testeAccept(Mnemonique.LDI);
                var num = this.scanner.get_symbCour().getNom();
                testeAccept(Mnemonique.NUM);
                interpreterPcode.loadMnemonic(Mnemonique.LDI, Integer.parseInt(num));
            }
            case LDA -> {
                testeAccept(Mnemonique.LDA);
                var num = this.scanner.get_symbCour().getNom();
                testeAccept(Mnemonique.NUM);
                interpreterPcode.loadMnemonic(Mnemonique.LDI, Integer.parseInt(num));
            }
            case BRN -> {
                testeAccept(Mnemonique.BRN);
                var num = this.scanner.get_symbCour().getNom();
                testeAccept(Mnemonique.NUM);
                interpreterPcode.loadMnemonic(Mnemonique.LDI, Integer.parseInt(num));
            }
            case BZE -> {
                testeAccept(Mnemonique.BZE);
                var num = this.scanner.get_symbCour().getNom();
                testeAccept(Mnemonique.NUM);
                interpreterPcode.loadMnemonic(Mnemonique.LDI, Integer.parseInt(num));
            }
            default -> {
                System.out.println(this.scanner.get_symbCour().nom);
            }
        }

    }





}
