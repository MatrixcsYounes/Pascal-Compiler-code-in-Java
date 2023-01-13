package Compiler;

import net.mips.interpreter.Mnemonique;

public class Instruction {
    private Mnemonique mne;
    private int suite;

    public Instruction(){
        this.mne = null;
        this.suite = 0;
    }
    public Instruction(Mnemonique mne, int suite) {
        this.mne = mne;
        this.suite = suite;
    }

    public Mnemonique getMne() {
        return mne;
    }

    public void setMne(Mnemonique mne) {
        this.mne = mne;
    }

    public int getSuite() {
        return suite;
    }

    public void setSuite(int suite) {
        this.suite = suite;
    }
}
