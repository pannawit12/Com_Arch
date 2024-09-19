package Plan;

public class ITypeCommand implements Plan {
    private final String instruction;
    private final String regA;
    private final String regB;
    private final String offset;

    public ITypeCommand(String instruction, String regA, String regB, String offset) {
        this.instruction = instruction;
        this.regA = regA;
        this.regB = regB;
        this.offset = offset;
    }

    public String toBinary() {
        int opcode;
        switch (instruction) {
            case "lw":
                opcode = 2; // 010
                break;
            case "sw":
                opcode = 3; // 011
                break;
            case "beq":
                opcode = 4; // 100
                break;
            default:
                throw new IllegalArgumentException("Invalid I-type instruction");
        }
        return BinaryUtils.toBinary(opcode, 3) +                          // opcode (3 bits)
                BinaryUtils.toBinary(Integer.parseInt(regA), 3) +          // regA (3 bits)
                BinaryUtils.toBinary(Integer.parseInt(regB), 3) +          // regB (3 bits)
                BinaryUtils.toBinary(Integer.parseInt(offset), 16);        // offset (16 bits)
    }

    @Override
    public String toString() {
        return instruction + " " + regA + " " + regB + " " + offset + " -> " + toBinary();
    }
}