//import Parser.SyntaxError;
//import Token.NoSuchElementException;
//import Token.PlanTokenizer;
//import Parser.PlanParser;
//
//public class Main {
//    public static void main(String[] args) throws SyntaxError, NoSuchElementException {
//        PlanTokenizer tkz;
//        String s = "    lw  0   1   five    load reg1 with 5 (uses symbolic address)\n";
////                "    lw  1   2   3   load reg2 with -1 (uses numeric address)\n" +
////                "start   add 1   2   1   decrement reg1\n" +
////                "    beq 0   1   2   goto end of program when reg1==0\n" +
////                "    beq 0   0   start   go back to the beginning of the loop\n" +
////                "    noop\n" +
////                "done    halt                                  end of program\n" +
////                "five    .fill   5\n" +
////                "neg1    .fill   -1\n" +
////                "stAddr  .fill   start                        will contain the address of start";
//        tkz = new PlanTokenizer(s);
//        while (tkz.hasNextToken()) {
//            System.out.println(tkz.consume());
//        }
//    }
//
//}

//
//public class Main {
//    public static void main(String[] args) throws SyntaxError, NoSuchElementException {
//        PlanTokenizer tkz;
//        String s = "lw 0 1 five"; // Test input
//
//        tkz = new PlanTokenizer(s);
//        PlanParser parser = new PlanParser(tkz);
//
//        try {
//            Plan command = parser.parse(); // Call the general parse method
//            System.out.println("Parsed command successfully: " + command);
//        } catch (SyntaxError | NoSuchElementException e) {
//            System.out.println("Error while parsing: " + e.getMessage());
//        }
//    }
//}

import Parser.SyntaxError;
import Token.NoSuchElementException;
import Token.PlanTokenizer;
import Parser.PlanParser;
import Plan.*;
public class Main {
    public static void main(String[] args) {
        RTypeCommand addCommand = new RTypeCommand("add", "1", "2", "3");
        System.out.println(addCommand);

        ITypeCommand lwCommand = new ITypeCommand("lw", "1", "2", "100");
        System.out.println(lwCommand);

        JTypeCommand jalrCommand = new JTypeCommand("jalr", "1", "2");
        System.out.println(jalrCommand);

        OTypeCommand haltCommand = new OTypeCommand("halt");
        System.out.println(haltCommand);

        FillCommand fillCommand = new FillCommand(".fill", "32");
        System.out.println(fillCommand);
    }
}
