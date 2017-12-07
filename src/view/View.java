package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class View {
	private static Scanner s;
	private static String[] strategyList = {"AES","Bitwise","ElGamal","RSA","Shift","Substitution"};
	
	//Initialize scanner
	public View(){
		s = new Scanner(System.in);
	}
	
	//Wrapper function for println to be called by controller
	public void displayText(String text){
		System.out.println(text);
	}
	
	//Wrapper function for scanner to be called by controller
	public String getInput(){
		return s.nextLine();
	}
	
	/**
	 * Displays the menu and gets the user's selection
	 * @return The user's selection as a string
	 */
	public String presentMenu(){
		//display the options
		System.out.println("\n==========================\nPlease choose one of the following encryption/decryption options, or enter Q to quit: ");
		for (int i = 0; i < strategyList.length; i++){
			System.out.println(Integer.toString(i) + " " + strategyList[i]);
		}
		//get user input and check if it's valid
		String input = s.nextLine();
		ArrayList<String> validOptions = new ArrayList<String>(Arrays.asList(new String[]{"0","1","2","3","4","5","q","Q"}));
		while (!validOptions.contains(input)){
			System.out.println("Invalid selection. Please choose from the above.");
			input = s.nextLine();
		}
		return input;
	}
	
	/**
	 * Asks the user to choose encryption or decryption. 
	 * @return Whether or not the message is encrypted. 
	 * (False if the user wants to encrypt, true if the user wants to decrypt)
	 */
	public boolean getDirectionChoice(){
		//Display options, validate input, and return choice
		System.out.println("Enter 1 for encryption, 2 for decryption");
		String input = s.nextLine();
		while (!input.equals("1") && !input.equals("2")){
			System.out.println("Invalid choice. Enter 1 for encryption or 2 for decryption");
			input = s.nextLine();
		}
		if (input.equals("1")) return false;
		else return true;
		
	}
	
	/**
	 * Gets the message from the user
	 * @return The string that represents the message the user wants to encrypt or decrypt
	 */
	public String getMessageText(){
		System.out.println("Enter the message: ");
		return s.nextLine();
	}
	
	
	/**
	 * Gets encryption key from the user
	 * @return The key(s) the user entered
	 */
	public String getKey(){
		System.out.println("Enter the key(s): ");
		return s.nextLine();
	}
	
	/**
	 * Displays the message after it's been processed by the encryption strategies
	 * @param text The text of the message after it's been processed
	 */
	public void displayModifiedMessage(String text){
		System.out.println("Your modified message is: " + text);
	}
	
	/**
	 * Displays the key 
	 * @param key The key to display
	 */
	public void displayKey(String key){
		System.out.println("Your key is: " + key);
	}
	
	/**
	 * Displays an error message
	 * @param errorMessage The error message to be displayed. If null, a generic message will be displayed
	 */
	public void displayError(String errorMessage){
		if (errorMessage == null) System.out.println("There was an error in your inputs and the message was unable to be processed.");
		else System.out.println(errorMessage);
	}
}
