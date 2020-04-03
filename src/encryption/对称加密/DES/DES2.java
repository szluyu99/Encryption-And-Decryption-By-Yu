package encryption.对称加密.DES;

import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @author tangx
 * 测试des的加密与解密
 */
public class DES2 {

    public static void main(String[] args) {
        String source  = "luzhenyu";
        String key = "17210224";
        String result = encrypt(source, key);
        //加密结果
        System.out.println(result);
        //解密
        System.out.println(decrypt(result, key));
    }

    /**
     * DES加密操作
     * @param source 要加密的源
     * @param key    约定的密钥
     * @return
     */
    public static String encrypt(String source,String key){
        //强加密随机数生成器
        SecureRandom random = new SecureRandom();
        try {
            //创建密钥规则
            DESKeySpec keySpec = new DESKeySpec(key.getBytes());
            //创建密钥工厂
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            //按照密钥规则生成密钥
            SecretKey secretKey =  keyFactory.generateSecret(keySpec);
            //加密对象
            Cipher cipher = Cipher.getInstance("DES");
            //初始化加密对象需要的属性
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, random);
            //开始加密
            byte[] result = cipher.doFinal(source.getBytes());
            //Base64加密
            return  new BASE64Encoder().encode(result) ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 解密
     * @param cryptograph 密文
     * @param key         约定的密钥
     * @return
     */
    public static String decrypt(String cryptograph,String key){
        //强加密随机生成器
        SecureRandom random  =  new SecureRandom();
        try {
            //定义私钥规则
            DESKeySpec keySpec = new DESKeySpec(key.getBytes());
            //定义密钥工厂
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
            //按照密钥规则生成密钥
            SecretKey secretkey = factory.generateSecret(keySpec);
            //创建加密对象
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, secretkey, random);
            //Base64对
            byte[] result = new BASE64Decoder().decodeBuffer(cryptograph);
            return new String(cipher.doFinal(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
