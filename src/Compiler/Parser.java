package Compiler;

public class Parser {
    Scanner scanner;


    public Parser(String file) throws Exception {
        this.scanner = new Scanner(file);
    }

    public void testeAccept(Tokens T, CodesErr C) throws Exception {
        System.out.println(this.scanner.symbCour.getToken() + " : " + this.scanner.symbCour.getNom());
        if (this.scanner.get_symbCour().getToken() == T) {
            if (this.scanner.get_carCour() != Scanner.EOF)
                this.scanner.symbsuiv();
        } else throw new ErreurSyntaxique(C);
    }


    public void program() throws Exception {
            testeAccept(Tokens.PROGRAM_TOKEN, CodesErr.PROGRAMM_ERR);
            testeAccept(Tokens.ID_TOKEN, CodesErr.ID_ERR);
            testeAccept(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
            block();
            testeAccept(Tokens.PNT_TOKEN, CodesErr.PNT_ERR);

    }

    public void block() throws Exception {
        consts();
        vars();
        insts();
    }

    //const A = 5;
    public void consts() throws Exception {
        if (this.scanner.get_symbCour().getToken() == Tokens.CONST_TOKEN) {
            testeAccept(Tokens.CONST_TOKEN, CodesErr.CONST_ERR);
            do {
                testeAccept(Tokens.ID_TOKEN, CodesErr.ID_ERR);
                testeAccept(Tokens.EG_TOKEN, CodesErr.EG_ERR);
                testeAccept(Tokens.NUM_TOKEN, CodesErr.NUM_ERR);
                testeAccept(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
            }
            while (this.scanner.symbCour.getToken() == Tokens.ID_TOKEN);
        }
    }

    public void vars() throws Exception {
        if (this.scanner.get_symbCour().getToken() == Tokens.VAR_TOKEN) {
            testeAccept(Tokens.VAR_TOKEN, CodesErr.VAR_ERR);
            testeAccept(Tokens.ID_TOKEN, CodesErr.ID_ERR);
            while (this.scanner.get_symbCour().getToken() == Tokens.VIR_TOKEN) {
                testeAccept(Tokens.VIR_TOKEN, CodesErr.VIR_ERR);
                testeAccept(Tokens.ID_TOKEN, CodesErr.ID_ERR);
            }
            testeAccept(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
        }
    }

    public void insts() throws Exception {
        testeAccept(Tokens.BEGIN_TOKEN, CodesErr.BEGIN_ERR);
        this.inst();
        while (this.scanner.get_symbCour().getToken() == Tokens.PVIR_TOKEN) {
            testeAccept(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
            this.inst();
        }
        testeAccept(Tokens.END_TOKEN, CodesErr.END_ERR);
    }

    public void inst() throws Exception {
        switch (this.scanner.symbCour.getToken()) {
            case BEGIN_TOKEN -> {
                insts();
            }
            case ID_TOKEN -> {
                affec();
            }
            case IF_TOKEN -> {
                si();
            }
            case WHILE_TOKEN -> {
                tantque();
            }
            case WRITE_TOKEN -> {
                ecrire();
            }
            case READ_TOKEN -> {
                lire();
            }
        }

    }

    public void affec() throws Exception {
        testeAccept(Tokens.ID_TOKEN, CodesErr.ID_ERR);
        testeAccept(Tokens.AFFEC_TOKEN, CodesErr.AFFEC_ERR);
        this.expr();
    }

    public void si() throws Exception {
        if (this.scanner.get_symbCour().getToken() == Tokens.IF_TOKEN) {
            testeAccept(Tokens.IF_TOKEN, CodesErr.IF_ERR);
            this.cond();
            testeAccept(Tokens.THEN_TOKEN, CodesErr.THEN_ERR);
            this.inst();
        }
    }

    public void tantque() throws Exception {
        if (this.scanner.get_symbCour().getToken() == Tokens.WHILE_TOKEN) {
            testeAccept(Tokens.WHILE_TOKEN, CodesErr.WHILE_ERR);
            cond();
            testeAccept(Tokens.DO_TOKEN, CodesErr.DO_ERR);
            inst();
        }
    }

    public void ecrire() throws Exception {
        if (this.scanner.get_symbCour().getToken() == Tokens.WRITE_TOKEN) {
            testeAccept(Tokens.WRITE_TOKEN, CodesErr.WRITE_ERR);
            testeAccept(Tokens.PARG_TOKEN, CodesErr.PARG_ERR);
            expr();
            while (this.scanner.get_symbCour().getToken() == Tokens.VIR_TOKEN) {
                testeAccept(Tokens.VIR_TOKEN, CodesErr.VIR_ERR);
                expr();
            }
            testeAccept(Tokens.PARD_TOKEN, CodesErr.PARD_ERR);
        }
    }

    public void lire() throws Exception {
        if (this.scanner.get_symbCour().getToken() == Tokens.READ_TOKEN) {
            testeAccept(Tokens.READ_TOKEN, CodesErr.READ_ERR);
            testeAccept(Tokens.PARG_TOKEN, CodesErr.PARG_ERR);
            testeAccept(Tokens.ID_TOKEN, CodesErr.ID_ERR);
            while (this.scanner.get_symbCour().getToken() == Tokens.VIR_TOKEN) {
                testeAccept(Tokens.VIR_TOKEN, CodesErr.VIR_ERR);
                testeAccept(Tokens.ID_TOKEN, CodesErr.ID_ERR);
            }
            testeAccept(Tokens.PARD_TOKEN, CodesErr.PARD_ERR);
        }
    }

    public void cond() throws Exception {
        expr();
        relop();
        expr();
    }

    public void relop() throws Exception {
        switch (this.scanner.symbCour.getToken()) {
            case EG_TOKEN -> {
                testeAccept(Tokens.EG_TOKEN, CodesErr.EG_ERR);
            }
            case DIFF_TOKEN -> {
                testeAccept(Tokens.DIFF_TOKEN, CodesErr.DIFF_ERR);
            }
            case INF_TOKEN -> {
                testeAccept(Tokens.INF_TOKEN, CodesErr.INF_ERR);
            }
            case SUP_TOKEN -> {
                testeAccept(Tokens.SUP_TOKEN, CodesErr.SUP_ERR);
            }
            case INFEG_TOKEN -> {
                testeAccept(Tokens.INFEG_TOKEN, CodesErr.INFEG_ERR);
            }
            case SUPEG_TOKEN -> {
                testeAccept(Tokens.SUPEG_TOKEN, CodesErr.SUPEG_ERR);
            }
        }
    }

    public void expr() throws Exception {
        term();
        while (this.scanner.get_symbCour().getToken() == Tokens.PLUS_TOKEN
                || this.scanner.get_symbCour().getToken() == Tokens.MOINS_TOKEN) {
            addop();
            term();
        }
    }

    public void addop() throws Exception {
        switch (this.scanner.symbCour.getToken()) {
            case PLUS_TOKEN -> {
                testeAccept(Tokens.PLUS_TOKEN, CodesErr.PLUS_ERR);
            }
            case MOINS_TOKEN -> {
                testeAccept(Tokens.MOINS_TOKEN, CodesErr.MOINS_ERR);
            }
        }
    }

    public void term() throws Exception {
        fact();
        while (this.scanner.get_symbCour().getToken() == Tokens.MUL_TOKEN
                || this.scanner.get_symbCour().getToken() == Tokens.DIV_TOKEN) {
            mulop();
            fact();
        }
    }

    public void mulop() throws Exception {
        switch (this.scanner.symbCour.getToken()) {
            case MUL_TOKEN -> {
                testeAccept(Tokens.PLUS_TOKEN, CodesErr.MUL_ERR);
            }
            case DIV_TOKEN -> {
                testeAccept(Tokens.DIV_TOKEN, CodesErr.DIV_ERR);
            }
        }
    }

    public void fact() throws Exception {
        switch (this.scanner.symbCour.getToken()) {
            case ID_TOKEN -> {
                testeAccept(Tokens.ID_TOKEN, CodesErr.ID_ERR);
            }
            case NUM_TOKEN -> {
                testeAccept(Tokens.NUM_TOKEN, CodesErr.NUM_ERR);
            }
            case PARG_TOKEN -> {
                testeAccept(Tokens.PARG_TOKEN, CodesErr.PARG_ERR);
                expr();
                testeAccept(Tokens.PARD_TOKEN, CodesErr.PARD_ERR);
            }
        }
    }


    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

}
