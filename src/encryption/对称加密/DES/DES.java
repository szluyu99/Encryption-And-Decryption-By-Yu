package encryption.对称加密.DES;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;


public class DES {

    public static String DES_CBC_Encrypt(String secretKey, String str) {
        try {
            byte[] keyBytes = secretKey.getBytes();
            byte[] content = str.getBytes();
            DESKeySpec keySpec = new DESKeySpec(keyBytes);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(keySpec);

            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(keySpec.getKey()));
            byte[] result = cipher.doFinal(content);
            return byteToHexString(result);
        } catch (Exception e) {
            System.out.println("exception:" + e.toString());
        }
        return null;
    }

    public static String DES_CBC_Decrypt(String secretKey, String str) {
        try {
            byte[] keyBytes = secretKey.getBytes();
            byte[] content = hexToByteArray(str);
            DESKeySpec keySpec = new DESKeySpec(keyBytes);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(keySpec);

            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(keyBytes));
            byte[] result = cipher.doFinal(content);
            return new String(result);
        } catch (Exception e) {
            System.out.println("exception:" + e.toString());
        }
        return null;
    }

    private static String byteToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length);
        String sTemp;
        for (byte aByte : bytes) {
            sTemp = Integer.toHexString(0xFF & aByte);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    private static byte[] hexToByteArray(String inHex) {
        int hexLen = inHex.length();
        byte[] result;
        if (hexLen % 2 == 1) {
            hexLen++;
            result = new byte[(hexLen / 2)];
            inHex = "0" + inHex;
        } else {
            result = new byte[(hexLen / 2)];
        }
        int j = 0;
        for (int i = 0; i < hexLen; i += 2) {
            result[j] = (byte) Integer.parseInt(inHex.substring(i, i + 2), 16);
            j++;
        }
        return result;
    }

    public static void main(String[] o) {
        String secretKey = "17210224"; // 密钥
        System.out.println("密钥：" + secretKey);
        String str = "luzhenyu"; // 加密内容
        String secret_pwd = DES.DES_CBC_Encrypt(secretKey, str);
        System.out.println("密文：" + secret_pwd);
        String clear_pwd = DES.DES_CBC_Decrypt(secretKey, secret_pwd);
        System.out.println("明文：" + clear_pwd);
    }

}


