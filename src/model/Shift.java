package model;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

public class Shift implements EncryptionStrategy{
	private static final String alphabet = "abcdefghijklmnopqrstuvwxyz";
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
		message.setText(message.getText().toLowerCase());
		int shiftAmount = Integer.parseInt(message.getKey());
		String shiftedMessage = "";
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
		return new Message(shiftedMessage, Integer.toString(-shiftAmount), true);
	}
	
	public static void main(String[] args){
		Shift test = new Shift();
		try {
			System.out.println(test.encrypt(new Message("this is a test", "1", false)).getText());
			System.out.println(test.decrypt(new Message("uijt jt b uftu", "1", true)).getText());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
