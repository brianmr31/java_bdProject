package bmrbd;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public class Crypto {
    private KeyPairGenerator keyGen = null;
    private KeyPair keyPair = null ;
    private PrivateKey privateKey = null ;
    private PublicKey  publicKey = null ;
    private PrivateKey privateKeyC=null;
    private PublicKey publicKeyC=null;
    private byte[] privateKeyBytes = null; 
    private byte[] publicKeyBytes = null ;
    private String PrivateKeyS = null ;
    private String PublicKeyS = null ;
    private byte[] plainText = null ;
    private String plainTextS= null ;
    private Cipher cipher = null ;
    private byte[] cipherText = null ;
    private String EncrpytText = null ;
    private String DecrpytText = null ;
    private Signature sig = null ;
    private byte[] signature = null;
    private KeyFactory keyFactory = null ;
    public Crypto(){
        try {
            this.keyGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Error KeyPairGenerator ...");
            System.exit(0);
        }
        this.keyGen.initialize(1024);
        try {
            this.keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.keyPair = keyGen.genKeyPair();
        this.privateKey = keyPair.getPrivate();
        this.publicKey  = keyPair.getPublic();
    }
    public void generatorPrivatekey(byte[] a){
        try {
            this.privateKeyC = this.keyFactory.generatePrivate(new PKCS8EncodedKeySpec(a));
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void generatorPublicKey(byte[] a){
        try {
            this.publicKeyC = this.keyFactory.generatePublic(new X509EncodedKeySpec(a));
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void setPlainText(String plainText){
        this.plainText = plainText.getBytes();
        this.plainTextS = plainText ;
    }
    public void setPlainByte(byte[] plainText){
        this.plainText = plainText ;
    }
    public String getPlainTextS(){
        return this.plainTextS;
    }
    public byte[] getPlainText(){
        return this.plainText;
    }
    public PrivateKey getPrivatekey(){
        return this.privateKey;
    }
    public byte[] getPrivatekeyByte(){
        return this.privateKey.getEncoded();
    }
    public String getPrivateKeyS(){
        this.privateKeyBytes = this.privateKey.getEncoded();
        this.PrivateKeyS = new String(this.privateKeyBytes);
        return this.PrivateKeyS ;
    }
    public PublicKey getPublickey(){
        return this.publicKey;
    }
    public String getPublicKeyS(){
        this.publicKeyBytes = this.publicKey.getEncoded();
        this.PublicKeyS = new String(this.publicKeyBytes);
        return this.PublicKeyS ;
    }
    public byte[] getPublicKeyByte(){
        return this.publicKey.getEncoded();
    }
    public Cipher getCipText(){
        return this.cipher ;
    }
    public byte[] getcipherText(){
        return this.cipherText ;
    }
    public void setcipherText(byte[] f){
        this.cipherText = f; 
    }
    public void setUp(){
        try {
            this.cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public byte[] setEncrypt(){
        //System.out.println("Start Encrypt RSA ... ");
        try {
            this.cipher.init(Cipher.ENCRYPT_MODE, this.publicKeyC);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            this.cipherText = this.cipher.doFinal(this.plainText);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println("Finish Encrypt RSA ... ");
        return this.cipherText ;
    }
    public String getEncrypt(){
        try {
            this.EncrpytText = new String(this.setEncrypt(),"UTF8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.EncrpytText ;
    }
    public byte[] setDecrpyt(){
        //System.out.println("Start Decrpyt RSA ... ");
        try {
            this.cipher.init(Cipher.DECRYPT_MODE, this.privateKey);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            this.cipherText = this.cipher.doFinal(this.cipherText);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println("Finish Decrpyt RSA ... ");
        return this.cipherText ;
    }
    public String getDecrpyt(){
        try {
            this.DecrpytText = new String(this.setDecrpyt(),"");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.DecrpytText ;
    }
    public void setSig(){
        try {
            sig = Signature.getInstance("MD5withRSA");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void checkSig(){
        try {
            this.sig.initSign(this.privateKey);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            this.sig.update(this.plainText);
        } catch (SignatureException ex) {
            Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            this.signature = this.sig.sign();
        } catch (SignatureException ex) {
            Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            this.sig.initVerify(this.publicKey);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            this.sig.update(this.plainText);
        } catch (SignatureException ex) {
            Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
        }
        try{
            if(this.sig.verify(this.signature)){
                System.out.println("Signature Verified ...");
            }
        }catch(SignatureException e){
            System.out.println("Error Signature ...");
        }
    }
}