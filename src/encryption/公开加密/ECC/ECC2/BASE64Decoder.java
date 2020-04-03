package encryption.公开加密.ECC.ECC2;

import java.io.*;
 
public class BASE64Decoder extends FilterInputStream {
 
    private static final char[] chars = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
            'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
            'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', '+', '/'
    };
 
    // A mapping between char values and six-bit integers
    private static final int[] ints = new int[128];
    static {
        for (int i = 0; i < 64; i++) {
            ints[chars[i]] = i;
        }
    }
 
    private int charCount;
    private int carryOver;
 
  
    public BASE64Decoder(InputStream in) {
        super(in);
    }
 
    
    public int read() throws IOException {
        // Read the next non-whitespace character
        int x;
        do {
            x = in.read();
            if (x == -1) {
                return -1;
            }
        } while (Character.isWhitespace((char)x));
        charCount++;
 
        // The '=' sign is just padding
        if (x == '=') {
            return -1;  // effective end of stream
        }
 
        // Convert from raw form to 6-bit form
        x = ints[x];
 
        // Calculate which character we're decoding now
        int mode = (charCount - 1) % 4;
 
        // First char save all six bits, go for another
        if (mode == 0) {
            carryOver = x & 63;
            return read();
        }
        // Second char use previous six bits and first two new bits,
        // save last four bits
        else if (mode == 1) {
            int decoded = ((carryOver << 2) + (x >> 4)) & 255;
            carryOver = x & 15;
            return decoded;
        }
        // Third char use previous four bits and first four new bits,
        // save last two bits
        else if (mode == 2) {
            int decoded = ((carryOver << 4) + (x >> 2)) & 255;
            carryOver = x & 3;
            return decoded;
        }
        // Fourth char use previous two bits and all six new bits
        else if (mode == 3) {
            int decoded = ((carryOver << 6) + x) & 255;
            return decoded;
        }
        return -1;  // can't actually reach this line
    }
 
  
    public int read(byte[] buf, int off, int len) throws IOException {
        if (buf.length < (len + off - 1)) {
            throw new IOException("The input buffer is too small: " + len +
                    " bytes requested starting at offset " + off + " while the buffer " +
                    " is only " + buf.length + " bytes long.");
        }
 
        // This could of course be optimized
        int i;
        for (i = 0; i < len; i++) {
            int x = read();
            if (x == -1 && i == 0) {  // an immediate -1 returns -1
                return -1;
            }
            else if (x == -1) {       // a later -1 returns the chars read so far
                break;
            }
            buf[off + i] = (byte) x;
        }
        return i;
    }
 
   
    public static String decode(String encoded) {
        return new String(decodeToBytes(encoded));
    }
 
   
    public static byte[] decodeToBytes(String encoded) {
        byte[] bytes = null;
        try {
            bytes = encoded.getBytes("8859_1");
        }
        catch (UnsupportedEncodingException ignored) { }
 
        BASE64Decoder in = new BASE64Decoder(
                new ByteArrayInputStream(bytes));
 
        ByteArrayOutputStream out =
                new ByteArrayOutputStream((int) (bytes.length * 0.67));
 
        try {
            byte[] buf = new byte[4 * 1024];  // 4K buffer
            int bytesRead;
            while ((bytesRead = in.read(buf)) != -1) {
                out.write(buf, 0, bytesRead);
            }
            out.close();
 
            return out.toByteArray();
        }
        catch (IOException ignored) { return null; }
    }
 
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: java Base64Decoder fileToDecode");
            return;
        }
 
        BASE64Decoder decoder = null;
        try {
            decoder = new BASE64Decoder(
                    new BufferedInputStream(
                            new FileInputStream(args[0])));
            byte[] buf = new byte[4 * 1024];  // 4K buffer
            int bytesRead;
            while ((bytesRead = decoder.read(buf)) != -1) {
                System.out.write(buf, 0, bytesRead);
            }
        }
        finally {
            if (decoder != null) decoder.close();
        }
    }
 
    public static byte[] decodeBuffer(String key) {
 
        return decodeToBytes(key);
    }
}
 