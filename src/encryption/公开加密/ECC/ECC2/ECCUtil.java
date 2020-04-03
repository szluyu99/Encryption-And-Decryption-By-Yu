package encryption.公开加密.ECC.ECC2;

import javax.crypto.Cipher;
import javax.crypto.NullCipher;
import java.io.Serializable;
import java.security.KeyFactory;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
 
/**
 * ecc 加密/解密
 */
public class ECCUtil implements Serializable {
 
 
    /**
     * 加密
     * @param data
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, String publicKey)
            throws Exception {
        byte[] keyBytes = BASE64Decoder.decodeBuffer(publicKey);
 
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ECCEnum.ALGORITHM.value());
 
        ECPublicKey pubKey = (ECPublicKey) keyFactory
                .generatePublic(x509KeySpec);
 
        Cipher cipher = new NullCipher();
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        return cipher.doFinal(data);
    }
 
 
    /**
     * 解密
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = BASE64Decoder.decodeBuffer(privateKey);
 
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ECCEnum.ALGORITHM.value());
 
        ECPrivateKey priKey = (ECPrivateKey) keyFactory
                .generatePrivate(pkcs8KeySpec);
 
        Cipher cipher = new NullCipher();
        cipher.init(Cipher.DECRYPT_MODE, priKey);
 
        return cipher.doFinal(data);
    }
    
    
}