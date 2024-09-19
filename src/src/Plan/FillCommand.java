package Plan;

public class FillCommand implements Plan {
    private final String instruction;
    private final String value;

    public FillCommand(String instruction, String value) {
        this.instruction = instruction;
        this.value = value;
    }

    public String toBinary() {
        int fillValue = Integer.parseInt(value);
        return BinaryUtils.toBinary(fillValue, 32);  // Convert the value to 32-bit binary
    }

    @Override
    public String toString() {
        return instruction + " " + value + " -> " + toBinary();
    }
}