package model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ElGamal implements EncryptionStrategy {
    final BigInteger ZERO=BigInteger.ZERO;
    final BigInteger ONE=BigInteger.ONE;
    final BigInteger TWO=BigInteger.ONE.add(BigInteger.ONE);
 
    // get a prime
    // i think i can do a do while?
    public BigInteger getPrime(int bL, int c, Random r) {
        BigInteger pPrime = new BigInteger(bL, c, r);
        // p = 2 * pPrime + 1
        BigInteger p = pPrime.multiply(TWO).add(BigInteger.ONE);

        // keep finding prime until certainty is met
        while (!p.isProbablePrime(c)) {
            pPrime = new BigInteger(bL, c, r);
            p = pPrime.multiply(TWO).add(BigInteger.ONE);
        }
        return p;
    }
    
    public BigInteger randNum(BigInteger N, Random r) {
        return new BigInteger(N.bitLength() + 100, r).mod(N);
    }

	public ArrayList<BigInteger> getKeys(int bL) {
		BigInteger p = getPrime(bL, 40, new Random());
		BigInteger g = randNum(p, new Random());
		BigInteger pPrime = p.subtract(ONE).divide(TWO);
		
		while (!g.modPow(pPrime, p).equals(ONE)) {
			if (g.modPow(pPrime.multiply(TWO), p).equals(ONE))
				g = g.modPow(TWO, p);
			else
				g = randNum(p, new Random());
		}
		
		BigInteger x = randNum(pPrime.subtract(ONE), new Random());
		BigInteger h = g.modPow(x,  p);

//		if i were to make a more secure program, i would use the below:
//		ArrayList<BigInteger> privateKey = new ArrayList<BigInteger>(Arrays.asList(p, x));
//		ArrayList<BigInteger> publicKey = new ArrayList<BigInteger>(Arrays.asList(p, g, h));
//		return new ArrayList<>(Arrays.asList(privateKey, publicKey));
		
		return new ArrayList<>(Arrays.asList(p, g, h, x));
	}

	// encrypt the message
	public Message encrypt(Message message) {
		MultiKeyMessage m = (MultiKeyMessage)message;
		BigInteger plain = new BigInteger(m.getText().getBytes());
		ArrayList<BigInteger> keys = getKeys(plain.bitLength());
		
		// somewhat unecessary, but makes it easier to understand
		BigInteger p = keys.get(0);
		BigInteger g = keys.get(1);
		BigInteger h = keys.get(2);
		BigInteger x = keys.get(3);
		m.setKey(p+","+g+","+h+","+x);
		// get pPrime from p
		BigInteger pPrime = p.subtract(ONE).divide(TWO);
		// r cant be bigger than pPrime
		BigInteger r = randNum(pPrime, new Random());
		
		// encoded messages always have two parts (c1, c2)
		// c1 is g^r mod p
		// c2 is message * h^r mod p
		// c1 used to get shared secret
		// use shared secret to get message from c2
		ArrayList<BigInteger> encMessage = new ArrayList<>(Arrays.asList(g.modPow(r,  p), plain.multiply(h.modPow(r, p))));
		
		m.setMultiKey(new ArrayList<String>(Arrays.asList(p.toString(), g.toString(), h.toString(), x.toString())));
		m.setEncrypted(true);
		m.setText(encMessage.toString());
		
		return m;
	}
	
	public Message decrypt(Message message) {
		MultiKeyMessage m = (MultiKeyMessage)message;
		// so, when we encrypted, its in 2 parts
		// [c1, c2]
		String encMessage = m.getText();
		// first get rid of brackets
		encMessage = encMessage.replaceAll("\\[", "").replaceAll("\\]","");
		// now split it into array on comma
		String[] sM = encMessage.split(",");
		// trim each string in array, because there's some spaces in there
		BigInteger gr = new BigInteger(sM[0].trim());
		BigInteger mhr = new BigInteger(sM[1].trim());
		
		// get the keys stored in message
		BigInteger x = new BigInteger(m.getMultiKey().get(3));
		BigInteger p = new BigInteger(m.getMultiKey().get(0));
		
		BigInteger hr = gr.modPow(x, p);
		BigInteger decMessage = mhr.multiply(hr.modInverse(p)).mod(p);
		
		// converting BigInt to byte sometimes has leading characters
		// this just gets rid of it
		byte[] array = decMessage.toByteArray();
		if (array[0] == 0) {
		    byte[] tmp = new byte[array.length - 1];
		    System.arraycopy(array, 1, tmp, 0, tmp.length);
		    array = tmp;
		}
		
		// set it to unencrypted
		// set text to plaintext
		// set keys to null
		m.setEncrypted(false);
		m.setText(new String(array));
		m.setKey(null);
		
		return m;
	}

}
