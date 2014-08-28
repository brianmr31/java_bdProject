package bmrbd;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConV {
    protected static FileInputStream fin = null ;
    protected static FileOutputStream fout= null ;
    protected static File f = null ;
    protected String path = null ;
    protected DataInputStream  din = null ;
    protected DataOutputStream dout= null ;
    protected int longwrite = 0;
    protected byte[] F2B = null;
    protected char[] Fc2Bc= null;
    protected int[] Fi2Bi = null;
    protected byte[] B2F = null ;
    protected char[] Bi2Fi=null ;
    protected String[] Dir=null ;
    protected String b2s = null ;
    protected byte[] s2b = null ;
    public void conStringtoByte(String c){
        this.s2b = c.getBytes();
    }
    public byte[] conString2Byte(String c){
        this.s2b = c.getBytes() ;
        return this.s2b;
    }
    public byte[] getStringtoByte(){
        return this.s2b;
    }
    public int getLengthS2B(){
        return this.s2b.length;
    }
    public void conBytetoString(byte[] c){
        try {
            this.b2s = new String(c, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ConV.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public String getBytetoString(){
        return this.b2s ;
    }
    public int getLengthB2S(){
        return this.b2s.length();
    }
    public void conFiletoByte(String f){
        try {
            this.fin = new FileInputStream(f);
        } catch (FileNotFoundException ex) {
            System.out.println("path Not Found ...");
            System.exit(0);
        }
        int i = 0 ,c = 0;
        try {
            Fc2Bc = new char[fin.available()];
            Fi2Bi = new int[fin.available()];
            F2B   = new byte[fin.available()];
        } catch (IOException ex) {
            System.out.println("Error Io ...");
            System.exit(0);
        }
        try {
            while((c = fin.read())!= -1 ){
                this.Fi2Bi[i]= c;
                this.Fc2Bc[i]= (char) c;
                this.F2B[i]= (byte) this.Fi2Bi[i];
                i++;
            }
        } catch (IOException ex) {
            System.out.print("Process File to Byte Error ...");
            System.exit(0);
        }
        try {
            fin.close();
        } catch (IOException ex) {
            Logger.getLogger(ConV.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public byte[] getFiletoByte(){
        return this.F2B;
    }
    public int[] getFiletoInt(){
        return this.Fi2Bi;
    }
    public char[] getFiletoChar(){
        return this.Fc2Bc;
    }
    public int getLengthF2B(){
        return this.F2B.length;
    }
    public int getLengthF2C(){
        return this.Fi2Bi.length;
    }
    public int getLengthF2I(){
        return this.Fi2Bi.length;
    }
    public void setlongWrite(int write){
        this.longwrite = write ;
    }
    public int getlongWrite(){
        return this.longwrite;
    }
    public void conIToFile(String f){
        try {
            this.fout = new FileOutputStream(f);
        } catch (FileNotFoundException ex) {
            System.out.println("path Not Found ...");
            System.exit(0);
        }
    }
    public void conCToFile(){
        try {
            this.fout.close();
        } catch (IOException ex) {
            System.out.println("Close error when write file ...");
            System.exit(0);
        }
    }
    public void conBytetoFile(String f,byte[] by){
        conIToFile(f);
        int i = 0 ;
        while(i < by.length){
            try {
                this.fout.write(by[i]);
            } catch (IOException ex) {
                System.out.println("Error Write File ...");
                System.exit(0);
            }
            i++ ;
        }
        conCToFile();
    }
    public void conChartoFile(String f, char[] by){
        conIToFile(f);
        int i = 0 ;
        while(i < this.longwrite){
            try {
                fout.write(by[i]);
            } catch (IOException ex) {
                Logger.getLogger(ConV.class.getName()).log(Level.SEVERE, null, ex);
            }
            i++ ;
        }
        conCToFile();
    }
    public void coninttoFile(String f, int[] by){
        conIToFile(f);
        int i = 0 ;
        while(i < this.longwrite){
            try {
                fout.write(by[i]);
            } catch (IOException ex) {
                Logger.getLogger(ConV.class.getName()).log(Level.SEVERE, null, ex);
            }
            i++ ;
        }
        conCToFile();
    }
    public String getPath(){
        return this.path;
    }
    public void setpath(String path){
        this.path = path ;
        this.f = new File(path);
    }
    public void listDir(){
        if(this.f.isDirectory()){
            this.Dir = this.f.list();
            for(String i : this.Dir){
                System.out.println(i);
            }
        }else{
            System.out.println("Not Directory ...");
        }
    }
}
