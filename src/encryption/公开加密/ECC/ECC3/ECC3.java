package encryption.公开加密.ECC.ECC3;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Base64;
import javax.crypto.Cipher;
import org.bouncycastle.jce.interfaces.ECPrivateKey;
import org.bouncycastle.jce.interfaces.ECPublicKey;

public class ECC3 {
	public static void main(String[] argu) throws Exception {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC", "BC");
		keyPairGenerator.initialize(256, new SecureRandom());
 
		KeyPair kp = keyPairGenerator.generateKeyPair();
 
		ECPublicKey publicKey = (ECPublicKey) kp.getPublic();
		ECPrivateKey privateKey = (ECPrivateKey) kp.getPrivate();

		System.out.println(kp.getPrivate());
		System.out.println(kp.getPublic());
 
		Cipher encrypter = Cipher.getInstance("ECIES", "BC");
		Cipher decrypter = Cipher.getInstance("ECIES", "BC");
		encrypter.init(Cipher.ENCRYPT_MODE, publicKey);
		decrypter.init(Cipher.DECRYPT_MODE, privateKey);
 
		String text = "17210224luzhenyu";

		byte[] e = encrypter.doFinal(text.getBytes("UTF-8"));
		System.out.println(Base64.getEncoder().encodeToString(e));
		/*System.out.println("Encrypted: " + Arrays.toString(e));*/

		System.out.println("Encrypted, length = " + e.length);
		
		byte[] de = decrypter.doFinal(e);
		String result = new String(de, "UTF-8");
		System.out.println(result);
	}
}