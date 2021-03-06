package controller;

import model.*;
import model.EncryptionStrategy;
import view.View;

public class Controller {
	private static View view;
	private static EncryptionStrategy currentStrategy;
	private static Message currentMessage;
	private static boolean keyWillBeGenerated = false; //used to determine if should ask the user for a key or if it should be generated by the program
	
	public static void main(String[] args){
		//initalize view and greet the user
		view = new View();
		view.displayText("Welcome to the message encryptor.");
		view.displayText("Written by Richard Myers, Vinhcent Vu, and Darian Thomas.");
		
		//show menu and get choice from user
		String userInput = view.presentMenu();
		//while user doesn't want to quit
		while (!userInput.toLowerCase().equals("q")){
			//get the encryption choice from the user and set the current strategy to an instance of the appropriate class
			int strategyChoice = Integer.parseInt(userInput);
			currentStrategy = getStrategyFromChoice(strategyChoice);
			//get the message in its current state from the user
			Message message = null;
			String text = view.getMessageText();
			//ask the user if the message is encrypted
			boolean encrypted = view.getDirectionChoice();
			//if the selected options require a key to be generated, let the user know and create the message object
			if (keyWillBeGenerated && !encrypted){
				System.out.println("Keys will be generated, user selected encryption");
				message = new Message(text, encrypted);
			}
			//else if the user is supplying the key, get the key from them and then create the method object
			else {
				String key = view.getKey();
				message = new Message(text, key, encrypted);
			}
			//if the current strategy requires multiple keys, construct a new MultiKeyMessage (will be downcasted in the strategy)
			if (currentStrategy instanceof ElGamal || currentStrategy instanceof RSA) message = new MultiKeyMessage(message);
			//encrypt or decrypt the message based on the users inputs
			Message modifiedMessage = null;
			if (encrypted) modifiedMessage = currentStrategy.decrypt(message);
			else modifiedMessage = currentStrategy.encrypt(message);
			//if the encryption or decryption process encountered an error, display the error message
			if (currentStrategy.getError() != null){
				view.displayError(currentStrategy.getError());
			}
			//else if everything worked, show the key and message
			else {
				if (!encrypted) view.displayKey(modifiedMessage.getKey());
				view.displayModifiedMessage(modifiedMessage.getText());
				
			}
			//reshow the menu
			userInput = view.presentMenu();
		}
		view.displayText("Exiting");
	}
	
	/**
	 * Converted the user's integer choice into an instance of EncryptionStrategy, and also sets the flag for whether or not keys will be generated by the strategy
	 * @param choice The user's selection
	 * @return The corresponding strategy
	 */
	public static EncryptionStrategy getStrategyFromChoice(int choice){
		switch(choice){
		case 0: keyWillBeGenerated = true; return new AES();
		case 1: keyWillBeGenerated = false; return new Bitwise();
		case 2: keyWillBeGenerated = true; return new ElGamal();
		case 3: keyWillBeGenerated = true; return new RSA();
		case 4: keyWillBeGenerated = false; return new Shift();
		case 5: keyWillBeGenerated = false; return new Substitution();
		}
		return null;
	}
	
	
}
