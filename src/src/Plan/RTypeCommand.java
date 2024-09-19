package Plan;

public class RTypeCommand implements Plan {
    private final String instruction;
    private final String regA;
    private final String regB;
    private final String destReg;

    public RTypeCommand(String instruction, String regA, String regB, String destReg) {
        this.instruction = instruction;
        this.regA = regA;
        this.regB = regB;
        this.destReg = destReg;
    }

    public String toBinary() {
        int opcode = instruction.equals("add") ? 0 : 1;  // add = 000, nand = 001
        return BinaryUtils.toBinary(opcode, 3) +           // opcode (3 bits)
                BinaryUtils.toBinary(Integer.parseInt(regA), 3) +  // regA (3 bits)
                BinaryUtils.toBinary(Integer.parseInt(regB), 3) +  // regB (3 bits)
                "000000000000000" +                       // unused (13 bits)
                BinaryUtils.toBinary(Integer.parseInt(destReg), 3); // destReg (3 bits)
    }

    @Override
    public String toString() {
        return instruction + " " + regA + " " + regB + " " + destReg + " -> " + toBinary();
    }
}