package bmrbd;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream ;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream ;
import java.io.FileOutputStream ;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream ;
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
    public SProcess(){
        this.port = 4444;
        try{
            this.SServer = new ServerSocket(this.port);
        }catch(IOException e){
            System.out.println("Error Port ServerSocket ..");
        }
    }
    public SProcess(int port) {
        this.out = null;
        this.in = null;
        this.port =  port ;
        try{
            this.SServer = new ServerSocket(this.port);
        }catch(IOException e){
            System.out.println("Error Port ServerSocket ..");
        }
    }
    public void SetUp(){
        this.Con = new ConV();
        this.crypto = new Crypto();
        this.crypto.setUp();
        this.crypto.setSig();
    }
    public void setCmd(String p){
        this.Pesan = p;
    }
    public void sendPrivateKey(){
        try {
            out.write(this.crypto.getPrivatekeyByte().length);
            out.flush();
            out.write(this.crypto.getPrivatekeyByte(), 0, this.crypto.getPrivatekeyByte().length);
            out.flush();
            //int i = 0 ;
            //while(i < this.crypto.getPublicKeyByte().length){
            //    System.out.println(this.crypto.getPublicKeyByte()[i]);
            //    i++;
            //}
            System.out.println(this.crypto.getPublicKeyByte());
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
            //int i = 0 ;
            //while(i < this.crypto.getPublicKeyByte().length){
            //    System.out.println(this.crypto.getPublicKeyByte()[i]);
            //    i++;
            //}
            System.out.println(this.crypto.getPublicKeyByte());
        } catch (IOException ex) {
            Logger.getLogger(SProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void recvPrivateKey(){
        try {
            int inputlength = in.readInt();
            this.privatekey = new byte[inputlength];
            in.readFully(this.privatekey, 0, inputlength);
            int i = 0 ;
            //while(i < inputlength){
            //    System.out.println(this.publickey[i]);
            //    i++;
            //}
            System.out.println(this.privatekey);
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
        System.out.println("Pesan : "+this.Pesan);
        this.crypto.setPlainByte(this.Con.conString2Byte(this.Pesan));
        this.Con.conBytetoString(this.crypto.setEncrypt());
        this.PesanC = this.crypto.getcipherText();
        //this.PesanC = this.Con.getBytetoString();
        System.out.println("PesanC : "+this.PesanC);
        System.out.println("P ; "+this.crypto.getPlainText());
        System.out.println("C ; "+this.crypto.getcipherText().length);
        try {
            this.out.write(this.PesanC.length);
            this.out.flush();
            this.out.write(this.PesanC, 0, this.PesanC.length);
            this.out.flush();
        } catch (IOException ex) {
            Logger.getLogger(SProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void recvCmd(){
        int pesanlength = 0 ;
        try {
            pesanlength = this.in.readInt();
            this.cmdInput = new byte[pesanlength];
            this.in.read(this.cmdInput, 0, pesanlength);
        } catch (IOException ex) {
            Logger.getLogger(SProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
        //this.crypto.setcipherText(this.Con.conString2Byte(this.PesanC));
        System.out.println("====================================================");
        System.out.println("Pesan Byte   : "+this.cmdInput);
        System.out.println("Pesan length   : "+pesanlength);
        this.crypto.setcipherText(this.cmdInput);
        this.crypto.setDecrpyt();
        this.Con.conBytetoString(this.crypto.getcipherText());
        this.Pesan = this.Con.getBytetoString();
        System.out.println("Pesan ; "+this.Pesan);
        System.out.println("====================================================");
    }
    
    public void sendFile(){
        //try {
            this.Con.conFiletoByte(this.Pesan);
            this.crypto.setPlainByte(this.Con.getFiletoByte());
            //System.out.println(this.crypto.getPlainText());
            this.PesanC = this.crypto.setEncrypt();
            //System.out.println(this.crypto.getcipherText());
            //this.out.write(this.crypto.setEncrypt(), 0, Con.getLengthF2B());
        //} catch (IOException ex) {
            //Logger.getLogger(SProcess.class.getName()).log(Level.SEVERE, null, ex);
        //}
    }
    public void recvFile(String f){
        byte[] C = null;
        int A = 0 , B = 0 ;
        try {
            B = this.in.read();
            A = this.in.read();
            C = new byte[A];
            this.in.read(C, 0, A);
            //this.crypto.setcipherText(Con.conString2Byte(this.in.readUTF()));
            //this.crypto.setcipherText(Con.conString2Byte(this.crypto.getEncrypt()));
            //System.out.println(this.crypto.getcipherText());
            
        } catch (IOException ex) {
           Logger.getLogger(SProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("===================================================");
        //this.crypto.setPlainByte(C);
        this.crypto.setcipherText(C);
        C =this.crypto.setDecrpyt();
        this.Con.setlongWrite(C.length);
        System.out.println("longwrite : "+C.length);
        System.out.println("Pesan : "+C);
        System.out.println("length: "+A);
        System.out.println("===================================================");
        //System.out.println(this.crypto.getPlainText());
        this.Con.setlongWrite(A);
        Con.conBytetoFile(f,C);
    }
    @Override
    public void run(){
        System.out.println("Connect On Port : "+
            SServer.getLocalPort()+"...");
        try {
            Sc= SServer.accept() ;
        }catch(IOException e){
            System.out.println("Error Accept Server ...");
        }
        System.out.println("Connect to "+Sc.getRemoteSocketAddress());
        try {
            in = new DataInputStream(Sc.getInputStream());
            out= new DataOutputStream(Sc.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(SProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
        sendPublicKey();
        //setCmd("ls -l");
        //sendCmd();
        //try {
        //    out.writeUTF("Hello From Server\n");
        //} catch (IOException ex) {
        //    Logger.getLogger(SProcess.class.getName()).log(Level.SEVERE, null, ex);
        //}
        while(run){
            //try {
                //String dcmd = in.readUTF();
                //System.out.println(">>> "+dcmd);
                
                //PesanS = new Scanner(System.in);
                //System.out.print(">>> ");
                //Pesan = PesanS.nextLine();
            
                recvCmd();
                execCMD();
                processOutputCmd();
                showCmd();
                System.out.println("===============================================");
                //recvFile("bbb.txt");
            //} catch (IOException ex) {
                //try {
                //    Sc.close();
                //    SServer.close();
                //    run = false;
                //    System.out.println("Error Input Output ...");
                //} catch (IOException ex1) {
                //   Logger.getLogger(SProcess.class.getName()).log(Level.SEVERE, null, ex1);
                //}
            //} 
            
       }
    }
}
