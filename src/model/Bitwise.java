package model;

import java.util.*;
import java.io.UnsupportedEncodingException;
import java.lang.ArrayIndexOutOfBoundsException;
import java.lang.NumberFormatException;

public class Bitwise implements EncryptionStrategy {

     private String str = "";
     private int k;

     public Message encrypt(Message message){
         message.setEncrypted(true);
         byte[] infoBin = null;
         int result = Integer.parseInt(message.getKey());
         //Key is now an integer
         try {
			infoBin = message.getText().getBytes("UTF-8");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

         /*for (byte b : infoBin) {
             //Makes the contents of the array binary
             System.out.println("c:" + (char) b + "-> "
                     + Integer.toBinaryString(b));
         }
*/
         
         for (byte b : infoBin) {
             int xor;
             String z = Integer.toBinaryString(b);
             int nResult = Integer.parseInt(z);
             xor = result ^ nResult;
             message.setText(Integer.toString(xor));
             str += message.getText() + " ";
             message.setText(str);

         }

         try {
         } catch (NumberFormatException e) {
             e.printStackTrace();
         }

         try {
             message.getText().getBytes("UTF-8");
         } catch (UnsupportedEncodingException e) {
             e.printStackTrace();
         }
         return message;
     }



     public Message decrypt(Message message) throws ArrayIndexOutOfBoundsException{
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
         try {

         } catch (ArrayIndexOutOfBoundsException e) {
             e.fillInStackTrace();
         }
         return message;
     }
 }
