package model;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSA implements EncryptionStrategy {
	private final String alg = "RSA";
	private	String error; //this is given a value if there was a problem with the encryption (usually because the user entered an invalid key

	public KeyPair generateKeys() throws NoSuchAlgorithmException {
		final int keySize = 1024;
		KeyPairGenerator kpg = KeyPairGenerator.getInstance(alg);
		kpg.initialize(keySize);
		return kpg.generateKeyPair();
	}

	public Message encrypt(Message message){
		MultiKeyMessage m = (MultiKeyMessage)message;
		String plainText = m.getText();
		byte[] encrypted = null;
		KeyPair keys = null;
		try{
			Cipher c = Cipher.getInstance(alg);
			keys = generateKeys();

			c.init(Cipher.ENCRYPT_MODE, keys.getPublic());

			// do encryption
			encrypted = c.doFinal(plainText.getBytes());
		} catch (Exception e){
			error = "Invalid key or malformed message";
		}
		
		// set the encrypted text as the text
		m.setText(Base64.getEncoder().encodeToString(encrypted));
		// set flag to encrypted
		m.setEncrypted(true);
		// set private key as first item, set public key as second item
		m.setMultiKey(new ArrayList<String>(Arrays.asList(Base64.getEncoder().encodeToString(keys.getPrivate().getEncoded()), Base64.getEncoder().encodeToString(keys.getPublic().getEncoded()))));
		String commaKey = null;
		for (String s : m.getMultiKey()){
			System.out.print(s+",");
			commaKey += s+",";
		}
		m.setKey(commaKey);
		
		return m;
	}

	public Message decrypt(Message message){
		MultiKeyMessage m = (MultiKeyMessage)message;
		try{// get text
			String cipherText = m.getText();
			Cipher c = Cipher.getInstance(alg);
			// key was encoded, so decode it
			byte[] privateBytes = Base64.getDecoder().decode(m.getMultiKey().get(0));
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateBytes);
			KeyFactory kf = KeyFactory.getInstance(alg);
			PrivateKey privateKey = kf.generatePrivate(keySpec);
	
			c.init(Cipher.DECRYPT_MODE, privateKey);
	
			// have to decode the cipher text
			// then decrypt
			byte[] decoded = Base64.getDecoder().decode(cipherText);
			byte[] decrypted = c.doFinal(decoded);
	
			// set plain text as text
			m.setText(new String(decrypted));
			// set flag to not encrypted
			m.setEncrypted(false);
			// not necessary, but set key to nothing
			m.setKey(null);
		} catch(Exception e){
			error = "Invalid key or malformed message";
		}
		return m;
	}
	
	public String getError(){
		return error;
	}
}
