package model;
import java.io.UnsupportedEncodingException;

public interface EncryptionStrategy {

    public Message encrypt(Message message) throws UnsupportedEncodingException,NumberFormatException;
    public Message decrypt(Message message)throws ArrayIndexOutOfBoundsException;
}
