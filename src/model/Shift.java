package model;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

public class Shift implements EncryptionStrategy{
	private static final String alphabet = "abcdefghijklmnopqrstuvwxyz";
	private String error;//this is given a value if there was a problem with the encryption (usually because the user entered an invalid key
	
	@Override
	public Message encrypt(Message message){
		System.out.println("Encrypting the message");
		return shift(message);
	}

	@Override
	public Message decrypt(Message message){
		return shift(message);
	}
	
	private Message shift(Message message){
		//initialize variables
		String shiftedMessage = "";
		int shiftAmount = 0;
		try{
			//make the message lowercase and get the shift amount
			message.setText(message.getText().toLowerCase());
			shiftAmount = Integer.parseInt(message.getKey());
			//loop through the message text string and shift the characters, and add them to the modified string
			for (int i = 0; i < message.getText().length(); i++){
				String currentCharacter = String.valueOf(message.getText().charAt(i));	
				if (alphabet.contains(currentCharacter)){
					int shifted = 0;
					if (!message.isEncrypted()) 
						shifted = alphabet.indexOf(currentCharacter)+shiftAmount;
					else
						shifted = alphabet.indexOf(currentCharacter)-shiftAmount;
					while (shifted >= 26) shifted -= 26;
					while (shifted < 0) shifted += 26;
					shiftedMessage += alphabet.charAt(shifted);
					//throw new NullPointerException();
				}
				else shiftedMessage += currentCharacter;
			}
		} catch (Exception e){
			error = "Invalid key or malformed message.";
		}
		return new Message(shiftedMessage, Integer.toString(shiftAmount), true);
	}
	
	@Override
	public String getError(){
		return error;
	}
}
