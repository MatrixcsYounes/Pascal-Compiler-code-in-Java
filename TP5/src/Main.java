import java.io.FileOutputStream;
import java.nio.file.Path;
import Compiler.ParserWS;
import net.mips.interpreter.ParserPcode;
public class Main {

    public static void main(String[] args) throws Exception{

        var mips1scanner = Path.of(System.getProperty("user.dir"), "prog.mips1");
        var resultfile = Path.of(System.getProperty("user.dir"), "results.txt");

        ParserWS parser = new ParserWS(mips1scanner.toString() , new FileOutputStream(resultfile.toString()));
        parser.getScanner().initMotsCles();
        parser.getScanner().lireCar();
        parser.getScanner().symbsuiv();
        parser.program();
        parser.savePcode();
        ParserPcode parserPcode = new ParserPcode(resultfile.toString());
        parserPcode.getScanner().liremot();
        parserPcode.getScanner().symbsuiv();
        parserPcode.pcode();
        parserPcode.getInterpreterPcode().interPcode();
        // while (scanner.get_carCour() != '\0') {
        //     scanner.symbsuiv();
        //    System.out.println(scanner.symbCour.getToken().toString() + " <===> " + scanner.symbCour.getNom());
        //}
    }
}
