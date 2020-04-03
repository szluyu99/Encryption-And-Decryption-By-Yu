package encryption.数字签名.数字签名2;


import org.apache.commons.codec.binary.Base64;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class RSAUtil {

    private static final int RSA_SIZE_1024 = 1024;

    private static final String ALGORITHM = "SHA1WithRSA";

    /**
     * 生成 RSA 密钥对
     *
     * @param keySize
     * @return: {@link Map<String,Object> }
     * @author: Andy
     * @time: 2019/5/10 16:59
     */
    public static Map<String, Object> createKeyPair(int keySize) {
        KeyPairGenerator keyGen = null;
        try {
            keyGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
//            LOGGER.error("初始化密钥工具异常", e);
            return null;
        }
        keyGen.initialize(keySize, new SecureRandom());
        KeyPair key = keyGen.generateKeyPair();
        PublicKey publicKey = key.getPublic();
        PrivateKey privateKey = key.getPrivate();
        Map map = new HashMap();
        map.put("publicKey", publicKey);
        map.put("privateKey", privateKey);
        map.put("publicKeyBase64", Base64.encodeBase64String(publicKey.getEncoded()));
        map.put("privateKeyBase64", Base64.encodeBase64String(privateKey.getEncoded()));
        return map;
    }

    /**
     * 获得公钥的 Base64 字符串
     *
     * @param publicKey 公钥
     * @return: {@link String }
     * @author: Andy
     * @time: 2019/5/10 17:11
     */
    public static String getBase64PublicKeyString(PublicKey publicKey) {
        return Base64.encodeBase64URLSafeString(publicKey.getEncoded()).trim();
    }

    /**
     * 获得私钥的 Base64 字符串
     *
     * @param privateKey 公钥
     * @return: {@link String }
     * @author: Andy
     * @time: 2019/5/10 17:11
     */
    public static String getBase64PrivateKeyString(PrivateKey privateKey) {
        return Base64.encodeBase64URLSafeString(privateKey.getEncoded()).trim();
    }

    /**
     * 获取公钥
     *
     *
     * @param publicKeyBase64 公钥的 Base64 字符串
     * @return: {@link PublicKey }
     * @author: Andy
     * @time: 2019/5/10 18:05
     */
    public static PublicKey getPublicKey(String publicKeyBase64)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyBase64));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(pubKeySpec);
        return publicKey;
    }

    /**
     * 获取私钥
     *
     *
     * @param privateKeyBase64 私钥的 Base64 字符串
     * @return: {@link PrivateKey }
     * @author: Andy
     * @time: 2019/5/10 18:05
     */
    public static PrivateKey getPrivateKey(String privateKeyBase64)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        PKCS8EncodedKeySpec priKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyBase64));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey priKey = keyFactory.generatePrivate(priKeySpec);
        return priKey;
    }


    /**
     * 使用私钥对数据进行数字签名
     *
     * @param data       需要签名的数据
     * @param privateKey 私钥
     * @return: {@link byte[] }
     * @author: Andy
     * @time: 2019/5/10 17:15
     */
    public static byte[] sign(byte[] data, PrivateKey privateKey)
            throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance(ALGORITHM);
        signature.initSign(privateKey);
        signature.update(data);
        return signature.sign();
    }

    /**
     * 使用私钥对数据进行数字签名
     *
     * @param data       需要签名的字符串
     * @param privateKey 私钥
     * @return: {@link String }
     * @author: Andy
     * @time: 2019/5/10 17:15
     */
    public static String sign(String data, PrivateKey privateKey)
            throws InvalidKeyException, NoSuchAlgorithmException, SignatureException {
        return Base64.encodeBase64URLSafeString(sign(data.getBytes(), privateKey)).trim();
    }

    /**
     * 签名校验
     *
     * @param data      参与签名的数据
     * @param sign      数字签名
     * @param publicKey 公钥
     * @return: {@link boolean }
     * @author: Andy
     * @time: 2019/5/10 17:22
     */
    public static boolean verify(byte[] data, byte[] sign, PublicKey publicKey)
            throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance(ALGORITHM);
        signature.initVerify(publicKey);
        signature.update(data);
        return signature.verify(sign);
    }

    /**
     * 签名校验
     *
     * @param data      参与签名的数据
     * @param sign      数字签名
     * @param publicKey 公钥
     * @return: {@link boolean }
     * @author: Andy
     * @time: 2019/5/10 17:22
     */
    public static boolean verify(String data, String sign, PublicKey publicKey)
            throws InvalidKeyException, NoSuchAlgorithmException, SignatureException {
        return verify(data.getBytes(), Base64.decodeBase64(sign), publicKey);
    }

    /**
     * 获取参与签名的参数的字符串。参数拼接的顺序由 TreeMap 决定。
     *
     * @param paramsMap 参与签名的参数名和参数值的映射
     * @return: {@link String }
     * @author: Andy
     * @time: 2019/5/10 17:43
     */
    public static String getSourceSignData(TreeMap<String, String> paramsMap) {
        StringBuilder paramsBuilder = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> paramEntry : paramsMap.entrySet()) {
            if(!first){
                paramsBuilder.append("&");
            }else {
                first = false;
            }

            paramsBuilder.append(paramEntry.getKey()).append("=").append(paramEntry.getValue());
        }

        return paramsBuilder.toString();
    }


    /**
     * 设计流程：
     * <p>
     * 1、创建密钥对
     * 2、获取参与签名的数据
     * 3、获取参与签名的数据的摘要（MD5值）
     * 4、使用私钥对摘要进行数字签名
     * 5、使用公钥验证签名
     * @author: Andy
     * @time: 2019/5/10 17:32
     */
    public static void main(String[] args) throws InvalidKeySpecException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        //1、创建密钥对
        Map<String, Object> keyPairMap = createKeyPair(RSA_SIZE_1024);
        String publicKeyBase64 = keyPairMap.get("publicKeyBase64").toString();
        String privateKeyBase64 = keyPairMap.get("privateKeyBase64").toString();
        System.out.println(String.format("publicKeyBase64: %s", publicKeyBase64));
        System.out.println(String.format("privateKeyBase64: %s", privateKeyBase64));

        //2、获取参与签名的数据
        //创建 TreeMap, 排序方式为字符串的自然顺序
        TreeMap<String, String> paramsMap = new TreeMap<>();
        paramsMap.put("17210224", "陆振宇");
        String sourceSignData = getSourceSignData(paramsMap);
        System.out.println(String.format("参与签名的数据: %s", sourceSignData));

        //3、获取参与签名的数据的摘要（MD5值）
        String md5Str = Md5Util.encrypt(sourceSignData);
        System.out.println(String.format("参与签名的数据的摘要（MD5值）: %s", md5Str));

        //4、使用私钥对摘要进行数字签名
        String signature = sign(md5Str, getPrivateKey(privateKeyBase64));
        System.out.println(String.format("数字签名: %s", signature));

        //5、使用公钥验证签名
        boolean success = verify(md5Str, signature, getPublicKey(publicKeyBase64));

        System.out.println(String.format("签名验证结果：%s", success));

    }



}
