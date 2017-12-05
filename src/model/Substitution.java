package model;

import java.io.UnsupportedEncodingException;

public class Substitution implements EncryptionStrategy{
	private static final String alphabet = "abcdefghijklmnopqrstuvwxyz";
	@Override
	public Message encrypt(Message message) throws UnsupportedEncodingException, NumberFormatException {
		return substitute(message);
	}

	@Override
	public Message decrypt(Message message) throws ArrayIndexOutOfBoundsException {
		return substitute(message);
	}
	
	private Message substitute(Message message){
		String original = "", changed = "";
		if (!message.isEncrypted()){
			original = alphabet;
			changed = message.getKey();
		}
		else{
			original = message.getKey();
			changed = alphabet;
		}
		String substitutedMessage = "";
		for (int i = 0; i < message.getText().length(); i++){
			String currentCharacter = String.valueOf(message.getText().charAt(i));
			if (alphabet.contains(currentCharacter))
				substitutedMessage += changed.charAt(original.indexOf(currentCharacter));
			else 
				substitutedMessage += currentCharacter;
		}
		return new Message(substitutedMessage, message.getKey(), !message.isEncrypted());
	}
	
	public static void main(String[] args){
		Substitution test = new Substitution();
		try {
			System.out.println(test.encrypt(new Message("this is a test", "zyxwvutsrqponmlkjihgfedcba", false)).getText());
			System.out.println(test.encrypt(new Message("gsrh rh z gvhg", "zyxwvutsrqponmlkjihgfedcba", true)).getText());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
	}

}
