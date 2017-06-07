/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forensictool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Abeera Usman Malik
 */
public class ForensicTool {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
    System.out.println("             ----MBR READER----");
    System.out.println("RUN THIS PROGRAM WITH ADMINISTRATOR RIGHTS");
        //Pysical Drive Number Input
    Scanner sc=new Scanner(System.in);
    StringBuilder DriveName = new StringBuilder();
    int dr;
    System.out.println("Enter the PhysicalDrive number. e.g 0,1,2..");
    try{
    dr=sc.nextInt();
    DriveName.append("\\\\.\\PhysicalDrive"+dr);
    }
    catch (InputMismatchException a){

    System.out.println("Drive Number Format is incorrect");
    System.exit(0);

}
    String driveName=DriveName.toString();
    System.out.println(driveName);
    
    
        //Boot Sector Input                                                                
String[] parts=diskRead(0,driveName);//diskRead is a function that reads from disk and returns a sector in an array

if((parts[450].equals("EE")) || (parts[450].equals("ee"))){
    System.out.println("The Drive does not have MBR partitioning Style. It has GPT Partitioning Style");
}
else{
        //Partition Details
 System.out.println("          ---MBR Partition Table: Entry 1---");
 long start1,end1,start2,end2,start3,end3,start4,end4,st1,en1;
 partitionType(parts[450]);//patitionType is a function which tells partition type
 String[] newArray = Arrays.copyOfRange(parts, 458, 462);//partition size
 String s1=optimize(reverse(newArray));
  //optimize is the function which removes the extra special characters from newArray that ae added due to copyOfRange() function
 //reverse is the function which reverses the newArray so that size can be calculated
 long a=hex2decimal(s1); //coversion of hexadecimal to decimal
 long size=(((a)*512)/1024)/1024;
 newArray = Arrays.copyOfRange(parts, 447, 450);//starting chs
 if(newArray[0].equals("FE") && newArray[1].equals("FF") && newArray[2].equals("FF"))
     //For partitions which begin or end beyond  the 1024th cylinder, the three CHS bytes should always be filled with: FE FF FF
 {
     String[] newArray1=Arrays.copyOfRange(parts, 454, 458);//Starting LBA
     start1=(hex2decimal(optimize(reverse(newArray1))));
     System.out.println("Starting sector: "+start1);
     end1=(start1+a)-1;
     System.out.println("Ending sector: "+end1);
     System.out.println("Partition Size: "+size+" MB");
     System.out.println("\n");
 }
 else{
 start1=sector(newArray);// sector is the function which returns the exact sector number
 newArray = Arrays.copyOfRange(parts, 451, 454);//ending chs
 end1=sector(newArray);
 System.out.println("Starting sector: "+start1);
 System.out.println("Ending sector: "+end1);
 System.out.println("Partition Size: "+size+" MB");
 System.out.println("\n");
}
        
        
        
 
 System.out.println("          ---MBR Partition Table: Entry 2---");
 partitionType(parts[466]);//partition type
 newArray = Arrays.copyOfRange(parts, 474, 478);//partition size
 s1=optimize(reverse(newArray));
 long b=hex2decimal(s1);
 size=(((b)*512)/1024)/1024;
 newArray = Arrays.copyOfRange(parts, 463, 466);//starting chs
 if(newArray[0].equals("FE") && newArray[1].equals("FF") && newArray[2].equals("FF")){
     String[] newArray1=Arrays.copyOfRange(parts, 470, 474);//Starting LBA
     start2=(hex2decimal(optimize(reverse(newArray1))));
     System.out.println("Starting sector: "+start2);
     end2=(start2+b)-1;
     System.out.println("Ending sector: "+end2);
     System.out.println("Partition Size: "+size+" MB");
     System.out.println("\n");
 }
else{
 start2=sector(newArray);
 newArray = Arrays.copyOfRange(parts, 467, 470);//ending chs
 end2=sector(newArray);

 if(!(parts[466].equals("00"))){
 System.out.println("Starting sector: "+start2);
 System.out.println("Ending sector: "+end2);
 System.out.println("Partition Size: "+size+" MB");}
 System.out.println("\n");
 
        }
 
 
 
 System.out.println("          ---MBR Partition Table: Entry 3---");
 partitionType(parts[482]);//partition type
 newArray = Arrays.copyOfRange(parts, 490, 494);//partition size
 s1=optimize(reverse(newArray));
 long c=hex2decimal(s1);
 size=(((c)*512)/1024)/1024;
 newArray = Arrays.copyOfRange(parts, 479, 482);//starting chs
 if(newArray[0].equals("FE") && newArray[1].equals("FF") && newArray[2].equals("FF")){
     String[] newArray1=Arrays.copyOfRange(parts, 486, 490);//Starting LBA
     start3=(hex2decimal(optimize(reverse(newArray1))));
     System.out.println("Starting sector: "+start3);
     end3=(start3+c)-1;
     System.out.println("Ending sector: "+end3);
     System.out.println("Partition Size: "+size+" MB");
     System.out.println("\n");
 }
 else{
 start3=sector(newArray);
 newArray = Arrays.copyOfRange(parts, 483, 486);//ending chs
 end3=sector(newArray);
 if(!(parts[482].equals("00"))){
 System.out.println("Starting sector: "+start3);
 System.out.println("Ending sector: "+end3);
 System.out.println("Partition Size: "+size+" MB");}
 System.out.println("\n");
 
 }
 
 
 System.out.println("          ---MBR Partition Table: Entry 4---");
 partitionType(parts[498]);// partition type
 newArray = Arrays.copyOfRange(parts, 506, 510);//partition size
 s1=optimize(reverse(newArray));
 long d=hex2decimal(s1);
 size=(((d)*512)/1024)/1024;
 newArray = Arrays.copyOfRange(parts, 495, 498);//starting chs
 if(newArray[0].equals("FE") && newArray[1].equals("FF") && newArray[2].equals("FF")){
     String[] newArray1=Arrays.copyOfRange(parts, 502, 506);//Starting LBA
     start4=(hex2decimal(optimize(reverse(newArray1))));
     System.out.println("Starting sector: "+start4);
     end4=(start4+d)-1;
     System.out.println("Ending sector: "+end4);
     System.out.println("Partition Size: "+size+" MB");
     System.out.println("\n");
 }
 else{
 start4=sector(newArray);
 newArray = Arrays.copyOfRange(parts, 499, 502);//ending chs
 end4=sector(newArray);
 if(!(parts[498].equals("00"))){
 System.out.println("Starting sector: "+start4);
 System.out.println("Ending sector: "+end4);
 System.out.println("Partition Size: "+size+" MB");}
 System.out.println("\n");
 }
 
 
 //MBR is corrupted or Uncorrupted???
 if(!(start2==end2)){//checks is there any other partition or there is just one partition?
     if(start2==(end1+1)){
         if(!(start3==end3)){//checks is there any other partition or there are just two partitions?
             if(start3==(end2+1)){
                 if(!(start4==end4)){//checks is there any other partition or there are just three partitions?
                     if(start4==(end3+1)){
                         System.out.println("MBR is Uncorrupted \n");
                     }else System.out.println("MBR is Corrupted \n");
                 }else System.out.println("MBR is Uncorrupted \n");
             }else System.out.println("MBR is Corrupted \n");
         }else System.out.println("MBR is Uncorrupted \n");
     }else System.out.println("MBR is Corrupted \n");
 }else System.out.println("MBR is Uncorrupted \n");
 
 
 
//Extended Partition details
 if(parts[498].equals("05")){
     int i=1;
     System.out.println("          ---Extended Partitions---");
     do{
     System.out.println("Extended partition "+i);
 parts=diskRead((start4)*512,driveName);
 partitionType(parts[450]);//patition type
 newArray = Arrays.copyOfRange(parts, 458, 462);//partition size
 s1=optimize(reverse(newArray));
 long a1=hex2decimal(s1);
 size=(((a1)*512)/1024)/1024;
 newArray = Arrays.copyOfRange(parts, 447, 450);//starting chs
 if(newArray[0].equals("FE") && newArray[1].equals("FF") && newArray[2].equals("FF")){
     String[] newArray1=Arrays.copyOfRange(parts, 454, 458);//Starting LBA
     st1=(hex2decimal(optimize(reverse(newArray1))));
     System.out.println("Starting sector: "+st1);
     en1=(st1+a1)-1;
     System.out.println("Ending sector: "+en1);
     System.out.println("Partition Size: "+size+" MB");
     System.out.println("\n");
 }
 else{
 st1=sector(newArray)+start4;
 System.out.println("Starting sector: "+st1);
 newArray = Arrays.copyOfRange(parts, 451, 454);//ending chs
 en1=sector(newArray)+start4;
 System.out.println("Ending sector: "+en1);
        System.out.println("Partition Size: "+size+" MB");
        System.out.println("\n"); 
 }
        i++;
        start4=en1+1;
     }while(parts[466].equals("05"));

 }
 

//Size of the Disk
size=(((a+b+c+d)*512)/1024)/1024;
        System.out.println("Total Disk Size: "+size+" MB");
} 
    }
    
public static String[] reverse(String[] validData){
    for(int i = 0; i < validData.length / 2; i++)
{
    String temp = validData[i];
    validData[i] = validData[validData.length - i - 1];
    validData[validData.length - i - 1] = temp;
}
    return validData;
}


public static String optimize(String[] newArray){
    String str=Arrays.toString(newArray);
str=str.replace("[", "");
str=str.replace(",", "");
str=str.replace("]", "");
str=str.replace(" ", "");
    return str;
}


public static int hex2decimal(String s) {
             String digits = "0123456789ABCDEF";
             s = s.toUpperCase();
             int val = 0;
             for (int i = 0; i < s.length(); i++) {
                 char c = s.charAt(i);
                 int d = digits.indexOf(c);
                 val = 16*val + d;
             }
             return val;
         }



public static String hex2binary(String s) {
String result = "";
String binVal;
s=s.toUpperCase();
    for (int i = 0; i < s.length(); i++) {
        char hexChar = s.charAt(i);

        switch (hexChar) {
            case ('0'):
                binVal = "0000";
                break;
            case ('1'):
                binVal = "0001";
                break;
            case ('2'):
                binVal = "0010";
                break;
            case ('3'):
                binVal = "0011";
                break;
            case ('4'):
                binVal = "0100";
                break;
            case ('5'):
                binVal = "0101";
                break;
            case ('6'):
                binVal = "0110";
                break;
            case ('7'):
                binVal = "0111";
                break;
            case ('8'):
                binVal = "1000";
                break;
            case ('9'):
                binVal = "1001";
                break;
            case ('A'):
                binVal = "1010";
                break;
            case ('B'):
                binVal = "1011";
                break;
            case ('C'):
                binVal = "1100";
                break;
            case ('D'):
                binVal = "1101";
                break;
            case ('E'):
                binVal = "1110";
                break;
            case ('F'):
                binVal = "1111";
                break;
            default:
                binVal = "invalid input";
                break;


        }
         result += binVal;
    }
    return result;
}


public static void partitionType(String s){
   
 if(s.equals("07")){ System.out.println("Partition Type: NTFS");
 }
 else if(s.equals("0C")){ System.out.println("Partition Type: FAT32, INT 13 Extensions");
 }
 else if(s.equals("0B")){ System.out.println("Partition Type: FAT32");
 }
 else if(s.equals("06")){ System.out.println("Partition Type: FAT16");
 }
 else if(s.equals("0E")){ System.out.println("Partition Type: FAT");
 }
 else if(s.equals("27")){ System.out.println("Partition Type: Reserved");
 }
 else if(s.equals("82")){ System.out.println("Partition Type: Linux Swap partition");
 }
 else if(s.equals("83")){ System.out.println("Partition Type: Linux native file systems");
 }
 else if(s.equals("42")){ System.out.println("Partition Type: Secure File System");
 }
 else if(s.equals("05")){ System.out.println("Partition Type: Extended");
 }
 else if(s.equals("0F")){ System.out.println("Partition Type: Extended, INT 13 Extensions ");
 }
 else if(s.equals("00")){ System.out.println("Empty Partition Entry");
 }
 else { System.out.println("Unknown Partitioning Style");
 }
}



public static long sector(String[] newArray){
    String hexVar = newArray[1];
    if(hexVar.compareTo("40")<0){ // When sector value is less than 40
    return ((((hex2decimal(newArray[2])*255)+hex2decimal(newArray[0]))*63)+(hex2decimal(newArray[1])-1));// returns sector number
    }
    else{  //When sector greater than or equal to 40
        String sec = hex2binary(newArray[1]);
        String msb = sec.substring(0,2);// extracts 2 most significant bits in a variable msb
        sec=sec.substring(2); //removes 2 most significant bits from sector value
        
        String cylinder = msb+hex2binary(newArray[2]);//attaches msb at the start of the binary value of cylinder
        int s=Integer.parseInt(sec,2);// calculates sector value in decimal
        int c=Integer.parseInt(cylinder,2);//calculates cylinder value in decimal
        return (((c*255)+hex2decimal(newArray[0]))*63)+(s-1);// returns sector number
    }
}


public static String bytesToHex(byte[] in) {
    final StringBuilder builder = new StringBuilder();
    for(byte b : in) {
        builder.append(String.format("%02x", b));
    }
    return builder.toString();
}


public static String[] diskRead(long n, String driveName) throws FileNotFoundException, IOException{
//    Runtime rt = Runtime.getRuntime();
//    Process pr = rt.exec("wmic diskdrive list brief > mDisks.txt");
    String s;
RandomAccessFile raf = null;
StringBuilder result = new StringBuilder();
try{
        raf = new RandomAccessFile(driveName,"r");
        byte [] block = new byte [512];
        raf.seek(n); //Starting point from where disk reading should begin
        raf.readFully(block);
        s=bytesToHex(block); // bytesToHex() converts bytes data to HEX
        for (int i = 0; i < s.length(); i++) {
   if (i > 0 && i%2==0) {
      result.append(" ");
    }
   result.append(s.charAt(i));
}
}
catch(FileNotFoundException e)
{System.out.println("No Such drive exists");
System.exit(0);
}
        s=result.toString();
        s=s.toUpperCase();
                                                                                              
String[] parts = s.split(" ");
return parts;
}

}
