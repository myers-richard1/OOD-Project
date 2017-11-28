import java.io.IOException;
//import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.lang.ArrayIndexOutOfBoundsException;
import java.lang.NumberFormatException;

public class Main {
    public static void main(String[] args) throws IOException, ArrayIndexOutOfBoundsException, NumberFormatException{
        Bitwise bit = new Bitwise();

        System.out.println("Enter the message you want encrypted: ");

        Scanner scan = new Scanner(System.in);

        Message mess = new Message(scan.nextLine(), "1111111", false);

        Message m1 = bit.encrypt(mess,mess.getKey());

        bit.decrypt(m1, mess.getKey());

    }
}

