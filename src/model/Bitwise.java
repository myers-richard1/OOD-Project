import java.util.*;
import java.io.UnsupportedEncodingException;
import java.lang.ArrayIndexOutOfBoundsException;
import java.lang.NumberFormatException;

 class Bitwise implements EncryptionStrategy {

     private String str = "";
     private int k;

     public Message encrypt(Message message, String key) throws UnsupportedEncodingException,NumberFormatException {
         message.setEncrypted(true);
         byte[] infoBin;
         int result = Integer.parseInt(key);
         //Key is now an integer
         infoBin = message.getText().getBytes("UTF-8");

         /*for (byte b : infoBin) {
             //Makes the contents of the array binary
             System.out.println("c:" + (char) b + "-> "
                     + Integer.toBinaryString(b));
         }
*/
         System.out.println("This is your new encrypted message");
         
         for (byte b : infoBin) {
             int xor;
             String z = Integer.toBinaryString(b);
             int nResult = Integer.parseInt(z);
             xor = result ^ nResult;
             message.setText(Integer.toString(xor));
             str += message.getText() + " ";
             message.setText(str);

         }

         System.out.println(str);

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



     public Message decrypt(Message message, String key) throws ArrayIndexOutOfBoundsException{
         message.setEncrypted(false);
         int result = Integer.parseInt(key);
         String str = "";
         String[] splited = message.getText().split("\\s+");
         System.out.println("This is the message decrypted");

         for(int i = 0; i < splited.length; i++){
             int nk = Integer.parseInt(splited[i]);
             k = nk ^ result;
             message.setText(Integer.toString(k));
             int nb = Integer.parseInt(message.getText(), 2);
             str += new Character((char)nb).toString();

         }

         System.out.println(str);

         try {

         } catch (ArrayIndexOutOfBoundsException e) {
             e.fillInStackTrace();
         }
         return message;
     }
 }
