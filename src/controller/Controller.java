package controller;

import model.*;
import model.EncryptionStrategy;
import view.View;

public class Controller {
	private static View view;
	private static EncryptionStrategy currentStrategy;
	
	public static void main(String[] args){
		view = new View();
		view.displayText("Welcome to the message encryptor.");
		view.displayText("Written by Richard Myers, Vinhcent Vu, and Darian Thomas.");
		
		String userInput = view.presentMenu();
		while (!userInput.toLowerCase().equals("q")){
			int strategyChoice = Integer.parseInt(userInput);
			currentStrategy = getStrategyFromChoice(strategyChoice);
			String key = view.getKey();
			boolean encrypted = view.getDirectionChoice();
			String text = view.getMessageText();
			Message message = new Message(text, key, encrypted);
			Message modifiedMessage = null;
			if (encrypted) modifiedMessage = currentStrategy.decrypt(message);
			else try{
				modifiedMessage = currentStrategy.encrypt(message);
			}catch (Exception e){}
			view.displayModifiedMessage(modifiedMessage.getText());
			userInput = view.presentMenu();
		}
		view.displayText("Exiting");
	}
	
	public static EncryptionStrategy getStrategyFromChoice(int choice){
		switch(choice){
		case 0: return new AES();
		case 1: return new Bitwise();
		case 2: return new ElGamal();
		case 3: return new RSA();
		case 4: return new Shift();
		case 5: return new Substitution();
		}
		return null;
	}
	
	
}
