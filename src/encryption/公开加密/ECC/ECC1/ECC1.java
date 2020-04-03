package encryption.公开加密.ECC.ECC1;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.security.*;
import java.io.File;
import  java.io.FileInputStream;
import java.lang.reflect.Field;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;

/**
* ECC非对称加密算法，密钥长度为256
*/
public class ECC1 {
  //密钥长度
  private final static int KEY_SIZE=256;

  //生成对应的签名，用来验签
  private final static String SIGNATURE="SHA256withECDSA";

  //注册BouncyCastle加密包
  static{
      Security.addProvider(new BouncyCastleProvider());
  }

  /**
   * 输出BouncyCastleProvider支持的算法，其中就有支持ECC加密的算法
   */
  private static void printProvider(){
      Provider provider=new BouncyCastleProvider();
      for(Provider.Service service:provider.getServices()){
          System.out.println(service.getType()+":"+service.getAlgorithm());
      }
  }

  /**
   * 生成密钥对
   * @return
   * @throws Exception
   */
  public static KeyPair getKeyPair() throws Exception{
      //BC即BouncyCastle加密包，EC为ECC算法
      KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance("EC","BC");
      keyPairGenerator.initialize(KEY_SIZE,new SecureRandom());
      KeyPair keyPair=keyPairGenerator.generateKeyPair();
      return keyPair;
  }

  /**
   * 获取公钥（BASE64编码成字符串后方便用于其他人解码）
   * @param keyPair
   * @return
   */
  public static String getPublicKey(KeyPair keyPair){
      ECPublicKey publicKey=(ECPublicKey)keyPair.getPublic();
      byte[] bytes=publicKey.getEncoded();
      return Base64.getEncoder().encodeToString(bytes);
  }

  /**
   * 获取私钥（Base64编码）
   * @param keyPair
   * @return
   */
  public static String getPrivateKey(KeyPair keyPair){
      ECPrivateKey privateKey=(ECPrivateKey)keyPair.getPrivate();
      byte[] bytes=privateKey.getEncoded();
      return Base64.getEncoder().encodeToString(bytes);
  }

  /**
   * 公钥加密
   * @param content
   * @param publicKey
   * @return
   * @throws Exception
   */
  public static byte[] encrypt(byte[] content,ECPublicKey publicKey) throws Exception{
      Cipher cipher=Cipher.getInstance("ECIES","BC");
      //setFieldValueByFieldName(cipher);
      cipher.init(Cipher.ENCRYPT_MODE,publicKey);
      return cipher.doFinal(content);
  }

  /**
   * 私钥解密
   * @param content
   * @param privateKey
   * @return
   * @throws Exception
   */
  public static byte[] decrypt(String content,ECPrivateKey privateKey) throws Exception{
      //content是采用base64编码后的内容，方便用于传输，下面会解码为byte[]类型的值
      Cipher cipher=Cipher.getInstance("ECIES","BC");
      //setFieldValueByFieldName(cipher);
      cipher.init(Cipher.DECRYPT_MODE,privateKey);
      return cipher.doFinal(Base64.getDecoder().decode(content));
  }

  /**
   * 使用反射解决因美国出口限制，ECC算法的密钥长度不能超过128的问题,如果需要的话，可以加
   * @param object
   */
  private static void setFieldValueByFieldName(Cipher object){
      if(object==null){
          return;
      }
      //获取Obj类的字节文件对像
      Class cipher=object.getClass();
      try{
          //获取该类的成员变量CryptoPermission cryptoPerm;
          Field cipherField=cipher.getDeclaredField("cryptoPerm");
          cipherField.setAccessible(true);
          Object cryptoPerm=cipherField.get(object);

          //获取CryptoPermission类的成员变量maxKeySize
          Class c=cryptoPerm.getClass();

          Field[] cs=c.getDeclaredFields();
          Field cryptoPermField=c.getDeclaredField("maxKeySize");
          cryptoPermField.setAccessible(true);
          //设置maxKeySize的值为257，257>256
          cryptoPermField.set(cryptoPerm,257);
      }catch (Exception e){
          e.printStackTrace();
      }
  }

  /**
   * 私钥签名
   * @param content
   * @param privateKey
   * @return
   */
  public static byte[] sign(String content,ECPrivateKey privateKey) throws Exception {
      Signature signature=Signature.getInstance(SIGNATURE);
      signature.initSign(privateKey);
      signature.update(content.getBytes());
      return signature.sign();
  }

  /**
   * 公钥验签
   * @param content
   * @param sign
   * @param publicKey
   * @return
   * @throws Exception
   */
  public static boolean verify(String content,byte[] sign,ECPublicKey publicKey) throws Exception{
      Signature signature=Signature.getInstance(SIGNATURE);
      signature.initVerify(publicKey);
      signature.update(content.getBytes());
      return signature.verify(sign);
  }

  /**
   * 解析证书的签名算法，单独一本公钥或者私钥是无法解析的，证书的内容远不止公钥或者私钥
   * @param certFile
   * @return
   * @throws Exception
   */
  private static String getSignature(File certFile) throws Exception{
      CertificateFactory certificateFactory=CertificateFactory.getInstance("X.509","BC");
      X509Certificate x509Certificate=(X509Certificate) certificateFactory.generateCertificate(new FileInputStream(certFile));
      return x509Certificate.getSigAlgName();
  }

  /**
   * 将Base64编码后的公钥转换成PublicKey对象，Base64编码后的公钥可以用于网络传输
   * @param pubStr
   * @return
   * @throws Exception
   */
  public static ECPublicKey string2PublicKey(String pubStr) throws Exception{
      byte[] keyBytes=Base64.getDecoder().decode(pubStr);
      X509EncodedKeySpec keySpec=new X509EncodedKeySpec(keyBytes);
      KeyFactory keyFactory=KeyFactory.getInstance("EC","BC");
      ECPublicKey publicKey=(ECPublicKey)keyFactory.generatePublic(keySpec);
      return publicKey;
  }

  /**
   * 将Base64编码后的私钥转换成PrivateKey对象，Base64编码后的私钥可以用于网络传输
   * @param priStr
   * @return
   * @throws Exception
   */
  public static ECPrivateKey string2PrivateKey(String priStr)throws Exception{
      byte[] keyBytes=Base64.getDecoder().decode(priStr);
      PKCS8EncodedKeySpec keySpec=new PKCS8EncodedKeySpec(keyBytes);
      KeyFactory keyFactory=KeyFactory.getInstance("EC","BC");
      ECPrivateKey privateKey=(ECPrivateKey) keyFactory.generatePrivate(keySpec);
      return privateKey;
  }

  public static void main(String[] args){
      try{
          KeyPair keyPair=getKeyPair();
          //生成公钥和私钥对象
          ECPublicKey publicKey=(ECPublicKey) keyPair.getPublic();
          ECPrivateKey privateKey=(ECPrivateKey) keyPair.getPrivate();

          //生成一个Base64编码的公钥字符串，可用来传输
          System.out.println("[publickey]:\t"+getPublicKey(keyPair));
          System.out.println("[privateKey]:\t"+getPrivateKey(keyPair));
          System.out.println();

//          ECPublicKey publicKey2=string2PublicKey("MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAE6G9CUzeBivIkq+m3n6v/hqJylI+lrgbGhJqgLMaQWmotqSlXHcUUUryf0fOFvbLLYHATbAmxWrycptSsydtsYA==");
//          ECPrivateKey privateKey2=string2PrivateKey("MIGTAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBHkwdwIBAQQgkPjCWsEvqDD37uh0UzyWIzUIN+LQOvTLdLw5rv+bfuqgCgYIKoZIzj0DAQehRANCAATob0JTN4GK8iSr6befq/+GonKUj6WuBsaEmqAsxpBaai2pKVcdxRRSvJ/R84W9sstgcBNsCbFavJym1KzJ22xg");

          //测试文本
          String content="luzhenyu";

          //加密
          byte[] cipherTxt=encrypt(content.getBytes(), publicKey);
          System.out.println("content: " + content);

          //解密,此字符串可用来网络传输
          String cipherString=Base64.getEncoder().encodeToString(cipherTxt);
          System.out.println(cipherString);
          byte[] clearTxt = decrypt(cipherString, privateKey);
          System.out.println("clearTxt:"+new String(clearTxt));
      }catch (Exception e){
          e.printStackTrace();
      }
  }
}
