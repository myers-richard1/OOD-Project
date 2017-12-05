package view;

import java.util.Scanner;

public class View {
	private static Scanner s;
	private static String[] strategyList = {"AES","Bitwise","ElGamal","RSA","Shift","Substitution"};
	
	public View(){
		s = new Scanner(System.in);
	}
	
	public void displayText(String text){
		System.out.println(text);
	}
	
	public String getInput(){
		return s.nextLine();
	}
	
	public String getText(){
		System.out.println("Enter the message");
		return s.nextLine();
	}
	
	public String presentMenu(){
		System.out.println("\n==========================\nPlease choose one of the following encryption/decryption options, or enter Q to quit: ");
		for (int i = 0; i < strategyList.length; i++){
			System.out.println(Integer.toString(i) + " " + strategyList[i]);
		}
		return s.nextLine();
	}
	
	public boolean getDirectionChoice(){
		System.out.println("Enter 1 for encryption, 2 for decryption");
		String input = s.nextLine();
		if (input.equals("1")) return false;
		else if (input.equals("2")) return true;
		return false; //TODO check for invalid input (remove this line)
	}
	
	public String getMessageText(){
		System.out.println("Enter the message: ");
		return s.nextLine();
	}
	
	public String getKey(){
		System.out.println("Enter the key: ");
		return s.nextLine();
	}
	
	public void displayModifiedMessage(String text){
		System.out.println("Your modified message is: " + text);
	}
	
	public void displayKey(String key){
		System.out.println("Your key is: " + key);
	}
}
