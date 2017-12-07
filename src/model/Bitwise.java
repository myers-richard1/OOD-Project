package model;

import java.util.*;
import java.io.UnsupportedEncodingException;
import java.lang.ArrayIndexOutOfBoundsException;
import java.lang.NumberFormatException;

public class Bitwise implements EncryptionStrategy {

     private String str = "";
     private int k;
     private String error;//this is given a value if there was a problem with the encryption (usually because the user entered an invalid key

     public Message encrypt(Message message){
         message.setEncrypted(true);
         byte[] infoBin = null;
         try{
	         int result = Integer.parseInt(message.getKey());
	         //Key is now an integer
	         infoBin = message.getText().getBytes("UTF-8");
         
	         for (byte b : infoBin) {
	             int xor;
	             String z = Integer.toBinaryString(b);
	             int nResult = Integer.parseInt(z);
	             xor = result ^ nResult;
	             message.setText(Integer.toString(xor));
	             str += message.getText() + " ";
	             message.setText(str);
	
	         }
             message.getText().getBytes("UTF-8");
         } catch (Exception e){
        	 error = "Invalid key or malformed message";
         }
         return message;
     }



     public Message decrypt(Message message){
         try{
        	 message.setEncrypted(false);
	         int result = Integer.parseInt(message.getKey());
	         String str = "";
	         String[] splited = message.getText().split("\\s+");
	
	         for(int i = 0; i < splited.length; i++){
	             int nk = Integer.parseInt(splited[i]);
	             k = nk ^ result;
	             message.setText(Integer.toString(k));
	             int nb = Integer.parseInt(message.getText(), 2);
	             str += new Character((char)nb).toString();
	
	         }
	
	         message.setText(str);
         } catch (Exception e){
        	 error="Invalid key or malformed message.";
         }
         return message;
     }
     
     public String getError(){
    	 return error;
     }
 }
