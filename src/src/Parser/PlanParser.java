package Parser;

import Token.NoSuchElementException;
import Token.PlanTokenizer;
import Plan.*;

public class PlanParser implements Parser {
    private final PlanTokenizer tkz;

    public PlanParser(PlanTokenizer tkz) {
        this.tkz = tkz;
    }

    @Override
    public Plan parse() throws SyntaxError, NoSuchElementException {
        String instruction = tkz.consume(); // First token is always an instruction
        switch (instruction) {
            case "add":
                return parseAdd();
            case "nand":
                return parseNand();
            case "lw":
                return parseLw();
            case "sw":
                return parseSw();
            case "beq":
                return parseBeq();
            case "jalr":
                return parseJalr();
            case "halt":
            case "noop":
                return new OTypeCommand(instruction); // No fields
            case ".fill":
                return parseFill();
            default:
                throw new SyntaxError("Unknown instruction: " + instruction);
        }
    }

    private Plan parseAdd() throws NoSuchElementException, SyntaxError {
        String regA = tkz.consume();
        String regB = tkz.consume();
        String destReg = tkz.consume();
        return new RTypeCommand("add", regA, regB, destReg);
    }

    private Plan parseNand() throws NoSuchElementException, SyntaxError {
        String regA = tkz.consume();
        String regB = tkz.consume();
        String destReg = tkz.consume();
        return new RTypeCommand("nand", regA, regB, destReg);
    }

    private Plan parseLw() throws NoSuchElementException, SyntaxError {
        String regA = tkz.consume();
        String regB = tkz.consume();
        String offset = tkz.consume();
        return new ITypeCommand("lw", regA, regB, offset);
    }

    private Plan parseSw() throws NoSuchElementException, SyntaxError {
        String regA = tkz.consume();
        String regB = tkz.consume();
        String offset = tkz.consume();
        return new ITypeCommand("sw", regA, regB, offset);
    }

    private Plan parseBeq() throws NoSuchElementException, SyntaxError {
        String regA = tkz.consume();
        String regB = tkz.consume();
        String offset = tkz.consume();
        return new ITypeCommand("beq", regA, regB, offset);
    }

    private Plan parseJalr() throws NoSuchElementException, SyntaxError {
        String regA = tkz.consume();
        String regB = tkz.consume();
        return new JTypeCommand("jalr", regA, regB);
    }

    private Plan parseFill() throws NoSuchElementException, SyntaxError {
        String value = tkz.consume();
        return new FillCommand(".fill", value);
    }
}
