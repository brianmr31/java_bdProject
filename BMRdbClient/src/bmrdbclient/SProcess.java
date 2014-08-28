package bmrdbclient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SProcess extends Thread {
    protected int port = 0 ;
    protected ServerSocket SServer = null ;
    protected Socket Sc = null ;
    protected DataInputStream in ;
    protected DataOutputStream out ;
    protected String Pesan = null ;
    protected byte[] PesanC= null ;
    //protected String PesanC= null ;
    protected boolean run = true ;
    //===========================================
    protected InputStream cmdIn = null ;
    protected String cmdOut = null ;
    protected Process p = null ;
    protected ConV Con = null ;
    protected Crypto crypto = null ;
    //===========================================
    protected byte[] publickey = null;
    protected byte[] privatekey= null;
    //===========================================
    protected byte[] cmdInput = null ;
    protected byte[] cmdOuput = null ;
    protected Socket client = null ;
    public SProcess(){
        this.port = 4444;
        this.out = null;
        this.in = null;
        try {
            client = new Socket("localhost", port);
        } catch (IOException ex) {
            Logger.getLogger(SProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public SProcess(int port) {
        this.out = null;
        this.in = null;
        this.port =  port ;
        try {
            client = new Socket("localhost", port);
        } catch (IOException ex) {
            Logger.getLogger(SProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void SetUp(){
        this.Con = new ConV();
        this.crypto = new Crypto();
        this.crypto.setUp();
        //this.crypto.setSig();
    }
    public void setCmd(String p){
        this.Pesan = p;
    }
    public void sendPrivateKey(){
        try {
            out.writeInt(this.crypto.getPrivatekeyByte().length);
            out.flush();
            out.write(this.crypto.getPrivatekeyByte(), 0, this.crypto.getPrivatekeyByte().length);
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(SProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void sendPublicKey(){
        try {
            out.write(this.crypto.getPublicKeyByte().length);
            System.out.println(this.crypto.getPublicKeyByte().length);
            out.flush();
            out.write(this.crypto.getPublicKeyByte(), 0, this.crypto.getPublicKeyByte().length);
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(SProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void recvPrivateKey(){
        try {
            int inputlength = in.readInt();
            in.readFully(this.privatekey, 0, inputlength);
        } catch (IOException ex) {
            Logger.getLogger(SProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void recvPublicKey(){
        try {
            int inputlength = in.read();
            System.out.println(inputlength);
            this.publickey = new byte[inputlength];
            in.read(this.publickey,0,inputlength);
            int i = 0 ;
            //while(i < inputlength){
            //    System.out.println(this.publickey[i]);
            //    i++;
            //}
            System.out.println(this.publickey);
            //System.out.println(this.publickey);
        } catch (IOException ex) {
            Logger.getLogger(SProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public String getCmd(){
        return this.Pesan ;
    }
    public void execCMD(){
        try {
            this.p = Runtime.getRuntime().exec(this.Pesan);
        } catch (IOException ex) {
            Logger.getLogger(SProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void processOutputCmd(){
        try {
            this.cmdIn =  this.p.getInputStream();
            int i = 0 ;
            char A = 0 ;
            try {
                this.p.waitFor();
            } catch (InterruptedException ex) {
                Logger.getLogger(SProcess.class.getName()).log(Level.SEVERE, null, ex);
            }
            while( 0 < cmdIn.available()) {
                if(this.cmdOut == null ){
                    this.cmdOut = "\n"+A ;
                }
                i++;
                A = (char) cmdIn.read();
                this.cmdOut = this.cmdOut + A ;
            }
        } catch (IOException ex) {
            Logger.getLogger(SProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
        p.destroy();
    }
    public void showCmd(){
        System.out.println(this.cmdOut);
    }
    public void sendCmd(){
        System.out.println("=====================================================");
        System.out.println("Pesan : "+this.Pesan);
        this.crypto.setPlainByte(this.Pesan.getBytes());
        this.Con.conBytetoString(this.crypto.setEncrypt());
        this.PesanC = this.crypto.getcipherText();
        //this.PesanC = this.Con.getBytetoString();
        System.out.println("Pesan Byte   : "+this.PesanC);
        System.out.println("Pesan length : "+this.crypto.getcipherText().length);
        System.out.println("=====================================================");
        try {
            this.out.writeInt(this.PesanC.length);
            this.out.flush();
            this.out.write(this.PesanC, 0, this.PesanC.length);
            this.out.flush();
        } catch (IOException ex) {
            Logger.getLogger(SProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendFile(){
        this.Con.conFiletoByte(this.Pesan);
        this.crypto.setPlainByte(this.Con.getFiletoByte());
        //System.out.println(this.crypto.getPlainText());
        this.PesanC = this.crypto.setEncrypt();
        //System.out.println(this.crypto.getcipherText());    
        try {
            this.out.write(this.Con.getLengthF2B());
            this.out.flush();
            this.out.write(this.PesanC.length);
            this.out.flush();
            this.out.write(this.PesanC, 0, this.PesanC.length);
            this.out.flush();
        } catch (IOException ex) {
            Logger.getLogger(SProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("===================================================");
        System.out.println("length : "+this.PesanC.length);
        System.out.println("Pesan : "+this.Con.getFiletoByte());
        this.Con.setlongWrite(this.Con.getLengthF2B());
        this.Con.conBytetoFile("AAA.txt",this.Con.getFiletoByte());
        //System.out.println("Pesan : "+ ));
        System.out.println("longwrite : "+this.Con.getLengthF2B());
        System.out.println("===================================================");
    }
    public void recvFile(String f){
        byte[] C = null;
        //try {
            //this.crypto.setcipherText(Con.conString2Byte(this.in.readUTF()));
            //this.crypto.setcipherText(Con.conString2Byte(this.crypto.getEncrypt()));
            //System.out.println(this.crypto.getcipherText());
            this.crypto.setPlainByte(this.PesanC);
            C =this.crypto.setDecrpyt();
            //System.out.println(this.crypto.getPlainText());
            this.Con.setlongWrite(this.Con.getLengthF2B());
        //} catch (IOException ex) {
        //    Logger.getLogger(SProcess.class.getName()).log(Level.SEVERE, null, ex);
        //}
        Con.conBytetoFile(f,C);
    }
    public void recvCmd(){
        try {
            int pesanlength = this.in.read();
            this.cmdInput = new byte[pesanlength];
            this.in.read(this.cmdInput, 0, pesanlength);
        } catch (IOException ex) {
            Logger.getLogger(SProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
        //this.crypto.setcipherText(this.Con.conString2Byte(this.PesanC));
        //this.crypto.setcipherText(this.PesanC);
        this.crypto.setcipherText(this.cmdInput);
        this.Con.conBytetoString(this.crypto.setDecrpyt());
        this.Pesan = this.Con.getBytetoString();
        System.out.println("PesanC : "+this.cmdInput);
        System.out.println("P ; "+this.crypto.getPlainText());
        System.out.println("C ; "+this.crypto.getcipherText().length);
        //System.out.println("Pesan : "+this.Pesan);
    }
    @Override
    public void run(){
        try {
            in = new DataInputStream(client.getInputStream());
            out= new DataOutputStream(client.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(SProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
        recvPublicKey();
        this.SetUp();
        this.crypto.generatorPublicKey(this.publickey);
        //System.out.println(this.crypto.getPublicKeyByte());
        //recvCmd();
        //try {
        //    String A = in.readUTF();
        //   System.out.println("S : "+A);
        //} catch (IOException ex) {
        //    Logger.getLogger(SProcess.class.getName()).log(Level.SEVERE, null, ex);
        //}
        while(run){
            Scanner PesanS = new Scanner(System.in);
            System.out.print(">>> ");
            String pesan = PesanS.nextLine();
            setCmd(pesan);
            sendCmd();
            System.out.println("===============================================");
            //setCmd("aaa.txt");
            //sendFile();
       }
    }
}
