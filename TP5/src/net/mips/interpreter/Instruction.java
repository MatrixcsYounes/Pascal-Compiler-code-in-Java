package net.mips.interpreter;

public class Instruction {
    Mnemonique mne;
    int suite;

    public Instruction(){
    this.mne = null;
    this.suite = 0;
    }
    public Instruction(Mnemonique mne, int s){
        this.mne = mne;
        this.suite = s;
    }
    public Instruction(Mnemonique mne){
        this.mne = mne;
        this.suite = Integer.MIN_VALUE;
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
