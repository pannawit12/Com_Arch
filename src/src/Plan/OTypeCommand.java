package Plan;

public class OTypeCommand implements Plan {
    private final String instruction;

    public OTypeCommand(String instruction) {
        this.instruction = instruction;
    }

    public String toBinary() {
        int opcode = instruction.equals("halt") ? 6 : 7;  // halt = 110, noop = 111
        return BinaryUtils.toBinary(opcode, 3) +           // opcode (3 bits)
                "0000000000000000000000000";               // unused (25 bits)
    }

    @Override
    public String toString() {
        return instruction + " -> " + toBinary();
    }
}