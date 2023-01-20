package Compiler;
import java.io.IOException;

public class ErreurSyntaxique extends IOException {
    public ErreurSyntaxique(CodesErr message) {
        super(message.getPrivate_Message());
    }

}