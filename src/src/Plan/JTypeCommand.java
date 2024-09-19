package Plan;

public class JTypeCommand implements Plan {
    private final String instruction;
    private final String regA;
    private final String regB;

    public JTypeCommand(String instruction, String regA, String regB) {
        this.instruction = instruction;
        this.regA = regA;
        this.regB = regB;
    }

    public String toBinary() {
        int opcode = 5; // 101 for jalr
        return BinaryUtils.toBinary(opcode, 3) +                          // opcode (3 bits)
                BinaryUtils.toBinary(Integer.parseInt(regA), 3) +          // regA (3 bits)
                BinaryUtils.toBinary(Integer.parseInt(regB), 3) +          // regB (3 bits)
                "0000000000000000";                                       // unused (16 bits)
    }

    @Override
    public String toString() {
        return instruction + " " + regA + " " + regB + " -> " + toBinary();
    }
}