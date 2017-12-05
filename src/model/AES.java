package model;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

public class AES  implements EncryptionStrategy {
	private final String alg = "AES";
	
	// generate a key
	// all keys are same size
	private Key generateKey() throws NoSuchAlgorithmException {
		//AES keys are 128 bits long
		// 1 byte is 8 bits
		// 128 / 8 = 16 bytes needed
		final byte[] key = new byte[16];
		SecureRandom.getInstanceStrong().nextBytes(key);
		return new SecretKeySpec(key, alg);
	}
	
	// encrypt message
	public Message encrypt(Message message){
		// get text from message
		String plainText = message.getText();
		// generate a new key each time you encrypt
		try{
			Key k = generateKey();
			
			// set algorithm
			Cipher c = Cipher.getInstance(alg);
			// init cipher
			c.init(Cipher.ENCRYPT_MODE, k);
			
			// do encryption
			byte[] encrypted = c.doFinal(plainText.getBytes());
			
			// set the encrypted text as the text
			message.setText(Base64.getEncoder().encodeToString(encrypted));
			// set flag to encrypted
			message.setEncrypted(true);
			// set the key in message (super safe)
			message.setKey(Base64.getEncoder().encodeToString(k.getEncoded()));
		} catch (Exception e){
			e.printStackTrace();
		}
		return message;
	}
	
	// decrypt message
	public Message decrypt(Message message){
		// get text
		String cipherText = message.getText();
		// have to use same key that was used to encrypt text
		try{
			Key k = new SecretKeySpec(Base64.getDecoder().decode(message.getKey()), alg);
		
		
			// set algorithm
			Cipher c = Cipher.getInstance(alg);
			// init cipher
			c.init(Cipher.DECRYPT_MODE, k);
			
			// have to decode the cipher text
			// then decrypt
			byte[] decoded = Base64.getDecoder().decode(cipherText);
			byte[] decrypted = c.doFinal(decoded);
			
			// set plain text as text
			message.setText(new String(decrypted));
			// set flag to not encrypted
			message.setEncrypted(false);
			// not necessary, but set key to nothing
			message.setKey(null);
		} catch (Exception e){
			e.printStackTrace();
		}
		return message;
	}

}
