import java.nio.file.Path;
import Compiler.ParserWS;
public class Main {

    public static void main(String[] args) throws Exception{

        var mips1scanner = Path.of(System.getProperty("user.dir"), "prog.mips1");

        ParserWS parser = new ParserWS(mips1scanner.toString() , System.out);

        parser.getScanner().initMotsCles();
        parser.getScanner().lireCar();
        parser.getScanner().symbsuiv();
        parser.program();
        parser.savePcode();
        // while (scanner.get_carCour() != '\0') {
        //     scanner.symbsuiv();
        //    System.out.println(scanner.symbCour.getToken().toString() + " <===> " + scanner.symbCour.getNom());
        //}
    }
}
