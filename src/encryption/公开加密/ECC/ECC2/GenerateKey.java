package encryption.公开加密.ECC.ECC2;

import org.bouncycastle.jce.interfaces.ECPrivateKey;
import org.bouncycastle.jce.interfaces.ECPublicKey;
import java.io.Serializable;
import java.security.*;
import java.util.HashMap;
import java.util.Map;
 
public class GenerateKey implements Serializable {
 
    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }
 
    public static Map<String,String> getGenerateKey() throws NoSuchProviderException, NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ECCEnum.ALGORITHM.value(),
                ECCEnum.PROVIDER.value());
        keyPairGenerator.initialize(256, new SecureRandom());
        KeyPair kp = keyPairGenerator.generateKeyPair();
        ECPublicKey publicKey = (ECPublicKey) kp.getPublic();
        ECPrivateKey privateKey = (ECPrivateKey) kp.getPrivate();
        Map<String,String> map = new HashMap<>();
 
        map.put(ECCEnum.PRIVATE_KEY.value(), BASE64Encoder.encodeBuffer(privateKey.getEncoded()));
        map.put(ECCEnum.PUBLIC_KEY.value(), BASE64Encoder.encodeBuffer(publicKey.getEncoded()));
        return map;
    }
}