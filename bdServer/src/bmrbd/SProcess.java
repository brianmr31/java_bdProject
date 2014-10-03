package bmrbd;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream ;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File ;
import java.io.FileInputStream ;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
    protected DataInputStream in = null;
    protected DataOutputStream out = null ;
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
    //===========================================
    // Transfer big data
    protected File F = null ;
    protected byte[] tmp = null ;
    protected byte[] a = new byte[117] ;
    protected byte[] b = null ;   
    protected byte c = 0 ;
    protected FileOutputStream FOS = null ;
    protected FileOutputStream FOSP = null ;
    protected FileInputStream FIS = null ;
    protected FileInputStream FISP = null ;
    protected int i ;
    protected int z ;
    // ============= Server Open Socket ========================================
    public void Process(){
        try{
            this.SServer = new ServerSocket(this.port);
        }catch(IOException e){
            System.out.println("Error Port ServerSocket ..");
        }
    }
    public void setPort(int port){
        this.port = port;
    }
    public int getPort(){
        return this.port;
    }
    // ============== Server close socket ======================================
    public void closeServer(){
        try {
            //out.write(1);
            //in.read();
            //out.close();
            //in.close();
            this.Sc.close();
            this.SServer.close();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    //=============== import Confersi & Config cripto ==========================
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
        try {
            this.Con.conFiletoByte(this.Pesan);
            this.crypto.setPlainByte(this.Con.getFiletoByte());
            //System.out.println(this.crypto.getPlainText());
            this.PesanC = this.crypto.setEncrypt();
            //System.out.println(this.crypto.getcipherText());
            this.out.write(this.crypto.setEncrypt(), 0, Con.getLengthF2B());
        } catch (IOException ex) {
            Logger.getLogger(SProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void recvFile(String f){
        byte[] C = null;
        int A = 0 , B = 0 ;
        try {
            B = this.in.read();
            A = this.in.read();
            C = new byte[A];
            this.in.read(C, 0, A);   
        } catch (IOException ex) {
           Logger.getLogger(SProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.crypto.setcipherText(C);
        C =this.crypto.setDecrpyt();
        this.Con.setlongWrite(C.length);
        this.Con.setlongWrite(A);
        Con.conBytetoFile(f,C);
    }
    public void recvBigFile(String f){
        int FlMod = 0 ;
        int LLength = 0 ;
        String Length = null ;
        byte[] Lebar = null ;
        DataInputStream test = null;
        try {
            LLength = this.in.read();
            Lebar = new byte[LLength];
            this.in.read(Lebar);
            FlMod = this.in.read();
        } catch (IOException ex) {
            Logger.getLogger(SProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.Con.conBytetoString(Lebar);
        Length = this.Con.getBytetoString();
        Long Fl = Long.parseLong(Length)/100;
        Long FlM = Fl % 100;
        //tmp = new byte[];
        tmp = new byte[100];
        //System.out.println(" Bfl "+LLength+" Length : "+Fl+" Sisa : "+FlMod+" : "+(int)Long.parseLong(Length));
        String nilai ;
        try {
            FOS = new FileOutputStream(f);
        } catch (FileNotFoundException ex) {
            System.out.println("");
            Logger.getLogger(SProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
        for(i=0;i<=/*1*/Fl;i++){       
            try {
                test = new DataInputStream(Sc.getInputStream());
            } catch (IOException ex) {
                Logger.getLogger(SProcess.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(i==Fl){
                try {
                    int a = test.read();
                    b = new byte[a];
                    test.read(b,0,a); // Membaca ditempatkan di tmp buffer (byte)
                    FOS.write(b); // Menulis dalam tmp 
                    FOS.flush();
                 } catch (IOException ex) {
                        System.out.println(" ==> ---------------  File Tidak ditemukan Path"+i);
                 }
            }else{
                try {
                    int a = test.read();
                    b = new byte[a];
                    System.out.println(" i "+i+" a : "+a);
                    test.read(b,0,a); // Membaca ditempatkan di tmp buffer (byte)
                    this.crypto.setcipherText(b);
                    b = this.crypto.setDecrpyt();
                    FOS.write(b); // Menulis dalam tmp 
                    FOS.flush();
                 } catch (IOException ex) {
                        System.out.println(" ==> ---------------  File Tidak ditemukan Path"+i);
                 }
            }
       }
        //System.out.println("....Flush......");
        try {
            FOS.flush(); // Memasukan ke file 
        } catch (IOException ex) {
            System.out.println(" ==> ---------------  File Tidak ditemukan Path"+i);
        }
        //System.out.println("....Selesai..... ");
        try {
            FOS.close();
        } catch (IOException ex) {
            System.out.println(" ==> ---------------  File Tidak ditemukan Path"+i);
        }   
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
        //System.out.println("Connect to "+Sc.getRemoteSocketAddress());
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
        //while(run){
            //try {
                //String dcmd = in.readUTF();
                //System.out.println(">>> "+dcmd);
                
                //PesanS = new Scanner(System.in);
                //System.out.print(">>> ");
                //Pesan = PesanS.nextLine();
                //Execute Command
                //System.out.println("===============================================");
                //recvCmd();
                //execCMD();
                //processOutputCmd();
                //showCmd();
                System.out.println("===============================================");
                //recvFile("bbb.txt");
                recvBigFile("b.jpg");
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
            
       //}
    }
}
