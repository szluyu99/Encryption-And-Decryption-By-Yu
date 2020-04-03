package app.controller;

import encryption.公开加密.ECC.ECC1.ECC1;
import encryption.公开加密.ECC.ECC2.ECCEnum;
import encryption.公开加密.ECC.ECC2.ECCUtil;
import encryption.公开加密.ECC.ECC2.GenerateKey;
import encryption.公开加密.RSA.RSA;
import encryption.公开加密.RSA.RSA2;
import encryption.对称加密.AES.AES;
import encryption.对称加密.AES.AES2;
import encryption.对称加密.DES.DES;
import encryption.对称加密.DES.DES2;
import encryption.对称加密.IDEA.IDEA;
import encryption.散列算法.CRC32.CRC32;
import encryption.散列算法.MD5.MD5;
import encryption.散列算法.MD5.MD5_2;
import encryption.散列算法.MD5_SHA1;
import encryption.散列算法.SHA1.SHA1_1;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Controller implements Initializable {
    @FXML
    AnchorPane dcjm;
    @FXML
    AnchorPane gkjm;
    @FXML
    AnchorPane slsf;

    @FXML
    TextArea des1_1;
    @FXML
    TextArea des1_2;
    @FXML
    TextArea des1_3;
    @FXML
    TextArea des1_4;
    @FXML
    TextArea des1_5;
    @FXML
    TextArea des1_6;
    @FXML
    TextArea des1_7;
    @FXML
    TextArea des1_8;
    @FXML
    TextArea des1_9;
    @FXML
    TextArea des1_10;
    @FXML
    TextArea des1_11;
    @FXML
    TextArea des1_12;
    @FXML
    TextArea des1_13;
    @FXML
    TextArea des1_14;
    @FXML
    TextArea des1_15;
    @FXML
    TextArea des1_16;
    @FXML
    TextArea des1_17;
    @FXML
    TextArea des1_18;
    @FXML
    TextArea des1_19;
    @FXML
    TextArea des1_20;
    @FXML
    TextArea des1_21;
    @FXML
    TextArea des1_22;
    @FXML
    TextArea des1_23;
    @FXML
    TextArea des1_24;
    @FXML
    TextArea des1_25;
    @FXML
    TextArea des1_26;
    @FXML
    TextArea des1_27;
    @FXML
    TextArea des1_28;
    @FXML
    TextArea des1_29;
    @FXML
    TextArea des1_30;


    @FXML
    TextArea des2_1;
    @FXML
    TextArea des2_2;
    @FXML
    TextArea des2_3;
    @FXML
    TextArea des2_4;
    @FXML
    TextArea des2_5;
    @FXML
    TextArea des2_6;
    @FXML
    TextArea des2_7;
    @FXML
    TextArea des2_8;
    @FXML
    TextArea des2_9;
    @FXML
    TextArea des2_10;
    @FXML
    TextArea des2_11;
    @FXML
    TextArea des2_12;
    @FXML
    TextField des2_13;

    @FXML
    TextArea des2_14;
    @FXML
    TextArea des2_15;
    @FXML
    TextArea des2_16;
    @FXML
    TextArea des2_17;
    @FXML
    TextArea des2_18;
    @FXML
    TextArea des2_19;
    @FXML
    TextArea des2_20;
    @FXML
    TextArea des2_21;
    @FXML
    TextArea des2_22;
    @FXML
    TextArea des2_23;
    @FXML
    TextArea des2_24;
    @FXML
    TextArea des2_25;

    @FXML
    TextArea des3_1;
    @FXML
    TextArea des3_2;
    @FXML
    CheckBox toUpper1;
    @FXML
    TextArea des3_3;
    @FXML
    TextArea des3_4;
    @FXML
    CheckBox toUpper2;
    @FXML
    TextArea des3_5;
    @FXML
    TextArea des3_6;
    @FXML
    CheckBox SHA1;
    @FXML
    CheckBox toUpper3;
    @FXML
    CheckBox withSalt;
    @FXML
    TextArea des3_7;
    @FXML
    TextArea des3_8;
    @FXML
    CheckBox toUpper4;
    @FXML
    TextArea des3_9;
    @FXML
    TextArea des3_10;
    @FXML
    CheckBox toUpper5;

    // 按钮-界面切换
    public void buttonDCJM(){
        dcjm.setVisible(true);
        gkjm.setVisible(false);
        slsf.setVisible(false);
    }
    public void buttonGKJM(){
        gkjm.setVisible(true);
        dcjm.setVisible(false);
        slsf.setVisible(false);
    }
    public void buttonSL(){
        slsf.setVisible(true);
        dcjm.setVisible(false);
        gkjm.setVisible(false);
    }

    // 对称加密
    public void desEncrypt1(){
        String key = des1_1.getText();
        String clear_pwd = des1_2.getText();
        des1_3.setText(DES.DES_CBC_Encrypt(key, clear_pwd));
    }
    public void desDecrypt1(){
        String key = des1_4.getText();
        String secret_pwd = des1_5.getText();
        des1_6.setText(DES.DES_CBC_Decrypt(key, secret_pwd));
    }
    public void desEncrypt2(){
        String key = des1_25.getText();
        String clear_pwd = des1_26.getText();
        des1_27.setText(DES2.encrypt(clear_pwd, key));
    }
    public void desDecrypt2(){
        String key = des1_28.getText();
        String secret_pwd = des1_29.getText();
        des1_30.setText(DES2.decrypt(secret_pwd, key));
    }
    public void aesEncrypt1() throws Exception {
        String key = des1_7.getText();
        String clear_pwd = des1_8.getText();
        des1_9.setText(AES.encrypt(clear_pwd, key));
    }
    public void aesDecrypt1() throws Exception {
        String key = des1_10.getText();
        String secret_pwd = des1_11.getText();
        des1_12.setText(AES.decrypt(secret_pwd, key));
    }
    public void aesEncrypt2(){
        String key = des1_13.getText();
        String clear_pwd = des1_14.getText();
        des1_15.setText(AES2.encrypt(clear_pwd, key));
    }
    public void aesDecrypt2(){
        String key = des1_16.getText();
        String secret_pwd = des1_17.getText();
        des1_18.setText(AES2.decrypt(secret_pwd, key));
    }
    public void ideaEncrypt1(){
        String key = des1_19.getText().trim().equals("") ? IDEA.getKey() : des1_19.getText();
        String clear_pwd = des1_20.getText();
        des1_21.setText(IDEA.ideaEncrypt(clear_pwd, key));
        des1_19.setText(key);
    }
    public void ideaDecrypt1(){
        String key = des1_22.getText();
        String secret_pwd = des1_23.getText();
        des1_24.setText(IDEA.ideaDecrypt(secret_pwd, key));
    }

    // 公开加密
    public void randomKeyPair1() throws NoSuchAlgorithmException {
        RSA.genKeyPair();
        des2_1.setText(RSA.keyMap.get(0)); // 公钥
        des2_2.setText(RSA.keyMap.get(1)); // 私钥
    }
    public void rsaEncrypt1() throws Exception {
        String clear_pwd = des2_3.getText();
        des2_4.setText(RSA.encrypt(clear_pwd,des2_1.getText()));
    }
    public void rsaDecrypt1() throws Exception {
        String secret_pwd = des2_5.getText();
        des2_6.setText(RSA.decrypt(secret_pwd, des2_2.getText()));
    }
    public void randomKeyPair2(){
        int size = Integer.parseInt(des2_13.getText());
        Map<String, String> keyMap = RSA2.createKeys(size);
        des2_7.setText(keyMap.get("publicKey"));
        des2_8.setText(keyMap.get("privateKey"));
    }
    public void rsaEncrypt2() throws InvalidKeySpecException, NoSuchAlgorithmException {
        String clear_pwd = des2_9.getText();
        String encodedData = RSA2.publicEncrypt(clear_pwd, RSA2.getPublicKey(des2_7.getText()));
        des2_10.setText(encodedData);
    }
    public void rsaDecrypt2() throws InvalidKeySpecException, NoSuchAlgorithmException {
        String secret_pwd = des2_11.getText();
        String decodedData = RSA2.privateDecrypt(secret_pwd, RSA2.getPrivateKey(des2_8.getText()));
        des2_12.setText(decodedData);
    }
    KeyPair keyPair = null;
    public void randomKeyPair3() throws Exception {
        keyPair = ECC1.getKeyPair();
        des2_14.setText(ECC1.getPublicKey(keyPair));
        des2_15.setText(ECC1.getPrivateKey(keyPair));
    }
    public void eccEncrypt1() throws Exception {
        String clear_pwd = des2_16.getText();
        byte[] cipherTxt= ECC1.encrypt(clear_pwd.getBytes(), (ECPublicKey) keyPair.getPublic());
        des2_17.setText(Base64.getEncoder().encodeToString(cipherTxt));
    }
    public void eccDecrypt1() throws Exception {
        String secret_pwd = des2_18.getText();
        byte[] clearTxt = ECC1.decrypt(secret_pwd,(ECPrivateKey) keyPair.getPrivate());
        des2_19.setText(new String(clearTxt));
    }
    public void randomKeyPair4() throws NoSuchProviderException, NoSuchAlgorithmException {
        Map<String,String> map = GenerateKey.getGenerateKey();
        String privKey = map.get(ECCEnum.PRIVATE_KEY.value());
        String pubKey = map.get(ECCEnum.PUBLIC_KEY.value());
        des2_20.setText(pubKey);
        des2_21.setText(privKey);
    }
    public void eccEncrypt2() throws Exception {
        String clear_pwd = des2_22.getText();
        byte [] b = ECCUtil.encrypt(clear_pwd.getBytes("UTF-8"), des2_20.getText());
        des2_23.setText(Base64.getEncoder().encodeToString(b));
    }
    public void eccDecrypt2() throws Exception {
        String secret_pwd = des2_24.getText();
        byte[] b = Base64.getDecoder().decode(secret_pwd);
        byte[] decrypt = ECCUtil.decrypt(b, des2_21.getText());
        des2_25.setText(new String(decrypt));
    }

    // 散列算法
    public void MD5_encrypt_1(){
        String clear_pwd = des3_1.getText();
        String secret_pwd = MD5.getInstance().getMD5(clear_pwd);
        if(toUpper1.isSelected()){
            des3_2.setText(secret_pwd.toUpperCase());
        }else{
            des3_2.setText(secret_pwd.toLowerCase());
        }
    }
    public void MD5_encrypt_2(){
        String clear_pwd = des3_3.getText();
        String secret_pwd = new MD5_2().start(clear_pwd);
        if(toUpper2.isSelected()){
            des3_4.setText(secret_pwd.toUpperCase());
        }else{
            des3_4.setText(secret_pwd.toLowerCase());
        }
    }
    public void MD5_SHA_encrypt(){
        String clear_pwd = des3_5.getText();
        String secret_pwd;

        if(SHA1.isSelected() && !withSalt.isSelected()){ // SHA1，无盐
            secret_pwd = MD5_SHA1.MD5WithoutSalt(clear_pwd, "SHA");
        }else if(SHA1.isSelected() && withSalt.isSelected()){ // SHA1，有盐
            secret_pwd = MD5_SHA1.MD5WithSalt(clear_pwd, 0, "SHA");
        }else if(!SHA1.isSelected() && !withSalt.isSelected()){ // MD5，无盐
            secret_pwd = MD5_SHA1.MD5WithoutSalt(clear_pwd, "MD5");
        } else { // MD5，有盐
            secret_pwd = MD5_SHA1.MD5WithSalt(clear_pwd,0, "MD5");
        }

        if(toUpper3.isSelected()){
            des3_6.setText(secret_pwd.toUpperCase());
        }else{
            des3_6.setText(secret_pwd.toLowerCase());
        }
    }
    public void SHA1_encrypt_1() throws Exception {
        String clear_pwd = des3_7.getText();
        String secret_pwd = SHA1_1.shaEncode(clear_pwd);
        if(toUpper4.isSelected()){
            des3_8.setText(secret_pwd.toUpperCase());
        }else{
            des3_8.setText(secret_pwd.toLowerCase());
        }
    }
    public void CRC32_1(){
        String clear_pwd = des3_9.getText();
        String secret_pwd = CRC32.getCRC32(clear_pwd);
        if(toUpper5.isSelected()){
            des3_10.setText(secret_pwd.toUpperCase());
        }else{
            des3_10.setText(secret_pwd.toLowerCase());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
