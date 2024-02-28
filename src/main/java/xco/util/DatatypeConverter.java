package xco.util;

public class DatatypeConverter {
    private static final char[] hexCode = "0123456789ABCDEF".toCharArray();
    static public String printHexBinary(byte[] data) {
        StringBuilder r = new StringBuilder(data.length * 2);
        byte[] var3 = data;
        int var4 = data.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            byte b = var3[var5];
            r.append(hexCode[b >> 4 & 15]);
            r.append(hexCode[b & 15]);
        }

        return r.toString();
    }
}
