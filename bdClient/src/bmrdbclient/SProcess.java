package bmrdbclient;

import static bmrdbclient.ConV.fin;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SProcess extends Thread {
    protected int port = 0 ;
    protected String host = null ;
    protected ServerSocket SServer = null ;
    protected Socket Sc = null ;
    protected DataInputStream in = null ;
    protected DataOutputStream out = null;
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
    //===========================================
    // Trasfer Big data 
    protected File F = null ;
    protected byte[] tmp = new byte[117] ;
    protected byte[] a = new byte[117] ;
    protected byte[] b = null ; 
    protected byte c = 0 ;
    protected FileOutputStream FOS = null ;
    protected FileOutputStream FOSP = null ;
    protected FileInputStream FIS = null ;
    protected FileInputStream FISP = null ;
    public void Process(String host,int port){
        this.port = port;
        this.host = host;
        try {
            client = new Socket(this.host, this.port);
        } catch (IOException ex) {
            Logger.getLogger(SProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void setHost(String Host){
        this.host = Host ;
    }
    public String getHost(){
        return this.host;
    }
    public void setPort(int port){
        this.port = port;
    }
    public int getPort(){
        return this.port ;
    }
    public void SetUp(){
        this.Con = new ConV();
        this.crypto = new Crypto();
        this.crypto.setUp();
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
            this.privatekey = new byte[inputlength];
            in.readFully(this.privatekey, 0, inputlength);
            int i = 0 ;
        } catch (IOException ex) {
            Logger.getLogger(SProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void recvPublicKey(){
        try {
            int inputlength = in.read();
            this.publickey = new byte[inputlength];
            in.read(this.publickey,0,inputlength);
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
        System.out.println(this.Con.F2B.length);
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
    public void sendBigFile(){
        //File F = new File(this.Pesan);
        int z = 0 ;
        int i = 0 ;
        try {
            F = new File(this.Pesan);
            long Fl  = F.length() / 100; // Jumlah file yang menjadi jumlah tmp
            int FlMod  = (int) (F.length() % 100); // Sisa dari pembagain lebar Fl
            String SFl = Long.toString(F.length()); // 
            byte[] BFl = SFl.getBytes();
            FIS = new FileInputStream(F);
            a  = new byte[(int) F.length()];
            FIS = new FileInputStream(F);
            tmp = new byte[Integer.parseInt(SFl)*100+FlMod];
            System.out.println("Nilai Length : "+SFl+" Nilai length Fl : "+Fl+" Nilai Mod Fl : "+FlMod+" BFl : "+BFl.length);
            try {
                this.out.write(BFl.length);
                this.out.flush();
                this.out.write(BFl);
                this.out.flush();
                this.out.write(FlMod);
                this.out.flush();
            } catch (IOException ex) {
                Logger.getLogger(SProcess.class.getName()).log(Level.SEVERE, null, ex);
            }
            for(i=0;i<=Fl;i++){
                if(i == Fl){
                    try { 
                        b = new byte[FlMod] ; // Membuat Ukuran byte yg constant
                        FIS.read(b, 0, FlMod);
                        this.out.write(FlMod);
                        this.out.flush();
                        this.out.write(b);
                        this.out.flush();
                    } catch (IOException ex) {
                        System.out.println("Fl : "+Fl+" z : "+z+" i : "+i);
                    }
                }else{
                    try {
                        b = new byte[100] ; // Membuat Ukuran byte yg constant
                        FIS.read(b, 0, 100); 
                        this.crypto.setPlainByte(b);
                        b = this.crypto.setEncrypt();
                        this.out.write(b.length);
                        this.out.flush();
                        this.out.write(b,0,b.length);
                        this.out.flush();
                    } catch (IOException ex) {
                        System.out.println("Fl : "+Fl+" z : "+z+" i : "+i);
                    }
                }
            }
        } catch (FileNotFoundException ex) {  }
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
    public void closeSocket(){
        try {
            client.close();
            this.in.close();
            this.out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
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
        //while(run){
            //Scanner PesanS = new Scanner(System.in);
            //System.out.print(">>> ");
            //String pesan = PesanS.nextLine();
            //setCmd(pesan);
            //sendCmd();
            System.out.println("===============================================");
            //setCmd("ls");
            //sendCmd();
            setCmd("a.jpg");
            sendBigFile();
       //}
    }
}
