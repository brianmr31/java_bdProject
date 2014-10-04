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
    protected Boolean running = false ;
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
        //System.out.println(this.cmdOut);
        this.cmdOut = "" ;
    }
    public void sendCmdOut(){
        System.out.println("Pesan : "+this.cmdOut);
        this.crypto.setPlainByte(this.Con.conString2Byte(this.cmdOut));
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
        this.SetUp();
        sendPublicKey();
        //================================ baru ditambahkan ==================
        recvPublicKey();
        this.crypto.generatorPublicKey(this.publickey);
        running = true ;
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
                //System.out.println("===============================================");
                //recvFile("bbb.txt");
                //recvBigFile("b.jpg");
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
