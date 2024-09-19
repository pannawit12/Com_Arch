package Plan;
public class BinaryUtils {
    public static String toBinary(int value, int bits) {
        String binaryString = Integer.toBinaryString(value);
        if (binaryString.length() > bits) {
            // Handle negative numbers by truncating excess bits
            binaryString = binaryString.substring(binaryString.length() - bits);
        } else {
            // Pad with leading zeros
            binaryString = String.format("%" + bits + "s", binaryString).replace(' ', '0');
        }
        return binaryString;
    }
}