package encryption.数字签名.数字签名1;

import org.apache.commons.codec.binary.Hex;

import java.security.*;
import java.security.interfaces.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Sign {

    public static String getPublicKey(KeyPair keyPair){
        ECPublicKey publicKey=(ECPublicKey)keyPair.getPublic();
        byte[] bytes=publicKey.getEncoded();
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static String getPrivateKey(KeyPair keyPair){
        ECPrivateKey privateKey=(ECPrivateKey)keyPair.getPrivate();
        byte[] bytes=privateKey.getEncoded();
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static void RSA(String text) throws Exception {
        String password = text;
        // 1.初始化密钥
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(512);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey rsaPublicKey = (RSAPublicKey)keyPair.getPublic();
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey)keyPair.getPrivate();
        //////////////////////////////////////////////////////////////////
        System.out.println("生成的公钥：" + rsaPublicKey); // 生成的公钥
        System.out.println("生成的私钥：" + rsaPrivateKey); // 生成的私钥
        //////////////////////////////////////////////////////////////////
        // 2.进行签名
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        // 获取私钥
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        //////////////////////////////////////////////////////////////////
        System.out.println("获取私钥" + privateKey); // 输出对比
        //////////////////////////////////////////////////////////////////
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initSign(privateKey);
        signature.update(password.getBytes());
        byte[] result = signature.sign();
        System.out.println("**************************************");
        System.out.println("签名：" + Hex.encodeHexString(result) );
        System.out.println("**************************************");
        // 3.验证签名
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
        keyFactory = KeyFactory.getInstance("RSA");
        // 获取公钥
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        //////////////////////////////////////////////////////////////////
        System.out.println("获取公钥：" + publicKey); // 输出对比
        //////////////////////////////////////////////////////////////////
        signature = Signature.getInstance("MD5withRSA");
        signature.initVerify(publicKey);
        signature.update(password.getBytes());
        boolean bool = signature.verify(result);
        System.out.println("验证签名：" + bool );
    }

    public static void DSA(String text) throws Exception{
        String password = text;
        // 1.初始化密钥
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DSA");
        keyPairGenerator.initialize(512);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        DSAPublicKey dsaPublicKey = (DSAPublicKey)keyPair.getPublic();
        DSAPrivateKey dsaPrivateKey = (DSAPrivateKey)keyPair.getPrivate();
        //////////////////////////////////////////////////////////////////
        System.out.println("生成的公钥：" + dsaPublicKey); // 生成的公钥
        System.out.println("生成的私钥：" + dsaPrivateKey); // 生成的私钥
        //////////////////////////////////////////////////////////////////

        // 2.进行签名
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(dsaPrivateKey.getEncoded());
        KeyFactory keyFactory = KeyFactory.getInstance("DSA");
        // 获取私钥
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        //////////////////////////////////////////////////////////////////
        System.out.println("获取私钥" + privateKey); // 输出对比
        //////////////////////////////////////////////////////////////////
        Signature signature = Signature.getInstance("SHA1withDSA");
        signature.initSign(privateKey);
        signature.update(password.getBytes());
        byte[] result = signature.sign();
        System.out.println("**************************************");
        System.out.println("签名：" + Hex.encodeHexString(result) );
        System.out.println("**************************************");
        // 3.验证签名
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(dsaPublicKey.getEncoded());
        keyFactory = KeyFactory.getInstance("DSA");
        // 获取公钥
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        //////////////////////////////////////////////////////////////////
        System.out.println("获取公钥：" + publicKey); // 输出对比
        //////////////////////////////////////////////////////////////////
        signature = Signature.getInstance("SHA1withDSA");
        signature.initVerify(publicKey);
        signature.update(password.getBytes());
        boolean bool = signature.verify(result);
        System.out.println("验证签名：" + bool );

    }

    public static void ECDSA(String text) throws  Exception{
        String password = text;
        // 1.初始化密钥
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
        keyPairGenerator.initialize(256);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        ECPublicKey ecPublicKey = (ECPublicKey)keyPair.getPublic();
        ECPrivateKey ecPrivateKey = (ECPrivateKey)keyPair.getPrivate();
        //////////////////////////////////////////////////////////////////
        System.out.println("生成的公钥：" + ecPublicKey); // 生成的公钥
        System.out.println("生成的私钥：" + ecPrivateKey); // 生成的私钥
        //////////////////////////////////////////////////////////////////
        // 2.进行签名（私钥加密）
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(ecPrivateKey.getEncoded());
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        // 获取私钥
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        //////////////////////////////////////////////////////////////////
        System.out.println("获取私钥" + privateKey); // 输出对比
        //////////////////////////////////////////////////////////////////
        Signature signature = Signature.getInstance("SHA1withECDSA");
        signature.initSign(privateKey);
        signature.update(password.getBytes());
        byte[] result = signature.sign();
        System.out.println("**************************************");
        System.out.println("签名：" + Hex.encodeHexString(result) );
        System.out.println("**************************************");
        // 3.验证签名（公钥解密）
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(ecPublicKey.getEncoded());
        keyFactory = KeyFactory.getInstance("EC");
        // 获取公钥
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        //////////////////////////////////////////////////////////////////
        System.out.println("获取公钥：" + publicKey); // 输出对比
        //////////////////////////////////////////////////////////////////
        signature = Signature.getInstance("SHA1withECDSA");
        signature.initVerify(publicKey);
        signature.update(password.getBytes());

        boolean bool = signature.verify(result);
        System.out.println("验证签名：" + bool );
    }

    public static void main(String[] args) throws Exception {
        String text = "17210224陆振宇";
        // RSA(text);
        // DSA(text);
        ECDSA(text);
    }

}
