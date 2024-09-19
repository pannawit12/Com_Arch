package Assembler;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import Plan.*;
import Parser.*;
import Token.*;

public class Assembler {

    private final Tokenizer tokenizer;
    private final Map<String, Integer> labelAddressMap;  // Maps labels to addresses
    private final Map<Integer, String> program;          // Stores machine code for each address
    private final Map<String, Integer> opcodes;          // Maps opcodes to their binary value

    public Assembler(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
        this.labelAddressMap = new HashMap<>();
        this.program = new HashMap<>();
        this.opcodes = new HashMap<>();
        initializeOpcodes();
    }

    // Initialize opcode mappings
    private void initializeOpcodes() {
        opcodes.put("add", 0);   // R-type
        opcodes.put("nand", 1);  // R-type
        opcodes.put("lw", 2);    // I-type
        opcodes.put("sw", 3);    // I-type
        opcodes.put("beq", 4);   // I-type
        opcodes.put("jalr", 5);  // J-type
        opcodes.put("halt", 6);  // O-type
        opcodes.put("noop", 7);  // O-type
    }

    // First pass: map labels to addresses
    public void firstPass() throws SyntaxError, NoSuchElementException, Token.NoSuchElementException {
        int address = 0;
        while (tokenizer.hasNextToken()) {
            String token = tokenizer.peek();
            if (token.endsWith(":")) {
                String label = token.substring(0, token.length() - 1);
                if (labelAddressMap.containsKey(label)) {
                    throw new SyntaxError("Duplicate label: " + label);
                }
                labelAddressMap.put(label, address);
                tokenizer.consume();  // Consume the label
            }
            tokenizer.consume(); // Move to the instruction
            address++;
        }
        tokenizer.reset(); // Reset tokenizer for second pass
    }

    // Second pass: translate to machine code
    public void secondPass() throws SyntaxError, NoSuchElementException, Token.NoSuchElementException {
        int address = 0;
        while (tokenizer.hasNextToken()) {
            String instruction = tokenizer.consume();
            if (instruction.endsWith(":")) {
                instruction = tokenizer.consume();  // Skip label
            }

            int machineCode;
            switch (instruction) {
                case "add":
                case "nand":
                    machineCode = parseRType(instruction);
                    break;
                case "lw":
                case "sw":
                case "beq":
                    machineCode = parseIType(instruction, address);
                    break;
                case "jalr":
                    machineCode = parseJType(instruction);
                    break;
                case "halt":
                case "noop":
                    machineCode = parseOType(instruction);
                    break;
                case ".fill":
                    machineCode = parseFill();
                    break;
                default:
                    throw new SyntaxError("Invalid opcode: " + instruction);
            }

            program.put(address, String.format("%d", machineCode));
            address++;
        }
    }

    // Parse R-type instructions
    private int parseRType(String instruction) throws SyntaxError, NoSuchElementException, Token.NoSuchElementException {
        int opcode = opcodes.get(instruction) << 22;
        int regA = Integer.parseInt(tokenizer.consume()) << 19;
        int regB = Integer.parseInt(tokenizer.consume()) << 16;
        int destReg = Integer.parseInt(tokenizer.consume());
        return opcode | regA | regB | destReg;  // Combine all parts into the final machine code
    }

    // Parse I-type instructions (lw, sw, beq)
    private int parseIType(String instruction, int currentAddress) throws SyntaxError, NoSuchElementException, Token.NoSuchElementException {
        int opcode = opcodes.get(instruction) << 22;
        int regA = Integer.parseInt(tokenizer.consume()) << 19;
        int regB = Integer.parseInt(tokenizer.consume()) << 16;
        String offsetStr = tokenizer.consume();
        int offset;

        if (isNumeric(offsetStr)) {
            offset = Integer.parseInt(offsetStr);
        } else {
            if (!labelAddressMap.containsKey(offsetStr)) {
                throw new SyntaxError("Undefined label: " + offsetStr);
            }
            int labelAddress = labelAddressMap.get(offsetStr);
            offset = labelAddress - (currentAddress + 1);  // Calculate offset
        }

        // Check for offset field overflow (16-bit 2's complement)
        if (offset < -32768 || offset > 32767) {
            throw new SyntaxError("Offset out of range: " + offset);
        }

        offset &= 0xFFFF;  // Ensure it fits in 16 bits
        return opcode | regA | regB | offset;
    }

    // Parse J-type instruction (jalr)
    private int parseJType(String instruction) throws NoSuchElementException, SyntaxError, Token.NoSuchElementException {
        int opcode = opcodes.get(instruction) << 22;
        int regA = Integer.parseInt(tokenizer.consume()) << 19;
        int regB = Integer.parseInt(tokenizer.consume()) << 16;
        return opcode | regA | regB;
    }

    // Parse O-type instructions (halt, noop)
    private int parseOType(String instruction) {
        int opcode = opcodes.get(instruction) << 22;
        return opcode;
    }

    // Parse .fill directive
    private int parseFill() throws SyntaxError, Token.NoSuchElementException {
        String value = tokenizer.consume();
        if (isNumeric(value)) {
            return Integer.parseInt(value);
        } else if (labelAddressMap.containsKey(value)) {
            return labelAddressMap.get(value);
        } else {
            throw new SyntaxError("Undefined label in .fill: " + value);
        }
    }

    // Check if string is numeric
    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Run assembler
    public void assemble() {
        try {
            firstPass();
            secondPass();
            printProgram();
        } catch (SyntaxError | NoSuchElementException | Token.NoSuchElementException e) {
            System.err.println("Error during assembly: " + e.getMessage());
            System.exit(1);
        }
        System.exit(0);
    }

    // Output the final program
    private void printProgram() {
        for (int i = 0; i < program.size(); i++) {
            System.out.println(program.get(i));
        }
    }

    public static void main(String[] args) {
        Tokenizer tokenizer = new Tokenizer("assembly code here...");
        Assembler assembler = new Assembler(tokenizer);
        assembler.assemble();
    }
}
