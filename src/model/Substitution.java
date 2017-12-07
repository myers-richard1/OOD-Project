package model;

import java.io.UnsupportedEncodingException;

public class Substitution implements EncryptionStrategy{
	private static final String alphabet = "abcdefghijklmnopqrstuvwxyz";
	private String error;//this is given a value if there was a problem with the encryption (usually because the user entered an invalid key
	@Override
	public Message encrypt(Message message){
		return substitute(message);
	}

	@Override
	public Message decrypt(Message message){
		return substitute(message);
	}
	
	private Message substitute(Message message){
		//initialize string to hold modified text
		String substitutedMessage = "";
		try{
			//get text and initalize strings to hold keys
			message.setText(message.getText().toLowerCase());
			String original = "", changed = ""; //original is the message's 'alphabet' in its current state, and changed is the target 'alphabet' we want to change it to
			//if the message is not encrypted, the original "alphabet" is the standard alphabet, and our target alphabet is the key
			if (!message.isEncrypted()){
				original = alphabet;
				changed = message.getKey();
			}
			//else if the message is encrypted, the original alphabet is the message's key, and the target alphabet is the standard alphabet
			else{
				original = message.getKey();
				changed = alphabet;
			}
			//loop through the string and replace any characters in the message's current alphabet with the corresponding character in the target alphabet
			for (int i = 0; i < message.getText().length(); i++){
				String currentCharacter = String.valueOf(message.getText().charAt(i));
				if (alphabet.contains(currentCharacter))
					substitutedMessage += changed.charAt(original.indexOf(currentCharacter));
				else 
					substitutedMessage += currentCharacter;
			}
		} catch(Exception e){
			error = "Invalid key or malformed message";
		}
		return new Message(substitutedMessage, message.getKey(), !message.isEncrypted());
	}
	
	public String getError(){
		return error;
	}
}
